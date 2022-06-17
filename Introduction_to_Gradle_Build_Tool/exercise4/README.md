## Introduction to Gradle Build Tool - Exercise 4

<p align="left">
<img width="10%" height="10%" src="https://raw.githubusercontent.com/gradle/build-tool-training-exercises/main/images/gradle_training_gradient_logo.png">
</p>

This is a hands-on exercise to go along with the
**Introduction to Gradle Build Tool** training module. In this exercise
you will get familiar with the following topics:

* Adding new project to Gradle
* Setting one project as a dependency to another
* Sharing common configuration between projects

---
### Prerequisites

* Completed [exercise 3](../exercise3/README.md)
* You can perform the exercises in the same Gradle project used in the 
previous labs, or open the `lab/settings.gradle.kts` Gradle project and work
there.

---
### Adding New Project to Gradle

In the file browser on the left of the IDE, right click on the top level folder
and select `New --> Module`. Note that `Module` here refers to a Gradle project
module, not a Gradle dependency module.

<p align="center">
<img width="60%" height="60%" src="https://raw.githubusercontent.com/gradle/build-tool-training-exercises/main/Introduction_to_Gradle_Build_Tool/exercise4/images/add_module.png">
</p>

Select `Gradle` with `Java` and `Kotlin DSL build script` options. Click
on the `Next` button.

<p align="center">
<img width="60%" height="60%" src="https://raw.githubusercontent.com/gradle/build-tool-training-exercises/main/Introduction_to_Gradle_Build_Tool/exercise4/images/add_module2.png">
</p>

Leave the parent as `Lab` and enter `model` for the name. Click on the
`Finish` button.

<p align="center">
<img width="30%" height="30%" src="https://raw.githubusercontent.com/gradle/build-tool-training-exercises/main/Introduction_to_Gradle_Build_Tool/exercise4/images/add_module3.png">
</p>

Give the IDE a minute, once it's done you will see the `model` project added.
Inspect the `settings.gradle.kts` file and notice the project added there too.

<p align="center">
<img width="30%" height="30%" src="https://raw.githubusercontent.com/gradle/build-tool-training-exercises/main/Introduction_to_Gradle_Build_Tool/exercise4/images/add_module4.png">
</p>

<p align="center">
<img width="60%" height="60%" src="https://raw.githubusercontent.com/gradle/build-tool-training-exercises/main/Introduction_to_Gradle_Build_Tool/exercise4/images/add_module5.png">
</p>

Open the `model/build.gradle.kts` file and add a dependency for
`implementation("com.google.guava:guava:30.1.1-jre")`. Click on the Gradle
elephant icon to refresh the configs.

<p align="center">
<img width="60%" height="60%" src="https://raw.githubusercontent.com/gradle/build-tool-training-exercises/main/Introduction_to_Gradle_Build_Tool/exercise4/images/add_module6.png">
</p>

Now add the `com.gradle.lab` package to the model project by right-clicking
the `java` folder under `model/src/main` and selecting `New --> Package`.

<p align="center">
<img width="60%" height="60%" src="https://raw.githubusercontent.com/gradle/build-tool-training-exercises/main/Introduction_to_Gradle_Build_Tool/exercise4/images/add_module7.png">
</p>

<p align="center">
<img width="40%" height="40%" src="https://raw.githubusercontent.com/gradle/build-tool-training-exercises/main/Introduction_to_Gradle_Build_Tool/exercise4/images/add_module8.png">
</p>

Now add a Java class named `AppModel` under the package by right-clicking
the `com.gradle.lab` package and selecting `New --> Java Class`.

<p align="center">
<img width="60%" height="60%" src="https://raw.githubusercontent.com/gradle/build-tool-training-exercises/main/Introduction_to_Gradle_Build_Tool/exercise4/images/add_module9.png">
</p>

<p align="center">
<img width="40%" height="40%" src="https://raw.githubusercontent.com/gradle/build-tool-training-exercises/main/Introduction_to_Gradle_Build_Tool/exercise4/images/add_module10.png">
</p>

Open the `AppModel.java` file and *copy* the getter methods from `App.java` to
here. The contents will be:

```java
package com.gradle.lab;

public class AppModel {

  public String getGreeting() {
    return "Hello World!";
  }

  public String getUrl() {
    // This is a small website and easily prints.
    return "https://wiby.me/";
  }
}
```

---
### Setting One Project as a Dependency to Another

Update the `app/build.gradle.kts` to include the `:model` project as a
dependency. Click on the Gradle elephant icon to refresh the configs.

<p align="center">
<img width="60%" height="60%" src="https://raw.githubusercontent.com/gradle/build-tool-training-exercises/main/Introduction_to_Gradle_Build_Tool/exercise4/images/proj_dep.png">
</p>

In `App.java` do the following:
1. Declare an `AppModel` variable called `appModel` and initialize it
2. Change the `getGreeting()` method to use the `appModel.getGreeting()`
3. Remove the `getUrl()` method
4. Change the `HttpRequest` line to use `app.model.getUrl()`

The updated `App.java` will look like this now:

```java
package com.gradle.lab;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.javanet.NetHttpTransport;

import java.io.IOException;

public class App {

  AppModel model = new AppModel();

  public String getGreeting() {
    return model.getGreeting();
  }

  public static void main(String[] args) throws IOException {
    App app = new App();
    System.out.println(app.getGreeting());

    HttpRequestFactory requestFactory = new NetHttpTransport().createRequestFactory();
    HttpRequest request = requestFactory.buildGetRequest(new GenericUrl(app.model.getUrl()));
    String rawResponse = request.execute().parseAsString();
    System.out.println("\n---------\n");
    System.out.println(rawResponse);
  }
}
```

Execute `./gradlew :app:run` to ensure the application still works.  You have
successfully created another project which the `app` project depends on.

---
### Sharing Common Configuration Between Projects

Often in multi-project builds, there is a lot of configuration that is the
same between projects. It is easier to manage the projects if the common
configuration is put in one location and shared between the projects.

This location for shared configuration is the `buildSrc` folder.

First create a folder under the root called `buildSrc/src/main/kotlin`.

<p align="center">
<img width="60%" height="60%" src="https://raw.githubusercontent.com/gradle/build-tool-training-exercises/main/Introduction_to_Gradle_Build_Tool/exercise4/images/buildsrc1.png">
</p>

<p align="center">
<img width="40%" height="40%" src="https://raw.githubusercontent.com/gradle/build-tool-training-exercises/main/Introduction_to_Gradle_Build_Tool/exercise4/images/buildsrc2.png">
</p>

Then create the file `buildSrc/build.gradle.kts` with the following contents:

```kotlin
plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}
```

Then create the file `buildSrc/src/main/kotlin/shared-build-conventions.gradle.kts`.
In it we will put the common configuration between the `app` and `model` projects,
and also move the custom tasks as we want to make them available for the model
project as well:

```kotlin
plugins {
    java
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")

    implementation("com.google.guava:guava:30.1.1-jre")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

tasks.register("testWithMsg") {
    group = "verification"
    description = "Runs tests and prints msg when done"
    dependsOn("test")

    doLast {
        println("Tests done!")
    }
}

tasks.register("msgAfterTest") {
    group = "verification"
    description = "Prints msg when tests are done"

    doLast {
        println("Tests done!!")
    }
}

tasks.named("test") {
    finalizedBy("msgAfterTest")
}

tasks.register<Copy>("backupTestXml") {
    from("build/test-results/test")
    into("/tmp/")

    exclude("binary/**")
}
```

We can then add the `shared-build-conventions` plugin to both the `app` and
`model` projects, and remove the common configuration.

The contents of `app/build.gradle.kts` will now be:

```kotlin
plugins { 
    application
    id("shared-build-conventions")
}

dependencies {
    implementation(project(":model"))
    implementation("com.google.http-client:google-http-client:1.41.8")
}

application {
    mainClass.set("com.gradle.lab.App")
}
```

Notice that we still need to apply the `application` plugin, since the `app`
project produces an executable. Also the extra dependencies need to be added
as well.

The contents of `model/build.gradle.kts` will now be:

```kotlin
plugins {
    id("shared-build-conventions")
}
```

Execute `./gradlew :app:run` to ensure things still work, also run the
`:app:test` task to ensure that still works.
