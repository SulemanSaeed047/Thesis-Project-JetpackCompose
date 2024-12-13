package com.example.ecomviews.developmenteffiency.screens

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecomviews.developmenteffiency.ViewModels.DEViewModel
import com.example.ecomviews.R
import com.example.ecomviews.developmenteffiency.Adopter.MainAdapter
import com.example.ecomviews.developmenteffiency.DevEfficiencyActivity

class MainFragment : Fragment() {

    private lateinit var viewModel: DEViewModel
    private val data = listOf(
        "Products" to R.drawable.products,
        "Users" to R.drawable.users,
        "Theme" to R.drawable.theme,
        "Setting" to R.drawable.settings
    )
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
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.addItemDecoration(GridSpacingItemDecoration(2, 16, true))
        recyclerView.adapter = MainAdapter(data) { route ->
            when(route){
                data[0].first->{ activity.navigateTo(ProductHomeFragment.newInstance())}
                data[1].first->{ activity.navigateTo(AllUsersFragment.newInstance())}
                data[2].first->{ activity.navigateTo(ThemeFragment.newInstance())}
                data[3].first->{ activity.navigateTo(SettingFragment.newInstance())}
            }
        }
        return view
    }
    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }

    class GridSpacingItemDecoration(private val spanCount: Int, private val spacing: Int, private val includeEdge: Boolean) : RecyclerView.ItemDecoration() {

        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            val position = parent.getChildAdapterPosition(view)
            val column = position % spanCount

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount
                outRect.right = (column + 1) * spacing / spanCount

                if (position < spanCount) {
                    outRect.top = spacing
                }
                outRect.bottom = spacing
            } else {
                outRect.left = column * spacing / spanCount
                outRect.right = spacing - (column + 1) * spacing / spanCount
                if (position >= spanCount) {
                    outRect.top = spacing
                }
            }
        }
    }

}
