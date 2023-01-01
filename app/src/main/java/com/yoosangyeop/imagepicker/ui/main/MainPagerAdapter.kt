package com.yoosangyeop.imagepicker.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.yoosangyeop.imagepicker.ui.search.SearchFragment

class MainPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment =
        when (position) {
            0 -> SearchFragment()
            else -> Fragment()
        }

    override fun getItemCount(): Int = 2
}