package com.example.belajarspek.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.belajarspek.domain.usecase.GetPostUseCase
import com.example.belajarspek.domain.usecase.GetProfileUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val profileUseCase: GetProfileUseCase,
    private val postUseCase: GetPostUseCase
) : ViewModel() {
    val isLoading = MutableLiveData<Boolean>()
    val profileName = MutableLiveData<String>()
    val posts = MutableLiveData<List<String>>()

    fun getProfile() {
        isLoading.value = true

        viewModelScope.launch(Dispatchers.IO) {
            val result = profileUseCase()

            withContext(Dispatchers.Main) {
                isLoading.value = false
                profileName.value = result
            }
        }
    }

    fun getPost() {
        isLoading.value = true

        viewModelScope.launch(Dispatchers.IO) {
            val result = postUseCase()

            withContext(Dispatchers.Main) {
                isLoading.value = false
                posts.value = result
            }
        }
    }
}