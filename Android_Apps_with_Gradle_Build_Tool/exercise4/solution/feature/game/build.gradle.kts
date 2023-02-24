plugins {
    id("base-android-library")
}

android {
    namespace = "com.gradle.lab.game"
}

dependencies {
    implementation(project(":math:calc"))
    implementation(project(":math:game"))
}