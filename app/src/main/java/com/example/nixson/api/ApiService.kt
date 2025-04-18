package com.example.nixson.api

import com.example.nixson.modulos.Categoria
import com.example.nixson.modulos.Venta
import com.example.nixson.modulos.Producto
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("Categoria")
    fun getCategorias(): Call<List<Categoria>>

    @POST("Categoria")
    fun agregarCategoria(@Body categoria: Categoria): Call<Categoria>

    @DELETE("Categoria/{id}")
    fun eliminarCategoria(@Path("id") id: Int): Call<Void>

    @GET("Venta")
    fun getVentas(): Call<List<Venta>>

    @POST("Venta")
    fun agregarVenta(@Body venta: Venta): Call<Venta>

    @DELETE("Venta/{id}")
    fun eliminarVenta(@Path("id") id: Int): Call<Void>

    @GET("Producto")
    fun getProductos(): Call<List<Producto>>

    @POST("Producto")
    fun agregarProducto(@Body producto: Producto): Call<Producto>

    @DELETE("Producto/{id}")
    fun eliminarProducto(@Path("id") id: Int): Call<Void>

    @PUT("Producto/{id}")
    fun actualizarProducto(@Path("id") id: Int, @Body producto: Producto): Call<Producto>
}