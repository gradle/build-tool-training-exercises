plugins {
    id("java-library")
    alias(libs.plugins.kotlin.jvm)
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
