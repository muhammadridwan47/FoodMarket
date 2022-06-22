package com.example.foodmarket.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.foodmarket.R
import com.example.foodmarket.databinding.FragmentDetailBinding
import com.example.foodmarket.model.response.home.Data
import com.example.foodmarket.utils.Helpers.formatPrice

class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    var bundle:Bundle?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // using for getData navigation with navigationFrament
        arguments?.let {
            DetailFragmentArgs.fromBundle(it).data.let {
                initView(it)
            }
        }

        binding.btnOrderNow.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_payment, bundle)
        }
    }

    private fun initView(data: Data?) {
//        Log.v("Check detail: ",data.toString())
        bundle = bundleOf("data" to data)

        data?.let {
            Glide.with(requireContext())
                .load(data?.picturePath)
                .into(binding?.ivPoster)
            binding?.tvTitle.text = data?.name
            binding?.tvDesc.text = data?.description
            binding?.tvIngredients.text = data?.ingredients

            binding?.tvTotal.formatPrice(data?.price.toString())
        }

    }

    override fun onStart() {
        super.onStart()
        (activity as DetailActivity).toolbarDetail()
    }

}