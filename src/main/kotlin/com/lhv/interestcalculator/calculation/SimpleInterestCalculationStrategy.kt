package com.lhv.interestcalculator.calculation

import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.math.RoundingMode

@Component("simpleInterestCalculationStrategy")
class SimpleInterestCalculationStrategy : InterestCalculationStrategy {
    override fun calculate(
        amount: BigDecimal,
        interestRate: BigDecimal,
        duration: Int,
    ): BigDecimal =
        amount
            .multiply(interestRate.divide(BigDecimal(100)))
            .multiply(BigDecimal(duration))
            .setScale(2, RoundingMode.HALF_UP)
}
