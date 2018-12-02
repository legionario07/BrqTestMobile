package br.com.brqtest.databasekotlin.dao

import br.com.brqtest.modelkotlin.Cliente
import com.j256.ormlite.dao.BaseDaoImpl
import com.j256.ormlite.support.ConnectionSource
import java.sql.SQLException


class ClienteDao @Throws(SQLException::class)
constructor(connectionSource: ConnectionSource) : BaseDaoImpl<Cliente, Int>(Cliente::class.java) {
    init {
        setConnectionSource(connectionSource)
        initialize()
    }
}