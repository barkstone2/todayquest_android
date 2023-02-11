package com.todayquest.ui.login

data class LoginUiState(
    val errorMessage: String = "",
    val isUserLoggedIn: Boolean = false,
) {
}