package br.com.brqtest.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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
import java.util.List;

public class MainActivity extends AppCompatActivity{

    private static final String CLIENTE_DETAIL = "CLIENTE_DETAIL";
    private ListView lstView;
    private List<Cliente> clientes;
    private ClienteAdapter clienteAdapter;

    private DatabaseHelper dh;
    private ClienteDao clienteDao;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.fabNovoCliente);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ClienteDetailActivity.class);
                startActivity(i);
            }
        });

        dh = new DatabaseHelper(MainActivity.this);
        try {
            clienteDao  = new ClienteDao(dh.getConnectionSource());
            clientes = clienteDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        lstView = findViewById(R.id.lstClientes);

        lstView.setOnItemClickListener(detailCustomer);

        createOrUpdateListView();

    }


    private AdapterView.OnItemClickListener detailCustomer = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Cliente cliente = (Cliente) lstView.getItemAtPosition(position);
            Intent i = new Intent(getApplicationContext(), ClienteDetailActivity.class);
            i.putExtra(CLIENTE_DETAIL, (Serializable) cliente);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
