package com.example.foodmarket.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.foodmarket.R
import com.example.foodmarket.databinding.FragmentPaymentSuccessBinding

class PaymentSuccessFragment : Fragment() {

    private lateinit var binding: FragmentPaymentSuccessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPaymentSuccessBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as DetailActivity).toolbarDetail()

        binding?.btnOrderFood.setOnClickListener {
            requireActivity().finish()
        }
        binding?.btnMyOrder.setOnClickListener {
            requireActivity().finish()
        }
    }

}