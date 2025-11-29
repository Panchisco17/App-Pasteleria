package com.example.app_pasteleria.data.model

data class Post(val userId: Int, // ID del usuario que creó el post
                val id: Int, // ID del post
                val title: String, // Título del post
                val body: String // Cuerpo o contenido del post
)

// aca agregar los atributos de nuestro archivo json.