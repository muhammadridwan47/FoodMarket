package com.example.foodmarket.ui.home

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodmarket.FoodMarket
import com.example.foodmarket.R
import com.example.foodmarket.databinding.FragmentHomeBinding
import com.example.foodmarket.model.dummy.HomeModel
import com.example.foodmarket.model.response.home.Data
import com.example.foodmarket.model.response.home.HomeResponse
import com.example.foodmarket.model.response.login.User
import com.example.foodmarket.ui.detail.DetailActivity
import com.google.gson.Gson

class HomeFragment : Fragment(), HomeAdapter.ItemAdapterCallback, HomeContract.View {

    private var _binding: FragmentHomeBinding? = null

    private var newStateList: ArrayList<Data>? = ArrayList()
    private var popularList: ArrayList<Data>? = ArrayList()
    private var recomendedList: ArrayList<Data>? = ArrayList()
    var progressDialog : Dialog? = null

    private lateinit var presenter: HomePresenter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // initDataDummy()
        initView()
        presenter = HomePresenter(this)
        presenter.getHome()


    }
    // fun initDataDummy() {

        // foodList = ArrayList()
        // foodList.add(HomeModel("Cherry Healty","",5f))
        // foodList.add(HomeModel("Burger Tamayo","",4f))
       //  foodList.add(HomeModel("Bakwan Cihuy","",4.5f))
    // }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View, data: Data) {
        val detail = Intent(activity, DetailActivity::class.java).putExtra("data", data)
        startActivity(detail)
    }

    override fun onHomeSuccess(homeResponse: HomeResponse) {

        for (a in homeResponse.data.indices) {
            var items: List<String> = homeResponse.data[a].types?.split(",") ?: ArrayList()
            for (x in items.indices)
            {
                if (items[x].equals("new_food", true)) {
                    newStateList?.add(homeResponse.data[a])
                } else if (items[x].equals("recommended", true)) {
                    recomendedList?.add(homeResponse.data[a])
                } else if (items[x].equals("popular", true)) {
                    popularList?.add(homeResponse.data[a])
                }
            }
        }

        var adapter = HomeAdapter(homeResponse.data, this)
        var layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
        _binding?.rcList?.layoutManager = layoutManager
        _binding?.rcList?.adapter = adapter

        // Section Pager
        val sectionPagerAdapter = SectionPagerAdapter(
            childFragmentManager
        )
        // setDataResponse
        sectionPagerAdapter.setData(newStateList,popularList,recomendedList)
        //id from View in fragment_home layout
        _binding?.viewPager?.adapter = sectionPagerAdapter
        //select tabLayout from id fragment_home layout
        _binding?.tabLayout?.setupWithViewPager(_binding?.viewPager)
    }

    private fun initView() {

        progressDialog = Dialog(requireContext())
        val dialogLayout = layoutInflater.inflate(R.layout.dialog_loader,null)

        progressDialog?.let {
            it.setContentView(dialogLayout)
            it.setCancelable(false)
            it.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }

        var user = FoodMarket.getApp().getUser()
        var userResponse = Gson().fromJson(user, User::class.java)
        if (!userResponse.profilePhotoUrl.isNullOrEmpty()) {
            Glide.with(requireActivity())
                .load(userResponse.profilePhotoUrl)
                .into(_binding?.ivProfil!!)
        }
    }


    override fun onHomeFailed(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()
    }
}