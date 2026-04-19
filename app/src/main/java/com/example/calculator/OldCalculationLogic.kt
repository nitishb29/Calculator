package com.example.calculator

import java.math.BigDecimal
import java.math.RoundingMode

class OldCalculationLogic {
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
}