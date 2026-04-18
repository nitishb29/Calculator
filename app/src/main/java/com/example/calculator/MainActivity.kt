package com.example.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculator.ui.theme.CalculatorTheme
import java.math.BigDecimal
import java.math.RoundingMode

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculatorTheme {
                val haptic = LocalHapticFeedback.current
                var arithmeticExpression by remember { mutableStateOf(value = "") }
                var result by remember { mutableStateOf(value = "") }
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .background(Color.Transparent)
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
                                .padding(7.dp)
                                .offset(15.dp, 50.dp)
                                .weight(1f)
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = arithmeticExpression,
                                fontSize = 35.sp,
                                fontWeight = FontWeight.Black
                            )
                        }
                        HorizontalDivider(modifier = Modifier)
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                //.size(150.dp, 25.dp)
                                .fillMaxWidth()
                        ) {
                            //Result Row
                            Column(modifier = Modifier) {
                                Row(
                                    modifier = Modifier
                                        .padding(7.dp)
                                        .offset(15.dp)
                                ) {
                                    Text(
                                        text = "Result : ",
                                        fontSize = 35.sp,
                                        fontWeight = FontWeight.Black
                                    )
                                }
                                Row(
                                    modifier = Modifier
                                        .padding(7.dp)
                                        .offset(x = 15.dp, y = 35.dp)
                                ) {
                                    Text(
                                        text = result,
                                        fontSize = 35.sp,
                                        fontWeight = FontWeight.Black
                                    )
                                }
                            }
                            Column(modifier = Modifier.offset(y = (-50).dp)) {
                                var isClicked by remember { mutableStateOf(false) }
                                Row(modifier = Modifier) {
                                    if (arithmeticExpression.isNotEmpty()) {
                                        ElevatedButton(
                                            onClick = {
                                                arithmeticExpression =
                                                    arithmeticExpression.dropLast(1); isClicked =
                                                true; haptic.performHapticFeedback(
                                                HapticFeedbackType.ContextClick
                                            )
                                            },
                                            modifier = Modifier.size(100.dp, 70.dp)
                                                .offset(290.dp, 50.dp)
                                        ) {
                                            Text(
                                                "C",
                                                fontSize = 35.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    }
                                }
                                Row(modifier = Modifier) {
                                    if (arithmeticExpression.isNotEmpty() and isClicked) {
                                        ElevatedButton(
                                            onClick = {
                                                arithmeticExpression = ""; result = ""; isClicked =
                                                false; haptic.performHapticFeedback(
                                                HapticFeedbackType.ContextClick
                                            )
                                            },
                                            modifier = Modifier.size(width = 100.dp, 90.dp)
                                                .offset(290.dp, 50.dp)
                                        ) {
                                            Text(
                                                "AC",
                                                fontSize = 35.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    }
                                }
                            }
                        }
                            HorizontalDivider(modifier = Modifier)
                            // First Row
                            Column(modifier = Modifier.weight(3f)) {
                                val firstRowItems = listOf<String>("1", "2", "3", "*")
                                val secondRowItems = listOf<String>("4", "5", "6", "%")
                                val thirdRowItems = listOf<String>("7", "8", "9", "-")
                                val fourthRowItems = listOf<String>(".", "0", "=", "+")
                                Row(
                                    modifier = Modifier
                                        .padding(10.dp)
                                        .fillMaxWidth()
                                ) {
                                    for (item in 0..<firstRowItems.size) {
                                        ElevatedButton(
                                            onClick = { arithmeticExpression += firstRowItems[item] ; haptic.performHapticFeedback(HapticFeedbackType.ContextClick)},
                                            modifier = Modifier
                                                .paddingFromBaseline(
                                                    top = 35.dp,
                                                    bottom = 25.dp
                                                )
                                                .size(90.dp, 90.dp)
                                        ) {
                                            Text(
                                                text = if (firstRowItems[item] == "*") {
                                                    "x"
                                                } else {
                                                    firstRowItems[item]
                                                },
                                                fontSize = 35.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                        Spacer(modifier = Modifier.size(5.dp))
                                    }
                                }
                                HorizontalDivider(modifier = Modifier)
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
                                                    haptic.performHapticFeedback(HapticFeedbackType.ContextClick)
                                                },
                                                modifier = Modifier
                                                    .paddingFromBaseline(
                                                        top = 35.dp,
                                                        bottom = 25.dp
                                                    )
                                                    .size(90.dp, 90.dp)
                                            ) {
                                                Text(
                                                    text = secondRowItems[item],
                                                    fontSize = 35.sp,
                                                    fontWeight = FontWeight.Bold
                                                )
                                            }
                                        }
                                        Spacer(modifier = Modifier.size(5.dp))
                                    }
                                }
                                HorizontalDivider(modifier = Modifier)
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
                                                    haptic.performHapticFeedback(HapticFeedbackType.ContextClick)
                                                },
                                                modifier = Modifier
                                                    .paddingFromBaseline(
                                                        top = 35.dp,
                                                        bottom = 25.dp
                                                    )
                                                    .size(90.dp, 90.dp)
                                            ) {
                                                Text(
                                                    text = thirdRowItems[item],
                                                    fontSize = 35.sp,
                                                    fontWeight = FontWeight.Bold
                                                )
                                            }
                                        }
                                        Spacer(modifier = Modifier.size(5.dp))
                                    }
                                }
                                HorizontalDivider(modifier = Modifier)
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
                                                        result = calculationLogic(arithmeticExpression)
                                                    } else {
                                                        arithmeticExpression =
                                                            arithmeticExpression.plus(fourthRowItems[item])
                                                        haptic.performHapticFeedback(HapticFeedbackType.ContextClick)
                                                    }
                                                },
                                                modifier = Modifier
                                                    .paddingFromBaseline(
                                                        top = 35.dp,
                                                        bottom = 25.dp
                                                    )
                                                    .size(90.dp, 90.dp)
                                            ) {
                                                Text(
                                                    text = fourthRowItems[item],
                                                    fontSize = 35.sp,
                                                    fontWeight = FontWeight.Bold
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
                    val value = if (rightNumber > 0) {
                        leftNumber.div(rightNumber)
                    } else {
                        0.0
                    }
                    if (value > 0) {
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
                    val subOperatorIndex =
                        numbersWithOperators.indexOf("%")
                    val leftNumber =
                        numbersWithOperators[subOperatorIndex - 1].toDouble()
                    val rightNumber =
                        numbersWithOperators[subOperatorIndex + 1].toDouble()
                    val value = leftNumber % rightNumber
                    numbersWithOperators.removeAt(subOperatorIndex - 1)
                    numbersWithOperators.removeAt(subOperatorIndex - 1)
                    numbersWithOperators.removeAt(subOperatorIndex - 1)
                    numbersWithOperators.add(
                        subOperatorIndex - 1,
                        value.toString()
                    )
                }
            }
            return BigDecimal(numbersWithOperators.first()).setScale(1, RoundingMode.HALF_UP).toString()

        } catch (e: Exception) {
            e.printStackTrace()
            return BigDecimal.ZERO.toString()

        }
    }