package com.zelyder.cbas.ast

class IfStatement(
    private val expressionNode: ExpressionNode,
    private val ifStatement: Statement,
    private val elseStatement: Statement?
): Statement {
    override fun execute() {
        if (expressionNode.eval().asBoolean()) ifStatement.execute() else elseStatement?.execute()
    }
}