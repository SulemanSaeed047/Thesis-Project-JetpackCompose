package com.example.ecomviews.developmenteffiency.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import android.widget.ImageView
import com.example.ecomviews.developmenteffiency.ViewModels.DEViewModel
import com.example.ecomviews.R

class ThemeFragment : Fragment() {
    private lateinit var viewModel: DEViewModel
    private lateinit var switchDarkTheme: Switch
    private lateinit var radioGroupTheme: RadioGroup
    private lateinit var ivThemePreview: ImageView
    private lateinit var rbLightTheme: RadioButton
    private lateinit var rbDarkTheme: RadioButton
    private lateinit var tvDarkTheme: TextView
    private lateinit var tvPreview: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(DEViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_theme, container, false)

        switchDarkTheme = view.findViewById(R.id.switchDarkTheme)
        radioGroupTheme = view.findViewById(R.id.radioGroupTheme)
        ivThemePreview = view.findViewById(R.id.ivThemePreview)
        rbLightTheme = view.findViewById(R.id.rbLightTheme)
        rbDarkTheme = view.findViewById(R.id.rbDarkTheme)
        tvDarkTheme = view.findViewById(R.id.tvDarkTheme)
        tvPreview = view.findViewById(R.id.tvPreview)

        viewModel.isDarkTheme.observe(viewLifecycleOwner, { isDark ->
            switchDarkTheme.isChecked = isDark
            ivThemePreview.setImageResource(
                if (isDark) R.drawable.dark_theme else R.drawable.light_theme
            )
        })

        switchDarkTheme.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setTheme(isChecked, "TS")
        }
        radioGroupTheme.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rbLightTheme -> {
                    ivThemePreview.setImageResource(R.drawable.light_theme)
                }
                R.id.rbDarkTheme -> {
                    ivThemePreview.setImageResource(R.drawable.dark_theme)
                }
            }
        }

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = ThemeFragment()
    }
}
