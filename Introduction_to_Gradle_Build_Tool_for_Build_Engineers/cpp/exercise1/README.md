## Introduction to Gradle Build Tool for Build Engineers - Exercise 1

<p align="left">
<img width="10%" height="10%" src="https://user-images.githubusercontent.com/120980/174325546-8558160b-7f16-42cb-af0f-511849f22ebc.png">
</p>

This is a hands-on exercise to go along with the
**Introduction to Gradle Build Tool for Build Engineers** training module. In this exercise you
will get familiar with the following topics:

* See available tasks
* Create custom tasks

---
### Prerequisites

* JDK 1.8+ and Gradle build tool installed
    * https://gradle.org/install/
* A good editor such as IntelliJ IDE
    * https://www.jetbrains.com/idea/download/

---
### See Available Tasks

Open the Gradle project in the `lab` folder.

Open a `Terminal` tab in the project directory and
run `./gradlew :app:tasks --all`. Scroll up and explore the tasks.

<p align="center">
<img width="60%" height="60%" src="https://github.com/gradle/build-tool-training-exercises/assets/120980/e0996084-8785-4520-853e-8f8f70c05722">
</p>

---
### Create Custom Tasks

#### Task With dependsOn

Now let's create some custom tasks. First, let's add a task that will run the
`build` task as a dependency and print a message when done. The task will:

1. Be called `buildWithMsg`
2. Be part of the `verification` group
3. Have an appropriate description
4. Run the `build` task as a dependency (*hint*: use `dependsOn`)
5. Print the message `"Build Done!"` as it's action (*hint*: use `doLast`)

```groovy
tasks.register("buildWithMsg") {
    // Implement the task here.
}
```

Once finished be sure to refresh the Gradle configs in the IDE.

<p align="center">
<img width="50%" height="50%" src="https://user-images.githubusercontent.com/120980/174329910-859ff843-b26b-4a67-a017-d1126c7eb032.png">
</p>

Now run the `buildWithMsg` task and see it run the build and print the message.

<p align="center">
<img width="40%" height="40%" src="https://user-images.githubusercontent.com/120980/174330023-ce004bf9-ced8-420a-9d00-64d3775eba8c.png">
</p>

You can refer to the [Solution](solution/app/build.gradle.kts#L44) if you
get stuck.

#### Task Using finalizedBy

Next let's create a similar task that prints a message when the build is done.
However this task will not depend on the `build` task. Instead use `finalizedBy`
to define a dependency to run the task after the `build` task runs. The task
will:

1. Be called `msgAfterBuild`
2. Be part of the `verification` group
3. Have an appropriate description
4. Print the message `"Build Done!!"` as it's action (*hint*: use `doLast`)

```groovy
tasks.register("msgAfterBuild") {
    // Implement the task here.
}
```

Then add a dependency using `finalizedBy`:

```groovy
tasks.named("test") {
    // Finalized goes here.
}
```

Once finished be sure to refresh the Gradle configs in the IDE.

<p align="center">
<img width="50%" height="50%" src="https://user-images.githubusercontent.com/120980/174330117-7309624c-221b-4a0c-a1ea-a32b9c86c290.png">
</p>

Now run the `test` task and see it run tests and print the message.

<p align="center">
<img width="40%" height="40%" src="https://user-images.githubusercontent.com/120980/174330193-b5d084fa-a295-40aa-b839-f693f54584d0.png">
</p>

You can refer to the [Solution](solution/app/build.gradle.kts#L54) if you
get stuck.

#### Bonus: Copy Task

Let's create a task that copies the XML test result report to the `tmp`
folder. *Note*: We only want to copy the one XML file, not the `binary`
directory.

<p align="center">
<img width="40%" height="40%" src="https://user-images.githubusercontent.com/120980/174330257-0a94a29d-f623-48b2-a4f5-829fd95561d2.png">
</p>

Refer to the [Copy Task docs](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.Copy.html)
for help. The task will:

1. Be called `backupExe`
2. Copy from the `build/exe/main/debug/` directory into `/tmp/exe/`

```groovy
tasks.register("backupExe", Copy) {
    // Implement the task here.
}
```

You can refer to the [Solution](solution/app/build.gradle.kts) if you
get stuck.

<p align="right">
<a href="https://github.com/gradle/build-tool-training-exercises/tree/main/Introduction_to_Gradle_Build_Tool_for_Build_Engineers/cpp/exercise2">Exercise 2 >></a>
</p>
