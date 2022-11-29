package com.zelyder.cbas.ast

import com.zelyder.cbas.parser.Context
import com.zelyder.cbas.parser.Token
import com.zelyder.cbas.parser.TokenType
import com.zelyder.cbas.values.BooleanValue
import com.zelyder.cbas.values.NumberValue
import com.zelyder.cbas.values.StringValue
import com.zelyder.cbas.values.Value

class BinOperationNode(
    val operator: Token,
    val leftNode: ExpressionNode,
    val rightNode: ExpressionNode
) : ExpressionNode {

    override fun eval(): Value {
        if (operator.type is TokenType.Assign) {
            val result = rightNode.eval()
            val variableNode: VariableNode = leftNode as VariableNode
            Context.scope[variableNode.variable.text] = result
            return result
        }

        val leftValue = leftNode.eval()
        val rightValue = rightNode.eval()
        when {
            leftValue is StringValue || rightValue is StringValue -> {
                return when (operator.type) {
                    is TokenType.Plus -> StringValue(leftValue.asString() + rightValue.asString())
                    is TokenType.LessThan -> BooleanValue(leftValue.asString() < rightValue.asString())
                    is TokenType.GreaterThan -> BooleanValue(leftValue.asString() > rightValue.asString())
                    is TokenType.Equals -> BooleanValue(leftValue.asString() == rightValue.asString())
                    else -> throw Exception("Оператор ${operator.type.name} не поддерживается")
                }
            }
            leftValue is BooleanValue || rightValue is BooleanValue -> {
                return when (operator.type) {
                    is TokenType.Plus -> BooleanValue(leftValue.asBoolean() || rightValue.asBoolean())
                    is TokenType.Multiply -> BooleanValue(leftValue.asBoolean() && rightValue.asBoolean())
                    is TokenType.And -> BooleanValue(leftValue.asBoolean() && rightValue.asBoolean())
                    is TokenType.Or -> BooleanValue(leftValue.asBoolean() || rightValue.asBoolean())
                    else -> throw Exception("Оператор ${operator.type.name} не поддерживается")
                }
            }
            else -> {
                return when (operator.type) {
                    is TokenType.Plus -> NumberValue(leftValue.asNumber() + rightValue.asNumber())
                    is TokenType.Minus -> NumberValue(leftValue.asNumber() - rightValue.asNumber())
                    is TokenType.Multiply -> NumberValue(leftValue.asNumber() * rightValue.asNumber())
                    is TokenType.Divide -> NumberValue(leftValue.asNumber() / rightValue.asNumber())
                    is TokenType.LessThan -> BooleanValue(leftValue.asNumber() < rightValue.asNumber())
                    is TokenType.GreaterThan -> BooleanValue(leftValue.asNumber() > rightValue.asNumber())
                    is TokenType.Equals -> BooleanValue(leftValue.asNumber() == rightValue.asNumber())
                    else -> throw Exception("Оператор ${operator.type.name} не поддерживается")
                }
            }
        }
    }
}