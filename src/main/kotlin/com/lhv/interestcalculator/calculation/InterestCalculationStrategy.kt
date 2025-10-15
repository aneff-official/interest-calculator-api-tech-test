package com.lhv.interestcalculator.calculation

import java.math.BigDecimal

interface InterestCalculationStrategy {
    fun calculate(
        amount: BigDecimal,
        interestRate: BigDecimal,
        duration: Int,
    ): BigDecimal
}
