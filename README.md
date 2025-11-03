# Demo Concurrency

An Android app demonstrating various concurrency concepts using Jetpack Compose and modern Android
architecture.

## Architecture

This app uses:

- **Jetpack Compose** for UI
- **Navigation Compose** for screen navigation
- **ViewModel** architecture for each screen
- **Material 3** design system

## Project Structure

```
app/src/main/java/com/rz/democoncurrency/
├── MainActivity.kt                  # Main activity with navigation setup
├── navigation/
│   ├── Screen.kt                   # Sealed class defining all screens
│   └── NavGraph.kt                 # Navigation graph configuration
├── screens/
│   ├── MenuScreen.kt               # Main menu screen
│   ├── Concurrency.kt              # Concurrency demo screen
│   ├── ConcurrencyViewModel.kt     # ViewModel for concurrency demo
│   ├── ParallelTimers.kt           # Parallel timers demo screen
│   └── ParallelTimersViewModel.kt  # ViewModel for parallel timers
└── ui/theme/                        # App theme configuration
```

## Features

- **Automatic navigation** with back button support
- **ViewModel lifecycle** automatically managed per screen
- **Material 3 design** with dynamic theming
- **Scrollable menu** that grows with new items
- **Top app bar** with proper titles and navigation

## Current Demos

1. **Parallel Timers** - Demonstration of parallel timer execution using Kotlin coroutines
2. **Concurrency** - Demonstration of concurrency patterns including:
    - Sequential execution vs concurrent execution
    - Coroutine cancellation
    - Exception handling with supervisorScope
    - Async/await for parallel operations

## Building and Running

1. Open the project in Android Studio
2. Sync Gradle dependencies
3. Run on emulator or device (API 27+)

## Dependencies

- AndroidX Core KTX
- Jetpack Compose (Material 3)
- Navigation Compose
- Lifecycle ViewModel Compose
- Kotlin Coroutines

## Notes

- Each screen gets its own ViewModel instance
- ViewModels are automatically cleared when leaving the screen
- The menu is easily extensible - just add new Screen objects
- All navigation is handled through the NavController
