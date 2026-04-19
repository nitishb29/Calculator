package com.example.calculator

import android.icu.text.DecimalFormat
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.delete
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.text.isDigitsOnly
import com.example.calculator.ui.theme.CalculatorTheme
import net.objecthunter.exp4j.ExpressionBuilder


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculatorTheme {
                TextFieldValue(text = "")
                var arithmeticExpression by remember { mutableStateOf(TextFieldState(initialText = "")) }
                var result by remember { mutableStateOf(value = "") }
                val decimalFormatter = DecimalFormat("0.##########")
                val focusRequester = remember { FocusRequester() }
                val bgColor = if (isSystemInDarkTheme()) {
                    Color(0xFF1A1C18)
                } else {
                    Color(0xFFF1F5E9)
                }
                LaunchedEffect(Unit) {
                    focusRequester.requestFocus()
                }
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .background(bgColor)
                    ) {
                        // Main Column
                        Text(
                            text = "Calculator",
                            modifier = Modifier
                                .padding(innerPadding)
                                .align(Alignment.CenterHorizontally),
                            fontSize = 35.sp,
                            fontWeight = FontWeight.Black,
                        )
                        //Row to show the expression
                        Column(
                            modifier = Modifier
                                .weight(2f)
                                .padding(horizontal = 24.dp, vertical = 16.dp)
                                .fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(5.dp)

                        ) {
                            Row(
                                modifier = Modifier
                                    .animateContentSize(tween(200))
                            ) {
                                AnimatedVisibility(
                                    visible = true,
                                    modifier = Modifier,
                                    enter = slideInVertically(
                                        initialOffsetY = { fullHeight -> fullHeight / 4 },
                                        animationSpec = tween(
                                            durationMillis = 400,
                                            easing = LinearOutSlowInEasing
                                        )
                                    ) + fadeIn(animationSpec = tween(400)),
                                    exit = slideOutVertically(targetOffsetY = { it / 2 }) + fadeOut()
                                ) {
                                    BasicTextField(
                                        state = arithmeticExpression,
                                        readOnly = true,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .focusRequester(focusRequester)
                                            .focusable(),
                                        textStyle = TextStyle(
                                            fontSize = 45.sp,
                                            textAlign = TextAlign.End,
                                            color = if (isSystemInDarkTheme()) {
                                                Color.LightGray
                                            } else {
                                                Color.DarkGray
                                            }
                                        ),
                                        cursorBrush = SolidColor(if (isSystemInDarkTheme()) Color.White else Color.Black)
                                    )
                                }
                            }
                            //Result Row
                            Row(
                                modifier = Modifier
                                    .animateContentSize(tween(200)),
                            ) {
                                AnimatedVisibility(
                                    visible = arithmeticExpression.text.isNotEmpty(),
                                    modifier = Modifier,
                                    enter = slideInVertically(
                                        initialOffsetY = { fullHeight -> fullHeight / 4 },
                                        animationSpec = tween(
                                            durationMillis = 400,
                                            easing = LinearOutSlowInEasing
                                        )
                                    ) + fadeIn(animationSpec = tween(400)),
                                    exit = slideOutVertically(targetOffsetY = { it / 2 }) + fadeOut()
                                ) {
                                    Text(
                                        text = result,
                                        modifier = Modifier.fillMaxWidth(),
                                        fontSize = 30.sp,
                                        textAlign = TextAlign.End,
                                        color = if (isSystemInDarkTheme()) {
                                            Color.White
                                        } else {
                                            Color.Black
                                        }
                                    )
                                }
                            }
                        }
                        // First Column
                        Column(
                            modifier = Modifier
                                .weight(6.5f)
                                .padding(5.dp),
                            verticalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            val topRowItems = arrayListOf("AC", "C", "Rem", "%")
                            val firstRowItems = listOf("1", "2", "3", "x")
                            val secondRowItems = listOf("4", "5", "6", "/")
                            val thirdRowItems = listOf("7", "8", "9", "-")
                            val fourthRowItems = listOf(".", "0", "=", "+")
                            Row(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .fillMaxWidth()
                            ) {
                                for (item in 0..<topRowItems.size) {
                                    ElevatedButton(
                                        onClick = {
                                            if (topRowItems[item] == "AC") {
                                                arithmeticExpression.setTextAndPlaceCursorAtEnd(""); result =
                                                    ""
                                            } else if (topRowItems[item] == "C") {
                                                arithmeticExpression.edit {
                                                    val cursorPosition = selection.start
                                                    if (arithmeticExpression.text[cursorPosition - 3] == 'R') {
                                                        delete(cursorPosition - 3, cursorPosition)
                                                    } else if (cursorPosition > 0) {
                                                        delete(cursorPosition - 1, cursorPosition)
                                                    }
                                                }
                                                result = calculatedValue(
                                                    arithmeticExpression.text.toString(),
                                                    decimalFormatter
                                                )
                                            } else {
                                                overwriteExistingOperator(
                                                    item,
                                                    arithmeticExpression,
                                                    topRowItems,
                                                    true
                                                )
                                                result = calculatedValue(
                                                    arithmeticExpression.text.toString(),
                                                    decimalFormatter
                                                )
                                            }
                                        },
                                        modifier = Modifier
                                            .shadow(8.dp, CircleShape)
                                            .weight(1f)
                                            .aspectRatio(1f),
                                        colors = ButtonDefaults.elevatedButtonColors(
                                            containerColor = containerColor(
                                                topRowItems[item],
                                                isSystemInDarkTheme()
                                            ),
                                            contentColor = contentColor(
                                                topRowItems[item],
                                                isSystemInDarkTheme()
                                            )
                                        )
                                    ) {
                                        Text(
                                            text = topRowItems[item],
                                            fontSize = 21.sp,
                                            fontWeight = FontWeight.W900,
                                            softWrap = false
                                        )
                                    }
                                }
                            }
                            Row(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .fillMaxWidth(),
                            ) {
                                for (item in 0..<firstRowItems.size) {
                                    ElevatedButton(
                                        onClick = {
                                            overwriteExistingOperator(
                                                item,
                                                arithmeticExpression,
                                                firstRowItems,
                                                false
                                            )
                                            result = calculatedValue(
                                                arithmeticExpression.text.toString(),
                                                decimalFormatter
                                            )
                                        },
                                        contentPadding = PaddingValues(0.dp),
                                        modifier = Modifier
                                            .shadow(8.dp, CircleShape)
                                            .weight(1f)
                                            .aspectRatio(1f),
                                        colors = ButtonDefaults.elevatedButtonColors(
                                            containerColor = containerColor(
                                                firstRowItems[item],
                                                isSystemInDarkTheme()
                                            ),
                                            contentColor = contentColor(
                                                firstRowItems[item],
                                                isSystemInDarkTheme()
                                            )
                                        )
                                    ) {
                                        Text(
                                            firstRowItems[item],
                                            fontSize = 21.sp,
                                            fontWeight = FontWeight.W900
                                        )
                                    }
                                }
                            }
                            Row(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .fillMaxWidth()
                            ) {
                                for (item in 0..<secondRowItems.size) {
                                    ElevatedButton(
                                        onClick = {
                                            overwriteExistingOperator(
                                                item,
                                                arithmeticExpression,
                                                secondRowItems,
                                                false
                                            )
                                            result = calculatedValue(
                                                arithmeticExpression.text.toString(),
                                                decimalFormatter
                                            )

                                        },
                                        modifier = Modifier
                                            .shadow(8.dp, CircleShape)
                                            .weight(1f)
                                            .aspectRatio(1f),
                                        contentPadding = PaddingValues(0.dp),
                                        colors = ButtonDefaults.elevatedButtonColors(
                                            containerColor = containerColor(
                                                secondRowItems[item],
                                                isSystemInDarkTheme()
                                            ),
                                            contentColor = contentColor(
                                                secondRowItems[item],
                                                isSystemInDarkTheme()
                                            )
                                        )
                                    ) {
                                        Text(
                                            text = secondRowItems[item],
                                            fontSize = 21.sp,
                                            fontWeight = FontWeight.W900
                                        )
                                    }
                                }
                            }
                            Row(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .fillMaxWidth()
                            ) {
                                for (item in 0..<thirdRowItems.size) {
                                    ElevatedButton(
                                        onClick = {
                                            overwriteExistingOperator(
                                                item,
                                                arithmeticExpression,
                                                thirdRowItems,
                                                false
                                            )
                                            result = calculatedValue(
                                                arithmeticExpression.text.toString(),
                                                decimalFormatter
                                            )
                                        },
                                        modifier = Modifier
                                            .shadow(8.dp, CircleShape)
                                            .weight(1f)
                                            .aspectRatio(1f),
                                        colors = ButtonDefaults.elevatedButtonColors(
                                            containerColor = containerColor(
                                                thirdRowItems[item],
                                                isSystemInDarkTheme()
                                            ),
                                            contentColor = contentColor(
                                                thirdRowItems[item],
                                                isSystemInDarkTheme()
                                            )
                                        )
                                    ) {
                                        Text(
                                            text = thirdRowItems[item],
                                            fontSize = 21.sp,
                                            fontWeight = FontWeight.W900
                                        )
                                    }
                                }
                            }
                            Row(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .fillMaxWidth(),
                            ) {
                                for (item in 0..<fourthRowItems.size) {
                                    ElevatedButton(
                                        onClick = {
                                            if (fourthRowItems[item] == "=") {
                                                arithmeticExpression.setTextAndPlaceCursorAtEnd(
                                                    calculatedValue(
                                                        arithmeticExpression.text.toString(),
                                                        decimalFormatter
                                                    )
                                                )
                                                result = ""
                                            } else {
                                                overwriteExistingOperator(
                                                    item,
                                                    arithmeticExpression,
                                                    fourthRowItems,
                                                    false
                                                )
                                                result = calculatedValue(
                                                    arithmeticExpression.text.toString(),
                                                    decimalFormatter
                                                )
                                            }
                                        },
                                        modifier = Modifier
                                            .shadow(8.dp, CircleShape)
                                            .weight(1f)
                                            .aspectRatio(1f),
                                        colors = ButtonDefaults.elevatedButtonColors(
                                            containerColor = containerColor(
                                                fourthRowItems[item],
                                                isSystemInDarkTheme()
                                            ),
                                            contentColor = contentColor(
                                                fourthRowItems[item],
                                                isSystemInDarkTheme()
                                            )
                                        )
                                    ) {
                                        Text(
                                            text = fourthRowItems[item],
                                            fontSize = 21.sp,
                                            fontWeight = FontWeight.W900
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun calculationLogic(arithmeticExpression: String): String {
    val cleanExpression = arithmeticExpression
        .replace("x", "*")
        .replace("Rem", "%")
    val percentageRegex = Regex("(\\d+\\.?\\d*)([+\\-*/])(\\d+\\.?\\d*)%")


    val newExpression = if (cleanExpression.contains(percentageRegex)) {
        cleanExpression.replace(percentageRegex) { matchResult ->
            val firstNum = matchResult.groupValues[1]
            val operator = matchResult.groupValues[2]
            val percentNum = matchResult.groupValues[3]
            "$firstNum$operator($firstNum*$percentNum/100)"
        }
    } else {
        cleanExpression.replace("%", "/100")
    }
    try {
        val expression = ExpressionBuilder(newExpression).build()
        val evalResult = expression.evaluate()
        return evalResult.toString()
    } catch (e: Exception) {
        e.printStackTrace()
        return ""
    }
}

@Composable
fun containerColor(item: String, isDarkThemeEnabled: Boolean): Color {
    val color = when (item) {
        "=" -> MaterialTheme.colorScheme.primary
        "/", "x", "-", "+" -> Color(0xFFD7E8CD)
        "C", "%", "Rem" -> Color(0xFFE5832B)
        "AC" -> Color(0xFFFEE2E2)
        else -> if (isDarkThemeEnabled) {
            Color.DarkGray
        } else {
            Color(0xFFD8EAA8)
        } // Clean for numbers
    }
    return color
}

@Composable
fun contentColor(item: String, isDarkThemeEnabled: Boolean): Color {
    val color = when (item) {
        "AC" -> Color(0xFF991B1B)
        "/", "x", "-", "+" -> Color(0xFF3A4D39)
        else -> if (isDarkThemeEnabled) {
            Color.White
        } else {
            Color(0xFF1C1C1C)
        }
    }
    return color
}

fun calculatedValue(value: String, decimalFormatter: DecimalFormat): String {
    val libCalculatedValue = calculationLogic(value)
    val calculatedValue = if (libCalculatedValue == "") {
        ""
    } else {
        decimalFormatter.format(libCalculatedValue.toBigDecimal())
    }
    return if (calculatedValue != "" && calculatedValue != value) {
        calculatedValue
    } else {
        ""
    }
}

fun overwriteExistingOperator(
    item: Int,
    arithmeticExpression: TextFieldState,
    itemRow: List<String>,
    isTopRow: Boolean
) {
    val operatorsList = listOf("+", "-", "x", "/", "Rem", "%")
    arithmeticExpression.edit {
        val caretPosition = selection.start
        if (caretPosition < arithmeticExpression.text.length) {
            if (arithmeticExpression.text[caretPosition - 1].toString() in operatorsList) {
                delete(caretPosition - 1, caretPosition)
                replace(caretPosition - 1, caretPosition - 1, itemRow[item])
            } else {
                replace(
                    caretPosition,
                    caretPosition,
                    itemRow[item]
                )
            }
        } else if (caretPosition != 0 && arithmeticExpression.text.last()
                .toString() in operatorsList && !itemRow[item].isDigitsOnly() && !isTopRow
        ) {
            delete(caretPosition, caretPosition)
            replace(
                caretPosition - 1,
                caretPosition,
                itemRow[item]
            )
        } else {
            append(itemRow[item])
        }
    }
}
