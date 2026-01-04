package com.atul.doctorappointmentappui.feature.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateTo
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.atul.doctorappointmentappui.R // Make sure you have an icon in res/drawable
import kotlinx.coroutines.delay

@Composable
fun BrandedLoadingScreen(
    afterLoading: () -> Unit,
    loadingState: Boolean
) {
    // We will now control the visibility of the AnimatedAppIcon with the loadingState
    if (loadingState){
        AnimatedAppIcon()
    } else {
        // When loadingState becomes false, this will be called,
        // and the AnimatedAppIcon will disappear.
        afterLoading()
    }
}

@Composable
private fun AnimatedAppIcon() {
    // Animation state for scale
    val scale = remember { Animatable(1.0f) } // Start at full size

    // This effect now creates a continuous, looping animation.
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 1.15f, // Scale up slightly
            animationSpec = infiniteRepeatable(
                // The animation will take 1200ms to go from 1.0f to 1.15f
                animation = tween(durationMillis = 1200, easing = FastOutSlowInEasing),
                // It will then reverse and go from 1.15f back to 1.0f
                repeatMode = RepeatMode.Reverse
            )
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background), // Use your theme's background
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.appicon), // Your app's logo
            contentDescription = "App Logo",
            modifier = Modifier
                .size(150.dp)
                .scale(scale.value), // Apply the scale animation
        )
    }
}

// --- Preview ---

@Preview(showBackground = true)
@Composable
private fun BrandedLoadingScreenPreview() {
    // DoctorAppointmentAppUITheme {
    BrandedLoadingScreen(
        afterLoading = {},
        loadingState = true
    )
    // }
}
