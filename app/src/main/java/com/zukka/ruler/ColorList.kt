package com.zukka.ruler

import android.graphics.Color

class ColorList {
    private val blackHex = "000000"
    private val whiteHex = "FFFFFF"

    val defaultColor: ColorObject = basicColors()[0]

    fun colorPosition(colorObject: ColorObject): Int {
        for (i in basicColors().indices) {
            if (colorObject == basicColors()[i])
                return i
        }
        return 0
    }

    fun basicColors(): List<ColorObject> {
        return listOf(
            ColorObject("Teal_200", "FF03DAC5", blackHex),
            ColorObject("Black", blackHex, whiteHex),
            ColorObject("Teal_700", "FF018786", whiteHex),
            ColorObject("White", whiteHex, blackHex),
            ColorObject("Orange", "FFC107", blackHex),
            ColorObject("Cyan", "03A9F4", blackHex),
            ColorObject("Dark cyan", "009688", whiteHex),
            ColorObject("Light dark blue", "3F51B5", whiteHex),
            ColorObject("Light green", "CDDC39", blackHex),
            ColorObject("Violet", "BC30DC", whiteHex)
        )
    }
}