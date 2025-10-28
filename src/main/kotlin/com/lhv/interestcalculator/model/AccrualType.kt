package com.lhv.interestcalculator.model

enum class AccrualType {
    SIMPLE,
    COMPOUND,
    DAILY,
}

fun isValidType(value: String): Boolean =
    try {
        AccrualType.valueOf(value.uppercase())
        true
    } catch (e: IllegalArgumentException) {
        false
    }
