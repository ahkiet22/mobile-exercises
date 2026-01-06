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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
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
        description = "With management based on priority and daily tasks, it will give you convenience in managing and determining the tasks that must be done first.",
        icon = Icons.Default.DateRange
    ),
    OnBoardingPage(
        title = "Increase Work Effectiveness",
        description = "Time management and the determination of more important tasks will give your job statistics better and always improve.",
        icon = Icons.Default.CheckCircle
    ),
    OnBoardingPage(
        title = "Reminder Notification",
        description = "The advantage of this application is that it also provides reminders for you so you don't forget to keep doing your assignments.",
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

// --- Navigation Host ---
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
        composable("home") {
            // This is where the app goes after onboarding
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Home Screen (To Do List)")
            }
        }
    }
}

// --- Screens ---
@Composable
fun SplashScreen(navController: NavController) {
    // Navigate after 2 seconds
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = { navController.navigate("home") }) {
                    Text(text = "skip", color = blueColor)
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(bottom = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) { page ->
                OnBoardingPageUI(page = onBoardingData[page])
            }


            Row(
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(onBoardingData.size) { iteration ->
                    val color = if (pagerState.currentPage == iteration) blueColor else Color.LightGray
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(10.dp)
                    )
                }
            }


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (pagerState.currentPage > 0) {
                    IconButton(
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage - 1)
                            }
                        },
                        modifier = Modifier
                            .background(blueColor, CircleShape)
                            .size(50.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.size(50.dp))
                }


                Button(
                    onClick = {
                        if (pagerState.currentPage < onBoardingData.size - 1) {
                            scope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        } else {
                            navController.navigate("home") {
                                popUpTo("onboarding") { inclusive = true }
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = blueColor),
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                        .padding(start = 16.dp),
                    shape = RoundedCornerShape(25.dp)
                ) {
                    Text(
                        text = if (pagerState.currentPage == onBoardingData.size - 1) "Get Started" else "Next",
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}

@Composable
fun OnBoardingPageUI(page: OnBoardingPage) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = page.icon,
            contentDescription = null,
            modifier = Modifier
                .size(200.dp)
                .padding(bottom = 32.dp),
            tint = Color(0xFF2196F3)
        )

        Text(
            text = page.title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = page.description,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            color = Color.Gray,
            lineHeight = 20.sp
        )
    }
}