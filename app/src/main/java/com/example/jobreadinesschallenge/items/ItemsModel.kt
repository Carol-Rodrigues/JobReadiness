package com.example.jobreadinesschallenge.items

import com.google.gson.annotations.SerializedName

class ItemsDetailsModel {

    @SerializedName("body")
    var itemDetailed: ItemsModel = ItemsModel()

}

class ItemsModel {

    // Dados da resposta JSON da API que usaremos no app
    // As variáveis são criadas com quaisquer nomes. É na anotação que indicamos o campos do JSON

    @SerializedName("id")
    var itemId: String = ""

    @SerializedName("title")
    var itemTitle: String = ""

    @SerializedName("price")
    var itemPrice: Double = 0.00

    @SerializedName("secure_thumbnail")
    var itemThumbnail: String = ""

    @SerializedName("pictures")
    var pictures: List<PictureModel> = emptyList()

    @SerializedName("category_id")
    var categoryId: String = ""

}

class PictureModel {
    @SerializedName("secure_url")
    var itemPicture: String = ""
}