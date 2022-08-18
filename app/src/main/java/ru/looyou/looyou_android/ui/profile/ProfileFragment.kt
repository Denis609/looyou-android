package ru.looyou.looyou_android.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.shape.AbsoluteCornerSize
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.CornerSize
import ru.looyou.looyou_android.R
import ru.looyou.looyou_android.databinding.ProfileFragmentBinding
import ru.looyou.looyou_android.ui.home.PostAdapter
import ru.looyou.looyou_android.ui.home.search.PostDto
import ru.looyou.looyou_android.util.ImageLoader
import kotlin.math.abs
import kotlin.math.roundToInt

class ProfileFragment : Fragment() {

    private lateinit var binding: ProfileFragmentBinding
    private val viewModel: ProfileViewModel by viewModels()

    private var WIDTH_EXPAND_AVATAR_SIZE: Float = 0F

    private var EXPAND_AVATAR_SIZE: Float = 0F
    private var COLLAPSE_IMAGE_SIZE: Float = 0F
    private var horizontalToolbarAvatarMargin: Float = 0F


    private var avatarAnimateStartPointY: Float = 0F
    private var avatarCollapseAnimationChangeWeight: Float = 0F
    private var isCalculated = false
    private var verticalToolbarAvatarMargin = 0F

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



        ImageLoader.picasso(
            url = "https://s.ws.pho.to/76eeee/img/index/ai/source.jpg",
            binding.imgbAvatarWrap
        )
        EXPAND_AVATAR_SIZE =
            binding.imgbAvatarWrap.layoutParams.height.toFloat()//resources.getDimension(R.dimen.default_expanded_image_size)
        WIDTH_EXPAND_AVATAR_SIZE =
            binding.imgbAvatarWrap.layoutParams.width.toFloat()
        COLLAPSE_IMAGE_SIZE = resources.getDimension(R.dimen.default_collapsed_image_size)
        horizontalToolbarAvatarMargin = resources.getDimension(R.dimen.activity_margin)
        binding.apply {
            appBar.addOnOffsetChangedListener(
                AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
                    if (isCalculated.not()) {
                        avatarAnimateStartPointY =
                            abs((appBarLayout.height - (EXPAND_AVATAR_SIZE + horizontalToolbarAvatarMargin)) / appBarLayout.totalScrollRange)
                        avatarCollapseAnimationChangeWeight = 1 / (1 - avatarAnimateStartPointY)
                        verticalToolbarAvatarMargin = (animToolbar.height - COLLAPSE_IMAGE_SIZE) * 2
                        isCalculated = true
                    }
                    updateViews(abs(verticalOffset / appBarLayout.totalScrollRange.toFloat()))
                }
            )
        }

    }

    private var _height = 0

    //    private var cashCollapseState: Pair<Int, Int>? = null
    private fun updateViews(offset: Float) {


        /* Collapse avatar img*/
        binding.imgbAvatarWrap.apply {
            when {
                offset > avatarAnimateStartPointY -> {
                    val avatarCollapseAnimateOffset =
                        (offset - avatarAnimateStartPointY) * avatarCollapseAnimationChangeWeight
                    val avatarSize =
                        EXPAND_AVATAR_SIZE - (EXPAND_AVATAR_SIZE - COLLAPSE_IMAGE_SIZE) * avatarCollapseAnimateOffset


                    this.layoutParams.also {
                        it.height = avatarSize.roundToInt()
                        it.width = avatarSize.roundToInt()
                        if (_height != it.height) {
                            this.layoutParams = it
                        }
                        _height = it.height


                        this.shapeAppearanceModel = this.shapeAppearanceModel.toBuilder().setAllCorners(
                            CornerFamily.ROUNDED,
                            60F).build()
                    }




                    this.translationX =
                        -(((binding.appBar.width - horizontalToolbarAvatarMargin - avatarSize) / 2) * avatarCollapseAnimateOffset)
                    this.translationY =
                        ((binding.animToolbar.height - verticalToolbarAvatarMargin - avatarSize) / 2) * avatarCollapseAnimateOffset
                }
                else -> this.layoutParams.also {
                    if (it.height != EXPAND_AVATAR_SIZE.toInt()) {
                        it.height =  EXPAND_AVATAR_SIZE.toInt()
                        it.width = WIDTH_EXPAND_AVATAR_SIZE.toInt()
                        this.layoutParams = it
                    }
                    translationX = 0f
                }
            }
        }
    }
}