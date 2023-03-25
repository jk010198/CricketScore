package com.interactive.sportz.models

data class Matchdetail(
    val Equation: String,
    val Match: Match,
    val Officials: Officials,
    val Player_Match: String,
    val Result: String,
    val Series: Series,
    val Status: String,
    val Status_Id: String,
    val Team_Away: String,
    val Team_Home: String,
    val Tosswonby: String,
    val Venue: Venue,
    val Weather: String,
    val Winmargin: String,
    val Winningteam: String
)