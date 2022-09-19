package com.example.jobreadinesschallenge.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jobreadinesschallenge.*
import com.example.jobreadinesschallenge.api.RetrofitClient
import com.example.jobreadinesschallenge.bestsellers.BestSellersService
import com.example.jobreadinesschallenge.categories.CategoriesModel
import com.example.jobreadinesschallenge.categories.CategoriesService
import com.example.jobreadinesschallenge.databinding.ActivityBestSellersBinding
import com.example.jobreadinesschallenge.infra.ProjectConstants
import com.example.jobreadinesschallenge.items.ItemsDetailsModel
import com.example.jobreadinesschallenge.items.ItemsService
import com.example.jobreadinesschallenge.view.BestSellersAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BestSellersActivity : AppCompatActivity() {

    private lateinit var binding : ActivityBestSellersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /* View Binding */
        binding = ActivityBestSellersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Escondendo Actionbar padrão do Android
        supportActionBar?.hide()

        // Ação de encerrar a Activity quando clicado na imagem de retorno
        binding.toolbar.iwBack.setOnClickListener { finish() }

        /* RECYCLERVIEW */
        val recycler: RecyclerView = findViewById(R.id.recycler_activities)
        recycler.layoutManager = LinearLayoutManager(this)
        //recycler.setHasFixedSize(true)

        /* CALLBACKS CHAIN */
        // getCategoriesData
            // @saída: id da categoria buscada pelo editText da MainActivity
        // getBestSellersData
            // @entrada: retorno da função anterior
            // @saída: String composta pelos 20 ids de itens mais buscados, separados por vírgula
        // getItemsData
            // @entrada: retorno da função anterior
            // @saída: lista dos itens detalhados, passado para o adapter do RecyclerView
        getCategoriesData {
                    categoryId : String -> getBestSellersData(categoryId) {
                        itemsIds : String -> getItemsData(itemsIds) {
                            itemsData : List<ItemsDetailsModel> -> recycler.adapter = BestSellersAdapter(itemsData)
                }
            }
        }

    }

    /* Função para mostrar o dado digitado na MainActivity */
    private fun showSearchedItems() {
        val bestSellers = SecurityPreferences(this).getData("SEARCH ARGUMENT")
        binding.twSearched.text = bestSellers
    }

    /* Função referente ao Get Preditor do Postman */
    // função de callback, relacionada ao service que traz o retorno da API
    // @entrada: recebe a String utilizada como busca na Main Activity
    // @saída: a função como parâmetro irá retornar o ID do item buscado como String
    private fun getCategoriesData(onResult: (String) -> Unit) {
        // Conectar o Retrofit ao Serviço
        val categoriesService = RetrofitClient.createService(CategoriesService::class.java)

        // Por se tratar de chamada de API, deve ser assíncrona. O Retrofit nos ajuda com isso
        val categoriesCall = categoriesService.findIdResult(
            SecurityPreferences(this).getData("SEARCH ARGUMENT"))

        categoriesCall.enqueue(object : Callback<List<CategoriesModel>> {
            override fun onResponse(call: Call<List<CategoriesModel>>, response: Response<List<CategoriesModel>>) {
                val categoryList = response.body()
                val categoryId = categoryList?.map { it.categoryId }
                if (categoryId != null) {
                    return onResult(categoryId.joinToString())
                }
            }

            override fun onFailure(call: Call<List<CategoriesModel>>, t: Throwable) {
                showToast()
                Log.e("ERROR: ", "$t")
            }

        })
    }

    /* Função referente ao Get TOP 20 do Postman */
    // função de callback, relacionada ao service que traz o retorno da API
    // @entrada: recebe o id gerado pela função getCategoriesData
    // @saída: a função como parâmetro irá retornar uma String, formada pelos IDs dos items relacionados
    private fun getBestSellersData(categoryId: String, onResult: (String) -> Unit) {
        // Conectar o Retrofit ao Serviço
        val bestSellersService = RetrofitClient.createService(BestSellersService::class.java)

        // Por se tratar de chamada de API, deve ser assíncrona. O Retrofit nos ajuda com isso
        val bestSellersCall = bestSellersService.showBestSellers(categoryId)

        Log.d("CATEGORY ID:", categoryId)

        bestSellersCall.enqueue(object : Callback<BestSellersModel> {
            override fun onResponse(call: Call<BestSellersModel>, response: Response<BestSellersModel>) {
                val bestSellersList = response.body()?.bestSellerDetail
                val itemsId = bestSellersList?.filter { it.itemType == "ITEM" }
                        ?.map { it.itemId }
                showSearchedItems()
                return onResult(itemsId?.joinToString(",") ?: "")
            }

            override fun onFailure(call: Call<BestSellersModel>, t: Throwable) {
                showToast()
                Log.e("ERROR: ", "$t")
            }

        })
    }

    /* Função referente ao Get MULTIGET do Postman */
    // função de callback, relacionada ao service que traz o retorno da API
    // @entrada: recebe a listagem de ids geradas pela função getBestSellersData
    // @saída: a função como parâmetro irá retornar a listagem detalhada dos itens do JSON
    private fun getItemsData(idsList: String, onResult: (List<ItemsDetailsModel>) -> Unit) {
        // Conectar o Retrofit ao Serviço
        val itemsService = RetrofitClient.createService(ItemsService::class.java)

        // Por se tratar de chamada de API, deve ser assíncrona. O Retrofit nos ajuda com isso
        val itemsCall = itemsService.listItems(idsList)

        itemsCall.enqueue(object : Callback<List<ItemsDetailsModel>> {
            override fun onResponse(call: Call<List<ItemsDetailsModel>>, response: Response<List<ItemsDetailsModel>>) {
                val items = response.body()
                if (items != null)
                    return onResult(items)
                else {
                    finish()
                    showToast()
                }
            }

            override fun onFailure(call: Call<List<ItemsDetailsModel>>, t: Throwable) {
                showToast()
                Log.e("ERROR: ", "$t")
            }

        })
    }

    /* Função para Toast quando a busca não é encontrada */
    private fun showToast() {
        Toast.makeText(applicationContext, ProjectConstants.RETROFIT.ERROR_RETURN, Toast.LENGTH_SHORT).show()
    }
}