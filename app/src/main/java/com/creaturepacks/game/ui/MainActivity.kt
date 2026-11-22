package com.creaturepacks.game.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.BottomNavigation
import androidx.compose.material3.BottomNavigationItem
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.navigation.NavController
import androidx.compose.navigation.NavHost
import androidx.compose.navigation.composable
import androidx.compose.navigation.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.creaturepacks.game.di.AppModule
import com.creaturepacks.game.ui.screen.BoosterShopScreen
import com.creaturepacks.game.ui.screen.CollectionScreen
import com.creaturepacks.game.ui.screen.HomeScreen
import com.creaturepacks.game.ui.screen.PackOpeningScreen
import com.creaturepacks.game.ui.screen.ProfileScreen
import com.creaturepacks.game.ui.theme.CreatureColors
import com.creaturepacks.game.ui.theme.CreaturePacksTheme

class MainActivity : ComponentActivity() {

    private val appModule: AppModule by lazy {
        AppModule(application)
    }

    private val homeViewModel by viewModels<com.creaturepacks.game.ui.viewmodel.HomeViewModel> {
        appModule.getViewModelFactory()
    }
    private val collectionViewModel by viewModels<com.creaturepacks.game.ui.viewmodel.CollectionViewModel> {
        appModule.getViewModelFactory()
    }
    private val shopViewModel by viewModels<com.creaturepacks.game.ui.viewmodel.ShopViewModel> {
        appModule.getViewModelFactory()
    }
    private val playerViewModel by viewModels<com.creaturepacks.game.ui.viewmodel.PlayerViewModel> {
        appModule.getViewModelFactory()
    }
    private val settingsViewModel by viewModels<com.creaturepacks.game.ui.viewmodel.SettingsViewModel> {
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
                        arguments = listOf(androidx.compose.navigation.navArgument("seriesCode") { type = androidx.compose.navigation.NavType.StringType })
                    ) { backStackEntry ->
                        val seriesCode = backStackEntry.getString() ?: "genesis"
                        val series = com.creaturepacks.game.data.sample.BOOSTER_SERIES.firstOrNull { it.code == seriesCode }
                            ?: com.creaturepacks.game.data.sample.BOOSTER_SERIES.first()
                        PackOpeningScreen(
                            series = series,
                            onCardsRevealed = { cards ->
                                // Cards are added to collection via ShopViewModel
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

    val currentRoute by navController.currentBackStackEntryAsState().map { it?.destination?.route ?: "" }

    NavigationBar(
        modifier = androidx.compose.ui.Modifier.fillMaxWidth(),
        backgroundColor = CreatureColors.SurfaceCard,
        containerColor = CreatureColors.SurfaceCard,
        contentColor = CreatureColors.TextPrimary
    ) {
        items.forEach { screen ->
            val isSelected = currentRoute == screen.route
            NavigationBarItem(
                icon = {
                    androidx.compose.material3.Icon(
                        imageVector = screen.icon,
                        contentDescription = screen.title,
                        tint = if (isSelected) CreatureColors.Gold else CreatureColors.TextMuted
                    )
                },
                label = { androidx.compose.material3.Text(text = screen.title, fontSize = 10.sp) },
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

sealed class Screen(val route: String, val title: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
    object Home : Screen("home", "Home", androidx.compose.material.icons.filled.Home)
    object Collection : Screen("collection", "Collection", androidx.compose.material.icons.filled.CollectionsBookmark)
    object Shop : Screen("shop", "Shop", androidx.compose.material.icons.filled.Shop)
    object Profile : Screen("profile", "Profile", androidx.compose.material.icons.filled.Person)
}
