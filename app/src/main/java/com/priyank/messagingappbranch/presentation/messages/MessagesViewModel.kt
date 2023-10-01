package com.priyank.messagingappbranch.presentation.messages

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import ch.qos.logback.core.joran.event.BodyEvent
import com.priyank.messagingappbranch.data.local.Header
import com.priyank.messagingappbranch.data.model.Message

import com.priyank.messagingappbranch.domain.MessageRepository
import com.priyank.messagingappbranch.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.time.Instant
import javax.inject.Inject

@HiltViewModel
class MessagesViewModel @Inject constructor(
    private val repository: MessageRepository,
    private val header: Header,
) : ViewModel() {

    private val _messagesList = MutableStateFlow<Map<Int, List<Message>>>(emptyMap())
    val messageList = _messagesList.asStateFlow()

    private val _loading = MutableStateFlow(true)
    val isloading = _loading.asStateFlow()

    private val _messageFromAgent = MutableStateFlow("")
    val messageFromAgent = _messageFromAgent.asStateFlow()
    private val _threadIdOfScreen = MutableStateFlow<Int>(999999999)
    val threadIdOfScreen = _threadIdOfScreen.asStateFlow()

    val subListOfMessage = combine(threadIdOfScreen, messageList) { threadId, messageList ->
        messageList[threadId]?.toMutableList() ?: emptyList()
    }



    init {

        viewModelScope.launch(Dispatchers.IO) {
            delay(1000)
            getAllMessages(header.getHeader())
        }
    }

    fun getAllMessages(header: String) {
        viewModelScope.launch {
            repository.getAllMessages(header = header).collect { response ->
                when (response) {
                    is Resource.Loading -> {

                    }

                    is Resource.Error -> {

                    }

                    is Resource.Success -> {

                        val list = groupMessagesByThreadId(response.data!!)
                        _messagesList.emit(
                            list
                        )

                    }
                }

            }
        }

    }

    fun updateMessageString(message: String) {
        viewModelScope.launch {
            _messageFromAgent.emit(message)

        }
    }
    fun updateThreadId(message: Int) {
        viewModelScope.launch {
            _threadIdOfScreen.emit(message)

        }
    }


    private fun groupMessagesByThreadId(messages: List<Message>): Map<Int, List<Message>> {
        val messageGroups = mutableMapOf<Int, MutableList<Message>>()

        // Iterate through the messages and group them by thread_id
        for (message in messages) {
            val threadId = message.threadId
            if (messageGroups.containsKey(threadId)) {
                // Add the message to an existing group
                messageGroups[threadId]?.add(message)
            } else {
                // Create a new group for this thread_id
                messageGroups[threadId] = mutableListOf(message)
            }
        }

        // Sort the messages within each group by timestamp
        messageGroups.values.forEach { messagesInThread ->
            messagesInThread.sortBy { message -> Instant.parse(message.timestamp) }
        }

        // Convert the map of groups to a single map
        val result = mutableMapOf<Int, List<Message>>()
        messageGroups.forEach { (threadId, messages) ->
            result[threadId] = messages
        }

        return result
    }

    fun postMessage(threadId: Int, body: String): Flow<Message> {
        return flow {

            repository.postMessage(threadId, body, header.getHeader()).collect {
                when (it) {
                    is Resource.Error -> {}
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        emit(it.data!!)

                    }
                }


            }


        }


    }


}