package com.example.app_pasteleria.data.repository

import com.example.app_pasteleria.data.dao.UsuarioDao
import com.example.app_pasteleria.data.model.Credential
import com.example.app_pasteleria.data.model.Usuario

class AuthRepository(private val usuarioDao: UsuarioDao? = null) {

    suspend fun login(username: String, password: String): Boolean {
        val esUsuarioEstatico = Credential.usuarios.any { it.username == username && it.password == password }
        if (esUsuarioEstatico) return true

        if (usuarioDao != null) {
            val usuarioEncontrado = usuarioDao.login(username, password)
            return usuarioEncontrado != null
        }

        return false
    }

    suspend fun register(usuario: Usuario): Boolean {
        if (usuarioDao == null) return false

        val existe = usuarioDao.buscarPorEmail(usuario.email)
        if (existe != null) {
            return false
        }

        usuarioDao.insertarUsuario(usuario)
        return true
    }
}