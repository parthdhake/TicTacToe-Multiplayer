 package com.qourall.tictactoe_multiplayer.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.qourall.tictactoe_multiplayer.Utils
import com.qourall.tictactoe_multiplayer.data.RoomDetails

class PlayViewModel : ViewModel() {

    lateinit var key: String
    var Player1or2: Int? = null
    val db = FirebaseDatabase.getInstance().getReference("RoomDetails")

    private var _RoomDetails = MutableLiveData<RoomDetails>()
    val RoomD : LiveData<RoomDetails>
        get() = _RoomDetails
    lateinit var roomDetailsClass : RoomDetails

    private var _moves = MutableLiveData<MutableList<String>>()
    val moves : LiveData<MutableList<String>>
        get() = _moves

    private var _player_move = MutableLiveData<Boolean>()
    val player_move : LiveData<Boolean>
        get() = _player_move

    private var _checkstatusOfGame = MutableLiveData<String?>()
    val checkstatusOfGame : LiveData<String?>
        get() = _checkstatusOfGame

    fun getRoomDetails(DBkey: String) {
        db.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (room in snapshot.children){
                        if (room.key == DBkey){
                            key = room.key!!
                            val roomDetails : RoomDetails? = room.getValue(RoomDetails::class.java)
                            roomDetailsClass = roomDetails!!
                            _RoomDetails.value = roomDetails
                            _moves.value = roomDetails.GameList
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private val valueEventListener = object : ValueEventListener{
        override fun onDataChange(snapshot: DataSnapshot) {
            val room = snapshot.getValue(RoomDetails::class.java)
            roomDetailsClass = room!!
            _moves.value = room!!.GameList
            if (Player1or2 == 1){
                _player_move.value = room.Player1Move
            }
            if (Player1or2 == 2){
                _player_move.value = room.Player2Move
            }
        }

        override fun onCancelled(error: DatabaseError) {}

    }

    fun getRealtimeDetails(DBkey: String, player: Int){
        Player1or2 = player
        db.child(DBkey).addValueEventListener(valueEventListener)
    }

    fun btnClick(btnPos: Int, player: Int){
        when(btnPos){
            1 -> add(player, 1)
            2 -> add(player, 2)
            3 -> add(player, 3)
            4 -> add(player, 4)
            5 -> add(player, 5)
            6 -> add(player, 6)
            7 -> add(player, 7)
            8 -> add(player, 8)
            9 -> add(player, 9)
        }
    }

    private fun add(player: Int, btn: Int){
        if(player == 1){
            roomDetailsClass.Player1Array.add(btn)
            roomDetailsClass.GameList[btn-1] = "X"
            roomDetailsClass.Player1Move = false
            roomDetailsClass.Player2Move = true
            roomDetailsClass.Player1Status = Utils.checkWin(roomDetailsClass.Player1Array, roomDetailsClass.GameList)
            if (roomDetailsClass.Player1Status == "Draw"){
                roomDetailsClass.Player2Status = "Draw"
            }
            if (roomDetailsClass.Player1Status == "Win"){
                roomDetailsClass.Player1Score = roomDetailsClass.Player1Score?.plus(1)
                roomDetailsClass.Player2Status = "Lose"
            }
            _checkstatusOfGame.value = roomDetailsClass.Player1Status
        }else{
            roomDetailsClass.Player2Array.add(btn)
            roomDetailsClass.GameList[btn-1] = "O"
            roomDetailsClass.Player2Move = false
            roomDetailsClass.Player1Move = true
            roomDetailsClass.Player2Status = Utils.checkWin(roomDetailsClass.Player1Array, roomDetailsClass.GameList)
            if (roomDetailsClass.Player2Status == "Draw"){
                roomDetailsClass.Player1Status = "Draw"
            }
            if (roomDetailsClass.Player2Status == "Win"){
                roomDetailsClass.Player1Score = roomDetailsClass.Player2Score?.plus(1)
                roomDetailsClass.Player1Status = "Lose"
            }
            _checkstatusOfGame.value = roomDetailsClass.Player1Status
        }
        db.child(key).setValue(roomDetailsClass)
    }
}