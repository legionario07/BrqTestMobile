package br.com.brqtest.viewkotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import br.com.brqtest.R
import br.com.brqtest.modelkotlin.Cliente

class MainActivity : AppCompatActivity() {

    var lstClientes:ListView? = null
    var clientes:List<Cliente>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
