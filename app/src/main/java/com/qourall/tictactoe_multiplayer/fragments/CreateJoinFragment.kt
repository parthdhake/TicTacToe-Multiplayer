package com.qourall.tictactoe_multiplayer.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.navigation.Navigation
import com.qourall.tictactoe_multiplayer.R


class CreateJoinFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_create_join, container, false)

        val create = root.findViewById<CardView>(R.id.CreateCard)
        val join = root.findViewById<CardView>(R.id.JoinCard)

        create.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_createJoinFragment_to_createFragment)
        }

        join.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_createJoinFragment_to_joinFragment)
        }

        return root
    }
}