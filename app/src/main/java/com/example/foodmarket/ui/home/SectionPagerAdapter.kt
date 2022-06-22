package com.example.foodmarket.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.foodmarket.model.response.home.Data
//import com.example.foodmarket.model.response.home.Data
import com.example.foodmarket.ui.home.newtaste.HomeNewTasteFragment
import com.example.foodmarket.ui.home.popular.HomePopularFragment
import com.example.foodmarket.ui.home.recommended.HomeRecomendedFragment

class SectionPagerAdapter (fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    var newStateList: ArrayList<Data>? = ArrayList()
    var popularList: ArrayList<Data>? = ArrayList()
    var recomendedList: ArrayList<Data>? = ArrayList()

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "New Taste"
            1 -> "Popular"
            2 -> "Recommended"
            else -> ""
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getItem(position: Int): Fragment {
        var fragment : Fragment
        return when (position) {
            0 -> {
                fragment = HomeNewTasteFragment()
                val bundle = Bundle()
                bundle.putParcelableArrayList("data", newStateList)
                fragment.setArguments(bundle)
                return fragment
            }
            1 -> {
                fragment = HomePopularFragment()
                val bundle = Bundle()
                bundle.putParcelableArrayList("data", popularList)
                fragment.setArguments(bundle)
                return fragment
            }
            2 -> {
                fragment = HomeRecomendedFragment()
                val bundle = Bundle()
                bundle.putParcelableArrayList("data", recomendedList)
                fragment.setArguments(bundle)
                return fragment
            }
            else -> {
                fragment = HomeNewTasteFragment()
                val bundle = Bundle()
                bundle.putParcelableArrayList("data", newStateList)
                fragment.setArguments(bundle)
                return fragment
            }
        }
    }

    fun setData(newStateListParms: ArrayList<Data>?, popularListParms: ArrayList<Data>?, recomendedListParms: ArrayList<Data>?) {
        newStateList = newStateListParms
        popularList = popularListParms
        recomendedList = recomendedListParms
    }

}