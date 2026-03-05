package com.example.vaultprepssc.ui.screens.analytics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalyticsScreen(
    viewModel: AnalyticsViewModel = hiltViewModel()
) {
    val stats by viewModel.subjectStats.collectAsState()
    val totalSolved by viewModel.totalQuestionsSolved.collectAsState()

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = { Text("Performance Vault") }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    StatMiniCard(
                        "Solved",
                        totalSolved.toString(),
                        Icons.Default.CheckCircle,
                        MaterialTheme.colorScheme.primary,
                        Modifier.weight(1f)
                    )
                    StatMiniCard(
                        "Accuracy",
                        "85%", // Placeholder
                        Icons.Default.Timeline,
                        MaterialTheme.colorScheme.secondary,
                        Modifier.weight(1f)
                    )
                }
            }

            item {
                Text("Subject Heatmap", style = MaterialTheme.typography.titleLarge)
            }

            item {
                HeatmapCard("Quantitative Aptitude", 0.75f)
                Spacer(modifier = Modifier.height(8.dp))
                HeatmapCard("English Language", 0.90f)
                Spacer(modifier = Modifier.height(8.dp))
                HeatmapCard("General Awareness", 0.45f)
                Spacer(modifier = Modifier.height(8.dp))
                HeatmapCard("Reasoning", 0.82f)
            }
            
            item {
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun StatMiniCard(
    label: String,
    value: String,
    icon: ImageVector,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.1f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Icon(icon, contentDescription = null, tint = color)
            Spacer(modifier = Modifier.height(8.dp))
            Text(value, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = color)
            Text(label, style = MaterialTheme.typography.labelMedium, color = color.copy(alpha = 0.8f))
        }
    }
}

@Composable
fun HeatmapCard(subject: String, progress: Float) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(subject, fontWeight = FontWeight.Medium)
                Text("${(progress * 100).toInt()}%", color = MaterialTheme.colorScheme.primary)
            }
            Spacer(modifier = Modifier.height(12.dp))
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier.fillMaxWidth().height(8.dp),
                shape = RoundedCornerShape(4.dp)
            )
        }
    }
}
