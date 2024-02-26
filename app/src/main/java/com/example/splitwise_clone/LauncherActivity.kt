package com.example.splitwise_clone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.splitwise_clone.databinding.ActivityLauncherBinding
import com.example.splitwise_clone.fragments.launcher_Fragment
import com.example.splitwise_clone.fragments.login_Fragment
import com.example.splitwise_clone.fragments.signup_Fragment

class LauncherActivity : AppCompatActivity() {
    private var TAG="splitwise:launcher:"

    private lateinit var binding: ActivityLauncherBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLauncherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, launcher_Fragment())
            .commit()
    }
}