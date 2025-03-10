package com.pdrarth.aplicativoconsultacep.api

import com.pdrarth.aplicativoconsultacep.Model.Endereco
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {
    //PASSAR A ROTA GET PARA CONSUMIR OS DADOS DA API
    //NESSE CONTEXTO EU ESTOU PASSANDO UMA VARIAVEL
    @GET("ws/{cep}/json/")
    //PASSAR O CAMINHO COM PATCH
    //CRIAR UM CAMINHO DEPOIS ATRAVES DE UMA VARIAVEL QUE VAI VIM DA MAIN OU DE OUTRO LUGAR
    //DEPOIS FAZER UMA CHAMADA ( ENDECO)
    fun setEndereco(@Path("cep")cep : String): retrofit2.Call<Endereco>


}