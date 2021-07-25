package com.qourall.tictactoe_multiplayer.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.qourall.tictactoe_multiplayer.data.RoomDetails

class JoinViewModel : ViewModel() {

    private var _RoomDetails = MutableLiveData<RoomDetails>()
    val RoomD : LiveData<RoomDetails>
        get() = _RoomDetails


    fun getRoomDetails(roomDetails: RoomDetails, name: String, etrRoomId: String){
        val db = FirebaseDatabase.getInstance().getReference("RoomDetails")

        db.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (room in snapshot.children){
                        if (room.key?.takeLast(5).toString() == etrRoomId){
                            roomDetails.RoomID = etrRoomId
                            val key = room.key!!

                            val d : RoomDetails? = room.getValue(RoomDetails::class.java)
                            if (d != null) {
                                d.Player2Name = name
                            }
                            _RoomDetails.value = d
                            db.child(key).setValue(d)

                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })


    }
}