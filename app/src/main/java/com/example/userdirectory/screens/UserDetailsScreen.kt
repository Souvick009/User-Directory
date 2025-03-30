package com.example.userdirectory.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.userdirectory.reusablecomposables.detailsComposable
import com.example.userdirectory.viewModel.UserViewModel

@Composable
fun UserDetailsScreen(navController: NavController, viewModel: UserViewModel) {

    val user = viewModel.selectedUser.collectAsState().value
    if (user == null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "User Not Found. Please try again.",
                color = Color.Red,
                textAlign = TextAlign.Center
            )
        }

        return
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.sweepGradient(
                    colors = listOf(
                        Color(0xFFffffff),
                        Color(0xFFE3E3E3)
                    )
                )
            ),
    ) {
        Card(
            colors = CardDefaults.cardColors(Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
            modifier = Modifier.widthIn(max = 350.dp)
        ) {
            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                detailsComposable("Name: ", user.name)
                user.username?.let { detailsComposable("Username: ", it) }
                detailsComposable("Email: ", user.email)
                detailsComposable("Phone: ", user.phone)
                user.website?.let { detailsComposable("Website: ", it) }
                user.address?.let {
                    detailsComposable(
                        "Address: ",
                        "${it.suite}, ${it.street}, ${it.city}, ${it.zipcode}"
                    )
                }
            }
        }
    }
}