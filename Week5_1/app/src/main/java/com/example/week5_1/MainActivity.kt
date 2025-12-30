package com.example.week5_1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.week5_1.ui.theme.Week5_1Theme
import androidx.compose.material.icons.automirrored.filled.ArrowBack

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Week5_1Theme {
                AppNavigation()
            }
        }
    }
}


@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("column_screen") { ColumnScreen(navController) }
        composable("lazy_column_screen") { LazyColumnScreen(navController) }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColumnScreen(navController: NavHostController) {
    val items = List(1000000) { "Item $it" }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Column") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            items.forEach { item ->
                ListItem(text = item)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LazyColumnScreen(navController: NavHostController) {
    val items = List(1000000) { "Item ${it + 1}" }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("LazyColumn") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding).fillMaxSize(),
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items) { item ->
                ListItem(text = item)
            }
        }
    }
}

//@Composable
//fun AppNavigation() {
//    val navController = rememberNavController()
//    NavHost(navController = navController, startDestination = "home") {
//        composable("home") { HomeScreen(navController) }
//        composable("column_screen") { ColumnScreen() }
//        composable("lazy_column_screen") { LazyColumnScreen() }
//    }
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Choose List Type") }) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { navController.navigate("column_screen") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                modifier = Modifier.fillMaxWidth(0.8f).padding(8.dp)
            ) {
                Text("Column (1,000,000 items)")
            }

            Button(
                onClick = { navController.navigate("lazy_column_screen") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800)),
                modifier = Modifier.fillMaxWidth(0.8f).padding(8.dp)
            ) {
                Text("LazyColumn (1,000,000 items)")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColumnScreen() {
    val items = List(1000000) { "Item $it" }
    Scaffold(topBar = { TopAppBar(title = { Text("Column") }) }) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            items.forEach { item ->
                ListItem(text = item)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LazyColumnScreen() {
    val items = List(1000000) { "Item ${it + 1}" }
    Scaffold(topBar = { TopAppBar(title = { Text("LazyColumn") }) }) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding).fillMaxSize(),
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items) { item ->
                ListItem(text = item)
            }
        }
    }
}

@Composable
fun ListItem(text: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD))
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = text, style = MaterialTheme.typography.titleMedium)
                Text(
                    text = "The only way to do great work\nis to love what you do.",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
            Icon(Icons.Default.ArrowForward, contentDescription = null)
        }
    }
}