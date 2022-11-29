package com.zelyder.cbas.parser

import com.zelyder.cbas.ast.*
import com.zelyder.cbas.values.Value


class Parser(
    private val tokens: List<Token>,
    private var position: Int = 0
) {
    private fun match(vararg expected: TokenType): Token? {
        if (position < tokens.size) {
            val currentToken = tokens[position]
            if (expected.any { type -> type.name == currentToken.type.name }) {
                position++
                return currentToken
            }
        }
        return null
    }

    private fun require(vararg expected: TokenType): Token =
        match(*expected) ?: throw Exception("На позиции $position ожидается ${expected[0].name}")

    private fun parseStatement(): Statement {
        val operatorLog = match(TokenType.Log)
        if (operatorLog != null) {
            return PrintStatement(parseFormula())
        }
        val ifStatement = match(TokenType.If)
        if (ifStatement != null) {
            return ifElse()
        }
        return assignmentStatement()
    }

    private fun ifElse(): Statement {
        val condition = parseFormula()
        val ifStatement = parseStatement()
        val elseToken = match(TokenType.Else)
        val elseStatement = if (elseToken != null) parseStatement() else null
        return IfStatement(condition, ifStatement, elseStatement)
    }

    private fun assignmentStatement():Statement {
        val variableNode = parseVariableOrValue() as VariableNode
        val assignOperator = match(TokenType.Assign)
        if (assignOperator != null) {
            val rightFormulaNode = parseFormula()
            return AssignmentStatement(variableNode.variable.text, rightFormulaNode)
        }
        throw RuntimeException("После переменной ожидается оператор присвоения(=) на позиции $position")
    }

    private fun parseVariableOrValue(): ExpressionNode {
        val number = match(TokenType.Number)
        if (number != null) {
            return NumberNode(number)
        }
        val variable = match(TokenType.Variable)
        if (variable != null) {
            return VariableNode(variable)
        }
        val text = match(TokenType.Text)
        if (text != null) {
            return TextNode(text)
        }
        throw Exception("Ожидается переменная, число или текст на $position позиции")
    }

    private fun parseParentheses(): ExpressionNode {
        return if (match(TokenType.LPar) != null) {
            val node = parseFormula()
            require(TokenType.RPar)
            node
        } else {
            parseVariableOrValue()
        }
    }

    private fun parseFormula(): ExpressionNode {
        var leftNode = parseParentheses()
        var operator = match(
            TokenType.Divide,
            TokenType.Multiply,
            TokenType.Minus,
            TokenType.Plus,
            TokenType.Equals,
            TokenType.GreaterThan,
            TokenType.LessThan
        )
        while (operator != null) {
            val rightNode = parseParentheses()
            leftNode = BinOperationNode(operator, leftNode, rightNode)
            operator = match(
                TokenType.Divide,
                TokenType.Multiply,
                TokenType.Minus,
                TokenType.Plus,
                TokenType.Equals,
                TokenType.GreaterThan,
                TokenType.LessThan
            )
        }
        return leftNode
    }

    fun parseCode(): ExpressionNode {
        val root = StatementsNode()
        while (position < tokens.size) {
            val codeStringNode = parseStatement()
            require(TokenType.Semicolon)
            root.addNode(codeStringNode)
        }
        return root
    }

    fun run(node: ExpressionNode): Value = node.eval()
}