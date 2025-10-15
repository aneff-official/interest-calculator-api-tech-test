package com.lhv.interestcalculator

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
class InterestCalculatorIntegrationTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `verify SIMPLE interest endpoint returns correct response`() {
        mockMvc
            .perform(
                MockMvcRequestBuilders
                    .get("/interest/calculate")
                    .param("amount", "1000")
                    .param("interestRate", "5")
                    .param("duration", "3")
                    .param("accrualType", "SIMPLE")
                    .contentType(MediaType.APPLICATION_JSON),
            ).andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.startingAmount").value(1000.0))
            .andExpect(MockMvcResultMatchers.jsonPath("$.interestAccrued").value(150.0))
            .andExpect(MockMvcResultMatchers.jsonPath("$.finalBalance").value(1150.0))
    }

    @Test
    fun `verify compound interest endpoint returns correct response`() {
        mockMvc
            .perform(
                MockMvcRequestBuilders
                    .get("/interest/calculate")
                    .param("amount", "1000")
                    .param("interestRate", "5")
                    .param("duration", "3")
                    .param("accrualType", "COMPOUND")
                    .contentType(MediaType.APPLICATION_JSON),
            ).andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.startingAmount").value(1000.0))
            .andExpect(MockMvcResultMatchers.jsonPath("$.interestAccrued").value(157.63))
            .andExpect(MockMvcResultMatchers.jsonPath("$.finalBalance").value(1157.63))
    }

    @Test
    fun `verify API returns bad request for negative amount`() {
        mockMvc
            .perform(
                MockMvcRequestBuilders
                    .get("/interest/calculate")
                    .param("amount", "-1000")
                    .param("interestRate", "5")
                    .param("duration", "3")
                    .param("accrualType", "SIMPLE")
                    .contentType(MediaType.APPLICATION_JSON),
            ).andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.content().string("All parameters must be positive numbers and not zero."))
    }

    @Test
    fun `verify API returns bad request for zero duration`() {
        mockMvc
            .perform(
                MockMvcRequestBuilders
                    .get("/interest/calculate")
                    .param("amount", "1000")
                    .param("interestRate", "5")
                    .param("duration", "0")
                    .param("accrualType", "SIMPLE")
                    .contentType(MediaType.APPLICATION_JSON),
            ).andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.content().string("All parameters must be positive numbers and not zero."))
    }
}
