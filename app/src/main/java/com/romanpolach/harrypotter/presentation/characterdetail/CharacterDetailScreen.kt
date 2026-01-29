package com.romanpolach.harrypotter.presentation.characterdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.romanpolach.harrypotter.domain.model.Character
import com.romanpolach.harrypotter.ui.theme.GryffindorRed
import com.romanpolach.harrypotter.ui.theme.HufflepuffYellow
import com.romanpolach.harrypotter.ui.theme.RavenclawBlue
import com.romanpolach.harrypotter.ui.theme.SlytherinGreen
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import androidx.compose.ui.platform.LocalContext
import com.romanpolach.harrypotter.R
import com.romanpolach.harrypotter.presentation.components.MagicalBackground

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailScreen(
    viewModel: CharacterDetailViewModel,
    onBackClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is CharacterDetailContract.Effect.GoBack -> onBackClick()
                is CharacterDetailContract.Effect.ShowError -> {
                    // Handle error - you could use a snackbar here as well
                }
            }
        }
    }
    
    MagicalBackground {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { 
                        Text(
                            text = state.character?.name ?: stringResource(R.string.character_detail_placeholder),
                            maxLines = 1
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onBackClick) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = stringResource(R.string.go_back_label)
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent
                    )
                )
            },
            containerColor = Color.Transparent,
            floatingActionButton = {
                state.character?.let { character ->
                    FloatingActionButton(
                        onClick = { viewModel.handleIntent(CharacterDetailContract.Intent.ToggleFavorite) },
                        containerColor = if (character.isFavorite) 
                            MaterialTheme.colorScheme.error 
                        else 
                            MaterialTheme.colorScheme.primaryContainer
                    ) {
                        Icon(
                            imageVector = if (character.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = if (character.isFavorite) {
                                stringResource(R.string.remove_from_favorites)
                            } else {
                                stringResource(R.string.add_to_favorites)
                            },
                        tint = if (character.isFavorite) 
                            MaterialTheme.colorScheme.onError 
                        else 
                            MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }
    ) { paddingValues ->
        when {
            state.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            state.error != null -> {
                val error = state.error
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(32.dp)
                    ) {
                        Text(
                            text = "⚠️",
                            style = MaterialTheme.typography.displayMedium
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = error?.asString() ?: "",
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
            state.character != null -> {
                CharacterDetailContent(
                    character = state.character!!,
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    }
    }
}

@Composable
private fun CharacterDetailContent(
    character: Character,
    modifier: Modifier = Modifier
) {
    val houseColor = getHouseColor(character.house)
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Hero image section
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(3f / 4f)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            houseColor.copy(alpha = 0.5f),
                            houseColor.copy(alpha = 0.2f),
                            Color.Transparent
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            if (character.imageUrl.isNotBlank()) {
                AsyncImage(
                    model = character.imageUrl,
                    contentDescription = character.name,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp)
                        .clip(RoundedCornerShape(24.dp)),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(240.dp)
                        .clip(CircleShape)
                        .background(houseColor.copy(alpha = 0.3f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.icon_sorcer),
                        contentDescription = null,
                        modifier = Modifier.size(140.dp),
                        tint = houseColor
                    )
                }
            }
        }
        
        // Character info
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = character.name,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            
            if (character.house.isNotBlank()) {
                Spacer(modifier = Modifier.height(12.dp))
                HouseBadge(house = character.house, large = true)
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Actor info card
            if (character.actor.isNotBlank()) {
                InfoCard(
                    title = stringResource(R.string.portrayed_by),
                    value = character.actor,
                    houseColor = houseColor
                )
            }
            
            if (character.species.isNotBlank()) {
                Spacer(modifier = Modifier.height(16.dp))
                InfoCard(
                    title = stringResource(R.string.species_label),
                    value = character.species.replaceFirstChar { it.uppercase() },
                    houseColor = houseColor
                )
            }
            
            // Extra spacer at bottom to account for FAB overlapping
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
private fun InfoCard(
    title: String,
    value: String,
    houseColor: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.weight(0.4f)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = houseColor,
                textAlign = TextAlign.End,
                modifier = Modifier.weight(0.6f)
            )
        }
    }
}

@Composable
private fun HouseBadge(house: String, large: Boolean = false) {
    val houseColor = getHouseColor(house)
    
    Box(
        modifier = Modifier
            .background(
                color = houseColor.copy(alpha = 0.2f),
                shape = RoundedCornerShape(if (large) 8.dp else 4.dp)
            )
            .padding(
                horizontal = if (large) 16.dp else 8.dp,
                vertical = if (large) 8.dp else 2.dp
            )
    ) {
        Text(
            text = house,
            style = if (large) MaterialTheme.typography.titleMedium else MaterialTheme.typography.labelSmall,
            color = houseColor,
            fontWeight = FontWeight.SemiBold
        )
    }
}

private fun getHouseColor(house: String): Color {
    return when (house.lowercase()) {
        "gryffindor" -> GryffindorRed
        "slytherin" -> SlytherinGreen
        "ravenclaw" -> RavenclawBlue
        "hufflepuff" -> HufflepuffYellow
        else -> Color.Gray
    }
}
