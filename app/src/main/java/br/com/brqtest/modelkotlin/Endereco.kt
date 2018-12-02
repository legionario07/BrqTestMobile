package br.com.brqtest.modelkotlin

class Endereco:EntidadeDominio() {

    private var logradouro: String? = null
    private var numero: String? = null
    private var complemento: String? = null
    private var bairro: String? = null
    private var cidade: String? = null
    private var uf: String? = null
    private var cep: String? = null

    fun Endereco(id: Int?) {
        setId(id)
    }

    fun Endereco() {

    }

    fun getLogradouro(): String? {
        return logradouro
    }

    fun setLogradouro(logradouro: String) {
        this.logradouro = logradouro
    }

    fun getNumero(): String? {
        return numero
    }

    fun setNumero(numero: String) {
        this.numero = numero
    }

    fun getComplemento(): String? {
        return complemento
    }

    fun setComplemento(complemento: String) {
        this.complemento = complemento
    }

    fun getBairro(): String? {
        return bairro
    }

    fun setBairro(bairro: String) {
        this.bairro = bairro
    }

    fun getCidade(): String? {
        return cidade
    }

    fun setCidade(cidade: String) {
        this.cidade = cidade
    }

    fun getUf(): String? {
        return uf
    }

    fun setUf(uf: String) {
        this.uf = uf
    }

    fun getCep(): String? {
        return cep
    }

    fun setCep(cep: String) {
        this.cep = cep
    }

    override fun toString(): String {
        return """Endereco{logradouro='$logradouro${'\''.toString()}, numero='$numero${'\''.toString()}, complemento='$complemento${'\''.toString()}, bairro='$bairro${'\''.toString()}, cidade='$cidade${'\''.toString()}, uf='$uf${'\''.toString()}, cep='$cep${'\''.toString()}${'}'.toString()}"""
    }

}