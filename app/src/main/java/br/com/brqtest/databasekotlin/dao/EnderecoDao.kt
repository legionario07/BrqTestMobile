package br.com.brqtest.databasekotlin.dao

import br.com.brqtest.modelkotlin.Endereco
import com.j256.ormlite.dao.BaseDaoImpl
import com.j256.ormlite.support.ConnectionSource
import java.sql.SQLException


class EnderecoDao @Throws(SQLException::class)
constructor(connectionSource: ConnectionSource) : BaseDaoImpl<Endereco, Int>(Endereco::class.java) {
    init {
        setConnectionSource(connectionSource)
        initialize()
    }
}
