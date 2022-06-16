package com.gggccca7x.reversewordle

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.util.*
import kotlin.random.Random.Default.nextInt

//TODO:
//1 - scoring logic
//2 - rearrange letters
//3 - make neater
//4 - start new game button
//5 - options/additional features??

//Rules:
//Same as wordle, but with points:
// +1 for every grey letter at bottom
// -1 for every orange letter guessed
// -3 for every green letter guessed
// +10 for correct word on the 6th attempt
class MainActivity : AppCompatActivity() {

    //the text views of all guessed letters (6x5 grid)
    private val letterTextViewsArray = arrayListOf<TextView>()
    private val textViewIdsArray = arrayOf(
            R.id.textView11, R.id.textView12, R.id.textView13, R.id.textView14, R.id.textView15,
            R.id.textView21, R.id.textView22, R.id.textView23, R.id.textView24, R.id.textView25,
            R.id.textView31, R.id.textView32, R.id.textView33, R.id.textView34, R.id.textView35,
            R.id.textView41, R.id.textView42, R.id.textView43, R.id.textView44, R.id.textView45,
            R.id.textView51, R.id.textView52, R.id.textView53, R.id.textView54, R.id.textView55,
            R.id.textView61, R.id.textView62, R.id.textView63, R.id.textView64, R.id.textView65
    )

    //all letter buttons (A-Z)
    private val letterButtonsArray = arrayListOf<Button>()
    private val buttonIdsArray = arrayOf(
            R.id.buttonA, R.id.buttonB, R.id.buttonC, R.id.buttonD, R.id.buttonE, R.id.buttonF, R.id.buttonG,
            R.id.buttonH, R.id.buttonI, R.id.buttonJ, R.id.buttonK, R.id.buttonL, R.id.buttonM, R.id.buttonN,
            R.id.buttonO, R.id.buttonP, R.id.buttonQ, R.id.buttonR, R.id.buttonS, R.id.buttonT, R.id.buttonU,
            R.id.buttonV, R.id.buttonW, R.id.buttonX, R.id.buttonY, R.id.buttonZ
    )
    private val letters = arrayOf("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
            "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z")


    //other buttons / textboxes
    private lateinit var textViewTesting: TextView
    private lateinit var buttonBackspace: Button
    private lateinit var buttonEnter: Button
    private lateinit var buttonReset: Button
    private lateinit var textViewScore: TextView

    private var column = 0
    private var row = 0
    private var guessedWord = ""
    private var correctWord = ""

    private var score = 0
    private var guessedGreyLettersArrayList = arrayListOf<Char>()

    private var wordsArrayList= arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initComponents()
        initClickListeners()

        getWordsList()
        //have to call to set word to guess
        resetButtonPressed()
    }

    //to start game or reset
    private fun resetButtonPressed() {
        column = 0
        row = 0
        guessedWord = ""
        correctWord = "" //remove? is set in method below
        score = 0
        mainLooper.run {
            textViewTesting.text = "word is $correctWord"
            textViewScore.text = ""
            setRandomWord()
            println("clear purple")
            clearTextViews()
            clearLetters()
            guessedGreyLettersArrayList.clear()
        }
    }

    //start of new game clear all text views
    private fun clearTextViews(){
        for(tv in letterTextViewsArray){
            tv.text = ""
            tv.setBackgroundColor(resources.getColor(R.color.purple_200, null))
        }
    }

    //randomly generate starting word
    private fun setRandomWord(){
        val r = Random()
        val randomIndex = r.nextInt(wordsArrayList.size)
        correctWord = wordsArrayList[randomIndex].toUpperCase(Locale.ROOT)
        println("The word to guess is: $correctWord")
    }

    //populate word arrayList in memory
    private fun getWordsList(){
        val res = resources
        val inputStream : InputStream = res.openRawResource(R.raw.words_list)

        var line : String? = ""
        val reader = BufferedReader(InputStreamReader(inputStream))

        while (line != null) {
            line = reader.readLine()
            wordsArrayList.add(line)
        }
    }

    private fun setTextView(letter: String){
        when (column) {
            0 -> {
                column++
                letterTextViewsArray[0 + 5 * row].text = letter
                guessedWord += letter
            }
            1 -> {
                column++
                letterTextViewsArray[1 + 5 * row].text = letter
                guessedWord += letter
            }
            2 -> {
                column++
                letterTextViewsArray[2 + 5 * row].text = letter
                guessedWord += letter
            }
            3 -> {
                column++
                letterTextViewsArray[3 + 5 * row].text = letter
                guessedWord += letter
            }
            4 -> {
                column++
                letterTextViewsArray[4 + 5 * row].text = letter
                guessedWord += letter
            }
        }
        textViewTesting.text = guessedWord
    }

    private fun backspaceButtonPressed(){
        when (column) {
            1 -> {
                column--
                letterTextViewsArray[0 + 5 * row].text = ""
                guessedWord = guessedWord.substring(0, guessedWord.length - 1)
                textViewTesting.text = guessedWord
            }
            2 -> {
                column--
                letterTextViewsArray[1 + 5 * row].text = ""
                guessedWord = guessedWord.substring(0, guessedWord.length - 1)
                textViewTesting.text = guessedWord
            }
            3 -> {
                column--
                letterTextViewsArray[2 + 5 * row].text = ""
                guessedWord = guessedWord.substring(0, guessedWord.length - 1)
                textViewTesting.text = guessedWord
            }
            4 -> {
                column--
                letterTextViewsArray[3 + 5 * row].text = ""
                guessedWord = guessedWord.substring(0, guessedWord.length - 1)
                textViewTesting.text = guessedWord
            }
            5 -> {
                column--
                letterTextViewsArray[4 + 5 * row].text = ""
                guessedWord = guessedWord.substring(0, guessedWord.length - 1)
                textViewTesting.text = guessedWord
            }
        }
    }

    private fun enterButtonPressed(){
        //TODO: change colours
        //TODO: handle second orange letter incorrectly set
        //TODO: if grey letter already guessed don't add points
        if(column == 5) {
            val isRealWord = checkWord(guessedWord)
            if(isRealWord) {
                //real word
                //handle scoring in here
                var tempScore = 0
                val isCorrectWord = guessedWord == correctWord
                //set array value to 1 when letter accounted for, for turning green or orange
                val identifiedLettersList = arrayOf(0, 0, 0, 0, 0)
                for ((i, c) in guessedWord.withIndex()) {
                    val letterIndex = findIndex(letters, c.toString())
                    if(c == correctWord[i]){
                        letterTextViewsArray[i + 5 * row].setBackgroundColor(Color.GREEN)
                        letterButtonsArray[letterIndex].setBackgroundColor(Color.GREEN)
                        tempScore-= 3
                        identifiedLettersList[i] = 1
                    }
                }
                for ((i, c) in guessedWord.withIndex()) {
                    val letterIndex = findIndex(letters, c.toString())
                    when {
                        c == correctWord[i] -> {
                            //do nothing, just prevent other stuff running
                        }
                        correctWord.contains(c) -> {
                            letterTextViewsArray[i + 5 * row].setBackgroundColor(Color.GRAY)
                            for((i2, c2) in correctWord.withIndex()){
                                if(c == c2 && identifiedLettersList[i2] != 1){
                                    //so repeated letters don't add points
                                    tempScore--
                                    identifiedLettersList[i2] = 1
                                    letterTextViewsArray[i + 5 * row].setBackgroundColor(Color.RED)
                                    letterButtonsArray[letterIndex].setBackgroundColor(Color.RED)
                                }
                            }
                        }
                        else -> {
                            letterTextViewsArray[i + 5 * row].setBackgroundColor(Color.GRAY)
                            letterButtonsArray[letterIndex].setBackgroundColor(Color.GRAY)
                            //don't add score to grey letters twice
                            if(!guessedGreyLettersArrayList.contains(c)){
                                guessedGreyLettersArrayList.add(c)
                                tempScore++
                            }
                        }
                    }
                }
                //TODO: tidy up
                if(row == 5){
                    //last row
                    if(isCorrectWord){
                        score += 10
                        textViewScore.text = "Final score is $score"
                    } else {
                        score += tempScore
                        textViewScore.text = "Final score is $score"
                    }
                } else {
                    if(isCorrectWord){
                        score += tempScore
                        textViewScore.text = "Final score is $score"
                    } else {
                        score += tempScore
                        textViewScore.text = "Current score is $score"
                    }
                }

                column = 0
                row++
                guessedWord = ""
            } else {
                //not real word
                //TODO: toast?
                println("NOT A REAL WORD, PICK A DIFFERENT ONE")
            }
        }
    }

    //to be called in reset function, to set all letters back to default colour
    private fun clearLetters() {
        for(but: Button in letterButtonsArray){
            but.setBackgroundColor(Color.BLUE)
        }
    }

    private fun checkWord(word: String) : Boolean {
        return wordsArrayList.contains(word.toLowerCase(Locale.ROOT))
    }

    private fun findIndex(arr: Array<String>, item: String) : Int {
        return arr.indexOf(item)
    }

    private fun initComponents(){
        for ((i, _) in textViewIdsArray.withIndex()) {
            val textView: TextView = findViewById(textViewIdsArray[i])
            letterTextViewsArray.add(textView)
        }
        for ((i, _) in buttonIdsArray.withIndex()) {
            val textView: Button = findViewById(buttonIdsArray[i])
            letterButtonsArray.add(textView)
        }

        textViewTesting = findViewById(R.id.textViewTesting)
        buttonBackspace = findViewById(R.id.buttonBackspace)
        buttonEnter = findViewById(R.id.buttonEnter)
        buttonReset = findViewById(R.id.buttonReset)
        textViewScore = findViewById(R.id.textViewScore)
    }

    private fun initClickListeners(){
        buttonBackspace.setOnClickListener {
            backspaceButtonPressed()
        }
        buttonEnter.setOnClickListener {
            enterButtonPressed()
        }
        buttonReset.setOnClickListener {
            resetButtonPressed()
        }
        for ((i, b) in letterButtonsArray.withIndex()){
            b.setOnClickListener {
                setTextView(letters[i])
            }
        }
    }
}