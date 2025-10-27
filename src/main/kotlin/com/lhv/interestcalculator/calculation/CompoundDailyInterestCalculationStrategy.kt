package com.lhv.interestcalculator.calculation

import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.math.RoundingMode

@Component("compoundDailyInterestCalculationStrategy")
class CompoundDailyInterestCalculationStrategy : InterestCalculationStrategy {
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
