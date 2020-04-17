package com.ggonzales.tictactoeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        refreshButton.setOnClickListener {
            refreshButtons()
        }

    }

    fun onClickButton(view: View){
        val buttonSelected = view as Button
        var blockId = 0
        when(buttonSelected.id){
            R.id.button1 -> blockId = 1
            R.id.button2 -> blockId = 2
            R.id.button3 -> blockId = 3
            R.id.button4 -> blockId = 4
            R.id.button5 -> blockId = 5
            R.id.button6 -> blockId = 6
            R.id.button7 -> blockId = 7
            R.id.button8 -> blockId = 8
            R.id.button9 -> blockId = 9
        }
        Log.d("App Clicks", "${buttonSelected.id.toString()} : $blockId")
        playGame(blockId, buttonSelected)
    }

    var activePlayer = 1 //To identify which player's turn it is
    var player1Moves = ArrayList<Int>()
    var player2Moves = ArrayList<Int>()


    fun playGame(blockId : Int, buttonSelected : Button){
        if(activePlayer == 1){
            //   Player 1
            buttonSelected.text = "X"
            buttonSelected.setBackgroundResource(R.color.backgroundPlayer1)
            player1Moves.add(blockId)
            activePlayer = 2

        }
        else{
            //   Player 2
            buttonSelected.text = "0"
            buttonSelected.setBackgroundResource(R.color.backgroundPlayer2)
            player2Moves.add(blockId)
            activePlayer = 1
        }
        buttonSelected.isEnabled = false

        checkWinner()
    }
    fun checkWinner(){
        var winner = -1
        // row 1
        if(player1Moves.contains(1)&& player1Moves.contains(2) && player1Moves.contains(3)){
            winner = 1
        }
        else if(player2Moves.contains(1)&& player2Moves.contains(2) && player2Moves.contains(3)){
            winner = 2
        }
        // row 2
        else if(player1Moves.contains(4)&& player1Moves.contains(5) && player1Moves.contains(6)){
            winner = 1
        }
        else if (player2Moves.contains(4)&& player2Moves.contains(5) && player2Moves.contains(6)){
            winner = 2
        }
        // row 3
        else if(player1Moves.contains(7)&& player1Moves.contains(8) && player1Moves.contains(9)){
            winner = 1
        }
        else if (player2Moves.contains(7)&& player2Moves.contains(8) && player2Moves.contains(9)){
            winner = 2
        }
        // col 1
        else if(player1Moves.contains(1)&& player1Moves.contains(4) && player1Moves.contains(7)){
            winner = 1
        }
        else if (player2Moves.contains(1)&& player2Moves.contains(4) && player2Moves.contains(7)){
            winner = 2
        }
        // col 2
        else if(player1Moves.contains(2)&& player1Moves.contains(5) && player1Moves.contains(8)){
            winner = 1
        }
        else if (player2Moves.contains(2)&& player2Moves.contains(5) && player2Moves.contains(8)){
            winner = 2
        }
        // col 3
        else if(player1Moves.contains(3)&& player1Moves.contains(6) && player1Moves.contains(9)){
            winner = 1
        }
        else if (player2Moves.contains(3)&& player2Moves.contains(6) && player2Moves.contains(9)){
            winner = 2
        }
        // diag 1
        else if(player1Moves.contains(1)&& player1Moves.contains(5) && player1Moves.contains(9)){
            winner = 1
        }
        else if (player2Moves.contains(1)&& player2Moves.contains(5) && player2Moves.contains(9)){
            winner = 2
        }
        // diag 2
        else if(player1Moves.contains(3)&& player1Moves.contains(5) && player1Moves.contains(7)){
            winner = 1
        }
        else if (player2Moves.contains(3)&& player2Moves.contains(5) && player2Moves.contains(7)){
            winner = 2
        }


        if(winner ==1){
            Toast.makeText(this, "The winner is 1", Toast.LENGTH_SHORT).show()
            disableButtons()
        }
        else if (winner == 2){
            Toast.makeText(this, "The winner is 2", Toast.LENGTH_SHORT).show()
            disableButtons()
        }

    }

    fun disableButtons(){
        button1.isEnabled = false
        button2.isEnabled = false
        button3.isEnabled = false
        button4.isEnabled = false
        button5.isEnabled = false
        button6.isEnabled = false
        button7.isEnabled = false
        button8.isEnabled = false
        button9.isEnabled = false
    }

    fun refreshButtons(){
        button1.text = ""
        button2.text = ""
        button3.text = ""
        button4.text = ""
        button5.text = ""
        button6.text = ""
        button7.text = ""
        button8.text = ""
        button9.text = ""

        button1.isEnabled = true
        button2.isEnabled = true
        button3.isEnabled = true
        button4.isEnabled = true
        button5.isEnabled = true
        button6.isEnabled = true
        button7.isEnabled = true
        button8.isEnabled = true
        button9.isEnabled = true

        button1.setBackgroundResource(R.color.blocksColor)
        button2.setBackgroundResource(R.color.blocksColor)
        button3.setBackgroundResource(R.color.blocksColor)
        button4.setBackgroundResource(R.color.blocksColor)
        button5.setBackgroundResource(R.color.blocksColor)
        button6.setBackgroundResource(R.color.blocksColor)
        button7.setBackgroundResource(R.color.blocksColor)
        button8.setBackgroundResource(R.color.blocksColor)
        button9.setBackgroundResource(R.color.blocksColor)
        activePlayer = 1
        player1Moves.clear()
        player2Moves.clear()
    }

}
