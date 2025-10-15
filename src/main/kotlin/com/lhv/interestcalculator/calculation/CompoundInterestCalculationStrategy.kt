package com.lhv.interestcalculator.calculation

import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.math.RoundingMode

@Component("compoundInterestCalculationStrategy")
class CompoundInterestCalculationStrategy : InterestCalculationStrategy {
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
