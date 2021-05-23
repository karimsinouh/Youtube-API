package com.example.youtubeapi.ui.main

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.youtubeapi.ui.theme.YoutubeAPITheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val vm by viewModels<MainViewModel>()

    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YoutubeAPITheme {

                val navController= rememberNavController()
                val currentScreen=remember{ mutableStateOf<Screen>(Screen.Videos) }

                window.statusBarColor= Color.parseColor("#ffffff")

                Surface(color = MaterialTheme.colors.background) {

                    BottomSheetScaffold(
                        sheetContent = {MainSheet()},
                        topBar = {
                            Column {

                                MainToolbar()

                                MainRow(currentScreen.value.position) {
                                    currentScreen.value=it
                                    navController.navigate(it.route)
                                }
                            }
                        },
                        sheetPeekHeight = 0.dp
                    ) {

                        MainNavHost(controller = navController,vm)

                    }
                }

            }
        }
    }


}
