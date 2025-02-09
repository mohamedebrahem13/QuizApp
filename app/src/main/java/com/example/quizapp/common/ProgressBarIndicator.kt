package com.example.quizapp.common

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ProgressBarIndicator(currentPage: Int, totalPages: Int) {
    val animatedProgress by animateFloatAsState(
        targetValue = (currentPage + 1).toFloat() / totalPages.toFloat(),
        animationSpec = tween(durationMillis = 500), // Smooth Animation
        label = "Progress Animation"
    )

    LinearProgressIndicator(
        progress = { animatedProgress }, // ✅ Use animated progress
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        color = Color.White,
        trackColor = Color.Gray,
    )
}
