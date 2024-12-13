package com.example.ecomviews.developmenteffiency.screens

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.ecomviews.developmenteffiency.ViewModels.DEViewModel
import com.example.ecomviews.R
import com.example.ecomviews.developmenteffiency.API.ModelClasses.SignUpUser

class SettingFragment : Fragment() {
    private lateinit var viewModel: DEViewModel
    private lateinit var etUsername: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnUpdate: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(DEViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_setting, container, false)

        etUsername = view.findViewById(R.id.etUsername)
        etEmail = view.findViewById(R.id.etEmail)
        etPassword = view.findViewById(R.id.etPassword)
        btnUpdate = view.findViewById(R.id.btnUpdate)

        viewModel.getUserAuth(requireContext())
        viewModel.userAuthData.observe(viewLifecycleOwner) { authData ->
            etUsername.setText(authData?.first ?: "")
            etEmail.setText(authData?.second ?: "")
            etPassword.setText(authData?.third ?: "")
        }

        btnUpdate.setOnClickListener {
            val username = etUsername.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            if (username.isNotBlank() && email.isNotBlank() && password.isNotBlank()) {
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(requireContext(), "Invalid email format", Toast.LENGTH_SHORT).show()
                } else {
                    val user = SignUpUser(username = username, email = email, password = password)
                    viewModel.SignUpUser(requireContext(), user,true)
                }
            } else {
                Toast.makeText(requireContext(), "All fields are required", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = SettingFragment()
    }
}
