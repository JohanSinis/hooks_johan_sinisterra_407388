package com.example.intentsintroduction

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    
    companion object {
        const val FULL_NAME_KEY = "FULL_NAME_KEY"
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
    
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MainScreen() {
        var fullName by rememberSaveable { mutableStateOf("") }
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Formulario de Datos",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 32.dp)
            )
            
            OutlinedTextField(
                value = fullName,
                onValueChange = { fullName = it },
                label = { Text("Nombre completo") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
            
            Button(
                onClick = { navigateToSecondActivity(fullName) },
                modifier = Modifier.fillMaxWidth(),
                enabled = fullName.isNotBlank()
            ) {
                Text("Continue")
            }
        }
    }
    
    private fun navigateToSecondActivity(fullName: String) {
        val intent = Intent(this, WelcomeActivity::class.java).apply {
            putExtra(FULL_NAME_KEY, fullName)
        }
        startActivity(intent)
    }
}