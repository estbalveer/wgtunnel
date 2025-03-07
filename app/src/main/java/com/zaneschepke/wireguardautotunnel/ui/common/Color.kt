package com.zaneschepke.wireguardautotunnel.ui.common

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val White = Color(0xFFFFFFFF)
val Black = Color(0xFF000000)
//val BrightBlue = Color(0xFF1975FE)
val BrightBlue = Color(0xFF1E90FF)

val GrayishBlue = Color(0xFF161F2D)
var DarkBlue = Color(0xFF020C1B)
var Gray = Color.Gray
var LightGray = Color(0xFFCCCCCC)
var LightYellow = Color(0xD1DEEB9E)

val BackgroundGradient = Brush.linearGradient(
    colors = listOf(DarkBlue, DarkBlue, Black),
    start = Offset(0f, 0f),
    end = Offset(0f, Float.POSITIVE_INFINITY)
)
