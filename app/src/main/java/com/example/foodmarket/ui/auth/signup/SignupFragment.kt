package com.example.foodmarket.ui.auth.signup

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.foodmarket.R
import com.example.foodmarket.databinding.FragmentSignupBinding
import com.example.foodmarket.model.request.RegisterRequest
import com.example.foodmarket.ui.auth.AuthActivity
import com.github.dhaval2404.imagepicker.ImagePicker

class SignupFragment : Fragment() {
    var filePath:Uri ?= null

    private var _binding: FragmentSignupBinding? = null
    private val binding  get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentSignupBinding.bind(view)  //if the view is already inflated then we can just bind it to view binding.
        initListener()
        initDummy(_binding!!)
        binding?.btnContinue?.setOnClickListener {
            (activity as AuthActivity).toolbarSignUpAddress()


            Navigation.findNavController(it)
                .navigate(R.id.action_signup_address)
        }
    }


    private fun initDummy(bind: FragmentSignupBinding) {
        bind?.etFullName.setText("Muhammad Riduwan")
        bind?.etEmail.setText("muhammadridwan@gmail.com")
        bind?.etPassword.setText("12345678")
    }

    private fun initListener() {
        _binding?.ivProfile?.setOnClickListener {
            ImagePicker.with(this)
                .cameraOnly()
                .start()
        }

        _binding?.btnContinue?.setOnClickListener{

            var fullName = _binding?.etFullName?.text.toString()
            var email = _binding?.etEmail?.text.toString()
            var pass = _binding?.etPassword?.text.toString()

            if (fullName.isNullOrEmpty()) {
                _binding?.etFullName?.error = "Please input your name!"
                _binding?.etFullName?.requestFocus()
            } else if (email.isNullOrEmpty()) {
                _binding?.etEmail?.error = "Please input your email!"
                _binding?.etEmail?.requestFocus()
            } else if (pass.isNullOrEmpty()) {
                _binding?.etPassword?.error = "Please input your password!"
                _binding?.etPassword?.requestFocus()
            } else {

                var data = RegisterRequest(
                    fullName,
                    email,
                    pass,
                    pass,
                    "",
                    "",
                    "",
                    "",
                    filePath
                )

                var bundle = Bundle()

                bundle.putParcelable("data", data)
                Navigation.findNavController(it)
                    .navigate(R.id.action_signup_address,bundle)

                (activity as AuthActivity).toolbarSignUpAddress()
            }

        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            filePath = data?.data

                Glide.with(this)
                    .load(filePath)
                    .apply(RequestOptions.circleCropTransform())
                    .into(_binding?.ivProfile!!)

        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(context, ImagePicker.getError(data),Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(context, "Task Cancelled",Toast.LENGTH_LONG).show()
        }
    }

    //Note: Fragments outlive their views. Make sure you clean up any references to the binding class
    // instance in the fragment's onDestroyView() method.
    override fun onDestroyView() {
        Toast.makeText(activity, "On destroy", Toast.LENGTH_SHORT).show()
        super.onDestroyView()
        _binding = null
    }

}