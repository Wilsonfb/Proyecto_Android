package com.example.nixson.controller

import com.example.nixson.modulos.Categoria

class CategoriaController {
    private val categorias = mutableListOf<Categoria>()
    private var nextId = 1

    fun agregarCategoria(nombre: String) {
        val categoria = Categoria(id = nextId++, nombre = nombre)
        categorias.add(categoria)
    }

    fun obtenerCategorias(): List<Categoria> {
        return categorias
    }
}