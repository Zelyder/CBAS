package com.zelyder.cbas.parser

import com.zelyder.cbas.values.Value

object Context {
    val scope: MutableMap<String, Value> = mutableMapOf()
    val logBuilder = StringBuilder()
}