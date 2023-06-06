plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    id("kotlin-test-coverage")
    id("org.barfuin.gradle.jacocolog")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

dependencies {
    testImplementation(kotlin("test"))
}
