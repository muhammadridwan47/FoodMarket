package com.example.foodmarket.ui.home.newtaste

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodmarket.databinding.FragmentHomeNewTasteBinding
import com.example.foodmarket.model.dummy.HomeVerticalModel
import com.example.foodmarket.model.response.home.Data
import com.example.foodmarket.ui.detail.DetailActivity

class HomeNewTasteFragment : Fragment(), HomeNewStateAdapter.ItemAdapterCallback {
    // private  var foodList : ArrayList<HomeVerticalModel>  = ArrayList()
    private var newTasteList: ArrayList<Data>? = ArrayList()
    private lateinit var binding: FragmentHomeNewTasteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeNewTasteBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        initDataDummy()

        newTasteList = arguments?.getParcelableArrayList("data") // getData
        var adapter = HomeNewStateAdapter(newTasteList!!, this)
        var layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(activity)
        binding.rcList.layoutManager = layoutManager
        binding.rcList.adapter = adapter
    }
//
//    fun initDataDummy() {
//        foodList = ArrayList()
//        foodList.add(HomeVerticalModel("Cherry Healty","","10000",5f))
//        foodList.add(HomeVerticalModel("Burger Tamayo","","30000",4f))
//        foodList.add(HomeVerticalModel("Bakwan Cihuy","","30000",4.5f))
//        foodList.add(HomeVerticalModel("Bakwan Cihuy","","30000",4.5f))
//    }

    override fun onClick(v: View, data: Data) {
        val detail = Intent(activity, DetailActivity::class.java).putExtra("data", data)
        startActivity(detail)
    }

}
