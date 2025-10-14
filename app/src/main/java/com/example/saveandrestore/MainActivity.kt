package com.example.saveandrestore

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    // Estado para guardar el número aleatorio
    private var randomNumber by mutableStateOf(0)
    
    companion object {
        private const val TAG = "MainActivity"
        private const val RANDOM_NUMBER = "random_number"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Recuperar el número aleatorio guardado
        randomNumber = savedInstanceState?.getInt(RANDOM_NUMBER, 0) ?: 0
        
        setContent {
            MaterialTheme {
                RandomNumberScreen(
                    randomNumber = randomNumber,
                    onRandomNumberChange = { newNumber ->
                        randomNumber = newNumber
                    }
                )
            }
        }
        Log.d(TAG, "onCreate")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Guardar el número aleatorio
        outState.putInt(RANDOM_NUMBER, randomNumber)
        Log.d(TAG, "onSaveInstanceState: saved randomNumber = $randomNumber")
    }
}

@Composable
fun RandomNumberScreen(
    randomNumber: Int,
    onRandomNumberChange: (Int) -> Unit
) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        // UI principal
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            
            Button(
                onClick = { onRandomNumberChange(Random.nextInt(0, 1000)) },
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.generate_random_number),
                    fontSize = 18.sp
                )
            }
            
            Text(
                text = stringResource(
                    id = R.string.random_number_message,
                    randomNumber
                ),
                fontSize = 20.sp,
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}