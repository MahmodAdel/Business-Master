package com.example.businessv1.frame.presentation

import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.*
import com.example.businessv1.R
import com.example.businessv1.frame.presentation.utils.ToolbarBehavior
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    companion object{
        init {
            System.loadLibrary("keys")
        }
    }
    public external fun getNativeKey(): String?


    @Inject
    lateinit var fragmentFactory: MainFragmentFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val key1 =
            String(Base64.decode(getNativeKey(), Base64.DEFAULT))
        Log.d("SecureKey",key1)
        initAppBar()
        initNavController();

    }

    private fun initAppBar() {
        setSupportActionBar(appbar_container)
        getSupportActionBar()?.setDisplayShowTitleEnabled(false);
        (appbar.layoutParams as CoordinatorLayout.LayoutParams).behavior = ToolbarBehavior()

    }

    private fun initNavController() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_main) as NavHostFragment
        navController = navHostFragment.findNavController()
      //  NavigationUI.setupActionBarWithNavController(this, navController);
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.businessFragment, R.id.favoriteFragment),
            drawer_layout
        )
        val toggle = ActionBarDrawerToggle(
            this,
            drawer_layout,
            appbar_container,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        setupActionBarWithNavController(navController, appBarConfiguration)
        nav_view.setupWithNavController(navController)
    }

    override fun onOptionsItemSelected(item: MenuItem)= item.onNavDestinationSelected(navController)
            || super.onOptionsItemSelected(item)


    override fun onSupportNavigateUp() = navController.navigateUp(appBarConfiguration)
}
