## JVM Builds with Gradle Build Tool - Exercise 2

<p align="left">
<img width="10%" height="10%" src="https://user-images.githubusercontent.com/120980/174325546-8558160b-7f16-42cb-af0f-511849f22ebc.png">
</p>

This is a hands-on exercise to go along with the
**JVM Builds with Gradle Build Tool** training module. In this exercise
you will get familiar with the following topics:

* Get familiar with Toolchains

---
### Prerequisites

* Completed exercise 1

---
### Configure Java Version for Whole Project

Add a toolchain that specifies to use `Java 11` for the project,
do this in the buildSrc shared configuration.

```kotlin
java {
    toolchain {
        // Your configuration goes here.
    }
}
```

*Hint:* If you get stuck refer to
[this documentation](https://docs.gradle.org/current/userguide/toolchains.html)
or to the
[solution](solution/buildSrc/src/main/kotlin/shared-build-conventions.gradle.kts#L55).

### Configure Java Version for Specific Task

How let's update the `extraSrc` sources to use some new functionality that
was added after Java 11. Modify the `extraSrc` source file to include the
following code in the `main` method:

```java
String msg = "Hello Extra! Time is " + LocalTime.now().toString();
System.out.println(msg.indent(4));
```

<p align="center">
<img width="60%" height="60%" src="https://user-images.githubusercontent.com/120980/189355136-eb522054-3d8d-4285-aee8-4221b94d8fcf.png">
</p>

Now if you try running the code it will fail:

```bash
./gradlew :<subproject>:runExtra
```

<p align="center">
<img width="40%" height="40%" src="https://user-images.githubusercontent.com/120980/189357892-771f2e3e-74ac-4941-9f39-d53710320a9c.png">
</p>

Modify the configuration for the `compileExtraJava` and `runExtra`
tasks to use `Java 17` using the toolchain configuration.

```kotlin
tasks.register<JavaExec>("runExtra") {
    classpath = extraSrc.output + extraSrc.runtimeClasspath
    mainClass.set("Extra")
    // Your configuration here.
}
tasks.named<JavaCompile>("compileExtraJava") {
    // Your configuration here.
}
```

*Hint:* You can refer to
[this documentation](https://docs.gradle.org/current/userguide/toolchains.html#specify_custom_toolchains_for_individual_tasks).
If you get stuck you can refer to the
[solution](solution/buildSrc/src/main/kotlin/shared-build-conventions.gradle.kts#L86).

Now when you execute the `runExtra` task it will succeed:

```bash
./gradlew :<subproject>:runExtra
```

<p align="center">
<img width="40%" height="40%" src="https://user-images.githubusercontent.com/120980/189358329-ec02a131-74cb-4505-95c6-f011ecb0a9a5.png">
</p>

<p align="right">
<a href="https://github.com/gradle/build-tool-training-exercises/tree/main/Jvm_Builds_with_Gradle_Build_Tool/exercise3">Exercise 3 >></a>
</p>
