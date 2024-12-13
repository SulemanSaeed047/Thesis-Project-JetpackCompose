package com.example.ecomviews.developmenteffiency.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecomviews.developmenteffiency.ViewModels.DEViewModel
import com.example.ecomviews.R
import com.example.ecomviews.developmenteffiency.Adopter.UserAdapter
import com.example.ecomviews.developmenteffiency.DevEfficiencyActivity

class AllUsersFragment : Fragment() {

    private lateinit var userAdapter: UserAdapter
    private lateinit var viewModel: DEViewModel
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
        val view = inflater.inflate(R.layout.fragment_all_users, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.rvAllUsers)
        val loadingProgBar = view.findViewById<ProgressBar>(R.id.loadingProgBar)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        userAdapter = UserAdapter(emptyList()) { }
        recyclerView.adapter = userAdapter
        viewModel.getAllUsers()

        viewModel.allUsers.observe(viewLifecycleOwner) { users ->
            if (users != null) {
                loadingProgBar.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
                userAdapter = UserAdapter(users) { userId ->
                    activity.navigateTo(UserDetailFragment.newInstance(userId))
                }
                recyclerView.adapter = userAdapter
            } else {
                loadingProgBar.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
            }
        }
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = AllUsersFragment()
    }
}
