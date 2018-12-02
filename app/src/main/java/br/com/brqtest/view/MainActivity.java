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
import br.com.brqtest.database.DatabaseHelper;
import br.com.brqtest.database.dao.ClienteDao;
import br.com.brqtest.database.dao.EnderecoDao;
import br.com.brqtest.model.Cliente;
import br.com.brqtest.model.Endereco;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    private ListView lstView;
    private List<Cliente> clientes;
    private ClienteAdapter clienteAdapter;

    private DatabaseHelper dh;
    private ClienteDao clienteDao;
    private EnderecoDao enderecoDao;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dh = new DatabaseHelper(MainActivity.this);
        try {
            clienteDao  = new ClienteDao(dh.getConnectionSource());
            clientes = clienteDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //getClientes();

        lstView = findViewById(R.id.lstClientes);

        lstView.setOnItemClickListener(detailCustomer);

        createOrUpdateListView();

    }

    private void getClientes() {
        dh = new DatabaseHelper(MainActivity.this);

        try {

            enderecoDao = new EnderecoDao(dh.getConnectionSource());
        } catch (SQLException e) {
            e.printStackTrace();
        }

       // builderClientes();


    }

    private void builderClientes() {
        clientes = new ArrayList<>();
        Cliente cliente = new Cliente();
        cliente.setNameFull("PAULO SERGIO MOREIRA DOS SANTOS DIAS");
        cliente.setId(1);
        cliente.setCpf("111.111.222.22");
        cliente.setDataDeNascimento(new Date());

        Endereco endereco = new Endereco();
        endereco.setCep("08773-130");
        endereco.setUf("SP");
        endereco.setBairro("MOGILAR");
        endereco.setCidade("MOGI DAS CRUZES");
        cliente.setEndereco(endereco);

        Cliente cliente1 = new Cliente();
        cliente1.setNameFull("STEVEN JOBS");
        cliente1.setId(2);
        cliente1.setCpf("333.333.333.45");
        cliente1.setDataDeNascimento(new Date());

        endereco = new Endereco();
        endereco.setCep("03251-060");
        endereco.setUf("SP");
        endereco.setBairro("INDUSTRIAL");
        endereco.setCidade("SANTO ANDRÉ");
        cliente1.setEndereco(endereco);

        Cliente cliente2 = new Cliente();
        cliente2.setNameFull("RUSSEL WILSON");
        cliente2.setId(3);
        cliente2.setCpf("444.444.555.25");
        cliente2.setDataDeNascimento(new Date());

        endereco = new Endereco();
        endereco.setCep("03251-060");
        endereco.setUf("SP");
        endereco.setBairro("INDUSTRIAL");
        endereco.setCidade("SANTO ANDRÉ");
        cliente2.setEndereco(endereco);

        Cliente cliente3 = new Cliente();
        cliente3.setNameFull("RENATO RUSSO");
        cliente3.setId(4);
        cliente3.setCpf("555.555.555.55");
        cliente3.setDataDeNascimento(new Date());
        endereco = new Endereco();
        endereco.setCep("03251-060");
        endereco.setUf("SP");
        endereco.setBairro("INDUSTRIAL");
        endereco.setCidade("SANTO ANDRÉ");
        cliente3.setEndereco(endereco);

        clientes.add(cliente);
        clientes.add(cliente1);
        clientes.add(cliente2);
        clientes.add(cliente3);

        for(Cliente c : clientes){

            try {
                c.getEndereco().setId(enderecoDao.create(c.getEndereco()));
                c.setId(clienteDao.create(c));
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
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
