## Advanced Dependency Management - Exercise 1

<p align="left">
<img width="10%" height="10%" src="https://user-images.githubusercontent.com/120980/174325546-8558160b-7f16-42cb-af0f-511849f22ebc.png">
</p>

This is a hands-on exercise to go along with the
**Advanced Dependency Management** training module. In this exercise
you will go over the following:

* `Bucket` vs `Resolved` dependency configurations
* Impact of `api` vs `implementation` dependency configurations on downstream projects

---
### Prerequisites

* JDK 1.8+ and latest Gradle build tool installed
    * https://gradle.org/install/
* Gradle Build Tool experience
    * Knowledge of core concepts
    * Authoring build files
    * Kotlin experience a plus but not required
* Basic experience with Java software development

---
### `Bucket` vs `Resolved` dependency configurations

Open the Gradle project in the `lab` folder. You can either open a terminal
and go to the folder, or open the project in an editor such as IntelliJ.

Inspect the build and source files. There are different subprojects including
`app` and `model`. We will be working with these subprojects in this exercise.
Notice there are currently no dependencies defined for the `model` subproject
and the only dependency of the `app` subproject is the `model` subproject.

Add a dependency in the `model` subproject to
[guava version "30.0-jre"](https://mvnrepository.com/artifact/com.google.guava/guava/30.0-jre):

```kotlin
implementation("com.google.guava:guava:30.0-jre")
```

Run the `dependencies` task and notice all the dependency configurations
that are available even though you have only used the `implementation`
one so far.

```bash
./gradlew :model:dependencies
```

Now run the `dependencies` task and inspect the `implementation` configuration:

```bash
./gradlew :model:dependencies --configuration=implementation
```

Notice the `guava` dependency is the only one listed. Also notice that Gradle
informs you that the `implementation` dependency configuration is *not
resolved*.

<p align="center">
<img width="75%" height="75%" src="https://user-images.githubusercontent.com/120980/200845071-f3127e11-fc10-4a03-8b35-78f2b13dc411.png">
</p>

Now inspect the `compileClasspath` and `runtimeClasspath` dependency
configurations:

```bash
./gradlew :model:dependencies --configuration=compileClasspath
./gradlew :model:dependencies --configuration=runtimeClasspath
```

Notice these dependency configurations include the transitive dependencies
unlike `implementation`. Also notice there is no indication that
these are not resolved - if there is no indication then the
dependency configurations *are resolved*.

In this case the two dependency configurations are the same, the
`guava` dependency along with its transitive dependencies are needed
both during compilation as well as execution.

### Impact of `api` vs `implementation`

Inspect the dependency configurations on the `app` subproject.

```bash
./gradlew :app:dependencies
```

The `implementation` declared dependency configuration only has the
`model` subproject. Notice the `compileClasspath` and `runtimeClasspath`
resolved dependency configurations are different. The `runtimeClasspath`
includes `guava` and its transitive dependencies while the `compileClasspath`
does not.

This is because `guava` was in the `implementation` dependency
configuration of the `model` subproject, an indication to Gradle that
it's not needed for compilation for downstream projects.

<p align="center">
<img width="75%" height="75%" src="https://user-images.githubusercontent.com/120980/200845540-aad80126-9397-45f8-befc-142003753682.png">
</p>

<p align="center">
<img width="75%" height="75%" src="https://user-images.githubusercontent.com/120980/200845695-4995e4a7-def6-416e-954f-29e77b80a24e.png">
</p>

Now add the
[json version "20220924"](https://mvnrepository.com/artifact/org.json/json/20220924)
dependency to the `api` dependency configuration in the `model`
subproject:

```kotlin
api("org.json:json:20220924")
```

Inspect the `compileClasspath` and `runtimeClasspath` dependency configurations
for the `app` subproject:

```bash
./gradlew :app:dependencies --configuration=compileClasspath
./gradlew :app:dependencies --configuration=runtimeClasspath
```

Notice the `json` dependency appears in both dependency configurations.
Dependencies in the `api` configuration are used both during compilation
and execution of downstream projects.

<p align="right">
<a href="https://github.com/gradle/build-tool-training-exercises/tree/main/Adv_Dependency_Management/exercise2">Exercise 2 >></a>
</p>
