package com.example.myapplication

//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.Text
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.screen.ProfileScreen
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    ProfileScreen(
                        name = "Johan Smith",
                        location = "California, USA",
                    )
                }
            }
        }
    }
}

//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(color = Color.LightGray)
//            .padding(20.dp)
//    ) {
//        Box(
//            modifier = modifier,
//            contentAlignment = Alignment.Center
//        ) {
//            Text(
//                text = "Johan Smith",
//                color = Color.Black,
//                fontWeight = FontWeight.Bold
//            )
//            Text(
//                text = "California, USA",
//                color = Color.Gray,
//                modifier = modifier
//            )
//        }
//    }
//}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            ProfileScreen(
                name = "Johan Smith",
                location = "California, USA",
            )
        }
    }
}
