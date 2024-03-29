/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package com.gradle.lab

import com.gradle.lab.old.OldMessage

class App {

    companion object {
        @JvmStatic
        val HELLO = Message("Hello World!")
    }

    val greeting: String
        get() {
            return HELLO.textWithTime
        }
}

fun main() {
    println(App().greeting)
    println(App.HELLO.hasWhitespace())
    println(App.HELLO.getTextHash())
    println(OldMessage("hello there").hasWhitespace())
}
