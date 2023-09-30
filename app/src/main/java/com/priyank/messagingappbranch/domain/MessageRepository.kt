package com.priyank.messagingappbranch.domain

import com.priyank.messagingappbranch.data.model.LoginCredentials
import com.priyank.messagingappbranch.data.model.LoginResponse
import com.priyank.messagingappbranch.data.model.Message
import com.priyank.messagingappbranch.util.Resource
import kotlinx.coroutines.flow.Flow

interface MessageRepository {

    suspend fun login(loginCredentials: LoginCredentials): Flow<Resource<LoginResponse>>
    suspend fun getAllMessages(header: String): Flow<Resource<List<Message>>>
    suspend fun postMessage(thread: Int, body: String, header: String):Flow<Resource<Message>>

}