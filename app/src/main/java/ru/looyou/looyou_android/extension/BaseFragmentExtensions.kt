package ru.looyou.looyou_android.extension

import androidx.fragment.app.Fragment

fun Fragment.onUnAuthorize() {
    requireActivity().changeAuthorize()
}