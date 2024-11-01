package com.example.weatherwise.feature.about

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherwise.R
import com.example.weatherwise.ui.theme.lightPink
import com.example.weatherwise.ui.theme.robotoFont

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun About(onBackPressed: () -> Unit) {
    return Scaffold(
        topBar = {
            Surface(shadowElevation = 4.dp) {
                TopAppBar(
                    title = {
                        Text(
                            stringResource(R.string.about),
                            fontFamily = robotoFont,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { onBackPressed() }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "back"
                            )
                        }
                    },
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(vertical = 20.dp, horizontal = 20.dp)
                .padding(innerPadding),
            content = {
                CustomAboutBox(text = stringResource(R.string.about_1))
                Spacer(modifier = Modifier.height(20.dp))
                CustomAboutBox(text = stringResource(R.string.about_2))
                Spacer(modifier = Modifier.height(20.dp))
                CustomAboutBox(text = stringResource(R.string.about_3))
                Spacer(modifier = Modifier.height(20.dp))
                CustomAboutBox(text = stringResource(R.string.about_4))
            }
        )
    }
}

@Composable
fun CustomAboutBox(text: String) {
    Box(
        modifier = Modifier
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(20.dp)
            )
            .clip(shape = RoundedCornerShape(20.dp))
            .background(lightPink)
            .padding(15.dp)
    ) {
        Row {
            Icon(Icons.Default.Info, contentDescription = null)
            Spacer(modifier = Modifier.width(15.dp))
            Text(text = text)
        }
    }
}