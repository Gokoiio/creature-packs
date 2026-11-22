package com.creaturepacks.game.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creaturepacks.game.data.sample.BOOSTER_SERIES
import com.creaturepacks.game.data.sample.allCards
import com.creaturepacks.game.domain.model.BoosterSeries
import com.creaturepacks.game.domain.model.Card
import com.creaturepacks.game.domain.model.CardRarity
import com.creaturepacks.game.domain.repository.CardRepository
import com.creaturepacks.game.domain.repository.PlayerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.Random

class ShopViewModel(
    private val cardRepository: CardRepository,
    private val playerRepository: PlayerRepository
) : ViewModel() {

    val allSeries: Flow<List<BoosterSeriesUi>> = combine(
        playerRepository.playerStats()
    ) { stats ->
        BOOSTER_SERIES.map { series ->
            val unlocked = series.isUnlocked || series.unlockRequirementSeries?.let { req ->
                // Check if prerequisite series is "completed" (placeholder logic)
                stats.uniqueCards > 10 // simplified
            } ?: true

            BoosterSeriesUi(
                code = series.code,
                title = series.title,
                description = series.description,
                price = series.price,
                illustration = series.illustration,
                isUnlocked = unlocked,
                canAfford = stats.credits >= series.price
            )
        }
    }

    private val _selectedSeries = MutableStateFlow<BoosterSeries?>(null)
    val selectedSeries: Flow<BoosterSeries?> = _selectedSeries

    fun selectSeries(series: BoosterSeries?) {
        _selectedSeries.value = series
    }

    fun tryPurchaseSeries(series: BoosterSeries) {
        viewModelScope.launch {
            val success = playerRepository.spendCredits(series.price)
            if (success) {
                // TODO: Open booster animation in future milestone
                // For now just add a placeholder card
                cardRepository.addCard("gen_001")
            }
        }
    }

    fun addCardsFromPack(cards: List<Card>) {
        viewModelScope.launch {
            for (card in cards) {
                cardRepository.addCard(card.id, 1)
                // Check for duplicates and convert to fragments
                val quantity = cardRepository.getCardQuantity(card.id)
                if (quantity > 1) {
                    // Convert extra copies to fragments
                    val fragments = (quantity - 1) * getFragmentValue(card.rarity)
                    playerRepository.addFragments(fragments)
                }
            }
        }
    }

    private fun getFragmentValue(rarity: CardRarity): Int = when (rarity) {
        CardRarity.COMMON -> 5
        CardRarity.UNCOMMON -> 15
        CardRarity.RARE -> 50
        CardRarity.EPIC -> 150
        CardRarity.LEGENDARY -> 500
        CardRarity.SECRET -> 2000
    }

    fun openPack(series: BoosterSeries): List<Card> {
        return generatePackContents(series)
    }

    private fun generatePackContents(series: BoosterSeries): List<Card> {
        val seriesCards = when (series.code) {
            "genesis" -> com.creaturepacks.game.data.sample.GENESIS_CARDS
            "ember_reach" -> com.creaturepacks.game.data.sample.EMBER_REACH_CARDS
            "frost_veil" -> com.creaturepacks.game.data.sample.FROST_VEIL_CARDS
            "crystal_spire" -> com.creaturepacks.game.data.sample.CRYSTAL_SPIRE_CARDS
            else -> com.creaturepacks.game.data.sample.GENESIS_CARDS
        }
        
        // Apply rarity-weighted selection with pity system
        return openBoosterPack(seriesCards)
    }

    private fun openBoosterPack(pool: List<Card>): List<Card> {
        val random = Random()
        val results = mutableListOf<Card>()
        val cardsPerPack = 5
        
        // Group cards by rarity
        val byRarity = pool.groupBy { it.rarity }
        
        // Drop rates (matching CardRarity.baseDropRate)
        val dropRates = mapOf(
            CardRarity.COMMON to 0.60,
            CardRarity.UNCOMMON to 0.25,
            CardRarity.RARE to 0.10,
            CardRarity.EPIC to 0.04,
            CardRarity.LEGENDARY to 0.009,
            CardRarity.SECRET to 0.001
        )
        
        // Pity counters (in a real implementation, these would be persisted)
        var pityRare = 0
        var pityEpic = 0
        var pityLegendary = 0
        var pitySecret = 0
        
        for (i in 0 until cardsPerPack) {
            var selectedRarity: CardRarity
            
            // Check pity timers
            if (pitySecret >= 200) {
                selectedRarity = CardRarity.SECRET
                pitySecret = 0
            } else if (pityLegendary >= 50) {
                selectedRarity = CardRarity.LEGENDARY
                pityLegendary = 0
            } else if (pityEpic >= 20) {
                selectedRarity = CardRarity.EPIC
                pityEpic = 0
            } else if (pityRare >= 10) {
                selectedRarity = CardRarity.RARE
                pityRare = 0
            } else {
                // Weighted random selection
                val roll = random.nextDouble()
                var cumulative = 0.0
                selectedRarity = CardRarity.COMMON
                
                for ((rarity, rate) in dropRates) {
                    cumulative += rate
                    if (roll <= cumulative) {
                        selectedRarity = rarity
                        break
                    }
                }
            }
            
            // Update pity counters
            when (selectedRarity) {
                CardRarity.COMMON -> {
                    pityRare++
                    pityEpic++
                    pityLegendary++
                    pitySecret++
                }
                CardRarity.UNCOMMON -> {
                    pityRare++
                    pityEpic++
                    pityLegendary++
                    pitySecret++
                }
                CardRarity.RARE -> {
                    pityRare = 0
                    pityEpic++
                    pityLegendary++
                    pitySecret++
                }
                CardRarity.EPIC -> {
                    pityEpic = 0
                    pityLegendary++
                    pitySecret++
                }
                CardRarity.LEGENDARY -> {
                    pityLegendary = 0
                    pitySecret++
                }
                CardRarity.SECRET -> {
                    pitySecret = 0
                }
            }
            
            // Select a random card of the chosen rarity
            val rarityPool = byRarity[selectedRarity] ?: byRarity[CardRarity.COMMON]!!
            val card = rarityPool.random()
            results.add(card)
        }
        
        return results
    }

    data class BoosterSeriesUi(
        val code: String,
        val title: String,
        val description: String,
        val price: Int,
        val illustration: String,
        val isUnlocked: Boolean,
        val canAfford: Boolean
    )
}
