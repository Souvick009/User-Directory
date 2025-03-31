package com.example.userdirectory.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.util.PatternsCompat
import androidx.navigation.NavController
import com.example.userdirectory.dataClass.User
import com.example.userdirectory.viewModel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserCreateScreen(navController: NavController, viewModel: UserViewModel) {
    Column(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize()
    ) {

        var name by rememberSaveable { mutableStateOf("") }
        var email by rememberSaveable { mutableStateOf("") }
        var phone by rememberSaveable { mutableStateOf("") }

        var nameError = rememberSaveable { mutableStateOf<String?>(null) }
        var emailError = rememberSaveable { mutableStateOf<String?>(null) }
        var phoneError = rememberSaveable { mutableStateOf<String?>(null) }

        val context = LocalContext.current

        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = "Create User",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                )

            },
            colors = TopAppBarColors(
                containerColor = Color.Transparent,
                scrolledContainerColor = Color.White,
                navigationIconContentColor = Color.Black,
                titleContentColor = Color.Black,
                actionIconContentColor = Color.Black
            )
        )

        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
            },
            label = {
                Text("Name")
            },
            isError = (nameError.value != null),
            supportingText = { (nameError.value?.let { Text(it, color = Color.Red) }) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                focusedBorderColor = Color.Gray,
                focusedLabelColor = Color.Gray,
                unfocusedTextColor = Color.Black,
                unfocusedBorderColor = Color.Gray,
                unfocusedLabelColor = Color.Gray,
                errorTextColor = Color.Black,
                cursorColor = Color.Black
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 40.dp, end = 40.dp, bottom = 30.dp, top = 60.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
            },
            label = {
                Text("Email")
            },
            isError = (emailError.value != null),
            supportingText = { emailError.value?.let { Text(it, color = Color.Red) } },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                focusedBorderColor = Color.Gray,
                focusedLabelColor = Color.Gray,
                unfocusedTextColor = Color.Black,
                unfocusedBorderColor = Color.Gray,
                unfocusedLabelColor = Color.Gray,
                errorTextColor = Color.Black,
                cursorColor = Color.Black
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 40.dp, end = 40.dp, bottom = 30.dp)
        )

        OutlinedTextField(
            value = phone,
            onValueChange = {
                phone = it
            },
            label = {
                Text("Phone Number")
            },
            isError = (phoneError.value != null),
            supportingText = { phoneError.value?.let { Text(it, color = Color.Red) } },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                focusedBorderColor = Color.Gray,
                focusedLabelColor = Color.Gray,
                unfocusedTextColor = Color.Black,
                unfocusedBorderColor = Color.Gray,
                unfocusedLabelColor = Color.Gray,
                errorTextColor = Color.Black,
                cursorColor = Color.Black,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 40.dp, end = 40.dp, bottom = 30.dp)
        )

        Button(
            onClick = {
                if (
                    isNameValid(name, nameError) &&
                    isEmailValid(email, emailError) &&
                    isPhoneValid(phone, phoneError)
                ) {
                    viewModel.createUser(
                        context,
                        User(name = name.trim(), email = email.trim(), phone = phone.trim()),
                        {
                            Toast.makeText(context, "User is successfully created", Toast.LENGTH_SHORT).show()
                            navController.popBackStack()
                        }
                    )
                }
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
        ) {
            Text(text = "Save", fontSize = 15.sp)
        }
    }
}

fun isNameValid(name: String, nameError: MutableState<String?>): Boolean {
    if (name.isEmpty()) {
        nameError.value = "Name Field cannot be empty"
        return false
    } else {
        nameError.value = null
        return true
    }
}

fun isEmailValid(email: String, emailError: MutableState<String?>): Boolean {
    if (!PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()) {
        emailError.value = "Email format is invalid"
        return false
    } else {
        emailError.value = null
        return true
    }
}

fun isPhoneValid(phone: String, phoneError: MutableState<String?>): Boolean {
    if (phone.isEmpty()) {
        phoneError.value = "Enter the phone number"
        return false
    } else if (!Regex("^[0-9x\\-\\s]+$").matches(phone)) {
        phoneError.value = "Phone number is invalid"
        return false
    } else {
        phoneError.value = null
        return true
    }
}