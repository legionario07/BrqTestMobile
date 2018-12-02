package br.com.brqtest.adapterkotlin

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import br.com.brqtest.R
import br.com.brqtest.modelkotlin.Cliente
import java.text.SimpleDateFormat


class ClienteAdapter(@get:JvmName("getContext_")private val context: Context, private val clientes: List<Cliente>) :
    ArrayAdapter<Cliente>(context, 0, clientes) {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val cliente = this.clientes[position]

        convertView = LayoutInflater.from(this.context).inflate(R.layout.item_clientes, null)

        val txtNameCustomer = convertView!!.findViewById<TextView>(R.id.txtNome)
        val txtCPFCustomer = convertView.findViewById<TextView>(R.id.txtCpf)
        val txtDateOfBirth = convertView.findViewById<TextView>(R.id.txtDataDeNascimento)

        val sdf = SimpleDateFormat("dd/MM/yyyy")

        txtNameCustomer.text = cliente.nameFull!!.toUpperCase()
        txtCPFCustomer.text = cliente.cpf
        txtDateOfBirth.text = sdf.format(cliente.dataDeNascimento)

        if (position % 2 == 0) {
            txtNameCustomer.setBackgroundColor(Color.LTGRAY)
            txtCPFCustomer.setBackgroundColor(Color.LTGRAY)
            txtDateOfBirth.setBackgroundColor(Color.LTGRAY)
        }

        return convertView
    }
}
