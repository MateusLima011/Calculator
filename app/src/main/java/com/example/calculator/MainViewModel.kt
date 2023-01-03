package com.example.calculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.calculator.constants.*
import com.example.calculator.model.CalcVisor
import net.objecthunter.exp4j.ExpressionBuilder


class MainViewModel : ViewModel() {

    private val visor = CalcVisor()
    private var equalPress = false
    private var hasError = false

    private val calcVisor: MutableLiveData<CalcVisor> by lazy {
        MutableLiveData<CalcVisor>()
    }

    fun getNumbers(): LiveData<CalcVisor> {
        return calcVisor
    }

    fun setClick(c: Any?) {
        if (hasError) {
            if (c == AC) processClick(AC)
        } else processClick(c.toString())
    }

    private fun processClick(c: String) {
        when (c) {
            AC -> resetCalc()
            BACK -> {
                if (visor.number.length > 1) {
                    visor.number = visor.number.substring(0, visor.number.length - 1)
                } else {
                    visor.number = N0
                }
            }
            POINT -> {
                if (!visor.number.contains(POINT)) {
                    if (visor.number == "") visor.number = "$N0." else visor.number += "$c"
                }
            }
            SIGNAL -> {
                visor.number =
                    if (visor.number.isNotEmpty() && visor.number.first() == MINUS.first()) {
                        visor.number.substring(1, visor.number.length)
                    } else {
                        "$MINUS${visor.number}"
                    }
            }
            PLUS -> setCalc(PLUS)
            MINUS -> setCalc(MINUS)
            MULTIPLY -> setCalc(MULTIPLY)
            DIVIDE -> setCalc(DIVIDE)
            EQUAL -> {
                visor.calc += visor.number
                makeCalc(visor.calc)
            }
            else -> {
                try {
                    c.toInt()
                    when (visor.number) {
                        N0 -> visor.number = ""
                        "$MINUS$N0" -> visor.number = "$MINUS"
                    }
                    visor.number += "$c"
                } catch (e: java.lang.Exception) {
                    hasError = true
                    visor.number = ERROR
                }
            }
        }
        calcVisor.value = visor
    }

    private fun resetCalc() {
        hasError = false
        equalPress = false
        visor.calc = ""
        visor.number = N0
    }

    private fun setCalc(str: String) {
        if (equalPress) {
            visor.calc = ""
            equalPress = false
        }
        visor.calc += "${visor.number}${str}"
        visor.number = N0
    }

    private fun makeCalc(str: String) {
        var result: String
        try {
            val replaceStr = str.replace(PLUS, "+")
                .replace(MINUS, "-")
                .replace(DIVIDE, "/")
                .replace(MULTIPLY, "*")
            val calc = ExpressionBuilder(replaceStr).build()
            result = calc.evaluate().toString()
            visor.calc += EQUAL
        } catch (e: Exception) {
            hasError = true
            result = ERROR
        }

        visor.number =
            if (result.substring(
                    result.length - 2,
                    result.length
                ) == "$POINT$N0"
            ) result.substring(
                0,
                result.length - 2
            ) else result
        equalPress = true
    }

}