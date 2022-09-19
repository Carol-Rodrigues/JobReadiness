package com.example.jobreadinesschallenge.bestsellers

import com.example.jobreadinesschallenge.BestSellersModel
import com.example.jobreadinesschallenge.infra.ProjectConstants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface BestSellersService {

    @Headers("Authorization: Bearer ${ProjectConstants.RETROFIT.TOKEN}")
    @GET("highlights/MLB/category/{categoryId}")
    fun showBestSellers(@Path("categoryId") categoryId: String) : Call<BestSellersModel>
}