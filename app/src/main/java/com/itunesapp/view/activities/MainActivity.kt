package com.itunesapp.view.activities

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.itunesapp.R
import com.itunesapp.core.activities.BaseActivity
import com.itunesapp.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private lateinit var navigationController: NavController

    override fun initPage() {

        initNavigation()
    }

    private fun initNavigation(){

        navigationController = Navigation.findNavController(this@MainActivity, R.id.fragment)
        NavigationUI.setupWithNavController(binding.toolbar, navigationController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navigationController, null)
    }
}