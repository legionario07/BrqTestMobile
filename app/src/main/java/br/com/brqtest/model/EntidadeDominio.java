package br.com.brqtest.model;

import java.io.Serializable;

public abstract class EntidadeDominio implements Serializable {

    private Integer id;


    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "id=" + id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
