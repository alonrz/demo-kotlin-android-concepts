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
├── MainActivity.kt                 # Main activity with navigation setup
├── navigation/
│   ├── Screen.kt                  # Sealed class defining all screens
│   └── NavGraph.kt                # Navigation graph configuration
├── screens/
│   ├── MenuScreen.kt              # Main menu screen
│   ├── ParallelTimers.kt          # Parallel timers demo screen
│   └── ParallelTimersViewModel.kt # ViewModel for parallel timers
└── ui/theme/                       # App theme configuration
```

## How to Add a New Screen

### 1. Create the Screen Definition

In `navigation/Screen.kt`, add a new data object:

```kotlin
data object YourNewScreen : Screen(
    "your_route",
    "Your Screen Title",
    "Description of your screen"
)
```

### 2. Create the ViewModel

Create `screens/YourScreenViewModel.kt`:

```kotlin
package com.rz.democoncurrency.screens

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class YourScreenViewModel : ViewModel() {
    // Define your state
    private val _state = MutableStateFlow(YourState())
    val state: StateFlow<YourState> = _state.asStateFlow()
    
    // Define your actions
    fun onAction() {
        // Update state
    }
    
    override fun onCleared() {
        super.onCleared()
        // Clean up resources
    }
}

data class YourState(
    val data: String = ""
)
```

### 3. Create the Screen Composable

Create `screens/YourScreen.kt`:

```kotlin
package com.rz.democoncurrency.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun YourScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    viewModel: YourScreenViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()
    
    // Your UI implementation
}
```

### 4. Add to Navigation Graph

In `navigation/NavGraph.kt`, add your screen to the NavHost:

```kotlin
composable(route = Screen.YourNewScreen.route) {
    YourScreen()
}
```

### 5. Add to Menu

In `screens/MenuScreen.kt`, add your screen to the menu items list:

```kotlin
val menuItems = listOf(
    Screen.ParallelTimers,
    Screen.YourNewScreen  // Add here
)
```

### 6. Update MainActivity

In `MainActivity.kt`, add the screen mapping in the `when` expression:

```kotlin
val currentScreen = when (currentRoute) {
    Screen.Menu.route -> Screen.Menu
    Screen.ParallelTimers.route -> Screen.ParallelTimers
    Screen.YourNewScreen.route -> Screen.YourNewScreen  // Add here
    else -> Screen.Menu
}
```

## Features

- **Automatic navigation** with back button support
- **ViewModel lifecycle** automatically managed per screen
- **Material 3 design** with dynamic theming
- **Scrollable menu** that grows with new items
- **Top app bar** with proper titles and navigation

## Current Demos

1. **Parallel Timers** - Demonstration of parallel timer execution

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
