package com.priyank.messagingappbranch.data.remote

import com.priyank.messagingappbranch.data.model.LoginCredentials
import com.priyank.messagingappbranch.data.model.LoginResponse
import com.priyank.messagingappbranch.data.model.Message
import com.priyank.messagingappbranch.util.Resource

interface MessagingApi {
    suspend fun login(loginCredentials: LoginCredentials) : LoginResponse

    suspend fun postMessage(thread: Int, body : String, header: String) : Message
    suspend fun getAllMessages(header: String) : List<Message>
}