package com.example.vaultprepssc.ui.navigation

sealed class Screen(val route: String) {
    object Dashboard : Screen("dashboard")
    object Library : Screen("library")
    object Analytics : Screen("analytics")
    object Exam : Screen("exam/{testId}") {
        fun createRoute(testId: String) = "exam/$testId"
    }
    object Settings : Screen("settings")
    object Mistakes : Screen("mistakes")
    object Flashcards : Screen("flashcards")
}
