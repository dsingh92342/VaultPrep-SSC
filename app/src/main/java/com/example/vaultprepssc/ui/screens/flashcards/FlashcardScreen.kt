package com.example.vaultprepssc.ui.screens.flashcards

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlashcardScreen(
    onNavigateBack: () -> Unit
) {
    // Basic local state for demo purposes - in a full app, this would come from a Repository/SRS engine
    val samples = listOf(
        Flashcard("Ephemeral", "Lasting for a very short time."),
        Flashcard("Battle of Panipat (1st)", "1526 AD - Babur vs Ibrahim Lodi"),
        Flashcard("Article 32", "Right to Constitutional Remedies (Heart & Soul of Constitution)"),
        Flashcard("Meticulous", "Showing great attention to detail; very careful and precise.")
    )
    
    var currentIndex by remember { mutableIntStateOf(0) }
    var isFlipped by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Zen Flashcards") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("SRS PROGRESS: ${currentIndex + 1} / ${samples.size}", style = MaterialTheme.typography.labelLarge)
            
            Spacer(modifier = Modifier.height(32.dp))
            
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clickable { isFlipped = !isFlipped },
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier.fillMaxSize(),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(24.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize().padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        androidx.compose.animation.AnimatedVisibility(
                            visible = !isFlipped,
                            enter = fadeIn() + scaleIn(),
                            exit = fadeOut() + scaleOut()
                        ) {
                            Text(
                                samples[currentIndex].front, 
                                fontSize = 28.sp, 
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                        }
                        
                        androidx.compose.animation.AnimatedVisibility(
                            visible = isFlipped,
                            enter = fadeIn() + scaleIn(),
                            exit = fadeOut() + scaleOut()
                        ) {
                            Text(
                                samples[currentIndex].back, 
                                fontSize = 20.sp, 
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(48.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                OutlinedIconButton(
                    onClick = { 
                        isFlipped = false
                        currentIndex = (currentIndex + 1) % samples.size 
                    },
                    modifier = Modifier.size(64.dp)
                ) {
                    Icon(Icons.Default.Close, contentDescription = "Hard", tint = MaterialTheme.colorScheme.error)
                }
                
                FilledIconButton(
                    onClick = { 
                        isFlipped = false
                        currentIndex = (currentIndex + 1) % samples.size 
                    },
                    modifier = Modifier.size(64.dp)
                ) {
                    Icon(Icons.Default.Check, contentDescription = "Easy")
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            Text("Tap card to flip", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.outline)
        }
    }
}

data class Flashcard(val front: String, val back: String)
