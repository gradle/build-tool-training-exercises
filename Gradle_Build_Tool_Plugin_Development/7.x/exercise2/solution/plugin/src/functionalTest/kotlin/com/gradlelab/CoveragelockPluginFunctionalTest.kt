/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package com.gradlelab

import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import java.io.File
import kotlin.test.Test
import kotlin.test.assertTrue

/**
 * A simple functional test for the 'com.gradlelab.greeting' plugin.
 */
class CoveragelockPluginFunctionalTest {

    @get:Rule val tempFolder = TemporaryFolder()

    @Test fun `no coverage file`() {
        setupTestProject("just_plugin_applied")
        val log : String = runAndGetLogs(false, "jacocoTestReport");
        assertTrue(log.contains("but expected minimum is 0.8"))
    }

    @Test fun `with coverage file`() {
        setupTestProject("just_plugin_with_file")
        val log : String = runAndGetLogs(false, "jacocoTestReport");
        assertTrue(log.contains("but expected minimum is 0.6"))
    }

    private fun runAndGetLogs(expectSuccess: Boolean, taskToRun: String): String {
        val runner = GradleRunner.create()
        runner.forwardOutput()
        runner.withPluginClasspath()
        runner.withProjectDir(tempFolder.root)
        runner.withArguments(taskToRun)
        val result : BuildResult = if (expectSuccess) runner.build() else runner.buildAndFail()

        return result.output
    }

    private fun setupTestProject(dir : String) {
        val srcDir = File("src/functionalTest/resources", dir)
        srcDir.copyRecursively(tempFolder.root)
    }
}
