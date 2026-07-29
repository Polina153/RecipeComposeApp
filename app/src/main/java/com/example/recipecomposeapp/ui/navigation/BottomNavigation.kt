package com.example.recipecomposeapp.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.recipecomposeapp.Destination
import com.example.recipecomposeapp.util.FavoriteDataStoreManager

@Composable
fun BottomNavigation(navController: NavController) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val context = LocalContext.current
    val dataStoreManager = remember(context) { FavoriteDataStoreManager(context) }
    val favoriteCountFlow = remember { dataStoreManager.getFavoriteCountFlow() }
    val count by favoriteCountFlow.collectAsState(initial = 0)

    val items = listOf(
        Destination.Categories,
        Destination.Favorites
    )

    NavigationBar {
        items.forEach { destination ->
            val navIcon: @Composable () -> Unit = when (destination) {
                Destination.Categories -> {
                    { Icon(Icons.Default.Home, contentDescription = destination.route) }
                }

                Destination.Favorites -> {
                    {
                        BadgedBox(badge = {
                            if (count > 0) {
                                Badge { Text(count.toString()) }
                            }
                        }) {
                            Icon(Icons.Default.Favorite, contentDescription = destination.route)
                        }
                    }
                }

                else -> {
                    { Icon(Icons.Default.Home, contentDescription = destination.route) }
                }
            }
            NavigationBarItem(
                icon = navIcon,
                label = { Text(destination.route) },
                selected = navBackStackEntry?.destination?.route == destination.route,
                onClick = {
                    navController.navigate(destination.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                    }
                }
               /* onClick = {
                    navController.navigate(destination.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }*/
            )
        }
    }


}
