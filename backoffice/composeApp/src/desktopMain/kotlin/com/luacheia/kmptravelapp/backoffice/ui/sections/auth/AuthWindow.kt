package com.luacheia.kmptravelapp.backoffice.ui.sections.auth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import com.luacheia.kmptravelapp.backoffice.ui.theme.backgroundColor
import com.luacheia.kmptravelapp.presentation.utils.UiState
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AuthWindow(
    modifier: Modifier = Modifier,
    onClose: () -> Unit = {}
) {
    Window(
        onCloseRequest = onClose,
        title = "Login"
    ) {
        AuthScreen(
            viewModel = koinViewModel(),
            onClose = onClose
        )
    }
}

@Composable
fun AuthScreen(
    viewModel: AuthViewModel,
    onClose: () -> Unit
) {
    var isLogin by remember { mutableStateOf(true) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val authState by viewModel.authState.collectAsState()

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                if (isLogin) "Login" else "Register",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
            Spacer(Modifier.height(16.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = backgroundColor,
                    unfocusedBorderColor = backgroundColor
                )
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = backgroundColor,
                    unfocusedBorderColor = backgroundColor
                )
            )
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = {
                    if (isLogin) viewModel.login(email, password)
                    else viewModel.register(email, password)
                },
                enabled = authState !is UiState.Loading,
                colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
            ) {
                Text(if (isLogin) "Login" else "Register")
            }
            Spacer(Modifier.height(8.dp))
            TextButton(onClick = { isLogin = !isLogin }) {
                Text(
                    text = if (isLogin) "No account? Register" else "Already have an account? Login",
                    color = backgroundColor
                )
            }
            Spacer(Modifier.height(8.dp))
            if (authState is UiState.Failure) {
                Text(
                    (authState as UiState.Failure).exception.message ?: "Login Generic Error",
                    color = Color.Red
                )
            }
            if (authState is UiState.Success) {
                LaunchedEffect(Unit) { onClose() }
            }
            Button(
                onClick = onClose,
                colors = ButtonDefaults.buttonColors(containerColor = backgroundColor)
            ) { Text("Close") }
        }
    }
}