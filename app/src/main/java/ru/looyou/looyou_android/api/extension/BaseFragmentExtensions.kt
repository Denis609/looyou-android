package ru.looyou.looyou_android.api.extension

import androidx.fragment.app.Fragment

fun Fragment.onUnAuthorize() {
    requireActivity().changeAuthorize()
}