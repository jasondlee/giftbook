# Repository Guidelines

## Project Structure & Module Organization

GiftBook is a Kotlin Multiplatform Compose application targeting Android and iOS.

- `composeApp/src/commonMain` contains shared models, Room database code, navigation, UI, themes, and resources.
- `composeApp/src/androidMain` and `composeApp/src/iosMain` contain platform-specific implementations and entry-point integration.
- `iosApp` contains the native Xcode project and SwiftUI/iOS application entry point.
- `composeApp/schemas` stores generated Room database schemas. Keep schema changes under version control.
- `_context/wiki` contains project context and development preferences. There is currently no dedicated test source tree; add tests under the appropriate shared or platform source set as coverage is introduced.

## Build, Test, and Development Commands

Use the checked-in Gradle wrapper so local and CI builds use the same Gradle version:

```bash
./gradlew build
./gradlew :composeApp:assembleDebug
./gradlew :composeApp:allTests
```

`build` performs the full project build, `assembleDebug` creates the Android debug artifact, and `allTests` runs available multiplatform tests. Open `iosApp/iosApp.xcodeproj` in Xcode to build and run the iOS application.

## Coding Style & Naming Conventions

Use Kotlin’s standard four-space indentation and idiomatic Kotlin naming: `PascalCase` for classes/composables, `camelCase` for functions and properties, and descriptive names for UI state and components. Keep shared logic in `commonMain`; isolate Android/iOS APIs in the matching source set. Follow existing package paths under `com.steeplesoft.giftbook`, and group related UI files by feature (for example, `ui/recipients`). No repository formatter or linter is currently configured; keep imports clean and match surrounding Compose style.

## Testing Guidelines

No test framework or coverage threshold is currently documented. Add focused tests for database, forms, navigation, and other business logic, using `*Test.kt` names and the narrowest applicable source set. Run `./gradlew :composeApp:allTests` before submitting changes.

## Commit & Pull Request Guidelines

Recent commits use short, imperative-style summaries such as `Build updates` and `Version updates`. Keep commits focused and use a concise subject. Pull requests should explain the behavior changed, identify validation commands run, link related issues when applicable, and include screenshots or recordings for UI changes on Android and/or iOS.
