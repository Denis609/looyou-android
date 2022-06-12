package ru.looyou.looyou_android.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.looyou.looyou_android.R
import ru.looyou.looyou_android.api.extension.onUnAuthorize
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

        viewModel.getToken()
        viewModel.token.observe(viewLifecycleOwner) {
            binding.token.text = "Access token = $it"
        }

        binding.logout.setOnClickListener {
            viewModel.logout()
            onUnAuthorize()
        }
    }
}