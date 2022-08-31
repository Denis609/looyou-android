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
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import ru.looyou.domain.looyou.enums.ProfileDetailAttributeTypeEnum
import ru.looyou.domain.looyou.profile.model.ProfileDetailAttributeTypeDto
import ru.looyou.domain.looyou.profile.model.ProfileDto
import ru.looyou.looyou_android.Const
import ru.looyou.looyou_android.R
import ru.looyou.looyou_android.databinding.ProfileFragmentBinding
import ru.looyou.looyou_android.extension.onUnAuthorize
import ru.looyou.looyou_android.ui.home.PostAdapter
import ru.looyou.looyou_android.ui.home.PostPhotoPagerAdapter
import ru.looyou.looyou_android.ui.home.search.PostDto
import ru.looyou.looyou_android.util.AppBarStateChangeListener
import ru.looyou.looyou_android.util.ImageLoader
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

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


        binding.avatar.setOnClickListener {
            findNavController().navigate(ProfileFragmentDirections.actionProfileToProfileDetails())
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.profile.collect {
                it?.let {
                    setProfileData(it)
                }
            }
        }

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



    }

    private fun setProfileData(profile: ProfileDto) {
        binding.name.text = profile.name
        binding.nameTitle.text = profile.name

        val localDateTime: LocalDateTime = LocalDateTime.parse(profile.lastOnlineAt)
        binding.lastOnline.text = localDateTime.toString()
        binding.lastOnlineTitle.text = localDateTime.toString()

        val listItems = mutableListOf<String>()
        listItems.add("https://looyou.online/api/looyou/file/${profile.avatar?.photo?.id}")
        profile.detail?.photos?.map {
            if (profile.avatar?.photo?.id != it.photo.id) {
                listItems.add("https://looyou.online/api/looyou/file/${it.photo.id}")
            }
        }
        binding.avatar.adapter = AvatarPagerAdapter(listItems)
        if (listItems.size <= 1) {
            binding.tabLayout.isVisible = false
        } else {
            TabLayoutMediator(binding.tabLayout, binding.avatar) { tab, position ->

            }.attach()
        }

        ImageLoader.picasso(
            url = "https://looyou.online/api/looyou/file/${profile.avatar?.photo?.id}?width=100&height=100",
            binding.profilePhoto
        )

        binding.birthdayTextView.text = getString(R.string.birthday_s, profile.birthday)

        profile.detail?.attributes?.map {
            when (it.type.id) {
                ProfileDetailAttributeTypeEnum.Hobby -> {
                    binding.hobbyTextView.text = getString(R.string.hobby_s, it.value)
                    binding.hobbyGroup.isVisible = true
                }
                ProfileDetailAttributeTypeEnum.Book -> {
                    binding.booksTextView.text = getString(R.string.books_s, it.value)
                    binding.booksGroup.isVisible = true
                }
                ProfileDetailAttributeTypeEnum.About -> {
                    binding.aboutMeTextView.text = getString(R.string.about_me_s, it.value)
                    binding.aboutMeGroup.isVisible = true
                }
                ProfileDetailAttributeTypeEnum.Film -> {
                    binding.filmsTextView.text = getString(R.string.films_s, it.value)
                    binding.filmsGroup.isVisible = true
                }
                ProfileDetailAttributeTypeEnum.Game -> {
                    binding.gamesTextView.text = getString(R.string.games_s, it.value)
                    binding.gamesGroup.isVisible = true
                }
                ProfileDetailAttributeTypeEnum.Music -> {
                    binding.musicTextView.text = getString(R.string.music_s, it.value)
                    binding.musicGroup.isVisible = true
                }
                ProfileDetailAttributeTypeEnum.Education -> {
                    binding.studyTextView.text = getString(R.string.study_s, it.value)
                    binding.studyGroup.isVisible = true
                }
                else -> {

                }
            }
        }
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