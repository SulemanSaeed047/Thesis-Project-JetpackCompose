package com.example.ecomviews.developmenteffiency.screens

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecomviews.developmenteffiency.ViewModels.DEViewModel
import com.example.ecomviews.R
import com.example.ecomviews.developmenteffiency.Adopter.ProductAdapter
import com.example.ecomviews.developmenteffiency.DevEfficiencyActivity

class ProductHomeFragment : Fragment() {
    private var selectedCategory: TextView? = null
    private lateinit var viewModel: DEViewModel
    private lateinit var productAdapter: ProductAdapter
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
        val view =  inflater.inflate(R.layout.fragment_product_home, container, false)
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAllProducts("All")
        viewModel.getAllCategories()
        val categoriesContainer = view.findViewById<LinearLayout>(R.id.categoriesContainer)
        val productRecyclerView = view.findViewById<RecyclerView>(R.id.productRecyclerView)
        val onlineLayout = view.findViewById<ConstraintLayout>(R.id.online_layout)
        val offlineLayout = view.findViewById<ConstraintLayout>(R.id.offline_layout)
        val connetButton = view.findViewById<Button>(R.id.connect_button)
        val loadingProgBar = view.findViewById<ProgressBar>(R.id.loadingProgBar)
        viewModel.isOnline.observe(viewLifecycleOwner, {
           /* if (it) {
                onlineLayout.visibility = View.VISIBLE
                offlineLayout.visibility = View.GONE
            } else {
                onlineLayout.visibility = View.GONE
                offlineLayout.visibility = View.VISIBLE
            }*/
        })
        viewModel.isLoading.observe(viewLifecycleOwner, {
            if (it) {
                loadingProgBar.visibility = View.VISIBLE
                onlineLayout.visibility = View.GONE
            } else {
                loadingProgBar.visibility = View.GONE
                onlineLayout.visibility = View.VISIBLE
            }
        })
        connetButton.setOnClickListener{
            var i = Intent(Settings.ACTION_WIFI_SETTINGS)
            requireActivity().startActivity(i)
        }
        viewModel.getAllCategories()
        viewModel.getAllProducts("All")

        productAdapter = ProductAdapter(emptyList()) { productId ->
            activity.navigateTo(ProductPrevFragment.newInstance(productId))
        }
        productRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        productRecyclerView.adapter = productAdapter
        viewModel.productList.observe(viewLifecycleOwner, { products ->
            productAdapter.updateProductList(products)
        })
        viewModel.prodCategoriesList.observe(viewLifecycleOwner, { categories ->
            categoriesContainer.removeAllViews()
            categories.forEach { category ->
                val categoryView = TextView(requireContext()).apply {
                    text = category
                    setPadding(24, 16, 24, 16)
                    minHeight = 40
                    setBackgroundResource(R.drawable.category_background)
                    isSelected = category == "All"
                    if (isSelected) {
                        selectedCategory = this
                    }
                    setOnClickListener {
                        selectedCategory?.isSelected = false
                        isSelected = true
                        selectedCategory = this
                        viewModel.getAllProducts(category)
                    }
                }
                categoriesContainer.addView(categoryView)
                val layoutParams = categoryView.layoutParams as LinearLayout.LayoutParams
                layoutParams.setMargins(8, 8, 8, 8)
                categoryView.layoutParams = layoutParams
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance()= ProductHomeFragment()
    }
}
