package com.example.youtubeapi.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.youtubeapi.ui.theme.DrawerShape
import com.example.youtubeapi.ui.theme.YoutubeAPITheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val vm by viewModels<MainViewModel>()

    @ExperimentalAnimationApi
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val navController= rememberNavController()
            val currentScreen=remember{ mutableStateOf<Screen>(Screen.Videos) }
            val scaffoldState= rememberBottomSheetScaffoldState()
            val scope= rememberCoroutineScope()
            val darkTheme=remember { mutableStateOf(false) }



            darkTheme.value= isSystemInDarkTheme()


            YoutubeAPITheme(darkTheme.value) {

                Surface(color = MaterialTheme.colors.background) {

                window.statusBarColor=MaterialTheme.colors.surface.toArgb()

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
                                        navController.navigate(it.route){
                                            popUpTo(Screen.Videos.route)
                                            launchSingleTop=true
                                        }
                                    }
                                }
                        },
                        sheetPeekHeight = 0.dp,
                        drawerShape = DrawerShape,
                        drawerContent = {
                            MainDrawer(
                                darkMode = darkTheme.value,
                                selectedScreenRoute = currentScreen.value.route,
                                onDarkModeChanges = {
                                    darkTheme.value=false
                                                    },
                                onNavigate = {
                                    currentScreen.value=it
                                    navController.navigate(it.route){
                                        popUpTo(Screen.Videos.route)
                                        launchSingleTop=true
                                    }
                                    scope.launch {
                                        scaffoldState.drawerState.close()
                                    }
                                },
                                playlists = vm.playlistsState.items.value
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
