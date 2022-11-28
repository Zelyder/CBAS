package com.zelyder.cbas.parser

sealed class TokenType(
    val name: String,
    val pattern: String
) {
    object Log : TokenType("LOG", "print")
    object If : TokenType("IF", "if")
    object Else : TokenType("ELSE", "else")
    object Number : TokenType("NUMBER", "[0-9]+[.,]?[0-9]*")
    object Variable : TokenType("VARIABLE", "[a-zA-Z_]+")
    object Text : TokenType("TEXT", """"[[A-Za-z0-9=:]*\s[A-Za-z0-9=:]*]*"""")
    object Semicolon : TokenType("SEMICOLON", ";")
    object Space : TokenType("SPACE", "[\\s\\n\\t\\r]+")
    object Assign : TokenType("ASSIGN", "=")
    object Plus : TokenType("PLUS", "\\+")
    object Minus : TokenType("MINUS", "-")
    object Multiply : TokenType("MULTIPLY", "\\*")
    object Divide : TokenType("DIVIDE", "/")
    object LessThan : TokenType("LESS_THAN", "<")
    object GreaterThan : TokenType("GREATER_THAN", ">")
    object LPar : TokenType("LPAR", "\\(")
    object RPar : TokenType("RPAR", "\\)")
}