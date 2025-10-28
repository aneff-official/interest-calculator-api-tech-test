package com.lhv.interestcalculator.model

import kotlinx.serialization.Serializable

@Serializable
data class ErrorHandler(
    val code: Int,
    val message: String,
    val errors: List<ErrorItem> = emptyList(),
)

@Serializable
data class ErrorItem(
    val property: String,
    val message: String,
)
