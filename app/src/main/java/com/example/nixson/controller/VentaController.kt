package com.example.nixson.controller

import com.example.nixson.modulos.Venta

class VentaController {
    private val ventas = mutableListOf<Venta>()

    fun agregarVenta(venta: Venta) {
        ventas.add(venta)
    }

    fun obtenerVentas(): List<Venta> {
        return ventas
    }
}