package com.gradle.lab.game

import java.util.*

object Game {

    private val RANDOM = Random()

    fun generateNextQuestion(): String {
        val type = RANDOM.nextInt(3)

        val question = when (type) {
            0 -> generateAddQuestion()
            1 -> generateSubtractionQuestion()
            else -> generateMultiplyQuestion()
        }

        return question
    }

    /**
     * Generate an addition question with 2 numbers.
     */
    fun generateAddQuestion(): String {
        val firstNumber = RANDOM.nextInt(990) + 11
        val secondNumber = RANDOM.nextInt(990) + 11
        return "$firstNumber+$secondNumber"
    }

    /**
     * Generate a subtraction question with 2 numbers.
     */
    fun generateSubtractionQuestion(): String {
        val firstNumber = RANDOM.nextInt(950) + 51
        val secondNumber = RANDOM.nextInt(firstNumber - 20) + 11
        return "$firstNumber-$secondNumber"
    }

    /**
     * Generate a multiplication question with 2 numbers.
     */
    fun generateMultiplyQuestion(): String {
        val firstNumber = RANDOM.nextInt(27) + 4
        val secondNumber = RANDOM.nextInt(27) + 4
        return "$firstNumber*$secondNumber"
    }
}
