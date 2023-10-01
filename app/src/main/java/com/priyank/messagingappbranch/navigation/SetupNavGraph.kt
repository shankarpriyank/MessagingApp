package com.priyank.messagingappbranch.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.priyank.messagingappbranch.presentation.login.LoginScreen
import com.priyank.messagingappbranch.presentation.login.LoginViewModel
import com.priyank.messagingappbranch.presentation.messages.AllMessagesScreen
import com.priyank.messagingappbranch.presentation.messages.MessagesViewModel
import com.priyank.messagingappbranch.presentation.messages.PostMessage

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    startDestination: String,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable(route = "login") {
            val vm = hiltViewModel<LoginViewModel>()
            LoginScreen(
                onUpdateUserName = vm::updateUsername,
                onUpdatePassword = vm::updatePassword,
                onLoginButtonClick = vm::loginClick,
                navHostController = navController,
                username = vm.username.collectAsState().value,
                password = vm.password.collectAsState().value,
                isButtonEnabled = vm.isButtonEnabled.collectAsState().value,
                navigateToNextString = vm.navigateToNextScreen.collectAsState(initial = false).value,
                showToast = vm.toastEvent.collectAsState().value
            )
        }
        composable(route = "messages") {
            val vm = hiltViewModel<MessagesViewModel>()
            AllMessagesScreen(
                list = vm.messageList.collectAsState().value,
                navController = navController,
                onClick = vm::updateThreadId
            )

        }
        composable(route = "post_message/{thread_id}") {
            val vm = hiltViewModel<MessagesViewModel>()
            val threadId = it.arguments!!.getString("thread_id")
            PostMessage(
                List =  vm.messageList.collectAsState().value,
                threadId = threadId!!.toInt(),
                messageFromAgent = vm.messageFromAgent.collectAsState().value,
                onPostClick = vm::postMessage,
                onUpdateEditText = vm::updateMessageString,
                addMessage = vm::addMessage

                )

        }
    }
}