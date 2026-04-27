package com.example.digitalplannertime

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
    val repo = remember { Repository(ctx) }

    // Hold user input as state so UI updates as the user types
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Small helper for toasts so calls are concise
    fun toast(msg: String) = Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Digital Planner",
                    style = MaterialTheme.typography.headlineSmall
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = "Log in or create an account to continue",
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(Modifier.height(20.dp))

                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Username") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(12.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            val cleanUsername = username.trim()
                            val cleanPassword = password.trim()

                            if (cleanUsername.isBlank() || cleanPassword.isBlank()) {
                                toast("Enter a username and password")
                                return@Button
                            }

                            if (repo.validateUser(cleanUsername, cleanPassword)) {
                                toast("Login successful")
                                onLoggedIn()
                            } else {
                                toast("Invalid username or password")
                            }
                        }
                    ) {
                        Text("Log In")
                    }

                    OutlinedButton(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            val cleanUsername = username.trim()
                            val cleanPassword = password.trim()

                            if (cleanUsername.isBlank() || cleanPassword.isBlank()) {
                                toast("Enter a username and password")
                                return@OutlinedButton
                            }

                            if (repo.createUser(cleanUsername, cleanPassword)) {
                                toast("Account created")
                                onLoggedIn()
                            } else {
                                toast("Account could not be created")
                            }
                        }
                    ) {
                        Text("Create Account")
                    }
                }
            }
        }
    }
}