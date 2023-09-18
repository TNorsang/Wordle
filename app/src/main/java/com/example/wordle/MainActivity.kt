package com.example.wordle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView

import com.example.wordle.FourLetterWordLists

class MainActivity : AppCompatActivity() {
    private lateinit var displayLetterTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        displayLetterTextView = findViewById(R.id.displayLetterTextView)
    }
    fun onKeyClick(view: View){
        if (view is Button) {
            val letterClicked = view.text.toString()
            displayLetterTextView.text = letterClicked
        }
    }

    fun checkGuess(guess: String) : String {
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