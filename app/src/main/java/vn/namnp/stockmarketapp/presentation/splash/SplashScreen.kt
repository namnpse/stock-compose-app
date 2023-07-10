package vn.namnp.stockmarketapp.presentation.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.delay
import vn.namnp.stockmarketapp.presentation.destinations.ListCompaniesScreenDestination
import vn.namnp.stockmarketapp.ui.theme.Purple500
import vn.namnp.stockmarketapp.ui.theme.Purple700
import vn.namnp.stockmarketapp.R

@Composable
@Destination(start = true)
fun SplashScreen(
    navigator: DestinationsNavigator,
) {
    val degrees = remember { Animatable(0f) }

//    val systemUiController = rememberSystemUiController()
//    systemUiController.setStatusBarColor(
//        color = MaterialTheme.colors.statusBarColor
//    )

    LaunchedEffect(key1 = true) {
        degrees.animateTo(
            targetValue = 360f,
            animationSpec = tween(
                durationMillis = 1000,
                delayMillis = 200
            )
        )
        navigator.navigate(ListCompaniesScreenDestination) {
            navigator.popBackStack()
        }
    }
    Splash(degrees = degrees.value)
}

@Composable
fun Splash(degrees: Float) {
    val modifier = if (!MaterialTheme.colors.isLight) {
        Modifier.background(
            Brush.verticalGradient(listOf(Purple700, Purple500))
        )
    } else {
        Modifier.background(Color.Black)
    }
    Box(
        modifier = modifier
            .padding(80.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.rotate(degrees = degrees),
            painter = painterResource(id = R.drawable.bitcoin_logo),
            contentDescription = "Splash",
        )
    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    Splash(1f)
}