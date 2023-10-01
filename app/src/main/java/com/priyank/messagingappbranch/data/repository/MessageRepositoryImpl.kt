package com.priyank.messagingappbranch.data.repository

import android.util.Log
import com.priyank.messagingappbranch.data.model.LoginCredentials
import com.priyank.messagingappbranch.data.model.LoginResponse
import com.priyank.messagingappbranch.data.model.Message
import com.priyank.messagingappbranch.data.remote.MessagingApi
import com.priyank.messagingappbranch.domain.MessageRepository
import com.priyank.messagingappbranch.util.Resource
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class MessageRepositoryImpl(private val messagingApi: MessagingApi) : MessageRepository {
    override suspend fun login(loginCredentials: LoginCredentials): Flow<Resource<LoginResponse>> {
        return flow {
            emit(Resource.Loading())

            try {

                    val header = messagingApi.login(loginCredentials)

                        emit(Resource.Success(data = header))





            } catch (e: RedirectResponseException) {
                // 3xx - responses
                println("Error:3xx ${e.response.status.description}")
                emit(Resource.Error(message = "Something Went Wrong"))

            } catch (e: ClientRequestException) {
                // 4xx - responses
                Log.e("4xx", "Error:4xx ${e.response.status.description}")
                emit(Resource.Error(message = "Check your password/username"))
            } catch (e: ServerResponseException) {
                // 5xx - responses
                println("Error:5xx ${e.response.status.description}")
                emit(Resource.Error(message = "Something Went Wrong"))
            } catch (e: java.net.UnknownHostException) {
                emit(
                    Resource.Error(
                        message = "Please Check Your Internet Connection"
                    )
                )
            } catch (e: Exception) {
                println("Error: ${e.message}")
                emit(Resource.Error(message = "Something Went Wrong"))
            }

        }

    }

    override suspend fun getAllMessages(header: String): Flow<Resource<List<Message>>> {
        return flow {
            emit(Resource.Loading())
            try {
                withContext(Dispatchers.IO) {
                    val messages = messagingApi.getAllMessages(header)
                    emit(Resource.Success(data = messages))

                }
            } catch (e: RedirectResponseException) {
                // 3xx - responses
                println("Error:3xx ${e.response.status.description}")
                emit(Resource.Error(message = "Something Went Wrong"))

            } catch (e: ClientRequestException) {
                // 4xx - responses
                println("Error:4xx ${e.response.status.description}")
                emit(Resource.Error(message = "Client Request Exception"))
            } catch (e: ServerResponseException) {
                // 5xx - responses
                println("Error:5xx ${e.response.status.description}")
                emit(Resource.Error(message = "Something Went Wrong"))
            } catch (e: java.net.UnknownHostException) {
                emit(
                    Resource.Error(
                        message = "Please Check Your Internet Connection"
                    )
                )
            } catch (e: Exception) {
                println("Error: ${e.message}")
                emit(Resource.Error(message = "Something Went Wrong"))
            }
        }
    }

    override suspend fun postMessage(
        thread: Int,
        body: String,
        header: String
    ): Flow<Resource<Message>> {
        return flow {
            emit(Resource.Loading())
            try {
                withContext(Dispatchers.IO) {
                    val message = messagingApi.postMessage(thread, body, header)
                    emit(Resource.Success(data = message))

                }
            } catch (e: RedirectResponseException) {
                // 3xx - responses
                println("Error:3xx ${e.response.status.description}")
                emit(Resource.Error(message = "Something Went Wrong"))

            } catch (e: ClientRequestException) {
                // 4xx - responses
                println("Error:4xx ${e.response.status.description}")
                emit(Resource.Error(message = "Client Request Exception"))
            } catch (e: ServerResponseException) {
                // 5xx - responses
                println("Error:5xx ${e.response.status.description}")
                emit(Resource.Error(message = "Something Went Wrong"))
            } catch (e: java.net.UnknownHostException) {
                emit(
                    Resource.Error(
                        message = "Please Check Your Internet Connection"
                    )
                )
            } catch (e: Exception) {
                println("Error: ${e.message}")
                emit(Resource.Error(message = "Something Went Wrong"))
            }
        }
    }
}