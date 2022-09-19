package com.example.jobreadinesschallenge.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.jobreadinesschallenge.infra.ProjectConstants
import com.example.jobreadinesschallenge.SecPreferences
import com.example.jobreadinesschallenge.databinding.ActivityBestSellersBinding
import com.example.jobreadinesschallenge.databinding.ActivityItemDetailedBinding
import com.squareup.picasso.Picasso

class ItemDetailedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityItemDetailedBinding
    private lateinit var intentLate: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /* View Binding */
        binding = ActivityItemDetailedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intentLate = intent

        // Escondendo Actionbar padrão do Android
        supportActionBar?.hide()

        // Ação de encerrar a Activity quando clicado na imagem de retorno
        //binding.toolbar.iwBack.setOnClickListener { finish() }

        var favoriteList = mutableSetOf<String>()

        // CHANGING PICTURE
        val listOfPictures = SecPreferences().getList(intentLate, "PICTURES_LIST")
        val numberOfPictures = listOfPictures?.size

        val itemId = SecPreferences().getItemId(intentLate, "ITEM_ID")
        val itemPrice = SecPreferences().getItemId(intentLate, "ITEM_PRICE")
        val itemTitle = SecPreferences().getItemId(intentLate, "ITEM_TITLE")
        var index = 0
        val itemPicture = listOfPictures?.get(index)

        // Listagem de favoritos
        // Não está atualizando a listagem
        binding.twFavorite.setOnClickListener {
            favoriteList.add(itemId ?: "")
            Toast.makeText(
                applicationContext,
                ProjectConstants.TOAST.CONFIRMATION,
                Toast.LENGTH_SHORT
            ).show()

            Log.d(ProjectConstants.TOAST.LOG_TITLE, favoriteList.joinToString(","))
        }

        // Navegação entre fotos
        binding.iwNavigateBefore.setOnClickListener {
            if (numberOfPictures != null) {
                if (index > 0) {
                    index--
                    val itemPicture = listOfPictures?.get(index)
                    Picasso.get().load(itemPicture).into(binding.iwItemPictures)
                } else {
                    Toast.makeText(applicationContext, ProjectConstants.TOAST.LIST, Toast.LENGTH_SHORT).show()
                }
            }
        }
        binding.iwNavigateAfter.setOnClickListener {
            if (numberOfPictures != null) {
                if (index < numberOfPictures - 1) {
                    index++
                    val itemPicture = listOfPictures?.get(index)
                    Picasso.get().load(itemPicture).into(binding.iwItemPictures)
                } else {
                    Toast.makeText(applicationContext, ProjectConstants.TOAST.LIST, Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.twTitleSmall.text = itemTitle
        binding.twTitleBig.text = itemTitle
        binding.twItemPrice.text = itemPrice
        Picasso.get().load(itemPicture).into(binding.iwItemPictures)


        println("AQUI: ${SecPreferences().getList(intentLate, "PICTURES_LIST")}")
    }
}