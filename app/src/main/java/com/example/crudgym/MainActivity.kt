package com.example.crudgym

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.crudgym.dao.AppDatabase
import com.example.crudgym.dao.ClienteRepository
import com.example.crudgym.ui.theme.CRUDGymTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Crear la base de datos y el repositorio
        val database = AppDatabase.getInstance(this)
        val repository = ClienteRepository(database.clienteDao())

        setContent {
            CRUDGymTheme {
                ClienteScreen(repository = repository)
            }
        }
    }
}

//
//CRUDGymTheme {
//    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//        Greeting(
//            name = "Android",
//            modifier = Modifier.padding(innerPadding)
//        )
//    }
//}