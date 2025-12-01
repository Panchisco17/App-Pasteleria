package com.example.app_pasteleria.ui.login

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_pasteleria.data.database.CatalogoDatabase
import com.example.app_pasteleria.data.repository.AuthRepository
import kotlinx.coroutines.launch

class LoginViewModel(application: Application): AndroidViewModel(application){

    private val repo: AuthRepository

    init {
        val db = CatalogoDatabase.getDatabase(application)
        repo = AuthRepository(db.UsuarioDao())
    }

    var uiState by mutableStateOf(LoginUiState())
        private set

    fun onUsernameChange(value:String){
        uiState = uiState.copy(username =value, error= null )
    }

    fun onPasswordChange(value:String){
        uiState = uiState.copy(password =value, error= null )
    }

    fun submit(onSucces:(String)->Unit){
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, error = null)

            val oK = repo.login(uiState.username.trim(), uiState.password)

            uiState = uiState.copy(isLoading = false, error= null)

            if (oK) onSucces(uiState.username.trim())
            else uiState = uiState.copy(error = "Credenciales Invalidas")
        }
    }
}