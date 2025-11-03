package com.lopez_jorge.corrutinas


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                PantallaLogin()
            }
        }
    }
}

@Composable
fun PantallaConCorrutina() {
    val scope = rememberCoroutineScope()
    var texto by remember { mutableStateOf("Presiona el botón") }
    var cargando by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = texto)
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                scope.launch {
                    cargando = true
                    texto = "Cargando..."
                    delay(2000) // Simula una tarea larga
                    texto = "¡Tarea completada!"
                    cargando = false
                }
            },
            enabled = !cargando
        ) {
            Text(if (cargando) "Ejecutando..." else "Iniciar Corrutina")
        }
    }
}


@Composable
fun PantallaLogin() {
    var user by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var errorMessage by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .padding(all = 40.dp)
    ) {
        Text(
            text = "Iniciar sesión",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        TextField(
            label = { Text(text = "Usuario") },
            value = user,
            onValueChange = { user = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        TextField(
            label = { Text(text = "Contraseña") },
            value = password,
            onValueChange = { password = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        Button(
            onClick = {
                errorMessage = if (user == "Rick" && password == "Sanchez") {
                    "Usuario logueado!"
                } else {
                    "Error"
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Iniciar sesión")
        }

        // Mostrar un Toast si hay mensaje de error
        LaunchedEffect(key1=errorMessage) {
            if (errorMessage.isNotEmpty()) {
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
            errorMessage = ""
        }
    }
}

