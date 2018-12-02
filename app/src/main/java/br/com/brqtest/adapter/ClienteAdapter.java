package br.com.brqtest.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import br.com.brqtest.R;
import br.com.brqtest.model.Cliente;

import java.text.SimpleDateFormat;
import java.util.List;

public class ClienteAdapter extends ArrayAdapter<Cliente> {

    private List<Cliente> clientes;
    private Context context;

    public ClienteAdapter( Context context,  List<Cliente> clientes) {
        super(context, 0, clientes);
        this.context = context;
        this.clientes = clientes;
    }

    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        Cliente cliente =  this.clientes.get(position);

        convertView = LayoutInflater.from(this.context).inflate(R.layout.item_clientes, null);

        TextView txtNameCustomer      = convertView.findViewById(R.id.txtNome);
        TextView txtCPFCustomer  = convertView.findViewById(R.id.txtCpf);
        TextView txtDateOfBirth    = convertView.findViewById(R.id.txtDataDeNascimento);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        txtNameCustomer.setText(cliente.getNameFull().toUpperCase());
        txtCPFCustomer.setText(cliente.getCpf());
        txtDateOfBirth.setText(sdf.format(cliente.getDataDeNascimento()));

        if(position%2==0){
            txtNameCustomer.setBackgroundColor(Color.LTGRAY);
            txtCPFCustomer.setBackgroundColor(Color.LTGRAY);
            txtDateOfBirth.setBackgroundColor(Color.LTGRAY);
        }

        return convertView;
    }
}
