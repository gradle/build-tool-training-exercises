plugins {
    `java-library`
    id("shared-tasks-convention")
}

repositories {
    mavenCentral()
}

dependencies {
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}