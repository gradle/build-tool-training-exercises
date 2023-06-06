package com.gradle.lab.calc

import com.gradle.lab.calc.Calc.evalExpression
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class CalcTest {

    @Test
    fun eval_good() {
        assertEquals("5", evalExpression("2+3"))
        assertEquals("2", evalExpression("5-3"))
        assertEquals("6", evalExpression("2*3"))
        assertEquals("3", evalExpression("9/3"))
        assertNull(evalExpression("2+3*"), "invalid input")
    }
}