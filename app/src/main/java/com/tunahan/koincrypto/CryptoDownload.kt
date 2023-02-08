package com.tunahan.koincrypto

interface CryptoDownload {
    suspend fun downloadCryptos(): Resource<List<Crypto>>
}