plugins {
    application
}

repositories {
    mavenCentral()
}

java {
    consistentResolution {
        useRuntimeClasspathVersions()
    }
}

dependencies {
    implementation(project(":model"))
    implementation("com.google.inject:guice:5.1.0")
    runtimeOnly("org.checkerframework:checker-qual:3.28.0")

    constraints {
        implementation("com.google.guava:guava") {
            version {
                //strictly("[28.0-jre, 30.0-jre]")
                strictly("[28.0-jre, 28.5-jre]")
            }
        }
    }
}

application {
    mainClass.set("com.gradle.lab.App")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
