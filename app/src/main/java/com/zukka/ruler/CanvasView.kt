package com.zukka.ruler

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import androidx.lifecycle.*
import com.zukka.ruler.CanvasScaleFactor.mScaleFactor
import kotlin.math.max
import kotlin.math.min

private object CanvasScaleFactor {
    var mScaleFactor = 1f
}

class CanvasView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    private val path = Path()
    private var mScaleDetector: ScaleGestureDetector? = null


    private val invalidPointerId = -1
    private var mActivePointerId = invalidPointerId
    private var mPosX = 0f
    private var mPosY = 0f

    private var mLastTouchX = 0f
    private var mLastTouchY = 0f

    private val paintBox = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        strokeWidth = 1f
        color = Color.WHITE
    }

    private var _scaleFactorLiveData = MutableLiveData<Float>()
    val scaleFactorLiveData: LiveData<Float>
        get() = _scaleFactorLiveData

    init {
        mScaleDetector = ScaleGestureDetector(context, ScaleListener(_scaleFactorLiveData))
    }

    private class ScaleListener(val scaleFactorLiveData: MutableLiveData<Float>) : ScaleGestureDetector.SimpleOnScaleGestureListener() {

        override fun onScale(detector: ScaleGestureDetector): Boolean {
            mScaleFactor *= detector.scaleFactor

            // Don't let the object get too small or too large.
            mScaleFactor = max(0.1f, min(mScaleFactor, 5.0f))

            scaleFactorLiveData.postValue(mScaleFactor)
            return true
        }
    }

    private fun centerScreenX() = (width / 2).toFloat()
    private fun centerScreenY() = (height / 2).toFloat()

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas!!.save()
        canvas.translate(mPosX, mPosY)
        canvas.scale(mScaleFactor, mScaleFactor, (width / 2).toFloat(), (height / 2).toFloat())
        path.reset()
        setBackgroundColor(Color.rgb(75, 78, 98))
        canvas.drawRect(centerScreenX() - 200, centerScreenY() - 100,
            centerScreenX() + 200,  centerScreenY() + 100, paintBox)

        canvas.restore()
        invalidate()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {

    }

    fun resetPosition() {
        mPosX = 0f
        mPosY = 0f
    }

    fun resetScale() {
        mScaleFactor = 1f
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent): Boolean {
        mScaleDetector!!.onTouchEvent(ev)
        val action = ev.action
        when (action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                val x = ev.x
                val y = ev.y
                mLastTouchX = x
                mLastTouchY = y
                mActivePointerId = ev.getPointerId(0)
            }
            MotionEvent.ACTION_MOVE -> {
                val pointerIndex = ev.findPointerIndex(mActivePointerId)
                val x = ev.getX(pointerIndex)
                val y = ev.getY(pointerIndex)

                // Only move if the ScaleGestureDetector isn't processing a gesture.
                if (!mScaleDetector!!.isInProgress) {
                    val dx: Float = x - mLastTouchX
                    val dy: Float = y - mLastTouchY
                    mPosX += dx
                    mPosY += dy
                    invalidate()
                }
                mLastTouchX = x
                mLastTouchY = y
            }
            MotionEvent.ACTION_UP -> {
                mActivePointerId = invalidPointerId
            }
            MotionEvent.ACTION_CANCEL -> {
                mActivePointerId = invalidPointerId
            }
            MotionEvent.ACTION_POINTER_UP -> {
                val pointerIndex = (ev.action and MotionEvent.ACTION_POINTER_INDEX_MASK
                        shr MotionEvent.ACTION_POINTER_INDEX_SHIFT)
                val pointerId = ev.getPointerId(pointerIndex)
                if (pointerId == mActivePointerId) {
                    val newPointerIndex = if (pointerIndex == 0) 1 else 0
                    mLastTouchX = ev.getX(newPointerIndex)
                    mLastTouchY = ev.getY(newPointerIndex)
                    mActivePointerId = ev.getPointerId(newPointerIndex)
                }
            }
        }
        return true
    }
}