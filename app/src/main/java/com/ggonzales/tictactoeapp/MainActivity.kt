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

    fun refreshButtons(){
        val buttonsList : ArrayList<Button> = arrayListOf(button1, button2, button3, button4, button5, button6, button7, button8, button9)

        for(button in buttonsList){
            refreshActiveButton(button)
        }

        activePlayer = 1
        player1Moves.clear()
        player2Moves.clear()
        resultTxtView.text = ""
    }

    fun refreshActiveButton(activeButton: Button){
        activeButton.text = ""
        activeButton.isEnabled = true
        activeButton.setBackgroundResource(R.color.blocksColor)
    }

    // directly called from each button (xml)
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
        if(player1Moves.containsAll(listOf(1,2,3))){
            winner = 1
        }
        else if(player2Moves.containsAll(listOf(1,2,3))){
            winner = 2
        }
        // row 2
        else if(player1Moves.containsAll(listOf(4,5,6))){
            winner = 1
        }
        else if (player2Moves.containsAll(listOf(4,5,6))){
            winner = 2
        }
        // row 3
        else if(player1Moves.containsAll(listOf(7,8,9))){
            winner = 1
        }
        else if (player2Moves.containsAll(listOf(7,8,9))){
            winner = 2
        }
        // col 1
        else if(player1Moves.containsAll(listOf(1,4,7))){
            winner = 1
        }
        else if (player2Moves.containsAll(listOf(1,4,7))){
            winner = 2
        }
        // col 2
        else if(player1Moves.containsAll(listOf(2,5,8))){
            winner = 1
        }
        else if (player2Moves.containsAll(listOf(2,5,8))){
            winner = 2
        }
        // col 3
        else if(player1Moves.containsAll(listOf(3,6,9))){
            winner = 1
        }
        else if (player2Moves.containsAll(listOf(3,6,9))){
            winner = 2
        }
        // diag 1
        else if(player1Moves.containsAll(listOf(1,5,9))){
            winner = 1
        }
        else if (player2Moves.containsAll(listOf(1,5,9))){
            winner = 2
        }
        // diag 2
        else if(player1Moves.containsAll(listOf(3,5,7))){
            winner = 1
        }
        else if (player2Moves.containsAll(listOf(3,5,7))){
            winner = 2
        }

        if(winner ==1){
            resultTxtView.text = "Player1 is the Winner"
            disableButtons()
        }
        else if (winner == 2){
            resultTxtView.text = "Player2 is the Winner"
            disableButtons()
        }
        else if(button1.text !="" && button2.text !="" && button3.text !="" && button4.text !="" && button5.text !="" && button6.text !="" && button7.text !="" && button8.text !="" && button9.text !=""){
            resultTxtView.text = "Tie Game!"
            disableButtons()
        }

    }

    fun disableButtons(){
        val buttonsList : ArrayList<Button> = arrayListOf(button1, button2, button3, button4, button5, button6, button7, button8, button9)

        for(button in buttonsList){
            button.isEnabled = false
        }
    }
}


