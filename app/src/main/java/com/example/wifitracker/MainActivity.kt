package com.example.wifitracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.wifitracker.ui.wifi.data.Routes
import com.example.wifitracker.ui.wifi.ui.ScreenMain
import com.example.wifitracker.ui.wifi.ui.ScreenRouterAttack
import com.example.wifitracker.ui.wifi.ui.ScreenRouterDetails
import com.example.wifitracker.ui.wifi.ui.ScreenSeeker
import com.example.wifitracker.ui.wifi.ui.ScreenSpeedTest


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navHost = rememberNavController()
            NavHost(navController = navHost, startDestination = Routes.ScreenMain.route) {
                composable(Routes.ScreenMain.route) { ScreenMain(navHost) }
                composable(Routes.ScreenSeeker.route) { ScreenSeeker(navHost) }
                composable(Routes.ScreenRouterDetails.route) { ScreenRouterDetails(navHost) }
                composable(Routes.ScreenRouterAttack.route) { ScreenRouterAttack(navHost) }
                composable(Routes.ScreenSpeedTest.route) { ScreenSpeedTest(navHost) }
            }

        }
    }
}

