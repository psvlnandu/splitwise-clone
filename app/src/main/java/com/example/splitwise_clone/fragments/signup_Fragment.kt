package com.example.splitwise_clone.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.splitwise_clone.HomeActivity
import com.example.splitwise_clone.databinding.FragmentSignupBinding
import com.google.firebase.auth.FirebaseAuth


class signup_Fragment : Fragment() {

    private var TAG = "splitwise:sign_up:"

    private lateinit var binding: FragmentSignupBinding

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView: of signup")
        binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d(TAG, "onViewCreated: of signup")
        binding.btnDone.setOnClickListener {
            signUpWithFirebase(
                binding.etEmail.text.toString(),
                binding.etPassword.text.toString()
            )
        }
        binding.btnBack.setOnClickListener { requireActivity().supportFragmentManager.popBackStack() }

    }

    private fun openHomeActivity() {
        val intent = Intent(activity, HomeActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    private fun signUpWithFirebase(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG, "createUserWithEmail:success")
                auth.currentUser
                openHomeActivity()
//                updateUI(user)
            } else {
                // If sign in fails, display a message to the user.
                Log.w(TAG, "createUserWithEmail:failure", it.exception)
//                updateUI(null)
            }
        }

    }

}