package com.example.splitwise_clone.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.splitwise_clone.R
import com.example.splitwise_clone.databinding.FragmentForgotpasswordBinding
import com.example.splitwise_clone.databinding.FragmentSignupBinding

class forgotpassword_fragment : Fragment() {

    private lateinit var binding: FragmentForgotpasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForgotpasswordBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRequestPswrd.setOnClickListener{
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    }