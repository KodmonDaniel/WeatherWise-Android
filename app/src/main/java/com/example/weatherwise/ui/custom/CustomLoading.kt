package com.example.weatherwise.ui.custom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.weatherwise.R
import com.example.weatherwise.ui.theme.greyTransparent
import com.example.weatherwise.ui.theme.white

@Composable
fun CustomLoading() {
    return Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(greyTransparent)
    ) {
        Box(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(20.dp))
                .background(white)
                .padding(15.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                CircularProgressIndicator()
                Spacer(modifier = Modifier.width(25.dp))
                Text(text = stringResource(R.string.loading))
            }
        }
    }
}
