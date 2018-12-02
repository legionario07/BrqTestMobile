package br.com.brqtest;

import android.util.Log;
import br.com.brqtest.deserializers.CEPDeserializer;
import br.com.brqtest.model.Endereco;
import br.com.brqtest.services.ApiCEPRetrofit;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(JUnit4.class)
public class ApiCEPRetrofitTest {

    private Endereco endereco;
    private ApiCEPRetrofit service;

    @Before
    public void init() {

        Gson gson = new GsonBuilder().registerTypeAdapter(Endereco.class, new CEPDeserializer()).create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiCEPRetrofit.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        service = retrofit.create(ApiCEPRetrofit.class);

    }

    @Test
    public void deveRetornarUmEndereco() {

        Call<Endereco> callCEPByCEP = service.getEnderecoByCEP("09121430");

        callCEPByCEP.enqueue(new Callback<Endereco>() {
            @Override
            public void onResponse(Call<Endereco> call, Response<Endereco> response) {
                if (response.isSuccessful()) {
                    endereco = response.body();

                }

                assertEquals("Rua Cisplatina",endereco.getLogradouro());
            }

            @Override
            public void onFailure(Call<Endereco> call, Throwable t) {
                Log.i("ERRO", t.getMessage());
            }
        });

    }

}

