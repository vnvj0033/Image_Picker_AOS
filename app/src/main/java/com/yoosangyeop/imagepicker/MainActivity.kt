package com.yoosangyeop.imagepicker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.yoosangyeop.imagepicker.databinding.ActivityMainBinding
import com.yoosangyeop.imagepicker.ui.main.MainPagerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindingView(binding)
    }

    private fun bindingView(binding: ActivityMainBinding) = with(binding) {
        val pagerAdapter = MainPagerAdapter(this@MainActivity)

        viewPager.adapter = pagerAdapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> TAB_NAME_SEARCH
                else -> TAB_NAME_GALLERY
            }
        }.attach()
    }
}

private const val TAB_NAME_SEARCH = "검색 결과"
private const val TAB_NAME_GALLERY = "내 보관함"