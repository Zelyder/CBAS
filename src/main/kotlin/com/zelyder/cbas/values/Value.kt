package com.zelyder.cbas.values

interface Value {

    fun asNumber(): Int
    fun asString(): String
    fun asBoolean(): Boolean
}