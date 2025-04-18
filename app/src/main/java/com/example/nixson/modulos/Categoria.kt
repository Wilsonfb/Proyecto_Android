package com.example.nixson.modulos

data class Categoria(
    private var id: Int = 0,
    private var nombre: String
) {

    fun getId(): Int {
        return id
    }
    fun getNombre(): String {
        return nombre
    }
}