package com.example.userdirectory.reusablecomposables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight

@Composable
fun detailsComposable(text: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
//            .padding(vertical = 4.dp),
//        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = value,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
