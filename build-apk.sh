#!/bin/sh
# Gradle-less Android APK build: aapt2 + javac + d8 + apksigner
set -eu

PROJECT="/storage/emulated/0/Documents/Pack opening"
cd "$PROJECT"

MIN_SDK=24
TARGET_SDK=35
VERSION_CODE=1
VERSION_NAME="1.0.0"
KEYSTORE="$HOME/.android/debug.keystore"
KS_ALIAS=androiddebugkey
KS_PASS=android
export KS_PASS

fail() { echo "build-apk: $*" >&2; exit 1; }
need() { command -v "$1" >/dev/null 2>&1 || fail "$1 is not installed. Run android_sdk_install first."; }

for tool in aapt2 javac jar d8 apksigner keytool; do need "$tool"; done
[ -f AndroidManifest.xml ] || fail "AndroidManifest.xml not found"

# Find android.jar
if [ -z "${ANDROID_JAR:-}" ]; then
    ANDROID_JAR=$(ls -d "$HOME"/android-sdk/platforms/android-*/android.jar 2>/dev/null | sort | tail -n 1 || true)
fi
[ -n "${ANDROID_JAR:-}" ] && [ -f "$ANDROID_JAR" ] || fail "android.jar not found. Run android_sdk_install, or set ANDROID_JAR."

PACKAGE=$(sed -n 's/.*package="\([^"]*\)".*/\1/p' AndroidManifest.xml | head -n 1)
[ -n "$PACKAGE" ] || fail "AndroidManifest.xml has no package attribute"

rm -rf build
mkdir -p build/classes build/dex build/gen

# Compile resources with aapt2
aapt2 compile --dir res -o build/res.zip

# Link resources and generate R.java
aapt2 link -I "$ANDROID_JAR" --manifest AndroidManifest.xml \
    --java build/gen --min-sdk-version "$MIN_SDK" --target-sdk-version "$TARGET_SDK" \
    --version-code "$VERSION_CODE" --version-name "$VERSION_NAME" \
    -o build/base.apk build/res.zip

# Compile Java sources
CLASSPATH_JARS="$ANDROID_JAR"
find src build/gen -name '*.java' > build/sources.txt
[ -s build/sources.txt ] || fail "no .java sources under src/"
javac -source 8 -target 8 -bootclasspath "$CLASSPATH_JARS" -Xlint:-options \
    -d build/classes @build/sources.txt

# Create classes.jar
jar cf build/classes.jar -C build/classes .

# Dex with d8
d8 --min-api "$MIN_SDK" --lib "$ANDROID_JAR" --output build/dex build/classes.jar

# Add DEX to APK (keep resources.arsc aligned)
cp build/base.apk build/unsigned.apk
for dex in build/dex/classes*.dex; do
    jar --update --file build/unsigned.apk -C build/dex "$(basename "$dex")"
done

# Sign
if [ ! -f "$KEYSTORE" ]; then
    mkdir -p "$(dirname "$KEYSTORE")"
    keytool -genkeypair -keystore "$KEYSTORE" -storepass "$KS_PASS" -keypass "$KS_PASS" \
        -alias "$KS_ALIAS" -keyalg RSA -keysize 2048 -validity 10000 \
        -dname "CN=Android Debug,O=Android,C=US"
fi

OUT="build/$PACKAGE.apk"
apksigner sign --ks "$KEYSTORE" --ks-key-alias "$KS_ALIAS" \
    --ks-pass env:KS_PASS --key-pass env:KS_PASS --out "$OUT" build/unsigned.apk
apksigner verify "$OUT"

echo "APK: $(pwd)/$OUT"
echo "Package: $PACKAGE"
