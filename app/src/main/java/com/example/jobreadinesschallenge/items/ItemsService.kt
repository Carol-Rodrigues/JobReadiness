package com.example.jobreadinesschallenge.items

import com.example.jobreadinesschallenge.infra.ProjectConstants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface ItemsService {

    // https://api.mercadolibre.com/items?ids=$ITEM_ID

    // O retrofit irá ajudar no tipo do retorno, para que seja possível fazer a conversão do JSON
    // em  uma lista. Por isso, usamos o tipo Call do retrofit e indicamos o tipo da conversão

    @Headers("Authorization: Bearer ${ProjectConstants.RETROFIT.TOKEN}")
    @GET("items")
    fun listItems(@Query("ids") idsList: String): Call<List<ItemsDetailsModel>>

}