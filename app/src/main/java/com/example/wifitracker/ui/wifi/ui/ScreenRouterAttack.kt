package com.example.wifitracker.ui.wifi.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.net.wifi.WifiNetworkSpecifier
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.example.wifitracker.R
import com.example.wifitracker.ui.theme.AppColor
import com.example.wifitracker.ui.wifi.data.Routes
import com.example.wifitracker.ui.wifi.viewmodel.WifiViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext


@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun ScreenRouterAttack(navHost: NavHostController, argument: String, wifiViewModel: WifiViewModel) {
    Column(
        Modifier
            .fillMaxSize()
            .background(AppColor.background)
    ) {
        TopBarAttack(navHost)
        Divider(Modifier.fillMaxWidth(), color = Color.White)
        BodyAttack(argument, wifiViewModel)
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun BodyAttack(ssid: String, wifiViewModel: WifiViewModel) {

    val password: String? by wifiViewModel.password.observeAsState(initial = "")

    ConstraintLayout(Modifier.fillMaxSize()) {
        val (nameRouter, process, input, button) = createRefs()

        NameRouterView(ssid,
            Modifier
                .padding(top = 20.dp)
                .constrainAs(nameRouter) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })

        ProcessView(
            Modifier
                .padding(top = 5.dp)
                .constrainAs(process) {
                    top.linkTo(nameRouter.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }

        )

        InputView(
            Modifier
                .padding(10.dp)
                .constrainAs(input) {
                    top.linkTo(process.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }, ssid, wifiViewModel
        )

        ButtoPassword(
            Modifier
                .padding(top = 5.dp)
                .constrainAs(button) {
                    top.linkTo(input.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }, password
        )
    }
}

@Composable
fun ButtoPassword(modifier: Modifier, password: String?) {
    val context = LocalContext.current
    OutlinedButton(
        onClick = {
            Toast.makeText(context, "Contraseña copiada: $password", Toast.LENGTH_LONG).show()
        },
        modifier = modifier,
        shape = RectangleShape,
        colors = ButtonDefaults.buttonColors(AppColor.button),
        border = BorderStroke(1.dp, Color.White)
    ) {
        Text(
            text = "COPY PASSWORD",
            fontSize = 20.sp,
            color = AppColor.letter, fontFamily = FontFamily.Serif


        )
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun InputView(modifier: Modifier, ssid: String, wifiViewModel: WifiViewModel) {

    var delayState by remember {
        mutableStateOf(false)
    }

    var claveWifi by remember {
        mutableStateOf<String?>(null)
    }
    Column(
        modifier = modifier
            .background(Color.Black)
            .fillMaxWidth()
            .height(150.dp)
            .padding(10.dp)
    ) {
        Text(
            text = "Process[1] Starting attack",
            fontSize = 18.sp,
            color = Color.Green,
            modifier = Modifier.padding(2.dp)
        )

        val context = LocalContext.current
        LaunchedEffect(Unit) {
            delay(3000)
            delayState = true

            withContext(Dispatchers.IO) {
                claveWifi = connectToWifi(ssid, listOf(""), context)
            }
        }
        if (delayState) {
            Text(
                text = "Process[2] Cracking password",
                fontSize = 18.sp,
                color = Color.Green,
                modifier = Modifier.padding(2.dp)
            )


            claveWifi?.let {
                Text(
                    text = "Process[3] Password: $it",
                    fontSize = 18.sp,
                    color = Color.Green,
                    modifier = Modifier.padding(2.dp)

                )
                wifiViewModel.changedPassword(it)
            }
        }
    }
}


@Composable
fun ProcessView(modifier: Modifier) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = painterResource(id = R.drawable.hacker),
            contentDescription = "hacker",
            modifier = Modifier
                .weight(1f)
                .height(140.dp)
        )
        LinearProgressIndicator(
            modifier = Modifier.weight(1f),
            color = Color.White,
            trackColor = AppColor.background
        )
        Image(
            painter = painterResource(id = R.drawable.router_process),
            contentDescription = "router",
            modifier = Modifier
                .weight(1f)
                .height(180.dp)
        )
    }
}

@Composable
fun NameRouterView(router: String, modifier: Modifier) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Filled.Circle,
            contentDescription = "Status",
            tint = AppColor.IconStatus, modifier = Modifier.padding(end = 5.dp)
        )
        Text(text = router, fontSize = 20.sp, color = AppColor.letter)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarAttack(navHost: NavHostController) {
    TopAppBar(
        title = { Text(text = "Attack router", fontSize = 25.sp, color = AppColor.letter) },
        navigationIcon = { NavigationAttack(navHost) },
        colors = TopAppBarDefaults.smallTopAppBarColors(AppColor.background)
    )
}

@Composable
fun NavigationAttack(navHost: NavHostController) {
    Icon(
        imageVector = Icons.Filled.ArrowBackIosNew,
        contentDescription = "Regresar",
        tint = AppColor.letter,
        modifier = Modifier.clickable { navHost.navigate(Routes.ScreenSeeker.route) }
    )
}


@RequiresApi(Build.VERSION_CODES.Q)
fun connectToWifi(networkSSID: String, networkPassKeys: List<String>, context: Context): String? {

    var foundPassword: String? = null

    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    for (password in networkPassKeys) {
        val networkRequest =
            NetworkRequest.Builder().addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .removeCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .addCapability(NetworkCapabilities.NET_CAPABILITY_TRUSTED).setNetworkSpecifier(
                    WifiNetworkSpecifier.Builder().setSsid(networkSSID).setWpa2Passphrase(password)
                        .build()
                ).build()


        val networkCallBck = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)

                foundPassword = password
                connectivityManager.unregisterNetworkCallback(this)
            }
        }

        connectivityManager.requestNetwork(networkRequest, networkCallBck)
        Thread.sleep(3000)

        if (foundPassword != null) {
            break
        }
    }
    return foundPassword
}