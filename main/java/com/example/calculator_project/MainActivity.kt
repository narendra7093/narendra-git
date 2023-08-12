package com.example.calculator_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.calculator_project.databinding.ActivityMainBinding
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.ArithmeticException


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var lastnumeric = false
    var stateError = false
    var lastdot = false

    lateinit var expression: Expression

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    fun OnClearClick(view: View) {
        binding.dataTv.text = ""
        lastnumeric = false
    }

    fun OnBackSpaceClick(view: View) {
        binding.dataTv.text = binding.dataTv.text.dropLast(1)
        try {
            val lastchar = binding.dataTv.text.last()
            if (lastchar.isDigit()) {
                Equal()
            }
        } catch (e: Exception) {
            binding.resultTv.text = ""
            binding.resultTv.visibility = View.VISIBLE
            Log.e("LastChar error", e.toString())
        }
    }

    fun OnOperatorClick(view: View) {
        if (!stateError && lastnumeric) {
            binding.dataTv.append((view as Button).text).toString()
            lastnumeric = false
            lastdot = false
            Equal()
        }
    }

    fun OnDigitClick(view: View) {
        if (stateError) {
            binding.dataTv.text = (view as Button).text
            stateError = false
        } else {
            binding.dataTv.append((view as Button).text)
        }
        lastnumeric = true
        Equal()
    }

    fun OnEqualClick(view: View) {
        Equal()
        binding.dataTv.text = binding.resultTv.text.toString().drop(1)

    }

    fun AllClearClick(view: View) {
        binding.dataTv.text = ""
        binding.resultTv.text = ""
        stateError = false
        lastnumeric = false
        lastdot = false
    }

    fun Equal() {
        if (lastnumeric && !stateError) {
            var txt = binding.dataTv.text.toString()
            expression = ExpressionBuilder(txt).build()
            try {
                var result = expression.evaluate()
                binding.resultTv.visibility = View.VISIBLE
                binding.resultTv.text = "=" + result.toString()
            } catch (e: ArithmeticException) {
                Log.e("Evaluation Error", e.toString())
                binding.resultTv.text = ""
                stateError = true
                lastnumeric = false
            }
        }

    }
}