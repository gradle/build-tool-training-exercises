package com.gradlelab

import groovy.util.NodeList
import groovy.xml.XmlParser
import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import java.io.File

abstract class CoverageLockInTask : DefaultTask() {

    @get:Input
    abstract val counter : Property<String>

    @get:Input
    abstract val goal : Property<Float>

    @get:Input
    abstract val onCi : Property<Boolean>

    @get:InputFile
    abstract val reportXmlFile : RegularFileProperty

    @get:OutputFile
    abstract val coverageFile : RegularFileProperty

    @TaskAction
    fun perform() {
        if (!onCi.get()) {
            logger.lifecycle("Not on CI machine, skipping...")
            return
        }

        if (!reportXmlFile.get().asFile.exists()) {
            logger.lifecycle("No Jacoco XML report file found")
            return
        }

        val currentCoverage : Float? = parseCoveragePercentage(reportXmlFile.get().asFile, createXmlParser())

        if (currentCoverage == null) {
            logger.lifecycle("No coverage info found for $counter")
            return
        }

        lockInGains(currentCoverage)

        // Git commit and push here.
    }

    private fun createXmlParser(): XmlParser {
        val parser = XmlParser(false, true, true)
        parser.setFeature("http://apache.org/xml/features/disallow-doctype-decl", false)
        parser.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

        return parser
    }

    private fun parseCoveragePercentage(reportFile: File, parser: XmlParser): Float? {
        val rootNode : groovy.util.Node = parser.parse(reportFile)
        val counters : NodeList = rootNode.get("counter") as NodeList

        counters.forEach { it as groovy.util.Node
            val type : String = it.get("@type") as String
            if (type == counter.get()) {
                val missed : Float = (it.get("@missed") as String).toFloat()
                val covered : Float = (it.get("@covered") as String).toFloat()

                val total : Float = missed + covered
                val percent : Float = covered / total
                return String.format("%.2f", percent).toFloat()
            }
        }

        return null
    }

    private fun lockInGains(currentCoverage: Float) {
        val lockInFile : File = coverageFile.get().asFile

        if (!lockInFile.exists()) {
            // If no coverage file exists, just lock in the current coverage.
            updateLockInFile(currentCoverage, lockInFile)
            return
        }

        val fileValue = try {
            lockInFile.readText().toFloat()
        } catch (e: NumberFormatException) {
            project.logger.error("Invalid coverage lock file: " + lockInFile.absolutePath)
            updateLockInFile(currentCoverage, lockInFile)
            return
        }

        if (currentCoverage > fileValue) {
            logger.lifecycle("Coverage has increased from: $fileValue to: $currentCoverage")

            if (fileValue >= goal.get()) {
                logger.lifecycle("Goal of: " + goal.get().toString() + " already achieved")
                return
            }

            updateLockInFile(currentCoverage, lockInFile)
            return
        }
    }

    private fun updateLockInFile(currentCoverage: Float, lockInFile: File) {
        // If we are at or above the goal, cap the coverage.
        var valueToWrite = currentCoverage
        if (currentCoverage > goal.get()) {
            valueToWrite = goal.get()
        }

        logger.lifecycle("Locking in coverage of: " + valueToWrite.toString() + " to file: " + lockInFile.absolutePath)
        lockInFile.writeText(valueToWrite.toString())
    }
}
