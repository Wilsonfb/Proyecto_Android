package com.example.nixson.controller

import com.example.nixson.modulos.Producto

class ProductoController {
    private val productos = mutableListOf<Producto>()
    private var nextId = 1

    fun agregarProducto(
        nombre: String,
        cantidad: Int,
        fechaEntrada: String,
        fechaVencimiento: String,
        proveedor: String,
        categoria: String
    ) {
        val producto = Producto(
            id = nextId++,
            nombre = nombre,
            cantidad = cantidad,
            fechaEntrada = fechaEntrada,
            fechaVencimiento = fechaVencimiento,
            proveedor = proveedor,
            categoria = categoria
        )
        productos.add(producto)
    }

    fun obtenerProductos(): List<Producto> {
        return productos
    }

    fun buscarProductoPorId(id: Int): Producto? {
        return productos.find { it.getId() == id }
    }

    fun eliminarProducto(id: Int): Boolean {
        val producto = buscarProductoPorId(id)
        return if (producto != null) {
            productos.remove(producto)
            true
        } else {
            false
        }
    }

    fun actualizarProducto(
        id: Int,
        nombre: String,
        cantidad: Int,
        fechaEntrada: String,
        fechaVencimiento: String,
        proveedor: String,
        categoria: String
    ): Boolean {
        val producto = buscarProductoPorId(id)
        return if (producto != null) {
            val updatedProducto = Producto(id, nombre, cantidad, fechaEntrada, fechaVencimiento, proveedor, categoria)
            val index = productos.indexOf(producto)
            productos[index] = updatedProducto
            true
        } else {
            false
        }
    }
}