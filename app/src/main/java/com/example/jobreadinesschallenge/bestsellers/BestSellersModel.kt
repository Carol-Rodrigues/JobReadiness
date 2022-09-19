package com.example.jobreadinesschallenge

import com.google.gson.annotations.SerializedName

class BestSellerDetailsModel {
    @SerializedName("id")
    var itemId: String = ""

    @SerializedName("type")
    var itemType: String = ""
}

class BestSellersModel {

    @SerializedName("content")
    var bestSellerDetail : List<BestSellerDetailsModel> = emptyList()
}