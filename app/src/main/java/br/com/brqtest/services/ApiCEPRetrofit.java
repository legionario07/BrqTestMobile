package br.com.brqtest.services;

import br.com.brqtest.model.Endereco;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiCEPRetrofit {

    String BASE_URL = "https://viacep.com.br/ws/";


    @GET("{CEP}/json/")
    Call<Endereco> getEnderecoByCEP(@Path("CEP") String CEP);

}
