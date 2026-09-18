# Project Overview

## What is GiftBook?

GiftBook is a gift tracking application designed to help people remember gift ideas and manage occasions for friends and family. It solves the common problem of forgetting what gifts people want or have mentioned throughout the year, making gift-giving more thoughtful and less stressful.

## Main Goals and Objectives

- **Primary Goal**: Publish GiftBook on both Android and iOS app stores
- **User Value**: Enable users to track gift ideas, manage recipients, and organize occasions efficiently
- **Technical Goal**: Build a production-ready Kotlin Multiplatform application with native UI experiences

## Target Users

- Individuals who want to be more thoughtful gift-givers
- People managing gifts for multiple family members and friends
- Anyone who struggles to remember gift ideas mentioned throughout the year

## Key Features and Modules

### Core Features

1. **Recipients Management**
   - Track friends and family members
   - Store relevant information about each person

2. **Occasions Tracking**
   - Associate occasions (birthdays, holidays, anniversaries) with recipients
   - Track important dates and events

3. **Gift Ideas**
   - Capture and organize gift ideas for each recipient
   - Link ideas to specific occasions

### Technical Architecture

- **Platform**: Kotlin Multiplatform (KMP)
- **UI Framework**: Compose Multiplatform
- **Targets**: Android and iOS
- **Database**: Room (with KMP support)
- **Dependency Injection**: Koin

### Key Modules

- `database/` - Room database, DAOs, and converters
- `model/` - Core data models (Recipient, Occasion, GiftIdea, EventType)
- `ui/` - Compose UI components and screens
- `form/` - Form handling for data entry
- `theme/` - UI theming and styling

## Project Structure

```
composeApp/
├── androidMain/     - Android-specific code
├── iosMain/         - iOS-specific code
└── commonMain/      - Shared business logic and UI
    ├── kotlin/
    │   └── com/steeplesoft/giftbook/
    │       ├── database/    - Data persistence
    │       ├── model/       - Domain models
    │       ├── ui/          - UI components
    │       └── form/        - Form handling
    └── composeResources/    - Shared resources
```

## Important Considerations

- **Cross-platform Consistency**: Maintain feature parity between Android and iOS
- **Data Persistence**: Reliable local storage using Room database
- **User Experience**: Intuitive navigation and data entry flows
- **App Store Readiness**: Code quality and polish suitable for public release

## Development Workflow

- Use the checked-in Gradle wrapper (`./gradlew`) for consistent builds.
- `./gradlew build` runs the full project build.
- `./gradlew :composeApp:assembleDebug` creates the Android debug artifact.
- `./gradlew :composeApp:allTests` runs the available multiplatform tests. A dedicated test source tree and coverage threshold are not currently established.
- Build and run the iOS target from `iosApp/iosApp.xcodeproj` in Xcode.

Shared code belongs in `composeApp/src/commonMain`; Android- and iOS-specific implementations belong in their corresponding source sets. Room schemas are kept under `composeApp/schemas` and should remain version controlled when database changes are made.
