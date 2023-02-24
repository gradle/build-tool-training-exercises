## Android Apps with Gradle Build Tool - Exercise 3

<p align="left">
<img width="10%" height="10%" src="https://user-images.githubusercontent.com/120980/174325546-8558160b-7f16-42cb-af0f-511849f22ebc.png">
</p>

This is a hands-on exercise to go along with the
**Android Apps with Gradle Build Tool** training module. In this exercise
you will go over the following:

* Create modules for features
* Add UI elements

At the end of this exercise you will have a fully working app.

---
### Prerequisites

* Completed exercise 2
* You can perform the exercises in the same project used in exercise 2

---
### Adding new dependency to version catalog

We will make use of [exp4j](https://en.wikipedia.org/wiki/Exp4j) to
evaluate calculations. Add this dependency to the version catalog.

```text
exp4j = "net.objecthunter:exp4j:0.4.8"
```

We will use the [jacocolog](https://plugins.gradle.org/plugin/org.barfuin.gradle.jacocolog)
plugin for showing more test coverage information.

```text
jacocolog = { id = "org.barfuin.gradle.jacocolog", version = "3.1.0" }
```

---
### :math:calc module

This module will handle all the math related functionality needed by
the calculator fragment.

* Add a kotlin library module called `:math:calc` with a `Calc` class

<p align="center">
<img width="75%" height="75%" src="https://user-images.githubusercontent.com/120980/220796992-7799b7bf-97fd-4bd6-a798-eb63ca7374e1.png">
</p>

* Update the contents of the [build.gradle.kts](solution/math/calc/build.gradle.kts) to include `exp4j` dependency, java toolchain and test coverage configuration
* Add code for [Calc.kt](solution/math/calc/src/main/java/com/gradle/lab/calc/Calc.kt) and [CalcTest.kt](solution/math/calc/src/test/java/com/gradle/lab/calc/CalcTest.kt)

---
### :math:game module

This module will handle all the math related functionality needed by
the game fragment.

* Add a kotlin library module called `:math:game` with a `Game` class
* Update the contents of the [build.gradle.kts](solution/math/game/build.gradle.kts) to include java toolchain and test coverage configuration
* Add code for [Game.kt](solution/math/game/src/main/java/com/gradle/lab/game/Game.kt) and [GameTest.kt](solution/math/game/src/test/java/com/gradle/lab/game/GameTest.kt)

---
### :feature:calc module

This module will provide the fragment for the calculator part of the app.

* Add an android library module called `:feature:calc`.
* Update the contents of the [build.gradle.kts](solution/feature/calc/build.gradle.kts) to use the version catalog, JDK 11 and dependency on `:math:calc`
* Add a vector for `backspace`

<p align="center">
<img width="75%" height="75%" src="https://user-images.githubusercontent.com/120980/220099752-b5edb691-1f19-4ecb-bd5f-a43af43798b0.png">
</p>

* Add a `drawable` file called [borderbottom.xml](solution/feature/calc/src/main/res/drawable/borderbottom.xml)
* Add a blank fragment called `CalcFragment`

<p align="center">
<img width="75%" height="75%" src="https://user-images.githubusercontent.com/120980/220098707-862c64e9-3aea-4615-9c40-8e0ace29dace.png">
</p>

* Add code for [CalcFragment.kt](solution/feature/calc/src/main/java/com/gradle/lab/calc/CalcFragment.kt), [fragment_calc.xml](solution/feature/calc/src/main/res/layout/fragment_calc.xml) and [strings.xml](solution/feature/calc/src/main/res/values/strings.xml)

---
### :feature:game module

This module will provide the fragment for the calculator part of the app.

* Add an android library module called `:feature:game`.
* Update the contents of the [build.gradle.kts](solution/feature/game/build.gradle.kts) to use the version catalog, JDK 11 and dependency on `:math:calc` and `:math:game`
* Add a vector for `send`
* Add a blank fragment called `GameFragment`
* Add code for [GameFragment.kt](solution/feature/game/src/main/java/com/gradle/lab/game/GameFragment.kt), [fragment_game.xml](solution/feature/game/src/main/res/layout/fragment_game.xml) and [strings.xml](solution/feature/game/src/main/res/values/strings.xml)

---
### Update :app module

Now let's update the `app` module to use the other modules we've added.

* Update the [app/build.gradle.kts](solution/app/build.gradle.kts) with feature dependencies
* Add calculate vector
* Add videogame asset vector
* Add a `Android Resource File` of type `menu` called `bottom_nav_menu` and put the [following contents](solution/app/src/main/res/menu/bottom_nav_menu.xml) in it

<p align="center">
<img width="75%" height="75%" src="https://user-images.githubusercontent.com/120980/220099390-79e8288c-095b-4c39-9197-b7e26c59933b.png">
</p>
<p align="center">
<img width="50%" height="50%" src="https://user-images.githubusercontent.com/120980/220099555-c6b020f1-38e1-49d2-938f-b4c368b72e20.png">
</p>

* Update the contents of [activity_main.xml](solution/app/src/main/res/layout/activity_main.xml)
* Update the contents of [MainActivity.kt](solution/app/src/main/java/com/gradle/lab/mycalculator/MainActivity.kt)


Press the `Gradle sync` button. You can now run the app and play with it.
