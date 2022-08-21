package ru.looyou.looyou_android.ui.profile.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.looyou.looyou_android.databinding.ProfileDetailsFragmentBinding
import ru.looyou.looyou_android.databinding.ProfileFragmentBinding

class ProfileDetailsFragment : Fragment() {

    private lateinit var binding: ProfileDetailsFragmentBinding
    private val viewModel: ProfileDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = ProfileDetailsFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}