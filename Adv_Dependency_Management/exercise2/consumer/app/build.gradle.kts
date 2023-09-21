plugins {
    application
}

repositories {
    mavenCentral()
}

dependencies {
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

application {
    mainClass.set("com.gradlelab.consumer.App")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
