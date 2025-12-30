package com.example.week3_3
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LibraryApp()
        }
    }
}

// 1. Strategy Pattern Interface
interface BorrowStrategy {
    fun getMaxDays(): Int
}

// 2. Concrete Strategies
class StudentStrategy : BorrowStrategy {
    override fun getMaxDays(): Int = 7
}

class StaffStrategy : BorrowStrategy {
    override fun getMaxDays(): Int = 30
}

// 3. Abstraction & Inheritance
abstract class User(
    val id: String,
    var name: String,
    protected val strategy: BorrowStrategy
) {
    fun getBorrowLimit(): Int = strategy.getMaxDays()
}

class Staff(id: String, name: String) : User(id, name, StaffStrategy())
class Student(id: String, name: String) : User(id, name, StudentStrategy())

// 4. Encapsulation
data class Book(
    val id: String,
    val title: String,
    var isSelected: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryApp() {
    val context = LocalContext.current
    var staffName by remember { mutableStateOf("Nguyen Van A") }

    var books by remember {
        mutableStateOf(listOf(
            Book("1", "Sách 01"),
            Book("2", "Sách 02"),
            Book("3", "Sách 03 Lập trình Kotlin")
        ))
    }

    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = Color.White) {
                NavigationBarItem(selected = true, onClick = {}, icon = { Icon(Icons.Default.Home, "Quản lý") }, label = { Text("Quản lý") })
                NavigationBarItem(selected = false, onClick = {}, icon = { Icon(Icons.Default.List, "DS Sách") }, label = { Text("DS Sách") })
                NavigationBarItem(selected = false, onClick = {}, icon = { Icon(Icons.Default.Person, "Nhân viên") }, label = { Text("Nhân viên") })
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Hệ thống\nQuản lý Thư viện", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Black)

            Spacer(modifier = Modifier.height(30.dp))

            // Section Nhân viên
            Column(modifier = Modifier.fillMaxWidth()) {
                Text("Nhân viên", fontWeight = FontWeight.SemiBold)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    OutlinedTextField(
                        value = staffName,
                        onValueChange = { staffName = it },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = { /* Xử lý đổi */ },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0056b3))
                    ) {
                        Text("Đổi")
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Text("Danh sách sách", fontWeight = FontWeight.SemiBold)

                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shadowElevation = 2.dp,
                    shape = RoundedCornerShape(10.dp),
                    color = Color(0xFFF5F5F5)
                ) {
                    LazyColumn(modifier = Modifier.padding(8.dp)) {
                        items(books) { book ->
                            BookItem(book = book, onCheckedChange = { isChecked ->
                                books = books.map {
                                    if (it.id == book.id) it.copy(isSelected = isChecked) else it
                                }
                            })
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = {
                    val selectedCount = books.count { it.isSelected }
                    // Áp dụng Strategy Pattern ở đây
                    val currentUser = Staff("NV01", staffName)
                    Toast.makeText(context,
                        "Nhân viên ${currentUser.name} mượn $selectedCount cuốn.\nHạn mượn: ${currentUser.getBorrowLimit()} ngày",
                        Toast.LENGTH_LONG).show()
                },
                modifier = Modifier.fillMaxWidth(0.6f),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0056b3))
            ) {
                Text("Thêm")
            }
        }
    }
}

@Composable
fun BookItem(book: Book, onCheckedChange: (Boolean) -> Unit) {
    Card(
        modifier = Modifier.padding(vertical = 4.dp).fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Checkbox(
                checked = book.isSelected,
                onCheckedChange = onCheckedChange,
                colors = CheckboxDefaults.colors(checkedColor = Color(0xFFb3002d))
            )
            Text(text = book.title, modifier = Modifier.padding(start = 8.dp))
        }
    }
}