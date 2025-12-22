package com.example.week3_1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.week3_1.ui.theme.Week3_1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Week3_1Theme {
                NullableExampleScreen()
            }
        }
    }
}

@Composable
fun NullableExampleScreen() {
    var name: String? by remember { mutableStateOf<String?>(null) }
    var errorMessage: String? by remember { mutableStateOf(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Name length: ${name?.length ?: "No name provided"}",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(20.dp))


        val nameLength = try {
            name!!.length
        } catch (e: NullPointerException) {
            null
        }


        if (nameLength != null) {
            Text("Name length (with !!): $nameLength")
        } else {
            errorMessage = "Name is null"
            Text("Error: $errorMessage")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            name = "Kotlin"
            errorMessage = null
        }) {
            Text("Set name to 'Kotlin'")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NullableExamplePreview() {
    Week3_1Theme {
        NullableExampleScreen()
    }
}
