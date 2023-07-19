package com.tmg.foosball.model

data class GameResultModel(
    val player1Name: String, val player1Score: Int,
    val player2Name: String, val player2Score: Int,
    val id : Int
) {
}