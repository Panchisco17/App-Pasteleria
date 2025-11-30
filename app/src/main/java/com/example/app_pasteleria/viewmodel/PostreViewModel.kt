package com.example.app_pasteleria.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_pasteleria.data.model.Postre
import com.example.app_pasteleria.data.repository.PostreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
// ViewModel que mantiene el estado de los datos obtenidos
class PostreViewModel : ViewModel() {
    private val repository = PostreRepository()
    // Flujo mutable que contiene la lista de posts
    private val _postList = MutableStateFlow<List<Postre>>(emptyList())
    // Flujo público de solo lectura
    val postList: StateFlow<List<Postre>> = _postList
    // Se llama automáticamente al iniciar
    init {
        fetchPosts()
    }
    // Función que obtiene los datos en segundo plano
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