package com.rz.democoncurrency.navigation

sealed class Screen(val route: String, val title: String, val description: String) {
    data object Menu : Screen("menu", "Concurrency Demos", "Select a demo")
    data object ParallelTimers : Screen(
        "parallel_timers",
        "Parallel Timers",
        "Demonstration of parallel timer execution"
    )
    data object Concurrency : Screen(
        "concurrency",
        "Concurrency",
        "Demonstration of concurrency"
    )
}
