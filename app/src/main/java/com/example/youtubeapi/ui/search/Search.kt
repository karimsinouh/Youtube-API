package com.example.youtubeapi.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.youtubeapi.ui.videos.VideoItem
import com.example.youtubeapi.ui.videos.VideoItemSmall
import com.example.youtubeapi.utils.CenterProgress

@Composable
fun Search(vm:SearchViewModel= hiltViewModel(),nav:NavController){

        Surface {

            Column(Modifier.padding(12.dp)) {
                SearchBar(
                    vm.searchQuery.value,
                    onValueChange = {vm.searchQuery.value=it}
                ) {
                    vm.search()
                }

                Spacer(Modifier.height(8.dp))

                if (vm.isLoading.value && vm.pageToken==""){
                    CenterProgress()
                }else{

                    LazyColumn {
                        items(vm.items){item->
                            VideoItem(snippet = item.snippet) {}
                        }
                    }

                }

            }

        }

}

@Composable
fun SearchBar(
    value:String,
    onValueChange:(q:String)->Unit,
    onSearchClick:()->Unit
){


    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clip(CircleShape)
            .background(MaterialTheme.colors.onSurface.copy(alpha = 0.1f))
    ) {

        BasicTextField(
            modifier= Modifier
                .weight(0.9f)
                .padding(12.dp),
            value = value,
            onValueChange = { onValueChange(it) },
            singleLine = true,
            textStyle = TextStyle(color = MaterialTheme.colors.onSurface)
        )

        IconButton(onClick = { onSearchClick() }) {
            Icon(Icons.Outlined.Search,"")
        }
    }
}