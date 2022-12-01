plugins {
    id("java")
}

group = "com.gradle.demo"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.withType<Test> {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}