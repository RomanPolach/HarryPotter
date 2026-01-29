package com.romanpolach.harrypotter.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun MagicalBackground(
    content: @Composable () -> Unit
) {
    val backgroundColor = MaterialTheme.colorScheme.background
    val surfaceColor = MaterialTheme.colorScheme.surfaceVariant
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        // Subtle magical gradient pulse/glow
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            surfaceColor.copy(alpha = 0.4f),
                            Color.Transparent
                        ),
                        radius = 1500f
                    )
                )
        )
        
        content()
    }
}
