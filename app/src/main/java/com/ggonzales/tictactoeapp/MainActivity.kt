package com.ggonzales.tictactoeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.database.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private var mFirebaseAnalytics : FirebaseAnalytics? = null
    //DB
    private var database = FirebaseDatabase.getInstance()
    private var myRef = database.reference
    var myEmail : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)

        var bundle : Bundle = intent.extras!!
        myEmail = bundle.getString("Email")
        currUserTxtView.text = myEmail
        getIncomingRequests()

        refreshButton.setOnClickListener {
            resetButtons()
        }
        newGameButton.setOnClickListener {
            startNewGame()
        }
        requestButton.setOnClickListener {
            requestFromPlayer()
        }
        acceptButton.setOnClickListener {
            acceptPlayer()
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
        Log.d("App Clicks", "${blockId}")
//        playGame(blockId, buttonSelected)
        myRef.child("PlayOnline").child(sessionID!!).child("cell-$blockId").setValue(myEmail)
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
//            if(winner == -1) onlinePlay()

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

    fun onlinePlay(autoSelID : Int) {
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

    //buttons Function
    protected fun requestFromPlayer(){
        //it receives the username only
        val secPlayerEmail = nameEText.text.toString().replace("@gmail.com","")
        myRef.child("Users").child(secPlayerEmail).child("Request").push().setValue(myEmail)
        setMatch(splitEmail(myEmail!!)+splitEmail(secPlayerEmail))
        playerSymbol = "X"

    }
    protected fun acceptPlayer(){
        val secPlayerEmail = nameEText.text.toString()
        //since the whole email is displayed, we have to use split
        myRef.child("Users").child(splitEmail(secPlayerEmail)).child("Request").push().setValue(myEmail)
        Toast.makeText(applicationContext, "Invitation Accepted", Toast.LENGTH_SHORT).show()
        setMatch(splitEmail(secPlayerEmail)+splitEmail(myEmail!!))
        playerSymbol = "0"
        nameEText.isEnabled = false
    }
    var sessionID : String? = null
    var playerSymbol : String? = null

    fun setMatch(sessionID : String){
        this.sessionID = sessionID
        myRef.child("PlayOnline").removeValue()
        myRef.child("PlayOnline").child(sessionID)
            .addValueEventListener(object:ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    try{
                        player1Moves.clear()
                        player2Moves.clear()
                        var datasnap=snapshot.getValue(object: GenericTypeIndicator<HashMap<String, Any>>() { })
                        if (datasnap != null) {
                            var value:String
                            for (key in datasnap.keys){
                                value= datasnap[key] as String

                                if(value!= myEmail){
                                    activePlayer= if(playerSymbol==="X") 1 else 2
                                }else{
                                    activePlayer= if(playerSymbol==="X") 2 else 1
                                }
                                onlinePlay(key.replace("cell-", "").toInt())
                            }
                        }
                    }catch (ex:Exception){
                        Log.e("Exception-Data Changed", "Saving PlayOnline", ex)
                    }
                }

                override fun onCancelled(p0: DatabaseError) {
                    Log.d("Match Cancelled", p0.toException().toString())
                }

            })
    }

    var number = 0
    fun getIncomingRequests(){
        myRef.child("Users").child(splitEmail(myEmail!!)).child("Request")
            .addValueEventListener(object:ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    try{
                        var datasnap=snapshot.getValue(object: GenericTypeIndicator<HashMap<String, Any>>() { })
                        if(datasnap != null){
                            var invitingPlayer : String
                            for(key in datasnap.keys){
                                Log.d("Result invitingPlayer", datasnap[key].toString())
                                invitingPlayer = datasnap[key] as String
                                nameEText.setText(invitingPlayer)

                                var notifyCurrPlayer = Notification()
                                notifyCurrPlayer!!.notifyRequests(applicationContext, invitingPlayer + " wants to play TicTacToe", number)
                                number++
                                break
                            }
                        }
                    }catch (ex: Exception){
                        Log.e("Exception IncomRequest", ex.message, ex)
                    }
                }

                override fun onCancelled(p0: DatabaseError) {
                    Log.d("Request Cancelled", p0.toException().toString())
                }

            })
    }
    fun splitEmail(email: String) : String {
        val username = email.split("@")
        return username[0]
    }


}


