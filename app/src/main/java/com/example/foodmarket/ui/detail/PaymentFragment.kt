package com.example.foodmarket.ui.detail

import android.app.Dialog
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
import com.example.foodmarket.FoodMarket
import com.example.foodmarket.R
import com.example.foodmarket.databinding.FragmentPaymentBinding
import com.example.foodmarket.model.response.checkout.CheckoutResponse
import com.example.foodmarket.model.response.home.Data
import com.example.foodmarket.model.response.login.User
import com.example.foodmarket.utils.Helpers.formatPrice
import com.google.gson.Gson

class PaymentFragment : Fragment(), PaymentContract.View {

    var progressDialog: Dialog? = null
    var total : Int = 0
    lateinit var presenter: PaymentPresenter

    private lateinit var binding: FragmentPaymentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaymentBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var data = arguments?.getParcelable<Data>("data")
        initView(data)
        initView()

        presenter = PaymentPresenter(this)
    }

    private fun initView() {
        progressDialog = Dialog(requireContext())
        val dialogLayout = layoutInflater.inflate(R.layout.dialog_loader, null)

        progressDialog?.let {
            it.setContentView(dialogLayout)
            it.setCancelable(false)
            it.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
    }


    private fun initView(data: Data?) {
        binding.tvTitle.text = data?.name
        binding.tvPrice.formatPrice(data?.price.toString())
        Glide.with(requireContext())
            .load(data?.picturePath)
            .into(binding.ivPoster)

        binding.tvNameItem.text = data?.name
        binding.tvHarga.formatPrice(data?.price.toString())

        if (!data?.price.toString().isNullOrEmpty()) {
            var totalTax = data?.price?.div(10)
            binding.tvTax.formatPrice(totalTax.toString())

            total = data?.price!! + totalTax!! + 50000
            binding.tvTotal.formatPrice(total.toString())
        } else {
            binding.tvPrice.text = "IDR. 0"
            binding.tvTax.text = "IDR. 0"
            binding. tvTotal.text = "IDR. 0"
            total = 0
        }

        var user = FoodMarket.getApp().getUser()
        var userResponse = Gson().fromJson(user,User::class.java)

        binding.tvName.text = userResponse?.name
        binding.tvPhone.text = userResponse?.phoneNumber
        binding.tvAddress.text = userResponse?.address
        binding.tvHouseNo.text = userResponse?.houseNumber
        binding.tvCity.text = userResponse?.city

        binding.btnCheckout.setOnClickListener {
            presenter.getCheckout(
                data?.id.toString(),
                userResponse?.id.toString(),
                "1",
                total.toString(),
                it
            )

        }
    }


    override fun onStart() {
        super.onStart()
        (activity as DetailActivity).toolbarPayment()
    }

    override fun onCheckoutSuccess(checkoutResponse: CheckoutResponse, view: View) {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(checkoutResponse.paymentUrl)
        startActivity(i)
        Navigation.findNavController(view).navigate(R.id.action_payment_success)
    }

    override fun onCheckoutFailed(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }
}