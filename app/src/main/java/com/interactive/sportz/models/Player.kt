package com.interactive.sportz.models

data class Player(
    val Position: String,
    val Name_Full: String,
    val Iscaptain: Boolean,
    val Iskeeper: Boolean,
    val Batting: Batting,
    val Bowling: Bowling
)
