plugins {
    application
    id("task-config-convention")
}

repositories {
    mavenCentral()
    maven {
        url = uri("https://packages.atlassian.com/maven-public/")
    }
    maven {
        url = uri("https://packages.atlassian.com/maven-public-snapshot/")
    }
    maven {
        url = uri("https://packages.atlassian.com/maven-external/")
    }
    maven {
        url = uri("https://repository.jboss.org/nexus/content/repositories/releases/")
    }
    google()
}

configurations.all {
    resolutionStrategy.cacheDynamicVersionsFor(0, "minutes")
    resolutionStrategy.cacheChangingModulesFor(0, "minutes")
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.1")
    implementation("com.google.guava:guava:31.1-jre")

    // From Google's maven repository.
    implementation("android.arch.core:common:1.1.1+")
    implementation("android.arch.lifecycle:common:1.1.1+")
    implementation("android.arch.navigation:navigation-common:1.0.0+")
    implementation("android.arch.navigation:navigation-common-ktx:1.0.0+")
    implementation("android.arch.navigation:navigation-fragment:1.0.0+")
    implementation("android.arch.navigation:navigation-fragment-ktx:1.0.0+")
    implementation("android.arch.paging:common:1.0.1+")
    implementation("android.arch.persistence:db:1.1.1+")
    implementation("android.arch.work:work-runtime:1.0.1+")
    implementation("androidx.arch.core:core-common:2.2.0+")
    implementation("androidx.asynclayoutinflater:asynclayoutinflater:1.0.0+")
    implementation("androidx.benchmark:benchmark:1.0.0-alpha03+")
    implementation("androidx.car:car:1.0.0-alpha7+")
    implementation("androidx.cardview:cardview:1.0.0+")
    implementation("com.google.gms:google-services:4.3.15+")
    implementation("com.google.firebase:firebase-common:20.3.3+")
    implementation("com.google.firebase:firebase-messaging:21.1.0+")
    implementation("com.google.firebase:firebase-invites:15.0.0+")
    implementation("com.google.firebase:firebase-ml-common:16.2.3+")
    implementation("com.google.firebase:firebase-ml-natural-language-translate:21.0.1+")
    implementation("com.google.firebase:firebase-storage:17.0.0+")
    implementation("com.google.firebase:firebase-measurement-connector:18.0.0+")
    implementation("com.google.firebase:firebase-database:20.0.2+")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

application {
    mainClass.set("com.gradle.lab.App")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

tasks.named<Tar>("distTar") {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.named<Zip>("distZip") {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.create("printClasspath") {
    val libraryNames = configurations.getByName("compileClasspath").map { it.name }

    doLast {
        logger.quiet(libraryNames.joinToString())
    }
}
