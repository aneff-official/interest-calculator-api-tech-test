package com.lhv.interestcalculator

import com.lhv.interestcalculator.api.InterestCalculatorController
import com.lhv.interestcalculator.service.InterestCalculatorService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(InterestCalculatorController::class, InterestCalculatorService::class)
class InterestCalculatorIntegrationTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `verify SIMPLE interest endpoint returns correct response`() {
        mockMvc.perform(
            get("/interest/calculate")
                .param("amount", "1000")
                .param("interestRate", "5")
                .param("duration", "3")
                .param("accrualType", "SIMPLE")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.startingAmount").value("1000.0"))
            .andExpect(jsonPath("$.interestAccrued").value("150.0"))
            .andExpect(jsonPath("$.finalBalance").value("1150.0"))
    }

    @Test
    fun `verify compound interest endpoint returns correct response`() {
        mockMvc.perform(
            get("/interest/calculate")
                .param("amount", "1000")
                .param("interestRate", "5")
                .param("duration", "3")
                .param("accrualType", "COMPOUND")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.startingAmount").value("1000.0"))
            .andExpect(jsonPath("$.interestAccrued").value("157.63"))
            .andExpect(jsonPath("$.finalBalance").value("1157.63"))
    }

    @Test
    fun `verify API returns bad request for negative amount`() {
        mockMvc.perform(get("/interest/calculate")
            .param("amount", "-1000")
            .param("interestRate", "5")
            .param("duration", "3")
            .param("accrualType", "SIMPLE")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest)
            .andExpect(content().string("All parameters must be positive numbers and not zero."))
    }

    @Test
    fun `verify API returns bad request for zero duration`() {
        mockMvc.perform(get("/interest/calculate")
            .param("amount", "1000")
            .param("interestRate", "5")
            .param("duration", "0")
            .param("accrualType", "SIMPLE")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest)
            .andExpect(content().string("All parameters must be positive numbers and not zero."))
    }
}
