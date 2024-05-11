package com.example.wifitracker.ui.wifi.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.WifiPassword
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.example.wifitracker.ui.theme.AppColor
import com.example.wifitracker.ui.wifi.data.Routes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import com.example.wifitracker.R
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition


@Composable
fun ScreenSeeker(navHost: NavHostController) {

    Column(
        Modifier
            .fillMaxSize()
            .background(AppColor.background)
    ) {
        TopBarSeeker(navHost)
        Divider(Modifier.fillMaxWidth(), color = Color.White)
        BodySeeker(navHost)

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarSeeker(navHost: NavHostController) {
    TopAppBar(
        title = { Text(text = "Net-Seeker", fontSize = 25.sp, color = AppColor.letter) },
        colors = TopAppBarDefaults.smallTopAppBarColors(AppColor.background),
        navigationIcon = { NavigationSeeker(navHost) }
    )
}

@Composable
fun NavigationSeeker(navHost: NavHostController) {
    Icon(
        imageVector = Icons.Filled.ArrowBackIosNew,
        contentDescription = "Regresar",
        tint = AppColor.letter,
        modifier = Modifier.clickable { navHost.navigate(Routes.ScreenMain.route) }
    )
}

@Composable
fun BodySeeker(navHost: NavHostController) {

    ConstraintLayout(Modifier.fillMaxSize()) {
        val (lottie, switchScanning, network, rvNetwork) = createRefs()
        LottieSeeker(
            Modifier
                .padding(top = 20.dp)
                .constrainAs(lottie) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)

                })

        SwitchScanning(
            Modifier
                .padding(top = 20.dp)
                .constrainAs(switchScanning) {
                    top.linkTo(lottie.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })

        NetworkView(
            Modifier
                .padding(start = 22.dp)
                .constrainAs(network) {
                    start.linkTo(parent.start)
                    top.linkTo(switchScanning.bottom)
                })

        RecyclerNetworks(
            Modifier
                .padding(10.dp)
                .constrainAs(rvNetwork) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(network.bottom)
                }, navHost
        )


    }

}

@Composable
fun RecyclerNetworks(modifier: Modifier, navHost: NavHostController) {

    val rvState = rememberLazyListState()
    LazyColumn(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center, state = rvState
    ) {
        items(7) {
            itemWifi(navHost)
        }
    }
}


@Composable
fun itemWifi(navHost: NavHostController) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(10.dp),
        colors = CardDefaults.cardColors(AppColor.button),
        elevation = CardDefaults.cardElevation(5.dp)
    ) {
        Row(Modifier.padding(5.dp)) {
            IconWifi(
                Modifier.weight(1f)
            )
            IconGreenWifi(
                Modifier.weight(0.5f)
            )
            WifiName(
                Modifier.weight(5f)
            )
            ArrowBackIcon(
                Modifier.weight(1f), navHost
            )

        }
    }
}

@Composable
fun ArrowBackIcon(modifier: Modifier, navHost: NavHostController) {

    var dialogState by remember {
        mutableStateOf(false)
    }
    Icon(
        imageVector = Icons.Filled.ChevronRight,
        contentDescription = "Details",
        tint = Color.White, modifier = modifier
            .size(35.dp)
            .clickable { dialogState = true }
    )

    if (dialogState) {
        InfoDialog(onDismiss = { !dialogState }, navHost)


    }
}


@Composable
fun InfoDialog(
    onDismiss: () -> Unit,
    navHost: NavHostController
) {
    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = false
        )
    ) {
        Box(
            modifier = Modifier
                .height(200.dp)
        ) {
            Column(
                modifier = Modifier
            ) {
                Spacer(modifier = Modifier.height(35.dp))
                Box(
                    modifier = Modifier
                        .height(80.dp)
                        .background(
                            color = AppColor.alertDialog,
                            shape = RoundedCornerShape(10.dp) // Solo necesitas un solo valor para todos los bordes
                        )
                        .border(
                            width = 1.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(10.dp) // Asegúrate de usar la misma forma que en el fondo
                        )
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        ButtonDetail(navHost)
                        Spacer(modifier = Modifier.size(5.dp))
                        ButtonAttack(navHost)
                    }
                }
            }
            HeaderImage(
                modifier = Modifier
                    .size(50.dp)
                    .align(Alignment.TopCenter)
                    .border(1.dp, Color.Black, shape = CircleShape)
            )

        }
    }
}

@Composable
fun HeaderImage(modifier: Modifier) {
    Image(
        painter = painterResource(id = R.drawable.pirata),
        contentDescription = "piarat",
        modifier = modifier.clip(CircleShape)

    )
}

@Composable
fun ButtonDetail(navHost: NavHostController) {
    OutlinedButton(
        onClick = { navHost.navigate(Routes.ScreenRouterDetails.route) },
        colors = ButtonDefaults.buttonColors(AppColor.buttonDetail),

        ) {
        Text(text = "DETAILS", fontSize = 18.sp, color = AppColor.letter)
    }
}

@Composable
fun ButtonAttack(navHost: NavHostController) {
    OutlinedButton(
        onClick = { navHost.navigate(Routes.ScreenRouterAttack.route) },
        colors = ButtonDefaults.buttonColors(AppColor.buttonAttack),

        ) {
        Text(text = "ATTACK", fontSize = 18.sp, color = AppColor.letter)
    }
}

@Composable
fun WifiName(modifier: Modifier) {
    Text(
        text = "Claro_pre2",
        fontSize = 20.sp,
        color = AppColor.letter,
        modifier = modifier.padding(start = 8.dp, top = 5.dp)
    )
}


@Composable
fun IconGreenWifi(modifier: Modifier) {
    Icon(
        imageVector = Icons.Filled.Circle,
        contentDescription = "IconWifi",
        tint = AppColor.IconStatus, modifier = modifier
            .height(25.dp)
            .padding(top = 8.dp)

    )
}

@Composable
fun IconWifi(modifier: Modifier) {
    Icon(
        imageVector = Icons.Filled.WifiPassword,
        contentDescription = "Wifi",
        modifier = modifier
            .size(27.dp)
            .padding(top = 3.dp), tint = Color.White
    )
}

@Composable
fun NetworkView(modifier: Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Networks", fontSize = 18.sp, color = AppColor.letter)
        CircularProgressIndicator(
            color = Color(0XFF38B82C),
            strokeWidth = 2.dp,
            modifier = Modifier
                .size(25.dp)
                .padding(start = 10.dp, top = 5.dp)
        )
    }

}

@Composable
fun SwitchScanning(modifier: Modifier) {
    Card(
        modifier = modifier
            .padding(10.dp)
            .width(215.dp),
        elevation = CardDefaults.elevatedCardElevation(5.dp),
        border = BorderStroke(
            2.dp,
            Color.Gray
        ),
        colors = CardDefaults.cardColors(Color.White),
        shape = MaterialTheme.shapes.small
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextSwitch("Start Scanning")
            Spacer(modifier = Modifier.size(20.dp))
            SwitchSeeker()
        }

    }
}

@Composable
fun SwitchSeeker() {
    var switchState by remember {
        mutableStateOf(false)
    }
    Switch(
        checked = switchState,
        onCheckedChange = { switchState = !switchState },
        colors = SwitchDefaults.colors(
            uncheckedThumbColor = Color.White,
            uncheckedBorderColor = Color.Gray,
            checkedThumbColor = Color.White,
            checkedTrackColor = AppColor.IconStatus
        )
    )
}

@Composable
fun TextSwitch(scanningTitle: String) {
    Text(
        text = scanningTitle,
        fontSize = 20.sp,
        color = Color.Black,
        modifier = Modifier.padding(start = 5.dp)
    )
}

@Composable
fun LottieSeeker(modifier: Modifier) {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.radar)
    )
    val preloaderProgress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true
    )
    Box(modifier = modifier.height(150.dp)) {
        LottieAnimation(
            composition = composition,
            progress = preloaderProgress,modifier=Modifier.size(200.dp)
        )
    }


}

