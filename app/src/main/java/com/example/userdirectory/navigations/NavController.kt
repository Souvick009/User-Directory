package com.example.userdirectory.navigations

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.userdirectory.screens.UserCreateScreen
import com.example.userdirectory.screens.UserDetailsScreen
import com.example.userdirectory.screens.UserListScreen
import com.example.userdirectory.viewModel.UserViewModel

@Composable
fun navigationController() {
    val viewModel : UserViewModel = viewModel()
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.UserListScreen.route,
        enterTransition = {
            slideInHorizontally(initialOffsetX = { it })
        },
        exitTransition = {
            slideOutHorizontally(targetOffsetX = { -it })
        },
        popEnterTransition = {
            slideInHorizontally(initialOffsetX = { -it })
        },
        popExitTransition = {
            slideOutHorizontally(targetOffsetX = { it })
        }

    ) {
        composable(route = Routes.UserListScreen.route) {
            UserListScreen(navController = navController, viewModel = viewModel)
        }

        composable(route = Routes.UserDetailsScreen.route) {
            UserDetailsScreen(navController = navController, viewModel = viewModel)
        }

        composable(route = Routes.UserCreateScreen.route) {
            UserCreateScreen(navController = navController, viewModel = viewModel)
        }
    }
}