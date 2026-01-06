package com.example.todo.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import com.example.todo.screens.auth.*
import com.example.todo.screens.home.HomeScreen
import com.example.todo.screens.onboarding.OnBoardingScreen
import com.example.todo.screens.splash.SplashScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "splash") {

        composable("splash") {
            SplashScreen(navController)
        }

        composable("onboarding") {
            OnBoardingScreen(navController)
        }

        composable("login") {
            LoginScreen(navController)
        }

        composable("forgot_password") {
            ForgotPasswordScreen(navController)
        }

        composable("forgot_verify") {
            VerifyCodeScreen(navController)
        }

        composable("reset_password") {
            ResetPasswordScreen(navController)
        }

        composable("confirm") {
            ConfirmScreen(navController)
        }

        composable("home") {
            HomeScreen()
        }
    }
}
