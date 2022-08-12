package ru.looyou.looyou_android.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import ru.looyou.looyou_android.R
import ru.looyou.looyou_android.databinding.LoginFragmentBinding

class LoginFragment : Fragment() {

    private lateinit var binding: LoginFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LoginFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tabAdapterInit()
        tabSelectedInit()
    }

    private fun setTabBackground(tab1: Int, tab2: Int) {
        val tabStrip: ViewGroup = binding.tabBar.getChildAt(0) as ViewGroup
        setTabItem(tabStrip.getChildAt(0), tab1)
        setTabItem(tabStrip.getChildAt(1), tab2)
    }

    private fun setTabItem(tabView: View, tab: Int) {
        val paddingStart = tabView.paddingStart
        val paddingTop = tabView.paddingTop
        val paddingEnd = tabView.paddingEnd
        val paddingBottom = tabView.paddingBottom
        ViewCompat.setBackground(
            tabView,
            AppCompatResources.getDrawable(tabView.context, tab)
        )
        ViewCompat.setPaddingRelative(
            tabView,
            paddingStart,
            paddingTop,
            paddingEnd,
            paddingBottom
        )
    }

    private fun tabAdapterInit() {
        binding.viewPager.adapter = ViewPagerAdapter(this)
        TabLayoutMediator(binding.tabBar, binding.viewPager) { tab, position ->
            resources.getStringArray(R.array.login_items).also {
                tab.text = it[position]
            }
        }.attach()
        setTabBackground(R.drawable.left_blue_tab_layout, R.drawable.right_empty_tab_layout)
    }

    private fun tabSelectedInit() {
        binding.tabBar.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (binding.tabBar.selectedTabPosition == 0) {
                    setTabBackground(
                        R.drawable.left_blue_tab_layout,
                        R.drawable.right_empty_tab_layout
                    );
                } else {
                    setTabBackground(
                        R.drawable.left_empty_tab_layout,
                        R.drawable.right_orange_tab_layout
                    );
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }
}