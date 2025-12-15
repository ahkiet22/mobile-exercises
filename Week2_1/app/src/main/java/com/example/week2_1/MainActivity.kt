package com.example.week2_1
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import com.example.week2_1.ui.theme.Week2_1Theme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Week2_1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NumberInputForm(modifier = Modifier.padding(innerPadding))
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

    @Suppress("SpellCheckingInspection")
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
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

                        errorMessage =
                            when {
                                value.isBlank() -> ""
                                value.toIntOrNull() == null -> "Dữ liệu của bạn nhập không hợp lệ"
                                else -> ""
                            }
                    },

                    label = { Text("Nhập vào số lượng") },
                    isError = errorMessage.isNotEmpty(),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {}
                    ),
                    modifier = Modifier
                        .weight(8f)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                )

                Button(
                    onClick = {
                        boxCount =
                            if (input.isNotEmpty() && input.all { it.isDigit() }) {
                                input.toInt()
                            } else {
                                0
                            }
                        isButtonClicked = true
                    },

                    modifier = Modifier
                        .weight(2f)
                        .height(60.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Tạo")
                }
            }

            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            if (isButtonClicked) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)

                ) {
                    items(boxCount) { index ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .background(Color.Red, RoundedCornerShape(16.dp))
                                .padding(horizontal = 16.dp),
                            contentAlignment = Alignment.Center

                        ) {
                            Text(
                                text = "${index + 1}",
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }


            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun NumberInputFormPreview() {
    Week2_1Theme {
        NumberInputForm()
    }
}
