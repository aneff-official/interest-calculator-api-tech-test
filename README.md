# Interest Calculator Technical Test

## Table of Contents
- [Technical Test](#technical-test)
- [Candidate Task Tracks](#candidate-task-tracks)
- [Application Overview](#application-overview)
- [Running the Application](#running-the-application)
- [Testing](#testing)
- [Candidate Notes & Reasoning (to be completed by you when submitting)](#candidate-notes--reasoning-to-be-completed-by-you-when-submitting)
- [AI Usage Summary (to be completed by you when submitting)](#ai-usage-summary-to-be-completed-by-you-when-submitting)

---

## Technical Test

### Background

You are provided with an API that calculates either simple or compound interest based on input parameters such as the
principal amount, interest rate, duration, and type of interest accrual. The API also performs input validation to
ensure all provided parameters are positive and the accrual type is either "simple" or "compound".

### Task

Choose ONE primary track (Test Automation or Backend Engineering) and demonstrate depth, clarity, and good engineering/testing practices within the suggested 2–4 hour window. Prioritise
quality over breadth: a smaller, well‑crafted set of tests or a focused, well‑tested feature beats a sprawling, shallow
submission.

Track summaries (details further below):
- Test Automation Track: Design and implement an automated suite validating existing behaviour (happy paths, validation,
  edge cases) and optionally add advanced techniques (property‑based, mutation, performance smoke, contract/schema).
- Backend Engineering Track: Extend the API with a small feature (e.g. new accrual type, schedule endpoint, improved
  error model) accompanied by unit + integration tests and concise documentation.

Expectations common to both tracks:
- Incremental commits with meaningful messages.
- Clear README (in your bundle) explaining how to run what you built and the rationale for your approach.
- Explicit note of any deliberate omissions due to time.

### Deliverables

- A git bundle that contains all your work. This can be done by:

```bash
git bundle create name.bundle --all 
```

- Include a file README.md in the git bundle detailing
    - How to set up and run your tests and/or new feature(s)
    - Your testing strategy (even for backend track: how you validated correctness)
    - Any findings or improvement suggestions (bugs, edge cases, design notes)
- Submit your sample git bundle emailing it to us (via your recruiter if you have one)

---

## Candidate Task Tracks

Pick ONE primary track. Timebox yourself (suggested 2–4 hours total). Prioritise depth & quality over breadth.

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

### Submission Guidance
- Communicate scope: note what you intentionally omitted due to time.
- Prefer clarity & maintainability over cleverness.
- Commit incrementally with meaningful messages reflecting decisions.

### Timeboxing Guidance
- If you reach ~2 hours and core tasks (chosen feature OR baseline tests) are solid, prefer polishing (refactor, assertions) over starting new major scope.
- Stop before implementing half-finished enhancements; document them instead.
- At ~4 hours you should have: chosen track core deliverables + optional 1–2 enhancements (only if fully tested).

---

## Application Overview

### Overview

This Spring Boot application provides an API to calculate interest based on user input. It is designed to handle both
simple and compound interest calculations using a set of given parameters. The application is built using Kotlin and
leverages the Spring Web dependency for handling HTTP requests.

### Features

- **Interest Calculation**: Users can calculate either simple or compound interest based on input parameters.
- **Input Validation**: Ensures all input values are positive and valid before processing.
- **Detailed Output**: The API returns a detailed breakdown including the starting amount, interest accrued, and the
  final balance.

### API Endpoint

**URL**: `/interest/calculate`

**Method**: `GET`

**Query Parameters**:

- `amount` (BigDecimal): The principal amount.
- `interestRate` (BigDecimal): The annual interest rate (in percentage).
- `duration` (Integer): The duration of the investment/debt in years.
- `accrualType` (String): Enum value: `SIMPLE` or `COMPOUND` (case-sensitive; must match exactly).

**Responses**:

- **200 OK**: Returns the calculation results.
- **400 Bad Request**: Returns an error message if input validation fails.

#### Sample Responses

SIMPLE interest example:
```json
{
  "startingAmount": 1000.00,
  "interest": 150.00,
  "finalAmount": 1150.00
}
```
COMPOUND interest example:
```json
{
  "startingAmount": 1000.00,
  "interest": 157.63,
  "finalAmount": 1157.63
}
```

### Validation

The API performs the following validations:

- All numeric parameters (`amount`, `interestRate`, and `duration`) must be positive values greater than zero.
- The `accrualType` must be either the enum value `SIMPLE` or `COMPOUND` (uppercase; other casing will be rejected).

### Interest Calculation

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

You can run the application locally with Gradle or inside a container using Docker Compose (via the provided `Makefile`). Choose the approach that best fits your workflow.

### Option A: Local (Gradle)

Prerequisites: JDK 21 (matches the Dockerfile base), internet access for dependency resolution.

```bash
./gradlew bootRun
```

### Option B: Docker Compose via Makefile (recommended for parity)

Prerequisites: Docker (and Docker Compose plugin) installed.

Bring everything up (build image + run container):

```bash
make run
```

Equivalent raw command (without Makefile):

```bash
docker compose up --build
```

Stop and remove containers (in the same terminal where it's running press Ctrl+C, then optionally):

```bash
docker compose down
```

### Option C: Smoke Test Shortcut

Runs container detached, waits briefly, hits health endpoint, then tears down:

```bash
make smoke
```

If the smoke test fails you'll see logs printed for the `app` service.

### Verifying the API

After starting (any option), test the endpoint:

```bash
curl "http://localhost:8080/interest/calculate?amount=1000&interestRate=5&duration=3&accrualType=COMPOUND" | jq
```

(If `jq` is not installed just omit the pipe.)

### Quick Troubleshooting
- Port already in use: ensure nothing else is bound to 8080 or change the exposed port in `compose.yaml`.
- Dependency download issues: retry `./gradlew build` (intermittent network hiccups) or run with `--no-daemon` for clearer logs.
- Docker build caching stale: run `docker compose build --no-cache`.

## Testing

You can test the API using tools like Postman or CURL by making requests to `http://localhost:8080/interest/calculate`
with the required query parameters.

Automated test commands (Gradle):
```bash
./gradlew test            # Runs unit tests
./gradlew integrationTest # Runs integration tests (src/int/...)
./gradlew check           # Runs both (test + integrationTest) and any other verification tasks
```

---

## Candidate Notes & Reasoning (to be completed by you when submitting)

(Please keep responses concise: aim for bullet points, max ~8–10 lines per subsection.)

Use this section to concisely explain what you did, why you did it, and anything you would do next with more time. Keep it focused and skimmable.

### 1. Scope & Track Selection
- Track chosen: Backend (Second choice) as I feel more confident with hands-on tasks like these
- Features: Core features such as a new variant of AccrualType (interest rate) - Compound Daily Interest Rate; Better Error Handling; Optional Currency Change
- Deliberate omissions due to time: The application.properties feature was done, however I lost 30 minutes trying to fix an error around testing it, so ultimately it was removed. No time for the rest of the features was left

### 2. Approach Summary
- High-level strategy: The strategy was to choose one of the tasks and continue with each subtask
- Key tools / libraries used and why: Mostly no other libraries/tools were used, except one that helps Serialize (kotlinx) the ErrorHandler class for better HttpResponses (and messages)
- Structure of tests / new code: I have tried to preserve to current structure of the project, with only adding new files in the already established folders in the project tree. Probably some of the models could be moved elsewhere.

### 3. Design & Implementation Decisions (Backend track only if applicable)
- Main abstractions added or changed: AccruelType.DAILY was implemented, alongside with its corresponding tests and revamped error handling.
- Rationale for any new endpoint / parameter / data structure: The schedule endpoint seemed easy to implement, however I found it confusing as to what is asked of me.
- Trade-offs (simplicity vs extensibility, performance vs clarity): I lost some time around the integration tests after I implemented the new ErrorHandler, as I had to return a response that had an exact value. The way I previously thought it could work is with variables, showing what the request param initially entered was.

### 4. Testing Strategy Details (always include)
- Test layers present (unit / integration / other): An interest rate of 0 seemed to be missing, so I added it. I have already mentioned above the new interest rate type test inclusion.
- Edge cases covered: I thought that there should be a maximum duration (due to being used in years), so I added a ceiling in addition to the minimum (0)
- Any property-based / mutation / performance / contract techniques used: There are a few places where property-based techniques are used, however no performance gains were tested if that is the question.

### 5. Assumptions & Clarifications
- Business or domain assumptions made: I assumed that the solution could be a very early or demo version of something similar (but more sophisticated) used in Finance
- Validation / precision assumptions: I saw that everywhere the scale used was (2) with the BigDecimal, however there was one place I needed a higher number (10) as the compound daily interest rate required higher precision (or else it would give a false calculation)

### 6. Findings / Potential Improvements
- Defects or odd behaviours noticed: Not to my knowledge
- Suggested refactors or enhancements: There could be many, many other enhancements around the existing and to be yet implemented features. There could be more parameters to the API calls, resulting in a more specific and complex was of calculation. More models for the different classes/properties. Externalise strings/constants
- Performance or reliability observations: I had no issues running the project or debugging it. It ran smoothly and the requests I made via Postman seemed to have no delay

### 7. Risk & Future Work
- Areas of fragility or technical debt: I think this is not applicable in this case, as technical debts usually originate from big legacy systems and are difficult to migrate, which is not the case here.
- Next steps if given more time: I would focus on modularity and restructuring when a few more features are implemented. Tests should also be written, validation could be improved.

### 8. Time & Environment
- Approximate time spent (breakdown): I am not sure if reading and writing the documentation counts. If yes, then I might be a little over the time limit. The first part of the core feature (code implementation) seemed to go very smoothly and quickly. As I tried to add more and more, it took more time to fix my syntax and test errors. Overall, the preparation took more than the actual work
- Environment issues encountered (network, tooling) and workarounds: I had never used the bundle command before and I had some trouble with it. That is why I will try sharing a link to my forked repository of your original one.

### 9. Final Reflection
- What you would prioritise next for quality or maintainability: It seems to me that precision is one of the key areas to focus on, as we are dealing with real money. So to make sure none is lost, further checks (with code & tests) must be done.


## AI Usage Summary (to be completed by you when submitting)

We support responsible use of AI. Provide a brief overview so we can focus interview questions effectively. Keep it lean (aim for a few bullet points per item).

1. Tools Used:
   - ChatGPT helped create a comparison table for Unit vs Integration tests, as I have very little experience with them.
2. Assisted Tasks:
   - It showed me a real world examples that helped me understand which is which and where it is used.
3. Example Interactions:
   - "Difference between unit and integration test. Give a short Java example"
4. Human Validation:
   - I have not directly copied anything from the chat bot or the internet. I did a lot of documentation reading, whether offical or not. Most of the useful code chunks I found were already part of the project; with little tweaks I was able to make them serve a new purpose
5. AI vs Manual Split:
   - Well, probably without the explanation I was given, I would have less of an understanding, however I would try and look in websites such as stackoverflow and would still come to a similar conclusion. I would say manual split is 90-95%
6. Intentional Non‑AI Areas:
   - I remembered that within the integration test I struggled a bit with the .andExpect jsonPath string matching and I asked the AI. However it was not very helpful, as it only hallucinated methods that I was not able to find anywhere.
7. Notable Rejections (optional):
   - Only the one I just mentioned.
