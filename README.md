# Interest Calculator Technical Test

## Techincal Test

### Background

You are provided with an API that calculates either simple or compound interest based on input parameters such as the
principal amount, interest rate, duration, and type of interest accrual. The API also performs input validation to
ensure all provided parameters are positive and the accrual type is either "simple" or "compound".

### Task

Your task is to write automated functional tests to verify the functionality and robustness of this Interest Calculator
API. Your tests should cover various scenarios including positive cases, negative cases, and edge cases. Feel free to
use any tooling of your choice to demonstrate how you would approach writing automation tests for this application.

### Deliverables

- A git bundle that contains all your automated tests
- A readme detailing
  - How to set up and run your tests
  - A brief explanation of your testing strategy and tools used
  - Any findings from the testing which you would feedback to developers

---

## Overview

This Spring Boot application provides an API to calculate interest based on user input. It is designed to handle both
simple and compound interest calculations using a set of given parameters. The application is built using Kotlin and
leverages the Spring Web dependency for handling HTTP requests.

## Features

- **Interest Calculation**: Users can calculate either simple or compound interest based on input parameters.
- **Input Validation**: Ensures all input values are positive and valid before processing.
- **Detailed Output**: The API returns a detailed breakdown including the starting amount, interest accrued, and the
  final balance.

## API Endpoint

**URL**: `/interest/calculation`

**Method**: `GET`

**Query Parameters**:

- `amount` (BigDecimal): The principal amount.
- `interestRate` (BigDecimal): The annual interest rate (in percentage).
- `duration` (Integer): The duration of the investment/debt in years.
- `accrualType` (String): The type of interest calculation (`simple` or `compound`).

**Responses**:

- **200 OK**: Returns the calculation results.
- **400 Bad Request**: Returns an error message if input validation fails.

## Validation

The API performs the following validations:

- All numeric parameters (`amount`, `interestRate`, and `duration`) must be positive values greater than zero.
- The `accrualType` must be either `simple` or `compound`. Any other values will result in an error.

## Interest Calculation

### Simple Interest

Simple interest is calculated using the formula:

**Interest = Principal x (Interest Rate / 100) x Time**

Where:

- **Principal** is the initial amount of money.
- **Interest Rate** is the annual interest rate in percentage.
- **Time** is the duration the money is invested or borrowed for, in years.

**Example**:

- Amount: £1000
- Interest Rate: 5%
- Duration: 3 years

**Calculation**:

- Interest = 1000 x (5 / 100) x 3 = £150

### Compound Interest

Compound interest is calculated using the formula:

**Total = Principal x (1 + Interest Rate / 100)^Time**

**Interest Earned = Total - Principal**

Where:

- **Principal** is the initial amount of money.
- **Interest Rate** is the annual interest rate in percentage.
- **Time** is the duration the money is compounded for, in years.

**Example**:

- Amount: £1000
- Interest Rate: 5%
- Duration: 3 years

**Calculation**:

- Total = 1000 x (1 + 5 / 100)^3 ≈ £1157.63
- Interest Earned = £1157.63 - £1000 = £157.63

## Running the Application

Ensure you have Gradle or Maven installed, and run the following command in the root directory of the project:

```bash
./gradlew bootRun  # For Gradle users
```

## Testing

You can test the API using tools like Postman or CURL by making requests to `http://localhost:8080/interest/calculate`
with the required query parameters.

