package com.example.splitwise_clone

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.splitwise_clone.databinding.ActivityLauncherBinding
import com.example.splitwise_clone.fragments.launcher_Fragment
import com.example.splitwise_clone.friends.tag_head
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LauncherActivity : AppCompatActivity() {
    private var TAG= tag_head+"launcher:"

    private lateinit var binding: ActivityLauncherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLauncherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = Firebase.auth.currentUser
        if(user!=null){
            //user!=null is user logged in take user to home activity
            Log.d(TAG, "user_email: ${user.email}")
            startActivity(Intent(this,HomeActivity::class.java))
            this.finish()
        }
        else{
            //if not loggedin take user to launcher_fragment
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, launcher_Fragment())
                .commit()
        }


    }

    override fun onStart() {
        super.onStart()

    }
}