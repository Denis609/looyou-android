package ru.looyou.looyou_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.looyou.looyou_android.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        changeAuthorize()

    }

    fun changeAuthorize() {
        binding.navView.menu.clear()
        if (viewModel.authorize()) {
            navController.setGraph(R.navigation.authorize_nav_graph)
            binding.navView.inflateMenu(R.menu.bottom_nav_menu)
            binding.navView.setupWithNavController(navController)
        } else {
            navController.setGraph(R.navigation.unauthorize_nav_graph)
        }
        binding.navView.isVisible = viewModel.authorize()
    }
}