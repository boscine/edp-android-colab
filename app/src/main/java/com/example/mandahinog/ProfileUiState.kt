package com.example.mandahinog

data class ProfileUiState(
    val name: String = "",
    val email: String = "",
    val contactNumber: String = "",
    val address: String = "",
    val username: String = "boscine_danven",
    val skills: List<String> = emptyList(),
    val newSkill: String = "",
    val isPreview: Boolean = false
)
