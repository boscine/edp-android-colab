package com.example.mandahinog

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProfileViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    fun onNameChange(value: String) = _uiState.update { it.copy(name = value) }
    fun onEmailChange(value: String) = _uiState.update { it.copy(email = value) }
    fun onContactChange(value: String) = _uiState.update { it.copy(contactNumber = value) }
    fun onAddressChange(value: String) = _uiState.update { it.copy(address = value) }
    fun onUsernameChange(value: String) = _uiState.update { it.copy(username = value) }
    fun onNewSkillChange(value: String) = _uiState.update { it.copy(newSkill = value) }

    fun addSkill() {
        val skill = _uiState.value.newSkill.trim()
        if (skill.isEmpty()) return
        // Bonus Challenge: Prevent adding a skill that is already in the list
        if (_uiState.value.skills.any { it.equals(skill, ignoreCase = true) }) return
        _uiState.update { current ->
            current.copy(
                skills = current.skills + skill,
                newSkill = ""
            )
        }
    }

    fun removeSkill(skill: String) {
        _uiState.update { current ->
            current.copy(skills = current.skills - skill)
        }
    }

    // Bonus Challenge: Add a Clear all button that resets the form to an empty ProfileUiState()
    fun clearAll() {
        _uiState.value = ProfileUiState()
    }

    fun showPreview() = _uiState.update { it.copy(isPreview = true) }
    fun backToEdit() = _uiState.update { it.copy(isPreview = false) }
}
