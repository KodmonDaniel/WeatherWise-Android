package com.example.weatherwise.ui.custom

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@Composable
fun CustomIcon(icon: Painter, background: Color) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(end = 20.dp)

    ) {
        Image(
            painter = icon,
            contentDescription = "contentDescription",
            modifier = Modifier
                .size(42.dp)
                .shadow(8.dp, CircleShape)

                .background(background, CircleShape)
                .padding(10.dp),
        )
    }
}