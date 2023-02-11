package com.todayquest

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.todayquest.common.GoogleSignInModule
import com.todayquest.ui.login.LoginScreen
import com.todayquest.ui.login.LoginViewModel
import com.todayquest.ui.quest.HomeScreen

enum class TodayQuestScreen(@StringRes val title: Int, val hasTopBar: Boolean = false) {
    Login(title = R.string.login),
    Home(title = R.string.app_name, true),
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodayQuestAppBar(
    currentScreen: TodayQuestScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    if(currentScreen.hasTopBar) {
        TopAppBar(
            title = { Text(stringResource(currentScreen.title)) },
            modifier = modifier,
            navigationIcon = {
                if (canNavigateBack) {
                    IconButton(onClick = navigateUp) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_button)
                        )
                    }
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodayQuestApp(
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController(),
) {

    val loginUiState by loginViewModel.uiState.collectAsStateWithLifecycle()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = TodayQuestScreen.valueOf(
        backStackEntry?.destination?.route ?: TodayQuestScreen.Home.name
    )

    val context = LocalContext.current
    val gso = GoogleSignInModule.provideGoogleSignInOptions(context)
    val mGoogleSignInClient = GoogleSignInModule.provideGoogleSignInClient(context, gso)

    val signInLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                loginViewModel.login(result) {
                    navController.navigate(TodayQuestScreen.Home.name) {
                        popUpTo(0)
                    }
                }
            }
        }

    val onClickLoginButton = { signInLauncher.launch(mGoogleSignInClient.signInIntent) }
    var isLoginPage by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TodayQuestAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null && currentScreen != TodayQuestScreen.Home,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = TodayQuestScreen.Home.name,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(route = TodayQuestScreen.Home.name) {
                if(!loginUiState.isUserLoggedIn && !isLoginPage) {
                    isLoginPage = true
                    navController.navigate(TodayQuestScreen.Login.name) {
                        popUpTo(0)
                    }
                } else {
                    HomeScreen(
                        loginUiState = loginUiState,
                        onLogout = {
                            loginViewModel.logout()
                            isLoginPage = false
                        },
                    )
                }
            }
            composable(route = TodayQuestScreen.Login.name) {
                LoginScreen(
                    onClickLoginButton = onClickLoginButton,
                    loginUiState = loginUiState,
                )
            }
        }
    }

}