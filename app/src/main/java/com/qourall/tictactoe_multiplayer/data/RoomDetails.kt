package com.qourall.tictactoe_multiplayer.data

import com.qourall.tictactoe_multiplayer.Utils

data class RoomDetails(
    var RoomID: String? = null,
    var Player1Name: String? = null,
    var Player2Name: String? = null,
    var Player1Score: Int? = 0,
    var Player2Score: Int? = 0,
    var Player1Char: String? = null,
    var Player2Char: String? = null,
    var Player1Move: Boolean? = null,
    var Player2Move: Boolean? = null,
    var Player1Status: String = "NA",
    var Player2Status: String = "NA",
    var Player1Array: MutableList<Int> = mutableListOf(10),
    var Player2Array: MutableList<Int> = mutableListOf(10),
    var GameList: MutableList<String> = mutableListOf("A","A","A","A","A","A","A","A","A")
)