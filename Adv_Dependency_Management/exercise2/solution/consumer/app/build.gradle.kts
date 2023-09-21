plugins {
    application
}

repositories {
    mavenCentral()
    maven {
        url = uri("/tmp/repo2")
    }
}

dependencies {
    implementation(platform("com.gradlelab:myplatform:1.3"))
    implementation("com.google.code.gson:gson")
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
