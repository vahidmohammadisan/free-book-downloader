package ir.vahidmohammadisan.base.presentation.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import ir.vahidmohammadisan.base.presentation.BooksNavigationFactory
import ir.vahidmohammadisan.base.presentation.contract.BooksUiState
import ir.vahidmohammadisan.core.navigation.NavigationFactory
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
internal object BooksViewModelModule {

    @Provides
    fun provideInitialBooksUiState(): BooksUiState = BooksUiState()
}

@Module
@InstallIn(SingletonComponent::class)
internal interface BooksSingletonModule {

    @Singleton
    @Binds
    @IntoSet
    fun bindBooksNavigationFactory(factory: BooksNavigationFactory): NavigationFactory
}
