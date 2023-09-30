package com.priyank.messagingappbranch.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import java.security.PrivateKey

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onUpdateUserName: (String) -> Unit,
    onUpdatePassword: (String) -> Unit,
    onUpdateButtonClick: (String) -> Unit,
    navHostController: NavHostController,
    username: String,
    password: String,
    isButtonEnabled: Boolean,
    navigateToNextString: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(24.dp),
    ) {
        Text(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            text = "Sign In", fontSize = 40.sp,
        )

        Text(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            text = "Enter Your Username", fontSize = 20.sp,
        )
        TextField(
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(text = "Enter Your Username here") },
            value = username,
            onValueChange = {},
            textStyle = TextStyle(lineHeight = 1.sp, fontSize = 20.sp),
            colors = TextFieldDefaults.outlinedTextFieldColors(containerColor = Color.Transparent)
        )


        Text(
            modifier = Modifier
                .padding(top = 62.dp, bottom = 12.dp, start = 20.dp)
                .fillMaxWidth(),
            text = "Enter Your Password", fontSize = 20.sp,
        )
        TextField(
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(text = "Enter Your Password here") },
            value = password,
            onValueChange = {},
            textStyle = TextStyle(lineHeight = 1.sp, fontSize = 20.sp),
            colors = TextFieldDefaults.outlinedTextFieldColors(containerColor = Color.Transparent)
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp),
            enabled = isButtonEnabled,
            onClick = { /*TODO*/ }) {
            Text("Log In")

        }


    }
}


@Preview(showBackground = true)
@Composable
fun gg() {
    LoginScreen(
        navHostController = NavHostController(LocalContext.current),
        isButtonEnabled = true,
        username = "",
        password = "",
        navigateToNextString = false,
        onUpdateButtonClick = {},
        onUpdatePassword = {},
        onUpdateUserName = {}
    )
}