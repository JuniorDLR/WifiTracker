package com.example.wifitracker.ui.wifi.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.example.wifitracker.R
import com.example.wifitracker.ui.theme.AppColor
import com.example.wifitracker.ui.wifi.data.Routes
import com.example.wifitracker.ui.wifi.viewmodel.WifiViewModel


@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun ScreenRouterDetails(
    navHost: NavHostController,
    argument: String,
    wifiViewModel: WifiViewModel
) {

    Column(
        Modifier
            .fillMaxSize()
            .background(AppColor.background)
    ) {

        TopBarDetails(navHost)
        Divider(Modifier.fillMaxWidth(), color = Color.White)
        BodyDetails(argument, wifiViewModel)
    }
}


@Composable
fun BodyDetails(argument: String, wifiViewModel: WifiViewModel) {
    ConstraintLayout(
        Modifier
            .fillMaxSize()
            .background(AppColor.background)
    ) {
        val (imageRouter, ssid, bssid, security, frequency, level, divider0, divider1, divider2, divider3, divider4, divider5) = createRefs()

        val getObjectResult = wifiViewModel.returnSSID(argument)

        ImageRouter(
            Modifier
                .constrainAs(imageRouter) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })

        if (getObjectResult != null) {
            Divider(Modifier.constrainAs(divider0) {
                top.linkTo(imageRouter.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })
            InfoWifi("Name:", getObjectResult.SSID, Modifier.constrainAs(ssid) {
                top.linkTo(divider0.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })

            Divider(Modifier.constrainAs(divider1) {
                top.linkTo(ssid.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })

            InfoWifi("MAC:", getObjectResult.BSSID, Modifier.constrainAs(bssid) {
                top.linkTo(divider1.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })

            Divider(Modifier.constrainAs(divider2) {
                top.linkTo(bssid.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })

            InfoWifi("Security: ", getObjectResult.capabilities, Modifier.constrainAs(security) {
                top.linkTo(divider2.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })

            Divider(Modifier.constrainAs(divider3) {
                top.linkTo(security.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })

            InfoWifi(
                "Frequency:",
                "${getObjectResult.frequency} MHz",
                Modifier.constrainAs(frequency) {
                    top.linkTo(divider3.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })

            Divider(Modifier.constrainAs(divider4) {
                top.linkTo(frequency.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })

            InfoWifi(
                "Signal level:",
                "${getObjectResult.level} dBm",
                Modifier.constrainAs(level) {
                    top.linkTo(divider4.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })

            Divider(Modifier.constrainAs(divider5) {
                top.linkTo(level.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })

        }
    }


}


@Composable
fun InfoWifi(type: String, info: String, modifier: Modifier) {
    Row(modifier = modifier.padding(5.dp)) {
        Text(text = type, fontSize = 16.sp, color = AppColor.letter)
        Text(text = info, fontSize = 15.sp, color = AppColor.letter)

    }
}


@Composable
fun ImageRouter(modifier: Modifier) {
    Image(
        painter = painterResource(id = R.drawable.router),
        contentDescription = "Router",
        modifier = modifier.size(220.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarDetails(navHost: NavHostController) {

    TopAppBar(
        title = { Text(text = "Router details", fontSize = 25.sp, color = AppColor.letter) },
        navigationIcon = { NavigationDetails(navHost) },
        colors = TopAppBarDefaults.smallTopAppBarColors(AppColor.background)
    )
}

@Composable
fun NavigationDetails(navHost: NavHostController) {
    Icon(
        imageVector = Icons.Filled.ArrowBackIosNew,
        contentDescription = "Regresar",
        tint = AppColor.letter,
        modifier = Modifier.clickable { navHost.navigate(Routes.ScreenSeeker.route) }
    )
}
