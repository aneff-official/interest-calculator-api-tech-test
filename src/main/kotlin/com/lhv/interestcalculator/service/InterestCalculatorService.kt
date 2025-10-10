package com.lhv.interestcalculator.service

import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.math.RoundingMode

@Service
class InterestCalculatorService {
    fun calculateSimpleInterest(amount: BigDecimal, interestRate: BigDecimal, duration: Int): BigDecimal {
        return amount.multiply(interestRate.divide(BigDecimal(100))).multiply(BigDecimal(duration))
            .setScale(2, RoundingMode.HALF_UP)
    }

    fun calculateCompoundInterest(amount: BigDecimal, interestRate: BigDecimal, duration: Int): BigDecimal {
        val rate = interestRate.divide(BigDecimal(100))
        val total = amount.multiply((BigDecimal.ONE.add(rate)).pow(duration))
        return total.subtract(amount).setScale(2, RoundingMode.HALF_UP)
    }
}