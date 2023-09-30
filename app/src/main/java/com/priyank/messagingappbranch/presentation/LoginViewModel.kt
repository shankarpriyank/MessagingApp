package com.priyank.messagingappbranch.presentation

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.priyank.messagingappbranch.data.model.LoginCredentials
import com.priyank.messagingappbranch.domain.MessageRepository
import com.priyank.messagingappbranch.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val messageRepository: MessageRepository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _isButtonEnabled = MutableStateFlow(true)
    val isButtonEnabled = _isButtonEnabled.asStateFlow()

    private val _toastEvent = MutableSharedFlow<UIEvent>()
    val toastEvent = _toastEvent.asSharedFlow()

    private val _navigateToNextScreen = MutableSharedFlow<Boolean>()
    val navigateToNextScreen = _navigateToNextScreen.asSharedFlow()


    private val _username = MutableStateFlow("")
    val username = _username.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    fun updateUsername(username: String) {
        viewModelScope.launch {
            _username.emit(username)

        }
    }

    fun updatePassword(password: String) {
        viewModelScope.launch {
            _password.emit(password)
        }
    }

    suspend fun loginClick() {
        messageRepository.login(
            LoginCredentials(
                username = _username.value,
                password = _password.value
            )
        ).collect { repsonse ->
            when (repsonse) {
                is Resource.Loading -> {
                    _isButtonEnabled.emit(false)
                }

                is Resource.Error -> {
                    _isButtonEnabled.emit(false)
                    _toastEvent.emit(
                        UIEvent.ShowToast(
                            message = repsonse.message ?: "Something Went Wrong"
                        )
                    )

                }

                is Resource.Success -> {
                    _isButtonEnabled.emit(false)
                    sharedPreferences.edit().putString("headers", repsonse.data!!.authToken).apply()
                    _navigateToNextScreen.emit(true)


                }
            }


        }
    }

    sealed class UIEvent {
        data class ShowToast(val message: String) : UIEvent()
    }

}