package com.example.digitalplannertime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

private enum class Screen { Login, Dash, Sms }

class DigitalPlannerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                // rememberSaveable keeps the current screen across rotate and process death
                var screen by rememberSaveable { mutableStateOf(Screen.Login) }

                when (screen) {
                    Screen.Login -> {
                        LoginScreen(
                            onLoggedIn = { screen = Screen.Dash }
                        )
                        // Back on login exits as usual so no BackHandler here
                    }
                    Screen.Dash -> {
                        // Back on dash finishes the activity
                        BackHandler { finish() }
                        EventDashboardScreen(
                            onAskSmsPermission = { screen = Screen.Sms }
                        )
                    }
                    Screen.Sms -> {
                        // Back from permission screen returns to dash
                        BackHandler { screen = Screen.Dash }
                        SMSPermissionScreen(
                            onDone = { screen = Screen.Dash }
                        )
                    }
                }
            }
        }
    }
}
