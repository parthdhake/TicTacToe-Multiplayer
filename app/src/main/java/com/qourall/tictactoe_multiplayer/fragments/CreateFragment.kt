package com.qourall.tictactoe_multiplayer.fragments

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.qourall.tictactoe_multiplayer.R
import com.qourall.tictactoe_multiplayer.data.RoomDetails
import com.qourall.tictactoe_multiplayer.viewModels.CreateViewModel
import kotlinx.android.synthetic.main.fragment_create.*
import kotlinx.android.synthetic.main.fragment_join.*


class CreateFragment : Fragment() {

    lateinit var createViewModel: CreateViewModel
    lateinit var key: String
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

        copy.setOnClickListener {
            val clipboardManager = requireContext().getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("text", txtroomid.text.toString().trim())
            clipboardManager.setPrimaryClip(clipData)
        }

        createViewModel.result.observe(viewLifecycleOwner, Observer {
            if(it == null){
                Toast.makeText(context, "Room Created Successfully", Toast.LENGTH_LONG).show()
            }
            else {
                Toast.makeText(context, it.message.toString(), Toast.LENGTH_LONG).show()
            }
        })

        createViewModel.join.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                if (it.Player2Name != null){
                    Toast.makeText(context, it.Player2Name + " Joined", Toast.LENGTH_LONG).show()
                    progressBar.visibility = View.GONE
                    waiting.visibility = View.GONE
                    startButton.visibility = View.VISIBLE
                }
            }
        })

        createViewModel.keyDB.observe(viewLifecycleOwner, Observer {
            if (it != null){
                key = it
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

            createViewModel.realtimeUpdate()
        }

        startButton.setOnClickListener {
            val action = CreateFragmentDirections.actionCreateFragmentToPlayFragment(key, 1)
            Navigation.findNavController(it).navigate(action)
        }

    }
}