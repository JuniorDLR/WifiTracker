package com.example.wifitracker.ui.wifi.data

sealed class Routes(val route: String) {
    object ScreenMain : Routes("ScreenMain")
    object ScreenSeeker : Routes("ScreenSeeker")
    object ScreenSpeedTest : Routes("ScreenSpeedTest")
    object ScreenRouterDetails : Routes("ScreenRouterDetails")
    object ScreenRouterAttack : Routes("ScreenRouterAttack")
}
