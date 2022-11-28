package com.zelyder.cbas.utils

fun String.isNumber(): Boolean{
    return this.all { char -> char.isDigit() }
}