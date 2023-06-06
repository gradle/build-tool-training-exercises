package com.gradle.lab.calc

import com.gradle.lab.calc.Calc.isZeroString
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import kotlin.test.assertEquals

@RunWith(Parameterized::class)
class ZeroStringTest(private val input: String?, private val expected: Boolean) {

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{index}: input={0}, expected={1}")
        fun data(): Collection<Array<Any?>> {
            return listOf(
                arrayOf("0000", true),
                arrayOf("0", true),
                arrayOf("  0000  ", true),
                arrayOf(null, false),
                arrayOf("0000.", false),
                arrayOf("5+0", false),
                arrayOf("", false),
                arrayOf(" ", false),
                arrayOf("0001", false),
                arrayOf("  000abc000  ", false),
            )
        }
    }

    @Test
    fun testIsZeroString() {
        assertEquals(isZeroString(input), expected, "$input should return $expected")
    }
}