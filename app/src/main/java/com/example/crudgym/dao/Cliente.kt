package com.example.crudgym.dao

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "clientes")
data class Cliente(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val edad: Int,
    val membresia: String,
    val telefono: String,
    val fechaRegistro: String
)
