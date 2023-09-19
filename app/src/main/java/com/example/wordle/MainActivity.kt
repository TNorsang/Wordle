package com.example.wordle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.text.SpannableStringBuilder
import android.text.Spannable
import android.text.style.ForegroundColorSpan
import android.graphics.Color
import android.widget.Toast

import com.example.wordle.FourLetterWordLists

class MainActivity : AppCompatActivity() {
    enum class GameState {
        RUNNING, WIN, LOSE
    }

    private lateinit var firstWordDisplay: TextView
    private lateinit var secondWordDisplay: TextView
    private lateinit var thirdWordDisplay: TextView

    private var fourLetterWordsList = FourLetterWordLists
    private var wordToGuess = fourLetterWordsList.getRandomFourLetterWord()

    private val maxLetters = 4
    private var clickedLetters = StringBuilder()
    private var remainingChances = 3 // Set the initial number of chances

    private var gameState = GameState.RUNNING

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firstWordDisplay = findViewById(R.id.firstWordDisplay)
        secondWordDisplay = findViewById(R.id.secondWordDisplay)
        thirdWordDisplay = findViewById(R.id.thirdWordDisplay)

        val guessButton = findViewById<Button>(R.id.guessButton)


        guessButton.setOnClickListener{
            if (remainingChances > 0) {
                Log.d("MainActivity", "Answer: $wordToGuess")
                var answer = checkGuess(clickedLetters)
                Log.d("MainActivity", "Answer: $answer")
                clickedLetters.setLength(0)
                Log.d("MainActivity", "Clicked Letters Should be empty: $clickedLetters")
            }
        }
    }

    private fun restartGame() {
        // Reset game-related variables
        gameState = GameState.RUNNING
        remainingChances = 3 // Set the initial number of chances
        clickedLetters.clear()
        wordToGuess = fourLetterWordsList.getRandomFourLetterWord()

        // Clear TextViews
        firstWordDisplay.text = ""
        secondWordDisplay.text = ""
        thirdWordDisplay.text = ""

        // Enable the guess button
        val guessButton = findViewById<Button>(R.id.guessButton)
        guessButton.isEnabled = true
    }

    fun onKeyClick(view: View) : StringBuilder {
        if (view is Button) {

            var letterClicked = view.text.toString()

            if (clickedLetters.length < maxLetters){
                clickedLetters.append(letterClicked)
                if (remainingChances == 3){
                    firstWordDisplay.text = clickedLetters.toString()
                } else if (remainingChances == 2){
                    secondWordDisplay.text = clickedLetters.toString()
                } else if (remainingChances == 1){
                    thirdWordDisplay.text = clickedLetters.toString()
                }
            }
        }
        println(clickedLetters)
        return clickedLetters
    }

    fun checkGuess(guess: StringBuilder): CharSequence {
        val builder = SpannableStringBuilder()

        for (i in 0 until maxLetters) {
            val guessedLetter = guess[i]
            val correctLetter = wordToGuess[i]

            val spannable = SpannableStringBuilder(guessedLetter.toString())

            if (guessedLetter == correctLetter) {
                spannable.setSpan(
                    ForegroundColorSpan(Color.GREEN),
                    0, 1,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            } else if (correctLetter in guess.toString()) {
                spannable.setSpan(
                    ForegroundColorSpan(Color.YELLOW),
                    0, 1,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            } else {
                spannable.setSpan(
                    ForegroundColorSpan(Color.RED),
                    0, 1,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
            builder.append(spannable)
        }

        if (remainingChances == 3) {
            firstWordDisplay.text = builder
            remainingChances--
        } else if (remainingChances == 2) {
            secondWordDisplay.text = builder
            remainingChances--
        } else if (remainingChances == 1) {
            thirdWordDisplay.text = builder
            remainingChances--
        }

        if(builder.toString() == wordToGuess){
            gameState = GameState.WIN
        } else if (remainingChances == 0){
            gameState = GameState.LOSE
        }


        if (gameState == GameState.WIN) {
            // Display a win message using Toast
            Toast.makeText(this, "Congratulations! You guessed the word correctly.", Toast.LENGTH_LONG).show()
            restartGame()
        } else if (gameState == GameState.LOSE) {
            // Display a lose message using Toast
            Log.d("Main Activity","Inside Game State Lose")
            Toast.makeText(this, "Sorry, you ran out of chances. The word was $wordToGuess", Toast.LENGTH_LONG).show()
            restartGame()
        }

        return builder
    }

}
