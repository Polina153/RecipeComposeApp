package com.example.recipecomposeapp.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.recipecomposeapp.Destination

@Composable
fun BottomNavigation(navController: NavController) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val items = listOf(
        Destination.Categories,
        Destination.Favorites
    )
    NavigationBar {
        items.forEach { destination ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = when (destination) {
                            Destination.Categories -> Icons.Default.Home
                            Destination.Favorites -> Icons.Default.Favorite
                            else -> Icons.Default.Home
                        },
                        contentDescription = destination.route
                    )
                },
                label = { Text(destination.route) },
                selected = navBackStackEntry?.destination?.route == destination.route,
                onClick = {
                    navController.navigate(destination.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
