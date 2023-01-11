## JVM Builds with Gradle Build Tool - Exercise 4

<p align="left">
<img width="10%" height="10%" src="https://user-images.githubusercontent.com/120980/174325546-8558160b-7f16-42cb-af0f-511849f22ebc.png">
</p>

This is a hands-on exercise to go along with the
**JVM Builds with Gradle Build Tool** training module. In this exercise
you will get familiar with the following topics:

* Creating an UberJar
* Relocating packages using the Shadow plugin

---
### Prerequisites

* Completed exercise 3

---
### Run the Existing Jar

Ensure the jar is created for your project by running:

```bash
./gradlew :<subproject>:jar
```

Look under `<subproject>/build/libs` and notice the jar file.

<p align="center">
<img width="30%" height="30%" src="https://user-images.githubusercontent.com/120980/189362450-e5d49201-d357-47f8-8ff5-2ff1bf2edadb.png">
</p>

Try running it:

```bash
java -jar <subproject>/build/libs/<name>.jar
```

Notice the error indicates that it could not find a dependency.

<p align="center">
<img width="80%" height="80%" src="https://user-images.githubusercontent.com/120980/189363024-f322655a-0a28-44b1-8fd5-934b76414493.png">
</p>

### Create an UberJar using the Shadow Plugin

Apply the `shadow` plugin. You can refer to the
[plugin portal](https://plugins.gradle.org/plugin/com.github.johnrengelman.shadow)
for the plugin ID.

**Note:** Be sure to use the Kotlin plugin apply example, and there is **no
need to specify the version** if you are applying it in the shared convention
plugin since the version is defined in `buildSrc/build.gradle.kts`.

Run the `shadowJar` task:

```bash
./gradlew :<subproject>:shadowJar
```

Look under `<subproject>/build/libs` and notice there is another jar file.

<p align="center">
<img width="30%" height="30%" src="https://user-images.githubusercontent.com/120980/189363243-e136d6c9-8f99-4613-a9c9-9ed71d0601f4.png">
</p>

Try running it:

```bash
java -jar <subproject>/build/libs/<name>-all.jar
```

Notice it runs since it has the dependencies.

### Add Dependency that uses Old Guava

Add an `implementation` dependency to the `guava-old-version` subproject.
Update the `App` class to use the `OldMessage` class from this subproject
(you can just uncomment the import and line of code that uses the class).

<p align="center">
<img width="70%" height="70%" src="https://user-images.githubusercontent.com/120980/189363964-fe14226a-d865-40dc-b38b-f48a585e8a64.png">
</p>

Execute the `run` task and notice the error:

```bash
./gradlew :<subproject>:run
```

<p align="center">
<img width="80%" height="80%" src="https://user-images.githubusercontent.com/120980/189364203-5802b238-884a-497b-bb7f-59e4d3f86f8e.png">
</p>

### Relocate Guava and Update Dependency

Apply the `shadow` plugin to the `guava-old-version` subproject.
You can refer to the
[plugin portal](https://plugins.gradle.org/plugin/com.github.johnrengelman.shadow).

If you get stuck you can refer to the
[solution](solution/guava-old-version/build.gradle.kts).

**Note:** You may not need to specify a version for the plugin just like before.

Add configuration in the subproject to relocate the `com.google.common` package.
Refer to the
[official documentation](https://imperceptiblethoughts.com/shadow/configuration/relocation/#filtering-relocation)
as well as [this example](https://github.com/johnrengelman/shadow/issues/484)

Now update the dependency to `guava-old-version` to use the `shadow` configuration
as per the
[official documentation](https://imperceptiblethoughts.com/shadow/multi-project/#depending-on-the-shadow-jar-from-another-project).
If you get stuck you can refer to the
[solution](solution/buildSrc/src/main/kotlin/shared-build-conventions.gradle.kts#L18).

Now execute the `run` task and notice it works now:

```bash
./gradlew :<subproject>:run
```
