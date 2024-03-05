package com.example.splitwise_clone.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.splitwise_clone.HomeActivity
import com.example.splitwise_clone.R
import com.example.splitwise_clone.databinding.FragmentLoginBinding
import com.example.splitwise_clone.friends.tag_head
import com.google.firebase.auth.FirebaseAuth


class login_Fragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    private lateinit var auth: FirebaseAuth

    private var TAG= tag_head+"login:"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
            binding = FragmentLoginBinding.inflate(inflater, container, false)
            return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
            signinWithFirebase(binding.etLoginEmail.text.toString(),binding.etLoginPassword.text.toString())
        }

        binding.tvForgotPassword.setOnClickListener{
            openForgotpassword()
        }
    }

    private fun openForgotpassword() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, forgotpassword_fragment())
            .addToBackStack(null)
            .commit()
    }

    private  fun signinWithFirebase(email:String,password:String)
    {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    auth.currentUser
                    startActivity(Intent(activity, HomeActivity::class.java))
                    activity?.finish()

                } else {
                    // If sign in fails, display a message to the user.
                    Log.d(TAG, "signInWithEmail:failure", it.exception)

                }
            }
    }

}