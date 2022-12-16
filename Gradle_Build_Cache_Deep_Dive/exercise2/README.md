## Gradle Build Cache Deep Dive - Exercise 2

<p align="left">
<img width="10%" height="10%" src="https://user-images.githubusercontent.com/120980/174325546-8558160b-7f16-42cb-af0f-511849f22ebc.png">
</p>

This is a hands-on exercise to go along with the
**Gradle Build Cache Deep Dive** training module. In this exercise
you will go over the following:

* Enable and use remote caching
* Compare build scans to identify cause of cache misses

---
### Prerequisites

* Completed [exercise 1](../exercise1/README.md)
* You can perform the exercises in the same Gradle project used in exercise 1.

---
### Enable Remote Cache

We will use the Gradle Enterprise Training instance as the remote cache. In
the `settings.gradle.kts` update the configuration for the `buildCache`
section by adding the `remote` block:

```kotlin
buildCache {
    local {
        ...
    }
    remote<HttpBuildCache> {
        url = uri("https://enterprise-training.gradle.com/cache/")
        isPush = true
    }
}
```

For now let's also disable the local cache, thereby ensuring the remote
cache gets used. We do this by adding `isEnabled = false` to the local
configuration:

```kotlin
buildCache {
    local {
        removeUnusedEntriesAfterDays = 30
        isEnabled = false
    }
}
```

We will use build scans as well, so let's add configuration for that as well.
In the end your `settings.gradle.kts` will look as follows:


```kotlin
plugins {
  id("com.gradle.enterprise") version "3.8"
}
gradleEnterprise {
  buildScan {
    server = "https://enterprise-training.gradle.com"
    capture {
      isTaskInputFiles = true
    }
  }
}

rootProject.name = "lab"
include("app")

buildCache {
  local {
    removeUnusedEntriesAfterDays = 30
    isEnabled = false
  }
  remote<HttpBuildCache> {
    url = uri("https://enterprise-training.gradle.com/cache/")
    isPush = true
  }
}
```

To get an access key to the remote cache run:

```bash
$ ./gradlew :provisionGradleEnterpriseAccessKey
```

Open the link and follow the steps to provision a key. **Note:** You will have to enter
credentials to get an access key. The instructor will provide them to you.

### Use Remote Cache and Compare Scans

Now run a clean followed by the tests. We will see no task output was fetched from the remote cache.

```diff
$ ./gradlew :app:clean :app:test
# Task :app:clean
# Task :app:generateLocalUniqueValue UP-TO-DATE
- Task :app:compileJava
# Task :app:processResources NO-SOURCE
- Task :app:classes
- Task :app:compileTestJava
# Task :app:processTestResources NO-SOURCE
- Task :app:testClasses
- Task :app:test
```

Now run the clean and tests again and you will see the remote cache being used.
Also pass the `--scan` flag which will generate a build scan that we can explore.

```diff
$ ./gradlew :app:clean :app:test --scan
# Task :app:clean
# Task :app:generateLocalUniqueValue UP-TO-DATE
! Task :app:compileJava FROM-CACHE
# Task :app:processResources NO-SOURCE
> Task :app:classes UP-TO-DATE
! Task :app:compileTestJava FROM-CACHE
# Task :app:processTestResources NO-SOURCE
> Task :app:testClasses UP-TO-DATE
! Task :app:test FROM-CACHE
```

Open the build scan, go to the Timeline (in the left menu) and expand the
compileJava task and inspect the cache details.

<p align="center">
<img width="60%" height="60%" src="https://user-images.githubusercontent.com/120980/177324633-23517ec0-5392-40e9-8cfa-a4d5eb593b93.png">
</p>

Now edit the string in `app/src/main/java/com/gradle/lab/App.java` to
something different and run the clean and tests with the `--scan` flag.

```diff
$ ./gradlew :app:clean :app:test --scan
# Task :app:clean
> Task :app:generateLocalUniqueValue UP-TO-DATE
- Task :app:compileJava
# Task :app:processResources NO-SOURCE
- Task :app:classes
! Task :app:compileTestJava FROM-CACHE
# Task :app:processTestResources NO-SOURCE
> Task :app:testClasses UP-TO-DATE
- Task :app:test
```

You can see the cache misses. Now open the build scan, and in the top right
click on the "Build Scans" link to view all the scans.

<p align="center">
<img width="35%" height="35%" src="https://user-images.githubusercontent.com/120980/177330766-ff55fbda-2f0d-45a4-98b1-645d1776a9c4.png">
</p>

Select your two recent scans to compare and click on the "Compare" button on
the bottom right.

<p align="center">
<img width="60%" height="60%" src="https://user-images.githubusercontent.com/120980/177334045-e52b140b-a989-4b03-933e-8685eaa09398.png">
</p>

Expand the file properties to see which files were different in the inputs
which caused the cache miss.

<p align="center">
<img width="60%" height="60%" src="https://user-images.githubusercontent.com/120980/177334631-5c94bbc3-5279-4fee-b202-ab4dc0599e17.png">
</p>

<p align="right">
<a href="https://github.com/gradle/build-tool-training-exercises/tree/main/Gradle_Build_Cache_Deep_Dive/exercise3">Exercise 3 >></a>
</p>
