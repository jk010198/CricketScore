package com.interactive.sportz.models

data class TeamPlayerDetail(
    val Team_Away: String,
    val Team_Home: String,
    val Team_No: String,
    val Name: String,
    val Iskeeper: Boolean,
    val Iscaptain: Boolean,
    val Batting: Batting,
    val Bowling: Bowling,
)
