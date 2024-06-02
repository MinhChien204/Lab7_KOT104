package learn.fpoly.lab7.Bai3

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import learn.fpoly.lab7.Bai2.Screen
import learn.fpoly.lab7.R

@Composable
fun LoginScreen(navController: NavController) {
    val loginViewModel: LoginViewModel = viewModel()
    LoginCard(navController, loginViewModel)
}

@Composable
fun LoginCard(navController: NavController, loginViewModel: LoginViewModel) {
    val snackbarHostState = remember { SnackbarHostState() }
    HandleLoginState(snackbarHostState, loginViewModel, navController)
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        LoginForm(loginViewModel, paddingValues)
    }
}

@Composable
fun HandleLoginState(
    snackbarHostState: SnackbarHostState,
    loginViewModel: LoginViewModel,
    navController: NavController
) {
    val isAuthenticated by loginViewModel.isAuthenticated.observeAsState()
    LaunchedEffect(isAuthenticated) {
        when (isAuthenticated) {
            true.toString() -> {
                navController.navigate(Screen.MOVIE_SCREEN.route) {
                    popUpTo(Screen.LOGIN.route) { inclusive = true }
                }
            }
            false.toString() -> {
                snackbarHostState.showSnackbar(
                    message = "Invalid username or password.",
                    duration = SnackbarDuration.Short
                )
                loginViewModel.resetAuthentication()
            }
            null -> {}
        }
    }
}

@Composable
fun LoginForm(
    loginViewModel: LoginViewModel,
    paddingValues: PaddingValues
) {
    val usernameState by loginViewModel.username.observeAsState("")
    val rememberMeState by loginViewModel.rememberMe.observeAsState(false)
    var username by remember { mutableStateOf(usernameState) }
    var password by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(rememberMeState) }
    val isLoginEnabled = username.isNotBlank() && password.isNotBlank()
    LaunchedEffect(usernameState, rememberMeState) {
        username = usernameState
        rememberMe = rememberMeState
        Log.d("PAM", "LoginForm: username $usernameState rememberMeState $rememberMeState")
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray),
        contentAlignment = Alignment.Center,
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .padding(paddingValues),
            backgroundColor = Color.White,
            elevation = 4.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(36.dp, 24.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "Logo",
                )
                Spacer(modifier = Modifier.height(20.dp))
                UsernameField(username, onUsernameChange = { username = it })
                PasswordField(password, onPasswordChange = { password = it })
                RememberMeSwitch(rememberMe as Boolean) { isChecked -> rememberMe = isChecked }
                Spacer(modifier = Modifier.height(16.dp))
                LoginButton(isLoginEnabled) {
                    loginViewModel.login(
                        username,
                        password,
                        rememberMe as Boolean
                    )
                }
            }
        }
    }
}

@Composable
fun UsernameField(username: String, onUsernameChange: (String) -> Unit) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = username,
        onValueChange = onUsernameChange,
        label = { Text("Username") },
    )
}

@Composable
fun PasswordField(password: String, onPasswordChange: (String) -> Unit) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = password,
        onValueChange = onPasswordChange,
        label = { Text("Password") },
        visualTransformation = PasswordVisualTransformation()
    )
}

@Composable
fun LoginButton(isEnabled: Boolean, onLoginClick: () -> Unit) {
    Button(
        onClick = onLoginClick,
        enabled = isEnabled,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (isEnabled) Color.DarkGray else Color.LightGray,
            contentColor = Color.White
        ),
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(40.dp)
    ) {
        Text("Login", fontWeight = FontWeight.Bold)
    }
}

@Composable
fun RememberMeSwitch(rememberMe: Boolean, onCheckedChange: (Boolean) -> Unit) {
    var isChecked by remember { mutableStateOf(rememberMe) }
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Switch(
            checked = isChecked,
            onCheckedChange = {
                isChecked = it
                onCheckedChange(it)
            },
            modifier = Modifier
                .scale(0.75f)
                .padding(0.dp),
        )
        Text("Remember Me?", modifier = Modifier.padding(start = 12.dp))
    }
}
