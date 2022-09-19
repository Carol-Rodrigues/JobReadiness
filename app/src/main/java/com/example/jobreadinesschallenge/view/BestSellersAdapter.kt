package com.example.jobreadinesschallenge.view

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jobreadinesschallenge.R
import com.example.jobreadinesschallenge.SecPreferences
import com.example.jobreadinesschallenge.items.ItemsDetailsModel
import com.example.jobreadinesschallenge.ui.main.ItemDetailedActivity
import com.squareup.picasso.Picasso
import kotlin.random.Random

class BestSellersAdapter(private val itemList : List<ItemsDetailsModel>):
        RecyclerView.Adapter<BestSellersAdapter.ItemsViewHolder>() {
    // Crio o layout. É chamado para cada item da RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_item, parent, false)

        return ItemsViewHolder(view)
    }

    // Faz a conexão, atribuindo os valores para o Layout
    override fun onBindViewHolder(holder: ItemsViewHolder, position: Int) {

        val bestSellersViewModel = itemList[position]

        holder.itemId.text = bestSellersViewModel.itemDetailed.itemId
        holder.categoryId.text = bestSellersViewModel.itemDetailed.categoryId
        holder.itemPrice.text = "$ ${bestSellersViewModel.itemDetailed.itemPrice}"
        holder.itemTitle.text = bestSellersViewModel.itemDetailed.itemTitle
        Picasso.get().load(bestSellersViewModel.itemDetailed.itemThumbnail).into(holder.itemThumb)

        holder.itemView.setOnClickListener {
                view: View -> Unit

            val context = holder.itemThumb.context
            val intent = Intent(context, ItemDetailedActivity::class.java)
            val itemId = bestSellersViewModel.itemDetailed.itemId
            val itemTitle = bestSellersViewModel.itemDetailed.itemTitle
            val itemPrice = "$ ${bestSellersViewModel.itemDetailed.itemPrice}"

            //val itemPicture = getRandomPicture(bestSellersViewModel)
            val itemPicture = bestSellersViewModel.itemDetailed.pictures[0].itemPicture

            val picList: ArrayList<String> = ArrayList(bestSellersViewModel.itemDetailed.pictures.map { it -> it.itemPicture })
            SecPreferences().saveList(intent, "PICTURES_LIST", picList)

            SecPreferences().storeItemId(intent, "ITEM_ID", itemId)
            SecPreferences().storeItemId(intent, "ITEM_PRICE", itemPrice)
            SecPreferences().storeItemId(intent, "ITEM_PICTURE", itemPicture)
            SecPreferences().storeItemId(intent, "ITEM_TITLE", itemTitle)

            context.startActivity(intent)
        }
    }

    // Verifica o tamanho da listagem e "passa" pra RecyclerView alocar o espaço de memória necessário
    override fun getItemCount(): Int {
        return itemList.size
    }

    // O itemView são os elementos que iremos definir em nosso Layout
    class ItemsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemId: TextView = itemView.findViewById(R.id.tw_item_id)
        val itemTitle: TextView = itemView.findViewById(R.id.tw_item_title)
        val itemPrice: TextView = itemView.findViewById(R.id.tw_item_price)
        val categoryId: TextView = itemView.findViewById(R.id.tw_category_id)
        val itemThumb: ImageView = itemView.findViewById(R.id.iw_thumbItem)
    }

    private fun getRandomPicture(viewModel: ItemsDetailsModel): String {
        val picturesList = viewModel.itemDetailed.pictures
        val index = Random.nextInt(picturesList.size)

        return picturesList[index].itemPicture
    }
}