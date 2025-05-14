package com.example.nilpay_registration_android.presentation.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nilpay_registration_android.core.data.BaseResult
import com.example.nilpay_registration_android.core.data.TokenManager
import com.example.nilpay_registration_android.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val tokenManager: TokenManager,
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onUsernameChange(username: String) {
        _uiState.update { it.copy(username = username) }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { it.copy(password = password) }
    }

    fun login() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            loginUseCase.execute(_uiState.value.username, _uiState.value.password).onStart { }
                .collect { result ->
                    when (result) {
                        is BaseResult.DataState -> {
                            if (result.items != null) {
                                result.items.token.let { token ->
                                    tokenManager.saveToken(token = token)
                                    tokenManager.saveUserId(userId = result.items.userId)
                                    tokenManager.setLogIn()
                                }
                                _uiState.update {
                                    it.copy(
                                        isLoading = false,
                                        isLoggedIn = true,
                                        error = null
                                    )
                                }
                            }
                        }

                        is BaseResult.ErrorState -> {
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    isLoggedIn = false,
                                    error = result.errorResponse.message
                                )
                            }
                        }
                    }
                }
        }
    }
}
