package com.zelyder.cbas.parser

object TokenTypes {
    val tokenList = listOf(
        TokenType.Log,
        TokenType.Scan,
        TokenType.If,
        TokenType.Else,
        TokenType.For,
        TokenType.While,
        TokenType.Number,
        TokenType.Text,
        TokenType.Variable,
        TokenType.Semicolon,
        TokenType.Space,
        TokenType.Equals,
        TokenType.Assign,
        TokenType.Multiply,
        TokenType.Divide,
        TokenType.Plus,
        TokenType.Minus,
        TokenType.And,
        TokenType.Or,
        TokenType.NotEquals,
        TokenType.GreaterThanOrEquals,
        TokenType.LessThanOrEquals,
        TokenType.LessThan,
        TokenType.GreaterThan,
        TokenType.LPar,
        TokenType.RPar,
        TokenType.LBrace,
        TokenType.RBrace,
    )
}