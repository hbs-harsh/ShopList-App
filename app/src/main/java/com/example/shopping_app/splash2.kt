package com.example.shopping_app

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.shopping_app.databinding.FragmentSplash2Binding

class splash2 : Fragment() {

    private var _binding: FragmentSplash2Binding? = null
    private val binding get() = _binding!!

    // Static credentials
    private val validUsername = "admin"
    private val validPassword = "password123"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplash2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up login button click listener
        binding.loginButton.setOnClickListener {
            val username = binding.usernameEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (validateCredentials(username, password)) {
                navigateToMainActivity()
            } else {
                binding.errorTextView.visibility = View.VISIBLE
                binding.errorTextView.text = "Invalid username or password"
            }
        }
    }

    private fun validateCredentials(username: String, password: String): Boolean {
        return username == validUsername && password == validPassword
    }

    private fun navigateToMainActivity() {
        val intent = Intent(requireContext(), MainActivity2::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}