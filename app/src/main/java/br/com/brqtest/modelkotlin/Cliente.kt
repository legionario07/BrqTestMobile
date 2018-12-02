package br.com.brqtest.modelkotlin

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import java.util.*

@DatabaseTable(tableName = "cliente")
class Cliente() : EntidadeDominio() {

    @DatabaseField(generatedId = true)
    var id: Int? = null
    @DatabaseField
    var nameFull: String? = null
    @DatabaseField
    var cpf: String? = null
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    var endereco: Endereco? = null
    @DatabaseField
    var dataDeNascimento: Date? = null



    init {
        endereco = Endereco()
    }

    override fun toString(): String {
        return "Cliente{" +
                "nameFull='" + nameFull + '\''.toString() +
                ", cpf='" + cpf + '\''.toString() +
                ", endereco=" + endereco +
                ", dataDeNascimento=" + dataDeNascimento +
                '}'.toString()
    }
}
