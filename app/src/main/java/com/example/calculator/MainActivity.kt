package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.calculator.model.CalcVisor
import com.example.calculator.constants.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {

    private val model: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listButton = listOf<View>(
            btnNum0, btnNum1, btnNum2, btnNum3,
            btnNum4, btnNum5, btnNum6, btnNum7,
            btnNum8, btnNum9, btnPonto, btnAc,
            btnLimpa, btnMais, btnMenos, btnMultiplica,
            btnDivide, btnIgual, btnSinal
        )

        (btnNum0 as Button).text = N0
        (btnNum1 as Button).text = N1
        (btnNum2 as Button).text = N2
        (btnNum3 as Button).text = N3
        (btnNum4 as Button).text = N4
        (btnNum5 as Button).text = N5
        (btnNum6 as Button).text = N6
        (btnNum7 as Button).text = N7
        (btnNum8 as Button).text = N8
        (btnNum9 as Button).text = N9
        (btnMais as Button).text = PLUS
        (btnPonto as Button).text = POINT
        (btnAc as Button).text = AC
        (btnMenos as Button).text = MINUS
        (btnMultiplica as Button).text = MULTIPLY
        (btnDivide as Button).text = DIVIDE
        (btnIgual as Button).text = EQUAL
        (btnSinal as Button).text = SIGNAL

        for (x in listButton) {
            x.click()
        }

        model.getNumbers().observe(this, Observer<CalcVisor> {
            tvNumbers.text = it.number
            tvCalc.text = it.calc
        })
    }

    private fun getBtnConstant(any: Any): Any? {
        return when (any) {
            btnNum0 -> N0
            btnNum1 -> N1
            btnNum2 -> N2
            btnNum3 -> N3
            btnNum4 -> N4
            btnNum5 -> N5
            btnNum6 -> N6
            btnNum7 -> N7
            btnNum8 -> N8
            btnNum9 -> N9
            btnPonto -> POINT
            btnSinal -> SIGNAL
            btnLimpa -> BACK
            btnAc -> AC
            btnMais -> PLUS
            btnMenos -> MINUS
            btnMultiplica -> MULTIPLY
            btnDivide -> DIVIDE
            btnIgual -> EQUAL
            else -> null
        }
    }

    private fun View.click() {
        this.setOnClickListener {
            send(it)
        }
    }

    fun send(v: View) {
        val c = getBtnConstant(v)
        if (c != null) {
            model.setClick(c)
        }
    }
}
