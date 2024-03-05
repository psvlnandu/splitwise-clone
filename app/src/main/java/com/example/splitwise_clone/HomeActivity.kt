package com.example.splitwise_clone

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.splitwise_clone.databinding.ActivityHomeBinding
import com.example.splitwise_clone.friends.tag_head
import com.example.splitwise_clone.nav_fragments.account
import com.example.splitwise_clone.nav_fragments.activity
import com.example.splitwise_clone.nav_fragments.friends
import com.example.splitwise_clone.nav_fragments.groups
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class HomeActivity : AppCompatActivity(), account.LogoutListener {

    private lateinit var binding: ActivityHomeBinding
    private var TAG = tag_head+"logout:"
    private lateinit var groupsFragment: Fragment
    private lateinit var friendsFragment: Fragment
    private lateinit var activityFragment: Fragment
    private lateinit var accountFragment: Fragment
    private val fragmentManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeFragments()
        setupInitialFragment()
        setupNavigationBar()
        setupAccountFragment()
        fetchUserInfo()
        Log.d(TAG, "HomeActivity onCreate: AccountFragment listener set up")
    }

    private fun initializeFragments() {
        groupsFragment = groups()
        friendsFragment = friends()
        activityFragment = activity()
        accountFragment = account()
    }

    private fun setupInitialFragment() {
        fragmentManager.beginTransaction().replace(R.id.mainPage, groupsFragment).commit()
    }

    private fun setupNavigationBar() {
        binding.navBar.setOnItemSelectedListener { menuItem ->
            val selectedFragment: Fragment = when (menuItem.itemId) {
                R.id.nav_groups -> groupsFragment
                R.id.nav_friends -> friendsFragment
                R.id.nav_activity -> activityFragment
                R.id.nav_account -> accountFragment
                else -> groupsFragment
            }
            fragmentManager.beginTransaction().replace(R.id.mainPage, selectedFragment).commit()
            true
        }
    }

    private fun setupAccountFragment() {
        if (accountFragment is account) {
            (accountFragment as account).setLogoutListener(this)
        }
    }

    override fun onLogout() {
        Log.d(TAG, "onlogout, launcher activity open!")
        val intent = Intent(this, LauncherActivity::class.java)
        startActivity(intent)
        finish()
    }
    private fun fetchUserInfo() {
        // Start coroutine to fetch user information
        CoroutineScope(Dispatchers.Main).launch {
            val (userName, userEmail) = retrieveUserInfo()
            passUserInfoToFragments(fetchFullNameFromFirestore(), userEmail)
        }
    }
    private suspend fun retrieveUserInfo(): Pair<String, String> = withContext(Dispatchers.IO) {
        // Retrieve user information from Firebase Authentication
        val currentUser = FirebaseAuth.getInstance().currentUser
        currentUser?.let {
            val userName = it.displayName ?: ""
            val userEmail = it.email ?: ""
            return@withContext Pair(userName, userEmail)
        }
        // Return empty strings if user is not logged in
        return@withContext Pair("", "")
    }

    private fun passUserInfoToFragments(userName: String, userEmail: String) {
        // Pass user information to fragments
        val fragments = listOf(groupsFragment, friendsFragment, activityFragment, accountFragment)
        fragments.forEach { fragment ->
            when (fragment) {
                is account -> fragment.setUserInfo(userName, userEmail)
                // Add cases for other fragments if needed
            }
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private suspend fun fetchFullNameFromFirestore(): String = withContext(Dispatchers.IO) {
        // Get current user's UID
        val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid

        // Retrieve full name from Firestore
        val fullName = currentUserUid?.let { uid ->
            FirebaseFirestore.getInstance().collection("users")
                .document(uid)
                .get()
                .await()
                .getString("fullName")
        }
        Log.d(TAG, "fetchFullNameFromFirestore: $fullName")

        // Return full name or empty string if not found
        fullName ?: ""
    }
}
