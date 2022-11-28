package com.zelyder.cbas.ast

import com.zelyder.cbas.parser.Token

class BinOperationNode(
    val operator: Token,
    val leftNode: ExpressionNode,
    val rightNode: ExpressionNode
) : ExpressionNode() {
}