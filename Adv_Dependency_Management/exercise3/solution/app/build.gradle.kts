plugins {
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.bundles.core.libs)
    implementation(libs.gson)

    testImplementation(libs.junit)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

application {
    mainClass.set("com.gradlelab.App")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
