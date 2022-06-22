package com.example.foodmarket.ui.auth.signup

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.foodmarket.R
import com.example.foodmarket.databinding.FragmentSignupSuccessBinding
import com.example.foodmarket.ui.MainActivity

class SignupSuccessFragment : Fragment() {

    private lateinit var _binding: FragmentSignupSuccessBinding
    private val binding  get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup_success, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSignupSuccessBinding.bind(view)  //if the view is already inflated then we can just bind it to view binding.

        _binding.btnFind.setOnClickListener {
            val home = Intent(activity,MainActivity::class.java)
            startActivity(home)
            activity?.finishAffinity()
        }
    }

}