package com.zelyder.cbas.ast

import com.zelyder.cbas.values.Value

interface ExpressionNode {
    fun eval(): Value
}