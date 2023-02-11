package com.todayquest

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.todayquest.ui.LoadingScreen
import com.todayquest.ui.login.LoginViewModel
import com.todayquest.ui.theme.TodayQuestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val loginViewModel: LoginViewModel by viewModels()
        setContent {
            var isLoaded by rememberSaveable{ mutableStateOf(false) }
            TodayQuestTheme {
                LoadingScreen() {
                    loginViewModel.loginCheck {
                        isLoaded = true
                    }
                }
                if(isLoaded) {
                    TodayQuestApp()
                }
            }
        }
    }

}