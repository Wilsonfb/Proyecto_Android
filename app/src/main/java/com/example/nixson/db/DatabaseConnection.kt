package com.example.nixson.db

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

object DatabaseConnection {
    private const val host = "localhost"
    private const val port = "3306"
    private const val database = "NX"
    private const val user = "root"
    private const val password = "nixson"

    private const val url = "jdbc:mysql://$host:$port/$database?useSSL=false&serverTimezone=UTC"

    fun getConnection(): Connection? {
        return try {
            DriverManager.getConnection(url, user, password)
        } catch (e: SQLException) {
            println("Error al conectar a la base de datos: ${e.message}")
            null
        }
    }
}