package com.example.ecomviews.developmenteffiency.screens

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.ecomviews.developmenteffiency.ViewModels.DEViewModel
import com.example.ecomviews.R

class ProductPrevFragment : Fragment() {
    private lateinit var productImg: ImageView
    private lateinit var productTitle: TextView
    private lateinit var productPrice: TextView
    private lateinit var productRating: TextView
    private lateinit var productCounts: TextView
    private lateinit var productDesc: TextView
    private var productId: Long? = null
    private lateinit var viewModel: DEViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            productId = it.getLong(ARG_PRODUCT_ID)
            Log.d("iddd","a $productId")
        }
        viewModel = ViewModelProvider(requireActivity()).get(DEViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_product_prev, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        productImg = view.findViewById(R.id.product_image)
        productTitle = view.findViewById(R.id.product_title)
        productPrice = view.findViewById(R.id.product_price)
        productRating = view.findViewById(R.id.product_rating)
        productCounts = view.findViewById(R.id.product_count)
        productDesc = view.findViewById(R.id.product_description)
        viewModel.productList.observe(viewLifecycleOwner, { products ->
            products.find { it.id == productId }?.let {
            productImg.load(it.image)
            productTitle.text = it.title
            productPrice.text = it.price.toString()
            productRating.text = it.rating.rate.toString()
            productCounts.text = it.rating.count.toString()
            productDesc.text = it.description
        }
        })
    }
    companion object {
        private const val ARG_PRODUCT_ID = "product_id"
        @JvmStatic
        fun newInstance(productId: Long) = ProductPrevFragment().apply {
            arguments = Bundle().apply {
                putLong(ARG_PRODUCT_ID, productId)
            }
        }
    }
}