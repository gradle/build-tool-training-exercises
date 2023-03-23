plugins {
    application
    id("com.gradlelab.coveragelock")
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")

    implementation("com.google.guava:guava:31.0.1-jre")
}

application {
    mainClass.set("com.gradlelab.App")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
