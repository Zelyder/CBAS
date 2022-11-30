package com.zelyder.cbas.ui// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.material.MaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.zelyder.cbas.parser.Lexer
import com.zelyder.cbas.parser.Parser
import java.io.File

@Composable
@Preview
fun App() {
    var inputText by remember { mutableStateOf("a = 5;\nb = 11;\nans = a + b;\nprint ans;") }
    var outputText by remember { mutableStateOf("Вывод:\n") }


    MaterialTheme {
        Column(
            modifier = Modifier.scrollable(rememberScrollState(), orientation = Orientation.Horizontal)
        ) {
            Button(onClick = {
                val lexer = Lexer(inputText)
                val tokens = lexer.lexAnalysis()
                println(tokens)
                val parser = Parser(tokens)
                val rootNode = parser.parseCode()
                val output = parser.run(rootNode).asString()
                outputText += output
            }) {
                Text("Запустить")
            }

            TextField(
                value = inputText,
                onValueChange = {
                    inputText = it
                }
            )
            Text(outputText)
        }
    }
}

fun mainUi() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}

fun mainConsole() {
    val inputText = File("src/main/resources/program.cbas").readText()
    val lexer = Lexer(inputText)
    val tokens = lexer.lexAnalysis()
    println(tokens)
    val parser = Parser(tokens)
    val rootNode = parser.parseCode()
    parser.run(rootNode)
}

fun main() {
    mainConsole()
}