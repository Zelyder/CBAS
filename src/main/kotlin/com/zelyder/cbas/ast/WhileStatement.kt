package com.zelyder.cbas.ast

class WhileStatement(
    private val condition: ExpressionNode,
    private val statement: Statement
): Statement {
    override fun execute() {
        while (condition.eval().asBoolean()) {
            statement.execute()
        }
    }
}