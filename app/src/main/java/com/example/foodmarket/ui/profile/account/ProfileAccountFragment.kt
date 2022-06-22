package com.example.foodmarket.ui.profile.account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodmarket.R
import com.example.foodmarket.databinding.FragmentProfileAccountBinding
import com.example.foodmarket.model.dummy.ProfileMenuModel
import com.example.foodmarket.ui.profile.ProfileMenuAdapter

class ProfileAccountFragment : Fragment(), ProfileMenuAdapter.ItemAdapterCallback {
    private var menuArrayList: ArrayList<ProfileMenuModel> = ArrayList()
    private var _binding: FragmentProfileAccountBinding? = null


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileAccountBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDataDummy()

        var adapter = ProfileMenuAdapter(menuArrayList,this)
        var layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(activity)
        _binding?.rcList?.layoutManager = layoutManager
        _binding?.rcList?.adapter = adapter
    }

    fun initDataDummy() {
        menuArrayList.add(ProfileMenuModel("Edit Profile"))
        menuArrayList.add(ProfileMenuModel("Home Address"))
        menuArrayList.add(ProfileMenuModel("Security"))
        menuArrayList.add(ProfileMenuModel("Payments"))
    }

    override fun onClick(v: View, data: ProfileMenuModel) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}