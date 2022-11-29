package com.zelyder.cbas.ast

import com.zelyder.cbas.parser.Context
import com.zelyder.cbas.parser.Token
import com.zelyder.cbas.parser.TokenType
import com.zelyder.cbas.utils.ParseException
import com.zelyder.cbas.values.Value

class UnaryOperationNode(
    val operator: Token,
    val operand: ExpressionNode
) : ExpressionNode{
    override fun eval(): Value {
        when (operator.type) {
            is TokenType.Log -> {
                val res = operand.eval()
                Context.logBuilder.append(res)
                Context.logBuilder.append('\n')
                return res
            }
            else -> throw ParseException("Оператор ${operator.type.name} не поддерживается")
        }
    }
}