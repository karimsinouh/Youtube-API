package com.example.youtubeapi.ui.viewVideo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.outlined.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.youtubeapi.R
import com.example.youtubeapi.data.Snippet
import com.example.youtubeapi.data.Statistics
import com.example.youtubeapi.data.thumbnails.Thumbnails
import com.example.youtubeapi.ui.theme.PlaceholderColor
import com.example.youtubeapi.utils.CoilImage
import com.example.youtubeapi.utils.Placeholder
import com.example.youtubeapi.utils.defaultThumbnail
import com.example.youtubeapi.utils.formatNumbers
import kotlin.random.Random


@Composable
@Preview
fun ViewVideoPlaceholder(){
    Column(Modifier.fillMaxSize()) {
        Placeholder(200)

        Column(
            Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Placeholder(25)
            Placeholder(20,120)
            Placeholder(20,220)

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 18.dp)
            ){
                repeat(5) {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(25.dp)
                            .background(PlaceholderColor)
                    )
                }
            }

            repeat(5){
                VideosListPlaceholder()
            }
        }

    }
}

@Composable
@Preview
fun VideosListPlaceholder(){
    Row (
        modifier = Modifier.fillMaxWidth()
    ){
        Box(modifier = Modifier
            .width(120.dp)
            .height(70.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(PlaceholderColor)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            repeat(2){
                Box(modifier = Modifier
                    .width(Random.nextInt(100, 200).dp)
                    .height(20.dp)
                    .background(PlaceholderColor))
            }
        }
    }
}


@Composable
fun Statistics.Show(
    inWatchLater:Boolean,
    onShare:()->Unit,
    onWatchLater:(Boolean)->Unit
){
    Row(
        modifier= Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ){

        VideoStatisticsItem(viewCount.formatNumbers(), painter = painterResource(R.drawable.ic_eye)) {}
        VideoStatisticsItem(likeCount.formatNumbers(), Icons.Default.ThumbUp) {}
        VideoStatisticsItem(dislikeCount.formatNumbers(), painter = painterResource(id = R.drawable.ic_dislike)) {}
        VideoStatisticsItem("Share", Icons.Outlined.Share,onClick = onShare)

        if (inWatchLater)
            VideoStatisticsItem("Remove", Icons.Outlined.Favorite) { onWatchLater(false) }
        else
            VideoStatisticsItem("Later", Icons.Outlined.FavoriteBorder) {onWatchLater(true)}

    }
}

@Composable
fun VideoStatisticsItem(
    text:String,
    imageVector: ImageVector?=null,
    painter: Painter?=null,
    onClick:()->Unit
){
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ){

        Column(
            modifier = Modifier
                .width(70.dp)
                .height(70.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (imageVector!=null)
                Icon(imageVector ,null)
            else
                Icon(painter!! ,null)

            Text(text,textAlign = TextAlign.Center)
        }

    }
}

@Composable
fun Snippet.Show(
    onClick: () -> Unit
){
    Box(
        Modifier
            .clickable(onClick = onClick)
            .fillMaxWidth()
    ) {
        Column(Modifier.padding(12.dp)) {
            Text(title,fontSize = 20.sp)
            Text(publishedAt,fontSize = 12.sp)
        }
    }
}

@Composable
fun Thumbnails.Show(
    includePlayButton:Boolean=true,
    onPlay:()->Unit
){
    Box(
        modifier = Modifier
            .height(200.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ){

        val url=
            high?.url ?: (medium?.url ?: (default?.url ?: defaultThumbnail))

        CoilImage(
            url=url,
            modifier = Modifier.fillMaxSize()
        )

        if (includePlayButton)
            FloatingActionButton(
                onClick=onPlay,
                backgroundColor = Color.White,
                contentColor = Color.Black
            ) {
                Icon(Icons.Outlined.PlayArrow, "")
            }

    }
}