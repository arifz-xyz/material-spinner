package xyz.arifz.materialspinner

import android.content.Context
import android.content.res.Resources.getSystem
import android.util.TypedValue


object ExtensionFunctions {
    fun Int.dpToPx() = (this * getSystem().displayMetrics.density).toInt()

}

fun Int.spToPx(context: Context): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        this.toFloat(),
        context.resources.displayMetrics
    ).toInt()
}