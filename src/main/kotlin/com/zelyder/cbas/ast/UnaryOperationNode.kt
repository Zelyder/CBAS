package com.zelyder.cbas.ast

import com.zelyder.cbas.parser.Token

class UnaryOperationNode(
    val operator: Token,
    val operand: ExpressionNode
) : ExpressionNode(){
}