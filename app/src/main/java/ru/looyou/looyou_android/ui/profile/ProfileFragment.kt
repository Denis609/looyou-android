package ru.looyou.looyou_android.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout
import dagger.hilt.android.AndroidEntryPoint
import ru.looyou.looyou_android.databinding.ProfileFragmentBinding
import ru.looyou.looyou_android.extension.onUnAuthorize
import ru.looyou.looyou_android.ui.home.PostAdapter
import ru.looyou.looyou_android.ui.home.search.PostDto
import ru.looyou.looyou_android.util.AppBarStateChangeListener
import ru.looyou.looyou_android.util.ImageLoader

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var binding: ProfileFragmentBinding
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = ProfileFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        changedAppBarInit()


        binding.imgbAvatarWrap.setOnClickListener {
            findNavController().navigate(ProfileFragmentDirections.actionProfileToProfileDetails())
        }



        ImageLoader.picasso(
            url = "https://play-lh.googleusercontent.com/CWzqShf8hi-AhV9dUjzsqk2URzdIv8Vk2LmxBzf-Hc8T-oGkLVXe6pMpcXv36ofpvtc",
            binding.profilePhoto
        )

        val items = mutableListOf<PostDto>()
        items.add(
            PostDto(
                postPhoto = "https://s.ws.pho.to/76eeee/img/index/ai/source.jpg",
                profilePhoto = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRbZp5yHd8-uso4BYtP6i7s-wNa3CgymO2qrq8oK_upoyoFRUkzlVMJ-XHbWFlHVgJWyKA&usqp=CAU",
                name = "Dimassek",
                year = "23 года",
                addres = "Строителей 18",
                date = "00:36 12.12.2022"
            )
        )
        items.add(
            PostDto(
                postPhoto = "https://play-lh.googleusercontent.com/CWzqShf8hi-AhV9dUjzsqk2URzdIv8Vk2LmxBzf-Hc8T-oGkLVXe6pMpcXv36ofpvtc",
                profilePhoto = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRbZp5yHd8-uso4BYtP6i7s-wNa3CgymO2qrq8oK_upoyoFRUkzlVMJ-XHbWFlHVgJWyKA&usqp=CAU",
                name = "Sanchek",
                year = "22 года",
                addres = "Хуй знает",
                date = "02:24 12.12.2022"
            )
        )
        items.add(
            PostDto(
                postPhoto = "https://imgv3.fotor.com/images/homepage-feature-card/Fotor-AI-photo-enhancement-tool-ru.jpg",
                profilePhoto = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRbZp5yHd8-uso4BYtP6i7s-wNa3CgymO2qrq8oK_upoyoFRUkzlVMJ-XHbWFlHVgJWyKA&usqp=CAU",
                name = "Densek",
                year = "22 года",
                addres = "Советской армии 153",
                date = "00:34 12.12.2022"
            )
        )
        binding.items.layoutManager = LinearLayoutManager(requireContext())
        binding.items.adapter = PostAdapter(items)

        viewModel.getProfile()


        ImageLoader.picasso(
            url = "https://play-lh.googleusercontent.com/CWzqShf8hi-AhV9dUjzsqk2URzdIv8Vk2LmxBzf-Hc8T-oGkLVXe6pMpcXv36ofpvtc",
            binding.imgbAvatarWrap
        )
    }

    private fun changedAppBarInit() {
        binding.app.addOnOffsetChangedListener(object : AppBarStateChangeListener() {
            override fun onStateChanged(appBarLayout: AppBarLayout?, state: State?) {
                if (state === State.EXPANDED) {
                    // Развернутое состояние
                    binding.nameAndOnline.isVisible = false
                    binding.profilePhoto.isVisible = false
                    binding.nameLastOnlineTitle.isVisible = true
                } else if (state === State.COLLAPSED) {
                    // Сложенное состояние
                    binding.nameAndOnline.isVisible = true
                    binding.profilePhoto.isVisible = true
                    binding.nameLastOnlineTitle.isVisible = false
                } else {
                    // Промежуточное состояние
                    binding.nameAndOnline.isVisible = false
                    binding.profilePhoto.isVisible = false
                    binding.nameLastOnlineTitle.isVisible = true
                }
            }
        })
    }
}