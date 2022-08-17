package ru.looyou.looyou_android.ui.home.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ru.looyou.looyou_android.databinding.SearchFragmentBinding
import ru.looyou.looyou_android.ui.home.PostAdapter

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private lateinit var binding: SearchFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SearchFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val items = mutableListOf<PostDto>()
        items.add(
            PostDto(
                postPhoto = "https://play-lh.googleusercontent.com/CWzqShf8hi-AhV9dUjzsqk2URzdIv8Vk2LmxBzf-Hc8T-oGkLVXe6pMpcXv36ofpvtc",
                profilePhoto = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRbZp5yHd8-uso4BYtP6i7s-wNa3CgymO2qrq8oK_upoyoFRUkzlVMJ-XHbWFlHVgJWyKA&usqp=CAU",
                name = "Dimassek",
                year = "23 года",
                addres = "Строителей 18",
                date = "00:36 12.12.2022"
            )
        )
        items.add(
            PostDto(
                postPhoto = "https://s.ws.pho.to/76eeee/img/index/ai/source.jpg",
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
    }
}

data class PostDto(
    val postPhoto: String?,
    val profilePhoto: String?,
    val name: String,
    val year: String,
    val addres: String,
    val date: String
)