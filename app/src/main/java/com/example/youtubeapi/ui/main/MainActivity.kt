package com.example.youtubeapi.ui.main

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.youtubeapi.ui.theme.DrawerShape
import com.example.youtubeapi.ui.theme.YoutubeAPITheme
import com.example.youtubeapi.utils.isDarkMode
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val vm by viewModels<MainViewModel>()

    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val navController= rememberNavController()
            val currentScreen=remember{ mutableStateOf<Screen>(Screen.Videos) }
            val scaffoldState= rememberBottomSheetScaffoldState()
            val scope= rememberCoroutineScope()
            val darkMode= mutableStateOf(false)



            darkMode.value=isDarkMode()

            window.statusBarColor=if (darkMode.value)
                Color.parseColor("#121212")
            else
                Color.parseColor("#ffffff")

            YoutubeAPITheme(darkMode.value) {

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

                                    MainToolbar(
                                        onNavigationClick = {
                                                            scope.launch {
                                                                scaffoldState.drawerState.open()
                                                            }
                                        },
                                        onSearchClick = {})
                                    MainRow(currentScreen.value.position!!) {
                                        currentScreen.value=it
                                        navController.navigate(it.route)
                                    }
                                }
                        },
                        sheetPeekHeight = 0.dp,
                        drawerShape = DrawerShape,
                        drawerContent = {
                            MainDrawer(
                                darkMode = darkMode.value,
                                selectedScreenRoute = currentScreen.value.route,
                                onDarkModeChanges = {
                                    darkMode.value=true
                                    Log.d("wtf","clicked")
                                                    },
                                onNavigate = {
                                    currentScreen.value=it
                                    navController.navigate(it.route)
                                    scope.launch {
                                        scaffoldState.drawerState.close()
                                    }
                                }
                            )
                                        },
                        drawerBackgroundColor = MaterialTheme.colors.background,
                        drawerContentColor = MaterialTheme.colors.onBackground,
                        scaffoldState = scaffoldState
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

}
