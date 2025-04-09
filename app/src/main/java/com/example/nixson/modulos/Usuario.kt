package com.example.nixson.modulos

data class Usuario(
    val id: Int,
    val nombres: String,
    val apellidos: String,
    val email: String,
    val telefono: Int,
    val direccion: String,
    val rol: Rol
)