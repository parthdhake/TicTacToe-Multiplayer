package com.qourall.tictactoe_multiplayer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.qourall.tictactoe_multiplayer.R
import com.qourall.tictactoe_multiplayer.data.RoomDetails
import com.qourall.tictactoe_multiplayer.viewModels.CreateViewModel
import kotlinx.android.synthetic.main.fragment_create.*


class CreateFragment : Fragment() {

    lateinit var Room_ID : String
    lateinit var createViewModel: CreateViewModel
    var name: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root =  inflater.inflate(R.layout.fragment_create, container, false)
        createViewModel = ViewModelProviders.of(this).get(CreateViewModel::class.java)
        return root
    }

    override fun onStart() {
        super.onStart()

        createViewModel.result.observe(viewLifecycleOwner, Observer {
            if(it == null){
                Toast.makeText(context, "Room Created Successfully", Toast.LENGTH_LONG).show()
            }
            else {
                Toast.makeText(context, it.message.toString(), Toast.LENGTH_LONG).show()
            }
        })

        roomButton.setOnClickListener {
            name  =  textFieldName.editText?.text.toString().trim()
            if (name.isNullOrEmpty()) {
                Toast.makeText(context, "We don't know your name.Please Enter It.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val roomDetails = RoomDetails()
            createViewModel.createRoom(name!!, roomDetails)
            txtroomid.text = roomDetails.RoomID

        }
    }
}