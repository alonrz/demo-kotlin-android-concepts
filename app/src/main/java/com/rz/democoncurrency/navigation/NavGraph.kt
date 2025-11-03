package com.rz.democoncurrency.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.rz.democoncurrency.screens.ConcurrencyScreenRoot
import com.rz.democoncurrency.screens.MenuScreen
import com.rz.democoncurrency.screens.ParallelTimersScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Menu.route,
        modifier = modifier
    ) {
        composable(route = Screen.Menu.route) {
            MenuScreen(
                onNavigateToScreen = { route ->
                    navController.navigate(route)
                }
            )
        }

        composable(route = Screen.ParallelTimers.route) {
            ParallelTimersScreen()
        }

        composable(route = Screen.Concurrency.route) {
            ConcurrencyScreenRoot()
        }

        // Add more composable destinations here as you create more screens
        // composable(route = Screen.AnotherDemo.route) {
        //     AnotherDemoScreen()
        // }
    }
}
