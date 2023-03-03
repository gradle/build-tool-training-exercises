## Android Apps with Gradle Build Tool - Exercise 3

<p align="left">
<img width="10%" height="10%" src="https://user-images.githubusercontent.com/120980/174325546-8558160b-7f16-42cb-af0f-511849f22ebc.png">
</p>

This is a hands-on exercise to go along with the
**Android Apps with Gradle Build Tool** training module. In this exercise
you will go over the following:

* Create modules for features

At the end of this exercise you will have the math modules ready.

---
### Prerequisites

* Completed exercise 2
* You can perform the exercises in the same project used in exercise 2

---
### Adding new dependency to version catalog

We will make use of [exp4j](https://en.wikipedia.org/wiki/Exp4j) to
evaluate calculations. Add this dependency to the version catalog.

```text
exp4j = "net.objecthunter:exp4j:0.4.8"
```

We will use the [jacocolog](https://plugins.gradle.org/plugin/org.barfuin.gradle.jacocolog)
plugin for showing more test coverage information.

```text
jacocolog = { id = "org.barfuin.gradle.jacocolog", version = "3.1.0" }
```

You can refer to the full
[version catalog in the solution](solution/gradle/libs.versions.toml)
if you get stuck.

---
### :math:calc module

This module will handle all the math related functionality needed by
the calculator fragment.

* Add a kotlin library module called `:math:calc` with a `Calc` class

<p align="center">
<img width="75%" height="75%" src="https://user-images.githubusercontent.com/120980/220796992-7799b7bf-97fd-4bd6-a798-eb63ca7374e1.png">
</p>

* Update the contents of the [build.gradle.kts](solution/math/calc/build.gradle.kts) to include `exp4j` dependency, java toolchain and test coverage configuration

```kotlin
plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    id("jacoco")
    alias(libs.plugins.jacocolog)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

dependencies {
    implementation(libs.exp4j)

    testImplementation(kotlin("test"))
}

tasks.named<JacocoReport>("jacocoTestReport") {
    dependsOn(tasks.named("test"))
    reports {
        xml.required.set(true)
    }
}
tasks.named<JacocoCoverageVerification>("jacocoTestCoverageVerification") {
    violationRules {
        rule {
            limit {
                counter = "LINE"
                value = "COVEREDRATIO"
                minimum = "0.5".toBigDecimal()
            }
        }
    }
}
tasks.named("check") {
    dependsOn("jacocoTestCoverageVerification")
}
```

* Add code for [Calc.kt](solution/math/calc/src/main/java/com/gradle/lab/calc/Calc.kt)

```kotlin
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
```

* Add code for [CalcTest.kt](solution/math/calc/src/test/java/com/gradle/lab/calc/CalcTest.kt)

```kotlin
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
```

---
### :math:game module

This module will handle all the math related functionality needed by
the game fragment.

* Add a kotlin library module called `:math:game` with a `Game` class
* Update the contents of the [build.gradle.kts](solution/math/game/build.gradle.kts) to include java toolchain and test coverage configuration

```kotlin
plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    id("jacoco")
    alias(libs.plugins.jacocolog)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.named<JacocoReport>("jacocoTestReport") {
    dependsOn(tasks.named("test"))
    reports {
        xml.required.set(true)
    }
}
tasks.named<JacocoCoverageVerification>("jacocoTestCoverageVerification") {
    violationRules {
        rule {
            limit {
                counter = "LINE"
                value = "COVEREDRATIO"
                minimum = "0.5".toBigDecimal()
            }
        }
    }
}
tasks.named("check") {
    dependsOn("jacocoTestCoverageVerification")
}
```

* Add code for [Game.kt](solution/math/game/src/main/java/com/gradle/lab/game/Game.kt)

```kotlin
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
```

* Add code for [GameTest.kt](solution/math/game/src/test/java/com/gradle/lab/game/GameTest.kt)

```kotlin
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
```

---
### Run Test Coverage Report

Run the task `jacocoTestReport` and observe the coverage output:

```bash
> Task :math:calc:jacocoLogTestCoverage
Test Coverage:
    - Class Coverage: 100%
    - Method Coverage: 100%
    - Branch Coverage: 75%
    - Line Coverage: 100%
    - Instruction Coverage: 100%
    - Complexity Coverage: 80%

> Task :math:game:jacocoLogTestCoverage
Test Coverage:
    - Class Coverage: 100%
    - Method Coverage: 80%
    - Branch Coverage: 0%
    - Line Coverage: 62.5%
    - Instruction Coverage: 77.6%
    - Complexity Coverage: 57.1%
```
