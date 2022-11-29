package com.zelyder.cbas.ast

import com.zelyder.cbas.parser.Token
import com.zelyder.cbas.values.NumberValue
import com.zelyder.cbas.values.Value

class NumberNode(val number: Token): ExpressionNode {
    override fun eval(): Value {
        return NumberValue(number.text.toInt())
    }
}