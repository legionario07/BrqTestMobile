package br.com.brqtest.modelkotlin

import br.com.brqtest.modelkotlin.EntidadeDominio
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

@DatabaseTable(tableName = "endereco")
class Endereco : EntidadeDominio {

    @DatabaseField(generatedId = true)
    var id: Int? = null
    @DatabaseField
    var logradouro: String? = null
    @DatabaseField
    var numero: String? = null
    @DatabaseField
    var complemento: String? = null
    @DatabaseField
    var bairro: String? = null
    @DatabaseField
    var cidade: String? = null
    var localidade: String? = null
    @DatabaseField
    var uf: String? = null
    @DatabaseField
    var cep: String? = null

    constructor() {

    }

    override fun toString(): String {
        return "Endereco{" +
                "logradouro='" + logradouro + '\''.toString() +
                ", numero='" + numero + '\''.toString() +
                ", complemento='" + complemento + '\''.toString() +
                ", bairro='" + bairro + '\''.toString() +
                ", cidade='" + cidade + '\''.toString() +
                ", uf='" + uf + '\''.toString() +
                ", cep='" + cep + '\''.toString() +
                '}'.toString()
    }
}
