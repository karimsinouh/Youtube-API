package com.example.youtubeapi.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.youtubeapi.ui.theme.PlaceholderColor
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.imageloading.ImageLoadState

@Composable
fun Placeholder(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PlaceholderColor)
    )
}

@Composable
fun Placeholder(height:Int,width:Int=0){

    val modifier=

        if (width==0)
            Modifier
                .fillMaxWidth()
                .height(height.dp)
                .background(PlaceholderColor)
        else
            Modifier
                .width(width.dp)
                .height(height.dp)
                .background(PlaceholderColor)


    Box(modifier = modifier)
}

@Composable
fun CoilImage(
    url:String,
    modifier: Modifier
){
    val painter= rememberCoilPainter(
        url,
        fadeIn = true,
    )

    Box(modifier) {
        when(painter.loadState){

            is ImageLoadState.Loading->Placeholder()

            is ImageLoadState.Error->Placeholder()

            is ImageLoadState.Success->
                Image(
                    painter = painter,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

            else->
                Image(
                    painter = painter,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

        }
    }
}

@Composable
fun CenterProgress(){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        CircularProgressIndicator(strokeWidth = 3.dp)
    }
}