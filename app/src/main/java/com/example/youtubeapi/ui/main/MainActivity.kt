package com.example.youtubeapi.ui.main

import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
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

                //color
                window.statusBarColor=if (isSystemDarkMode())
                    Color.parseColor("#121212")
                else
                    Color.parseColor("#ffffff")



                Surface(color = MaterialTheme.colors.background) {

                    BottomSheetScaffold(
                        sheetContent = {MainSheet()},
                        topBar = {
                            if (
                                currentScreen.value.route==Screen.Videos.route ||
                                currentScreen.value.route==Screen.Playlists.route ||
                                currentScreen.value.route==Screen.WatchLater.route
                            )
                                Column {
                                    MainToolbar()
                                    MainRow(currentScreen.value.position!!) {
                                        navController.navigate(it.route)
                                    }
                                }
                        },
                        sheetPeekHeight = 0.dp
                    ) {

                        MainNavHost(navController,vm)

                    }

                    navController.addOnDestinationChangedListener { _, destination, _ ->
                        destination.route?.let {
                            currentScreen.value = findByRoute(it)
                        }
                    }
                }

            }
        }
    }

    private fun isSystemDarkMode():Boolean{
        val mode=resources.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK)
        return mode==Configuration.UI_MODE_NIGHT_YES
    }

}
