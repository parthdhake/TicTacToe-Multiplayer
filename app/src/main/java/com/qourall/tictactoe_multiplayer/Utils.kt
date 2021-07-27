package com.qourall.tictactoe_multiplayer

import android.util.Log

object Utils {
    private val answers = listOf<List<Int>>(
        listOf<Int>(1, 2, 3),
        listOf<Int>(4, 5, 6),
        listOf<Int>(7, 8, 9),
        listOf<Int>(1, 4, 7),
        listOf<Int>(2, 5, 8),
        listOf<Int>(3, 6, 9),
        listOf<Int>(1, 5, 9),
        listOf<Int>(3, 5, 7),
    )

    fun checkWin(playerArray: MutableList<Int>, GameList: MutableList<String>) : String {
        var status = "NA"
        Log.d("csc",playerArray.toString())
        for (i in 0..7){
            if (playerArray.containsAll(answers[i])){
                status = "Win"
            }
        }
        if (!GameList.contains("A") && status == "NA"){
            status = "Draw"
        }
        return status
    }

}