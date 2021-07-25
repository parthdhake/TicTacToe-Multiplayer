package com.qourall.tictactoe_multiplayer.viewModels

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.FirebaseDatabase
import com.qourall.tictactoe_multiplayer.data.RoomDetails

class CreateViewModel : ViewModel() {

    private val _result = MutableLiveData<Exception?>()
    val result: LiveData<Exception?>
        get() = _result

    fun createRoom(name: String, roomDetails: RoomDetails){
        val db = FirebaseDatabase.getInstance().getReference("RoomDetails")
        roomDetails.Player1Name = name
        val roomid: String = db.push().key!!
        roomDetails.RoomID = roomid.takeLast(5)

        db.child(roomid).setValue(roomDetails)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    _result.value = null
                }
                else {
                    _result.value = it.exception
                }
            }
    }

}