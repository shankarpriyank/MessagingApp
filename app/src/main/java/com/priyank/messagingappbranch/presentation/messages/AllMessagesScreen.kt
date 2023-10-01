package com.priyank.messagingappbranch.presentation.messages


import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.priyank.messagingappbranch.data.model.Message
import com.priyank.messagingappbranch.presentation.login.navigate

@Composable
fun AllMessagesScreen(
    list: Map<Int, List<Message>>,
    navController: NavHostController,
    onClick : (Int) -> Unit
) {
    fun navigate(id: Int) {
        Log.e("Click", id.toString())
        onClick(id)
        navController.navigate("post_message/${id}")
    }

    if (list.isEmpty()) {

        Text(text = "Loading")

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


