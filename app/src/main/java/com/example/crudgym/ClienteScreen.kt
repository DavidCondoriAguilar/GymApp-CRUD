package com.example.crudgym

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.crudgym.dao.Cliente
import com.example.crudgym.dao.ClienteRepository
import com.example.crudgym.dao.ClienteViewModel
import com.example.crudgym.dao.ClienteViewModelFactory
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClienteScreen(repository: ClienteRepository) {
    val viewModel: ClienteViewModel = viewModel(factory = ClienteViewModelFactory(repository))
    val clientes by viewModel.clientes.collectAsState(initial = emptyList())
    val scope = rememberCoroutineScope()

    var nombre by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }
    var membresia by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var isEdit by remember { mutableStateOf(false) }
    var selectedCliente: Cliente? by remember { mutableStateOf(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Gestión de Clientes", color = Color.White) },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xFF6200EE))
            )
        },
        content = { padding ->
            Column(modifier = Modifier.padding(padding).padding(16.dp)) {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(clientes.size) { index ->
                        val cliente = clientes[index]
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            elevation = CardDefaults.cardElevation(4.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        "Nombre: ${cliente.nombre}",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp
                                    )
                                    Text("Edad: ${cliente.edad}")
                                    Text("Membresía: ${cliente.membresia}")
                                    Text("Teléfono: ${cliente.telefono}")
                                    Text("Fecha Registro: ${cliente.fechaRegistro}")
                                }
                                Row {
                                    IconButton(onClick = {
                                        isEdit = true
                                        selectedCliente = cliente
                                        nombre = cliente.nombre
                                        edad = cliente.edad.toString()
                                        membresia = cliente.membresia
                                        telefono = cliente.telefono
                                    }) {
                                        Icon(
                                            Icons.Default.Edit,
                                            contentDescription = "Editar",
                                            tint = Color(0xFF03A9F4)
                                        )
                                    }
                                    IconButton(onClick = {
                                        scope.launch { viewModel.eliminarCliente(cliente) }
                                    }) {
                                        Icon(
                                            Icons.Default.Delete,
                                            contentDescription = "Eliminar",
                                            tint = Color(0xFFFF5722)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Formulario
                Text(
                    text = if (isEdit) "Editar Cliente" else "Agregar Nuevo Cliente",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color(0xFF6200EE)
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre") },
                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = "Nombre") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = edad,
                    onValueChange = { edad = it },
                    label = { Text("Edad") },
                    leadingIcon = { Icon(Icons.Default.Add , contentDescription = "Edad") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = membresia,
                    onValueChange = { membresia = it },
                    label = { Text("Membresía") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = telefono,
                    onValueChange = { telefono = it },
                    label = { Text("Teléfono") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        val cliente = Cliente(
                            id = selectedCliente?.id ?: 0,
                            nombre = nombre,
                            edad = edad.toIntOrNull() ?: 0,
                            membresia = membresia,
                            telefono = telefono,
                            fechaRegistro = selectedCliente?.fechaRegistro ?: "2024-11-26"
                        )
                        scope.launch {
                            if (isEdit) {
                                viewModel.actualizarCliente(cliente)
                            } else {
                                viewModel.agregarCliente(cliente)
                            }
                        }
                        nombre = ""
                        edad = ""
                        membresia = ""
                        telefono = ""
                        isEdit = false
                        selectedCliente = null
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
                ) {
                    Text(
                        text = if (isEdit) "Actualizar Cliente" else "Agregar Cliente",
                        color = Color.White
                    )
                }
            }
        }
    )
}
