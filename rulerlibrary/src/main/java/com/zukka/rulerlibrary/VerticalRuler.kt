package com.zukka.rulerlibrary

import android.graphics.Canvas

class VerticalRuler(rulerProperties: RulerProperties, private val mScaleFactor: Float) {

    private val rulerHeight = rulerProperties.rulerHeight
    private val markCmWidth = rulerProperties.getMarkCmWidthToPx()
    private val markHalfCmWidth = rulerProperties.getMarkHalfCmWidthToPx()
    private val markMmWidth = rulerProperties.getMarkMmWidthToPx()
    private val paint = rulerProperties.getMarksPaint()
    private val textPaint = rulerProperties.getTextPaint()

    internal fun drawInchVerticalMarksFourInchStep(
        index: Int,
        canvas: Canvas?,
        y: Float
    ) {
        if (index % 16 == 0) {
            drawRulerLine(canvas, y)
            drawRulerInchTextLine(canvas, index, y)
        }
    }

    internal fun drawInchVerticalMarksTwoInchStep(
        index: Int,
        canvas: Canvas?,
        y: Float,
    ) {
        if (index % 8 == 0) {
            drawRulerLine(canvas, y)
            drawRulerInchTextLine(canvas, index, y)
        }
    }

    internal fun drawMmVerticalMarksTwoCmStep(
        index: Int,
        canvas: Canvas?,
        y: Float,
    ) {
        if (index % 20 == 0) {
            drawRulerLine(canvas, y)
            drawRulerTextLine(canvas, index, y)
        }
    }

    internal fun drawMmVerticalMarksFiveCmStep(
        index: Int,
        canvas: Canvas?,
        y: Float,
    ) {
        if (index % 50 == 0) {
            drawRulerLine(canvas, y)
            drawRulerTextLine(canvas, index, y)
        }
    }

    internal fun drawInchVerticalMarks(
        i: Int,
        canvas: Canvas?,
        y: Float,
    ) {
        when {
            i % 4 == 0 -> {
                drawRulerLine(canvas, y)
                drawRulerInchTextLine(canvas, i, y)
            }
            i % 2 == 0 -> {
                drawRulerMMHalfLine(canvas, y)
                canvas?.drawText(
                    "1/2",
                    (rulerHeight - markHalfCmWidth - textPaint.textSize),
                    y + textPaint.textSize,
                    textPaint
                )
            }
            else -> {
                if (mScaleFactor >= 0.5f)
                    canvas?.drawLine(
                        rulerHeight - markMmWidth,
                        y,
                        rulerHeight,
                        y,
                        paint
                    )
            }
        }
    }

    private fun drawRulerInchTextLine(
        canvas: Canvas?,
        index: Int,
        y: Float
    ) {
        canvas?.drawText(
            "${index / 4}",
            (rulerHeight - markHalfCmWidth - textPaint.textSize),
            y + textPaint.textSize,
            textPaint
        )
    }

    internal fun drawMmVerticalMarks(
        i: Int,
        canvas: Canvas?,
        y: Float,
    ) {
        when {
            i % 10 == 0 -> {
                drawRulerLine(canvas, y)
                drawRulerTextLine(canvas, i, y)
            }
            i % 5 == 0 -> {
                drawRulerMMHalfLine(canvas, y)
            }
            else -> {
                canvas?.drawLine(
                    rulerHeight - markMmWidth,
                    y,
                    rulerHeight,
                    y,
                    paint
                )
            }
        }
    }

    private fun drawRulerMMHalfLine(canvas: Canvas?, y: Float) {
        canvas?.drawLine(
            rulerHeight - markHalfCmWidth,
            y,
            rulerHeight,
            y,
            paint
        )
    }

    private fun drawRulerTextLine(
        canvas: Canvas?,
        index: Int,
        y: Float
    ) {
        canvas?.drawText(
            "${index / 10}",
            (rulerHeight - markHalfCmWidth - textPaint.textSize),
            y + textPaint.textSize,
            textPaint
        )
    }

    private fun drawRulerLine(canvas: Canvas?, y: Float) {
        canvas?.drawLine(
            rulerHeight - markCmWidth,
            y,
            rulerHeight,
            y,
            paint
        )
    }

}