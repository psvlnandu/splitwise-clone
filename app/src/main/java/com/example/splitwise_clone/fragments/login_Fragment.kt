package com.example.splitwise_clone.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.splitwise_clone.HomeActivity
import com.example.splitwise_clone.R
import com.example.splitwise_clone.databinding.FragmentLauncherBinding
import com.example.splitwise_clone.databinding.FragmentLoginBinding
import com.example.splitwise_clone.databinding.FragmentSignupBinding


class login_Fragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
            binding = FragmentLoginBinding.inflate(inflater, container, false)
            return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
            startActivity(Intent(activity, HomeActivity::class.java))
            activity?.finish()
        }

        binding.tvForgotPassword.setOnClickListener{
            openForgotpassword();
        }
    }

    private fun openForgotpassword() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, forgotpassword_fragment())
            .addToBackStack(null)
            .commit()
    }

}