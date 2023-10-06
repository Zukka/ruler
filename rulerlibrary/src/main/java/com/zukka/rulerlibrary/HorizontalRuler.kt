package com.zukka.rulerlibrary

import android.graphics.Canvas

class HorizontalRuler(rulerProperties: RulerProperties, private val mScaleFactor: Float) {

    private val rulerHeight = rulerProperties.rulerHeight
    private val markCmWidth = rulerProperties.getMarkCmWidthToPx()
    private val markHalfCmWidth = rulerProperties.getMarkHalfCmWidthToPx()
    private val markMmWidth = rulerProperties.getMarkMmWidthToPx()
    private val paint = rulerProperties.getMarksPaint()
    private val textPaint = rulerProperties.getTextPaint()

    internal fun drawMmHorizontalMarksFiveCmStep(
        index: Int,
        canvas: Canvas?,
        x: Float
    ) {
        if (index % 50 == 0) {
            drawRulerLine(canvas, x)
            drawRulerText(canvas, index, x)
        }
    }

    internal fun drawMmHorizontalMarksTwoCmStep(
        i: Int,
        canvas: Canvas?,
        x: Float
    ) {
        if (i % 20 == 0) {
            drawRulerLine(canvas, x)
            drawRulerText(canvas, i, x)
        }
    }

    internal fun drawMmHorizontalMarks(
        i: Int,
        canvas: Canvas?,
        x: Float
    ) {
        when {
            i % 10 == 0 -> {
                drawRulerLine(canvas, x)
                drawRulerText(canvas, i, x)
            }
            i % 5 == 0 -> {
                drawHalfLine(canvas, x)
            }
            else -> {
                canvas?.drawLine(
                    x,
                    rulerHeight,
                    x,
                    rulerHeight - markMmWidth,
                    paint
                )
            }
        }
    }

    private fun drawRulerText(canvas: Canvas?, i: Int, x: Float) {
        canvas?.drawText(
            "${i / 10}",
            x + 4,
            (rulerHeight - markHalfCmWidth - 2),
            textPaint
        )
    }


    internal fun drawInchHorizontalMarksFourInchStep(
        i: Int,
        canvas: Canvas?,
        x: Float
    ) {
        if (i % 16 == 0) {
            drawRulerLine(canvas, x)
            drawMarkInchText(canvas, i, x)
        }
    }

    internal fun drawInchHorizontalMarksTwoInchStep(
        i: Int,
        canvas: Canvas?,
        x: Float
    ) {
        if (i % 8 == 0) {
            drawRulerLine(canvas, x)
            drawMarkInchText(canvas, i, x)
        }
    }

    internal fun drawInchHorizontalMarks(
        i: Int,
        canvas: Canvas?,
        x: Float
    ) {
        when {
            i % 4 == 0 -> {
                drawRulerLine(canvas, x)
                drawMarkInchText(canvas, i, x)
            }
            i % 2 == 0 -> {
                drawHalfLine(canvas, x)
                canvas?.drawText(
                    "1/2",
                    x - textPaint.textSize,
                    (rulerHeight - markHalfCmWidth - textPaint.textSize / 3),
                    textPaint
                )
            }
            else -> {
                if (mScaleFactor >= 0.5f)
                    canvas?.drawLine(
                        x,
                        rulerHeight,
                        x,
                        rulerHeight - markMmWidth,
                        paint
                    )
            }
        }
    }

    private fun drawMarkInchText(canvas: Canvas?, i: Int, x: Float) {
        canvas?.drawText(
            "${i / 4}",
            x + 4,
            (rulerHeight - markHalfCmWidth - 2),
            textPaint
        )
    }

    private fun drawHalfLine(canvas: Canvas?, x: Float) {
        canvas?.drawLine(
            x,
            rulerHeight,
            x,
            rulerHeight - markHalfCmWidth,
            paint
        )
    }

    private fun drawRulerLine(canvas: Canvas?, x: Float) {
        canvas?.drawLine(
            x,
            rulerHeight,
            x,
            rulerHeight - markCmWidth,
            paint
        )
    }
}