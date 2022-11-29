package com.zelyder.cbas.values

class BooleanValue(val value: Boolean) : Value {
    override fun asNumber(): Int = if (value) 1 else 0

    override fun asString(): String = if (value) "true" else "false"

    override fun asBoolean(): Boolean = value

    override fun toString(): String = asString()
}