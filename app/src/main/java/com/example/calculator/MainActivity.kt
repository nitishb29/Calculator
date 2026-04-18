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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.calculator.ui.theme.CalculatorTheme
import java.math.BigDecimal
import java.math.RoundingMode

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculatorTheme {
                var arithmeticExpression by remember { mutableStateOf(value = "") }
                var result by remember { mutableStateOf(value = "") }
                val pattern = Regex("(?<=[+\\-*/%])|(?=[+\\-*/%])")
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
                            modifier = Modifier.weight(1f)
                        ) {
                            Row(
                                modifier = Modifier,
                                horizontalArrangement = Arrangement.End
                            ) {
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
                                        modifier = Modifier.padding(8.dp),
                                        textAlign = TextAlign.End
                                    )
                                }
                            }
                        }
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(10.dp)
                            //.fillMaxWidth()
                        ) {
                            //Result Row
                            Column(
                                modifier = Modifier
                                    .padding(10.dp)
                                    //.offset(x = 20.dp, y = (10).dp)
                                    .animateContentSize(tween(200)),
                                horizontalAlignment = Alignment.End
                            ) {
                                Row(
                                    modifier = Modifier,
                                    verticalAlignment = Alignment.Bottom,
                                    horizontalArrangement = Arrangement.End
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
                        }
                        HorizontalDivider(modifier = Modifier.clip(RoundedCornerShape(24.dp)))
                        // First Row
                        Column(modifier = Modifier.weight(6f)) {
                            val topRowItems = arrayListOf("AC", "C", "Rem", "%")
                            val firstRowItems = listOf("1", "2", "3", "*")
                            val secondRowItems = listOf("4", "5", "6", "/")
                            val thirdRowItems = listOf("7", "8", "9", "-")
                            val fourthRowItems = listOf(".", "0", "=", "+")
                            Row(
                                modifier = Modifier
                                    .padding(10.dp)
                                    .fillMaxWidth()
                            ) {
                                for (item in 0..<topRowItems.size) {
                                    ElevatedButton(
                                        onClick = {
                                            if (topRowItems[item] == "AC") {
                                                arithmeticExpression = ""; result = ""
                                            } else if (topRowItems[item] == "C") {
                                                arithmeticExpression =
                                                    arithmeticExpression.dropLast(1)
                                                if (arithmeticExpression.isNotEmpty()) {
                                                    if (arithmeticExpression.last()
                                                            .isDigit() and arithmeticExpression.contains(
                                                            pattern
                                                        )
                                                    ) {
                                                        result = decimalformatter.format(
                                                            calculationLogic(arithmeticExpression).toBigDecimal()
                                                        )
                                                    }
                                                }
                                            } else {
                                                arithmeticExpression += topRowItems[item]
                                                if (arithmeticExpression.last()
                                                        .isDigit() and arithmeticExpression.contains(
                                                        pattern
                                                    )
                                                ) {
                                                    result = decimalformatter.format(
                                                        calculationLogic(arithmeticExpression).toBigDecimal()
                                                    )
                                                }
                                            }
                                        },
                                        modifier = Modifier
                                            .paddingFromBaseline(
                                                top = 35.dp,
                                                bottom = 25.dp
                                            )
                                            .size(90.dp, 90.dp),
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
                                    Spacer(modifier = Modifier.size(5.dp))
                                }
                            }
                            HorizontalDivider(modifier = Modifier.clip(RoundedCornerShape(24.dp)))
                            Row(
                                modifier = Modifier
                                    .padding(10.dp)
                                    .fillMaxWidth()
                            ) {
                                for (item in 0..<firstRowItems.size) {
                                    ElevatedButton(
                                        onClick = {
                                            if (arithmeticExpression == "0") {
                                                arithmeticExpression.replace("0", "")
                                            }
                                            arithmeticExpression += firstRowItems[item]
                                            if (arithmeticExpression.last()
                                                    .isDigit() and arithmeticExpression.contains(
                                                    pattern
                                                )
                                            ) {
                                                result = decimalformatter.format(
                                                    calculationLogic(arithmeticExpression).toBigDecimal()
                                                )
                                            }
                                        },
                                        contentPadding = PaddingValues(0.dp),
                                        modifier = Modifier
                                            .paddingFromBaseline(
                                                top = 35.dp,
                                                bottom = 25.dp
                                            )
                                            .size(90.dp, 90.dp),
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
                                            text = if (firstRowItems[item] == "*") {
                                                "x"
                                            } else {
                                                firstRowItems[item]
                                            },
                                            fontSize = 21.sp,
                                            fontWeight = FontWeight.W900
                                        )
                                    }
                                    Spacer(modifier = Modifier.size(5.dp))
                                }
                            }
                            HorizontalDivider(modifier = Modifier.clip(RoundedCornerShape(24.dp)))
                            Row(
                                modifier = Modifier
                                    .padding(10.dp)
                                    .fillMaxWidth()
                            ) {
                                for (item in 0..<secondRowItems.size) {
                                    Column(modifier = Modifier) {
                                        ElevatedButton(
                                            onClick = {
                                                arithmeticExpression =
                                                    arithmeticExpression.plus(secondRowItems[item])
                                                if (arithmeticExpression.last()
                                                        .isDigit() and arithmeticExpression.contains(
                                                        pattern
                                                    )
                                                ) {
                                                    result = decimalformatter.format(
                                                        calculationLogic(arithmeticExpression).toBigDecimal()
                                                    )
                                                }
                                            },
                                            modifier = Modifier
                                                .paddingFromBaseline(
                                                    top = 35.dp,
                                                    bottom = 25.dp
                                                )
                                                .size(90.dp, 90.dp),
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
                                    Spacer(modifier = Modifier.size(5.dp))
                                }
                            }
                            HorizontalDivider(modifier = Modifier.clip(RoundedCornerShape(24.dp)))
                            Row(
                                modifier = Modifier
                                    .padding(10.dp)
                                    .fillMaxWidth()
                            ) {
                                for (item in 0..<thirdRowItems.size) {
                                    Column(modifier = Modifier) {
                                        ElevatedButton(
                                            onClick = {
                                                arithmeticExpression =
                                                    arithmeticExpression.plus(thirdRowItems[item])
                                                if (arithmeticExpression.last()
                                                        .isDigit() and arithmeticExpression.contains(
                                                        pattern
                                                    )
                                                ) {
                                                    result = decimalformatter.format(
                                                        calculationLogic(arithmeticExpression).toBigDecimal()
                                                    )
                                                }
                                            },
                                            modifier = Modifier
                                                .paddingFromBaseline(
                                                    top = 35.dp,
                                                    bottom = 25.dp
                                                )
                                                .size(90.dp, 90.dp),
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
                                    Spacer(modifier = Modifier.size(5.dp))
                                }
                            }
                            HorizontalDivider(modifier = Modifier.clip(RoundedCornerShape(24.dp)))
                            Row(
                                modifier = Modifier
                                    .padding(10.dp)
                                    .fillMaxWidth()
                            ) {
                                for (item in 0..<fourthRowItems.size) {
                                    Column(modifier = Modifier) {
                                        ElevatedButton(
                                            onClick = {
                                                if (fourthRowItems[item] == "=") {
                                                    if (arithmeticExpression.last()
                                                            .isDigit() and arithmeticExpression.contains(
                                                            pattern
                                                        )
                                                    ) {
                                                        arithmeticExpression =
                                                            decimalformatter.format(
                                                                calculationLogic(
                                                                    arithmeticExpression
                                                                ).toBigDecimal()
                                                            )
                                                        result = ""
                                                    }
                                                } else {
                                                    arithmeticExpression =
                                                        arithmeticExpression.plus(fourthRowItems[item])
                                                    if (arithmeticExpression.last()
                                                            .isDigit() and arithmeticExpression.contains(
                                                            pattern
                                                        )
                                                    ) {
                                                        result = decimalformatter.format(
                                                            calculationLogic(arithmeticExpression).toBigDecimal()
                                                        )
                                                    }
                                                }
                                            },
                                            modifier = Modifier
                                                .paddingFromBaseline(
                                                    top = 35.dp,
                                                    bottom = 25.dp
                                                )
                                                .size(90.dp, 90.dp),
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
                                    Spacer(modifier = Modifier.size(5.dp))
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
    val pattern = Regex("(?<=[+\\-*/%])|(?=[+\\-*/%])")
    val numbersWithOperators = arithmeticExpression.split(pattern).toMutableList()
    try {
        if (numbersWithOperators.contains("/")) {
            while (numbersWithOperators.contains("/")) {
                val divOperatorIndex = numbersWithOperators.indexOf("/")
                val leftNumber =
                    numbersWithOperators[divOperatorIndex - 1].toDouble()
                val rightNumber =
                    numbersWithOperators[divOperatorIndex + 1].toDouble()
                val value = if (rightNumber > 0.0) {
                    leftNumber / rightNumber
                } else {
                    0.0
                }
                if (value > 0.0) {
                    numbersWithOperators.removeAt(divOperatorIndex - 1)
                    numbersWithOperators.removeAt(divOperatorIndex - 1)
                    numbersWithOperators.removeAt(divOperatorIndex - 1)
                    numbersWithOperators.add(divOperatorIndex - 1, value.toString())
                } else {
                    return BigDecimal.ZERO.toString()
                }
            }
        }
        if (numbersWithOperators.contains("*")) {
            while (numbersWithOperators.contains("*")) {
                val mulOperatorIndex = numbersWithOperators.indexOf("*")
                val leftNumber =
                    numbersWithOperators[mulOperatorIndex - 1].toDouble()
                val rightNumber =
                    numbersWithOperators[mulOperatorIndex + 1].toDouble()
                val value = leftNumber.times(rightNumber)
                numbersWithOperators.removeAt(mulOperatorIndex - 1)
                numbersWithOperators.removeAt(mulOperatorIndex - 1)
                numbersWithOperators.removeAt(mulOperatorIndex - 1)
                numbersWithOperators.add(mulOperatorIndex - 1, value.toString())
            }
        }
        if (numbersWithOperators.contains("+")) {
            while (numbersWithOperators.contains("+")) {
                val addOperatorIndex = numbersWithOperators.indexOf("+")
                val leftNumber =
                    numbersWithOperators[addOperatorIndex - 1].toDouble()
                val rightNumber =
                    numbersWithOperators[addOperatorIndex + 1].toDouble()
                val value = leftNumber.plus(rightNumber)
                numbersWithOperators.removeAt(addOperatorIndex - 1)
                numbersWithOperators.removeAt(addOperatorIndex - 1)
                numbersWithOperators.removeAt(addOperatorIndex - 1)
                numbersWithOperators.add(addOperatorIndex - 1, value.toString())
            }
        }
        if (numbersWithOperators.contains("-")) {
            while (numbersWithOperators.contains("-")) {
                val subOperatorIndex =
                    numbersWithOperators.indexOf("-")
                val leftNumber =
                    numbersWithOperators[subOperatorIndex - 1].toDouble()
                val rightNumber =
                    numbersWithOperators[subOperatorIndex + 1].toDouble()
                val value = leftNumber.minus(rightNumber)
                numbersWithOperators.removeAt(subOperatorIndex - 1)
                numbersWithOperators.removeAt(subOperatorIndex - 1)
                numbersWithOperators.removeAt(subOperatorIndex - 1)
                numbersWithOperators.add(
                    subOperatorIndex - 1,
                    value.toString()
                )
            }
        }
        if (numbersWithOperators.contains("%")) {
            while (numbersWithOperators.contains("%")) {
                val percentOperatorIndex =
                    numbersWithOperators.indexOf("%")
                val leftNumber =
                    numbersWithOperators[percentOperatorIndex - 1].toDouble()
                val rightNumber =
                    numbersWithOperators[percentOperatorIndex + 1].toDouble()
                val value = (leftNumber * rightNumber) / 100
                numbersWithOperators.removeAt(percentOperatorIndex - 1)
                numbersWithOperators.removeAt(percentOperatorIndex - 1)
                numbersWithOperators.removeAt(percentOperatorIndex - 1)
                numbersWithOperators.add(
                    percentOperatorIndex - 1,
                    value.toString()
                )
            }
        }
        if (numbersWithOperators.contains("Rem")) {
            while (numbersWithOperators.contains("Rem")) {
                val remOperatorIndex =
                    numbersWithOperators.indexOf("Rem")
                val leftNumber =
                    numbersWithOperators[remOperatorIndex - 1].toDouble()
                val rightNumber =
                    numbersWithOperators[remOperatorIndex + 1].toDouble()
                val value = leftNumber % rightNumber
                numbersWithOperators.removeAt(remOperatorIndex - 1)
                numbersWithOperators.removeAt(remOperatorIndex - 1)
                numbersWithOperators.removeAt(remOperatorIndex - 1)
                numbersWithOperators.add(
                    remOperatorIndex - 1,
                    value.toString()
                )
            }
        }

        return BigDecimal(numbersWithOperators.first()).setScale(10, RoundingMode.HALF_UP)
            .toString()

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