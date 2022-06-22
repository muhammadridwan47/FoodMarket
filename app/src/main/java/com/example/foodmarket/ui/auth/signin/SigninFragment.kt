package com.example.foodmarket.ui.auth.signin

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.foodmarket.FoodMarket
import com.example.foodmarket.R
import com.example.foodmarket.databinding.FragmentSigninBinding
import com.example.foodmarket.model.response.login.LoginResponse
import com.example.foodmarket.ui.MainActivity
import com.example.foodmarket.ui.auth.AuthActivity
import com.google.gson.Gson

class SigninFragment : Fragment(), SigninContract.View  {

    private var _binding: FragmentSigninBinding? = null
    private val binding  get() = _binding!!
    lateinit var presenter: SigninPresenter

    // make loader screen
    var progressDialog : Dialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signin, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSigninBinding.bind(view)  //if the view is already inflated then we can just bind it to view binding.
        presenter = SigninPresenter(this)

        if (!FoodMarket.getApp().getToken().isNullOrEmpty()) {
            val home = Intent(activity,MainActivity::class.java)
            startActivity(home)
            activity?.finish()
        }

        binding?.btnSignup?.setOnClickListener {
            val signup = Intent(activity, AuthActivity::class.java)
            signup.putExtra("page_request", 2)
            startActivity(signup)
        }
        initInputDummy();
        initView();
        binding.btnSignin.setOnClickListener{

            var email = binding.etEmail.text.toString()
            var password = binding.etPassword.text.toString()
            if (email.isNullOrEmpty()) {
                binding.etEmail.error = "Please input your email...!"
                binding.etEmail.requestFocus()
            } else if (password.isNullOrEmpty()) {
                binding.etPassword.error = "Please input your password...!"
                binding.etPassword.requestFocus()
            } else {
                presenter.submitLogin(email,password)
            }
        }
    }

    //Note: Fragments outlive their views. Make sure you clean up any references to the binding class
    // instance in the fragment's onDestroyView() method.
    override fun onDestroyView() {
        Toast.makeText(activity, "On destroy", Toast.LENGTH_SHORT).show()
        super.onDestroyView()
        _binding = null
    }

    private fun initInputDummy() {
        binding.etEmail.setText("ridwan@gmail.com")
        binding.etPassword.setText("12345678")
    }

    override fun onLoginSuccess(loginResponse: LoginResponse) {
        FoodMarket.getApp().setToken(loginResponse.accessToken)
        val gson = Gson()
        val json = gson.toJson(loginResponse.user)
        FoodMarket.getApp().setUser(json)

        val home = Intent(activity,MainActivity::class.java)
        startActivity(home)
        activity?.finish()
    }

    override fun onLoginFailed(message: String) {
        Toast.makeText(activity,message,Toast.LENGTH_SHORT).show()
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


    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }


}