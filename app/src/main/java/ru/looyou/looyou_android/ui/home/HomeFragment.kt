package ru.looyou.looyou_android.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import ru.looyou.looyou_android.R
import ru.looyou.looyou_android.databinding.HomeFragmentBinding

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: HomeFragmentBinding
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.postViewPager.adapter = PostPagerAdapter(this)
        binding.postViewPager.isUserInputEnabled = false
        TabLayoutMediator(binding.tabLayout, binding.postViewPager) { tab, position ->
            resources.getStringArray(R.array.posts_items).also {
                tab.text = it[position]
            }
        }.attach()
    }
}