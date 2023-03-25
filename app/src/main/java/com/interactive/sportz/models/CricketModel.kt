package com.interactive.sportz.models

data class CricketModel(
    val Innings: List<Inning>,
    val Matchdetail: Matchdetail,
    val Teams: Teams
): java.io.Serializable