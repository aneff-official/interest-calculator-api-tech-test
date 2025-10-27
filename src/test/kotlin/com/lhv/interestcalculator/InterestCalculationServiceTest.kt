package com.lhv.interestcalculator

import com.lhv.interestcalculator.calculation.InterestCalculationStrategy
import com.lhv.interestcalculator.model.AccrualType
import com.lhv.interestcalculator.service.InterestCalculatorService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.math.RoundingMode

class InterestCalculationServiceTest {
    // Test implementations of the strategies
    private val simpleStrategy =
        object : InterestCalculationStrategy {
            override fun calculate(
                amount: BigDecimal,
                interestRate: BigDecimal,
                duration: Int,
            ): BigDecimal =
                amount
                    .multiply(
                        interestRate.divide(BigDecimal(100)),
                    ).multiply(BigDecimal(duration))
                    .setScale(2, RoundingMode.HALF_UP)
        }

    private val compoundStrategy =
        object : InterestCalculationStrategy {
            override fun calculate(
                amount: BigDecimal,
                interestRate: BigDecimal,
                duration: Int,
            ): BigDecimal {
                val rate = interestRate.divide(BigDecimal(100))
                val total = amount.multiply((BigDecimal.ONE.add(rate)).pow(duration))
                return total.subtract(amount).setScale(2, RoundingMode.HALF_UP)
            }
        }

    private val compoundDailyStrategy =
        object : InterestCalculationStrategy {
            override fun calculate(
                amount: BigDecimal,
                interestRate: BigDecimal,
                duration: Int,
            ): BigDecimal {
                val daysInYear = 365
                val rate = interestRate.divide(BigDecimal(100), 2, RoundingMode.HALF_UP)

                // We lose precision with scale 10, however error occurs if not handled
                val ratePerDay = rate.divide(BigDecimal(daysInYear), 10, RoundingMode.HALF_UP)
                val total = amount.multiply((BigDecimal.ONE.add(ratePerDay)).pow(daysInYear.times(duration)))

                return total.subtract(amount).setScale(2, RoundingMode.HALF_UP)
            }
        }

    private val service =
        InterestCalculatorService(
            simpleStrategy,
            compoundStrategy,
            compoundDailyStrategy,
        )

    @Test
    fun `test simple interest calculation`() {
        val amount = BigDecimal("1000")
        val interestRate = BigDecimal("5")
        val duration = 3
        val expectedInterest = BigDecimal("150.00")

        val calculation = service.calculate(amount, interestRate, duration, AccrualType.SIMPLE).setScale(2)

        assertEquals(expectedInterest, calculation)
    }

    @Test
    fun `test compound interest calculation`() {
        val amount = BigDecimal("1000")
        val interestRate = BigDecimal("5")
        val duration = 3
        val expectedInterest = BigDecimal("157.63")

        val calculation = service.calculate(amount, interestRate, duration, AccrualType.COMPOUND)

        assertEquals(expectedInterest.setScale(2), calculation.setScale(2))
    }

    @Test
    fun `test compound daily interest calculation`() {
        val amount = BigDecimal("1000")
        val interestRate = BigDecimal("5")
        val duration = 3
        val expectedInterest = BigDecimal("161.82")

        val calculation = service.calculate(amount, interestRate, duration, AccrualType.DAILY)

        assertEquals(expectedInterest.setScale(2), calculation.setScale(2))
    }
}
