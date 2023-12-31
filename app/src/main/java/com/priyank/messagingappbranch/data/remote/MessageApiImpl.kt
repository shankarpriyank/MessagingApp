package com.priyank.messagingappbranch.data.remote

import android.util.Log
import com.priyank.messagingappbranch.data.ApiRoutes
import com.priyank.messagingappbranch.data.model.LoginCredentials
import com.priyank.messagingappbranch.data.model.LoginResponse
import com.priyank.messagingappbranch.data.model.Message
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json

class MessageApiImpl : MessagingApi {

    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json()
        }
    }

    override suspend fun login(loginCredentials: LoginCredentials): LoginResponse {
        return httpClient.post(ApiRoutes.loginUrl) {

            contentType(ContentType.Application.Json)
            setBody(
                loginCredentials
            )
        }.body<LoginResponse>()
    }

    override suspend fun postMessage(thread: Int, body: String, header: String): Message {

        return httpClient.post(ApiRoutes.messagingUrl) {
            headers {
                append("X-Branch-Auth-Token", header)
            }
            parameter("thread_id", thread)
            parameter("body", body)

            contentType(ContentType.Application.Json)


        }.body<Message>()

    }

    override suspend fun getAllMessages(header: String): List<Message> {
        return try {

            httpClient.get(ApiRoutes.messagingUrl) {
                headers {
                    append("X-Branch-Auth-Token", header)
                }

            }.body<List<Message>>()
        } catch (e: RedirectResponseException) {
            // 3xx - responses
            println("Error: ${e.response.status.description}")
            emptyList()
        } catch (e: ClientRequestException) {
            // 4xx - responses
            println("Error: ${e.response.status.description}")
            emptyList()
        } catch (e: ServerResponseException) {
            // 5xx - responses
            println("Error: ${e.response.status.description}")
            emptyList()
        } catch (e: Exception) {
            println("Error: ${e.message}")
            emptyList()
        }
    }
}