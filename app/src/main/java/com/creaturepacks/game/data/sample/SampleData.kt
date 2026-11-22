package com.creaturepacks.game.data.sample

import com.creaturepacks.game.domain.model.BoosterSeries
import com.creaturepacks.game.domain.model.Card
import com.creaturepacks.game.domain.model.CardRarity
import com.creaturepacks.game.domain.model.CreatureType
import android.content.Context
import androidx.core.content.res.ResourcesCompat

val SET_CODE_GEN = "GEN"
val SET_CODE_EMB = "EMB"
val SET_CODE_FRO = "FRO"
val SET_CODE_CRY = "CRY"

/**
 * Original creature cards for the Genesis booster set.
 * All names and artwork placeholders are original; no licensed IP is used.
 */
val GENESIS_CARDS: List<Card> = listOf(
    // Common (6)
    Card("gen_001", "Cinderpup", "Cinderpup", CreatureType.EMBER, CardRarity.COMMON,
        "art_gen_cinderpup", "A small fox-like creature with smoldering embers in its fur.", SET_CODE_GEN, 1),
    Card("gen_002", "Glimminnow", "Glimminnow", CreatureType.SPARK, CardRarity.COMMON,
        "art_gen_glimminnow", "A tiny fish that glows with faint static electricity.", SET_CODE_GEN, 2),
    Card("gen_003", "Bramblekin", "Bramblekin", CreatureType.LEAF, CardRarity.COMMON,
        "art_gen_bramblekin", "A woodland spirit wrapped in living vines.", SET_CODE_GEN, 3),
    Card("gen_004", "Pebbleback", "Pebbleback", CreatureType.STONE, CardRarity.COMMON,
        "art_gen_pebbleback", "A slow, steady creature with a rocky shell.", SET_CODE_GEN, 4),
    Card("gen_005", "Dewmoth", "Dewmoth", CreatureType.FROST, CardRarity.COMMON,
        "art_gen_dewmoth", "A moth whose wings collect morning frost.", SET_CODE_GEN, 5),
    Card("gen_006", "Dustspire", "Dustspire", CreatureType.SHADOW, CardRarity.COMMON,
        "art_gen_dustspire", "A wisp formed from ancient cave dust.", SET_CODE_GEN, 6),
    // Uncommon (6)
    Card("gen_007", "Embermaw", "Embermaw", CreatureType.EMBER, CardRarity.UNCOMMON,
        "art_gen_embermaw", "A burrowing lizard that breathes warm cinders.", SET_CODE_GEN, 7),
    Card("gen_008", "Glintfin", "Glintfin", CreatureType.SPARK, CardRarity.UNCOMMON,
        "art_gen_glintfin", "A swift fish with metallic scales that flash in light.", SET_CODE_GEN, 8),
    Card("gen_009", "Vinesnap", "Vinesnap", CreatureType.LEAF, CardRarity.UNCOMMON,
        "art_gen_vinesnap", "A plant beast with quick, snapping tendrils.", SET_CODE_GEN, 9),
    Card("gen_010", "Bouldertusk", "Bouldertusk", CreatureType.STONE, CardRarity.UNCOMMON,
        "art_gen_bouldertusk", "A boar-like creature armored in layered stone.", SET_CODE_GEN, 10),
    Card("gen_011", "Frostmaw", "Frostmaw", CreatureType.FROST, CardRarity.UNCOMMON,
        "art_gen_frostmaw", "A wolf whose howl freezes the air around it.", SET_CODE_GEN, 11),
    Card("gen_012", "Shroudmire", "Shroudmire", CreatureType.SHADOW, CardRarity.UNCOMMON,
        "art_gen_shroudmire", "A silent stalker that blends into twilight.", SET_CODE_GEN, 12),
    // Rare (6)
    Card("gen_013", "Pyroenix", "Pyroenix", CreatureType.EMBER, CardRarity.RARE,
        "art_gen_pyroenix", "A majestic bird wreathed in perpetual flame.", SET_CODE_GEN, 13),
    Card("gen_014", "Volttail", "Volttail", CreatureType.SPARK, CardRarity.RARE,
        "art_gen_volttail", "A sleek feline crackling with raw electricity.", SET_CODE_GEN, 14),
    Card("gen_015", "Thornheart", "Thornheart", CreatureType.LEAF, CardRarity.RARE,
        "art_gen_thornheart", "A guardian spirit of the deep thicket.", SET_CODE_GEN, 15),
    Card("gen_016", "Glacierhorn", "Glacierhorn", CreatureType.FROST, CardRarity.RARE,
        "art_gen_glacierhorn", "A stag with antlers made of solid ice.", SET_CODE_GEN, 16),
    Card("gen_017", "Obsidian", "Obsidian", CreatureType.STONE, CardRarity.RARE,
        "art_gen_obsidian", "A golem carved from volcanic glass.", SET_CODE_GEN, 17),
    Card("gen_018", "Nightshade", "Nightshade", CreatureType.SHADOW, CardRarity.RARE,
        "art_gen_nightshade", "A flower that blooms only in total darkness.", SET_CODE_GEN, 18),
    // Epic (3)
    Card("gen_019", "Solarion", "Solarion", CreatureType.LIGHT, CardRarity.EPIC,
        "art_gen_solarion", "A radiant being that carries a fragment of dawn.", SET_CODE_GEN, 19),
    Card("gen_020", "Umbrax", "Umbrax", CreatureType.SHADOW, CardRarity.EPIC,
        "art_gen_umbrax", "A lord of shadows that moves between realms.", SET_CODE_GEN, 20),
    Card("gen_021", "Crystalwing", "Crystalwing", CreatureType.CRYSTAL, CardRarity.EPIC,
        "art_gen_crystalwing", "A dragon whose scales are pure prismatic crystal.", SET_CODE_GEN, 21),
    // Legendary (2)
    Card("gen_022", "Aurelion", "Aurelion", CreatureType.LIGHT, CardRarity.LEGENDARY,
        "art_gen_aurelion", "The first light, said to awaken all creatures.", SET_CODE_GEN, 22),
    Card("gen_023", "Netheron", "Netheron", CreatureType.SHADOW, CardRarity.LEGENDARY,
        "art_gen_netheron", "The void between stars, given form.", SET_CODE_GEN, 23),
    // Secret (1)
    Card("gen_024", "Chromalis", "Chromalis", CreatureType.CRYSTAL, CardRarity.SECRET,
        "art_gen_chromalis", "A mythical creature that refracts every color at once.", SET_CODE_GEN, 24)
)

/**
 * Ember Reach - Volcanic creatures from the burning lands.
 */
val EMBER_REACH_CARDS: List<Card> = listOf(
    // Common (6)
    Card("emb_001", "Magmaling", "Magmaling", CreatureType.EMBER, CardRarity.COMMON,
        "art_emb_magmaling", "A tiny droplet of living magma that hops across volcanic rock.", SET_CODE_EMB, 1),
    Card("emb_002", "Cinderhopper", "Cinderhopper", CreatureType.EMBER, CardRarity.COMMON,
        "art_emb_cinderhopper", "A grasshopper-like insect that leaves scorch marks with every leap.", SET_CODE_EMB, 2),
    Card("emb_003", "Ashpuff", "Ashpuff", CreatureType.EMBER, CardRarity.COMMON,
        "art_emb_ashpuff", "A floating spore that drifts on hot updrafts.", SET_CODE_EMB, 3),
    Card("emb_004", "Slagmite", "Slagmite", CreatureType.STONE, CardRarity.COMMON,
        "art_emb_slagmite", "A small crustacean with a shell of cooled lava.", SET_CODE_EMB, 4),
    Card("emb_005", "Smolderling", "Smolderling", CreatureType.EMBER, CardRarity.COMMON,
        "art_emb_smolderling", "A timid creature that hides in warm embers.", SET_CODE_EMB, 5),
    Card("emb_006", "Flarefin", "Flarefin", CreatureType.SPARK, CardRarity.COMMON,
        "art_emb_flarefin", "A fish that swims through rivers of liquid fire.", SET_CODE_EMB, 6),
    // Uncommon (6)
    Card("emb_007", "Lavaback", "Lavaback", CreatureType.EMBER, CardRarity.UNCOMMON,
        "art_emb_lavaback", "A turtle with a shell of flowing magma.", SET_CODE_EMB, 7),
    Card("emb_008", "Ignisnake", "Ignisnake", CreatureType.EMBER, CardRarity.UNCOMMON,
        "art_emb_ignisnake", "A serpent that leaves a trail of fire in its wake.", SET_CODE_EMB, 8),
    Card("emb_009", "Cinderclaw", "Cinderclaw", CreatureType.STONE, CardRarity.UNCOMMON,
        "art_emb_cinderclaw", "A crab with claws that glow white-hot.", SET_CODE_EMB, 9),
    Card("emb_010", "Pyrobat", "Pyrobat", CreatureType.SHADOW, CardRarity.UNCOMMON,
        "art_emb_pyrobat", "A bat that navigates by sensing heat signatures.", SET_CODE_EMB, 10),
    Card("emb_011", "Emberhorn", "Emberhorn", CreatureType.EMBER, CardRarity.UNCOMMON,
        "art_emb_emberhorn", "A ram whose horns burn with eternal flame.", SET_CODE_EMB, 11),
    Card("emb_012", "Sparkspine", "Sparkspine", CreatureType.SPARK, CardRarity.UNCOMMON,
        "art_emb_sparkspine", "A porcupine that shoots electric quills.", SET_CODE_EMB, 12),
    // Rare (6)
    Card("emb_013", "Magmawyrm", "Magmawyrm", CreatureType.EMBER, CardRarity.RARE,
        "art_emb_magmawyrm", "A massive serpent that dwells in the planet's core.", SET_CODE_EMB, 13),
    Card("emb_014", "Infernotaur", "Infernotaur", CreatureType.EMBER, CardRarity.RARE,
        "art_emb_infernotaur", "A minotaur forged from living magma and blackened steel.", SET_CODE_EMB, 14),
    Card("emb_015", "Volcaron", "Volcaron", CreatureType.STONE, CardRarity.RARE,
        "art_emb_volcaron", "A towering golem that erupts with each step.", SET_CODE_EMB, 15),
    Card("emb_016", "Blazewing", "Blazewing", CreatureType.SPARK, CardRarity.RARE,
        "art_emb_blazewing", "A phoenix-like bird of pure plasma.", SET_CODE_EMB, 16),
    Card("emb_017", "Cinderfang", "Cinderfang", CreatureType.SHADOW, CardRarity.RARE,
        "art_emb_cinderfang", "A panther that stalks through ash clouds.", SET_CODE_EMB, 17),
    Card("emb_018", "Slagmaw", "Slagmaw", CreatureType.STONE, CardRarity.RARE,
        "art_emb_slagmaw", "A wurm that devours minerals and excretes molten ore.", SET_CODE_EMB, 18),
    // Epic (3)
    Card("emb_019", "Pyroclasm", "Pyroclasm", CreatureType.EMBER, CardRarity.EPIC,
        "art_emb_pyroclasm", "An elemental avatar of volcanic destruction.", SET_CODE_EMB, 19),
    Card("emb_020", "Solarflare", "Solarflare", CreatureType.LIGHT, CardRarity.EPIC,
        "art_emb_solarflare", "A being of concentrated stellar energy.", SET_CODE_EMB, 20),
    Card("emb_021", "Obsidianheart", "Obsidianheart", CreatureType.STONE, CardRarity.EPIC,
        "art_emb_obsidianheart", "An ancient dragon sleeping beneath the crust.", SET_CODE_EMB, 21),
    // Legendary (2)
    Card("emb_022", "Vulkanos", "Vulkanos", CreatureType.EMBER, CardRarity.LEGENDARY,
        "art_emb_vulkanos", "The first volcano, given consciousness and form.", SET_CODE_EMB, 22),
    Card("emb_023", "Helios Prime", "Helios Prime", CreatureType.LIGHT, CardRarity.LEGENDARY,
        "art_emb_heliosprime", "A fallen star that burns with righteous fire.", SET_CODE_EMB, 23),
    // Secret (1)
    Card("emb_024", "Eternal Flame", "Eternal Flame", CreatureType.CRYSTAL, CardRarity.SECRET,
        "art_emb_eternalflame", "A flame that has burned since the world's creation.", SET_CODE_EMB, 24)
)

/**
 * Frost Veil - Icy creatures from the frozen north.
 */
val FROST_VEIL_CARDS: List<Card> = listOf(
    // Common (6)
    Card("fro_001", "Frostling", "Frostling", CreatureType.FROST, CardRarity.COMMON,
        "art_fro_frostling", "A small humanoid made of packed snow.", SET_CODE_FRO, 1),
    Card("fro_002", "Iceguppy", "Iceguppy", CreatureType.FROST, CardRarity.COMMON,
        "art_fro_iceguppy", "A tiny fish with antifreeze proteins in its blood.", SET_CODE_FRO, 2),
    Card("fro_003", "Snowdrift", "Snowdrift", CreatureType.FROST, CardRarity.COMMON,
        "art_fro_snowdrift", "A creature that looks like a moving pile of snow.", SET_CODE_FRO, 3),
    Card("fro_004", "Glaciermite", "Glaciermite", CreatureType.STONE, CardRarity.COMMON,
        "art_fro_glaciermite", "A microscopic organism that thrives in ice cores.", SET_CODE_FRO, 4),
    Card("fro_005", "Rimewind", "Rimewind", CreatureType.SHADOW, CardRarity.COMMON,
        "art_fro_rimewind", "A wisp of freezing air given vague form.", SET_CODE_FRO, 5),
    Card("fro_006", "Chillspore", "Chillspore", CreatureType.LEAF, CardRarity.COMMON,
        "art_fro_chillspore", "A fungus that releases clouds of frost.", SET_CODE_FRO, 6),
    // Uncommon (6)
    Card("fro_007", "Iceclaw", "Iceclaw", CreatureType.FROST, CardRarity.UNCOMMON,
        "art_fro_iceclaw", "A bear with claws of transparent blue ice.", SET_CODE_FRO, 7),
    Card("fro_008", "Frostfang", "Frostfang", CreatureType.FROST, CardRarity.UNCOMMON,
        "art_fro_frostfang", "A wolf whose breath creates instant blizzards.", SET_CODE_FRO, 8),
    Card("fro_009", "Glacierback", "Glacierback", CreatureType.STONE, CardRarity.UNCOMMON,
        "art_fro_glacierback", "A slow-moving turtle with a shell of ancient ice.", SET_CODE_FRO, 9),
    Card("fro_010", "Snowstalker", "Snowstalker", CreatureType.SHADOW, CardRarity.UNCOMMON,
        "art_fro_snowstalker", "A predator that becomes invisible in snowstorms.", SET_CODE_FRO, 10),
    Card("fro_011", "Icethorn", "Icethorn", CreatureType.LEAF, CardRarity.UNCOMMON,
        "art_fro_icethorn", "A plant that grows razor-sharp icicles instead of thorns.", SET_CODE_FRO, 11),
    Card("fro_012", "Hailstrider", "Hailstrider", CreatureType.SPARK, CardRarity.UNCOMMON,
        "art_fro_hailstrider", "A bird that rides lightning through thunderstorms.", SET_CODE_FRO, 12),
    // Rare (6)
    Card("fro_013", "Blizzardwyrm", "Blizzardwyrm", CreatureType.FROST, CardRarity.RARE,
        "art_fro_blizzardwyrm", "A serpentine dragon that summons whiteouts.", SET_CODE_FRO, 13),
    Card("fro_014", "Frost Giant", "Frost Giant", CreatureType.FROST, CardRarity.RARE,
        "art_fro_frostgiant", "A towering humanoid of compacted ice and snow.", SET_CODE_FRO, 14),
    Card("fro_015", "Cryostone", "Cryostone", CreatureType.STONE, CardRarity.RARE,
        "art_fro_cryostone", "A golem carved from never-melting ice.", SET_CODE_FRO, 15),
    Card("fro_016", "Aurorawing", "Aurorawing", CreatureType.LIGHT, CardRarity.RARE,
        "art_fro_aurorawing", "A bird whose wings paint the sky with northern lights.", SET_CODE_FRO, 16),
    Card("fro_017", "Glacial Heart", "Glacial Heart", CreatureType.CRYSTAL, CardRarity.RARE,
        "art_fro_glacialheart", "A massive crystal that beats like a frozen heart.", SET_CODE_FRO, 17),
    Card("fro_018", "Winter's Grasp", "Winter's Grasp", CreatureType.SHADOW, CardRarity.RARE,
        "art_fro_wintersgrasp", "An entity that freezes the very concept of warmth.", SET_CODE_FRO, 18),
    // Epic (3)
    Card("fro_019", "Permafrost", "Permafrost", CreatureType.FROST, CardRarity.EPIC,
        "art_fro_permafrost", "An ancient spirit of eternal winter.", SET_CODE_FRO, 19),
    Card("fro_020", "Cryos", "Cryos", CreatureType.CRYSTAL, CardRarity.EPIC,
        "art_fro_cryos", "A dragon of living diamond and ice.", SET_CODE_FRO, 20),
    Card("fro_021", "Borealis", "Borealis", CreatureType.LIGHT, CardRarity.EPIC,
        "art_fro_borealis", "The personification of the aurora itself.", SET_CODE_FRO, 21),
    // Legendary (2)
    Card("fro_022", "Ymir's Echo", "Ymir's Echo", CreatureType.FROST, CardRarity.LEGENDARY,
        "art_fro_ymisecho", "A fragment of the primordial frost giant.", SET_CODE_FRO, 22),
    Card("fro_023", "The White Walker", "The White Walker", CreatureType.SHADOW, CardRarity.LEGENDARY,
        "art_fro_whitewalker", "Death given form in the frozen wastes.", SET_CODE_FRO, 23),
    // Secret (1)
    Card("fro_024", "Absolute Zero", "Absolute Zero", CreatureType.CRYSTAL, CardRarity.SECRET,
        "art_fro_absolutezero", "The theoretical temperature where all motion ceases.", SET_CODE_FRO, 24)
)

/**
 * Crystal Spire - Rare crystal creatures from deep underground.
 */
val CRYSTAL_SPIRE_CARDS: List<Card> = listOf(
    // Common (6)
    Card("cry_001", "Shardling", "Shardling", CreatureType.CRYSTAL, CardRarity.COMMON,
        "art_cry_shardling", "A small fragment of crystal that moves with purpose.", SET_CODE_CRY, 1),
    Card("cry_002", "Glimmerite", "Glimmerite", CreatureType.CRYSTAL, CardRarity.COMMON,
        "art_cry_glimmerite", "A soft mineral that glows gently in darkness.", SET_CODE_CRY, 2),
    Card("cry_003", "Quartzling", "Quartzling", CreatureType.CRYSTAL, CardRarity.COMMON,
        "art_cry_quartzling", "A tiny creature of clear quartz.", SET_CODE_CRY, 3),
    Card("cry_004", "Geodude", "Geodude", CreatureType.STONE, CardRarity.COMMON,
        "art_cry_geodude", "A rock with simple crystalline growths.", SET_CODE_CRY, 4),
    Card("cry_005", "Prismite", "Prismite", CreatureType.LIGHT, CardRarity.COMMON,
        "art_cry_prismite", "A being that splits light into rainbow fragments.", SET_CODE_CRY, 5),
    Card("cry_006", "Facetwisp", "Facetwisp", CreatureType.SHADOW, CardRarity.COMMON,
        "art_cry_facetwisp", "A shadow that reflects like a dark mirror.", SET_CODE_CRY, 6),
    // Uncommon (6)
    Card("cry_007", "Crystalback", "Crystalback", CreatureType.CRYSTAL, CardRarity.UNCOMMON,
        "art_cry_crystalback", "A turtle with a shell of interlocking gems.", SET_CODE_CRY, 7),
    Card("cry_008", "Amethysthorn", "Amethysthorn", CreatureType.CRYSTAL, CardRarity.UNCOMMON,
        "art_cry_amethysthorn", "A deer with antlers of purple crystal.", SET_CODE_CRY, 8),
    Card("cry_009", "Diamondclaw", "Diamondclaw", CreatureType.STONE, CardRarity.UNCOMMON,
        "art_cry_diamondclaw", "A predator with claws that can cut glass.", SET_CODE_CRY, 9),
    Card("cry_010", "Opalshade", "Opalshade", CreatureType.SHADOW, CardRarity.UNCOMMON,
        "art_cry_opalshade", "A creature that shifts colors like an opal.", SET_CODE_CRY, 10),
    Card("cry_011", "Berylvine", "Berylvine", CreatureType.LEAF, CardRarity.UNCOMMON,
        "art_cry_berylvine", "A plant with leaves of green beryl.", SET_CODE_CRY, 11),
    Card("cry_012", "Topazwing", "Topazwing", CreatureType.SPARK, CardRarity.UNCOMMON,
        "art_cry_topazwing", "A moth with wings of golden topaz.", SET_CODE_CRY, 12),
    // Rare (6)
    Card("cry_013", "Emeraldwyrm", "Emeraldwyrm", CreatureType.CRYSTAL, CardRarity.RARE,
        "art_cry_emeraldwyrm", "A serpent of flawless emerald.", SET_CODE_CRY, 13),
    Card("cry_014", "Rubyguardian", "Rubyguardian", CreatureType.CRYSTAL, CardRarity.RARE,
        "art_cry_rubyguardian", "A knight animated by a heart of ruby.", SET_CODE_CRY, 14),
    Card("cry_015", "Sapphiregolem", "Sapphiregolem", CreatureType.STONE, CardRarity.RARE,
        "art_cry_sapphiregolem", "A colossus of deep blue crystal.", SET_CODE_CRY, 15),
    Card("cry_016", "Prismdrake", "Prismdrake", CreatureType.LIGHT, CardRarity.RARE,
        "art_cry_prismdrake", "A dragon that refracts magic into pure light.", SET_CODE_CRY, 16),
    Card("cry_017", "Onyxphantom", "Onyxphantom", CreatureType.SHADOW, CardRarity.RARE,
        "art_cry_onyxphantom", "A specter of absolute black crystal.", SET_CODE_CRY, 17),
    Card("cry_018", "Rainbowgeode", "Rainbowgeode", CreatureType.CRYSTAL, CardRarity.RARE,
        "art_cry_rainbowgeode", "A living geode containing every color.", SET_CODE_CRY, 18),
    // Epic (3)
    Card("cry_019", "Crystalheart", "Crystalheart", CreatureType.CRYSTAL, CardRarity.EPIC,
        "art_cry_crystalheart", "The core of the world, given consciousness.", SET_CODE_CRY, 19),
    Card("cry_020", "Diamondwyrm", "Diamondwyrm", CreatureType.CRYSTAL, CardRarity.EPIC,
        "art_cry_diamondwyrm", "An ancient dragon of the hardest substance.", SET_CODE_CRY, 20),
    Card("cry_021", "Prismlord", "Prismlord", CreatureType.LIGHT, CardRarity.EPIC,
        "art_cry_prismlord", "A ruler of the light spectrum itself.", SET_CODE_CRY, 21),
    // Legendary (2)
    Card("cry_022", "The First Facet", "The First Facet", CreatureType.CRYSTAL, CardRarity.LEGENDARY,
        "art_cry_firstfacet", "The original crystal from which all others formed.", SET_CODE_CRY, 22),
    Card("cry_023", "Voidcrystal", "Voidcrystal", CreatureType.SHADOW, CardRarity.LEGENDARY,
        "art_cry_voidcrystal", "A crystal that absorbs all light and hope.", SET_CODE_CRY, 23),
    // Secret (1)
    Card("cry_024", "Singularity", "Singularity", CreatureType.CRYSTAL, CardRarity.SECRET,
        "art_cry_singularity", "A point of infinite density and beauty.", SET_CODE_CRY, 24)
)

fun allCards(): List<Card> = GENESIS_CARDS + EMBER_REACH_CARDS + FROST_VEIL_CARDS + CRYSTAL_SPIRE_CARDS

val BOOSTER_SERIES: List<BoosterSeries> = listOf(
    BoosterSeries(
        code = "genesis",
        title = "Genesis",
        description = "The original set of 24 discovered creatures.",
        price = 150,
        illustration = "pack_genesis",
        isUnlocked = true
    ),
    BoosterSeries(
        code = "ember_reach",
        title = "Ember Reach",
        description = "Volcanic creatures from the burning lands.",
        price = 250,
        illustration = "pack_ember",
        isUnlocked = false,
        unlockRequirementSeries = "genesis"
    ),
    BoosterSeries(
        code = "frost_veil",
        title = "Frost Veil",
        description = "Icy creatures from the frozen north.",
        price = 250,
        illustration = "pack_frost",
        isUnlocked = false,
        unlockRequirementSeries = "genesis"
    ),
    BoosterSeries(
        code = "crystal_spire",
        title = "Crystal Spire",
        description = "Rare crystal creatures from deep underground.",
        price = 500,
        illustration = "pack_crystal",
        isUnlocked = false,
        unlockRequirementSeries = "ember_reach"
    )
)
