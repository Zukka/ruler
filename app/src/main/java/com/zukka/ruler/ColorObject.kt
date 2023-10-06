package com.zukka.ruler

class ColorObject(var name: String, var hex: String, var contrastHex: String) {
    val hexHash = "#$hex"
    val contrastHexHash = "#$contrastHex"

}