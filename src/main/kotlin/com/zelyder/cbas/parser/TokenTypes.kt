package com.zelyder.cbas.parser

object TokenTypes {
    val tokenList = listOf(
        TokenType.Log,
        TokenType.If,
        TokenType.Else,
        TokenType.Number,
        TokenType.Text,
        TokenType.Variable,
        TokenType.Semicolon,
        TokenType.Space,
        TokenType.Assign,
        TokenType.Multiply,
        TokenType.Divide,
        TokenType.Plus,
        TokenType.Minus,
        TokenType.LessThan,
        TokenType.GreaterThan,
        TokenType.LPar,
        TokenType.RPar
    )
}