package com.example.crudgym.dao

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ClienteViewModel(private val repository: ClienteRepository) : ViewModel() {

    val clientes: Flow<List<Cliente>> = repository.clientes

    fun agregarCliente(cliente: Cliente) {
        viewModelScope.launch {
            repository.agregarCliente(cliente)
        }
    }

    fun actualizarCliente(cliente: Cliente) {
        viewModelScope.launch {
            repository.actualizarCliente(cliente)
        }
    }

    fun eliminarCliente(cliente: Cliente) {
        viewModelScope.launch {
            repository.eliminarCliente(cliente)
        }
    }
}

class ClienteViewModelFactory(private val repository: ClienteRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ClienteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ClienteViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
