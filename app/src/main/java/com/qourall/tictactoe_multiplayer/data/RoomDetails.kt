package com.qourall.tictactoe_multiplayer.data

data class RoomDetails (
    var RoomID: String? = null,
    var Player1Name: String? = null,
    var Player2Name: String? = null,
    var Player1Score: Int? = null,
    var Player2Score: Int? = null,
    var Player1Array: List<Int>? = null,
    var Player2Array: List<Int>? = null,
)