package br.com.brqtest.databasekotlin

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import br.com.brqtest.modelkotlin.Cliente
import br.com.brqtest.modelkotlin.Endereco
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.TableUtils
import java.sql.SQLException


class DatabaseHelper(context: Context) : OrmLiteSqliteOpenHelper(context, DB_NAME, null, VERSION) {

    override fun onCreate(sqLiteDatabase: SQLiteDatabase, connectionSource: ConnectionSource) {
        try {
            TableUtils.createTable(connectionSource, Cliente::class.java)
            TableUtils.createTable(connectionSource, Endereco::class.java)
        } catch (e: SQLException) {
            e.printStackTrace()
        }

    }

    override fun onUpgrade(
        sqLiteDatabase: SQLiteDatabase,
        connectionSource: ConnectionSource,
        oldVersion: Int,
        newVersion: Int
    ) {

        try {
            TableUtils.dropTable<Cliente, Any>(connectionSource, Cliente::class.java, true)
            TableUtils.dropTable<Endereco, Any>(connectionSource, Endereco::class.java, true)
            onCreate(sqLiteDatabase, connectionSource)
        } catch (e: SQLException) {
            e.printStackTrace()
        }

    }

    override fun close() {
        super.close()

    }

    companion object {
        private val VERSION = 2
        private val DB_NAME = "brqtest"
    }
}
