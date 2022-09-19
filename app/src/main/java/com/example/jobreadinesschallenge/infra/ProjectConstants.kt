package com.example.jobreadinesschallenge.infra

class ProjectConstants {

    object RETROFIT {
        // Obs.: para o Retrofit, a URL deve sempre terminar em barra
        const val BASE_URL = "https://api.mercadolibre.com/"
        const val TOKEN = "APP_USR-3512365239789244-091908-f3639d0e26f530f10a8d89a2006ebcd9-62019522"
        const val ERROR_RETURN = "Não há itens nessa categoria"
    }

    object TOAST {
        const val CONFIRMATION = "Item adicionado com sucesso!"
        const val LOG_TITLE = "Lista de favoritos"
        const val LIST = "Fim da lista"
    }

}