package com.lhv.interestcalculator

import com.lhv.interestcalculator.api.InterestCalculatorController
import com.lhv.interestcalculator.service.InterestCalculatorService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class InterestCalculationServiceTest {

    private val service = InterestCalculatorService()

    @Test
    fun `test simple interest calculation`() {
        val amount = BigDecimal("1000")
        val interestRate = BigDecimal("5")
        val duration = 3
        val expectedInterest = BigDecimal("150.00")

        val calculation = service.calculateSimpleInterest(amount, interestRate, duration).setScale(2)

        assertEquals(expectedInterest, calculation)
    }

    @Test
    fun `test compound interest calculation`() {
        val amount = BigDecimal("1000")
        val interestRate = BigDecimal("5")
        val duration = 3
        val expectedInterest = BigDecimal("157.63")

        val calculation = service.calculateCompoundInterest(amount, interestRate, duration)

        assertEquals(expectedInterest.setScale(2), calculation.setScale(2))
    }
}
