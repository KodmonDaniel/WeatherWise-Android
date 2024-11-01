package com.example.weatherwise.ui.custom

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherwise.R
import com.example.weatherwise.ui.theme.robotoFont

@Composable
fun EmptyScreen() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Icon(painterResource(id = R.drawable.unknown_icon), contentDescription = "?")
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = "Nothing to show.",
            fontFamily = robotoFont,
            fontSize = 16.sp,
        )
    }
}