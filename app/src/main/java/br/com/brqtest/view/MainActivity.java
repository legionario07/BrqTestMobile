package br.com.brqtest.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import br.com.brqtest.R;
import br.com.brqtest.adapter.ClienteAdapter;
import br.com.brqtest.model.Cliente;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    private ListView lstView;
    private List<Cliente> clientes;
    private ClienteAdapter clienteAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        builderClientes();

        lstView = findViewById(R.id.lstClientes);

        lstView.setOnItemClickListener(detailCustomer);

        createOrUpdateListView();

    }

    private void builderClientes() {
        clientes = new ArrayList<>();
        Cliente cliente = new Cliente();
        cliente.setNameFull("PAULO SERGIO MOREIRA DOS SANTOS DIAS");
        cliente.setId(1);
        cliente.setCpf("1122");
        cliente.setDataDeNascimento(new Date());

        Cliente cliente1 = new Cliente();
        cliente1.setNameFull("PAULO SERGIO MOREIRA DOS SANTOS DIAS 2");
        cliente1.setId(2);
        cliente1.setCpf("1122");
        cliente1.setDataDeNascimento(new Date());

        Cliente cliente2 = new Cliente();
        cliente2.setNameFull("PAULO SERGIO MOREIRA DOS SANTOS DIAS");
        cliente2.setId(3);
        cliente2.setCpf("1122");
        cliente2.setDataDeNascimento(new Date());

        Cliente cliente3 = new Cliente();
        cliente3.setNameFull("PAULO SERGIO MOREIRA DOS SANTOS DIAS 2");
        cliente3.setId(4);
        cliente3.setCpf("1122");
        cliente3.setDataDeNascimento(new Date());

        clientes.add(cliente);
        clientes.add(cliente1);
        clientes.add(cliente2);
        clientes.add(cliente3);
    }

    private AdapterView.OnItemClickListener detailCustomer = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Cliente cliente = (Cliente) lstView.getItemAtPosition(position);
            Intent i = new Intent(getApplicationContext(), ClienteDetailActivity.class);
            i.putExtra("CLIENTE_DETAIL", (Serializable) cliente);
            startActivity(i);

        }
    };


    private void createOrUpdateListView(){

        if (clienteAdapter == null){

            clienteAdapter = new ClienteAdapter(this,clientes);
            lstView.setAdapter(clienteAdapter);

        }else{
            clienteAdapter.notifyDataSetChanged();
        }
    }
}
