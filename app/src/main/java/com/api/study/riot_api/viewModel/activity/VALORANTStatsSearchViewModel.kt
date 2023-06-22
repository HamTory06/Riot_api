package com.api.study.riot_api.viewModel.activity

import android.util.Log
import androidx.lifecycle.ViewModel
import com.api.study.riot_api.data.App
import com.api.study.riot_api.data.network.retrofit.client.ClientRetrofit
import com.api.study.riot_api.data.network.retrofit.riot.RiotApi
import com.api.study.riot_api.data.network.retrofit.`val`.VALORANTapi
import com.api.study.riot_api.data.network.retrofit.`val`.response.valStatusResponse.ValStatusResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class VALORANTStatsSearchViewModel: ViewModel() {
    companion object {
        const val API_KEY = "RGAPI-2d419dcd-91f1-41db-b40c-eb3946710b4f"
    }

    private var krRetrofitInstance: VALORANTapi = ClientRetrofit.krGetInstance().create(VALORANTapi::class.java)
    private var asiarRetrofitInstance:  RiotApi = ClientRetrofit.AsiarGetInstance().create(RiotApi::class.java)
    private var ddragonGetInstance: RiotApi = ClientRetrofit.ddragonGetInstance().create(RiotApi::class.java)


    private val viewModelScope = CoroutineScope(Dispatchers.Main)

    fun getCountry(){
        var data: ValStatusResponse
        CoroutineScope(Dispatchers.IO).async{
            data = krRetrofitInstance.getValStatus(API_KEY)
            App.prefs.locale = data.id
        }
    }

    fun setInputUserName(text: String) = viewModelScope.launch {

        CoroutineScope(Dispatchers.IO).async {
            val gameName = text.split("#")[0]
            val tagLine = text.split("#")[1]
            App.prefs.valpuuid = asiarRetrofitInstance.getUserPuuId(gameName, tagLine, API_KEY).puuId

            val data = async {
                krRetrofitInstance.getMatchid(App.prefs.valpuuid.toString(), API_KEY)
            }

            Log.d("상태",data.await().toString())
        }
    }
}