package com.zelyder.cbas.parser

import com.zelyder.cbas.utils.ParseException

class Lexer(
    private val code: String,
    private var position: Int = 0,
    private val tokenList: MutableList<Token> = mutableListOf()
) {

    fun lexAnalysis(): List<Token> {
        while (position < code.length){
            if(code[position] == '"'){
                val builder = StringBuilder()
                val startPosition = position
                position++
                while (code[position] != '"'){
                    builder.append(code[position])
                    position++
                }
                tokenList.add(Token(TokenType.Text, builder.toString(), startPosition))
                position++
            }else {
                nextToken()
            }
        }
        return tokenList.filter { it.type != TokenType.Space }
    }

    private fun nextToken(): Boolean {
        for (tokenType in TokenTypes.tokenList) {
            val regex = Regex( "^${tokenType.pattern}")
            val result = regex.find(code.substring(position))
            if(result?.value != null) {
                val token = Token(tokenType, result.value, position)
                position += result.value.length
                tokenList.add(token)
                return true
            }
        }
        throw ParseException("На позиции $position обнаружена ошибка")
    }
}