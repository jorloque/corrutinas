package com.lopez_jorge.corrutinas


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                PantallaConCorrutina()
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


