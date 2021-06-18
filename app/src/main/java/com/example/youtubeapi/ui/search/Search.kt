package com.example.youtubeapi.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.youtubeapi.utils.CenterProgress

@Composable
fun Search(vm:SearchViewModel= hiltViewModel(),nav:NavController){

        Surface {

            Column(Modifier.padding(12.dp)) {
                SearchBar(
                    vm.searchQuery.value,
                    onValueChange = {vm.searchQuery.value=it}
                ) {}
                CenterProgress()
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
                .height(50.dp),
            value = value,
            onValueChange = { onValueChange(it) },
            singleLine = true
        )

        IconButton(onClick = { onSearchClick() }) {
            Icon(Icons.Outlined.Search,"")
        }
    }
}