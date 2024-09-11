package com.vanskarner.cleanmoviek.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.vanskarner.cleanmoviek.R
import dagger.hilt.android.AndroidEntryPoint
import java.util.Objects

@AndroidEntryPoint
class MenuActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_activity)
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomMenu)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_container) as NavHostFragment
        val navController = Objects.requireNonNull(navHostFragment).navController
        NavigationUI.setupWithNavController(bottomNavigation, navController)
    }

}