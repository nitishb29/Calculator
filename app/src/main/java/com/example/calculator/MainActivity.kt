package com.example.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import com.example.calculator.ui.theme.CalculatorTheme
import kotlin.enums.enumEntries

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculatorTheme {
                var firstNumber by remember { mutableStateOf(value = "") }
                var secondNumber by remember { mutableStateOf(value = "") }
                var selectedOperator by remember { mutableStateOf(value = "") }
                var result by remember { mutableStateOf(value = "") }
                var expressionRow by remember { mutableStateOf(value = "") }
                val arithmeticOperators : List<String> =  listOf("+","-","*","/")
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .background(Color.Black)
                    ) {
                        // Main Column
                        Text(
                            text = "Calculator",
                            modifier = Modifier.padding(innerPadding),
                            fontSize = 35.sp,
                            fontWeight = FontWeight.Black
                        )
                        //Row to show the expression
                        Box(modifier = Modifier) {
                            Row(
                                modifier = Modifier.padding(vertical = 100.dp),
                                horizontalArrangement = Arrangement.End
                            ) {
                                /*if (firstNumber.isNotEmpty() and firstNumber.equals(0.toString())) {
                                    expressionRow = 0.toString()
                                } else if (selectedOperator == "") {
                                    expressionRow = firstNumber.toInt().toString()
                                } else if (secondNumber.isNotEmpty() and secondNumber.equals(0.toString())) {
                                    expressionRow = firstNumber.toInt().toString() + selectedOperator
                                } else {
                                    expressionRow =
                                        firstNumber + selectedOperator + secondNumber
                                }*/
                                Text(
                                    text = firstNumber,
                                    fontSize = 35.sp,
                                    fontWeight = FontWeight.Black
                                )
                            }
                        }
                        HorizontalDivider(modifier = Modifier)
                        Box(modifier = Modifier.weight(2f)) {
                            //Result Row
                            Row(modifier = Modifier.padding(vertical = 25.dp, horizontal = 15.dp)) {
                                Text(
                                    text = "Result : ",
                                    fontSize = 35.sp,
                                    fontWeight = FontWeight.Black
                                )
                                Text(
                                    text = result,
                                    fontSize = 35.sp,
                                    fontWeight = FontWeight.Black
                                )
                            }
                        }
                        HorizontalDivider(modifier = Modifier.padding(1.dp))
                        Box(modifier = Modifier.weight(2f)) {
                            Row(
                                modifier = Modifier.padding(all = 2.dp),
                                verticalAlignment = Alignment.Top,
                                horizontalArrangement = Arrangement.Absolute.Center
                            ) {
                                // First Row
                                for (firstRowNum in 1..3) {
                                    Column(modifier = Modifier.padding(10.dp)) {
                                        Button(
                                            onClick = {
                                                firstNumber += firstRowNum.toString()
//                                                if (firstNumber.isNotEmpty() and firstNumber.equals(0.toString())) {
//                                                    firstNumber = firstRowNum.toString()
//                                                } else if(firstNumber.toDouble() > 0.0 && selectedOperator.isEmpty()){
//                                                 firstNumber = firstNumber.plus(firstRowNum.toString())
//                                                }
//                                                else if(selectedOperator.isNotEmpty() && secondNumber.isNotEmpty() && secondNumber.equals(0.toString())){
//                                                    secondNumber = firstRowNum.toString()
//                                                } else{
//                                                    secondNumber = secondNumber.plus(firstRowNum.toString())
//                                                }
                                            },
                                            modifier = Modifier.size(80.dp, 120.dp)
                                        ) {
                                            Text(
                                                text = firstRowNum.toString(),
                                                fontSize = 35.sp,
                                                fontWeight = FontWeight.Black
                                            )
                                        }
                                    }
                                }
                                Column(modifier = Modifier.padding(10.dp)) {
                                    Button(
                                        onClick = {
                                            if(firstNumber.isNotEmpty() and !arithmeticOperators.contains(firstNumber.last().toString())){
                                                firstNumber += "/"
                                            }
                                            //selectedOperator = "/"
                                                  },
                                        modifier = Modifier.size(80.dp, 120.dp)
                                    ) {
                                        Text(
                                            text = "/",
                                            fontSize = 35.sp,
                                            fontWeight = FontWeight.Black
                                        )
                                    }
                                }
                            }
                        }
                        HorizontalDivider(modifier = Modifier.padding(1.dp))
                        Box(modifier = Modifier.weight(2f)) {
                            Row(
                                modifier = Modifier.padding(all = 2.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Absolute.Center
                            ) {
                                for (secondRowNum in 4..6) {
                                    Column(modifier = Modifier.padding(10.dp)) {
                                        Button(
                                            onClick = {
                                                firstNumber += secondRowNum.toString()
                                                /*if (firstNumber.isNotEmpty() and firstNumber.equals(0.toString())) {
                                                    firstNumber = secondRowNum.toString()
                                                } else {
                                                    secondNumber = secondRowNum.toString()
                                                }*/
                                            },
                                            modifier = Modifier.size(80.dp, 120.dp)
                                        ) {
                                            Text(
                                                text = secondRowNum.toString(),
                                                fontSize = 35.sp,
                                                fontWeight = FontWeight.Black
                                            )
                                        }
                                    }
                                }
                                Column(modifier = Modifier.padding(10.dp)) {
                                    Button(
                                        onClick = {
                                            if(firstNumber.isNotEmpty() and !arithmeticOperators.contains(firstNumber.last().toString())){
                                                firstNumber += "*"
                                            }
                                            //selectedOperator = "*"
                                            },
                                        modifier = Modifier.size(80.dp, 120.dp)
                                    ) {
                                        Text(
                                            text = "*",
                                            fontSize = 35.sp,
                                            fontWeight = FontWeight.Black
                                        )
                                    }
                                }
                            }
                        }
                        HorizontalDivider(modifier = Modifier.padding(1.dp))
                        Box(modifier = Modifier.weight(2f)) {
                            Row(
                                modifier = Modifier.padding(all = 2.dp),
                                verticalAlignment = Alignment.Bottom,
                                horizontalArrangement = Arrangement.Absolute.Center
                            ) {
                                for (thirdRowNum in 7..9) {
                                    Column(modifier = Modifier.padding(10.dp)) {
                                        Button(
                                            onClick = {
                                                firstNumber += thirdRowNum.toString()
                                                /*if (firstNumber.isNotEmpty() and firstNumber.equals(0.toString())) {
                                                    firstNumber = thirdRowNum.toString()
                                                } else {
                                                    secondNumber = thirdRowNum.toString()
                                                }*/
                                            },
                                            modifier = Modifier.size(80.dp, 120.dp)
                                        ) {
                                            Text(
                                                text = thirdRowNum.toString(),
                                                fontSize = 35.sp,
                                                fontWeight = FontWeight.Black
                                            )
                                        }
                                    }
                                }
                                Column(modifier = Modifier.padding(10.dp)) {
                                    Button(
                                        onClick = {
                                            if(firstNumber.isNotEmpty() and !arithmeticOperators.contains(firstNumber.last().toString())){
                                                firstNumber += "-"
                                            }
                                            //selectedOperator = "-"
                                                  },
                                        modifier = Modifier.size(80.dp, 120.dp)
                                    ) {
                                        Text(
                                            text = "-",
                                            fontSize = 35.sp,
                                            fontWeight = FontWeight.Black
                                        )
                                    }
                                }
                            }
                        }
                        HorizontalDivider(modifier = Modifier.padding(1.dp))
                        Box(modifier = Modifier.weight(2f)) {
                            Row(
                                modifier = Modifier.padding(all = 2.dp),
                                verticalAlignment = Alignment.Bottom,
                                horizontalArrangement = Arrangement.Absolute.Center
                            ) {
                                Column(modifier = Modifier.padding(10.dp)) {
                                    Button(
                                        onClick = {
                                            if (firstNumber.last() == '0' && firstNumber.length == 1){
                                                firstNumber = 0.toString()
                                            } else {
                                                firstNumber += 0.toString()
                                            }
                                            /*if (firstNumber.toDouble() > 0.0 && selectedOperator.isEmpty()) {
                                                firstNumber += "0"
                                            } else if (secondNumber.toDouble() > 0.0 && selectedOperator.isNotEmpty()) {
                                                secondNumber += "0"
                                            } else {
                                                firstNumber = "0"
                                            }*/
                                        },
                                        modifier = Modifier.size(80.dp, 120.dp)
                                    ) {
                                        Text(
                                            text = "0",
                                            fontSize = 35.sp,
                                            fontWeight = FontWeight.Black
                                        )
                                    }
                                }
                                Column(modifier = Modifier.padding(10.dp)) {
                                    Button(
                                        onClick = {
                                            if(firstNumber.isNotEmpty() and !arithmeticOperators.contains(firstNumber.last().toString())){
                                                firstNumber += "+"
                                            }
                                            //selectedOperator = "+"
                                                  },
                                        modifier = Modifier.size(80.dp, 120.dp)
                                    ) {
                                        Text(
                                            text = "+",
                                            fontSize = 35.sp,
                                            fontWeight = FontWeight.Black
                                        )
                                    }
                                }
                                Column(modifier = Modifier.padding(10.dp)) {
                                    Button(
                                        onClick = {
                                            val pattern = Regex("(?<=[+\\-*/])|(?=[+\\-*/])")
                                            val numbersWithOperators = firstNumber.split(pattern)
                                            var calculatedValue = 0.0
                                            var alreadyExecutedValue = 0.0
                                            val sortedListAccBODMAS = {numbersWithOperators.find { it in arithmeticOperators }}
                                            for (number in 0..<numbersWithOperators.size){
                                                if(numbersWithOperators[number] == ""){
                                                    calculatedValue = 0.0
                                                } else if(numbersWithOperators[number].isDigitsOnly() && numbersWithOperators[number].toDouble() != alreadyExecutedValue && numbersWithOperators[number] !in arithmeticOperators ){
                                                    calculatedValue = numbersWithOperators[number].toDouble()
                                                } else if(numbersWithOperators[number] in arithmeticOperators){
                                                    if(result != "" && numbersWithOperators[number+1].isDigitsOnly() && alreadyExecutedValue != numbersWithOperators[number+1].toDouble()){
                                                        calculatedValue = when(numbersWithOperators[number]){
                                                            "+" -> (result.toDouble() + numbersWithOperators[number+1].toDouble())
                                                            "-" -> (result.toDouble() - numbersWithOperators[number+1].toDouble())
                                                            "*" -> (result.toDouble() * numbersWithOperators[number+1].toDouble())
                                                            "/" -> (result.toDouble() / numbersWithOperators[number+1].toDouble())
                                                            else -> 0.0
                                                        }
                                                        alreadyExecutedValue = numbersWithOperators[number+1].toDouble()
                                                    } else{
                                                        continue
                                                    }
                                                }
                                                result = calculatedValue.toString()
                                            }
                                            /*if (firstNumber != null && secondNumber != null && selectedOperator.isNotBlank()) {
                                                result = when (selectedOperator) {
                                                    "+" -> firstNumber.toDouble() + secondNumber.toDouble()
                                                    "-" -> firstNumber.toDouble() - secondNumber.toDouble()
                                                    "*" -> firstNumber.toDouble() * secondNumber.toDouble()
                                                    "/" -> if (firstNumber.toDouble() > 0 || secondNumber.toDouble() > 0) {
                                                        firstNumber.toDouble() / secondNumber.toDouble()
                                                    } else {
                                                       ""
                                                    }
                                                    else -> 0.toString()
                                                } as String
                                            }*/
                                        },
                                        modifier = Modifier.size(80.dp, 120.dp)
                                    ) {
                                        Text(
                                            text = "=",
                                            fontSize = 35.sp,
                                            fontWeight = FontWeight.Black
                                        )
                                    }
                                }
                                Column(modifier = Modifier.padding(10.dp)) {
                                    Button(
                                        onClick = {
                                            firstNumber = "";
                                            secondNumber = "";
                                            selectedOperator = "";
                                            expressionRow = "";
                                            result = ""
                                        },
                                        modifier = Modifier.size(80.dp, 120.dp)
                                    ) {
                                        Text(
                                            text = "C",
                                            fontSize = 35.sp,
                                            fontWeight = FontWeight.Black
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
