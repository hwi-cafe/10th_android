package com.example.umc_week07.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class FollowingUser(val name: String, val avatarUrl: String)

data class ProfileUiState(
    val isLoading: Boolean = false,
    val userData: UserData? = null,
    val followingList: List<FollowingUser> = listOf(
        FollowingUser("Michael Lawson", "https://reqres.in/img/faces/7-image.jpg"),
        FollowingUser("Lindsay Ferguson", "https://reqres.in/img/faces/8-image.jpg"),
        FollowingUser("Tobias Funke", "https://reqres.in/img/faces/9-image.jpg"),
        FollowingUser("Byron Fields", "https://reqres.in/img/faces/10-image.jpg")
    )
)

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val user = userRepository.getUser(1)
                _uiState.update { it.copy(isLoading = false, userData = user) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }
}