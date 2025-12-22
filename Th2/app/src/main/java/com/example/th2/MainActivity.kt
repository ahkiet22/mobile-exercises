package com.example.th2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.th2.ui.theme.Th2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Th2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NumberInputForm(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun NumberInputForm(modifier: Modifier = Modifier) {
    var input by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var boxCount by remember { mutableIntStateOf(0) }
    var isButtonClicked by remember { mutableStateOf(false) }

    var successMessage by remember { mutableStateOf("") }


    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Thực hành 02",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = input,
                    onValueChange = { value ->
                        input = value
                        isButtonClicked = false
                        successMessage = ""

                        errorMessage =
                            when {
                                value.isBlank() -> ""
                                !value.contains('@') -> "Email phải chứa ký tự @"
                                else -> ""
                            }
                    },

                    label = { Text("Nhập email") },
                    isError = errorMessage.isNotEmpty(),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                        }
                    ),
                    modifier = Modifier
                        .weight(8f),
                    shape = RoundedCornerShape(16.dp)
                )

                Button(
                    onClick = {
                        if (errorMessage.isEmpty() && input.isNotEmpty()) {
                            successMessage = "Email đã được gửi thành công: $input"
                            isButtonClicked = true
                        }
                    },
                    enabled = input.isNotEmpty() && errorMessage.isEmpty(),

                    modifier = Modifier
                        .weight(2f)
                        .height(60.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Gửi")
                }
            }

            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 8.dp)
                )
            } else if (successMessage.isNotEmpty()) {
                Text(
                    text = successMessage,
                    color = Color.Green,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }


            if (isButtonClicked && boxCount > 0) {
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun EmailInputFormPreview() {
    Th2Theme {
        NumberInputForm()
    }
}