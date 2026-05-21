package com.example.youthspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.youthspace.navigation.AppNavigation
import com.example.youthspace.ui.theme.YouthSpaceTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {

            YouthSpaceTheme {

                val navController = rememberNavController()

                AppNavigation(navController = navController)
            }
        }
    }
}