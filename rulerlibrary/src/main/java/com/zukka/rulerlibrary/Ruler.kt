package com.zukka.rulerlibrary

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import com.zukka.rulerlibrary.enums.ERuleOrientation
import com.zukka.rulerlibrary.enums.ERulerUM
import kotlin.properties.Delegates


inline fun <reified T : Enum<T>> TypedArray.getEnum(index: Int, default: T) =
    getInt(index, -1).let {
        if (it >= 0) enumValues<T>()[it] else default
    }

class Ruler(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    private var isInch by Delegates.notNull<Boolean>()
    var mScaleFactor = 1f
    private val rulerProperties = RulerProperties(context)


    init {
        if (attrs != null) {
            val attribute = context.obtainStyledAttributes(
                attrs,
                R.styleable.Ruler, 0, 0
            )
            rulerProperties.rulerHeight = attribute.getFloat(R.styleable.Ruler_rulerHeight, 40f)
            rulerProperties.markCmWidth = attribute.getFloat(R.styleable.Ruler_markCmWidth, 25f)
            rulerProperties.markHalfCmWidth = attribute.getFloat(R.styleable.Ruler_markHalfCmWidth, 10f)
            rulerProperties.markMmWidth = attribute.getFloat(R.styleable.Ruler_markMmWidth, 6f)
            isInch =
                attribute.getEnum(R.styleable.Ruler_measureUnit, ERulerUM.Inch) == ERulerUM.Inch
            rulerProperties.setTextColor(
                attribute.getString(R.styleable.Ruler_textColor).toString()
            )
            rulerProperties.setOrientation(
                attribute.getEnum(
                    R.styleable.Ruler_rulerOrientation,
                    ERuleOrientation.Horizontal
                )
            )
            rulerProperties.setMarksPaint(
                attribute.getColor(
                    R.styleable.Ruler_lineColor,
                    Color.BLACK
                )
            )
            attribute.recycle()
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas!!.save()

        when (rulerProperties.getRulerOrientation()) {
            ERuleOrientation.Horizontal -> drawHorizontalMarks(canvas, rulerProperties)
            ERuleOrientation.Vertical -> drawVerticalMarks(canvas, rulerProperties)
        }

        canvas.restore()
        invalidate()
    }

    private fun drawVerticalMarks(canvas: Canvas?, rulerProperties: RulerProperties) {
        val verticalRuler = VerticalRuler(rulerProperties, mScaleFactor)
        var oneUnitInPx = RulerUnit.mmToPx(1f, mScaleFactor, resources.displayMetrics)
        if (isInch)
            oneUnitInPx = RulerUnit.inchToPx(0.254f, mScaleFactor, resources.displayMetrics)
        canvas?.drawRect(
            0f,
            0f,
            rulerProperties.rulerHeight,
            height.toFloat(),
            rulerProperties.getPaintRuler()
        )
        for (index in 0..10000) {
            val y = oneUnitInPx * index
            if (isInch) {
                when (mScaleFactor) {
                    in 0f..0.25f -> verticalRuler.drawInchVerticalMarksFourInchStep(
                        index,
                        canvas,
                        y
                    )
                    in 0.251f..0.5f -> verticalRuler.drawInchVerticalMarksTwoInchStep(
                        index,
                        canvas,
                        y
                    )
                    else -> verticalRuler.drawInchVerticalMarks(index, canvas, y)
                }
            } else {
                when (mScaleFactor) {
                    in 0f..0.25f -> verticalRuler.drawMmVerticalMarksFiveCmStep(
                        index,
                        canvas,
                        y
                    )
                    in 0.251f..0.5f -> verticalRuler.drawMmVerticalMarksTwoCmStep(
                        index,
                        canvas,
                        y
                    )
                    else -> verticalRuler.drawMmVerticalMarks(index, canvas, y)
                }
            }
            if (y >= height)
                break
        }
    }

    private fun drawHorizontalMarks(canvas: Canvas?, rulerProperties: RulerProperties) {
        val horizontalRuler = HorizontalRuler(rulerProperties, mScaleFactor)
        var oneUnitInPx = RulerUnit.mmToPx(1f, mScaleFactor, resources.displayMetrics)
        if (isInch)
            oneUnitInPx = RulerUnit.inchToPx(0.254f, mScaleFactor, resources.displayMetrics)

        canvas?.drawRect(
            0f,
            0f,
            width.toFloat(),
            rulerProperties.rulerHeight,
            rulerProperties.getPaintRuler()
        )
        for (i in 0..10000) {
            val x = oneUnitInPx * i
            if (isInch) {
                when (mScaleFactor) {
                    in 0f..0.25f -> horizontalRuler.drawInchHorizontalMarksFourInchStep(
                        i,
                        canvas,
                        x
                    )
                    in 0.251f..0.5f -> horizontalRuler.drawInchHorizontalMarksTwoInchStep(
                        i,
                        canvas,
                        x
                    )
                    else -> horizontalRuler.drawInchHorizontalMarks(i, canvas, x)
                }
            } else {
                when (mScaleFactor) {
                    in 0f..0.25f -> horizontalRuler.drawMmHorizontalMarksFiveCmStep(i, canvas, x)
                    in 0.251f..0.5f -> horizontalRuler.drawMmHorizontalMarksTwoCmStep(
                        i,
                        canvas,
                        x
                    )
                    else -> horizontalRuler.drawMmHorizontalMarks(i, canvas, x)
                }
            }
            if (x >= width)
                break
        }
    }

    fun setTextColor(textColorString: String) {
        val success = rulerProperties.setTextColor(textColorString)
        if (!success)
            Toast.makeText(context, R.string.update_color_failed, Toast.LENGTH_SHORT).show()
    }

    fun setMeasureUnit(eRulerUM: ERulerUM) {
        isInch = eRulerUM == ERulerUM.Inch
    }

    fun setLineColor(color: Int) {
        rulerProperties.setMarksPaint(color)
    }

}