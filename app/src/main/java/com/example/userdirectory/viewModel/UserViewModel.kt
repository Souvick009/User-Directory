package com.example.userdirectory.viewModel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.userdirectory.api.ApiState
import com.example.userdirectory.api.RetrofitInstance
import com.example.userdirectory.dataClass.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {

    private val _users = MutableStateFlow<ApiState<List<User>>>(ApiState.Loading)
    val users: StateFlow<ApiState<List<User>>> = _users

    private val _selectedUser = MutableStateFlow<User?>(null)
    val selectedUser: StateFlow<User?> = _selectedUser

    private val _searchQuery = MutableStateFlow<String>("")
    val searchQuery: StateFlow<String> = _searchQuery

    val filteredList = combine(_users, _searchQuery) { state, query ->
        when (state) {
            is ApiState.Success<List<User>> -> {
                if (query.isEmpty()) {
                    state.data
                } else {
                    state.data.filter { user ->
                        user.name.startsWith(
                            query, ignoreCase = true
                        )
                    }
                }
            }

            else -> emptyList()
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun saveSelectedUser(user: User?) {
        _selectedUser.value = user
    }

    fun search(query: String) {
        _searchQuery.value = query
    }

    fun fetchUsers() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.jsonPlaceholderApi.fetchUsers()
                if (response.isSuccessful) {
                    _users.value = ApiState.Success(response.body() ?: emptyList())
                } else {
                    _users.value = ApiState.Error("Error: ${response.errorBody()}")
                }
            } catch (exception: Exception) {
                _users.value = ApiState.Error(exception.message.toString())
            }
        }
    }

    fun createUser(context: Context, user: User, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.reqResApi.createUser(user)
                if (response.isSuccessful) {
                    val createdUser = response.body()!!
                    _users.update { state ->
                        when (state) {
                            is ApiState.Success -> {
                                val updatedList = state.data + createdUser
                                ApiState.Success(updatedList)
                            }

                            else -> {
                                ApiState.Success(listOf(createdUser))
                            }
                        }
                    }

                    onSuccess()
                } else {
                    Toast.makeText(
                        context, "User Creation Failed\nPlease try again.",
                        Toast.LENGTH_LONG
                    ).show()
                    Log.e("CreateUser", "Failed to create user: ${response.errorBody()}")
                }
            } catch (exception: Exception) {
                Toast.makeText(
                    context, "Check your Internet connection\nand please try again.",
                    Toast.LENGTH_LONG
                ).show()
                Log.e("CreateUser", "Failed to create user: ${exception.message}")
            }
        }
    }
}