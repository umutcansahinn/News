package com.umutcansahin.mynewsapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.umutcansahin.mynewsapp.R
import com.umutcansahin.mynewsapp.common.extensions.gone
import com.umutcansahin.mynewsapp.common.extensions.visible
import com.umutcansahin.mynewsapp.data.preference.DataStorePreference
import com.umutcansahin.mynewsapp.databinding.ActivityMainBinding
import com.umutcansahin.mynewsapp.manager.language.LanguageManager
import com.umutcansahin.mynewsapp.manager.theme.ThemeManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    @Inject
    lateinit var dataStorePreference: DataStorePreference

    @Inject
    lateinit var languageManager: LanguageManager

    @Inject
    lateinit var themeManager: ThemeManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navHostFragment = supportFragmentManager
            .findFragmentById(binding.fragmentContainerView.id) as NavHostFragment

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home_screen,
                R.id.navigation_setting_screen,
                R.id.navigation_search_screen
            )
        )

        navController = navHostFragment.findNavController()
        setupActionBarWithNavController(navController, appBarConfiguration)

        val bottomNavView: BottomNavigationView = binding.bottomNavView
        bottomNavView.setupWithNavController(navController)

        setLanguage()
        setTheme()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() or super.onSupportNavigateUp()
    }

    private fun setLanguage() {
        lifecycleScope.launch {
            dataStorePreference.getSelectedLanguage.flowWithLifecycle(lifecycle)
                .collect { countryCode ->
                    languageManager.takeLanguageCode(countryCode = countryCode)
                }
        }
    }

    private fun setTheme() {
        lifecycleScope.launch {
            dataStorePreference.isDarkModeEnabled.flowWithLifecycle(lifecycle)
                .collect { isLight ->
                    themeManager.setUiTheme(isLight)
                }
        }
    }

    fun hideBottomNavigation() {
        binding.bottomNavView.gone()

    }

    fun showBottomNavigation() {
        binding.bottomNavView.visible()
    }


    fun hideActionBar() {
        supportActionBar?.hide()
    }

    fun showActionBar() {
        supportActionBar?.show()
    }
}