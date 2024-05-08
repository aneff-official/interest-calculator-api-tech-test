package com.lhv.interestcalculator.api

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal
import java.util.*

@RestController
class InterestCalculatorController {
    @GetMapping("/interest/calculate")
    fun calculateInterest(
        @RequestParam("amount") amount: BigDecimal,
        @RequestParam("interestRate") interestRate: BigDecimal,
        @RequestParam("duration") duration: Int,
        @RequestParam("accrualType") accrualType: String
    ): ResponseEntity<Any> {
        if (amount <= BigDecimal.ZERO || interestRate <= BigDecimal.ZERO || duration <= 0) {
            return ResponseEntity.badRequest().body("All parameters must be positive numbers and not zero.")
        }

        val interest = when (accrualType.lowercase(Locale.getDefault())) {
            "simple" -> calculateSimpleInterest(amount, interestRate, duration)
            "compound" -> calculateCompoundInterest(amount, interestRate, duration)
            else -> return ResponseEntity.badRequest().body("Invalid accrual type. Use 'simple' or 'compound'.")
        }

        return ResponseEntity.ok(interest)
    }

    private fun calculateSimpleInterest(amount: BigDecimal, interestRate: BigDecimal, duration: Int): BigDecimal {
        return amount * interestRate / BigDecimal(100) * BigDecimal(duration)
    }

    private fun calculateCompoundInterest(amount: BigDecimal, interestRate: BigDecimal, duration: Int): BigDecimal {
        return amount * (BigDecimal.ONE + interestRate / BigDecimal(100)).pow(duration) - amount
    }
}