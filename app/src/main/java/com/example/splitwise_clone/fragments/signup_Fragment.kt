package com.example.splitwise_clone.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.splitwise_clone.HomeActivity
import com.example.splitwise_clone.R
import com.example.splitwise_clone.databinding.FragmentSignupBinding


class signup_Fragment : Fragment() {

    private var TAG="splitwise:sign_up:"
    private lateinit var binding:FragmentSignupBinding

    private lateinit var btn_back:Button

    private lateinit var btn_done:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView: of signup")
        binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d(TAG, "onViewCreated: of signup")
        binding.btnDone.setOnClickListener { openHomeActivity() }
        binding.btnBack.setOnClickListener { requireActivity().supportFragmentManager.popBackStack() }
    }
    private fun openHomeActivity() {
        val intent = Intent(activity, HomeActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

}