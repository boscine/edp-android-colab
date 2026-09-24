package com.example.mandahinog.ui

import com.example.mandahinog.domain.model.User

sealed interface AuthUiState {
    data object Idle : AuthUiState
    data object Loading : AuthUiState
    data class Error(val message: String) : AuthUiState
    data class AccountCreated(val name: String) : AuthUiState
    data class LoggedIn(val user: User) : AuthUiState
}
