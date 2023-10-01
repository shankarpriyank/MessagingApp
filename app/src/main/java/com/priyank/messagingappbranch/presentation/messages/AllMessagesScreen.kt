package com.priyank.messagingappbranch.presentation.messages


import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.priyank.messagingappbranch.data.model.Message
import com.priyank.messagingappbranch.presentation.login.navigate
import com.priyank.messagingappbranch.util.Indicator

@Composable
fun AllMessagesScreen(
    list: Map<Int, List<Message>>,
    navController: NavHostController,
    onClick : (Int) -> Unit
) {
    fun navigate(id: Int) {
        onClick(id)
        navController.navigate("post_message/${id}")
    }

    if (list.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
            Indicator()


        }


    } else {


        Column(Modifier.verticalScroll(state = rememberScrollState(), enabled = true)) {
            list.forEach {
                MessageComponent(message = it.value.first(), onCLick = { id ->
                    navigate(id)
                })
            }
        }

    }


}


