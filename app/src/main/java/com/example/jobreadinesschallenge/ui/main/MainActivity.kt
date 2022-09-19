package com.example.jobreadinesschallenge.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.jobreadinesschallenge.R
import com.example.jobreadinesschallenge.SecurityPreferences
import com.example.jobreadinesschallenge.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ViewBinding para acessar elementos da view
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Tornando a imagem de busca um elemento clicável
        binding.iwSearch.setOnClickListener {
            // Buscar e salvar os dados digitados
            saveSearchArg()
        }

        // Escondendo actionbar padrão do Android
        supportActionBar?.hide()


        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }

    private fun saveSearchArg() {
        val searchArgument = binding.etSearch.text.toString()

        if (searchArgument != "") {

            SecurityPreferences(this).storeData("SEARCH ARGUMENT", searchArgument)

            val intent = Intent(this, BestSellersActivity::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(this, "Digite algo para buscar", Toast.LENGTH_SHORT).show()
        }
    }
}