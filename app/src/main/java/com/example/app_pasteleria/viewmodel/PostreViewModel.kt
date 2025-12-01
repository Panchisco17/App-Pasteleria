package com.example.app_pasteleria.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_pasteleria.data.model.Postre
import com.example.app_pasteleria.data.repository.PostreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
class PostreViewModel : ViewModel() {
    private val repository = PostreRepository()
    private val _postList = MutableStateFlow<List<Postre>>(emptyList())
    val postList: StateFlow<List<Postre>> = _postList
    init {
        fetchPosts()
    }
    private fun fetchPosts() {
        viewModelScope.launch {
            try {
                _postList.value = repository.getPosts()
            } catch (e: Exception) {
                println("Error al obtener datos: ${e.localizedMessage}")
            }
        }
    }
}