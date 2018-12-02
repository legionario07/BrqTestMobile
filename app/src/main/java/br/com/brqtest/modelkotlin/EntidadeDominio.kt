package br.com.brqtest.modelkotlin

abstract class EntidadeDominio {
    private var id: Int? = null


    fun getId(): Int? {
        return id
    }

    override fun toString(): String {
        return "id=" + id!!
    }

    fun setId(id: Int?) {
        this.id = id
    }
}