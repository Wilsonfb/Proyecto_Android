package com.example.nixson.modulos

data class Producto(
    val id: Int,
    val nombre: String,
    val cantidad: Int,
    val fechaEntrada: String,
    val fechaVencimiento: String,
    val proveedor: String,
    val categoria: String
)
