package com.gradle.lab.calc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

/**
 * A simple [Fragment] subclass.
 */
class CalcFragment : Fragment() {

    private var equationField: TextView? = null
    private var resultField: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_calc, container, false)

        equationField = view.findViewById(R.id.equation_field)
        resultField = view.findViewById(R.id.result_field)

        initNumOrOperationButton(view.findViewById(R.id.button_0))
        initNumOrOperationButton(view.findViewById(R.id.button_1))
        initNumOrOperationButton(view.findViewById(R.id.button_2))
        initNumOrOperationButton(view.findViewById(R.id.button_3))
        initNumOrOperationButton(view.findViewById(R.id.button_4))
        initNumOrOperationButton(view.findViewById(R.id.button_5))
        initNumOrOperationButton(view.findViewById(R.id.button_6))
        initNumOrOperationButton(view.findViewById(R.id.button_7))
        initNumOrOperationButton(view.findViewById(R.id.button_8))
        initNumOrOperationButton(view.findViewById(R.id.button_9))
        initNumOrOperationButton(view.findViewById(R.id.button_add))
        initNumOrOperationButton(view.findViewById(R.id.button_subtract))
        initNumOrOperationButton(view.findViewById(R.id.button_multiply))
        initNumOrOperationButton(view.findViewById(R.id.button_divide))
        initNumOrOperationButton(view.findViewById(R.id.button_dot))

        initEqualsButton(view.findViewById(R.id.button_equals))
        initAcButton(view.findViewById(R.id.button_ac))

        initDelButton(view.findViewById(R.id.button_del))

        return view
    }

    private fun initNumOrOperationButton(button: Button) {
        button.setOnClickListener {
            // If there are only 0s so far, clear them.
            if (equationField!!.text != null && equationField!!.text.isNotEmpty()) {
                val currentEquation = equationField!!.text.toString().trim()
                if (Calc.isZeroString(currentEquation)) {
                    equationField!!.text = ""
                }
            }

            equationField!!.append(button.text)
        }
    }

    private fun initEqualsButton(button: Button) {
        button.setOnClickListener(View.OnClickListener {
            if (equationField!!.text == null) {
                return@OnClickListener
            }
            val currentEquation = equationField!!.text.toString().trim { it <= ' ' }
            if (currentEquation == "") {
                return@OnClickListener
            }
            val result: String? = Calc.evalExpression(currentEquation)
            if (result == null) {
                resultField!!.text = getString(R.string.error)
            } else {
                resultField!!.text = result
                equationField!!.text = ""
            }
        })
    }

    private fun initAcButton(button: Button) {
        button.setOnClickListener {
            resultField!!.text = ""
            equationField!!.text = ""
        }
    }

    private fun initDelButton(button: Button) {
        button.setOnClickListener(View.OnClickListener {
            if (equationField!!.text == null || equationField!!.text.isEmpty()) {
                return@OnClickListener
            }
            val currentEquation = equationField!!.text.toString()
            equationField!!.text = currentEquation.substring(0, currentEquation.length - 1)
        })
    }
}