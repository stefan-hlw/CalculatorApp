package com.pepa.calculatorapp.domain

import org.junit.Before
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class ExpressionWriterTest {

    private lateinit var writer: ExpressionWriter

    @Before
    fun setup() {
        writer = ExpressionWriter()
    }

    @Test
    fun `Initial parentheses parsed`() {
        writer.processAction(CalculatorAction.Parentheses)
        writer.processAction(CalculatorAction.Number(3))
        writer.processAction(CalculatorAction.Op(Operation.SUBTRACT))
        writer.processAction(CalculatorAction.Number(2))
        writer.processAction(CalculatorAction.Parentheses)

        val expectedResult = "(3-2)"
        val actualResult = writer.expression

        expectThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun `Multiple parentheses at start of an expression cannot be of type closing`() {
        writer.processAction(CalculatorAction.Parentheses)
        writer.processAction(CalculatorAction.Parentheses)

        val expectedResult = "(("
        val actualResult = writer.expression

        expectThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun `Parentheses surrounding a single number are parsed`() {
        writer.processAction(CalculatorAction.Parentheses)
        writer.processAction(CalculatorAction.Number(-3))
        writer.processAction(CalculatorAction.Parentheses)

        val expectedResult = "(-3)"
        val actualResult = writer.expression

        expectThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun `Expression is empty after using clear`() {
        writer.processAction(CalculatorAction.Parentheses)
        writer.processAction(CalculatorAction.Number(3))
        writer.processAction(CalculatorAction.Op(Operation.SUBTRACT))
        writer.processAction(CalculatorAction.Number(2))
        writer.processAction(CalculatorAction.Parentheses)
        writer.processAction(CalculatorAction.Clear)

        val expectedResult = ""
        val actualResult = writer.expression

        expectThat(actualResult).isEqualTo(expectedResult)
    }

}