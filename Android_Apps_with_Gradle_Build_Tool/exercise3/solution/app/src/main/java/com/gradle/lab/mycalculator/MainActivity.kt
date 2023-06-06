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