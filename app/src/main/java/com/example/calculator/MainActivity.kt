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
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.calculator.ui.theme.CalculatorTheme
import net.objecthunter.exp4j.ExpressionBuilder


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculatorTheme {
                var arithmeticExpression by remember { mutableStateOf(value = "") }
                var result by remember { mutableStateOf(value = "") }
                val decimalformatter = DecimalFormat("0.##########")
                val bgColor = if (isSystemInDarkTheme()) {
                    Color(0xFF1A1C18)
                } else {
                    Color(0xFFF1F5E9)
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
                            fontWeight = FontWeight.Black
                        )
                        //Row to show the expression
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(2.dp)
                                .align(Alignment.End)
                        ) {
                            Row(modifier = Modifier) {
                                AnimatedVisibility(
                                    visible = arithmeticExpression.isNotEmpty(),
                                    modifier = Modifier,
                                    enter = slideInVertically(
                                        initialOffsetY = { fullHeight -> fullHeight / 2 },
                                        animationSpec = tween(
                                            durationMillis = 400,
                                            easing = LinearOutSlowInEasing
                                        )
                                    ) + fadeIn(animationSpec = tween(400)),
                                    exit = slideOutVertically(targetOffsetY = { it / 2 }) + fadeOut()
                                ) {
                                    Text(
                                        text = arithmeticExpression,
                                        fontSize = 35.sp,
                                        modifier = Modifier.padding(8.dp)
                                    )
                                }
                            }
                        }
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(2.dp)
                                .align(Alignment.End)
                        ) {
                            //Result Row
                            Row(
                                modifier = Modifier
                                    .padding(10.dp)
                                    .animateContentSize(tween(200))
                            ) {
                                AnimatedVisibility(
                                    visible = arithmeticExpression.isNotEmpty(),
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
                                        fontSize = 35.sp,
                                        textAlign = TextAlign.End
                                    )
                                }
                            }
                        }
                        // First Row
                        Column(modifier = Modifier.weight(6f)) {
                            val topRowItems = arrayListOf("AC", "C", "Rem", "%")
                            val firstRowItems = listOf("1", "2", "3", "x")
                            val secondRowItems = listOf("4", "5", "6", "/")
                            val thirdRowItems = listOf("7", "8", "9", "-")
                            val fourthRowItems = listOf(".", "0", "=", "+")
                            Row(
                                modifier = Modifier
                                    .padding(5.dp)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                for (item in 0..<topRowItems.size) {
                                    ElevatedButton(
                                        onClick = {
                                            if (topRowItems[item] == "AC") {
                                                arithmeticExpression = ""; result = ""
                                            } else if (topRowItems[item] == "C") {
                                                arithmeticExpression =
                                                    arithmeticExpression.dropLast(1)
                                                result = calculatedValue(
                                                    arithmeticExpression,
                                                    decimalformatter
                                                )
                                            } else {
                                                arithmeticExpression += topRowItems[item]
                                                result = calculatedValue(
                                                    arithmeticExpression,
                                                    decimalformatter
                                                )
                                            }
                                        },
                                        modifier = Modifier
                                            //.paddingFromBaseline(top = 35.dp, bottom = 25.dp)
                                            .weight(1f)
                                            .aspectRatio(1f),
                                        colors = ButtonDefaults.elevatedButtonColors(
                                            containerColor = containerColor(
                                                topRowItems[item],
                                                isSystemInDarkTheme()
                                            ), // Your background color (Blue)
                                            contentColor = contentColor(
                                                topRowItems[item],
                                                isSystemInDarkTheme()
                                            )         // Your text color
                                        )
                                    ) {
                                        Text(
                                            text = topRowItems[item],
                                            fontSize = 21.sp,
                                            fontWeight = FontWeight.W900,
                                            softWrap = false
                                        )
                                    }
                                    Spacer(modifier = Modifier)
                                }
                            }
                            Row(
                                modifier = Modifier
                                    .padding(5.dp)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                for (item in 0..<firstRowItems.size) {
                                    ElevatedButton(
                                        onClick = {
                                            if (arithmeticExpression == "0") {
                                                arithmeticExpression.replace("0", "")
                                            } else {
                                                arithmeticExpression += firstRowItems[item]
                                            }
                                            result = calculatedValue(
                                                arithmeticExpression,
                                                decimalformatter
                                            )
                                        },
                                        contentPadding = PaddingValues(0.dp),
                                        modifier = Modifier
                                            //.paddingFromBaseline(top = 35.dp, bottom = 25.dp)
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
                                    Spacer(modifier = Modifier)
                                }
                            }
                            Row(
                                modifier = Modifier
                                    .padding(5.dp)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                for (item in 0..<secondRowItems.size) {
                                    ElevatedButton(
                                        onClick = {
                                            arithmeticExpression =
                                                arithmeticExpression.plus(secondRowItems[item])
                                            result = calculatedValue(
                                                arithmeticExpression,
                                                decimalformatter
                                            )

                                        },
                                        modifier = Modifier
                                            //.paddingFromBaseline(top = 35.dp, bottom = 25.dp)
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
                                    Spacer(modifier = Modifier)
                                }
                            }
                            Row(
                                modifier = Modifier
                                    .padding(5.dp)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                for (item in 0..<thirdRowItems.size) {
                                    ElevatedButton(
                                        onClick = {
                                            arithmeticExpression =
                                                arithmeticExpression.plus(thirdRowItems[item])
                                            result = calculatedValue(
                                                arithmeticExpression,
                                                decimalformatter
                                            )
                                        },
                                        modifier = Modifier
                                            //.paddingFromBaseline(top = 35.dp, bottom = 25.dp)
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
                                    Spacer(modifier = Modifier)
                                }
                            }
                            Row(
                                modifier = Modifier
                                    .padding(5.dp)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                for (item in 0..<fourthRowItems.size) {
                                    ElevatedButton(
                                        onClick = {
                                            if (fourthRowItems[item] == "=") {
                                                arithmeticExpression = calculatedValue(
                                                    arithmeticExpression,
                                                    decimalformatter
                                                )
                                                result = ""
                                            } else {
                                                arithmeticExpression =
                                                    arithmeticExpression.plus(fourthRowItems[item])
                                                result = calculatedValue(
                                                    arithmeticExpression,
                                                    decimalformatter
                                                )
                                            }
                                        },
                                        modifier = Modifier
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
                                    Spacer(modifier = Modifier)
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

fun calculatedValue(value: String, decimalformatter: DecimalFormat): String {
    val libCalculatedValue = calculationLogic(value)
    val calculatedValue = if (libCalculatedValue == "") {
        ""
    } else {
        decimalformatter.format(libCalculatedValue.toBigDecimal())
    }
    return if (calculatedValue != "" && calculatedValue != value) {
        calculatedValue
    } else {
        ""
    }
}