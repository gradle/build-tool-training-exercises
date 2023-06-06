package com.gradle.lab.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.gradle.lab.calc.Calc

/**
 * A simple [Fragment] subclass.
 */
class GameFragment : Fragment() {

    private var gameQuestion: TextView? = null
    private var answerResult: TextView? = null
    private var answer: EditText? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_game, container, false)

        gameQuestion = view.findViewById(R.id.game_question)
        answerResult = view.findViewById(R.id.answer_result)
        answer = view.findViewById(R.id.answer)

        initNextButton(view.findViewById(R.id.button_next))
        initAnswerButton(view.findViewById(R.id.button_answer))

        generateNextQuestion()

        return view
    }

    private fun initNextButton(button: Button) {
        button.setOnClickListener { generateNextQuestion() }
    }

    private fun initAnswerButton(button: Button) {
        button.setOnClickListener { checkAnswer() }
    }

    private fun generateNextQuestion() {
        answerResult!!.text = ""
        answer!!.setText("")
        gameQuestion!!.text = Game.generateNextQuestion()
    }

    private fun checkAnswer() {
        val actual = answer!!.text.toString().trim()
        if (actual == "") {
            return
        }
        val expected: String? =
            Calc.evalExpression(gameQuestion!!.text.toString().trim())
        if (expected == null) {
            answerResult!!.text = getString(R.string.error)
            return
        }
        if (expected == actual) {
            answerResult!!.text = getString(R.string.correct)
        } else {
            answerResult!!.text = getString(R.string.wrong, expected)
        }
    }
}