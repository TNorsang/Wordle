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
    private val clickedLetters = StringBuilder()
    private val maxLetters = 4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firstWordDisplay = findViewById(R.id.firstWordDisplay)

    }
    fun onKeyClick(view: View) : StringBuilder {
        if (view is Button) {

            val letterClicked = view.text.toString()

            if(clickedLetters.length < maxLetters){
                clickedLetters.append(letterClicked)
                firstWordDisplay.text = clickedLetters.toString()

                val answer = checkGuess(clickedLetters)
                Log.d("Main Activity","Answer:$answer")

            }
        }

        return clickedLetters
    }

    fun checkGuess(guess: StringBuilder) : String {
        val fourLetterWordsList = FourLetterWordLists
        val wordToGuess = fourLetterWordsList.getRandomFourLetterWord()

        Log.d("MainActivity", "Random Four-Letter Word: $wordToGuess")
        var result = ""
        for (i in 0..3) {
            if (guess[i] == wordToGuess[i]) {
                result += "O"
            }
            else if (guess[i] in wordToGuess) {
                result += "+"
            }
            else {
                result += "X"
            }
        }
        return result
    }


}