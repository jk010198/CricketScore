package com.interactive.sportz.models

data class Bowler(
    val Bowler: String,
    val Dots: String,
    val Economyrate: String,
    val Isbowlingnow: Boolean,
    val Isbowlingtandem: Boolean,
    val Maidens: String,
    val Noballs: String,
    val Overs: String,
    val Runs: String,
    val ThisOver: List<ThisOver>,
    val Wickets: String,
    val Wides: String
)