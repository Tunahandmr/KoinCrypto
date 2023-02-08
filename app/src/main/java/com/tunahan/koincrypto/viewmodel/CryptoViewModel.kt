package com.tunahan.koincrypto.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tunahan.koincrypto.Resource
import com.tunahan.koincrypto.model.Crypto
import com.tunahan.koincrypto.repo.CryptoDownload
import kotlinx.coroutines.*

class CryptoViewModel(
    private val cryptoDownloadRepository: CryptoDownload
) : ViewModel() {

    val cryptoList = MutableLiveData<Resource<List<Crypto>>>()
    val cryptoError = MutableLiveData<Resource<Boolean>>()
    val cryptoLoading = MutableLiveData<Resource<Boolean>>()
    private var job: Job? = null

    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Error: ${throwable.localizedMessage}")
        cryptoError.value = Resource.error(throwable.localizedMessage ?: "Error", true)
    }

    fun getDataFromAPI() {
        cryptoLoading.value = Resource.loading(true)

        /*  viewModelScope.launch(Dispatchers.IO+exceptionHandler){

          }*/

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val resource = cryptoDownloadRepository.downloadCryptos()
            withContext(Dispatchers.Main) {
                resource.data?.let {
                    cryptoList.value = resource
                    cryptoLoading.value = Resource.loading(false)
                    cryptoError.value = Resource.error("", false)
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}