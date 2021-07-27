package com.qourall.tictactoe_multiplayer.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.*
import com.qourall.tictactoe_multiplayer.data.RoomDetails

class CreateViewModel : ViewModel() {

    private val db = FirebaseDatabase.getInstance().getReference("RoomDetails")
    private var key: String? = null
    private val _keyDB = MutableLiveData<String?>()
    val keyDB : LiveData<String?>
    get() = _keyDB

    private val _result = MutableLiveData<Exception?>()
    val result: LiveData<Exception?>
        get() = _result

    private val _join = MutableLiveData<RoomDetails?>()
    val join: LiveData<RoomDetails?>
        get() = _join

    fun createRoom(name: String, roomDetails: RoomDetails){
        roomDetails.Player1Name = name
        key = db.push().key!!
        _keyDB.value = key
        roomDetails.RoomID = key!!.takeLast(5)
        val rand = (0..1).random()
        if (rand == 1){
            roomDetails.Player1Char = "X"
            roomDetails.Player1Move = true
            roomDetails.Player2Move = false
            roomDetails.Player2Char = "O"
        }
        else{
            roomDetails.Player1Char = "O"
            roomDetails.Player1Move = false
            roomDetails.Player2Move = true
            roomDetails.Player2Char = "X"
        }


        db.child(key!!).setValue(roomDetails)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    _result.value = null
                }
                else {
                    _result.value = it.exception
                }
            }
    }


    private val valueEventListener = object : ValueEventListener{
        override fun onDataChange(snapshot: DataSnapshot) {
            val d = snapshot.getValue(RoomDetails::class.java)
            _join.value = d
        }

        override fun onCancelled(error: DatabaseError) {}

    }
    fun realtimeUpdate(){
//        db.addChildEventListener(childEventListener)
        db.child(key!!).addValueEventListener(valueEventListener)
    }

    override fun onCleared() {
        super.onCleared()
        db.removeEventListener(valueEventListener)
    }
}