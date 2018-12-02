package br.com.brqtest.modelkotlin

import br.com.brqtest.model.Endereco
import java.util.*

class Cliente: EntidadeDominio(){

    private var nameFull: String? = null
    private var cpf: String? = null
    private var endereco: Endereco? = null
    private var date: Date? = null


    fun Cliente(id: Int?) {
        Cliente()
        setId(id)
    }

    fun Cliente() {
        setEndereco(Endereco())
    }

    fun getNameFull(): String? {
        return nameFull
    }

    fun setNameFull(nameFull: String) {
        this.nameFull = nameFull
    }

    fun getCpf(): String? {
        return cpf
    }

    fun setCpf(cpf: String) {
        this.cpf = cpf
    }

    fun getEndereco(): Endereco? {
        return endereco
    }

    fun setEndereco(endereco: Endereco) {
        this.endereco = endereco
    }

    fun getDate(): Date? {
        return date
    }

    fun setDate(date: Date) {
        this.date = date
    }

    override fun toString(): String {
        return """Cliente{nameFull='$nameFull${'\''.toString()}, cpf='$cpf${'\''.toString()}, endereco=$endereco, date=$date${'}'.toString()}"""
    }

}