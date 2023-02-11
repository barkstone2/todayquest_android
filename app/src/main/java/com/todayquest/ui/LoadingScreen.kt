package com.todayquest.ui

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.todayquest.ui.login.AppTitle
import com.todayquest.ui.login.LogoImage

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier,
    onLoading: () -> Unit
) {

    Log.d("LoadingScreen", "loading")
    run {
        onLoading()
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxHeight()
        ) {
            LogoImage()
            Spacer(Modifier.size(50.dp))
            AppTitle()
            Spacer(Modifier.size(50.dp))
        }
    }
}