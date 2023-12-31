package com.priyank.messagingappbranch.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    @SerialName("auth_token")
    val authToken : String
)
