package com.example.weatherwise.ui.custom

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.weatherwise.ui.theme.robotoFont

@Composable
fun CustomIconButton(
    onClicked: () -> Unit,
    label: String,
    icon: Painter
) {
    Button(
        onClick = { onClicked() }
    ) {
        Icon(icon, contentDescription = null)
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            fontFamily = robotoFont
        )
    }
}