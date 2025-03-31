package com.example.userdirectory.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.userdirectory.dataClass.User
import com.example.userdirectory.reusablecomposables.detailsComposable

@Composable
fun UserCard(
    user: User,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(bottom = 8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        colors = CardDefaults.cardColors(Color.White)

    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                Modifier
                    .padding(8.dp)
                    .fillMaxWidth(0.9F)
            ) {
                detailsComposable("Name: ", user.name)
                detailsComposable("Email: ", user.email)

                user.company?.let { company ->
                    detailsComposable("Company Name: ", company.name)
                }
            }


            Icon(
                imageVector = Icons.Filled.KeyboardArrowRight,
                contentDescription = "KeyboardArrowRight Icon",
            )

        }

    }
}