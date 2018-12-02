package br.com.brqtest.viewkotlin

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.widget.AdapterView
import android.widget.ListView
import br.com.brqtest.R
import br.com.brqtest.adapterkotlin.ClienteAdapter
import br.com.brqtest.databasekotlin.DatabaseHelper
import br.com.brqtest.databasekotlin.dao.ClienteDao
import br.com.brqtest.modelkotlin.Cliente
import br.com.brqtest.viewkotlin.ClienteDetailActivity
import java.io.Serializable
import java.sql.SQLException


class MainActivity : AppCompatActivity() {
    private var lstView: ListView? = null
    private lateinit var clientes: List<Cliente>
    private var clienteAdapter: ClienteAdapter? = null

    private var dh: DatabaseHelper? = null
    private var clienteDao: ClienteDao? = null


    private val detailCustomer = AdapterView.OnItemClickListener { parent, view, position, id ->
        val cliente = lstView!!.getItemAtPosition(position) as Cliente
        val i = Intent(applicationContext, ClienteDetailActivity::class.java)
        i.putExtra(CLIENTE_DETAIL, cliente as Serializable)
        startActivity(i)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fab = findViewById<FloatingActionButton>(R.id.fabNovoCliente)
        fab.setOnClickListener {
            val i = Intent(this@MainActivity, ClienteDetailActivity::class.java)
            startActivity(i)
        }

        dh = DatabaseHelper(this@MainActivity)
        try {
            clienteDao = ClienteDao(dh!!.connectionSource)
            clientes = clienteDao!!.queryForAll()
        } catch (e: SQLException) {
            e.printStackTrace()
        }

        lstView = findViewById(R.id.lstClientes)

        lstView!!.onItemClickListener = detailCustomer

        createOrUpdateListView()

    }


    private fun createOrUpdateListView() {

        if (clienteAdapter == null) {

            clienteAdapter = ClienteAdapter(this, clientes)
            lstView!!.adapter = clienteAdapter

        } else {
            clienteAdapter!!.notifyDataSetChanged()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    companion object {

        private val CLIENTE_DETAIL = "CLIENTE_DETAIL"
    }
}
