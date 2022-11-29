package com.zelyder.cbas.ast

import com.zelyder.cbas.parser.Context
import com.zelyder.cbas.values.StringValue

class ScanStatement(
    private val variable: String
): Statement {
    override fun execute() {
        Context.scope[variable] = StringValue(readlnOrNull() ?: "")
    }
}