package com.qourall.tictactoe_multiplayer.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.qourall.tictactoe_multiplayer.R
import com.qourall.tictactoe_multiplayer.data.RoomDetails
import com.qourall.tictactoe_multiplayer.viewModels.PlayViewModel
import kotlinx.android.synthetic.main.fragment_play.*
import java.util.ArrayList


class PlayFragment : Fragment() {

    lateinit var playViewModel: PlayViewModel
    lateinit var roomDetails: RoomDetails
    lateinit var playerPiece: String
    var playerMove: Boolean = false

    val args : PlayFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        playViewModel = ViewModelProviders.of(this).get(PlayViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_play, container, false)
        return root
    }

    override fun onStart() {
        super.onStart()

        playViewModel.getRoomDetails(args.key)
        playViewModel.getRealtimeDetails(args.key, args.player)
        playViewModel.RoomD.observe(viewLifecycleOwner, {
            if (it != null){
                roomDetails = it
                Log.d("css",it.toString())
                if (args.player == 1){
                    playerPiece = roomDetails.Player1Char!!
                    if (roomDetails.Player1Move == true){
                        playerMove = true
                    }
                }
                if (args.player == 2){
                    playerPiece = roomDetails.Player2Char!!
                    if (roomDetails.Player2Move == true){
                        playerMove = true
                    }
                }
                Player1.text = roomDetails.Player1Name
                Player2.text = roomDetails.Player2Name
            }
        })

        playViewModel.player_move.observe(viewLifecycleOwner, {
            if (it) {
                btnOnClick()
                turn.text = "Your" + " turn"

            } else {
                setImageViewUnClickable()
                turn.text = "Opponent's" + " turn"

            }
        })

        playViewModel.moves.observe(viewLifecycleOwner, {
            refreshPlayedMoves(it)
        })

        btnQuit.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_playFragment_to_createJoinFragment)
        }
        btnOnClick()
    }

    private fun btnOnClick(){
        btn1.setOnClickListener {
            innerbtnOnClick(btn1, 1)
        }
        btn2.setOnClickListener {
            innerbtnOnClick(btn2, 2)
        }
        btn3.setOnClickListener {
            innerbtnOnClick(btn3, 3)
        }
        btn4.setOnClickListener {
            innerbtnOnClick(btn4, 4)
        }
        btn5.setOnClickListener {
            innerbtnOnClick(btn5, 5)
        }
        btn6.setOnClickListener {
            innerbtnOnClick(btn6, 6)
        }
        btn7.setOnClickListener {
            innerbtnOnClick(btn7, 7)
        }
        btn8.setOnClickListener {
            innerbtnOnClick(btn8, 8)
        }
        btn9.setOnClickListener {
            innerbtnOnClick(btn9, 9)
        }
    }

    fun innerbtnOnClick(btn : ImageView , btnPos: Int){
        if (playerPiece == "X"){
            btn.setImageResource(R.drawable.ic_cross_yellow)
        }
        else{
            btn.setImageResource(R.drawable.ic_circle_secondary)
        }
        playViewModel.btnClick(btnPos ,args.player)
    }

    private fun refreshPlayedMoves(moves: MutableList<String>){
        for (i in moves.indices){
            if (i == 0 && moves[i] == "X"){
                btn1.setImageResource(R.drawable.ic_cross_yellow)
            }
            else if (i == 0 && moves[i] == "O"){
                btn1.setImageResource(R.drawable.ic_circle_secondary)
            }
            if (i == 1 && moves[i] =="X"){
                btn2.setImageResource(R.drawable.ic_cross_yellow)
            }
            else if (i == 1 && moves[i] == "O"){
                btn2.setImageResource(R.drawable.ic_circle_secondary)
            }
            if (i == 2 && moves[i] =="X"){
                btn3.setImageResource(R.drawable.ic_cross_yellow)
            }
            else if (i == 2 && moves[i] == "O"){
                btn3.setImageResource(R.drawable.ic_circle_secondary)
            }
            if (i == 3 && moves[i] =="X"){
                btn4.setImageResource(R.drawable.ic_cross_yellow)
            }
            else if (i == 3 && moves[i] == "O"){
                btn4.setImageResource(R.drawable.ic_circle_secondary)
            }
            if (i == 4 && moves[i] =="X"){
                btn5.setImageResource(R.drawable.ic_cross_yellow)
            }
            else if (i == 4 && moves[i] == "O"){
                btn5.setImageResource(R.drawable.ic_circle_secondary)
            }
            if (i == 5 && moves[i] =="X"){
                btn6.setImageResource(R.drawable.ic_cross_yellow)
            }
            else if (i == 5 && moves[i] == "O"){
                btn6.setImageResource(R.drawable.ic_circle_secondary)
            }
            if (i == 6 && moves[i] =="X"){
                btn7.setImageResource(R.drawable.ic_cross_yellow)
            }
            else if (i == 6 && moves[i] == "O"){
                btn7.setImageResource(R.drawable.ic_circle_secondary)
            }
            if (i == 7 && moves[i] =="X"){
                btn8.setImageResource(R.drawable.ic_cross_yellow)
            }
            else if (i == 7 && moves[i] == "O"){
                btn8.setImageResource(R.drawable.ic_circle_secondary)
            }
            if (i == 8 && moves[i] =="X"){
                btn9.setImageResource(R.drawable.ic_cross_yellow)
            }
            else if (i == 8 && moves[i] == "O"){
                btn9.setImageResource(R.drawable.ic_circle_secondary)
            }
        }
    }

    private fun setImageViewUnClickable(){
        btn1.setOnClickListener(null)
        btn2.setOnClickListener(null)
        btn3.setOnClickListener(null)
        btn4.setOnClickListener(null)
        btn5.setOnClickListener(null)
        btn6.setOnClickListener(null)
        btn7.setOnClickListener(null)
        btn8.setOnClickListener(null)
        btn9.setOnClickListener(null)
    }

}