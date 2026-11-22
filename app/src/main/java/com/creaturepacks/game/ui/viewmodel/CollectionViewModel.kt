package com.creaturepacks.game.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creaturepacks.game.data.sample.allCards
import com.creaturepacks.game.domain.model.Card
import com.creaturepacks.game.domain.model.CardRarity
import com.creaturepacks.game.domain.repository.CardRepository
import com.creaturepacks.game.domain.repository.CollectionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class CollectionViewModel(
    private val collectionRepository: CollectionRepository,
    private val cardRepository: CardRepository
) : ViewModel() {

    private val _selectedRarity = MutableStateFlow<CardRarity?>(null)
    val selectedRarity: Flow<CardRarity?> = _selectedRarity

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: Flow<String> = _searchQuery

    val filteredCards: Flow<List<Card>> = combine(
        cardRepository.getAllCards(),
        selectedRarity,
        searchQuery
    ) { allCards, rarity, query ->
        allCards.filter { card ->
            val matchesRarity = rarity == null || card.rarity == rarity
            val matchesQuery = query.isBlank() ||
                card.name.contains(query, ignoreCase = true) ||
                card.creatureName.contains(query, ignoreCase = true)
            matchesRarity && matchesQuery
        }
    }

    val ownedCards: Flow<List<Card>> = collectionRepository.getOwnedCards()
    val missingCards: Flow<List<Card>> = collectionRepository.getMissingCards()
    val collectionProgress: Flow<Float> = collectionRepository.collectionProgress()

    fun setRarityFilter(rarity: CardRarity?) {
        _selectedRarity.value = rarity
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun addCard(cardId: String, quantity: Int = 1) {
        viewModelScope.launch {
            cardRepository.addCard(cardId, quantity)
        }
    }
}
