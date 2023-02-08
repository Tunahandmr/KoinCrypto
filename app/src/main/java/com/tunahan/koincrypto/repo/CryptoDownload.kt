package com.tunahan.koincrypto.repo

import com.tunahan.koincrypto.Resource
import com.tunahan.koincrypto.model.Crypto

interface CryptoDownload {
    suspend fun downloadCryptos(): Resource<List<Crypto>>
}