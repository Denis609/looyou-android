package ru.looyou.looyou_android

import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import ru.looyou.looyou_android.databinding.ActivityMainBinding
import ru.looyou.looyou_android.ui.login.LoginFragmentDirections


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        navController.setGraph(R.navigation.nav_graph)
        changeAuthorize()

        KeyboardVisibilityEvent.registerEventListener(activity = this) {
            binding.navView.isVisible = if (viewModel.authorize()) !it else false
        }
    }

    fun changeAuthorize() {
        binding.navView.menu.clear()
        if (viewModel.authorize()) {
            binding.navView.inflateMenu(R.menu.bottom_nav_menu)
            binding.navView.setupWithNavController(navController)
            setMenuItem()
            navController.navigate(NavGraphDirections.actionLoginToHome())
        } else {
            navController.navigate(NavGraphDirections.actionLogin())
        }
        binding.navView.isVisible = viewModel.authorize()
        binding.fab.isVisible = binding.navView.isVisible
    }

    private fun setMenuItem() {
        binding.navView.itemIconTintList = null
        repeat(times = 2) {
            // set color text
            val spanString = SpannableString(binding.navView.menu.getItem(it).title.toString())
            spanString.setSpan(
                ForegroundColorSpan(ContextCompat.getColor(this, R.color.blue_color)),
                0,
                spanString.length,
                0
            )
            binding.navView.menu.getItem(it).title = spanString
            // set color icons
            binding.navView.menu.getItem(it).icon.setTint(
                ContextCompat.getColor(
                    this,
                    R.color.blue_color
                )
            )
        }
        binding.navView.menu.getItem(2).isEnabled = false
    }
}