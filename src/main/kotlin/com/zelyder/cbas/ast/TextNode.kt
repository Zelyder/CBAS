package com.zelyder.cbas.ast

import com.zelyder.cbas.parser.Token
import com.zelyder.cbas.values.StringValue
import com.zelyder.cbas.values.Value

class TextNode(val text: Token): ExpressionNode {
    override fun eval(): Value {
        return StringValue(text.text)
    }
}