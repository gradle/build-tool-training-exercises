plugins {
    `java-library`
    id("shared-tasks-convention")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.guava:guava:30.0-jre")
    api("org.json:json:20220924")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
