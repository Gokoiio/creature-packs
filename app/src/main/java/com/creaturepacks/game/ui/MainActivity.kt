package com.creaturepacks.game.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CollectionsBookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Shop
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.creaturepacks.game.di.AppModule
import com.creaturepacks.game.ui.screen.BoosterShopScreen
import com.creaturepacks.game.ui.screen.CollectionScreen
import com.creaturepacks.game.ui.screen.HomeScreen
import com.creaturepacks.game.ui.screen.PackOpeningScreen
import com.creaturepacks.game.ui.screen.ProfileScreen
import com.creaturepacks.game.ui.theme.CreatureColors
import com.creaturepacks.game.ui.theme.CreaturePacksTheme
import com.creaturepacks.game.ui.viewmodel.HomeViewModel
import com.creaturepacks.game.ui.viewmodel.CollectionViewModel
import com.creaturepacks.game.ui.viewmodel.ShopViewModel
import com.creaturepacks.game.ui.viewmodel.PlayerViewModel
import com.creaturepacks.game.ui.viewmodel.SettingsViewModel

class MainActivity : ComponentActivity() {

    private val appModule: AppModule by lazy {
        AppModule(application)
    }

    private val homeViewModel by viewModels<HomeViewModel> {
        appModule.getViewModelFactory()
    }
    private val collectionViewModel by viewModels<CollectionViewModel> {
        appModule.getViewModelFactory()
    }
    private val shopViewModel by viewModels<ShopViewModel> {
        appModule.getViewModelFactory()
    }
    private val playerViewModel by viewModels<PlayerViewModel> {
        appModule.getViewModelFactory()
    }
    private val settingsViewModel by viewModels<SettingsViewModel> {
        appModule.getViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CreaturePacksTheme {
                val navController = rememberNavController()
                NavHost(navController, startDestination = Screen.Home.route) {
                    composable(Screen.Home.route) {
                        HomeScreen(
                            viewModel = homeViewModel,
                            onNavigateToShop = { navController.navigate(Screen.Shop.route) },
                            onNavigateToCollection = { navController.navigate(Screen.Collection.route) },
                            onNavigateToProfile = { navController.navigate(Screen.Profile.route) }
                        )
                    }
                    composable(Screen.Collection.route) {
                        CollectionScreen(
                            viewModel = collectionViewModel,
                            onCardClick = { card ->
                                // TODO: Navigate to card detail screen in future milestone
                            }
                        )
                    }
                    composable(Screen.Shop.route) {
                        BoosterShopScreen(
                            viewModel = shopViewModel,
                            onOpenPack = { series ->
                                navController.navigate("pack_opening/${series.code}")
                            }
                        )
                    }
                    composable(
                        route = "pack_opening/{seriesCode}",
                        arguments = listOf(navArgument("seriesCode") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val seriesCode = backStackEntry.arguments?.getString("seriesCode") ?: "genesis"
                        val series = com.creaturepacks.game.data.sample.BOOSTER_SERIES.firstOrNull { it.code == seriesCode }
                            ?: com.creaturepacks.game.data.sample.BOOSTER_SERIES.first()
                        PackOpeningScreen(
                            series = series,
                            onCardsRevealed = { cards ->
                                shopViewModel.addCardsFromPack(cards)
                            },
                            onClose = {
                                navController.popBackStack()
                            }
                        )
                    }
                    composable(Screen.Profile.route) {
                        ProfileScreen(
                            playerViewModel = playerViewModel,
                            settingsViewModel = settingsViewModel
                        )
                    }
                }

                BottomNavigationBar(navController = navController)
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        Screen.Home,
        Screen.Collection,
        Screen.Shop,
        Screen.Profile
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: ""

    NavigationBar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = CreatureColors.SurfaceCard,
        contentColor = CreatureColors.TextPrimary
    ) {
        items.forEach { screen ->
            val isSelected = currentRoute == screen.route
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = screen.icon,
                        contentDescription = screen.title,
                        tint = if (isSelected) CreatureColors.Gold else CreatureColors.TextMuted
                    )
                },
                label = { Text(text = screen.title, fontSize = 10.sp) },
                selected = isSelected,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                alwaysShowLabel = true
            )
        }
    }
}

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("home", "Home", Icons.Filled.Home)
    object Collection : Screen("collection", "Collection", Icons.Filled.CollectionsBookmark)
    object Shop : Screen("shop", "Shop", Icons.Filled.Shop)
    object Profile : Screen("profile", "Profile", Icons.Filled.Person)
}
