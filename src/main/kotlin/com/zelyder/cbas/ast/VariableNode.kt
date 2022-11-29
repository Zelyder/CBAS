package com.zelyder.cbas.ast

import com.zelyder.cbas.parser.Context
import com.zelyder.cbas.parser.Token
import com.zelyder.cbas.values.Value

class VariableNode(val variable: Token): ExpressionNode {
    override fun eval(): Value {
        if (Context.scope.contains(variable.text) && Context.scope[variable.text] != null) {
            return Context.scope[variable.text]!!
        } else {
            throw Exception("Переменная с названием ${variable.text} не найдена")
        }
    }
}