package com.priyank.messagingappbranch.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.priyank.messagingappbranch.presentation.LoginScreen
import com.priyank.messagingappbranch.presentation.LoginViewModel

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    startDestination: String,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
     composable(route = "login"){
         val vm = hiltViewModel<LoginViewModel>()
         LoginScreen(
             onUpdateUserName =vm::updateUsername ,
             onUpdatePassword =vm::updatePassword  ,
             onLoginButtonClick = vm::loginClick,
             navHostController = navController ,
             username = vm.username.collectAsState().value,
             password = vm.password.collectAsState().value,
             isButtonEnabled = vm.isButtonEnabled.collectAsState().value,
             navigateToNextString = vm.navigateToNextScreen.collectAsState(initial = false).value,
             showToast = vm.toastEvent.collectAsState().value
         )
     }
    }
}