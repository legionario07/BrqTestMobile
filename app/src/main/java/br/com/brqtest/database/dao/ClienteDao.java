package br.com.brqtest.database.dao;

import br.com.brqtest.model.Cliente;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

public class ClienteDao extends BaseDaoImpl<Cliente, Integer> {
    public ClienteDao(ConnectionSource connectionSource) throws SQLException {
        super(Cliente.class);
        setConnectionSource(connectionSource);
        initialize();
    }
}