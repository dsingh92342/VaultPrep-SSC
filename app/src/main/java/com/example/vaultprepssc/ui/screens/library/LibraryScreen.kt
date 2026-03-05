package com.example.vaultprepssc.ui.screens.library

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.vaultprepssc.data.local.entity.Question

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryScreen(
    onNavigateBack: () -> Unit,
    onQuestionClick: (String) -> Unit,
    viewModel: LibraryViewModel = hiltViewModel()
) {
    val questions by viewModel.questions.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Question Library") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO: Filter */ }) {
                        Icon(Icons.Default.FilterList, contentDescription = "Filter")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (questions.isEmpty()) {
                item {
                    Text("No questions found in the vault.", modifier = Modifier.padding(16.dp))
                }
            } else {
                items(questions) { question ->
                    QuestionItem(question, onClick = { onQuestionClick(question.id) })
                }
            }
        }
    }
}

@Composable
fun QuestionItem(question: Question, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                AssistChip(
                    onClick = {},
                    label = { Text(question.subjectId) }
                )
                AssistChip(
                    onClick = {},
                    label = { Text(question.year.toString()) }
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                question.text,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 2
            )
        }
    }
}
