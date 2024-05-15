package com.example.wifitracker.ui.wifi.ui

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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


@Composable
fun ScreenMain(navHost: NavHostController) {

    Column(
        Modifier
            .fillMaxSize()
            .background(AppColor.background)
    ) {
        BodyMain(navHost)
    }

}

@Composable
fun BodyMain(navHost: NavHostController) {

    ConstraintLayout(
        modifier = Modifier.fillMaxSize(),

        ) {
        val (name, logo, buttonHunter, buttonSpeedTest, creator) = createRefs()
        NameApp("Wifi-Tracker",
            Modifier
                .padding(top = 10.dp)
                .constrainAs(name) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })
        LogoApp(
            Modifier
                .padding(top = 50.dp)
                .constrainAs(logo) {
                    top.linkTo(name.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })

        ButtonHunter(
            Modifier
                .padding(bottom = 10.dp, top = 15.dp)
                .constrainAs(buttonHunter) {
                    top.linkTo(logo.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }, navHost
        )

        ButtonSpeedTest(Modifier.constrainAs(buttonSpeedTest) {
            top.linkTo(buttonHunter.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }, navHost)

        CreatorApp("Create by Junaxer",
            Modifier
                .padding(bottom = 10.dp)
                .constrainAs(creator) {

                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                })

    }

}

@Composable
fun CreatorApp(creator: String, modifier: Modifier) {
    Text(
        text = creator,
        fontSize = 15.sp,
        color = AppColor.letter,
        modifier = modifier,
        fontFamily = FontFamily.Serif
    )
}

@Composable
fun ButtonSpeedTest(modifier: Modifier, navHost: NavHostController) {
    val contexto = LocalContext.current
    OutlinedButton(
        onClick = {
            Toast.makeText(contexto, "Aun no esta disponible esta opcion", Toast.LENGTH_LONG).show()
            /*
            * navHost.navigate(Routes.ScreenSpeedTest.route)*/
        },
        modifier = modifier,
        shape = RectangleShape,
        colors = ButtonDefaults.buttonColors(AppColor.button),
        border = BorderStroke(1.dp, Color.White)
    ) {
        Text(
            text = "WIFI SPEED TEST",
            fontSize = 20.sp,
            color = AppColor.letter, fontFamily = FontFamily.Serif
        )
    }
}

@Composable
fun ButtonHunter(modifier: Modifier, navHost: NavHostController) {


    OutlinedButton(
        onClick = { navHost.navigate(Routes.ScreenSeeker.route) },
        modifier = modifier,
        shape = RectangleShape,
        colors = ButtonDefaults.buttonColors(AppColor.button),
        border = BorderStroke(1.dp, Color.White)
    ) {
        Text(
            text = "WIFI HUNTER",
            fontSize = 20.sp,
            color = AppColor.letter, fontFamily = FontFamily.Serif


        )
    }
}

@Composable
fun LogoApp(modifier: Modifier) {
    Image(
        painter = painterResource(id = R.drawable.logo_app),
        contentDescription = "logoApp",
        modifier = modifier.size(300.dp)
    )
}

@Composable
fun NameApp(title: String, modifier: Modifier) {
    Text(
        text = title,
        fontSize = 25.sp,
        color = AppColor.letter,
        modifier = modifier,
        fontFamily = FontFamily.Serif
    )
}
