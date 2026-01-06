package com.example.todo.screens.onboarding

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

data class OnBoardingPage(
    val title: String,
    val description: String,
    val icon: ImageVector
)

val onBoardingData = listOf(
    OnBoardingPage(
        "Easy Time Management",
        "With management based on priority and daily tasks...",
        Icons.Default.DateRange
    ),
    OnBoardingPage(
        "Increase Work Effectiveness",
        "Time management and determination of important tasks...",
        Icons.Default.CheckCircle
    ),
    OnBoardingPage(
        "Reminder Notification",
        "This application also provides reminders...",
        Icons.Default.Notifications
    )
)
