package com.example.crudgym.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ClienteDao {
    @Query("SELECT * FROM clientes")
    fun obtenerClientes(): Flow<List<Cliente>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarCliente(cliente: Cliente)

    @Update
    suspend fun actualizarCliente(cliente: Cliente)

    @Delete
    suspend fun eliminarCliente(cliente: Cliente)
}
