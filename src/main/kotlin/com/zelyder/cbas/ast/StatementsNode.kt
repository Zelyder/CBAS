package com.zelyder.cbas.ast

import com.zelyder.cbas.parser.Context
import com.zelyder.cbas.values.StringValue
import com.zelyder.cbas.values.Value

class StatementsNode: ExpressionNode {
    private val codeStrings: MutableList<ExpressionNode> = mutableListOf()

    fun addNode(node: ExpressionNode) {
        codeStrings.add(node)
    }

    override fun eval(): Value {
        codeStrings.forEach { codeString ->
            codeString.eval()
        }
        val res = Context.logBuilder.toString()
        Context.logBuilder.clear()
        return StringValue(res)
    }
}