package ca.suthakaran.assignment3.ui.common


import java.text.NumberFormat

fun currencyformat(value: Double): String =
    NumberFormat.getCurrencyInstance().format(value)