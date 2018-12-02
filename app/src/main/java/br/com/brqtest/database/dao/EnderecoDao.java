package br.com.brqtest.database.dao;

import br.com.brqtest.model.Endereco;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

public class EnderecoDao extends BaseDaoImpl<Endereco, Integer> {
    public EnderecoDao(ConnectionSource connectionSource) throws SQLException {
        super(Endereco.class);
        setConnectionSource(connectionSource);
        initialize();
    }
}
