package com.zelyder.cbas.ast

import com.zelyder.cbas.parser.Context

class AssignmentStatement(
    private val variable: String,
    private val expressionNode: ExpressionNode
): Statement {
    override fun execute() {
        Context.scope[variable] = expressionNode.eval()
    }
}