## Android Apps with Gradle Build Tool - Exercise 4

<p align="left">
<img width="10%" height="10%" src="https://user-images.githubusercontent.com/120980/174325546-8558160b-7f16-42cb-af0f-511849f22ebc.png">
</p>

This is a hands-on exercise to go along with the
**Android Apps with Gradle Build Tool** training module. In this exercise
you will go over the following:

* Add UI elements

At the end of this exercise you will have a fully working app.

---
### Prerequisites

* Completed exercise 3
* You can perform the exercises in the same project used in exercise 3

---
### :feature:calc module

This module will provide the fragment for the calculator part of the app.

* Add an android library module called `:feature:calc`.
* Update the contents of the [feature/calc/build.gradle.kts](../exercise3/solution/feature/calc/build.gradle.kts) to use the version catalog, JDK 11 and dependency on `:math:calc`

```kotlin
plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.gradle.lab.calc"
    compileSdk = 33

    defaultConfig {
        minSdk = 24
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(libs.androidx.core.kts)
    implementation(libs.androidx.appcompat)
    implementation(libs.android.material)
    implementation(libs.androidx.constraintlayout)
    implementation(project(":math:calc"))

    androidTestImplementation(libs.bundles.androidx.tests)
}
```

* Add a vector for `backspace`

<p align="center">
<img width="75%" height="75%" src="https://user-images.githubusercontent.com/120980/220099752-b5edb691-1f19-4ecb-bd5f-a43af43798b0.png">
</p>

* Add a `drawable` file called [borderbottom.xml](../exercise3/solution/feature/calc/src/main/res/drawable/borderbottom.xml)
* Add a blank fragment called `CalcFragment`

<p align="center">
<img width="75%" height="75%" src="https://user-images.githubusercontent.com/120980/220098707-862c64e9-3aea-4615-9c40-8e0ace29dace.png">
</p>

```xml
<?xml version="1.0" encoding="utf-8"?>
<layer-list xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:top="-2dp" android:left="-2dp" android:right="-2dp">
        <shape android:shape="rectangle">
            <stroke android:width="1dp" android:color="#000000" />
            <solid android:color="#ffffff" />
        </shape>
    </item>
</layer-list>
```

* Add code for [feature/calc/src/main/java/com/gradle/lab/calc/CalcFragment.kt](../exercise3/solution/feature/calc/src/main/java/com/gradle/lab/calc/CalcFragment.kt)

```kotlin
package com.gradle.lab.calc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

/**
 * A simple [Fragment] subclass.
 */
class CalcFragment : Fragment() {

    private var equationField: TextView? = null
    private var resultField: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_calc, container, false)

        equationField = view.findViewById(R.id.equation_field)
        resultField = view.findViewById(R.id.result_field)

        initNumOrOperationButton(view.findViewById(R.id.button_0))
        initNumOrOperationButton(view.findViewById(R.id.button_1))
        initNumOrOperationButton(view.findViewById(R.id.button_2))
        initNumOrOperationButton(view.findViewById(R.id.button_3))
        initNumOrOperationButton(view.findViewById(R.id.button_4))
        initNumOrOperationButton(view.findViewById(R.id.button_5))
        initNumOrOperationButton(view.findViewById(R.id.button_6))
        initNumOrOperationButton(view.findViewById(R.id.button_7))
        initNumOrOperationButton(view.findViewById(R.id.button_8))
        initNumOrOperationButton(view.findViewById(R.id.button_9))
        initNumOrOperationButton(view.findViewById(R.id.button_add))
        initNumOrOperationButton(view.findViewById(R.id.button_subtract))
        initNumOrOperationButton(view.findViewById(R.id.button_multiply))
        initNumOrOperationButton(view.findViewById(R.id.button_divide))
        initNumOrOperationButton(view.findViewById(R.id.button_dot))

        initEqualsButton(view.findViewById(R.id.button_equals))
        initAcButton(view.findViewById(R.id.button_ac))

        initDelButton(view.findViewById(R.id.button_del))

        return view
    }

    private fun initNumOrOperationButton(button: Button) {
        button.setOnClickListener {
            // If there are only 0s so far, clear them.
            if (equationField!!.text != null && equationField!!.text.isNotEmpty()) {
                val currentEquation = equationField!!.text.toString().trim()
                if (Calc.isZeroString(currentEquation)) {
                    equationField!!.text = ""
                }
            }

            equationField!!.append(button.text)
        }
    }

    private fun initEqualsButton(button: Button) {
        button.setOnClickListener(View.OnClickListener {
            if (equationField!!.text == null) {
                return@OnClickListener
            }
            val currentEquation = equationField!!.text.toString().trim { it <= ' ' }
            if (currentEquation == "") {
                return@OnClickListener
            }
            val result: String? = Calc.evalExpression(currentEquation)
            if (result == null) {
                resultField!!.text = getString(R.string.error)
            } else {
                resultField!!.text = result
                equationField!!.text = ""
            }
        })
    }

    private fun initAcButton(button: Button) {
        button.setOnClickListener {
            resultField!!.text = ""
            equationField!!.text = ""
        }
    }

    private fun initDelButton(button: Button) {
        button.setOnClickListener(View.OnClickListener {
            if (equationField!!.text == null || equationField!!.text.isEmpty()) {
                return@OnClickListener
            }
            val currentEquation = equationField!!.text.toString()
            equationField!!.text = currentEquation.substring(0, currentEquation.length - 1)
        })
    }
}
```

* Add code for [feature/calc/src/main/res/layout/fragment_calc.xml](../exercise3/solution/feature/calc/src/main/res/layout/fragment_calc.xml)

```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    tools:context=".CalcFragment">

    <TextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="0"
        android:textSize="24sp"
        android:textAlignment="textEnd"
        android:textColor="#9E9E9E"
        android:layout_above="@+id/equation_field"
        android:id="@+id/result_field"
        android:background="@drawable/borderbottom"
        android:layout_margin="16dp" />

    <TextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text=""
        android:textSize="36sp"
        android:textAlignment="textEnd"
        android:textColor="@color/black"
        android:layout_above="@+id/main_buttons_layout"
        android:id="@+id/equation_field"
        android:background="@drawable/borderbottom"
        android:layout_margin="16dp" />

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:id="@+id/main_buttons_layout"
        android:layout_alignParentBottom="true"
        android:orientation="vertical">

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:gravity="center"
            android:orientation="horizontal">

            <com.google.android.material.button.MaterialButton
                android:layout_width="72dp"
                android:layout_height="72dp"
                app:cornerRadius="36dp"
                style="@style/Widget.MaterialComponents.ExtendedFloatingActionButton"
                android:text="AC"
                android:textSize="21sp"
                android:textColor="@color/white"
                android:backgroundTint="#E53935"
                android:layout_margin="12dp"
                android:id="@+id/button_ac"/>
            <com.google.android.material.button.MaterialButton
                android:layout_width="72dp"
                android:layout_height="72dp"
                app:cornerRadius="36dp"
                style="@style/Widget.MaterialComponents.ExtendedFloatingActionButton"
                android:textSize="21sp"
                android:layout_margin="12dp"
                android:backgroundTint="#E53935"
                app:icon="@drawable/baseline_backspace_24"
                app:iconTint="@color/white"
                android:id="@+id/button_del"/>
            <com.google.android.material.button.MaterialButton
                android:layout_width="72dp"
                android:layout_height="72dp"
                app:cornerRadius="36dp"
                style="@style/Widget.MaterialComponents.ExtendedFloatingActionButton"
                android:textSize="32sp"
                android:layout_margin="12dp"
                android:backgroundTint="@color/white"
                android:visibility="invisible"
                android:enabled="false"
                android:id="@+id/button_hidden"/>
            <com.google.android.material.button.MaterialButton
                android:layout_width="72dp"
                android:layout_height="72dp"
                app:cornerRadius="36dp"
                style="@style/Widget.MaterialComponents.ExtendedFloatingActionButton"
                android:textSize="32sp"
                android:layout_margin="12dp"
                android:backgroundTint="#FB8C00"
                android:text="/"
                android:textColor="@color/white"
                android:id="@+id/button_divide"/>
        </LinearLayout>
        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:gravity="center"
            android:orientation="horizontal">

            <com.google.android.material.button.MaterialButton
                android:layout_width="72dp"
                android:layout_height="72dp"
                app:cornerRadius="36dp"
                style="@style/Widget.MaterialComponents.ExtendedFloatingActionButton"
                android:text="7"
                android:textSize="32sp"
                android:textColor="@color/white"
                android:layout_margin="12dp"
                android:id="@+id/button_7"/>
            <com.google.android.material.button.MaterialButton
                android:layout_width="72dp"
                android:layout_height="72dp"
                app:cornerRadius="36dp"
                style="@style/Widget.MaterialComponents.ExtendedFloatingActionButton"
                android:text="8"
                android:textSize="32sp"
                android:textColor="@color/white"
                android:layout_margin="12dp"
                android:id="@+id/button_8"/>
            <com.google.android.material.button.MaterialButton
                android:layout_width="72dp"
                android:layout_height="72dp"
                app:cornerRadius="36dp"
                style="@style/Widget.MaterialComponents.ExtendedFloatingActionButton"
                android:text="9"
                android:textSize="32sp"
                android:textColor="@color/white"
                android:layout_margin="12dp"
                android:id="@+id/button_9"/>
            <com.google.android.material.button.MaterialButton
                android:layout_width="72dp"
                android:layout_height="72dp"
                app:cornerRadius="36dp"
                style="@style/Widget.MaterialComponents.ExtendedFloatingActionButton"
                android:textSize="36sp"
                android:layout_margin="12dp"
                android:backgroundTint="#FB8C00"
                android:text="*"
                android:textColor="@color/white"
                android:id="@+id/button_multiply"/>
        </LinearLayout>
        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:gravity="center"
            android:orientation="horizontal">

            <com.google.android.material.button.MaterialButton
                android:layout_width="72dp"
                android:layout_height="72dp"
                app:cornerRadius="36dp"
                style="@style/Widget.MaterialComponents.ExtendedFloatingActionButton"
                android:text="4"
                android:textSize="32sp"
                android:textColor="@color/white"
                android:layout_margin="12dp"
                android:id="@+id/button_4"/>
            <com.google.android.material.button.MaterialButton
                android:layout_width="72dp"
                android:layout_height="72dp"
                app:cornerRadius="36dp"
                style="@style/Widget.MaterialComponents.ExtendedFloatingActionButton"
                android:text="5"
                android:textSize="32sp"
                android:textColor="@color/white"
                android:layout_margin="12dp"
                android:id="@+id/button_5"/>
            <com.google.android.material.button.MaterialButton
                android:layout_width="72dp"
                android:layout_height="72dp"
                app:cornerRadius="36dp"
                style="@style/Widget.MaterialComponents.ExtendedFloatingActionButton"
                android:text="6"
                android:textSize="32sp"
                android:textColor="@color/white"
                android:layout_margin="12dp"
                android:id="@+id/button_6"/>
            <com.google.android.material.button.MaterialButton
                android:layout_width="72dp"
                android:layout_height="72dp"
                app:cornerRadius="36dp"
                style="@style/Widget.MaterialComponents.ExtendedFloatingActionButton"
                android:textSize="36sp"
                android:layout_margin="12dp"
                android:backgroundTint="#FB8C00"
                android:text="-"
                android:textColor="@color/white"
                android:id="@+id/button_subtract"/>
        </LinearLayout>
        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:gravity="center"
            android:orientation="horizontal">

            <com.google.android.material.button.MaterialButton
                android:layout_width="72dp"
                android:layout_height="72dp"
                app:cornerRadius="36dp"
                style="@style/Widget.MaterialComponents.ExtendedFloatingActionButton"
                android:text="1"
                android:textSize="32sp"
                android:textColor="@color/white"
                android:layout_margin="12dp"
                android:id="@+id/button_1"/>
            <com.google.android.material.button.MaterialButton
                android:layout_width="72dp"
                android:layout_height="72dp"
                app:cornerRadius="36dp"
                style="@style/Widget.MaterialComponents.ExtendedFloatingActionButton"
                android:text="2"
                android:textSize="32sp"
                android:textColor="@color/white"
                android:layout_margin="12dp"
                android:id="@+id/button_2"/>
            <com.google.android.material.button.MaterialButton
                android:layout_width="72dp"
                android:layout_height="72dp"
                app:cornerRadius="36dp"
                style="@style/Widget.MaterialComponents.ExtendedFloatingActionButton"
                android:text="3"
                android:textSize="32sp"
                android:textColor="@color/white"
                android:layout_margin="12dp"
                android:id="@+id/button_3"/>
            <com.google.android.material.button.MaterialButton
                android:layout_width="72dp"
                android:layout_height="72dp"
                app:cornerRadius="36dp"
                style="@style/Widget.MaterialComponents.ExtendedFloatingActionButton"
                android:textSize="36sp"
                android:layout_margin="12dp"
                android:backgroundTint="#FB8C00"
                android:text="+"
                android:textColor="@color/white"
                android:id="@+id/button_add"/>
        </LinearLayout>
        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:gravity="center"
            android:orientation="horizontal">

            <com.google.android.material.button.MaterialButton
                android:layout_width="166dp"
                android:layout_height="72dp"
                app:cornerRadius="36dp"
                style="@style/Widget.MaterialComponents.ExtendedFloatingActionButton"
                android:text="0"
                android:textSize="32sp"
                android:textColor="@color/white"
                android:layout_margin="12dp"
                android:id="@+id/button_0"/>
            <com.google.android.material.button.MaterialButton
                android:layout_width="72dp"
                android:layout_height="72dp"
                app:cornerRadius="36dp"
                style="@style/Widget.MaterialComponents.ExtendedFloatingActionButton"
                android:text="."
                android:textSize="32sp"
                android:textColor="@color/white"
                android:layout_margin="12dp"
                android:id="@+id/button_dot"/>
            <com.google.android.material.button.MaterialButton
                android:layout_width="72dp"
                android:layout_height="72dp"
                app:cornerRadius="36dp"
                style="@style/Widget.MaterialComponents.ExtendedFloatingActionButton"
                android:textSize="36sp"
                android:layout_margin="12dp"
                android:backgroundTint="#FB8C00"
                android:text="="
                android:textColor="@color/white"
                android:id="@+id/button_equals"/>
        </LinearLayout>




    </LinearLayout>


</RelativeLayout>
```

* Add code for [feature/calc/src/main/res/values/strings.xml](../exercise3/solution/feature/calc/src/main/res/values/strings.xml)

```xml
<resources>
    <string name="error">Error</string>
</resources>
```

---
### :feature:game module

This module will provide the fragment for the calculator part of the app.

* Add an android library module called `:feature:game`.
* Update the contents of the [feature/game/build.gradle.kts](../exercise3/solution/feature/game/build.gradle.kts) to use the version catalog, JDK 11 and dependency on `:math:calc` and `:math:game`

```kotlin
plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.gradle.lab.game"
    compileSdk = 33

    defaultConfig {
        minSdk = 24
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(libs.androidx.core.kts)
    implementation(libs.androidx.appcompat)
    implementation(libs.android.material)
    implementation(libs.androidx.constraintlayout)
    implementation(project(":math:calc"))
    implementation(project(":math:game"))

    androidTestImplementation(libs.bundles.androidx.tests)
}
```

* Add a vector for `send`
* Add a blank fragment called `GameFragment`
* Add code for [feature/game/src/main/java/com/gradle/lab/game/GameFragment.kt](../exercise3/solution/feature/game/src/main/java/com/gradle/lab/game/GameFragment.kt)

```kotlin
package com.gradle.lab.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.gradle.lab.calc.Calc

/**
 * A simple [Fragment] subclass.
 */
class GameFragment : Fragment() {

    private var gameQuestion: TextView? = null
    private var answerResult: TextView? = null
    private var answer: EditText? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_game, container, false)

        gameQuestion = view.findViewById(R.id.game_question)
        answerResult = view.findViewById(R.id.answer_result)
        answer = view.findViewById(R.id.answer)

        initNextButton(view.findViewById(R.id.button_next))
        initAnswerButton(view.findViewById(R.id.button_answer))

        generateNextQuestion()

        return view
    }

    private fun initNextButton(button: Button) {
        button.setOnClickListener { generateNextQuestion() }
    }

    private fun initAnswerButton(button: Button) {
        button.setOnClickListener { checkAnswer() }
    }

    private fun generateNextQuestion() {
        answerResult!!.text = ""
        answer!!.setText("")
        gameQuestion!!.text = Game.generateNextQuestion()
    }

    private fun checkAnswer() {
        val actual = answer!!.text.toString().trim()
        if (actual == "") {
            return
        }
        val expected: String? =
            Calc.evalExpression(gameQuestion!!.text.toString().trim())
        if (expected == null) {
            answerResult!!.text = getString(R.string.error)
            return
        }
        if (expected == actual) {
            answerResult!!.text = getString(R.string.correct)
        } else {
            answerResult!!.text = getString(R.string.wrong, expected)
        }
    }
}
```

* Add code for [feature/game/src/main/res/layout/fragment_game.xml](../exercise3/solution/feature/game/src/main/res/layout/fragment_game.xml)

```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    tools:context=".GameFragment">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:id="@+id/main_buttons_layout"
        android:layout_alignParentBottom="false"
        android:gravity="center"
        android:orientation="vertical">

        <TextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text=""
            android:textSize="64sp"
            android:textAlignment="center"
            android:textColor="@color/black"
            android:id="@+id/game_question"
            android:layout_margin="16dp" />

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:gravity="center"
            android:orientation="horizontal">

            <EditText
                android:id="@+id/answer"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:ems="12"
                android:inputType="number"
                android:layout_marginTop="30dp"
                android:layout_marginBottom="30dp"
                />
            <com.google.android.material.button.MaterialButton
                android:layout_width="72dp"
                android:layout_height="72dp"
                app:cornerRadius="36dp"
                style="@style/Widget.MaterialComponents.ExtendedFloatingActionButton"
                android:textSize="21sp"
                android:layout_margin="12dp"
                android:backgroundTint="#FB8C00"
                app:icon="@drawable/baseline_send_24"
                app:iconTint="@color/white"
                android:id="@+id/button_answer"/>
        </LinearLayout>

        <com.google.android.material.button.MaterialButton
            android:layout_width="150dp"
            android:layout_height="72dp"
            app:cornerRadius="36dp"
            style="@style/Widget.MaterialComponents.ExtendedFloatingActionButton"
            android:text="@string/next"
            android:textSize="21sp"
            android:textColor="@color/white"
            android:layout_margin="12dp"
            android:id="@+id/button_next"/>

        <TextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text=""
            android:textSize="48sp"
            android:textAlignment="center"
            android:textColor="@color/black"
            android:id="@+id/answer_result"
            android:layout_margin="16dp" />

    </LinearLayout>

</RelativeLayout>
```

* Add code for [feature/game/src/main/res/values/strings.xml](../exercise3/solution/feature/game/src/main/res/values/strings.xml)

```xml
<resources>
    <string name="next">Next</string>
    <string name="error">Error</string>
    <string name="correct">Correct!</string>
    <string name="wrong">Wrong, expected: %1$s</string>
</resources>
```

---
### Update :app module

Now let's update the `app` module to use the other modules we've added.

* Update the [app/build.gradle.kts](../exercise3/solution/app/build.gradle.kts) with feature dependencies

```kotlin
implementation(project(":feature:calc"))
implementation(project(":feature:game"))
```

* Add calculate vector
* Add videogame asset vector
* Add a `Android Resource File` of type `menu` called `bottom_nav_menu` with the following contents:

<p align="center">
<img width="75%" height="75%" src="https://user-images.githubusercontent.com/120980/220099390-79e8288c-095b-4c39-9197-b7e26c59933b.png">
</p>
<p align="center">
<img width="50%" height="50%" src="https://user-images.githubusercontent.com/120980/220099555-c6b020f1-38e1-49d2-938f-b4c368b72e20.png">
</p>

```xml
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android">

    <item android:id="@+id/calc_item"
        android:title="Calc"
        android:icon="@drawable/baseline_calculate_24"/>
    <item android:id="@+id/game_item"
        android:title="Game"
        android:icon="@drawable/baseline_videogame_asset_24"/>
</menu>
```

* Update the contents of [app/src/main/res/layout/activity_main.xml](../exercise3/solution/app/src/main/res/layout/activity_main.xml)

```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <FrameLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:id="@+id/frame_container"
        android:layout_above="@id/bottom_nav"
        />

    <com.google.android.material.bottomnavigation.BottomNavigationView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:id="@+id/bottom_nav"
        android:layout_alignParentBottom="true"
        app:menu="@menu/bottom_nav_menu"/>

</RelativeLayout>
```

* Update the contents of [app/src/main/java/com/gradle/lab/mycalculator/MainActivity.kt](../exercise3/solution/app/src/main/java/com/gradle/lab/mycalculator/MainActivity.kt)

```kotlin
package com.gradle.lab.mycalculator

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.gradle.lab.calc.CalcFragment
import com.gradle.lab.game.GameFragment

class MainActivity : AppCompatActivity() {

    private val calcFragment = CalcFragment()
    private val gameFragment = GameFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_nav)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_container, calcFragment)
            .commit()
        bottomNavigationView.setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener { item ->
            handleMenuItemSelection(
                item
            )
        })
    }

    private fun handleMenuItemSelection(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.calc_item -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_container, calcFragment)
                    .commit()
                return true
            }
            R.id.game_item -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_container, gameFragment)
                    .commit()
                return true
            }
        }
        return false
    }
}
```

Press the `Gradle sync` button. You can now run the app and play with it.
