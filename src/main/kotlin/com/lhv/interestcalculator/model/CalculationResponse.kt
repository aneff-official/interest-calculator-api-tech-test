package com.lhv.interestcalculator.model

import java.math.BigDecimal

data class CalculationResponse(
    val startingAmount: BigDecimal,
    val interestAccrued: BigDecimal,
    val finalBalance: BigDecimal,
)
