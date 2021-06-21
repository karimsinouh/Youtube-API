package com.example.youtubeapi.utils

import kotlin.math.abs

fun Int.formatNumbers():String= when{
        abs(this)/1000 >= 1-> "${abs(this)/1000}K"
        abs(this)/1000000 >= 1-> "${abs(this)/1000000}M"
        abs(this)/1000000000 >= 1-> "${abs(this)/1000000000}B"
        else-> this.toString()
    }

