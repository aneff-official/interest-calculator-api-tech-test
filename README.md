# Interest Calculator Technical Test

## Technical Test

### Background

You are provided with an API that calculates either simple or compound interest based on input parameters such as the
principal amount, interest rate, duration, and type of interest accrual. The API also performs input validation to
ensure all provided parameters are positive and the accrual type is either "simple" or "compound".

### Task

Your task is to write automated functional tests to verify the functionality and robustness of this Interest Calculator
API. Your tests should cover various scenarios including positive cases, negative cases, and edge cases. Feel free to
use any tooling of your choice to demonstrate how you would approach writing automation tests for this application. You
should spend no longer than 4 hours on this task and we would rather you complete less functionality to a good standard
than rushing to try to complete it all. Show us your workings by initialising a git repo and committing your changes as
you go. Your submission should be considered the quality you would expect to open a PR, not to merge one. We don't
expect the submission to be production standard or gold plated. The submission will be followed up with a review call
where we will ask you to walk us through your solution. We will then ask you to extend it with some additional
functionality.

### Deliverables

- A git bundle that contains all your automated tests. This can be done by:

```bash
git bundle create name.bundle --all 
```

- README.md in the git bundle detailing
    - How to set up and run your tests
    - A brief explanation of your testing strategy and tools used
    - Any findings from the testing which you would feedback to developers
- Submit your sample git bundle emailing it to us (via your recruiter if you have one)

---

## Candidate Task Tracks

This repository can be used for two related evaluation tracks. Pick ONE primary track (or a balanced subset of both if explicitly asked). Timebox yourself (suggested 2–4 hours total). Prioritise depth & quality over breadth.

### 1. SEIT / Test Automation Focus
Core (aim to complete first):
1. Happy Path Coverage: Write automated tests (integration/functional) verifying correct responses for both simple and compound interest calculations with representative inputs.
2. Validation Matrix: Table‑driven tests ensuring each invalid parameter (missing, zero, negative, unsupported accrualType) yields a 400 with an explanatory message.
3. Edge Cases: Very large principal, high interest rate (e.g. 99%), fractional interest rate (e.g. 0.125), multi‑year duration boundary (1 vs large value) confirming precision and no overflow/rounding surprises.
4. Deterministic Precision: Assert numeric values to a suitable scale (e.g. 2 or 4 decimal places) rather than raw string equality.

Enhancements (choose one or two if time remains):
- Property‑Based Tests: Randomly generate valid triples (amount, rate, duration) asserting invariants (interest >= 0; compound >= simple for same inputs when duration > 1; linearity of simple interest).
- Negative Fuzzing: Random invalid inputs (empty, non‑numeric, extremely large) asserting graceful failures.
- Contract / Schema Guard: Capture a JSON schema (or snapshot) for the response and fail tests on structural drift.
- Performance Smoke: Small load (e.g. 50 RPS for 30s) capturing p95 latency & error rate; produce a short summary.
- Mutation Testing (e.g. PIT): Demonstrate mutation score and highlight any surviving mutants with remediation suggestions.

Expected SEIT Deliverables:
- Automated test suite runnable via a single command.
- Short testing strategy summary (could be in your bundle README) explaining pyramid, tools, and rationale.
- Findings list: potential improvements or edge behaviours discovered.

### 2. Backend Engineering Focus
If instructed to extend functionality instead of (or in addition to) writing tests, implement small, well‑scoped improvements with accompanying tests. Suggested progression:

Core Extension Options (pick 1–2):
1. New Accrual Type: Add daily compound interest (365 compounding periods). Include unit + integration tests.
2. Schedule Endpoint: Add `GET /interest/schedule` returning yearly (or per period) breakdown: year, startingAmount, interestEarned, endAmount.
3. Error Model Improvement: Standardise validation errors into a structured JSON object with fields like `code`, `message`, `errors[]`.

Optional Enhancements (choose if time):
- Rounding Strategy: Configurable rounding mode via application properties.
- Currency Support: Accept a `currency` parameter (default GBP) and include it in responses (no FX logic required).
- Caching: Cache identical calculation requests (in‑memory) with a short TTL.
- Metrics: Basic timing metric around calculation (if you add actuator/micrometer) plus test asserting metric presence.

Expected Backend Deliverables:
- Clean, tested code (unit + at least one integration test) for chosen features.
- Updated README (or notes) briefly documenting new endpoints/parameters.
- Justification for design decisions (inline comments or minimal doc notes acceptable).

### 3. Collaborative / Mixed (Optional)
If pairing tracks, coordinate: backend adds a new feature; test engineer adds property‑based + contract tests including the new feature and validates no regressions.

### Submission Guidance
- Communicate scope: note what you intentionally omitted due to time.
- Prefer clarity & maintainability over cleverness.
- Commit incrementally with meaningful messages reflecting decisions.

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

**URL**: `/interest/calculate`

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

Ensure you have Gradle installed (Gradle wrapper is included), and run the following command in the root directory of the project:

```bash
./gradlew bootRun
```

## Testing

You can test the API using tools like Postman or CURL by making requests to `http://localhost:8080/interest/calculate`
with the required query parameters.
