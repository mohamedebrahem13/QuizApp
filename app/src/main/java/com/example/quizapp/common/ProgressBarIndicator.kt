package com.example.quizapp.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ProgressBarIndicator(currentPage: Int, totalPages: Int) {
    LinearProgressIndicator(
        progress = { (currentPage + 1) / totalPages.toFloat() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        color = Color.White,
        trackColor = Color.Gray,
    )
}