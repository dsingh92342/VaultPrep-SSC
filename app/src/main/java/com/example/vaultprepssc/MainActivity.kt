package com.example.vaultprepssc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.vaultprepssc.ui.navigation.Screen
import com.example.vaultprepssc.ui.screens.dashboard.DashboardScreen
import com.example.vaultprepssc.ui.theme.VaultPrepSSCTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VaultPrepSSCTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                val items = listOf(
                    NavigationItem("Dashboard", Screen.Dashboard.route, Icons.Default.Dashboard),
                    NavigationItem("Library", Screen.Library.route, Icons.Default.LibraryBooks),
                    NavigationItem("Analytics", Screen.Analytics.route, Icons.Default.Analytics),
                    NavigationItem("Settings", Screen.Settings.route, Icons.Default.Settings)
                )

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        NavigationBar(
                            containerColor = MaterialTheme.colorScheme.surface,
                            tonalElevation = 8.dp
                        ) {
                            items.forEach { item ->
                                NavigationBarItem(
                                    icon = { Icon(item.icon, contentDescription = item.label) },
                                    label = { Text(item.label) },
                                    selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                                    onClick = {
                                        navController.navigate(item.route) {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Dashboard.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(Screen.Dashboard.route) {
                            DashboardScreen(
                                onNavigateToLibrary = { navController.navigate(Screen.Library.route) },
                                onStartMock = { navController.navigate(Screen.Exam.createRoute("mock_1")) }
                            )
                        }
                        composable(Screen.Library.route) {
                            LibraryScreen(
                                onNavigateBack = { navController.navigateUp() },
                                onQuestionClick = { questionId -> 
                                    // Navigate to single question view or start session
                                }
                            )
                        }
                        composable(Screen.Analytics.route) {
                            AnalyticsScreen()
                        }
                        composable(Screen.Settings.route) {
                            SettingsScreen()
                        }
                        composable(Screen.Exam.route) { 
                            ExamScreen(
                                onExit = { navController.navigateUp() }
                            )
                        }
                    }
                }
            }
        }
    }
}

data class NavigationItem(
    val label: String,
    val route: String,
    val icon: ImageVector
)

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    VaultPrepSSCTheme {
        Greeting("Android")
    }
}