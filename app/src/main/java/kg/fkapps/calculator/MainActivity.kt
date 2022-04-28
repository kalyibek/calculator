package kg.fkapps.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import kg.fkapps.calculator.R
import java.lang.ArithmeticException


class MainActivity : AppCompatActivity() {

    private var lastNumeric: Boolean = false
    private var lastDot: Boolean = false
    private var inputTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        inputTextView = findViewById(R.id.inputTextView)
    }

    fun onDigit(view: View) {
        inputTextView?.append((view as Button).text)
        lastNumeric = true
        lastDot = false
    }

    fun onOperator(view: View) {
        inputTextView?.text?.let {
            if (lastNumeric && !isOperatorAdded(it.toString())) {
                inputTextView?.append((view as Button).text)
                lastNumeric = false
                lastDot = false
            }
        }
    }

    fun onClear(view: View) {
        inputTextView?.text = ""
        lastNumeric = false
        lastDot = false
    }

    fun onDecimalPoint(view: View) {
        if (lastNumeric && !lastDot) {
            inputTextView?.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onEqual(view: View) {
        if (lastNumeric) {
            var valueOfTV = inputTextView?.text.toString()
            var prefix = ""
            try {
                if (valueOfTV.startsWith("-")) {
                    prefix = valueOfTV[0].toString()
                    valueOfTV = valueOfTV.substring(1)
                }
                when {
                    valueOfTV.contains("/") -> {
                        val splitValue = valueOfTV.split("/")

                        var firstDigit = splitValue[0]
                        val secondDigit = splitValue[1]

                        if (prefix.isNotEmpty()) {
                            firstDigit = prefix + firstDigit
                        }
                        inputTextView?.text =
                            removeZeroAfterDot((firstDigit.toDouble() / secondDigit.toDouble()).toString())
                    }

                    valueOfTV.contains("*") -> {
                        val splitValue = valueOfTV.split("*")

                        var firstDigit = splitValue[0]
                        val secondDigit = splitValue[1]

                        if (prefix.isNotEmpty()) {
                            firstDigit = prefix + firstDigit
                        }
                        inputTextView?.text =
                            removeZeroAfterDot((firstDigit.toDouble() * secondDigit.toDouble()).toString())
                    }

                    valueOfTV.contains("-") -> {
                        val splitValue = valueOfTV.split("-")

                        var firstDigit = splitValue[0]
                        val secondDigit = splitValue[1]

                        if (prefix.isNotEmpty()) {
                            firstDigit = prefix + firstDigit
                        }
                        inputTextView?.text =
                            removeZeroAfterDot((firstDigit.toDouble() - secondDigit.toDouble()).toString())
                    }

                    valueOfTV.contains("+") -> {
                        val splitValue = valueOfTV.split("+")

                        var firstDigit = splitValue[0]
                        val secondDigit = splitValue[1]

                        if (prefix.isNotEmpty()) {
                            firstDigit = prefix + firstDigit
                        }
                        inputTextView?.text =
                            removeZeroAfterDot((firstDigit.toDouble() + secondDigit.toDouble()).toString())
                    }
                }
            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }
        }
    }

    private fun removeZeroAfterDot(result: String): String {

        var value = result

        if (result.contains(".0")) {
            value = result.substring(0, result.length - 2)
        }

        return value
    }

    private fun isOperatorAdded(value: String): Boolean {

        return if (value.startsWith("-")) {
            false
        } else {
            (value.contains("/")
                    || value.contains("*")
                    || value.contains("-")
                    || value.contains("+"))
        }
    }

}