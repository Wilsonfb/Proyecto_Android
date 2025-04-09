package com.example.nixson.controller
import com.example.nixson.modulos.Usuario

class UsuarioController {
    private val usuarios = mutableListOf<Usuario>()

    fun agregarUsuario(usuario: Usuario) {
        usuarios.add(usuario)
    }

    fun obtenerUsuarioPorId(id: Int): Usuario? {
        return usuarios.find { it.id == id }
    }

    fun obtenerTodosLosUsuarios(): List<Usuario> {
        return usuarios
    }

    fun eliminarUsuario(id: Int): Boolean {
        val usuario = obtenerUsuarioPorId(id)
        return if (usuario != null) {
            usuarios.remove(usuario)
            true
        } else {
            false
        }
    }
}