package com.todayquest.ui.login

import androidx.activity.result.ActivityResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.todayquest.data.ProviderType
import com.todayquest.data.TokenRequest
import com.todayquest.data.login.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
): ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun logout() {
        viewModelScope.launch {
            loginRepository.logout()
                .handleResult(
                    onSuccess = {
                        _uiState.update {
                            it.copy(isUserLoggedIn = false)
                        }
                    },
                    onError = {
                        displayError(it)
                    }
            )
        }
    }
    fun loginCheck(onLoaded: () -> Unit) {
        viewModelScope.launch {
            loginRepository.silentSignIn()
                .handleResult(
                    onSuccess = { loginSucceed() },
                    onError = {}
                )
            onLoaded()
        }
    }

    private fun loginSucceed() {
        _uiState.update {
            it.copy(isUserLoggedIn = true)
        }
    }

    private fun displayError(errorMessage: String) {
        println("login Failed: $errorMessage")
        _uiState.update {
            it.copy(errorMessage = errorMessage)
        }

        viewModelScope.launch(Dispatchers.Default) {
            delay(2000)
            messageDismiss()
        }
    }

    private fun messageDismiss() {
        _uiState.update {
            it.copy(errorMessage = "")
        }
    }

    fun login(result: ActivityResult, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                val account = task.getResult(ApiException::class.java)
                val idToken = account.idToken!!

                val tokenRequest = TokenRequest(idToken, ProviderType.GOOGLE)

                loginRepository.requestLogin(tokenRequest)
                    .handleResult(
                        onSuccess = {
                            loginSucceed()
                            onSuccess()
                        },
                        onError = {
                            displayError(it)
                        }
                    )
            } catch (e: ApiException) {
                displayError("로그인에 실패했습니다.")
            }
        }
    }
}