# Compose Quality Improvements Design

## Goal

Improve GiftBook's reliability, lifecycle behavior, database integrity, input validation, accessibility, local-data protection, and build readiness without changing the product's core workflow.

## Approach

- Keep Decompose as the navigation/component boundary and expose component state through observable immutable values.
- Bind component coroutines to component destruction; perform Room work off the main dispatcher and keep navigation/UI state updates structured.
- Move derived occasion progress into a Room projection query, add the missing gift-to-occasion relationship, and make database initialization deterministic.
- Keep demo data out of production initialization.
- Add focused tests for validation and data calculations, then verify Android compilation where dependencies are available.

## Ordered deliverables

1. Test infrastructure and focused tests for validators, cost parsing, and progress calculations.
2. Form/input safety and loaded-screen null-state handling.
3. Lifecycle-scoped coroutines and observable immutable component state.
4. Room foreign keys, migration/schema updates, and a non-N+1 progress query.
5. Deterministic database startup and debug-only demo data.
6. Compose accessibility, list keys, navigation selection, and FAB layout improvements.
7. Android/iOS data-protection settings and Gradle/dependency cleanup.

## Constraints

- Preserve unrelated working-tree changes.
- Commit each deliverable independently with a concise description.
- Do not introduce a new state-management or database library when the existing Compose, Decompose, and Room stack can provide the behavior.
- Keep user-entered costs non-negative and represented consistently by the existing integer domain model.
