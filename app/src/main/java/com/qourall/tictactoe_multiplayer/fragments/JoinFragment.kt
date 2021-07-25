package com.qourall.tictactoe_multiplayer.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.qourall.tictactoe_multiplayer.R
import com.qourall.tictactoe_multiplayer.data.RoomDetails
import com.qourall.tictactoe_multiplayer.viewModels.JoinViewModel
import kotlinx.android.synthetic.main.fragment_create.*
import kotlinx.android.synthetic.main.fragment_join.*



class JoinFragment : Fragment() {

    lateinit var joinViewModel: JoinViewModel
    var name : String? = null
    var etrRoomID : String? = null
    private var flag : Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        joinViewModel = ViewModelProviders.of(this).get(JoinViewModel::class.java)
        return inflater.inflate(R.layout.fragment_join, container, false)
    }

    override fun onStart() {
        super.onStart()
        var roomDetails = RoomDetails()

        startGame.setOnClickListener {
            name  =  player2name.editText?.text.toString().trim()
            etrRoomID = room_id.editText?.text.toString().trim()

            if (name.isNullOrEmpty() || etrRoomID.isNullOrEmpty()){
                Toast.makeText(context,"Name or RoomID missing...", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            joinViewModel.getRoomDetails(roomDetails, name!!, etrRoomID!!)
        }

        joinViewModel.RoomD.observe(viewLifecycleOwner, Observer {
            roomDetails = it
//            Toast.makeText(context, it.toString(), Toast.LENGTH_LONG).show()
            if (name == it.Player2Name){
                Navigation.findNavController(requireView()).navigate(R.id.action_joinFragment_to_playFragment)
            }
            if (name != it.Player2Name){
                Toast.makeText(context, "RoomID Invalid", Toast.LENGTH_LONG).show()
            }
        })

    }


}