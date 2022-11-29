package com.zelyder.cbas.ast

import com.zelyder.cbas.parser.Context

class PrintStatement(val expressionNode: ExpressionNode) : Statement {
    override fun execute() {
        val res = expressionNode.eval()
        Context.logBuilder.append(res)
        Context.logBuilder.append('\n')
        println(res)
    }

    override fun toString(): String {
        return expressionNode.eval().asString()
    }
}