package com.example.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.text.DecimalFormat

enum class CalculatorMode {
    None, Add, Substract
}

class MainActivity : AppCompatActivity() {

    var lastButtonWasMode = false
    var currentMode = CalculatorMode.None
    var labelString = ""
    var savedNum = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupCalculator()
    }

    fun setupCalculator () {
        val allButtons = arrayOf (button0,button1,button2,button3,button4,button5,button6,button7,button8,button9)
        for (i in allButtons.indices) {
            allButtons [i].setOnClickListener {didPressNumber(i)}
        }
        buttonPlus.setOnClickListener { changeMode( CalculatorMode.Add) }
        buttonMinus.setOnClickListener { changeMode( CalculatorMode.Substract) }
        buttonEquals.setOnClickListener { didPressEquals() }
        buttonClear.setOnClickListener { didPressClear() }

    }

    fun didPressEquals () {

        if (lastButtonWasMode) {
            return
        }

        val labelInt = labelString.toInt()
        when (currentMode) {
            CalculatorMode.Add -> savedNum +=  labelInt
            CalculatorMode.Substract -> savedNum -=  labelInt
            CalculatorMode.None -> return
        }

        currentMode = CalculatorMode.None
        labelString = savedNum.toString()
        updateText()
        lastButtonWasMode = true
    }

    fun didPressClear () {
        lastButtonWasMode = false
        currentMode = CalculatorMode.None
        labelString = "0"
        savedNum = 0
        updateText()
    }

    fun updateText () {

        if (labelString.length > 8) {
            didPressClear()
            Display.setText("Too Big")
            return
        }

        val labelInt = labelString.toInt()
        val df = DecimalFormat ("#,###")

        Display.setText(df.format(labelInt))

        if (currentMode == CalculatorMode.None) {
            savedNum = labelInt
        }
    }

    fun changeMode (mode: CalculatorMode) {

        if (savedNum == 0) {
            return
        }

        currentMode = mode
        lastButtonWasMode = true

    }

    fun didPressNumber (num: Int) {
        val strVal = num.toString()

        if (lastButtonWasMode) {
            lastButtonWasMode = false
            labelString = "0"
        }

        labelString += strVal
        updateText()
    }

}
