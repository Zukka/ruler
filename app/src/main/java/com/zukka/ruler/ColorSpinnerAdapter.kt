package com.zukka.ruler

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class ColorSpinnerAdapter(context: Context, list: List<ColorObject>): ArrayAdapter<ColorObject>(context, 0 , list) {
    private var layoutInflater = LayoutInflater.from(context)

    @SuppressLint("ViewHolder", "InflateParams")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return view(layoutInflater.inflate(R.layout.color_spinner_bg, null, true), position)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        var cv = convertView
        if (cv == null)
            cv = layoutInflater.inflate(R.layout.color_spinner_item, parent, false)

        return view(cv!!, position)
    }

    private fun view(view: View, position: Int): View {

        val colorObject : ColorObject = getItem(position) ?: return view

        val colorNameItem = view.findViewById<TextView>(R.id.color_name)
        val colorHexItem = view.findViewById<TextView>(R.id.color_hex)
        val colorNameBg = view.findViewById<TextView>(R.id.color_name_bg)
        val colorBlob = view.findViewById<View>(R.id.color_blob)

        colorNameBg?.text = colorObject.name
        colorNameBg?.setTextColor(Color.parseColor(colorObject.contrastHexHash))

        colorNameItem?.text = colorObject.name
        colorHexItem?.text = colorObject.hex

        colorBlob?.background?.setTint(Color.parseColor(colorObject.hexHash))

        return view
    }
}