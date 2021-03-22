package tg.nscode.calculatorapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var lastNumeric: Boolean = false
    var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onDigit(view: View) {
        val tvInput: TextView = findViewById(R.id.tvInput)

        tvInput.append((view as Button).text)
        lastNumeric = true
    }

    fun onClear(view: View) {
        val tvInput: TextView = findViewById(R.id.tvInput)

        tvInput.text = ""
        lastNumeric = false
        lastDot = false
    }

    fun onDecimalPoint(view: View) {
        val tvInput: TextView = findViewById(R.id.tvInput)

        if (lastNumeric && !lastDot) {
            tvInput.append(".")
            lastNumeric = false
            lastDot = true
        } else {

        }
    }

    fun onOperator(view: View) {
        if (lastNumeric && !isOperatorAdded(tvInput.text.toString())) {
            tvInput.append((view as Button).text)
            lastNumeric = false
            lastDot = false
        }
    }

    private fun isOperatorAdded(value: String): Boolean {
        return if (value.startsWith("-")) {
            false
        } else {
            value.contains("/") || value.contains("*") || value.contains("+") || value.contains("-")
        }
    }

    private fun removeZeroAfterDow(result: String) : String {
        var value = result
        if(result.contains(".0"))
            value = result.substring(0, result.length - 2)
        return value
    }

    fun onEqual(view: View) {
        if (lastNumeric) {
            var tvValue = tvInput.text.toString()
            var prefix = ""
            try {

                if (tvValue.startsWith("-")) {
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }

                // Substraction
                if (tvValue.contains("-")) {
                    val splitValue = tvValue.split("-")

                    var one = splitValue[0]
                    var two = splitValue[1]

                    if (!prefix.isEmpty()) {
                        one = prefix + one
                    }

                    tvInput.text = removeZeroAfterDow((one.toDouble() - two.toDouble()).toString())
                }

                // Addition
                if (tvValue.contains("+")) {
                    val splitValue = tvValue.split("+")

                    var one = splitValue[0]
                    var two = splitValue[1]

                    if (!prefix.isEmpty()) {
                        one = prefix + one
                    }

                    tvInput.text = removeZeroAfterDow((one.toDouble() + two.toDouble()).toString())
                }

                // Multiplication
                if (tvValue.contains("*")) {
                    val splitValue = tvValue.split("*")

                    var one = splitValue[0]
                    var two = splitValue[1]

                    if (!prefix.isEmpty()) {
                        one = prefix + one
                    }

                    tvInput.text = removeZeroAfterDow((one.toDouble() * two.toDouble()).toString())
                }

                // Division
                if (tvValue.contains("/")) {
                    val splitValue = tvValue.split("/")

                    var one = splitValue[0]
                    var two = splitValue[1]

                    if (!prefix.isEmpty()) {
                        one = prefix + one
                    }

                    tvInput.text = removeZeroAfterDow((one.toDouble() / two.toDouble()).toString())
                }
            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }
        }
    }
}