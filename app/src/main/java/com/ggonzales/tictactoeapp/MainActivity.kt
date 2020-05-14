package com.ggonzales.tictactoeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private var mFirebaseAnalytics : FirebaseAnalytics? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)

        refreshButton.setOnClickListener {
            resetButtons()
        }
        newGameButton.setOnClickListener {
            startNewGame()
        }

    }
    fun startNewGame(){
        val buttonsList : ArrayList<Button> = arrayListOf(button1, button2, button3, button4, button5, button6, button7, button8, button9)

        for(button in buttonsList){
            resetActiveButton(button)
        }

        activePlayer = 1
        player1Moves.clear()
        player2Moves.clear()
//        resultTxtView.text = ""
    }


    fun resetButtons(){
        val buttonsList : ArrayList<Button> = arrayListOf(button1, button2, button3, button4, button5, button6, button7, button8, button9)

        for(button in buttonsList){
            resetActiveButton(button)
        }

        activePlayer = 1
        player1Moves.clear()
        player2Moves.clear()
        player1Wins = 0
        player2Wins = 0
        resultTxtView.text = ""
    }

    fun resetActiveButton(activeButton: Button){
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
    var winner = -1
    var player1Moves = ArrayList<Int>()
    var player2Moves = ArrayList<Int>()
    var player1Wins = 0
    var player2Wins = 0

    fun playGame(blockId : Int, buttonSelected : Button){
        if(activePlayer == 1){
            //   Player 1
            buttonSelected.text = "X"
            buttonSelected.setBackgroundResource(R.color.backgroundPlayer1)
            player1Moves.add(blockId)
            activePlayer = 2
            checkWinner()
            if(winner == -1) autoPlay()

        }
        else{
            //   Player 2
            buttonSelected.text = "0"
            buttonSelected.setBackgroundResource(R.color.backgroundPlayer2)
            player2Moves.add(blockId)
            activePlayer = 1
            checkWinner()
        }
        buttonSelected.isEnabled = false
    }

    fun checkWinner(){
        winner = -1
        // row 1
        when {
            player1Moves.containsAll(listOf(1,2,3)) -> winner = 1
            player2Moves.containsAll(listOf(1,2,3)) -> winner = 2
            // row 2
            player1Moves.containsAll(listOf(4,5,6)) -> winner = 1
            player2Moves.containsAll(listOf(4,5,6)) -> winner = 2
            // row 3
            player1Moves.containsAll(listOf(7,8,9)) -> winner = 1
            player2Moves.containsAll(listOf(7,8,9)) -> winner = 2
            // col 1
            player1Moves.containsAll(listOf(1,4,7)) -> winner = 1
            player2Moves.containsAll(listOf(1,4,7)) -> winner = 2
            // col 2
            player1Moves.containsAll(listOf(2,5,8)) -> winner = 1
            player2Moves.containsAll(listOf(2,5,8)) -> winner = 2
            // col 3
            player1Moves.containsAll(listOf(3,6,9)) -> winner = 1
            player2Moves.containsAll(listOf(3,6,9)) -> winner = 2
            // diag 1
            player1Moves.containsAll(listOf(1,5,9)) -> winner = 1
            player2Moves.containsAll(listOf(1,5,9)) -> winner = 2
            // diag 2
            player1Moves.containsAll(listOf(3,5,7)) -> winner = 1
            player2Moves.containsAll(listOf(3,5,7)) -> winner = 2
        }
        if(winner ==1){
            Toast.makeText(this, "Player1 is the Winner!", Toast.LENGTH_SHORT).show()
            player1Wins += 1
            resultTxtView.text = "PlayerA: $player1Wins - Auto: $player2Wins"
            disableButtons()
        }
        else if (winner == 2){
            Toast.makeText(this, "Player2 is the Winner!", Toast.LENGTH_SHORT).show()
            player2Wins += 1
            resultTxtView.text = "PlayerA: $player1Wins - Auto: $player2Wins"
            disableButtons()
        }
        else if(player1Moves.size + player2Moves.size == 9){
            Toast.makeText(this, "Tie Game!", Toast.LENGTH_SHORT).show()
            resultTxtView.text = "PlayerA: $player1Wins - Auto: $player2Wins"
            disableButtons()
        }

    }

    fun disableButtons() {
        val buttonsList : ArrayList<Button> = arrayListOf(button1, button2, button3, button4, button5, button6, button7, button8, button9)

        for(button in buttonsList){
            button.isEnabled = false
        }
    }

    fun autoPlay() {
        val emptyblocksList = ArrayList<Int>()
        for(x in 1..9){
            if(!(player1Moves.contains(x) || player2Moves.contains(x))){
                emptyblocksList.add(x)
            }
        }

        if(emptyblocksList.size > 0){
            val r = Random
            val randInx = r.nextInt(emptyblocksList.size)
            val autoSelID = emptyblocksList[randInx]
            var autoSelBtn : Button? = null
            when(autoSelID){
                1 -> autoSelBtn = button1
                2 -> autoSelBtn = button2
                3 -> autoSelBtn = button3
                4 -> autoSelBtn = button4
                5 -> autoSelBtn = button5
                6 -> autoSelBtn = button6
                7 -> autoSelBtn = button7
                8 -> autoSelBtn = button8
                9 -> autoSelBtn = button9
            }
            playGame(autoSelID, autoSelBtn!!)

        }
    }
}


