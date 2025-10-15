package com.lhv.interestcalculator.service

import com.lhv.interestcalculator.calculation.InterestCalculationStrategy
import com.lhv.interestcalculator.model.AccrualType
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class InterestCalculatorService(
    @param:Qualifier("simpleInterestCalculationStrategy")
    private val simpleInterestCalculationStrategy: InterestCalculationStrategy,
    @param:Qualifier("compoundInterestCalculationStrategy")
    private val compoundInterestCalculationStrategy: InterestCalculationStrategy,
) {
    fun calculate(
        amount: BigDecimal,
        interestRate: BigDecimal,
        duration: Int,
        accrualType: AccrualType,
    ): BigDecimal {
        val strategy =
            when (accrualType) {
                AccrualType.SIMPLE -> simpleInterestCalculationStrategy
                AccrualType.COMPOUND -> compoundInterestCalculationStrategy
            }
        return strategy.calculate(amount, interestRate, duration)
    }
}
