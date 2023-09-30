package com.priyank.messagingappbranch.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.priyank.messagingappbranch.data.remote.MessageApiImpl
import com.priyank.messagingappbranch.data.remote.MessagingApi
import com.priyank.messagingappbranch.data.repository.MessageRepositoryImpl
import com.priyank.messagingappbranch.domain.MessageRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {
    @Singleton
    @Provides
    fun provideHeaders(@ApplicationContext context: Context): String {
        val sharedPreferences = context.getSharedPreferences("accountDetails", MODE_PRIVATE)
        val header = sharedPreferences.getString("headers", "0")
        return header ?: "0"
    }

    @Singleton
    @Provides
    fun provideMessagingApi(): MessagingApi {
        return MessageApiImpl()
    }

    @Singleton
    @Provides
    fun provideMessageRepository(messagingApi: MessagingApi): MessageRepository {

        return MessageRepositoryImpl(messagingApi = messagingApi)
    }

}