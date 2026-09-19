# Repository Guidelines

## Repository Workflow

- At the start of each task, read `_context/wiki/index.md` and follow only the links relevant to
  the task; do not read the entire wiki by default.
- After completing a task, offer to update the wiki when the work produces durable project knowledge,
  then wait for approval before editing it.

## Project Structure & Module Organization

GiftBook is a Kotlin Multiplatform Compose application targeting Android and iOS.

- `composeApp/src/commonMain` contains shared models, Room database code, navigation, UI, themes,
  and resources.
- `composeApp/src/androidMain` and `composeApp/src/iosMain` contain platform-specific
  implementations and entry-point integration.
- `iosApp` contains the native Xcode project and SwiftUI/iOS application entry point.
- `composeApp/schemas` stores generated Room database schemas. Keep schema changes under version
  control.
- `_context/wiki` contains project context and development preferences.
- `composeApp/src/commonTest` contains shared tests; add platform-specific tests under the matching
  source set when needed.

## Build, Test, and Development Commands

Use the checked-in Gradle wrapper so local and CI builds use the same Gradle version:

```bash
./gradlew build
./gradlew :composeApp:assembleDebug
./gradlew :composeApp:allTests
```

`build` performs the full project build, `assembleDebug` creates the Android debug artifact, and
`allTests` runs available multiplatform tests. Open `iosApp/iosApp.xcodeproj` in Xcode to build and
run the iOS application.

For focused Android verification, use `./gradlew :composeApp:compileDebugKotlinAndroid` and
`./gradlew :composeApp:testDebugUnitTest`. The current Camper dependency is the stable `0.3.2`
release; avoid restoring the unavailable snapshot version.

## Coding Style & Naming Conventions

Use Kotlin’s standard four-space indentation and idiomatic Kotlin naming: `PascalCase` for
classes/composables, `camelCase` for functions and properties, and descriptive names for UI state
and components. Keep shared logic in `commonMain`; isolate Android/iOS APIs in the matching source
set. Follow existing package paths under `com.steeplesoft.giftbook`, and group related UI files by
feature (for example, `ui/recipients`). No repository formatter or linter is currently configured;
keep imports clean and match surrounding Compose style.

Decompose components should use lifecycle-owned coroutine scopes and expose mutable screen data
through observable immutable values. Keep database work off the main dispatcher and avoid mutating
lists or Room entities in place when updating Compose state.

Room schema changes require a migration and a checked-in schema under `composeApp/schemas`. Demo data
is Android-debug-only and must never seed release or iOS user databases. Android backup rules exclude
the local database; keep platform storage and data-protection behavior consistent with that policy.

## Testing Guidelines

Use `kotlin.test` for focused tests covering database, forms, navigation, and other business logic.
Name tests `*Test.kt` and place them in the narrowest applicable source set. Run
`./gradlew :composeApp:allTests` before submitting changes; remember that iOS simulator tests require
macOS and may be skipped on Linux.

## Commit & Pull Request Guidelines

Recent commits use short, imperative-style summaries such as `Build updates` and `Version updates`.
Keep commits focused and use a concise subject. Pull requests should explain the behavior changed,
identify validation commands run, link related issues when applicable, and include screenshots or
recordings for UI changes on Android and/or iOS.
