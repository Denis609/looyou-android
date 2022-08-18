package ru.looyou.looyou_android.extension

import android.app.Activity
import ru.looyou.looyou_android.MainActivity

fun Activity.changeAuthorize() {
    if (this is MainActivity) {
        this.changeAuthorize()
    }
}