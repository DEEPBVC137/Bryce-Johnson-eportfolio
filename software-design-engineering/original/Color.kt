package com.example.digitalplannertime

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.digitalplannertime.data.Repository

// Login screen connects UI to the Repository for user validation and creation
@Composable
fun LoginScreen(
    onLoggedIn: () -> Unit // called after a successful login or account creation
) {
    val ctx = LocalContext.current
    // Repository provides simple access to SQLite and utility functions
    val repo = remember { Repository(ctx) }

    // Hold user input as state so UI updates as the user types
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Small helper for toasts so calls are concise
    fun toast(msg: String) = Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show()

    // Layout for the login form
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Digital Planner", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))

        // Username text field
        OutlinedTextField(
            value = username,
            onValueChange = { username = it.trim() },
            label = { Text("Username") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        // Password text field
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        // Action buttons for login and account creation
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(
                modifier = Modifier.weight(1f),
                onClick = {
                    // Validate against users table
                    if (repo.validateUser(username, password)) onLoggedIn() else toast("Invalid login")
                }
            ) { Text("Log in") }

            OutlinedButton(
                modifier = Modifier.weight(1f),
                onClick = {
                    // Create a new user then continue to dashboard
                    if (repo.createUser(username, password)) {
                        toast("Account created")
                        onLoggedIn()
                    } else toast("User exists or error")
                }
            ) { Text("Create account") }
        }
    }
}