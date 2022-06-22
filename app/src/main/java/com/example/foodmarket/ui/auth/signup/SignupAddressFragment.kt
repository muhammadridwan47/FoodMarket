package com.example.foodmarket.ui.auth.signup

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.foodmarket.FoodMarket
import com.example.foodmarket.R
import com.example.foodmarket.databinding.FragmentSignupAddressBinding
import com.example.foodmarket.model.request.RegisterRequest
import com.example.foodmarket.model.response.login.LoginResponse
import com.example.foodmarket.ui.auth.AuthActivity
import com.google.gson.Gson

class SignupAddressFragment : Fragment(), SignupContract.View {
    private lateinit var data:RegisterRequest
    lateinit var presenter: SignupPresenter
    var progressDialog:Dialog? = null

    private var _binding: FragmentSignupAddressBinding? = null
    private val binding  get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup_address, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentSignupAddressBinding.bind(view)  //if the view is already inflated then we can just bind it to view binding.
        initDummy()
        initListener()
        initView()
        binding?.btnSignUpNow?.setOnClickListener {
            (activity as AuthActivity).toolbarSignUpSuccess()
           Navigation.findNavController(it)
               .navigate(R.id.action_signup_success)
        }
    }


    private fun initListener() {
        _binding?.btnSignUpNow?.setOnClickListener{
            var phone = _binding?.etPhoneNumber?.text.toString()
            var address = _binding?.etAddress?.text.toString()
            var houseNumber = _binding?.etHouseNumber?.text.toString()
            var city = _binding?.etCity?.text.toString()

            data.let {
                it.address = address
                it.city = city
                it.houseNumber = houseNumber
                it.phoneNumber = phone
            }

            if (phone.isNullOrEmpty()) {
                _binding?.etPhoneNumber?.error = "Please input your number!"
                _binding?.etPhoneNumber?.requestFocus()
            } else if (address.isNullOrEmpty()) {
                _binding?.etAddress?.error = "Please input your phone number!"
                _binding?.etAddress?.requestFocus()
            } else if (houseNumber.isNullOrEmpty()) {
                _binding?.etHouseNumber?.error = "Please input your house number!"
                _binding?.etHouseNumber?.requestFocus()
            } else if (city.isNullOrEmpty()) {
                _binding?.etCity?.error = "Please input your city!"
                _binding?.etCity?.requestFocus()
            } else {

//                presenter.submitRegister(data, it)
                presenter.submitPhotoRegister(data.filePath!!, it)
            }


        }
    }

    private fun initDummy() {
        _binding?.etPhoneNumber?.setText("05435343")
        _binding?.etAddress?.setText("05435343")
        _binding?.etCity?.setText("05435343")
        _binding?.etHouseNumber?.setText("05435343")
    }

    private fun initView() {
        progressDialog = Dialog(requireContext())
        val dialogLayout = layoutInflater.inflate(R.layout.dialog_loader,null)

        progressDialog?.let {
            it.setContentView(dialogLayout)
            it.setCancelable(false)
            it.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
    }

    //Note: Fragments outlive their views. Make sure you clean up any references to the binding class
    // instance in the fragment's onDestroyView() method.
    override fun onDestroyView() {
        Toast.makeText(activity, "On destroy", Toast.LENGTH_SHORT).show()
        super.onDestroyView()
        _binding = null
    }

    override fun onRegisterSuccess(loginResponse: LoginResponse, view: View) {
        FoodMarket.getApp().setToken(loginResponse.accessToken)

        val gson = Gson()
        val json = gson.toJson(loginResponse.user)
        FoodMarket.getApp().setUser(json)

        if(data.filePath == null) {
            Navigation.findNavController(view).navigate(R.id.action_signup_success)
            (activity as AuthActivity).toolbarSignUpSuccess()
        } else {
            presenter.submitPhotoRegister(data.filePath!!, view)
        }
    }

    override fun onRegisterPhotoSuccess(view: View) {
        Navigation.findNavController(view)
            .navigate(R.id.action_signup_success,null)

        (activity as AuthActivity).toolbarSignUpSuccess()
    }

    override fun onRegisterFailed(message: String) {
        Toast.makeText(activity,message,Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }
}