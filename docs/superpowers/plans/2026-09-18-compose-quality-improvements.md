# Compose Quality Improvements Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:executing-plans (recommended) or superpowers:subagent-driven-development to implement this plan task-by-task.

**Goal:** Improve GiftBook reliability, lifecycle behavior, database integrity, input validation, accessibility, local-data protection, and build readiness.

**Architecture:** Keep Decompose components as the state/navigation boundary. Give each component a lifecycle-owned coroutine scope and expose screen data through observable immutable values. Keep Room as the source of truth, with a projection query for occasion progress and explicit database initialization before the root screen loads.

**Tech Stack:** Kotlin Multiplatform, Compose Multiplatform, Decompose, Room, Koin, kotlinx.coroutines, kotlinx.datetime, kotlin.test.

**Spec:** `docs/superpowers/specs/2026-09-18-compose-quality-design.md`

## Global Constraints

- Preserve unrelated working-tree changes.
- Commit each task independently.
- Reuse the existing Compose, Decompose, Room, and Koin stack.
- Keep costs non-negative and represented by the existing integer model.

### Task 1: Test foundation and pure domain tests

**Files:**
- Modify: `composeApp/build.gradle.kts`
- Create: `composeApp/src/commonTest/kotlin/com/steeplesoft/giftbook/form/ValidationTest.kt`
- Create: `composeApp/src/commonTest/kotlin/com/steeplesoft/giftbook/model/OccasionProgressTest.kt`

- [ ] Add common test dependencies and source-set configuration.
- [ ] Write failing tests for integer validation, positive target values, and progress aggregation behavior.
- [ ] Run the narrow common test task and confirm the tests fail for the current validator behavior.
- [ ] Implement the minimum pure-code fixes needed for the tests.
- [ ] Run `./gradlew :composeApp:allTests` and commit.

### Task 2: Safe form and loaded-screen behavior

**Files:**
- Modify: form classes, `GiftCostDialog.kt`, and all `View*Content.kt` files that read `lateinit` data.
- Test: common validation tests from Task 1.

- [ ] Replace force unwraps at user-input boundaries with validated early returns.
- [ ] Parse cost input safely, reject invalid/negative values, and use numeric keyboard semantics.
- [ ] Render async-loaded data and dialogs only after required data exists.
- [ ] Run tests and Android compilation if dependencies resolve; commit.

### Task 3: Lifecycle-owned observable state

**Files:**
- Modify: `ComponentExtensions.kt` and all affected Decompose components/content files.

- [ ] Add a component coroutine scope cancelled with component destruction.
- [ ] Move database work to structured coroutines and surface failures as observable error state.
- [ ] Replace plain mutable lists/properties with immutable observable values.
- [ ] Replace the empty-list refresh workaround with copy-based state updates and stable lazy-list keys.
- [ ] Run available tests and compile checks; commit.

### Task 4: Room integrity and progress query

**Files:**
- Modify: `GiftIdea.kt`, DAO files, `AppDatabase.kt`, and generated schema as required.
- Create or modify: progress projection model and focused database tests.

- [ ] Add the `GiftIdea.occasionId` foreign key with explicit delete behavior.
- [ ] Add a Room projection query that computes occasion progress without per-recipient queries.
- [ ] Add indexes required by the query and verify schema output.
- [ ] Run database tests and commit the schema/data-integrity changes.

### Task 5: Deterministic database initialization

**Files:**
- Modify: `KoinModule.kt`, `DemoData.kt`, platform build configuration, and app startup entry points.

- [ ] Make database creation complete before the root component can query it.
- [ ] Restrict demo data to debug/demo operation and prevent accidental reseeding of user data.
- [ ] Verify startup behavior and commit.

### Task 6: Compose UX and accessibility

**Files:**
- Modify: `RootContent.kt`, `ActionButton.kt`, `MyBottomBar.kt`, list/detail content files, and icon actions.

- [ ] Move the FAB into the Scaffold slot.
- [ ] Derive selected navigation item from the current route.
- [ ] Use semantic `IconButton`s and accurate content descriptions.
- [ ] Add stable lazy-list keys and preserve modifiers consistently.
- [ ] Run compile checks and commit.

### Task 7: Platform data protection and build cleanup

**Files:**
- Modify: `AndroidManifest.xml`, Android backup rules, iOS database path setup, `libs.versions.toml`, and Gradle configuration.

- [ ] Define explicit Android backup/data-extraction behavior for local gift data.
- [ ] Move iOS storage to an appropriate app-support location where practical.
- [ ] Resolve or document the unavailable Camper dependency and address current Gradle/KMP warnings without changing unrelated versions.
- [ ] Run final verification and commit.

### Task 8: Final verification

- [ ] Run `./gradlew :composeApp:allTests`.
- [ ] Run `./gradlew :composeApp:assembleDebug`.
- [ ] Inspect `git diff`, status, and commit history for scope and uncommitted task changes.
- [ ] Report any remaining environment limitations precisely.
