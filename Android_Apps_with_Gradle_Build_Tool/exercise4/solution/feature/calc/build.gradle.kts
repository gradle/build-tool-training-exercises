plugins {
    id("base-android-library")
}

android {
    namespace = "com.gradle.lab.calc"
}

dependencies {
    implementation(project(":math:calc"))
}