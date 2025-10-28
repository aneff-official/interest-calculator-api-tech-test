package com.lhv.interestcalculator.api

import com.lhv.interestcalculator.model.AccrualType
import com.lhv.interestcalculator.model.CalculationResponse
import com.lhv.interestcalculator.model.ErrorHandler
import com.lhv.interestcalculator.model.ErrorItem
import com.lhv.interestcalculator.model.isValidType
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
        val errors = mutableListOf<ErrorItem>()
        val maxYears = 100
        val invalidAmount = amount <= BigDecimal.ZERO
        val invalidInterest = interestRate <= BigDecimal.ZERO || interestRate > BigDecimal(100)
        val invalidDuration = duration !in 1..maxYears
        val invalidType = !isValidType(accrualType.toString())

        if (invalidAmount || invalidInterest || invalidDuration || invalidType) {
            if (invalidAmount) {
                errors.add(ErrorItem("amount", "Amount must be greater than zero."))
            }

            if (invalidInterest) {
                errors.add(ErrorItem("interestRate", "Interest rate must be between 0 and 100 percent."))
            }

            if (invalidDuration) {
                errors.add(ErrorItem("duration", "Duration must be at least 1 year and a sensible number."))
            }

            // Seems invalid types do not reach this
            if (invalidType) {
                errors.add(ErrorItem("accrualType", "Type is not valid."))
            }

            // "All parameters must be positive numbers and not zero."
            val response = ErrorHandler(400, "Validation Error", errors)
            return ResponseEntity.badRequest().body(response)
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
