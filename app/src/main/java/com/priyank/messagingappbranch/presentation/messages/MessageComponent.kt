package com.priyank.messagingappbranch.presentation.messages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.priyank.messagingappbranch.data.model.Message

    @Composable
    fun MessageComponent(message: Message, onCLick : (Int) -> Unit) {

        Column(modifier = Modifier.padding(8.dp).clickable {
            onCLick(message.threadId)
        }) {

            Text(modifier = Modifier.padding(4.dp), text = "Sender Id - ${message.agentId ?: message.userId}")
            Text(modifier = Modifier.padding(4.dp), text = "TimeStamp - ${message.timestamp}")

            Text(modifier = Modifier.padding(4.dp),textAlign = TextAlign.Start, text = "Body - ${message.body}")




        }


    }

@Preview(showBackground = true)
@Composable
fun prev() {
    MessageComponent(
        message = Message(
            threadId = 2,
            agentId = null,
            userId = "g",
            id = 2,
            body = "ggg",
            timestamp = "gg",
        ),
        onCLick = {}
    )
}