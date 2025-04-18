package com.example.nixson.modulos

data class Venta(
    private var id: Int,
    private var producto: String ,
    private var cantidad: Int
) {

    fun getId(): Int {
        return id
    }
    fun getProducto(): String {
        return producto
    }
    fun getCantidad(): Int {
        return cantidad
    }
}