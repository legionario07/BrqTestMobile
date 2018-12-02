package br.com.brqtest.serviceskotlin

import br.com.brqtest.modelkotlin.Endereco
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiCEPRetrofit {


    @GET("{CEP}/json/")
    fun getEnderecoByCEP(@Path("CEP") CEP: String): Call<Endereco>

    companion object {

        val BASE_URL = "https://viacep.com.br/ws/"
    }

}
