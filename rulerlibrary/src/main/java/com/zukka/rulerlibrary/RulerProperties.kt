package com.zukka.rulerlibrary

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.text.TextPaint
import com.zukka.rulerlibrary.enums.ERuleOrientation


class RulerProperties(private val context: Context) {

    private val defaultTextColor = "#FFFFFFFF"
    private val defaultMarksColor = "#FFFFFFFF"
    private var orientation = ERuleOrientation.Horizontal
    var rulerHeight = 35f
    var markCmWidth = 25f
    var markHalfCmWidth = 10f
    var markMmWidth = 6f

    private fun dpTOpx(dp: Float): Float {
        return dp * context.resources.displayMetrics.density
    }

    fun getMarkCmWidthToPx() = dpTOpx(markCmWidth)
    fun getMarkHalfCmWidthToPx() = dpTOpx(markHalfCmWidth)
    fun getMarkMmWidthToPx() = dpTOpx(markMmWidth)

    private val paintRuler = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        strokeWidth = 1f
        color = Color.TRANSPARENT
    }
    fun getPaintRuler() = paintRuler

    private val marksPaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        strokeWidth = 1f
        color = Color.parseColor(defaultMarksColor)
    }
    fun getMarksPaint() = marksPaint

    private val textPaint = TextPaint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        strokeWidth = 1f
        color = Color.parseColor(defaultTextColor)
    }
    fun getTextPaint() = textPaint

    fun setTextColor(textColor: String): Boolean {
        return try {
            textPaint.color = Color.parseColor(textColor)
            true
        } catch (ex: java.lang.Exception) {
            false
        }
    }

    fun getRulerOrientation() = orientation

    fun setOrientation(rulerOrientation: ERuleOrientation) {
        orientation = try {
            rulerOrientation
        } catch (ex: Exception) {
            ERuleOrientation.Horizontal
        }
    }

    fun setMarksPaint(color: Int) {
        marksPaint.color = color
    }
}