package com.example.jobreadinesschallenge.categories

import com.example.jobreadinesschallenge.infra.ProjectConstants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface CategoriesService {

    @Headers("Authorization: Bearer ${ProjectConstants.RETROFIT.TOKEN}")
    @GET("sites/MLB/domain_discovery/search?limit=1")
    fun findIdResult(@Query("q") search: String): Call<List<CategoriesModel>>
}