/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Java application project to get you started.
 * For more details take a look at the 'Building Java & JVM projects' chapter in the Gradle
 * User Manual available at https://docs.gradle.org/7.4.2/userguide/building_java_projects.html
 */

plugins {
    // Apply the application plugin to add support for building a CLI application in Java.
    application
    id("org.barfuin.gradle.taskinfo") version "1.4.0"
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    // Use JUnit Jupiter for testing.
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")

    // This dependency is used by the application.
    implementation("com.google.guava:guava:30.1.1-jre")
}

application {
    // Define the main class for the application.
    mainClass.set("com.gradle.lab.App")
}

tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}

tasks.register("testWithMsg") {
    group = "verification"
    description = "Runs tests and prints msg when done"
    dependsOn("test")

    doLast {
        println("Tests done!")
    }
}

tasks.register("msgAfterTest") {
    group = "verification"
    description = "Prints msg when tests are done"

    doLast {
        println("Tests done!!")
    }
}

tasks.named("test") {
    finalizedBy("msgAfterTest")
}

tasks.register<Copy>("backupTestXml") {
    from("build/test-results/test")
    into("/tmp/")

    exclude("binary/**")
}
