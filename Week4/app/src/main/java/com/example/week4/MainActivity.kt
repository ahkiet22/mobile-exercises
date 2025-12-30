package com.example.week4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.week4.ui.theme.Week4Theme

sealed class Screen(val route: String) {
    object MainList : Screen("main_list")
    object TextDetail : Screen("text_detail")
    object ImageDetail : Screen("image_detail")
    object TextFieldDetail : Screen("textfield_detail")
    object PasswordDetail : Screen("password_detail")
    object ColumnLayout : Screen("column_layout")
    object RowLayout : Screen("row_layout")
    object SelfStudy : Screen("self_study")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Week4Theme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Screen.MainList.route) {
                    composable(Screen.MainList.route) { MainListScreen(navController) }
                    composable(Screen.TextDetail.route) { TextDetailScreen(navController) }
                    composable(Screen.ImageDetail.route) { ImageDetailScreen(navController) }
                    composable(Screen.TextFieldDetail.route) { TextFieldDetailScreen(navController) }
                    composable(Screen.PasswordDetail.route) { PasswordDetailScreen(navController) }
                    composable(Screen.ColumnLayout.route) { ColumnLayoutScreen(navController) }
                    composable(Screen.RowLayout.route) { RowLayoutScreen(navController) }
                    composable(Screen.SelfStudy.route) { SelfStudyScreen(navController) }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainListScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("UI Components List", color = Color(0xFF2196F3), fontWeight = FontWeight.Bold) })
        }
    ) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding).padding(16.dp)) {
            item { CategoryHeader("Display") }
            item { ComponentItem("Text", "Displays text", Color(0xFFBBDEFB)) { navController.navigate(Screen.TextDetail.route) } }
            item { ComponentItem("Image", "Displays an image", Color(0xFFBBDEFB)) { navController.navigate(Screen.ImageDetail.route) } }

            item { CategoryHeader("Input") }
            item { ComponentItem("TextField", "Input field for text", Color(0xFFBBDEFB)) { navController.navigate(Screen.TextFieldDetail.route) } }
            item { ComponentItem("PasswordField", "Input field for passwords", Color(0xFFBBDEFB)) { navController.navigate(Screen.PasswordDetail.route) } }

            item { CategoryHeader("Layout") }
            item { ComponentItem("Column", "Arranges elements vertically", Color(0xFFBBDEFB)) { navController.navigate(Screen.ColumnLayout.route) } }
            item { ComponentItem("Row", "Arranges elements horizontally", Color(0xFFBBDEFB)) { navController.navigate(Screen.RowLayout.route) } }

            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { ComponentItem("Tự tìm hiểu", "Tìm ra tất cả các thành phần UI Cơ bản", Color(0xFFFFCDD2)) { navController.navigate(Screen.SelfStudy.route) } }
        }
    }
}

@Composable
fun CategoryHeader(title: String) {
    Text(title, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 8.dp))
}

@Composable
fun ComponentItem(title: String, subtitle: String, color: Color, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp).clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = color)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(subtitle, fontSize = 14.sp, color = Color.Gray)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScaffold(title: String, navController: NavHostController, content: @Composable (PaddingValues) -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title, color = Color(0xFF2196F3)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color(0xFF2196F3))
                    }
                }
            )
        },
        content = content
    )
}

@Composable
fun TextDetailScreen(navController: NavHostController) {
    DetailScaffold("Text Detail", navController) { padding ->
        Box(Modifier.padding(padding).fillMaxSize(), contentAlignment = Alignment.Center) {
            val styledText = buildAnnotatedString {
                append("The ")
                withStyle(style = SpanStyle(textDecoration = TextDecoration.LineThrough)) { append("quick") }
                append(" ")
                withStyle(style = SpanStyle(color = Color(0xFFAD7A3F), fontWeight = FontWeight.Bold, fontSize = 24.sp)) { append("Brown") }
                append(" fox ")
                withStyle(style = SpanStyle(fontStyle = FontStyle.Italic, fontSize = 24.sp)) { append("jumps") }
                append(" ")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) { append("over") }
                append("\n")
                withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) { append("the") }
                append(" ")
                withStyle(style = SpanStyle(fontStyle = FontStyle.Italic)) { append("lazy") }
                append(" dog.")
            }
            Text(text = styledText, fontSize = 20.sp)
        }
    }
}

@Composable
fun ImageDetailScreen(navController: NavHostController) {
    DetailScaffold("Images", navController) { padding ->
        Column(Modifier.padding(padding).fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            AsyncImage(
                model = "https://s.cmx-cdn.com/giaothongvantaitphcm.edu.vn/wp-content/uploads/2024/06/ky-niem-36-nam-thanh-lap-truong-dai-hoc-giao-thong-van-tai-tphcm-560px.jpg",
                contentDescription = "UTH",
                modifier = Modifier.fillMaxWidth().height(200.dp),
                contentScale = ContentScale.Fit
            )
            Text("https://s.cmx-cdn.com/giaothongvantaitphcm.edu.vn/wp-content/uploads/2024/06/ky-niem-36-nam-thanh-lap-truong-dai-hoc-giao-thong-van-tai-tphcm-560px.jpg",
                fontSize = 10.sp, color = Color.Blue, modifier = Modifier.padding(8.dp))

            Image(
                painter = painterResource(id = R.drawable.uth),
                contentDescription = "Logo UTH Local",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Fit
            )
            Text("In app")
        }
    }
}

@Composable
fun TextFieldDetailScreen(navController: NavHostController) {
    var text by remember { mutableStateOf("") }
    DetailScaffold("TextField", navController) { padding ->
        Column(Modifier.padding(padding).fillMaxSize().padding(32.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Thông tin nhập") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text("Tự động cập nhật dữ liệu theo textfield", color = Color.Red, fontSize = 12.sp)
            Text(text, fontSize = 18.sp)
        }
    }
}

@Composable
fun RowLayoutScreen(navController: NavHostController) {
    DetailScaffold("Row Layout", navController) { padding ->
        Column(Modifier.padding(padding).fillMaxSize().padding(16.dp)) {
            repeat(4) {
                Row(Modifier.fillMaxWidth().padding(vertical = 8.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
                    Box(Modifier.size(80.dp, 50.dp).background(Color(0xFFBBDEFB), RoundedCornerShape(8.dp)))
                    Box(Modifier.size(80.dp, 50.dp).background(Color(0xFF42A5F5), RoundedCornerShape(8.dp)))
                    Box(Modifier.size(80.dp, 50.dp).background(Color(0xFFBBDEFB), RoundedCornerShape(8.dp)))
                }
            }
        }
    }
}

@Composable
fun PasswordDetailScreen(navController: NavHostController) {
    DetailScaffold("PasswordField", navController) { padding ->
        Box(Modifier.padding(padding).fillMaxSize(), contentAlignment = Alignment.Center) {
            OutlinedTextField(value = "123456", onValueChange = {}, visualTransformation = PasswordVisualTransformation())
        }
    }
}

@Composable
fun ColumnLayoutScreen(navController: NavHostController) {
    DetailScaffold("Column Layout", navController) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(Modifier.size(200.dp, 60.dp).background(Color(0xFFBBDEFB), RoundedCornerShape(8.dp)))
            Box(Modifier.size(200.dp, 60.dp).background(Color(0xFF42A5F5), RoundedCornerShape(8.dp)))
            Box(Modifier.size(200.dp, 60.dp).background(Color(0xFFBBDEFB), RoundedCornerShape(8.dp)))
            Box(Modifier.size(200.dp, 60.dp).background(Color(0xFF1976D2), RoundedCornerShape(8.dp)))
        }
    }
}
@Composable
fun SelfStudyScreen(navController: NavHostController) {
    var checked by remember { mutableStateOf(true) }
    var switched by remember { mutableStateOf(false) }
    var sliderValue by remember { mutableFloatStateOf(0.5f) }

    DetailScaffold("Tự tìm hiểu", navController) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Button(onClick = { }) { Text("Button") }
                Spacer(Modifier.width(16.dp))
                Text("Nút bấm cơ bản", fontSize = 14.sp, color = Color.Gray)
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = checked, onCheckedChange = { checked = it })
                Text("Checkbox (Lựa chọn)", fontSize = 14.sp)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Bật/Tắt (Switch)")
                Switch(checked = switched, onCheckedChange = { switched = it })
            }

            Column {
                Text("Slider: ${(sliderValue * 100).toInt()}%", fontSize = 14.sp)
                Slider(value = sliderValue, onValueChange = { sliderValue = it })
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                CircularProgressIndicator(modifier = Modifier.size(30.dp), strokeWidth = 3.dp)
                Spacer(Modifier.width(16.dp))
                Text("Đang tải...", fontSize = 14.sp)
            }
        }
    }
}