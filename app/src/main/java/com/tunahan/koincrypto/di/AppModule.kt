package com.tunahan.koincrypto

import com.tunahan.koincrypto.repo.CryptoDownload
import com.tunahan.koincrypto.repo.CryptoDownloadImpl
import com.tunahan.koincrypto.service.CryptoAPI
import com.tunahan.koincrypto.viewmodel.CryptoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    //singleton module
    single {
        val BASE_URL = "https://raw.githubusercontent.com/"

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CryptoAPI::class.java)
    }

    single<CryptoDownload> {
        CryptoDownloadImpl(get())
    }

    viewModel{
        CryptoViewModel(get())
    }
    //single aksine her inject edildiğinde yeni bir nesne oluşturulur
    factory {

    }
}