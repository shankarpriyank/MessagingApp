package com.priyank.messagingappbranch.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable()
data class Message(
	@SerialName("thread_id")
	val threadId: Int,
	@SerialName("agent_id")
	val agentId: String? = null,
	@SerialName("user_id")
	val userId: String,
	@SerialName("id")
	val id: Int,
	@SerialName("body")
	val body: String,
	@SerialName("timestamp")
	val timestamp: String?
)

