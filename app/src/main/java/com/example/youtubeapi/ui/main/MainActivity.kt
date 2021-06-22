package com.example.youtubeapi.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.youtubeapi.R
import com.example.youtubeapi.data.Snippet
import com.example.youtubeapi.ui.theme.DrawerShape
import com.example.youtubeapi.ui.theme.SheetShape
import com.example.youtubeapi.ui.theme.YoutubeAPITheme
import com.example.youtubeapi.utils.isDarkMode
import com.example.youtubeapi.utils.setDarkMode
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val vm by viewModels<MainViewModel>()

    private lateinit var navController: NavHostController
    private lateinit var scope:CoroutineScope
    private lateinit var currentScreen:MutableState<Screen>
    private lateinit var darkTheme:MutableState<Boolean>
    @ExperimentalMaterialApi
    private lateinit var scaffoldState: BottomSheetScaffoldState
    private lateinit var uiController:SystemUiController

    @ExperimentalAnimationApi
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_YoutubeAPI)
        setContent {

            scope= rememberCoroutineScope()
            navController= rememberNavController()
            currentScreen=remember{ mutableStateOf(Screen.Videos) }
            scaffoldState= rememberBottomSheetScaffoldState()
            darkTheme=remember { mutableStateOf(false) }
            uiController= rememberSystemUiController()

            val bottomSheetSnippet = remember{
                mutableStateOf<Snippet?>(null)
            }

            //theming
            darkTheme.value=isDarkMode()

            YoutubeAPITheme(darkTheme.value) {

                uiController.setSystemBarsColor(
                    color = MaterialTheme.colors.background,
                    darkIcons = !darkTheme.value
                )

                Surface(color = MaterialTheme.colors.background) {



                    BottomSheetScaffold(
                        sheetContent = {MainSheet(bottomSheetSnippet.value)},
                        topBar = {TopBar()},
                        sheetPeekHeight = 0.dp,
                        drawerShape = DrawerShape,
                        drawerContent = { Drawer() },
                        drawerBackgroundColor = MaterialTheme.colors.background,
                        drawerContentColor = MaterialTheme.colors.onBackground,
                        scaffoldState = scaffoldState,
                        sheetShape = SheetShape,
                        sheetBackgroundColor = MaterialTheme.colors.background,
                        sheetContentColor = MaterialTheme.colors.onBackground
                    ) {
                        MainNavHost(navController,vm) {
                            bottomSheetSnippet.value=it
                            scope.launch {
                                scaffoldState.bottomSheetState.expand()
                            }
                        }
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

    @ExperimentalMaterialApi
    @Composable
    private fun TopBar() {
        currentScreen.value.let {
            if (
                it.route==Screen.Videos.route ||
                it.route==Screen.Playlists.route ||
                it.route==Screen.WatchLater.route
            )
                Column {

                    MainToolbar(
                        onNavigationClick = {
                            scope.launch {
                                scaffoldState.drawerState.open()
                            }
                        },
                        onSearchClick = {
                            navController.navigate(Screen.Search.route)
                        })

                    MainRow(it.position!!) {destination->
                        navigate(destination)
                    }
                }
        }
    }


    @ExperimentalMaterialApi
    @ExperimentalAnimationApi
    @Composable
    private fun Drawer(){
        MainDrawer(
            darkMode = darkTheme.value,
            selectedScreenRoute = currentScreen.value.route,
            onDarkModeChanges = {
                setDarkTheme(it)
            },
            onNavigate = {
                navigate(it)
                scope.launch {
                    scaffoldState.drawerState.close()
                }
            },
            playlists = vm.playlistsState.items.value,
            onPlaylistClick = {
                navController.navigate("viewPlaylist/$it"){
                    launchSingleTop=true
                }
            }
        )
    }

    private fun setDarkTheme(value:Boolean){
        darkTheme.value=value
        setDarkMode(value)
    }

    private fun navigate(screen:Screen){
        navController.navigate(screen.route){
            popUpTo(Screen.Videos.route)
            launchSingleTop=true
        }
    }


}
