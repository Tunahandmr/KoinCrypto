package com.tunahan.koincrypto.repo

import com.tunahan.koincrypto.Resource
import com.tunahan.koincrypto.model.Crypto
import com.tunahan.koincrypto.service.CryptoAPI

class CryptoDownloadImpl(private val api: CryptoAPI): CryptoDownload {
    override suspend fun downloadCryptos(): Resource<List<Crypto>> {

        return try {
            val response = api.getData()
            if (response.isSuccessful){

                response.body()?.let {
                    return@let Resource.success(it)
                }?: Resource.error("Error", null)
            }else{
                Resource.error("Error", null)
            }
        }catch (e:Exception){
            Resource.error("No Data!", null)
        }
    }

}