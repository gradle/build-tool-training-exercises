## Gradle Build Cache Deep Dive - Exercise 1

<p align="left">
<img width="10%" height="10%" src="https://user-images.githubusercontent.com/120980/174325546-8558160b-7f16-42cb-af0f-511849f22ebc.png">
</p>

This is a hands-on exercise to go along with the
**Gradle Build Cache Deep Dive** training module. In this exercise
you will go over the following:

* Recap of Incremental Builds
* Enable and use Local Cache
* Order of lookup for cached outputs

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
### Recap of Incremental Cache and Limitations

Open the Gradle project in the `lab` folder. You can either open a terminal
and go to the folder, or open the project in an editor such as IntelliJ.

Inspect the build and source files, there is only one subproject called `app`.
The `gradle.properties` file has a property so verbose console logging will be
enabled. You may notice `app/build.gradle.kts` has a task called
`generateLocalUniqueValue`, ignore it for now. We will cover this later in the
training.

Now let's run tests on the project. Notice the task outcome labels indicate
that most tasks' actions were executed.

```diff
$ ./gradlew :app:test
- Task :app:compileJava
# Task :app:processResources NO-SOURCE
- Task :app:classes
- Task :app:compileTestJava
# Task :app:processTestResources NO-SOURCE
- Task :app:testClasses
- Task :app:test
```

Now run the tests again. Notice that the outcome labels indicate that most
tasks' actions were **not** executed. The output from the previous execution
in the `build` directory was used.

```diff
$ ./gradlew :app:test
> Task :app:compileJava UP-TO-DATE
# Task :app:processResources NO-SOURCE
> Task :app:classes UP-TO-DATE
> Task :app:compileTestJava UP-TO-DATE
# Task :app:processTestResources NO-SOURCE
> Task :app:testClasses UP-TO-DATE
> Task :app:test UP-TO-DATE
```

Now make a change to the `app/src/main/java/com/gradle/lab/App.java` file, add
2 more exclamation marks to the `Hello World!` string:

```java
public String getGreeting() {
    return "Hello World!!!";
}
```

Run the tests again. Notice that the actions of `compileJava` and `test` were
executed since changes were made to the inputs for those tasks.

```diff
$ ./gradlew :app:test
- Task :app:compileJava
# Task :app:processResources NO-SOURCE
- Task :app:classes
> Task :app:compileTestJava UP-TO-DATE
# Task :app:processTestResources NO-SOURCE
> Task :app:testClasses UP-TO-DATE
- Task :app:test
```

Revert the changes made to `app/src/main/java/com/gradle/lab/App.java` and
run the tests again. Notice that the actions of `compileJava` and `test` were
executed since the most recent build had different inputs for those tasks.

```diff
$ ./gradlew :app:test
- Task :app:compileJava
# Task :app:processResources NO-SOURCE
- Task :app:classes
> Task :app:compileTestJava UP-TO-DATE
# Task :app:processTestResources NO-SOURCE
> Task :app:testClasses UP-TO-DATE
- Task :app:test
```

---
### Enable and Use Local Cache

Edit the `gradle.properties` file and add `org.gradle.caching=true` to it. The
contents of the file now look like:

```properties
org.gradle.console=verbose
org.gradle.caching=true
```

Run the clean task and then the tests, this will populate the cache.

```diff
$ ./gradlew :app:clean :app:test
# Task :app:clean
- Task :app:compileJava
# Task :app:processResources NO-SOURCE
- Task :app:classes
- Task :app:compileTestJava
# Task :app:processTestResources NO-SOURCE
- Task :app:testClasses
- Task :app:test
```

Now change the string in `app/src/main/java/com/gradle/lab/App.java` to have 2
extra exclamations again. Run the tests, notice that the task actions were
executed. The inputs and outputs for these changes are not in the cache.

```diff
$ ./gradlew :app:test
- Task :app:compileJava
# Task :app:processResources NO-SOURCE
- Task :app:classes
> Task :app:compileTestJava UP-TO-DATE
# Task :app:processTestResources NO-SOURCE
> Task :app:testClasses UP-TO-DATE
- Task :app:test
```

Now revert the changes to `app/src/main/java/com/gradle/lab/App.java` and run
the tests again. Notice that some task actions were skipped and the outputs
from the cache were used.

```diff
$ ./gradlew :app:test
! Task :app:compileJava FROM-CACHE
# Task :app:processResources NO-SOURCE
> Task :app:classes UP-TO-DATE
> Task :app:compileTestJava UP-TO-DATE
# Task :app:processTestResources NO-SOURCE
> Task :app:testClasses UP-TO-DATE
! Task :app:test FROM-CACHE
```

If you run the tests again, you will notice that the outcome label indicates
the output from the previous execution in the `build` directory was used.
This shows the order in which Gradle will look for the cached outputs,
first in the build directory, then in the cache.

```diff
$ ./gradlew :app:test
> Task :app:compileJava UP-TO-DATE
# Task :app:processResources NO-SOURCE
> Task :app:classes UP-TO-DATE
> Task :app:compileTestJava UP-TO-DATE
# Task :app:processTestResources NO-SOURCE
> Task :app:testClasses UP-TO-DATE
> Task :app:test UP-TO-DATE
```

If you use the **-i** flag you can see the cache key used for each
task input. Run a clean followed by the tests to see this.

```bash
$ ./gradlew :app:clean :app:test -i
...
...
...
> Task :app:test FROM-CACHE
Build cache key for task ':app:test' is a9c53b883c4efdde1f278be0947511f7
Task ':app:test' is not up-to-date because:
  Output property 'binaryResultsDirectory' file /Users/.../exercise1/lab/app/build/test-results/test/binary has been removed.
  Output property 'binaryResultsDirectory' file /Users/.../exercise1/lab/app/build/test-results/test/binary/output.bin has been removed.
  Output property 'binaryResultsDirectory' file /Users/.../exercise1/lab/app/build/test-results/test/binary/output.bin.idx has been removed.
Loaded cache entry for task ':app:test' with cache key a9c53b883c4efdde1f278be0947511f7
```

You can also use the **-Dorg.gradle.caching.debug=true** flag to view more details.

```bash
$ ./gradlew :app:clean :app:test -Dorg.gradle.caching.debug=true
```

You can see the cache entries by looking in the cache directory:

```bash
$ ls -ltr ~/.gradle/caches/build-cache-1/
```

---
### Local Cache Configuration

By default items will expire from the local cache after 7 days. Let's
modify the configuration to keep items for 30 days.

Edit the `settings.gradle.kts` file and add the following at the end of the
file:

```kotlin
buildCache {
    local {
        removeUnusedEntriesAfterDays = 30
    }
}
```

Run a clean then the tests to make sure there was no mistake in the configuration:

```diff
$ ./gradlew :app:clean :app:test
# Task :app:clean
! Task :app:compileJava FROM-CACHE
# Task :app:processResources NO-SOURCE
> Task :app:classes UP-TO-DATE
! Task :app:compileTestJava FROM-CACHE
# Task :app:processTestResources NO-SOURCE
> Task :app:testClasses UP-TO-DATE
! Task :app:test FROM-CACHE
```

It is important to understand that the outputs from the cache are put into
the build directory and that's how the outputs are available to the tasks.

<p align="right">
<a href="https://github.com/gradle/build-tool-training-exercises/tree/main/Gradle_Build_Cache_Deep_Dive/exercise2">Exercise 2 >></a>
</p>
