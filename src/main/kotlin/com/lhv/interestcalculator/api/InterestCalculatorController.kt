package com.lhv.interestcalculator.api

import com.lhv.interestcalculator.model.AccrualType
import com.lhv.interestcalculator.model.CalculationResponse
import com.lhv.interestcalculator.service.InterestCalculatorService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal

@RestController
class InterestCalculatorController(
    val interestCalculator: InterestCalculatorService,
) {
    @GetMapping("/interest/calculate")
    fun calculateInterest(
        @RequestParam("amount") amount: BigDecimal,
        @RequestParam("interestRate") interestRate: BigDecimal,
        @RequestParam("duration") duration: Int,
        @RequestParam("accrualType") accrualType: AccrualType,
    ): ResponseEntity<Any> {
        // Could possibly split into more specific errors
        if (amount <= BigDecimal.ZERO || interestRate <= BigDecimal.ZERO || duration <= 0) {
            return ResponseEntity.badRequest().body("All parameters must be positive numbers and not zero.")
        }

        val interest = interestCalculator.calculate(amount, interestRate, duration, accrualType).setScale(2)

        return ResponseEntity.ok(
            CalculationResponse(
                amount.setScale(2),
                interest.setScale(2),
                (amount + interest).setScale(2),
            ),
        )
    }
}
