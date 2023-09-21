plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.4.0"
}

dependencyResolutionManagement {
    repositories {
        maven {
            url = uri("/tmp/repo")
        }
    }
    versionCatalogs {
        create("mycatalog") {
            from("com.gradlelab:mycatalog:1.2")
        }
    }
}

rootProject.name = "consumer"
include("app")
