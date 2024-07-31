package ir.vahidmohammadisan.core.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.vahidmohammadisan.bookdownloader.core.BuildConfig
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    private const val INTERCEPTOR_LOGGING_NAME = "INTERCEPTOR_LOGGING"

    @Provides
    @Named(INTERCEPTOR_LOGGING_NAME)
    fun provideHttpLoggingInterceptor(): Interceptor {
        return if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        } else {
            noOpInterceptor()
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @Named(INTERCEPTOR_LOGGING_NAME) loggingInterceptor: Interceptor,
    ): OkHttpClient {
        return OkHttpClient
            .Builder()
            .apply {
                addNetworkInterceptor(loggingInterceptor)
            }
            .build()
    }

    @Provides
    @Singleton
    @BooksMainApi
    fun provideMainAPiRetrofit(
        okHttpClient: OkHttpClient,
    ): Retrofit {
        val json = Json {
            ignoreUnknownKeys = true
        }
        val contentType = "application/json".toMediaType()

        return Retrofit
            .Builder()
            .addConverterFactory(json.asConverterFactory(contentType))
            .baseUrl(BuildConfig.BOOKS_MAIN_API)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    @BooksSecondApi
    fun provideSecondApiRetrofit(
        okHttpClient: OkHttpClient,
    ): Retrofit {
        val json = Json {
            ignoreUnknownKeys = true
        }
        val contentType = "application/json".toMediaType()

        return Retrofit
            .Builder()
            .addConverterFactory(json.asConverterFactory(contentType))
            .baseUrl(BuildConfig.BOOKS_SECOND_API)
            .client(okHttpClient)
            .build()
    }


    private fun noOpInterceptor(): Interceptor {
        return Interceptor { chain ->
            chain.proceed(chain.request())
        }
    }
}
