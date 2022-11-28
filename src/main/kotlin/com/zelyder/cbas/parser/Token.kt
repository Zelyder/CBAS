package com.zelyder.cbas.parser

data class Token(
    val type: TokenType,
    val text: String,
    val position: Int
)
