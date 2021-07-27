package com.qourall.tictactoe_multiplayer.fragments

import android.os.Bundle
import android.util.Log
import android.view.Gravity.CENTER
import android.view.Gravity.END
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.geniusforapp.fancydialog.builders.FancyDialogBuilder
import com.qourall.tictactoe_multiplayer.R
import com.qourall.tictactoe_multiplayer.data.RoomDetails
import com.qourall.tictactoe_multiplayer.viewModels.PlayViewModel
import kotlinx.android.synthetic.main.fragment_play.*
import java.util.ArrayList


class PlayFragment : Fragment() {

    lateinit var playViewModel: PlayViewModel
    lateinit var roomDetails: RoomDetails
    lateinit var playerPiece: String
    private var playedMoves: MutableList<String> = mutableListOf("A","A","A","A","A","A","A","A","A")
    var playerMove: Boolean = false

    lateinit var btnList : Array<ImageView>

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

        btnList = arrayOf(btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9)

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
                else if (args.player == 2){
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
            playedMoves = it
            refreshPlayedMoves(it)
        })

        playViewModel.scoreP1.observe(viewLifecycleOwner, {
            p1_winning.text = it.toString()
        })
        playViewModel.scoreP2.observe(viewLifecycleOwner, {
            p2_winning.text = it.toString()
        })

        playViewModel.checkstatusOfGame.observe(viewLifecycleOwner, {
            when (it) {
                "Draw" -> {
                    val dialog = FancyDialogBuilder(requireContext(), R.style.CustomDialog)
                        .withImageIcon(R.drawable.tic_tac_toe)
                        .withTitleTypeFace(R.font.otomanopeeone_regular)
                        .withSubTitleTypeFace(R.font.otomanopeeone_regular)
                        .withActionPositiveTypeFace(R.font.otomanopeeone_regular)
                        .withActionNegativeTypeFace(R.font.otomanopeeone_regular)
                        .withTextGravity(CENTER)
                        .withPanelGravity(CENTER)
                        .withTitle("Draw")
                        .withSubTitle("The Game is Draw")
                        .withPositive("PLAY AGAIN") { _, dialog ->
                            dialog.dismiss()
                            playViewModel.playAgain()
                        }
                        .withNegative("LEAVE GAME") { _, dialog ->
                            dialog.dismiss()
                            Navigation.findNavController(requireView()).navigate(R.id.action_playFragment_to_createJoinFragment)
                        }
                    dialog.show()
                }
                "Win" -> {
                    val dialog = FancyDialogBuilder(requireContext(), R.style.CustomDialog)
                        .withImageIcon(R.drawable.trophy_win)
                        .withTitleTypeFace(R.font.otomanopeeone_regular)
                        .withSubTitleTypeFace(R.font.otomanopeeone_regular)
                        .withActionPositiveTypeFace(R.font.otomanopeeone_regular)
                        .withActionNegativeTypeFace(R.font.otomanopeeone_regular)
                        .withTextGravity(CENTER)
                        .withPanelGravity(CENTER)
                        .withTitle("Win")
                        .withSubTitle("Congratulations!! You won the Game.")
                        .withPositive("PLAY AGAIN") { _, dialog ->
                            dialog.dismiss()
                            playViewModel.playAgain()
                        }
                        .withNegative("LEAVE GAME") { _, dialog ->
                            dialog.dismiss()
                            Navigation.findNavController(requireView()).navigate(R.id.action_playFragment_to_createJoinFragment)
                        }
                    dialog.show()
                }
                "Lose" -> {
                    val dialog = FancyDialogBuilder(requireContext(), R.style.CustomDialog)
                        .withImageIcon(R.drawable.defeat)
                        .withTitleTypeFace(R.font.otomanopeeone_regular)
                        .withSubTitleTypeFace(R.font.otomanopeeone_regular)
                        .withActionPositiveTypeFace(R.font.otomanopeeone_regular)
                        .withActionNegativeTypeFace(R.font.otomanopeeone_regular)
                        .withTextGravity(CENTER)
                        .withPanelGravity(CENTER)
                        .withTitle("Defeat")
                        .withSubTitle("Oh No!! You just lost the Game.")
                        .withPositive("PLAY AGAIN") { _, dialog ->
                            dialog.dismiss()
                            playViewModel.playAgain()
                        }
                        .withNegative("LEAVE GAME") {view, dialog ->
                            dialog.dismiss()
                            Navigation.findNavController(requireView()).navigate(R.id.action_playFragment_to_createJoinFragment)

                        }
                    dialog.show()
                }
            }
        })

        btnQuit.setOnClickListener {
            val dialog = FancyDialogBuilder(requireContext(), R.style.CustomDialog)
                .withImageIcon(R.drawable.tic_tac_toe)
                .withTitleTypeFace(R.font.otomanopeeone_regular)
                .withSubTitleTypeFace(R.font.otomanopeeone_regular)
                .withActionPositiveTypeFace(R.font.otomanopeeone_regular)
                .withActionNegativeTypeFace(R.font.otomanopeeone_regular)
                .withTextGravity(CENTER)
                .withPanelGravity(CENTER)
                .withTitle("Exit Game")
                .withSubTitle("Are you sure you want to leave this Game.")
                .withPositive("YES") { _, dialog ->
                    Navigation.findNavController(it).navigate(R.id.action_playFragment_to_createJoinFragment)
                    dialog.dismiss()
                }
                .withNegative("NO") {view, dialog -> dialog.dismiss() }
            dialog.show()
        }
        btnOnClick()
    }

    private fun btnOnClick(){
        for (i in 0..8){
            if(playedMoves[i] == "A"){
                btnList[i].setOnClickListener {
                    innerbtnOnClick(btnList[i], i+1)
                }
            }
        }
    }

    fun innerbtnOnClick(btn : ImageView , btnPos: Int){
        if (playerPiece == "X"){
            btn.setImageResource(R.drawable.ic_cross_yellow)
        }
        if (playerPiece == "O"){
            btn.setImageResource(R.drawable.ic_circle_secondary)
        }
        playViewModel.btnClick(btnPos ,args.player)
    }

    private fun refreshPlayedMoves(moves: MutableList<String>){
        for (i in moves.indices){

            for(j in 0..8){
                if (i == j && moves[i] == "X"){
                    btnList[j].setImageResource(R.drawable.ic_cross_yellow)
                }
                else if (i == j && moves[i] == "O"){
                    btnList[j].setImageResource(R.drawable.ic_circle_secondary)
                }
                else if (i == j && moves[i] == "A"){
                    btnList[j].setImageDrawable(null)
                }
            }
//
//            if (i == 0 && moves[i] == "X"){
//                btn1.setImageResource(R.drawable.ic_cross_yellow)
//            }
//            else if (i == 0 && moves[i] == "O"){
//                btn1.setImageResource(R.drawable.ic_circle_secondary)
//            }
//            if (i == 1 && moves[i] =="X"){
//                btn2.setImageResource(R.drawable.ic_cross_yellow)
//            }
//            else if (i == 1 && moves[i] == "O"){
//                btn2.setImageResource(R.drawable.ic_circle_secondary)
//            }
//            if (i == 2 && moves[i] =="X"){
//                btn3.setImageResource(R.drawable.ic_cross_yellow)
//            }
//            else if (i == 2 && moves[i] == "O"){
//                btn3.setImageResource(R.drawable.ic_circle_secondary)
//            }
//            if (i == 3 && moves[i] =="X"){
//                btn4.setImageResource(R.drawable.ic_cross_yellow)
//            }
//            else if (i == 3 && moves[i] == "O"){
//                btn4.setImageResource(R.drawable.ic_circle_secondary)
//            }
//            if (i == 4 && moves[i] =="X"){
//                btn5.setImageResource(R.drawable.ic_cross_yellow)
//            }
//            else if (i == 4 && moves[i] == "O"){
//                btn5.setImageResource(R.drawable.ic_circle_secondary)
//            }
//            if (i == 5 && moves[i] =="X"){
//                btn6.setImageResource(R.drawable.ic_cross_yellow)
//            }
//            else if (i == 5 && moves[i] == "O"){
//                btn6.setImageResource(R.drawable.ic_circle_secondary)
//            }
//            if (i == 6 && moves[i] =="X"){
//                btn7.setImageResource(R.drawable.ic_cross_yellow)
//            }
//            else if (i == 6 && moves[i] == "O"){
//                btn7.setImageResource(R.drawable.ic_circle_secondary)
//            }
//            if (i == 7 && moves[i] =="X"){
//                btn8.setImageResource(R.drawable.ic_cross_yellow)
//            }
//            else if (i == 7 && moves[i] == "O"){
//                btn8.setImageResource(R.drawable.ic_circle_secondary)
//            }
//            if (i == 8 && moves[i] =="X"){
//                btn9.setImageResource(R.drawable.ic_cross_yellow)
//            }
//            else if (i == 8 && moves[i] == "O"){
//                btn9.setImageResource(R.drawable.ic_circle_secondary)
//            }
        }
    }

    private fun setImageViewUnClickable(){
        for (i in 0..8){
            btnList[i].setOnClickListener(null)
        }
    }

}