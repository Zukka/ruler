package com.zukka.ruler

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.zukka.ruler.databinding.ActivityMainBinding
import com.zukka.rulerlibrary.enums.ERulerUM

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var selectedBGColor: ColorObject
    private lateinit var selectedTextColor: ColorObject
    private lateinit var selectedLineColor: ColorObject

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        binding.viewModel = viewModel

        val horizontalRuler = binding.horizontalRuler
        horizontalRuler.setBackgroundColor(Color.parseColor(ColorList().basicColors()[0].hexHash))
        horizontalRuler.setTextColor(ColorList().basicColors()[1].hexHash)
        horizontalRuler.setLineColor(Color.parseColor(ColorList().basicColors()[1].hexHash))

        val verticalRuler = binding.verticalRuler
        verticalRuler.setBackgroundColor(Color.GREEN)
        verticalRuler.setTextColor("#FF001144")
        verticalRuler.setMeasureUnit(ERulerUM.Millimeter)
        verticalRuler.setLineColor(Color.DKGRAY)

        val canvasView = binding.canvasView

        canvasView.scaleFactorLiveData.observe(this) {
            verticalRuler.mScaleFactor = try {
                it
            } catch (ex: Exception) {
                1f
            }
            horizontalRuler.mScaleFactor = try {
                it
            } catch (ex: Exception) {
                1f
            }
        }

        binding.rulerUm.setOnCheckedChangeListener {_, isChecked ->
            viewModel.setUm(isChecked)
            }

        viewModel.umLiveData.observe(this) {
            when (it) {
                true -> {
                    horizontalRuler.setMeasureUnit(ERulerUM.Millimeter)
                    binding.rulerUm.text = getString(R.string.mm)
                }
                false -> {
                    horizontalRuler.setMeasureUnit(ERulerUM.Inch)
                    binding.rulerUm.text = getString(R.string.inch)
                }
            }
        }

        loadBackgroundColorSpinner()
        loadTextColorSpinner()
        loadLineColorSpinner()

        binding.textColorSpinner.setSelection(1)
        binding.lineColorSpinner.setSelection(1)
    }

    private fun loadLineColorSpinner() {
        selectedLineColor = ColorList().defaultColor
        binding.lineColorSpinner.apply {
            adapter = ColorSpinnerAdapter(applicationContext, ColorList().basicColors())
            setSelection(ColorList().colorPosition(selectedLineColor), false)
            onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                    selectedLineColor = ColorList().basicColors()[position]
                    binding.horizontalRuler.setLineColor(Color.parseColor(ColorList().basicColors()[position].hexHash))
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {}

            }
        }
    }

    private fun loadTextColorSpinner() {
        selectedTextColor = ColorList().defaultColor
        binding.textColorSpinner.apply {
            adapter = ColorSpinnerAdapter(applicationContext, ColorList().basicColors())
            setSelection(ColorList().colorPosition(selectedTextColor), false)
            onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                    selectedTextColor = ColorList().basicColors()[position]
                    binding.horizontalRuler.setTextColor(ColorList().basicColors()[position].hexHash)
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {}

            }
        }
    }
    private fun loadBackgroundColorSpinner() {
        selectedBGColor = ColorList().defaultColor
        binding.colorSpinner.apply {
            adapter = ColorSpinnerAdapter(applicationContext, ColorList().basicColors())
            setSelection(ColorList().colorPosition(selectedBGColor), false)
            onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                    selectedBGColor = ColorList().basicColors()[position]
                    binding.horizontalRuler.setBackgroundColor(Color.parseColor(ColorList().basicColors()[position].hexHash))
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {}

            }
        }
    }

}