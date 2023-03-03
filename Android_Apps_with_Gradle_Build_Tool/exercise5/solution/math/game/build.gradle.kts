plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    id("kotlin-test-coverage")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

dependencies {
    testImplementation(kotlin("test"))
}
