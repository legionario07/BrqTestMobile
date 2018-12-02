package br.com.brqtest.viewkotlin

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.*
import br.com.brqtest.R
import br.com.brqtest.databasekotlin.DatabaseHelper
import br.com.brqtest.databasekotlin.dao.ClienteDao
import br.com.brqtest.databasekotlin.dao.EnderecoDao
import br.com.brqtest.deserializerskotlin.CEPDeserializer
import br.com.brqtest.modelkotlin.Cliente
import br.com.brqtest.modelkotlin.Endereco
import br.com.brqtest.serviceskotlin.ApiCEPRetrofit
import br.com.brqtest.utilskotlin.VerificaConexaoStrategy
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.sql.SQLException
import java.text.ParseException
import java.text.SimpleDateFormat


class ClienteDetailActivity : AppCompatActivity(), View.OnClickListener {

    private var inpNome: EditText? = null
    private var inpID: EditText? = null
    private var inpCPF: EditText? = null
    private var inpDataDeNascimento: EditText? = null
    private var inpCEP: EditText? = null
    private var inpLogradouro: EditText? = null
    private var inpNumero: EditText? = null
    private var inpBairro: EditText? = null
    private var inpCidade: EditText? = null
    private var inpUF: EditText? = null

    private var imgSalvar: ImageButton? = null
    private var imgDeletar: ImageButton? = null
    private var imgMapa: ImageButton? = null
    private var cliente: Cliente? = null
    private val sdf = SimpleDateFormat("dd/MM/yyyy")
    private var progressBar: ProgressBar? = null

    private var dh: DatabaseHelper? = null
    private var enderecoDao: EnderecoDao? = null
    private var clienteDao: ClienteDao? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_customer)

        inpID = findViewById(R.id.inpID)
        inpNome = findViewById(R.id.inpName)
        inpCPF = findViewById(R.id.inpCpf)
        inpDataDeNascimento = findViewById(R.id.inpDataDeNascimento)
        inpCEP = findViewById(R.id.inpCEP)
        inpLogradouro = findViewById(R.id.inpLogradouro)
        inpNumero = findViewById(R.id.inpNumero)
        inpBairro = findViewById(R.id.inpBairro)
        inpCidade = findViewById(R.id.inpCidade)
        inpUF = findViewById(R.id.inpUF)
        imgSalvar = findViewById(R.id.imgSalvar)
        imgDeletar = findViewById(R.id.imgDeletar)
        imgMapa = findViewById(R.id.imgMap)

        dh = DatabaseHelper(this@ClienteDetailActivity)
        try {
            clienteDao = ClienteDao(dh!!.connectionSource)
            enderecoDao = EnderecoDao(dh!!.connectionSource)
        } catch (e: SQLException) {
            e.printStackTrace()
        }

        if (savedInstanceState != null) {
            restoreBundle(savedInstanceState)
        }

        if (intent.extras != null && intent.extras!!.containsKey(CLIENTE_DETAIL)) {
            cliente = intent.getSerializableExtra(CLIENTE_DETAIL) as Cliente
            fillDataInView()
        } else {
            cliente = Cliente()
            imgDeletar!!.visibility = View.INVISIBLE
            val layoutParams = LinearLayout.LayoutParams(0, 0, 0f)
            imgDeletar!!.layoutParams = layoutParams
        }


        imgSalvar!!.setOnClickListener(this)
        imgDeletar!!.setOnClickListener(this)
        imgMapa!!.setOnClickListener(this)

        val gson = GsonBuilder().registerTypeAdapter(Endereco::class.java, CEPDeserializer()).create()

        val retrofit = Retrofit.Builder()
            .baseUrl(ApiCEPRetrofit.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        val service = retrofit.create(ApiCEPRetrofit::class.java)

        inpCEP!!.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (!VerificaConexaoStrategy.verificarConexao(this@ClienteDetailActivity)) {
                Toast.makeText(this@ClienteDetailActivity, "Sem conexão para buscar o CEP", Toast.LENGTH_LONG).show()
                return@OnFocusChangeListener
            }

            if (!hasFocus) {
                // code to execute when EditText loses focus

                progressBar = ProgressBar(applicationContext)
                progressBar!!.visibility = View.VISIBLE

                val callCEPByCEP = service.getEnderecoByCEP(inpCEP!!.text.toString().replace(".", "").replace("-", ""))

                callCEPByCEP.enqueue(object : Callback<Endereco> {
                    override fun onResponse(call: Call<Endereco>, response: Response<Endereco>) {
                        val cep = response.body()
                        fillEnderecoInView(cep!!)

                        progressBar!!.visibility = View.INVISIBLE
                    }

                    override fun onFailure(call: Call<Endereco>, t: Throwable) {


                    }
                })

            }
        }

    }

    private fun restoreBundle(savedInstanceState: Bundle) {

        inpNome!!.setText(savedInstanceState.getString("inpNomeSaved"))
        inpCPF!!.setText(savedInstanceState.getString("inpCPFSaved"))
        inpDataDeNascimento!!.setText(savedInstanceState.getString("inpDataDeNascimentoSaved"))
        inpCEP!!.setText(savedInstanceState.getString("inpCEPSaved"))
        inpLogradouro!!.setText(savedInstanceState.getString("inpLogradouroSaved"))
        inpNumero!!.setText(savedInstanceState.getString("inpNumeroSaved"))
        inpBairro!!.setText(savedInstanceState.getString("inpBairroSaved"))
        inpCidade!!.setText(savedInstanceState.getString("inpCidadeSaved"))
        inpUF!!.setText(savedInstanceState.getString("inpUFSaved"))
    }

    private fun fillEnderecoInView(endereco: Endereco) {

        inpCEP!!.setText(endereco.cep)
        inpLogradouro!!.setText(endereco.logradouro)
        inpNumero!!.setText(endereco.numero)
        inpBairro!!.setText(endereco.bairro)
        inpCidade!!.setText(endereco.localidade)
        inpUF!!.setText(endereco.uf)
    }

    private fun fillDataInView() {

        inpID!!.setText(cliente!!.id!!.toString())
        inpNome!!.setText(cliente!!.nameFull!!.toUpperCase())
        inpCPF!!.setText(cliente!!.cpf)
        inpDataDeNascimento!!.setText(sdf.format(cliente!!.dataDeNascimento))
        inpCEP!!.setText(cliente!!.endereco!!.cep)
        inpLogradouro!!.setText(cliente!!.endereco!!.logradouro)
        inpNumero!!.setText(cliente!!.endereco!!.numero)
        inpBairro!!.setText(cliente!!.endereco!!.bairro)
        inpCidade!!.setText(cliente!!.endereco!!.cidade)
        inpUF!!.setText(cliente!!.endereco!!.uf)

    }

    @Throws(ParseException::class)
    private fun fillDataInEntity() {

        cliente!!.nameFull = inpNome!!.text.toString().toUpperCase().trim { it <= ' ' }
        cliente!!.cpf = inpCPF!!.text.toString().trim { it <= ' ' }
        cliente!!.dataDeNascimento = sdf.parse(inpDataDeNascimento!!.text.toString().trim { it <= ' ' })
        val endereco = Endereco()
        endereco.cep = inpCEP!!.text.toString().trim { it <= ' ' }
        endereco.logradouro = inpLogradouro!!.text.toString().trim { it <= ' ' }
        endereco.numero = inpNumero!!.text.toString().trim { it <= ' ' }
        endereco.bairro = inpBairro!!.text.toString().trim { it <= ' ' }
        endereco.cidade = inpCidade!!.text.toString().trim { it <= ' ' }
        endereco.uf = inpUF!!.text.toString().trim { it <= ' ' }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("inpNomeSaved", inpNome!!.text.toString())
        outState.putString("inpCPFSaved", inpCPF!!.text.toString())
        outState.putString("inpDataDeNascimentoSaved", inpDataDeNascimento!!.text.toString())
        outState.putString("inpCEPSaved", inpCEP!!.text.toString())
        outState.putString("inpLogradouroSaved", inpLogradouro!!.text.toString())
        outState.putString("inpNumeroSaved", inpNumero!!.text.toString())
        outState.putString("inpBairroSaved", inpBairro!!.text.toString())
        outState.putString("inpCidadeSaved", inpCidade!!.text.toString())
        outState.putString("inpUFSaved", inpUF!!.text.toString())

        super.onSaveInstanceState(outState)


    }

    override fun onClick(v: View) {

        when (v.id) {
            R.id.imgSalvar -> {

                val erro = validarDados()
                if (erro.length != 0) {
                    Toast.makeText(this, erro, Toast.LENGTH_LONG).show()
                    return
                }

                try {
                    fillDataInEntity()
                } catch (e: Exception) {
                    Toast.makeText(this, "Verifique a Data de Nascimento Digitada", Toast.LENGTH_LONG).show()
                    return
                }

                //Eh update?
                if (cliente!!.id != null && cliente!!.id!! > 0) {
                    try {
                        enderecoDao!!.update(cliente!!.endereco)
                        clienteDao!!.update(cliente)
                    } catch (e: SQLException) {
                        e.printStackTrace()
                    }

                } else {
                    try {
                        cliente!!.endereco!!.id = enderecoDao!!.create(cliente!!.endereco)
                        clienteDao!!.create(cliente)
                    } catch (e: SQLException) {
                        e.printStackTrace()
                    }

                }

                finish()
            }

            R.id.imgDeletar -> {

                try {
                    clienteDao!!.delete(cliente)
                } catch (e: SQLException) {
                    e.printStackTrace()
                }

                finish()
            }

            R.id.imgMap -> {

                val retorno = validarEndereco()

                if (retorno.length > 0) {
                    Toast.makeText(this, retorno, Toast.LENGTH_LONG).show()
                    return
                }

                val i = Intent(this, MapsActivity::class.java)
                i.putExtra(ENDERECO, cliente!!.endereco)
                startActivity(i)
            }
        }


    }

    private fun validarDados(): String {

        val retorno = StringBuilder()

        if (inpNome!!.text.toString().length == 0) {
            retorno.append("O Nome deve ser Preenchido")
        } else if (inpCPF!!.text.toString().length == 0) {
            retorno.append("O CPF deve ser Preenchido")
        } else if (inpDataDeNascimento!!.text.toString().length == 0) {
            retorno.append("A Data de Nascimento deve ser Preenchida")
        } else {
            retorno.append(validarEndereco())
        }

        return retorno.toString()


    }

    private fun validarEndereco(): String {

        val retorno = StringBuilder()
        if (inpCEP!!.text.toString().length == 0) {
            retorno.append("O CEP deve ser preenchido")
        } else if (inpLogradouro!!.text.toString().length == 0) {
            retorno.append("O Logradouro deve ser Preenchido")
        } else if (inpBairro!!.text.toString().length == 0) {
            retorno.append("O Bairro deve ser Preenchido")
        } else if (inpCidade!!.text.toString().length == 0) {
            retorno.append("A cidade deve ser Preenchida")
        } else if (inpUF!!.text.toString().length == 0) {
            retorno.append("A UF deve ser Preenchida")
        }

        return retorno.toString()
    }

    override fun finish() {

        val i = Intent(this, MainActivity::class.java)
        startActivity(i)

        super.finish()

    }

    companion object {

        private val CLIENTE_DETAIL = "CLIENTE_DETAIL"
        private val ENDERECO = "ENDERECO"
    }
}
