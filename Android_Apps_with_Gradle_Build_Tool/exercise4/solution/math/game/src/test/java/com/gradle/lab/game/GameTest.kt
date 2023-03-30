package com.gradle.lab.game

import java.util.regex.Pattern

import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class GameTest {

    @Test
    fun generateNextQuestion() {
        assertNotNull(Game.generateAddQuestion(), "should return question")
    }

    @Test
    fun generateAddQuestion() {
        val question = Game.generateAddQuestion()
        val regex = "[0-9]+\\+[0-9]+"
        val pattern = Pattern.compile(regex)
        val matcher = pattern.matcher(question)
        assertTrue(matcher.matches(), "should match regex")
    }

    @Test
    fun generateSubtractionQuestion() {
        val question = Game.generateSubtractionQuestion()
        val regex = "[0-9]+-[0-9]+"
        val pattern = Pattern.compile(regex)
        val matcher = pattern.matcher(question)
        assertTrue(matcher.matches(), "should match regex")
    }

    @Test
    fun generateMultiplyQuestion() {
        val question = Game.generateMultiplyQuestion()
        val regex = "[0-9]+\\*[0-9]+"
        val pattern = Pattern.compile(regex)
        val matcher = pattern.matcher(question)
        assertTrue(matcher.matches(), "should match regex")
    }
}