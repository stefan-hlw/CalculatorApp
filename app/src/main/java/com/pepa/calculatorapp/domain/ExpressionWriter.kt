package com.pepa.calculatorapp.domain

class ExpressionWriter {

    var expression = EMPTY_STRING

    fun processAction(action: CalculatorAction) {
        when(action) {

            CalculatorAction.Calculate -> {
                val parser = ExpressionParser(prepareForCalculation())
                val evaluator = ExpressionEvaluator(parser.parse())
                expression = evaluator.evaluate().toString()
            }

            CalculatorAction.Clear -> {
                expression = EMPTY_STRING
            }

            CalculatorAction.Decimal -> {
                if(isDecimalAllowed()) {
                    expression += PERIOD
                }
            }

            CalculatorAction.Delete -> {
                expression.dropLast(1)
            }

            is CalculatorAction.Number -> {
                expression += action.number
            }

            is CalculatorAction.Op -> {
                if(isOperationAllowed(action.operation)) {
                    expression += action.operation.symbol
                }
            }

            CalculatorAction.Parentheses -> {
                processParentheses()
            }

        }

    }

    private fun prepareForCalculation(): String {
        val newExpression = expression.takeLastWhile {
            it in "$operationSymbols($PERIOD"
        }
        if(newExpression.isEmpty()) {
            return ZERO
        }
        return newExpression
    }

    private fun processParentheses() {
        val openingCount = expression.count { it == '(' }
        val closingCount = expression.count { it == ')' }
        expression += when {
            expression.isEmpty() || expression.last() in "$operationSymbols(" -> "("
            expression.last() in "$DIGITS)" && openingCount == closingCount -> return
            else -> ")"
        }
    }

    private fun isDecimalAllowed(): Boolean {
        if(expression.isEmpty() || expression.last() in "$operationSymbols$PERIOD()") {
            return false
        }
        return !expression.takeLastWhile {
            it in "$DIGITS$PERIOD"
        }.contains(PERIOD)
    }

    private fun isOperationAllowed(operation: Operation): Boolean {

        if(operation in listOf(Operation.ADD, Operation.SUBTRACT)) {
            return expression.isEmpty() || expression.last() in "$operationSymbols()$DIGITS"
        }

        // Expressions * and / are allowed after closing parentheses but not after opening
        // ie. (2+9)/3 is fine, but (/2+9) is not fine
        return expression.isNotEmpty() || expression.last() in "$DIGITS)"

    }

    companion object {
        const val ZERO = "0"
        const val PERIOD = "."
        const val EMPTY_STRING = ""
        const val DIGITS = "0123456789"
    }

}