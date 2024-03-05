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
import com.example.splitwise_clone.friends.tag_head
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class signup_Fragment : Fragment() {

    private var TAG = tag_head+"sign_up:"

    private lateinit var binding: FragmentSignupBinding

    private lateinit var auth: FirebaseAuth

    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
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
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { createUserTask ->
                if (createUserTask.isSuccessful) {
                    // User creation successful, proceed to save additional details in Firestore
                    val currentUser = auth.currentUser
                    currentUser?.let { user ->
                        // Call signUpDetails function to create user details map
                        val userDetails = signUpDetails(binding.etFullname.text.toString(), email)
                        // Store user details in Firestore
                        firestore.collection("users")
                            .document(user.uid)
                            .set(userDetails)
                            .addOnSuccessListener {
                                // Firestore document successfully created
                                Log.d(TAG, "User details added to Firestore")
                                openHomeActivity()
                            }
                            .addOnFailureListener { e ->
                                // Error occurred while adding user details to Firestore
                                Log.e(TAG, "Error adding user details to Firestore", e)
                            }

                        // Create a "friends" sub-collection for the user
                        firestore.collection("users")
                            .document(user.uid)
                            .collection("friends")
                            .document("friendsList")
                            .set(mapOf("friends" to emptyList<String>())) // Initialize with an empty list of friends
                            .addOnSuccessListener {
                                Log.d(TAG, "Friends sub-collection created for the user")
                            }
                            .addOnFailureListener { e ->
                                Log.e(TAG, "Error creating friends sub-collection for the user", e)
                            }
                    }
                } else {
                    // If sign up fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", createUserTask.exception)
                }
            }
    }

    fun signUpDetails(fullName: String, email: String): Map<String, Any> {
        val userDetails = HashMap<String, Any>()
        userDetails["fullName"] = fullName
        userDetails["email"] = email
        // You can add more fields as needed
        return userDetails
    }

}