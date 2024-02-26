package com.example.splitwise_clone.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.splitwise_clone.R
import com.example.splitwise_clone.databinding.FragmentLauncherBinding


class launcher_Fragment : Fragment() {
    private lateinit var binding: FragmentLauncherBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLauncherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSignup.setOnClickListener {
            // Handle sign-up button click
            openSignUpFragment()
        }

        binding.btnLogin.setOnClickListener {
            // Handle login button click
            openLoginFragment()
        }
    }

    private fun openSignUpFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, signup_Fragment())
            .addToBackStack(null)
            .commit()
    }

    private fun openLoginFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, login_Fragment())
            .addToBackStack(null)
            .commit()
    }
}