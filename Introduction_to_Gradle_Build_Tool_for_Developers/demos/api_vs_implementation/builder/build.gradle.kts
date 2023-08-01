plugins {
    `java-library`
}

group = "com.gradle.demo"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    api(project(":model"))

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
