package com.example.splitwise_clone

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.splitwise_clone.databinding.ActivityHomeBinding
import com.example.splitwise_clone.nav_fragments.account
import com.example.splitwise_clone.nav_fragments.activity
import com.example.splitwise_clone.nav_fragments.friends
import com.example.splitwise_clone.nav_fragments.groups

class HomeActivity : AppCompatActivity(), account.LogoutListener {

    private lateinit var binding: ActivityHomeBinding

    private var TAG="splitwise:logout"

    private lateinit var groupsFragment: Fragment
    private lateinit var friendsFragment: Fragment
    private lateinit var activityFragment: Fragment
    private lateinit var accountFragment: Fragment

    private val fragmentManager = supportFragmentManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        groupsFragment=groups()
        friendsFragment=friends()
        activityFragment=activity()
        accountFragment=account()

        // Set the initial fragment
        fragmentManager.beginTransaction().replace(R.id.mainPage,groupsFragment ).commit()

        binding.navBar.setOnItemSelectedListener {
            val selectedFragment:Fragment=when(it.itemId)
            {
                R.id.nav_groups->groupsFragment
                R.id.nav_friends->friendsFragment
                R.id.nav_activity->activityFragment
                R.id.nav_account->accountFragment
                else->groupsFragment
            }
            fragmentManager.beginTransaction().replace(R.id.mainPage,selectedFragment).commit()
            true
        }


        (accountFragment as account).setLogoutListener(this)

        Log.d(TAG, "HomeActivity onCreate: AccountFragment listener set up")
    }

    override fun onLogout() {
        // Handle logout here
        // Navigate back to the launcher fragment in the launcher activity
        Log.d(TAG, "onlogout, launcher activity open!")
        val intent = Intent(this, LauncherActivity::class.java)
        startActivity(intent)
        finish()
    }
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        finish() // Close the app
    }

}