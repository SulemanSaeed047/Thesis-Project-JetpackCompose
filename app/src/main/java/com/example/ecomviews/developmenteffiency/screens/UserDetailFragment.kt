package com.example.ecomviews.developmenteffiency.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.ecomviews.developmenteffiency.ViewModels.DEViewModel
import com.example.ecomviews.R

class UserDetailFragment : Fragment() {

    private lateinit var viewModel: DEViewModel
    private var userId: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(DEViewModel::class.java)
        userId = arguments?.getLong("userId")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user_detail, container, false)

        if (userId != null) {
            viewModel.allUsers.observe(viewLifecycleOwner, { users ->
                val selectedUser = users.find { it.id == userId }
                if (selectedUser != null) {
                    view.findViewById<TextView>(R.id.tvFirstNameValue).text = selectedUser.name.firstname
                    view.findViewById<TextView>(R.id.tvLastNameValue).text = selectedUser.name.lastname
                    view.findViewById<TextView>(R.id.tvEmailValue).text = selectedUser.email
                    view.findViewById<TextView>(R.id.tvUsernameValue).text = selectedUser.username
                    view.findViewById<TextView>(R.id.tvPhoneValue).text = selectedUser.phone
                    view.findViewById<TextView>(R.id.tvCityValue).text = selectedUser.address.city
                    view.findViewById<TextView>(R.id.tvStreetValue).text = selectedUser.address.street
                    view.findViewById<TextView>(R.id.tvNumberValue).text = selectedUser.address.number.toString()
                    view.findViewById<TextView>(R.id.tvZipcodeValue).text = selectedUser.address.zipcode
                    view.findViewById<TextView>(R.id.tvLatitudeValue).text = selectedUser.address.geolocation.lat
                    view.findViewById<TextView>(R.id.tvLongitudeValue).text = selectedUser.address.geolocation.long
                } else {
                    Toast.makeText(requireContext(), "No user found", Toast.LENGTH_SHORT).show()
                }
            })
        }

        return view
    }

    companion object {
        private const val ARG_USER_ID = "userId"
        @JvmStatic
        fun newInstance(userId: Long) = UserDetailFragment().apply {
            arguments = Bundle().apply {
                putLong(ARG_USER_ID, userId)
            }
        }
    }
}
