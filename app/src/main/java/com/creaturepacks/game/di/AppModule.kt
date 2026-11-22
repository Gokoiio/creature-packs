package com.creaturepacks.game.di

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.creaturepacks.game.data.local.datastore.SettingsDataStore
import com.creaturepacks.game.data.local.room.AppDatabase
import com.creaturepacks.game.data.repository.CardRepositoryImpl
import com.creaturepacks.game.data.repository.CollectionRepositoryImpl
import com.creaturepacks.game.data.repository.PlayerRepositoryImpl
import com.creaturepacks.game.data.repository.SettingsRepositoryImpl
import com.creaturepacks.game.domain.repository.CardRepository
import com.creaturepacks.game.domain.repository.CollectionRepository
import com.creaturepacks.game.domain.repository.PlayerRepository
import com.creaturepacks.game.domain.repository.SettingsRepository
import com.creaturepacks.game.ui.viewmodel.CardViewModel
import com.creaturepacks.game.ui.viewmodel.CollectionViewModel
import com.creaturepacks.game.ui.viewmodel.HomeViewModel
import com.creaturepacks.game.ui.viewmodel.PlayerViewModel
import com.creaturepacks.game.ui.viewmodel.SettingsViewModel
import com.creaturepacks.game.ui.viewmodel.ShopViewModel

class AppModule(private val application: Application) {
    private val database: AppDatabase = Room.databaseBuilder(
        application,
        AppDatabase::class.java,
        "creature_packs_db"
    ).fallbackToDestructiveMigration().build()

    private val settingsDataStore: SettingsDataStore = SettingsDataStore.apply {
        init(application)
    }

    val cardRepository: CardRepository = CardRepositoryImpl(database, application)
    val collectionRepository: CollectionRepository = CollectionRepositoryImpl(cardRepository, database)
    val playerRepository: PlayerRepository = PlayerRepositoryImpl(database, settingsDataStore)
    val settingsRepository: SettingsRepository = SettingsRepositoryImpl(settingsDataStore)

    val homeViewModel: HomeViewModel = HomeViewModel(playerRepository, settingsRepository)
    val collectionViewModel: CollectionViewModel = CollectionViewModel(collectionRepository, cardRepository)
    val shopViewModel: ShopViewModel = ShopViewModel(cardRepository, playerRepository)
    val playerViewModel: PlayerViewModel = PlayerViewModel(playerRepository, settingsRepository)
    val settingsViewModel: SettingsViewModel = SettingsViewModel(settingsRepository)
    val cardViewModel: CardViewModel = CardViewModel(cardRepository)

    fun getViewModelFactory(): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : androidx.lifecycle.ViewModel?> create(modelClass: Class<T>): T {
            return when (modelClass) {
                HomeViewModel::class.java -> homeViewModel as T
                CollectionViewModel::class.java -> collectionViewModel as T
                ShopViewModel::class.java -> shopViewModel as T
                PlayerViewModel::class.java -> playerViewModel as T
                SettingsViewModel::class.java -> settingsViewModel as T
                CardViewModel::class.java -> cardViewModel as T
                else -> throw IllegalArgumentException("Unknown ViewModel class: $modelClass")
            }
        }
    }
}
