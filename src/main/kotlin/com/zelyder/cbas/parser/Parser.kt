package com.zelyder.cbas.parser

import com.zelyder.cbas.ast.*
import com.zelyder.cbas.utils.isNumber


class Parser(
    private val tokens: List<Token>,
    private var position: Int = 0,
    private val scope: MutableMap<String, Any> = mutableMapOf()
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


    private fun parseVariableOrNumber(): ExpressionNode {
        val number = match(TokenType.Number)
        if (number != null) {
            return NumberNode(number)
        }
        val variable = match(TokenType.Variable)
        if (variable != null) {
            return VariableNode(variable)
        }
        throw Exception("Ожидается переменная или число на $position позиции")
    }

    private fun parseVariableOrNumberOrText(): ExpressionNode {
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
            parseVariableOrNumber()
        }
    }

    private fun parseParenthesesWithText(): ExpressionNode {
        return if (match(TokenType.LPar) != null) {
            val node = parseFormulaWithText()
            require(TokenType.RPar)
            node
        } else {
            parseVariableOrNumberOrText()
        }
    }

    private fun parseFormula(): ExpressionNode {
        var leftNode = parseParentheses()
        var operator = match(TokenType.Divide, TokenType.Multiply, TokenType.Minus, TokenType.Plus)
        while (operator != null) {
            val rightNode = parseParentheses()
            leftNode = BinOperationNode(operator, leftNode, rightNode)
            operator = match(TokenType.Divide, TokenType.Multiply, TokenType.Minus, TokenType.Plus)
        }
        return leftNode
    }

    private fun parseFormulaWithText(): ExpressionNode {
        var leftNode = parseParenthesesWithText()
        var operator = match(TokenType.Divide, TokenType.Multiply, TokenType.Minus, TokenType.Plus)
        while (operator != null) {
            val rightNode = parseParenthesesWithText()
            leftNode = BinOperationNode(operator, leftNode, rightNode)
            operator = match(TokenType.Divide, TokenType.Multiply, TokenType.Minus, TokenType.Plus)
        }
        return leftNode
    }

    private fun parsePrint(): ExpressionNode {
        val operatorLog = match(TokenType.Log)
        if (operatorLog != null) {
            return UnaryOperationNode(operatorLog, parseFormulaWithText())
        }
        throw Exception("Ожидается унарный оператор ${TokenType.Log.name} на позиции $position")
    }

    private fun parseExpression(): ExpressionNode {
        if (match(TokenType.Variable) == null) {
            return parsePrint()
        }
        position--
        val variableNode = parseVariableOrNumber()
        val assignOperator = match(TokenType.Assign)
        if (assignOperator != null) {
            val rightFormulaNode = parseFormula()
            return BinOperationNode(assignOperator, variableNode, rightFormulaNode)
        }
        throw Exception("После переменной ожидается оператор присвоения(=) на позиции $position")
    }

    fun parseCode(): ExpressionNode {
        val root = StatementsNode()
        while (position < tokens.size) {
            val codeStringNode = parseExpression()
            require(TokenType.Semicolon)
            root.addNode(codeStringNode)
        }
        return root
    }

    private val logBuilder = StringBuilder()
    fun run(node: ExpressionNode): Any {
        if (node is NumberNode) {
            return node.number.text.toInt()
        }
        if (node is TextNode) {
            return node.text.text.substring(1, node.text.text.length - 1)
        }
        if (node is UnaryOperationNode) {
            when (node.operator.type) {
                is TokenType.Log -> {
                    val res = run(node.operand)
                    logBuilder.append(res)
                    logBuilder.append('\n')
                    return res
                }
                is TokenType.If -> {

                }
                else -> throw Exception("Оператор ${node.operator.type.name} не поддерживается")
            }
        }
        if (node is BinOperationNode) {
            when (node.operator.type) {
                is TokenType.Plus -> {
                    val left = run(node.leftNode)
                    val right = run(node.rightNode)
                    return if (left.toString().isNumber() && right.toString().isNumber()) {
                        left.toString().toInt() + right.toString().toInt()
                    } else {
                        left.toString() + right.toString()
                    }
                }
                is TokenType.Minus -> return run(node.leftNode).toString().toInt() - run(node.rightNode).toString()
                    .toInt()
                is TokenType.Multiply -> return run(node.leftNode).toString().toInt() * run(node.rightNode).toString()
                    .toInt()
                is TokenType.Divide -> return run(node.leftNode).toString().toInt() / run(node.rightNode).toString()
                    .toInt()
                is TokenType.Assign -> {
                    val result = run(node.rightNode)
                    val variableNode: VariableNode = node.leftNode as VariableNode
                    scope[variableNode.variable.text] = result
                    return result
                }
                else -> throw Exception("Оператор ${node.operator.type.name} не поддерживается")
            }
        }
        if (node is VariableNode) {
            if (scope.contains(node.variable.text) && scope[node.variable.text] != null) {
                return scope[node.variable.text]!!
            } else {
                throw Exception("Переменная с названием ${node.variable.text} не найдена")
            }
        }
        if (node is StatementsNode) {
            node.codeStrings.forEach { codeString ->
                run(codeString)
            }
            return logBuilder.toString()
        }
        throw Exception("Ошибка!")
    }
}