package com.example.ecomviews.developmenteffiency.screens

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.ecomviews.developmenteffiency.ViewModels.DEViewModel
import com.example.ecomviews.R
import com.example.ecomviews.developmenteffiency.DevEfficiencyActivity

class SplashFragment : Fragment() {
    private lateinit var viewModel: DEViewModel
    private lateinit var tvCompose: TextView
    private lateinit var tvVS: TextView
    private lateinit var tvXML: TextView
    private lateinit var tvComposeVersion: TextView
    private lateinit var activity: DevEfficiencyActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = context as DevEfficiencyActivity
        viewModel = ViewModelProvider(requireActivity()).get(DEViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_splash, container, false)

        tvCompose = view.findViewById(R.id.tvCompose)
        tvVS = view.findViewById(R.id.tvVS)
        tvXML = view.findViewById(R.id.tvXML)
        tvComposeVersion = view.findViewById(R.id.tvComposeVersion)

        Handler(Looper.getMainLooper()).postDelayed({
            moveToLogin()
        }, 3000)

        return view
    }

    private fun moveToLogin() {
        activity.removeFragmentFromBackStack(newInstance())
        activity.navigateTo(LoginFragment.newInstance())

    }
    companion object {
        @JvmStatic
        fun newInstance() = SplashFragment()
    }
}
