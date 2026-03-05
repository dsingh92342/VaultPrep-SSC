package com.example.vaultprepssc.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onNavigateToLibrary: () -> Unit,
    onStartMock: () -> Unit,
    onNavigateToMistakes: () -> Unit,
    onNavigateToFlashcards: () -> Unit,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val recentSessions by viewModel.recentSessions.collectAsState()
    val isOffline by viewModel.isOffline.collectAsState()

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = {
                    Column {
                        Text("VaultPrep SSC", style = MaterialTheme.typography.displayMedium)
                        Text("Ready for deep work?", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                ZenStatusIndicator(isOffline)
                Spacer(modifier = Modifier.height(20.dp))
                QuickStartSection(
                    onLibraryClick = onNavigateToLibrary, 
                    onMockClick = onStartMock,
                    onMistakesClick = onNavigateToMistakes,
                    onFlashcardsClick = onNavigateToFlashcards
                )
            }

            item {
                Text(
                    "Recent Sessions",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            if (recentSessions.isEmpty()) {
                item {
                    EmptyStateCard()
                }
            } else {
                items(recentSessions.take(5), key = { it.id }) { session ->
                    SessionItem(session)
                }
            }
            
            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}

@Composable
fun QuickStartSection(
    onLibraryClick: () -> Unit,
    onMockClick: () -> Unit,
    onMistakesClick: () -> Unit,
    onFlashcardsClick: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            DashboardCard(
                title = "Library",
                subtitle = "PYQs & Topics",
                icon = Icons.Default.LibraryBooks,
                gradient = Brush.linearGradient(listOf(Color(0xFF3B82F6), Color(0xFF1D4ED8))),
                modifier = Modifier.weight(1f),
                onClick = onLibraryClick
            )
            DashboardCard(
                title = "Mock Test",
                subtitle = "Simulated Exam",
                icon = Icons.Default.Psychology,
                gradient = Brush.linearGradient(listOf(Color(0xFFF59E0B), Color(0xFFD97706))),
                modifier = Modifier.weight(1f),
                onClick = onMockClick
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            DashboardCard(
                title = "Mistakes",
                subtitle = "Review Errors",
                icon = Icons.Default.Warning,
                gradient = Brush.linearGradient(listOf(Color(0xFFEF4444), Color(0xFFB91C1C))),
                modifier = Modifier.weight(1f),
                onClick = onMistakesClick
            )
            DashboardCard(
                title = "Flashcards",
                subtitle = "Zen Memorization",
                icon = Icons.Default.FlashOn,
                gradient = Brush.linearGradient(listOf(Color(0xFF10B981), Color(0xFF047857))),
                modifier = Modifier.weight(1f),
                onClick = onFlashcardsClick
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    gradient: Brush,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(24.dp),
        modifier = modifier.height(160.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradient)
                .padding(16.dp)
        ) {
            Icon(
                icon,
                contentDescription = null,
                modifier = Modifier.size(40.dp).align(Alignment.TopEnd),
                tint = Color.White.copy(alpha = 0.8f)
            )
            Column(modifier = Modifier.align(Alignment.BottomStart)) {
                Text(title, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text(subtitle, color = Color.White.copy(alpha = 0.9f), fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun ZenStatusIndicator(isOffline: Boolean) {
    Surface(
        color = if (isOffline) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.3f),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                if (isOffline) Icons.Default.SelfImprovement else Icons.Default.Warning,
                contentDescription = null,
                tint = if (isOffline) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    if (isOffline) "ZEN MODE ACTIVE" else "ONLINE MODE",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = if (isOffline) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    if (isOffline) "Deep Work points are being banked." else "Go offline to eliminate distractions.",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
fun SessionItem(session: com.example.vaultprepssc.data.local.entity.TestSession) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text("${(session.score.toFloat() / session.totalQuestions * 100).toInt()}%", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(session.testName, fontWeight = FontWeight.Bold)
                Text("Score: ${session.score}/${session.totalQuestions}", style = MaterialTheme.typography.bodySmall)
            }
            Text(
                java.text.SimpleDateFormat("dd MMM", java.util.Locale.getDefault()).format(session.timestamp),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.outline
            )
        }
    }
}

@Composable
fun EmptyStateCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(Icons.Default.History, contentDescription = null, modifier = Modifier.size(48.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.height(12.dp))
            Text("No tests taken yet.", fontWeight = FontWeight.Medium)
            Text("Start your first session to see analytics.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}
