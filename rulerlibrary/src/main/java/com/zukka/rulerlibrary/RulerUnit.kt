package com.zukka.rulerlibrary

import android.util.DisplayMetrics
import android.util.TypedValue


class RulerUnit {
    companion object {

        fun mmToPx(mm: Float, coefficient: Float, displayMetrics: DisplayMetrics): Float {
            return mm * TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_MM,
                coefficient,
                displayMetrics
            )
        }

        fun inchToPx(inch: Float, coefficient: Float, displayMetrics: DisplayMetrics): Float {
            return inch * TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_IN,
                coefficient,
                displayMetrics
            )
        }
    }
}