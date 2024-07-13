package com.pepa.calculatorapp.domain

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class ExpressionParserTest {

    private lateinit var parser: ExpressionParser

    @Test
    fun `Expression is properly parsed`() {
        parser = ExpressionParser("3+5-3x4/3")

        val actualExpressionParts = parser.parse()

        val expectedExpressionParts = listOf(
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

        assertThat(expectedExpressionParts).isEqualTo(actualExpressionParts)
    }

    @Test
    fun `Expression containing decimals is properly parsed`() {
        parser = ExpressionParser("6.3+2.1x1.8")

        val actualExpressionParts = parser.parse()

        val expectedExpressionParts = listOf(
            ExpressionPart.Number(6.3),
            ExpressionPart.Op(Operation.ADD),
            ExpressionPart.Number(2.1),
            ExpressionPart.Op(Operation.MULTIPLY),
            ExpressionPart.Number(1.8),
        )

        assertThat(expectedExpressionParts).isEqualTo(actualExpressionParts)
    }

    @Test
    fun `Expression containing parentheses is properly parsed`() {
        parser = ExpressionParser("6x(2+1)")

        val actualExpressionParts = parser.parse()

        val expectedExpressionParts = listOf(
            ExpressionPart.Number(6.0),
            ExpressionPart.Op(Operation.MULTIPLY),
            ExpressionPart.Parentheses(ParenthesesType.Opening),
            ExpressionPart.Number(2.0),
            ExpressionPart.Op(Operation.ADD),
            ExpressionPart.Number(1.0),
            ExpressionPart.Parentheses(ParenthesesType.Closing),
        )

        assertThat(expectedExpressionParts).isEqualTo(actualExpressionParts)
    }
}
