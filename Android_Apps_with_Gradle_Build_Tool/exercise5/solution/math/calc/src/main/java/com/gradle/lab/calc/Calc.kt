package com.gradle.lab.calc

import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder
import java.util.regex.Pattern

object Calc {

    private const val ZERO_STRING = "0+"
    private val ZERO_PATTERN = Pattern.compile(ZERO_STRING)

    fun isZeroString(str: String?): Boolean {
        if (str == null) {
            return false
        }

        val matcher = ZERO_PATTERN.matcher(str.trim())

        return matcher.matches()
    }

    fun evalExpression(expressionStr: String?): String? {
        return try {
            val expression: Expression = ExpressionBuilder(expressionStr).build()
            var result = expression.evaluate().toString()

            // Remove trailing .0 if its there.
            if (result.endsWith(".0")) {
                result = result.substring(0, result.length - 2)
            }

            result
        } catch (ex: Exception) {
            null
        }
    }
}
