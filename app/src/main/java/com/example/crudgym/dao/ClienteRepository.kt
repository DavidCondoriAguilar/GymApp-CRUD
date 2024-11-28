package com.example.crudgym.dao

import kotlinx.coroutines.flow.Flow

class ClienteRepository(private val clienteDao: ClienteDao) {

    val clientes: Flow<List<Cliente>> = clienteDao.obtenerClientes()

    suspend fun agregarCliente(cliente: Cliente) {
        clienteDao.insertarCliente(cliente)
    }

    suspend fun actualizarCliente(cliente: Cliente) {
        clienteDao.actualizarCliente(cliente)
    }

    suspend fun eliminarCliente(cliente: Cliente) {
        clienteDao.eliminarCliente(cliente)
    }
}
