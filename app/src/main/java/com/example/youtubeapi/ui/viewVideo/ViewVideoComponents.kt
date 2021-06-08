package com.example.youtubeapi.ui.viewVideo

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.youtubeapi.data.Statistics
import com.example.youtubeapi.utils.Placeholder
import com.example.youtubeapi.data.Snippet
import com.example.youtubeapi.data.thumbnails.Thumbnails
import com.example.youtubeapi.utils.CoilImage

@Composable
fun ViewVideoPlaceholder(){
    Column(Modifier.fillMaxSize()) {
        Placeholder(200)

        Column(
            Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Placeholder(25)
            Placeholder(20,120)
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

        VideoStatisticsItem(viewCount.toString(), Icons.Outlined.ThumbUp) {}
        VideoStatisticsItem(likeCount.toString(), Icons.Outlined.Email) {}
        VideoStatisticsItem(dislikeCount.toString(), Icons.Outlined.Edit) {}
        VideoStatisticsItem("Share", Icons.Outlined.Share,onClick = onShare)

        if (inWatchLater)
            VideoStatisticsItem("Remove", Icons.Outlined.Favorite) { onWatchLater(false) }
        else
            VideoStatisticsItem("Watch Later", Icons.Outlined.FavoriteBorder) {onWatchLater(true)}

    }
}

@Composable
fun VideoStatisticsItem(
    text:String,
    icon: ImageVector,
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
                .width(75.dp)
                .height(75.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(icon,null)
            Text(text,textAlign = TextAlign.Center)
        }

    }
}

@Composable
fun Snippet.Show(
    onClick: () -> Unit
){
    Box(
        Modifier.clickable(onClick = onClick)
    ) {
        Column(Modifier.padding(12.dp)) {
            Text(title,fontSize = 20.sp)
            Text(publishedAt,fontSize = 12.sp)
        }
    }
}

@Composable
fun Thumbnails.Show(
    onPlay:()->Unit
){
    Box(
        modifier = Modifier
            .height(200.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ){

        CoilImage(
            url=medium.url,
            modifier = Modifier.fillMaxSize()
        )

        FloatingActionButton(
            onClick=onPlay,
            backgroundColor = Color.White,
            contentColor = Color.Black
        ) {
            Icon(Icons.Outlined.PlayArrow, "")
        }

    }
}