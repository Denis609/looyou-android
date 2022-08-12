package ru.looyou.looyou_android.ui.login

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.looyou.looyou_android.ui.login.authorization.AuthorizationFragment
import ru.looyou.looyou_android.ui.login.registration.RegistrationFragment

class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> {
            AuthorizationFragment()
        }
        else -> {
            RegistrationFragment()
        }
    }
}