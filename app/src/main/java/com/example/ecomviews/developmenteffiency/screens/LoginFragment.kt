package com.example.ecomviews.developmenteffiency.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.ecomviews.developmenteffiency.ViewModels.DEViewModel
import com.example.ecomviews.developmenteffiency.API.ModelClasses.LoginReq
import com.example.ecomviews.R
import com.example.ecomviews.developmenteffiency.DevEfficiencyActivity

class LoginFragment : Fragment() {

    private lateinit var viewModel: DEViewModel
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var signUpTextView: TextView
    private lateinit var activity: DevEfficiencyActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = context as DevEfficiencyActivity
        viewModel = ViewModelProvider(requireActivity()).get(DEViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        usernameEditText = view.findViewById(R.id.etUsername)
        passwordEditText = view.findViewById(R.id.etPassword)
        loginButton = view.findViewById(R.id.btnLogin)
        signUpTextView = view.findViewById(R.id.tvSignUp)

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            if (username.isNotEmpty() && password.isNotEmpty()) {
                viewModel.loginUser(requireContext(), LoginReq(username, password))
                observeLoginState()
            } else {
                Toast.makeText(requireContext(), "Please enter all fields", Toast.LENGTH_SHORT).show()
            }
        }

        signUpTextView.setOnClickListener {
            activity.navigateTo(SignUpFragment.newInstance())
       }

        return view
    }

    private fun observeLoginState() {
        viewModel.isLogin.observe(viewLifecycleOwner) { isLogin ->
            if (isLogin == true) {
                Toast.makeText(requireContext(), "Login successful", Toast.LENGTH_SHORT).show()
                activity.removeFragmentFromBackStack(newInstance())
                activity.navigateTo(MainFragment.newInstance())
            }
        }
    }
    companion object {
        @JvmStatic
        fun newInstance() =  LoginFragment()
    }
}