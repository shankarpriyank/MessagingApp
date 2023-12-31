package com.priyank.messagingappbranch.presentation.messages

import android.app.Activity
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.getSystemService
import com.priyank.messagingappbranch.R
import com.priyank.messagingappbranch.data.model.Message
import com.priyank.messagingappbranch.util.Indicator
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostMessage(
    List: Map<Int, List<Message>>,
    threadId: Int,
    messageFromAgent: String,
    onPostClick: (threadId: Int, body: String) -> Flow<Message>,
    onUpdateEditText: (String) -> Unit,
    addMessage: (Int, Message) -> Unit
) {

     val subList = List[threadId]


  val context = LocalContext.current

    suspend fun updateList() {
        onPostClick(threadId, messageFromAgent).collect {
            addMessage(threadId, it)
            val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm!!.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)



        }

    }

    if (subList.isNullOrEmpty()){
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
            Indicator()


        }    }else{
        Column(verticalArrangement = Arrangement.SpaceEvenly) {
            subList?.forEach { entry ->
                MessageComponent(message = entry, onCLick = {})


            }
            TextField(
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(text = "Enter Your Message") },
                value = messageFromAgent,
                onValueChange = { onUpdateEditText(it) },
                textStyle = TextStyle(lineHeight = 1.sp, fontSize = 20.sp),
                colors = TextFieldDefaults.outlinedTextFieldColors(containerColor = Color.Transparent),
                trailingIcon = {
                    Image(
                        modifier = Modifier.clickable {
                            GlobalScope.launch {
                                updateList()
                            }
                        },
                        painter = painterResource(id = R.drawable.baseline_send_24),
                        contentDescription = ""
                    )
                }
            )


        }

    }



}


