## Introduction to Gradle Build Tool for Build Engineers - Exercise 2

<p align="left">
<img width="10%" height="10%" src="https://user-images.githubusercontent.com/120980/174325546-8558160b-7f16-42cb-af0f-511849f22ebc.png">
</p>

This is a hands-on exercise to go along with the
**Introduction to Gradle Build Tool for Build Engineers** training module. In this exercise
you will get familiar with the following topics:

* Sharing common configuration between projects

---
### Prerequisites

* Completed [exercise 1](../exercise1/README.md)
* You can perform the exercises in the same Gradle project used in the 
previous lab.

---
### Sharing Common Configuration Between Projects

Often in multi-project builds, there is a lot of configuration that is the
same between projects. It is easier to manage the projects if the common
configuration is put in one location and shared between the projects.

This location for shared configuration is the `buildSrc` folder.

First create a folder under the root called `buildSrc/src/main/groovy`.

Then create the file `buildSrc/build.gradle` with the following contents:

```groovy
plugins {
    id "groovy-gradle-plugin"
}

repositories {
    gradlePluginPortal()
}
```

Then create the file `buildSrc/src/main/groovy/shared-build-conventions.gradle`.
In it we will put the common configuration between the `app` and `model` projects,
and also move the custom tasks as we want to make them available for the model
project as well:

```groovy
tasks.register("buildWithMsg") {
    group = "verification"
    description = "Runs build and prints msg when done"
    dependsOn("build")

    doLast {
        println("Build done!")
    }
}

tasks.register("msgAfterBuild") {
    group = "verification"
    description = "Prints msg when build is done"

    doLast {
        println("Build done!!")
    }
}

tasks.named("build") {
    finalizedBy("msgAfterBuild")
}

tasks.register("backupExe", Copy) {
    from("build/exe/main/debug/")
    into("/tmp/exe/")
}
```

We can then add the `shared-build-conventions` plugin to both the `app` and
`model` projects, and remove the common configuration.

The plugins section of `app/build.gradle` will now be:

```groovy
plugins {
    id 'cpp-application'
    id 'cpp-unit-test'
    id 'shared-build-conventions'
}
```

Execute `./gradlew :app:build` to ensure things still work.
