package com.example.app_pasteleria.data.repository

import com.example.app_pasteleria.data.model.Credential

private val usuarios = Credential.usuarios.toMutableList()
class AuthRepository {
    fun login (username:String,password:String): Boolean {
        return usuarios.any { it.username == username && it.password == password }
    }

    fun register(username: String, password: String): Boolean {
        if (usuarios.any { it.username == username }) {
            return false // si el Usuario ya existe
        }
        val newUser = Credential(username, password)
        usuarios.add(newUser)
        return true
    }

}//fin del class
