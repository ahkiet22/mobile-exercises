package com.example.week3_2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.week3_2.ui.theme.PaymentSelectionTheme

interface PaymentStrategy {
    val name: String
    val logoUrl: String
    fun processPayment()
}

class PayPalStrategy : PaymentStrategy {
    override val name: String = "PayPal"
    override val logoUrl: String = "https://img.icons8.com/color/1200/paypal.jpg"
    override fun processPayment() {
        println("Processing payment with PayPal")
    }
}

class GooglePayStrategy : PaymentStrategy {
    override val name: String = "Google Pay"
    override val logoUrl: String = "https://img.icons8.com/color/512/google-pay.png"
    override fun processPayment() {
        println("Processing payment with GooglePay")
    }
}

class ApplePayStrategy : PaymentStrategy {
    override val name: String = "Apple Pay"
    override val logoUrl: String = "https://img.icons8.com/m_rounded/1200/mac-os.jpg"
    override fun processPayment() {
        println("Processing payment with ApplePay")
    }
}

class PaymentContext(private var paymentStrategy: PaymentStrategy) {
    fun setPaymentStrategy(paymentStrategy: PaymentStrategy) {
        this.paymentStrategy = paymentStrategy
    }

    fun executePayment() {
        paymentStrategy.processPayment()
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PaymentSelectionTheme {
                PaymentSelectionScreen()
            }
        }
    }
}

@Composable
fun PaymentSelectionScreen() {
    val paymentMethods = remember {
        listOf(
            PayPalStrategy(),
            GooglePayStrategy(),
            ApplePayStrategy()
        )
    }

    var selectedPaymentMethod by remember { mutableStateOf(paymentMethods[0]) }
    val paymentContext = remember { PaymentContext(selectedPaymentMethod) }

    var showAlert by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Chọn hình thức thanh toán", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(20.dp))


        paymentMethods.forEach { paymentStrategy ->
            PaymentOptionRow(paymentStrategy, selectedPaymentMethod) {
                selectedPaymentMethod = paymentStrategy
                paymentContext.setPaymentStrategy(paymentStrategy)
            }
            Spacer(modifier = Modifier.height(20.dp))
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                paymentContext.executePayment()
                showAlert = true
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
            enabled = selectedPaymentMethod != null,
            colors = ButtonDefaults.buttonColors(
                containerColor = androidx.compose.ui.graphics.Color(0xFF4A80F0)
            )
        ) {
            Text("Continue", style = MaterialTheme.typography.titleMedium)
        }


        if (showAlert) {
            AlertDialog(
                onDismissRequest = { showAlert = false },
                title = { Text("Thông báo") },
                text = { Text("Thanh toán qua ${selectedPaymentMethod.name} đã được thực hiện.") },
                confirmButton = {
                    TextButton(onClick = { showAlert = false }) {
                        Text("OK")
                    }
                }
            )
        }
    }
}

@Composable
fun PaymentOptionRow(paymentStrategy: PaymentStrategy, selectedPaymentMethod: PaymentStrategy, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
        color = Color.White,
        tonalElevation = 2.dp,
        shadowElevation = 4.dp,
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = paymentStrategy == selectedPaymentMethod,
                onClick = onClick
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = paymentStrategy.name)

            Spacer(modifier = Modifier.weight(1f))

            AsyncImage(
                model = paymentStrategy.logoUrl,
                contentDescription = null,
                modifier = Modifier.height(24.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PaymentSelectionTheme {
        PaymentSelectionScreen()
    }
}
