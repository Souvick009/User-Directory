package com.example.userdirectory

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import com.example.userdirectory.api.ApiState
import com.example.userdirectory.dataClass.User
import com.example.userdirectory.navigations.navigationController
import com.example.userdirectory.ui.theme.UserDirectoryTheme
import com.example.userdirectory.viewModel.UserViewModel

class MainActivity : ComponentActivity() {
    @SuppressLint("StateFlowValueCalledInComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            UserDirectoryTheme {
                navigationController()
            }
        }
    }
}