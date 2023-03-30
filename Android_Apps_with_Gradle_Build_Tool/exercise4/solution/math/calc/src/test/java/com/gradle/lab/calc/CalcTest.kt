package com.gradle.lab.calc

import com.gradle.lab.calc.Calc.evalExpression
import com.gradle.lab.calc.Calc.isZeroString

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class CalcTest {

    @Test
    fun zerostring_null() {
        assertFalse(isZeroString(null), "null should return false")
    }

    @Test
    fun zerostring_zeros() {
        assertTrue(isZeroString("0000"), "0000 should return true")
        assertTrue(isZeroString("  0000  "), "0000 with whitespace should return true")
    }

    @Test
    fun zerostring_other() {
        assertFalse(isZeroString("0000."), "other text should return false")
        assertFalse(isZeroString("5+0"), "other text should return false")
    }

    @Test
    fun eval_good() {
        assertEquals("5", evalExpression("2+3"))
        assertEquals("2", evalExpression("5-3"))
        assertEquals("6", evalExpression("2*3"))
        assertEquals("3", evalExpression("9/3"))
        assertNull(evalExpression("2+3*"), "invalid input")
    }
}