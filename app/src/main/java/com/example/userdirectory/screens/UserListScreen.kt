package com.example.userdirectory.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.userdirectory.api.ApiState
import com.example.userdirectory.dataClass.User
import com.example.userdirectory.navigations.Routes
import com.example.userdirectory.viewModel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListScreen(navController: NavController, viewModel: UserViewModel) {
    LaunchedEffect(Unit) {
        if (viewModel.users.value is ApiState.Loading) {
            viewModel.fetchUsers()
        }
    }
    val apiState = viewModel.users.collectAsState().value

    when (apiState) {
        is ApiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is ApiState.Error -> {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
            ) {
                Text(
                    text = "Error: Unable to fetch users.\nPlease Check your Internet connection and try again.",
                    color = Color.Red,
                    textAlign = TextAlign.Center
                )
            }
        }

        is ApiState.Success -> {
            var userList: List<User> = viewModel.filteredList.collectAsState().value

            var search = viewModel.searchQuery.collectAsState().value

            Column(
                modifier = Modifier.statusBarsPadding()
            ) {

                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "User Directory",
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
                    value = search,
                    onValueChange = {
                        viewModel.search(it)
                    },
                    label = { Text(text = "Search") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedBorderColor = Color.Gray,
                        focusedLabelColor = Color.Gray,
                        cursorColor = Color.Gray
                    ),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search Icon"
                        )
                    },
                    trailingIcon = {
                        if (search.isNotEmpty()) {
                            IconButton(onClick = { viewModel.search("") }) {
                                Icon(
                                    imageVector = Icons.Filled.Clear,
                                    contentDescription = "Clear"
                                )
                            }
                        }
                    }
                )

                Box(modifier = Modifier.fillMaxSize())
                {
                    LazyColumn(
                        modifier = Modifier
                            .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
                    ) {
                        items(userList) { user ->
                            UserCard(user = user, onClick = {
                                viewModel.saveSelectedUser(user)
                                navController.navigate(Routes.UserDetailsScreen.route)
                            })
                        }
                    }

                    FloatingActionButton(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(end = 25.dp, bottom = 30.dp),
                        onClick = {
                            navController.navigate(Routes.UserCreateScreen.route)
                        },
                        content = {
                            Icon(
                                imageVector = Icons.Filled.AddCircle,
                                contentDescription = "Create User"
                            )
                        }
                    )
                }
            }
        }
    }
}

