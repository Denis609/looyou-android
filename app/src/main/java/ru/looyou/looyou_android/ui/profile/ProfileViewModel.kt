package ru.looyou.looyou_android.ui.profile

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.looyou.domain.db.sharedprefs.SharedPrefs
import ru.looyou.domain.looyou.profile.LooYouGetProfileUseCase
import ru.looyou.domain.looyou.profile.model.ProfileDto
import ru.looyou.looyou_android.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val looYouGetProfileUseCase: LooYouGetProfileUseCase,
    private val sharedPrefs: SharedPrefs,
) : BaseViewModel() {

    private val _profile: MutableStateFlow<ProfileDto?> = MutableStateFlow(null)
    val profile: StateFlow<ProfileDto?> = _profile

    fun getProfile() {
        viewModelScope.launch(Dispatchers.IO + errorHandler) {
            _profile.value = looYouGetProfileUseCase.execute()
        }
    }

    fun logout() {
        sharedPrefs.logout()
    }
}