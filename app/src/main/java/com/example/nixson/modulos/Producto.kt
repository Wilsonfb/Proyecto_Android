package com.example.nixson.modulos

data class Producto(
    private var id: Int,
    private var nombre: String,
    private var cantidad: Int,
    private var fechaEntrada: String,
    private var fechaVencimiento: String,
    private var proveedor: String,
    private var categoria: String
) {

    fun getId(): Int {
        return id
    }

    fun getNombre(): String {
        return nombre
    }

    fun getCantidad(): Int {
        return cantidad
    }

    fun getFechaEntrada(): String {
        return fechaEntrada
    }

    fun getFechaVencimiento(): String {
        return fechaVencimiento
    }

    fun getProveedor(): String {
        return proveedor
    }

    fun getCategoria(): String {
        return categoria
    }
}