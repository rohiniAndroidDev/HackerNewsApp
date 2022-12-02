package com.example.sampleappfortest.home.presentation.ui.activities

import android.app.AlertDialog
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.example.sampleappfortest.R
import com.example.sampleappfortest.home.presentation.ui.fragments.CalendarFragment
import com.example.sampleappfortest.home.presentation.ui.fragments.HomeFragment
import com.example.sampleappfortest.home.presentation.ui.fragments.ProfileFragment
import com.example.sampleappfortest.home.presentation.ui.fragments.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.android.support.DaggerAppCompatActivity
import retrofit2.Retrofit
import javax.inject.Inject


class HomeActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var restApi: Retrofit
    private lateinit var navGraph: NavGraph
    private lateinit var navController: NavController
    private lateinit var navView: BottomNavigationView
    private lateinit var sharedPrefUtil: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setStatusBarColor(this, R.color.status_bar_color)
        setContentView(com.example.sampleappfortest.R.layout.activity_home)
        sharedPrefUtil =
            PreferenceManager.getDefaultSharedPreferences(this.applicationContext)
        setBottomnav()


    }

    fun navigateDestination(destination: Int) {
        navGraph.startDestination = destination
        navController.graph = navGraph
        // fragment = navView.getf
        navView.setupWithNavController(navController)


    }


    private fun setBottomnav() {

        navView = findViewById(R.id.bottomNavigation)

        val navHostFragment = supportFragmentManager
            .findFragmentById(com.example.sampleappfortest.R.id.fragment) as NavHostFragment?

        val graphInflater = navHostFragment?.navController?.navInflater
        navGraph = graphInflater?.inflate(R.navigation.home_nav)!!
        navController = navHostFragment.navController

        navigateDestination(R.id.navigation_home)

    }

    private fun getCurrentFragment(): Fragment {
        val navHostFragment = supportFragmentManager.primaryNavigationFragment as NavHostFragment?
        val fragmentManager = navHostFragment!!.childFragmentManager
        val fragment = fragmentManager.primaryNavigationFragment

        return fragment!!
    }

    override fun onBackPressed() {
        //   super.onBackPressed()
        // navigateDestination(R.id.navigation_home)

        // if (navGraph.startDestination != R.id.navigation_home){

        //  if (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) is HomeFragment) {


        if (getCurrentFragment() is ProfileFragment) {
            navigateDestination(R.id.navigation_home)
        } else if (getCurrentFragment() is SearchFragment) {
            navigateDestination(R.id.navigation_home)
        } else if (getCurrentFragment() is CalendarFragment) {
            navigateDestination(R.id.navigation_home)
        } else if (getCurrentFragment() is HomeFragment) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle(getString(R.string.alert))
            builder.setMessage(getString(R.string.exit_alert))
            builder.setPositiveButton(R.string.exit_yes) { dialog, _ ->
                dialog.dismiss()
                finish()
                finishAffinity()
            }
            builder.setNegativeButton(R.string.exit_no) { dialog, _ ->
                dialog.dismiss()
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        } else {
            navigateDestination(R.id.navigation_home)
        }
        /* } else {

             navigateDestination(R.id.navigation_home)
         }*/
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(navController) ||
                super.onOptionsItemSelected(item)
    }
}


