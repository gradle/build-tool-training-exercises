## Introduction to Gradle Build Tool for Developers - Exercise 3

<p align="left">
<img width="10%" height="10%" src="https://user-images.githubusercontent.com/120980/174325546-8558160b-7f16-42cb-af0f-511849f22ebc.png">
</p>

This is a hands-on exercise to go along with the
**Introduction to Gradle Build Tool for Developers** training module. In this exercise
you will get familiar with the following topics:

* See dependencies for a project
* Adding dependencies
* Examining dependency conflict resolution
* Creating a version catalog
* Updating build files to use the catalog

---
### Prerequisites

* Completed [exercise 2](../exercise2/README.md)
* You can perform the exercises in the same Gradle project used in the
previous labs, or open the `lab/settings.gradle.kts` Gradle project and work
there.

---
### See Dependencies for a Project

Open the terminal from the bottom toolbar and run
`./gradlew :app:dependencies`. You will see dependencies grouped by
configurations.

<p align="center">
<img width="60%" height="60%" src="https://user-images.githubusercontent.com/120980/174330482-f470305a-210f-4780-b47e-5340c6a7de70.png">
</p>

To see the dependencies of just the runtimeClasspath configuration run
`./gradlew :app:dependencies --configuration=runtimeClasspath`

<p align="center">
<img width="60%" height="60%" src="https://user-images.githubusercontent.com/120980/174330540-ed0e3ad2-d76b-4cca-8830-7c27d324261c.png">
</p>

### Adding Dependencies

Let's add some more functionality to our application. Currently it only prints
a greeting message. Let's make it fetch some data from a url and print it. We
will use the [Google Http Client](https://github.com/googleapis/google-http-java-client)
for this.

We need to get the Gradle dependency statement for this library. Follow the
steps below to get this:

* Go to [https://mvnrepository.com/](https://mvnrepository.com/)
* In the search bar type `google http client`
* One of the top results will be `com.google.http-client » google-http-client`,
click on it, and then click on the `1.41.8` version
* Click on the `Gradle (Kotlin)` tab and uncheck the
`Include comment with link to declaration` if it is checked
* Copy the dependency statement

<p align="center">
<img width="60%" height="60%" src="https://user-images.githubusercontent.com/120980/174330616-2b3965b2-f9bd-4665-90cc-5e7d0c4ed7ab.png">
</p>

In your `build.gradle.kts` file, go to the `dependencies` block and paste
the statement there. Click on the Gradle elephant icon to refresh the configs.

<p align="center">
<img width="60%" height="60%" src="https://user-images.githubusercontent.com/120980/174330680-0b3b00cb-4944-4564-8a64-db9faa84d2c8.png">
</p>

Run the `./gradlew :app:dependencies --configuration=runtimeClasspath` again
and notice the additional dependencies - both direct and transitive.

<p align="center">
<img width="60%" height="60%" src="https://user-images.githubusercontent.com/120980/174330739-0c7c0236-b3e2-4b43-b536-de6c2db5f5e2.png">
</p>

### Examining Dependency Conflict Resolution

Notice in the dependencies, the `google-http-client` library depends on
`guava`, which is also a direct dependency.

<p align="center">
<img width="60%" height="60%" src="https://user-images.githubusercontent.com/120980/174330809-387755b1-b63f-46e6-8025-e0eeb8363275.png">
</p>

The `guava` version `google-http-client` depends on is older than the
version in the direct dependency, so you will see Gradle handles the
conflict by using the highest version of the `guava` library.

<p align="center">
<img width="60%" height="60%" src="https://user-images.githubusercontent.com/120980/174330889-4ba33818-060a-4a68-8358-5d434df60e39.png">
</p>

### Update Application

Open the `app/src/main/java/com/gradle/lab/App.java` file and replace the
contents with the following:

```java
package com.gradle.lab;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.javanet.NetHttpTransport;

import java.io.IOException;

public class App {
    public String getGreeting() {
        return "Hello World!";
    }

    public String getUrl() {
        // This is a small website and easily prints.
        return "https://wiby.me/";
    }

    public static void main(String[] args) throws IOException {
        App app = new App();
        System.out.println(app.getGreeting());

        HttpRequestFactory requestFactory = new NetHttpTransport().createRequestFactory();
        HttpRequest request = requestFactory.buildGetRequest(new GenericUrl(app.getUrl()));
        String rawResponse = request.execute().parseAsString();
        System.out.println("\n---------\n");
        System.out.println(rawResponse);
    }
}
```

Then open the terminal and execute `./gradlew :app:run`, notice the website
contents are also printed.

<p align="center">
<img width="70%" height="70%" src="https://user-images.githubusercontent.com/120980/174330977-494f5958-e164-4d29-b4d9-180cec1bd56d.png">
</p>

<p align="right">
<a href="https://github.com/gradle/build-tool-training-exercises/tree/main/Introduction_to_Gradle_Build_Tool/exercise4">Exercise 4 >></a>
</p>

---
### Create Version Catalog

In the `gradle` folder create a file called `libs.versions.toml`. Add all the
dependency modules to it under the `libraries` section.
Also add the taskinfo plugin under the `plugins` section.

```text
[libraries]
...
...

[plugins]
...
```

Update the dependencies to use the version catalog. Also update the plugin section.
If you get stuck you can refer to the [solution](solution/gradle/libs.versions.toml).
