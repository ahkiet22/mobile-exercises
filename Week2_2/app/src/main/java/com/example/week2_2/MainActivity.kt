package com.example.week2_2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.week2_2.ui.theme.Week2_2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Week2_2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CalculateScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun CalculateScreen(modifier: Modifier = Modifier) {
    var num1 by remember { mutableStateOf("") }
    var num2 by remember { mutableStateOf("") }
    var operator by remember { mutableStateOf("+") }

    val result = calculateResult(num1, num2, operator)
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = modifier
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Text(
                text = "Thực hành 03",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(24.dp))

            NumberInput(value = num1, onValueChange = { num1 = it })

            Spacer(modifier = Modifier.height(16.dp))

            OperatorRow(
                selectedOperator = operator,
                onOperatorSelected = { operator = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            NumberInput(value = num2, onValueChange = { num2 = it })

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Kết quả: $result",
                fontSize = 16.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )
        }
    }

}

@Composable
fun NumberInput(value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Number") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    )
}


@Composable
fun OperatorRow(
    selectedOperator: String,
    onOperatorSelected: (String) -> Unit
) {
    val operators = listOf("+", "-", "*", "/")

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        operators.forEach { op ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
                    .background(
                        color = when (op) {
                            "+" -> Color(0xFFE53935)
                            "-" -> Color(0xFFFFB300)
                            "*" -> Color(0xFF5E35B1)
                            "/" -> Color(0xFF212121)
                            else -> Color.Gray
                        },
                        shape = RoundedCornerShape(8.dp)
                    )
                    .border(
                        width = if (op == selectedOperator) 2.dp else 0.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clickable { onOperatorSelected(op) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = op,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

fun calculateResult(num1: String, num2: String, operator: String): String {
    val a = num1.toIntOrNull()
    val b = num2.toIntOrNull()

    if (a == null || b == null) return ""

    return when (operator) {
        "+" -> (a + b).toString()
        "-" -> (a - b).toString()
        "*" -> (a * b).toString()
        "/" -> if (b != 0) (a / b).toString() else "Lỗi"
        else -> ""
    }
}


@Preview(showBackground = true)
@Composable
fun Week2_2Preview() {
    Week2_2Theme {
        CalculateScreen()
    }
}
