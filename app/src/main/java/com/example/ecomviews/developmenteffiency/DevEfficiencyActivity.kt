package com.example.ecomviews.developmenteffiency

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Debug
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.example.ecomviews.developmenteffiency.ViewModels.DEViewModel
import com.example.ecomviews.R
import com.example.ecomviews.developmenteffiency.screens.SplashFragment

class DevEfficiencyActivity : AppCompatActivity() {
    private lateinit var viewModel: DEViewModel
    private lateinit var internetChangeReceiver: BroadcastReceiver
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Start tracing with a file name and buffer size
        Debug.startMethodTracing("app_startup_trace", 16 * 1024 * 1024) // 16MB buffer

        val startTime = System.nanoTime()
        //enableEdgeToEdge()
        setContentView(R.layout.activity_development_efficiency)
        val currentNightMode = AppCompatDelegate.getDefaultNightMode()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        if (savedInstanceState == null) {
            navigateTo(SplashFragment.newInstance())
        }
        // Stop tracing after UI initialization is complete
        Debug.stopMethodTracing()

        val endTime = System.nanoTime()
        val startupTime = (endTime - startTime) / 1_000_000  // to milliseconds
        Log.d("StartupTime", "XML Layout Startup Time: ${startupTime} ms")

        viewModel = ViewModelProvider(this).get(DEViewModel::class.java)
        if (savedInstanceState == null) {
            viewModel.setTheme(isDark = currentNightMode == AppCompatDelegate.MODE_NIGHT_YES,"" )
        }
        viewModel.checkOnline(this)
        internetChangeReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (intent?.action == ConnectivityManager.CONNECTIVITY_ACTION) {
                    viewModel.checkOnline(this@DevEfficiencyActivity)
                }
            }
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        printBackStack()
        val backStackEntryCount = supportFragmentManager.backStackEntryCount
        if (backStackEntryCount <= 0) {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        val ifilter = IntentFilter()
        val receiverFlags = ContextCompat.RECEIVER_EXPORTED
        ifilter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        ContextCompat.registerReceiver(this,internetChangeReceiver, ifilter,receiverFlags)
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(internetChangeReceiver)
    }

    fun navigateTo(fragment: Fragment) {
        val fragmentTag = fragment::class.java.simpleName
        Log.d("BackStack 123", "create: $fragmentTag")
        val existingFragment = supportFragmentManager.findFragmentByTag(fragmentTag)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment, fragmentTag)  // Add a tag to avoid duplicate instances
            .addToBackStack(fragmentTag) // Add this transaction to backstack for back navigation
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE) // Optional: for smooth transitions
            .commit()
    }
    fun removeFragmentFromBackStack(fragment: Fragment) {
        val fragmentTag = fragment::class.java.simpleName
        Log.d("BackStack 123", "remove: $fragmentTag")
        val fragmentInBackStack = supportFragmentManager.findFragmentByTag(fragmentTag)
        if (fragmentInBackStack != null) {
           // supportFragmentManager.popBackStack(fragmentTag, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            supportFragmentManager.popBackStackImmediate(fragmentTag, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }
    fun printBackStack(){
        val backStackCount = supportFragmentManager.backStackEntryCount
        for (i in backStackCount - 1 downTo 0) {
            val backStackEntry = supportFragmentManager.getBackStackEntryAt(i)
            val fragmentTag = backStackEntry.name
            Log.d("BackStack", "Fragment at index $i: $fragmentTag")
        }
        Log.d("BackStack", "###########################")
    }

}
