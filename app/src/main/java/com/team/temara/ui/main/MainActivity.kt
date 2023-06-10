package com.team.temara.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavHost
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.team.temara.R
import com.team.temara.databinding.MainActivityBinding
import com.team.temara.ui.main.fragment.ArticleFragment
import com.team.temara.ui.main.fragment.HomeFragment

class MainActivity : AppCompatActivity() {
    private val binding : MainActivityBinding by lazy {
        MainActivityBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navBottom: BottomNavigationView = binding.navBottom

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navMainFragment) as NavHostFragment
        val navController = navHostFragment.navController
        navBottom.setupWithNavController(navController)

    }
}