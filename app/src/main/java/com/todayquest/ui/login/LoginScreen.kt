package com.todayquest.ui.login

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.todayquest.R
import com.todayquest.ui.theme.LightGrey

@Preview
@Composable
fun preview() {
    LoginScreen(
        onClickLoginButton = { /*TODO*/ },
        loginUiState = LoginUiState("메시지 테스트")
    )
}

@Composable
fun LoginScreen(
    onClickLoginButton: () -> Unit,
    loginUiState: LoginUiState,
    modifier: Modifier = Modifier
) {
    Log.d("LoginScreen", "start")

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxHeight()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxHeight(0.5f)
            ) {
                LogoImage()
                Spacer(Modifier.size(50.dp))
                AppTitle()
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxHeight(),
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                ) {
                    LoginMessage()
                    ErrorMessage(loginUiState.errorMessage)
                }
                Spacer(Modifier.size(50.dp))
                LoginButton(onClick = onClickLoginButton)
            }
        }
    }

}

@Composable
fun LogoImage() {
    val image = painterResource(R.drawable.checklist)
    Image(
        painter = image,
        contentDescription = "logo image",
        modifier = Modifier
            .width(128.dp)
            .height(128.dp)
    )
}

@Composable
fun AppTitle() {
    Text(
        stringResource(R.string.todayquest),
        fontSize = 40.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight()
    )
}

@Composable
fun LoginMessage() {

    val flashAnimation by rememberInfiniteTransition().animateColor(
        initialValue = LightGrey,
        targetValue = Color.Transparent,
        animationSpec = infiniteRepeatable(
            tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Text(
        stringResource(R.string.login_guide_message),
        fontSize = 20.sp,
        textAlign = TextAlign.Center,
        color = flashAnimation,
        modifier = Modifier
            .width(234.dp)
            .wrapContentHeight()
    )
}

@Composable
fun LoginButton(onClick: () -> Unit) {
    val image = painterResource(R.drawable.btn_google_light_normal)

    Button(
        onClick = onClick,
        shape = FloatingActionButtonDefaults.extendedFabShape,
        contentPadding = PaddingValues(5.dp),
        modifier = Modifier
            .width(215.dp)
            .height(50.dp),

        ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = image,
                contentDescription = "",
                modifier = Modifier
                    .fillMaxHeight(0.6f)
            )
            Spacer(Modifier.size(5.dp))
            Text(
                stringResource(R.string.google_login),
            )
        }
    }
}

@Composable
fun ErrorMessage(
    errorMessage: String,
) {

    AnimatedVisibility(
        visible = errorMessage.isNotEmpty(),
        Modifier
            .fillMaxWidth(0.75f)
    ) {
        Snackbar() {
            Text(
                text = errorMessage,
                Modifier.wrapContentWidth()
            )
        }
    }
}