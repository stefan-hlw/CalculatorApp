package com.pepa.calculatorapp.domain

class ExpressionEvaluator(
    private val expression: List<ExpressionPart>
) {
    fun evaluate(): Double {
        return evaluateExpression(expression).value
    }

    private fun evaluateTerm(expression: List<ExpressionPart>): ExpressionResult {
        val result = evaluateFactor(expression)
        var remaining = result.remainingExpression
        var sum = result.value

        while(true) {
            when(remaining.firstOrNull()) {
                ExpressionPart.Op(Operation.MULTIPLY) -> {
                    val factor = evaluateFactor(remaining.drop(1))
                    sum *= factor.value
                    remaining = factor.remainingExpression
                }

                ExpressionPart.Op(Operation.DIVIDE) -> {
                    val factor = evaluateFactor(remaining.drop(1))
                    sum /= factor.value
                    remaining = factor.remainingExpression
                }

                ExpressionPart.Op(Operation.PERCENT) -> {
                    val factor = evaluateFactor(remaining.drop(1))
                    sum *= factor.value / 100.0
                    remaining = factor.remainingExpression
                }

                else -> return ExpressionResult(remaining, sum)

            }
        }
    }

    private fun evaluateExpression(expression: List<ExpressionPart>): ExpressionResult {
        val result = evaluateTerm(expression)
        var remaining = result.remainingExpression
        var sum = result.value

        while(true) {
            when(remaining.firstOrNull()) {
                ExpressionPart.Op(Operation.ADD) -> {
                    val term = evaluateTerm(remaining.drop(1))
                    sum += term.value
                    remaining = term.remainingExpression
                }

                ExpressionPart.Op(Operation.SUBTRACT) -> {
                    val term = evaluateTerm(remaining.drop(1))
                    sum -= term.value
                    remaining = term.remainingExpression
                }

                else -> return ExpressionResult(remaining, sum)

            }
        }
    }


    private fun evaluateFactor(expression: List<ExpressionPart>): ExpressionResult {
        return when(val part = expression.firstOrNull()) {
            ExpressionPart.Op(Operation.ADD) -> {
                evaluateFactor(expression.drop(1))
            }
            ExpressionPart.Op(Operation.SUBTRACT) -> {
                evaluateFactor(expression.drop(1)).run {
                    ExpressionResult(remainingExpression, -value)
                }
            }

            ExpressionPart.Op(Operation.PERCENT) -> {
                evaluateTerm(expression.drop(1))
            }

            ExpressionPart.Parentheses(ParenthesesType.Opening) -> {
                evaluateExpression(expression.drop(1)).run {
                    ExpressionResult(remainingExpression.drop(1), value)
                }
            }
            ExpressionPart.Parentheses(ParenthesesType.Closing) -> {
                evaluateExpression(expression.drop(1)).run {
                    ExpressionResult(remainingExpression.drop(1), value)
                }
            }

            is ExpressionPart.Number -> ExpressionResult(
                remainingExpression = expression.drop(1),
                value = part.number
                )

            else -> throw RuntimeException("Invalid Expression")
        }
    }

    data class ExpressionResult(
        val remainingExpression: List<ExpressionPart>,
        val value: Double
    )
}
