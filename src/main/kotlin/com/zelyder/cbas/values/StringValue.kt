package com.zelyder.cbas.values

class StringValue(val value: String = ""): Value {
    override fun asNumber(): Int = value.toIntOrNull() ?: 0

    override fun asString(): String = value

    override fun asBoolean(): Boolean = value != ""

    override fun toString(): String = asString()
}