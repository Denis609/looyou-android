package ru.looyou.looyou_android.ui.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.looyou.looyou_android.ui.home.search.SearchFragment
import ru.looyou.looyou_android.ui.posts.PostFragment

class PostPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> {
            SearchFragment()
        }
        1 -> {
            PostFragment()
        }
        else -> {
            PostFragment()
        }
    }
}