package ru.looyou.looyou_android.ui.profile

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.looyou.domain.db.sharedprefs.SharedPrefs
import ru.looyou.domain.looyou.profile.LooYouGetProfileUseCase
import ru.looyou.looyou_android.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val looYouGetProfileUseCase: LooYouGetProfileUseCase,
    private val sharedPrefs: SharedPrefs
) : BaseViewModel() {

    fun getProfile() {
        viewModelScope.launch(Dispatchers.IO + errorHandler) {
            looYouGetProfileUseCase.execute()
        }
    }

    fun logout() {
        sharedPrefs.logout()
    }
}