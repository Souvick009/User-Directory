package com.example.userdirectory.navigations

sealed class Routes(val route: String) {
    object UserListScreen : Routes("UserListScreen")
    object UserDetailsScreen : Routes("UserDetailsScreen")
    object UserCreateScreen : Routes("UserCreateScreen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}