package com.example.wordle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView

import com.example.wordle.FourLetterWordLists
class MainActivity : AppCompatActivity() {
    private lateinit var firstWordDisplay: TextView
    private lateinit var secondWordDisplay: TextView
    private lateinit var thirdWordDisplay: TextView

    private val fourLetterWordsList = FourLetterWordLists
    private val wordToGuess = fourLetterWordsList.getRandomFourLetterWord()
    private var answer : String = ""

    private val maxLetters = 4
    private var clickedLetters = StringBuilder()
    private var remainingChances = 3 // Set the initial number of chances

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firstWordDisplay = findViewById(R.id.firstWordDisplay)
        secondWordDisplay = findViewById(R.id.secondWordDisplay)
        thirdWordDisplay = findViewById(R.id.thirdWordDisplay)

        val guessButton = findViewById<Button>(R.id.guessButton)

        guessButton.setOnClickListener{
            if (remainingChances > 0) {
                answer = checkGuess(clickedLetters)
                Log.d("MainActivity", "Answer: $answer")
                remainingChances-- // Decrease the remaining chances
                clickedLetters.setLength(0)
                Log.d("MainActivity", "Clicked Letters Should be empty: $clickedLetters")
            }
            if (remainingChances == 0) {
                // Disable the button when chances are used up
                guessButton.isEnabled = false
            }

        }
    }

    fun onKeyClick(view: View) : StringBuilder {
        if (view is Button) {

            var letterClicked = view.text.toString()

            if (clickedLetters.length < maxLetters){
                clickedLetters.append(letterClicked)
                firstWordDisplay.text = clickedLetters.toString()
            }
        }
        println(clickedLetters)
        return clickedLetters
    }


    fun checkGuess(guess: StringBuilder) : String {
        Log.d("MainActivity", "Random Four-Letter Word: $wordToGuess")
        for (i in 0..3) {
            if (guess[i] == wordToGuess[i]) {
                answer += "O"
            }
            else if (guess[i] in wordToGuess) {
                answer += "+"
            }
            else {
                answer += "X"
            }
        }
        return answer
    }

}
