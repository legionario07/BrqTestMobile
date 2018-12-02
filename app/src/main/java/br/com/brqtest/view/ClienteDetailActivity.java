package br.com.brqtest.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;
import br.com.brqtest.R;
import br.com.brqtest.deserializers.CEPDeserializer;
import br.com.brqtest.model.Cliente;
import br.com.brqtest.model.Endereco;
import br.com.brqtest.services.ApiCEPRetrofit;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ClienteDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText inpNome, inpID, inpCPF, inpDataDeNascimento, inpCEP, inpLogradouro,
            inpNumero, inpBairro, inpCidade, inpUF;

    private ImageButton imgSalvar, imgDeletar;
    private Cliente cliente;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private ProgressBar progressBar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_customer);


        inpID = findViewById(R.id.inpID);
        inpNome = findViewById(R.id.inpName);
        inpCPF = findViewById(R.id.inpCpf);
        inpDataDeNascimento = findViewById(R.id.inpDataDeNascimento);
        inpCEP = findViewById(R.id.inpCEP);
        inpLogradouro = findViewById(R.id.inpLogradouro);
        inpNumero = findViewById(R.id.inpNumero);
        inpBairro = findViewById(R.id.inpBairro);
        inpCidade = findViewById(R.id.inpCidade);
        inpUF = findViewById(R.id.inpUF);
        imgSalvar = findViewById(R.id.imgSalvar);
        imgDeletar = findViewById(R.id.imgDeletar);

        if (savedInstanceState != null) {
            restoreBundle(savedInstanceState);
        }

        if (getIntent().getExtras()!=null && getIntent().getExtras().containsKey("CLIENTE_DETAIL")) {
            cliente = (Cliente) getIntent().getSerializableExtra("CLIENTE_DETAIL");
            fillDataInView();
        } else {
            cliente = new Cliente();
            imgDeletar.setVisibility(View.INVISIBLE);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, 0,0);
            imgDeletar.setLayoutParams(layoutParams);
        }



        imgSalvar.setOnClickListener(this);
        imgDeletar.setOnClickListener(this);

        Gson gson = new GsonBuilder().registerTypeAdapter(Endereco.class, new CEPDeserializer()).create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiCEPRetrofit.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        final ApiCEPRetrofit service = retrofit.create(ApiCEPRetrofit.class);

        inpCEP.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus) {
                    // code to execute when EditText loses focus

                    progressBar = new ProgressBar(getApplicationContext());
                    progressBar.setVisibility(View.VISIBLE);

                    Call<Endereco> callCEPByCEP = service.getEnderecoByCEP(inpCEP.getText().toString().replace(".", "").replace("-", ""));

                    callCEPByCEP.enqueue(new Callback<Endereco>() {
                        @Override
                        public void onResponse(Call<Endereco> call, Response<Endereco> response) {
                                Endereco cep = response.body();
                                fillEnderecoInView(cep);

                            progressBar.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onFailure(Call<Endereco> call, Throwable t) {


                        }
                    });

                }
            }
        });

    }

    private void restoreBundle(Bundle savedInstanceState) {

        inpNome.setText(savedInstanceState.getString("inpNomeSaved"));
        inpCPF.setText(savedInstanceState.getString("inpCPFSaved"));
        inpDataDeNascimento.setText(savedInstanceState.getString("inpDataDeNascimentoSaved"));
        inpCEP.setText(savedInstanceState.getString("inpCEPSaved"));
        inpLogradouro.setText(savedInstanceState.getString("inpLogradouroSaved"));
        inpNumero.setText(savedInstanceState.getString("inpNumeroSaved"));
        inpBairro.setText(savedInstanceState.getString("inpBairroSaved"));
        inpCidade.setText(savedInstanceState.getString("inpCidadeSaved"));
        inpUF.setText(savedInstanceState.getString("inpUFSaved"));
    }

    private void fillEnderecoInView(Endereco endereco) {

        inpCEP.setText(endereco.getCep());
        inpLogradouro.setText(endereco.getLogradouro());
        inpNumero.setText(endereco.getNumero());
        inpBairro.setText(endereco.getBairro());
        inpCidade.setText(endereco.getLocalidade());
        inpUF.setText(endereco.getUf());
    }

    private void fillDataInView() {

        inpID.setText(cliente.getId().toString());
        inpNome.setText(cliente.getNameFull().toUpperCase());
        inpCPF.setText(cliente.getCpf());
        inpDataDeNascimento.setText(sdf.format(cliente.getDataDeNascimento()));
        inpCEP.setText(cliente.getEndereco().getCep());
        inpLogradouro.setText(cliente.getEndereco().getLogradouro());
        inpNumero.setText(cliente.getEndereco().getNumero());
        inpBairro.setText(cliente.getEndereco().getBairro());
        inpCidade.setText(cliente.getEndereco().getCidade());
        inpUF.setText(cliente.getEndereco().getUf());

    }

    private void fillDataInEntity() throws ParseException {

        cliente.setNameFull(inpNome.getText().toString().toUpperCase().trim());
        cliente.setCpf(inpCPF.getText().toString().trim());
        cliente.setDataDeNascimento(sdf.parse(inpDataDeNascimento.getText().toString().trim()));
        Endereco endereco = new Endereco();
        endereco.setCep(inpCEP.getText().toString().trim());
        endereco.setLogradouro(inpLogradouro.getText().toString().trim());
        endereco.setNumero(inpNumero.getText().toString().trim());
        endereco.setBairro(inpBairro.getText().toString().trim());
        endereco.setCidade(inpCidade.getText().toString().trim());
        endereco.setUf(inpUF.getText().toString().trim());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("inpNomeSaved", inpNome.getText().toString());
        outState.putString("inpCPFSaved", inpCPF.getText().toString());
        outState.putString("inpDataDeNascimentoSaved", inpDataDeNascimento.getText().toString());
        outState.putString("inpCEPSaved", inpCEP.getText().toString());
        outState.putString("inpLogradouroSaved", inpLogradouro.getText().toString());
        outState.putString("inpNumeroSaved", inpNumero.getText().toString());
        outState.putString("inpBairroSaved", inpBairro.getText().toString());
        outState.putString("inpCidadeSaved", inpCidade.getText().toString());
        outState.putString("inpUFSaved", inpUF.getText().toString());

        super.onSaveInstanceState(outState);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.imgSalvar:

                String erro = validarDados();
                if (erro.length() != 0) {
                    Toast.makeText(this, erro, Toast.LENGTH_LONG).show();
                    return;
                }

                try {
                    fillDataInEntity();
                } catch (Exception e) {
                    Toast.makeText(this, "Verifique a Data de Nascimento Digitada", Toast.LENGTH_LONG).show();
                    return;
                }


                break;

            case R.id.imgDeletar:

                finish();

                break;
        }


    }

    private String validarDados() {

        StringBuilder retorno = new StringBuilder();

        if (inpNome.getText().toString().length() == 0) {
            retorno.append("O Nome deve ser Preenchido");
        } else if (inpCPF.getText().toString().length() == 0) {
            retorno.append("O CPF deve ser Preenchido");
        } else if (inpDataDeNascimento.getText().toString().length() == 0) {
            retorno.append("A Data de Nascimento deve ser Preenchida");
        } else if (inpCEP.getText().toString().length() == 0) {
            retorno.append("O CEP deve ser preenchido");
        } else if (inpLogradouro.getText().toString().length() == 0) {
            retorno.append("O Logradouro deve ser Preenchido");
        } else if (inpBairro.getText().toString().length() == 0) {
            retorno.append("O Bairro deve ser Preenchido");
        } else if (inpCidade.getText().toString().length() == 0) {
            retorno.append("A cidade deve ser Preenchida");
        } else if (inpUF.getText().toString().length() == 0) {
            retorno.append("A UF deve ser Preenchida");
        }

        return retorno.toString();


    }

    @Override
    public void finish() {
        super.finish();

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);

    }
}
