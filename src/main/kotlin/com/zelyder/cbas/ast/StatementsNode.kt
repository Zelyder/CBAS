package com.zelyder.cbas.ast

class StatementsNode: ExpressionNode() {
    val codeStrings: MutableList<ExpressionNode> = mutableListOf()

    fun addNode(node: ExpressionNode) {
        codeStrings.add(node)
    }
}