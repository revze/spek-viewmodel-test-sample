package com.example.belajarspek

import com.example.belajarspek.external.helper.Calculator
import junit.framework.Assert.assertEquals
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature

@RunWith(JUnitPlatform::class)
object CalculatorTest : Spek({
    Feature("calculator") {
        var number1 = 0
        var number2 = 0
        var result = 0
        var expectedResult = 0
        lateinit var calculator: Calculator

        beforeEachScenario {
            calculator = Calculator()
        }

        Scenario("adding numbers") {
            Given("Initial value (10) and (20)") {
                number1 = 20
                number2 = 10
                expectedResult = 30
            }

            When("add number1 (10) and number2 (20)") {
                result = calculator.add(number1, number2)
            }

            Then("should return correct number (30)") {
                assertEquals(expectedResult, result)
            }
        }

        Scenario("subtract numbers") {
            Given("Initial value (30) and (10)") {
                number1 = 30
                number2 = 10
                expectedResult = 20
            }

            When("subtract number1 (30) and number2 (10)") {
                result = calculator.subtract(number1, number2)
            }

            Then("should return correct number (20)") {
                assertEquals(expectedResult, result)
            }
        }
    }
})