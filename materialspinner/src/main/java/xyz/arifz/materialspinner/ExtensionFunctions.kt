package xyz.arifz.materialspinner

import android.content.res.Resources.getSystem

object ExtensionFunctions {
    fun Int.dpToPx() = (this * getSystem().displayMetrics.density).toInt()
}