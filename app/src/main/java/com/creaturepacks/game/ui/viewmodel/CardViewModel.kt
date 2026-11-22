package com.creaturepacks.game.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.creaturepacks.game.domain.model.Card
import com.creaturepacks.game.domain.repository.CardRepository
import kotlinx.coroutines.flow.Flow

class CardViewModel(
    private val cardRepository: CardRepository
) : ViewModel() {

    fun getCard(cardId: String): Flow<Card?> = cardRepository.getCard(cardId)
}
