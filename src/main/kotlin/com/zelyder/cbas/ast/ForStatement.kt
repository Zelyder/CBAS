package com.zelyder.cbas.ast

class ForStatement(
    private val initialization: Statement,
    private val termination: ExpressionNode,
    private val increment: Statement,
    private val statement: Statement
): Statement {
    override fun execute() {
        initialization.execute()
        while (termination.eval().asBoolean()) {
            statement.execute()
            increment.execute()
        }
    }
}