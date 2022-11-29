package com.zelyder.cbas.ast

class BlockStatement: Statement {

    private val statements = mutableListOf<Statement>()

    fun add(statement: Statement) {
        statements.add(statement)
    }

    override fun execute() {
        for (statement in statements) {
            statement.execute()
        }
    }
}