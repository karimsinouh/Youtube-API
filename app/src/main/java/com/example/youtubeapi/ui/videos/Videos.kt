package com.example.youtubeapi.ui.videos

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.youtubeapi.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Videos(){
    LazyColumn(
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxSize()
    ) {

        stickyHeader {
            Header(title = "Normal")
        }

        items(4){
            VideoItem()
        }

        stickyHeader {
            Header(title = "Cards")
        }

        items(4){
            CardVideoItem()
        }
    }
}

@Composable
fun Header(title:String){

    Box(modifier = Modifier
        .background(MaterialTheme.colors.surface)
        .fillMaxWidth()){
        Text(
            text = title,
            color = MaterialTheme.colors.onSurface,
            modifier = Modifier.padding(12.dp)
        )
    }
}

@Composable
@Preview
fun VideoItem(){
    Column(
        Modifier
            .clickable { },
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.material),
            "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(200.dp)
                .clip(RoundedCornerShape(8.dp))
        )
        Text(text = "This is just a damn stupid picture",fontSize = 18.sp)
        Text(text = "3 days ago",fontSize = 12.sp)
    }
}

@Composable
@Preview
fun CardVideoItem(){

    Card(Modifier.clickable {  }) {
        Column{
            Image(
                painter = painterResource(id = R.drawable.material),
                "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(200.dp)
            )
            Column(Modifier.padding(8.dp),) {
                Text(text = "This is just a damn stupid picture",fontSize = 18.sp)
                Text(text = "3 days ago",fontSize = 12.sp)
            }
        }
    }
}