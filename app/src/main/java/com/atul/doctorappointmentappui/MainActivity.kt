package com.atul.doctorappointmentappui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.atul.doctorappointmentappui.core.viewmodel.MainViewModel
import com.atul.doctorappointmentappui.feature.home.MainScreen
import com.atul.doctorappointmentappui.navigatiion.AppNavGraph
import com.atul.doctorappointmentappui.ui.theme.DoctorAppointmentAppUITheme

class MainActivity : ComponentActivity() {
    private val vm by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
                val nav = rememberNavController()
                AppNavGraph(nav, vm)
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}