package com.example.wifitracker


import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.wifitracker.ui.wifi.data.Routes
import com.example.wifitracker.ui.wifi.ui.ScreenMain
import com.example.wifitracker.ui.wifi.ui.ScreenRouterAttack
import com.example.wifitracker.ui.wifi.ui.ScreenRouterDetails
import com.example.wifitracker.ui.wifi.ui.ScreenSeeker
import com.example.wifitracker.ui.wifi.ui.ScreenSpeedTest
import com.example.wifitracker.ui.wifi.viewmodel.WifiViewModel


class MainActivity : ComponentActivity() {

    private val wifiViewModel: WifiViewModel by viewModels()
    private val wifiManaguer by lazy { applicationContext.getSystemService(WIFI_SERVICE) as WifiManager }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navHost = rememberNavController()

            NavHost(navController = navHost, startDestination = Routes.ScreenMain.route) {
                composable(Routes.ScreenMain.route) { ScreenMain(navHost) }
                composable(Routes.ScreenSeeker.route) {
                    ScreenSeeker(
                        navHost,
                        wifiViewModel,
                        wifiManaguer
                    )
                }
                composable("${Routes.ScreenRouterDetails.route}/{name}", arguments = listOf(
                    navArgument("name") {
                        type = NavType.StringType
                    }
                )) { navBackStackEntry ->
                    val argument = navBackStackEntry.arguments?.getString("name") ?: ""
                    ScreenRouterDetails(navHost, argument, wifiViewModel)
                }
                composable("${Routes.ScreenRouterAttack.route}/{name}", arguments = listOf(
                    navArgument("name") {
                        type = NavType.StringType
                    }
                )) { navBackStackEntry ->
                    val argument = navBackStackEntry.arguments?.getString("name") ?: ""
                    ScreenRouterAttack(navHost,argument,wifiViewModel)
                }
                composable(Routes.ScreenSpeedTest.route) { ScreenSpeedTest(navHost) }
            }

        }
    }
}

