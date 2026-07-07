package com.example.lifemap.presentation.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lifemap.presentation.ForgotPassword.view.ForgotPasswordScreen
import com.example.lifemap.presentation.Home.view.HomeScreen
import com.example.lifemap.presentation.Login.view.LoginScreen
import com.example.lifemap.presentation.SignUp.view.SignUpScreen
import com.example.lifemap.presentation.welcome.WelcomeScreen

@Composable
fun Navigation(){
    val rememberNavController = rememberNavController()
    var startDestination by remember { mutableStateOf(Routes.WELCOME) }

    NavHost(navController = rememberNavController, startDestination = startDestination) {
        composable(Routes.WELCOME) {
            WelcomeScreen(rememberNavController)
        }
        composable(Routes.LOGIN) {
            LoginScreen(rememberNavController)
        }
        composable(Routes.SIGNUP) {
            SignUpScreen(rememberNavController)
        }
        composable(Routes.FORGOT_PASSWORD) {
            ForgotPasswordScreen(rememberNavController)
        }
        composable(Routes.HOME) {
            HomeScreen(rememberNavController)
        }
    }
}

