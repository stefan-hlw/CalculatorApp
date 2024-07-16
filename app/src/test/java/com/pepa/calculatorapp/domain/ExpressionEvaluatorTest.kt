package com.pepa.calculatorapp.domain


import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class ExpressionEvaluatorTest {

    private lateinit var evaluator: ExpressionEvaluator

    @Test
    fun `Expression properly evaluated`() {
        evaluator = ExpressionEvaluator(
            listOf(
                ExpressionPart.Number(3.0),
                ExpressionPart.Op(Operation.ADD),
                ExpressionPart.Number(5.0),
                ExpressionPart.Op(Operation.SUBTRACT),
                ExpressionPart.Number(3.0),
                ExpressionPart.Op(Operation.MULTIPLY),
                ExpressionPart.Number(4.0),
                ExpressionPart.Op(Operation.DIVIDE),
                ExpressionPart.Number(3.0),
            )
        )

        val actualResult = evaluator.evaluate()
        val expectedResult = 4.0

        expectThat(actualResult).isEqualTo(expectedResult)

    }

    @Test
    fun `Expression containing decimals properly evaluated`() {
        evaluator = ExpressionEvaluator(
            listOf(
                ExpressionPart.Number(3.2),
                ExpressionPart.Op(Operation.ADD),
                ExpressionPart.Number(5.8),
                ExpressionPart.Op(Operation.SUBTRACT),
                ExpressionPart.Number(3.0),
                ExpressionPart.Op(Operation.MULTIPLY),
                ExpressionPart.Number(2.1),
                ExpressionPart.Op(Operation.DIVIDE),
                ExpressionPart.Number(3.0),
            )
        )

        val actualResult = evaluator.evaluate()
        val expectedResult = 6.9

        expectThat(actualResult).isEqualTo(expectedResult)

    }

    @Test
    fun `Expression containing parentheses properly evaluated`() {
        evaluator = ExpressionEvaluator(
            listOf(
                ExpressionPart.Number(3.0),
                ExpressionPart.Op(Operation.ADD),
                ExpressionPart.Parentheses(ParenthesesType.Opening),
                ExpressionPart.Number(5.0),
                ExpressionPart.Op(Operation.SUBTRACT),
                ExpressionPart.Number(3.0),
                ExpressionPart.Parentheses(ParenthesesType.Closing),
                ExpressionPart.Op(Operation.MULTIPLY),
                ExpressionPart.Number(2.0),
                ExpressionPart.Op(Operation.ADD),
                ExpressionPart.Number(3.0),
            )
        )

        val actualResult = evaluator.evaluate()
        val expectedResult = 10.0

        expectThat(actualResult).isEqualTo(expectedResult)

    }


}
