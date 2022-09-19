package com.example.jobreadinesschallenge

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import java.util.ArrayList

class SecurityPreferences (context: Context) {

    private var security : SharedPreferences =
        context.getSharedPreferences("Job Readiness", Context.MODE_PRIVATE)

    fun storeData(key: String, str: String) {
        security.edit().putString(key, str).apply()
    }

    fun getData(key: String): String {
        // Caso eu não encontre a key, retorno o segundo parâmetro
        // Caso o valor passado seja nulo, retornamos também uma string vazia
        return security.getString(key, "") ?: ""
    }
}

class SecPreferences {

    fun storeItemId(intent: Intent, key: String, category: String) {
        intent.putExtra(key, category)
    }

    fun getItemId(intent: Intent, key: String): String? {
        return intent.getStringExtra(key)
    }

    fun saveList(intent: Intent, key: String, list: ArrayList<String>) {
        intent.putStringArrayListExtra(key, list)
    }

    fun getList(intent: Intent, key: String): ArrayList<String>? {
        return intent.getStringArrayListExtra(key)
    }
}