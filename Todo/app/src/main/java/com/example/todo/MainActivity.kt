package com.example.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todo.ui.theme.TodoTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class OnBoardingPage(
    val title: String,
    val description: String,
    val icon: ImageVector
)

val onBoardingData = listOf(
    OnBoardingPage(
        title = "Easy Time Management",
        description = "With management based on priority and daily tasks, it will give you convenience...",
        icon = Icons.Default.DateRange
    ),
    OnBoardingPage(
        title = "Increase Work Effectiveness",
        description = "Time management and the determination of more important tasks will give your job statistics better...",
        icon = Icons.Default.CheckCircle
    ),
    OnBoardingPage(
        title = "Reminder Notification",
        description = "The advantage of this application is that it also provides reminders for you...",
        icon = Icons.Default.Notifications
    )
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodoTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") {
            SplashScreen(navController = navController)
        }
        composable("onboarding") {
            OnBoardingScreen(navController = navController)
        }
        composable("login") {
            LoginScreen(navController = navController)
        }
        composable("forgot_password") {
            ForgotPasswordScreen(navController = navController)
        }
        composable("forgot_verify") {
            VerifyCodeScreen(navController = navController)
        }
        composable("reset_password") {
            ResetPasswordScreen(navController = navController)
        }
        composable("confirm") {
            ConfirmScreen(navController = navController)
        }
        composable("home") {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Home Screen (To Do List)", fontSize = 24.sp)
            }
        }
    }
}

// --- Screens ---

@Composable
fun SplashScreen(navController: NavController) {
    LaunchedEffect(Unit) {
        delay(2000)
        navController.navigate("onboarding") {
            popUpTo("splash") { inclusive = true }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = "Logo",
                modifier = Modifier.size(100.dp),
                tint = Color(0xFF2196F3)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "UTH SmartTasks",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2196F3)
            )
        }
    }
}

@Composable
fun OnBoardingScreen(navController: NavController) {
    val pagerState = rememberPagerState(pageCount = { onBoardingData.size })
    val scope = rememberCoroutineScope()
    val blueColor = Color(0xFF2196F3)

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = { navController.navigate("login") { popUpTo("onboarding") { inclusive = true } } }) {
                    Text(text = "skip", color = blueColor)
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding).padding(bottom = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HorizontalPager(state = pagerState, modifier = Modifier.weight(1f)) { page ->
                OnBoardingPageUI(page = onBoardingData[page])
            }
            Row(modifier = Modifier.height(50.dp).fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                repeat(onBoardingData.size) { iteration ->
                    val color = if (pagerState.currentPage == iteration) blueColor else Color.LightGray
                    Box(modifier = Modifier.padding(2.dp).clip(CircleShape).background(color).size(10.dp))
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (pagerState.currentPage > 0) {
                    IconButton(
                        onClick = { scope.launch { pagerState.animateScrollToPage(pagerState.currentPage - 1) } },
                        modifier = Modifier.background(blueColor, CircleShape).size(50.dp)
                    ) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                } else {
                    Spacer(modifier = Modifier.size(50.dp))
                }
                Button(
                    onClick = {
                        if (pagerState.currentPage < onBoardingData.size - 1) {
                            scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
                        } else {
                            navController.navigate("login") { popUpTo("onboarding") { inclusive = true } }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = blueColor),
                    modifier = Modifier.weight(1f).height(50.dp).padding(start = 16.dp),
                    shape = RoundedCornerShape(25.dp)
                ) {
                    Text(text = if (pagerState.currentPage == onBoardingData.size - 1) "Get Started" else "Next", fontSize = 18.sp)
                }
            }
        }
    }
}

@Composable
fun OnBoardingPageUI(page: OnBoardingPage) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(imageVector = page.icon, contentDescription = null, modifier = Modifier.size(200.dp).padding(bottom = 32.dp), tint = Color(0xFF2196F3))
        Text(text = page.title, fontSize = 24.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, color = Color.Black)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = page.description, fontSize = 14.sp, textAlign = TextAlign.Center, color = Color.Gray, lineHeight = 20.sp)
    }
}

@Composable
fun LoginScreen(navController: NavController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val blueColor = Color(0xFF2196F3)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.Lock,
            contentDescription = "Login",
            modifier = Modifier.size(80.dp),
            tint = blueColor
        )
        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Welcome Back!",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = blueColor
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
            TextButton(onClick = { navController.navigate("forgot_password") }) {
                Text("Forgot Password?", color = blueColor)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                navController.navigate("home") {
                    popUpTo("login") { inclusive = true }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = blueColor),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(text = "LOGIN", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun ForgotPasswordScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    val blueColor = Color(0xFF2196F3)

    Scaffold(
        topBar = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.Black)
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Forgot Password",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = blueColor
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Enter your email address to reset your password.",
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email Address") },
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    navController.navigate("forgot_verify")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = blueColor),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = "SEND RESET LINK", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun VerifyCodeScreen(navController: NavController) {
    val blueColor = Color(0xFF2196F3)
    val focusManager = LocalFocusManager.current

    // Dùng 6 ô theo ảnh
    val otpDigits = remember { mutableStateListOf("", "", "", "", "", "") }

    Scaffold(
        topBar = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.Black)
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Verify Code", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = blueColor)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Enter the code we just sent to your registered Email",
                fontSize = 14.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(32.dp))

            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                otpDigits.forEachIndexed { index, value ->
                    OutlinedTextField(
                        value = value,
                        onValueChange = { v ->
                            if (v.length <= 1 && (v.isEmpty() || v[0].isDigit())) {
                                otpDigits[index] = v
                                if (v.isNotEmpty()) {
                                    if (index < otpDigits.lastIndex) {
                                        // request next by focusing next field: simpler to clear focus then rely on keyboard navigation
                                        // or user can tap; for full focus control use FocusRequester per field
                                    } else {
                                        focusManager.clearFocus()
                                    }
                                }
                            }
                        },
                        modifier = Modifier
                            .size(52.dp)
                            .padding(4.dp),
                        singleLine = true,
                        textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center, fontSize = 20.sp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        shape = RoundedCornerShape(8.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            Button(
                onClick = {
                    navController.navigate("reset_password")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = blueColor),
                shape = RoundedCornerShape(25.dp)
            ) {
                Text(text = "Next", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(12.dp))

            TextButton(onClick = { /* resend logic */ }) {
                Text("Resend code", color = blueColor)
            }
        }
    }
}

@Composable
fun ResetPasswordScreen(navController: NavController) {
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmVisible by remember { mutableStateOf(false) }
    val blueColor = Color(0xFF2196F3)

    Scaffold(
        topBar = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.Black)
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Create new password", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = blueColor)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Your new password must be different from previously used password",
                fontSize = 14.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff, contentDescription = null)
                    }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirm Password") },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                trailingIcon = {
                    IconButton(onClick = { confirmVisible = !confirmVisible }) {
                        Icon(imageVector = if (confirmVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff, contentDescription = null)
                    }
                },
                visualTransformation = if (confirmVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(28.dp))

            Button(
                onClick = {
                    navController.navigate("confirm")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = blueColor),
                shape = RoundedCornerShape(25.dp)
            ) {
                Text(text = "Next", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun ConfirmScreen(navController: NavController) {
    var email by remember { mutableStateOf("uth@gmail.com") }
    var code by remember { mutableStateOf("123456") }
    var password by remember { mutableStateOf("newpassword") }
    val blueColor = Color(0xFF2196F3)

    Scaffold(
        topBar = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.Black)
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Confirm", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = blueColor)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "We are here to help you!", fontSize = 14.sp, color = Color.Gray)

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = code,
                onValueChange = { code = it },
                leadingIcon = { Icon(Icons.Default.Mail, contentDescription = null) },
                label = { Text("Code") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                label = { Text("New Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    navController.navigate("login") {
                        popUpTo("confirm") { inclusive = true }
                    }
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = blueColor),
                shape = RoundedCornerShape(25.dp)
            ) {
                Text(text = "Summit", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
