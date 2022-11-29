package com.zelyder.cbas.values

class NumberValue(val value: Int = 0): Value {
    override fun asNumber(): Int = value

    override fun asString(): String = value.toString()

    override fun asBoolean(): Boolean = value == 1

    override fun toString(): String = asString()

}