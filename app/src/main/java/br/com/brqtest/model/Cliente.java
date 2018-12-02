package br.com.brqtest.model;

import java.util.Date;

public class Cliente extends EntidadeDominio {


    private String nameFull;
    private String cpf;
    private Endereco endereco;
    private Date dataDeNascimento;



    public Cliente(Integer id){
        this();
        setId(id);
    }

    public Cliente(){
        setEndereco(new Endereco());
    }

    public String getNameFull() {
        return nameFull;
    }

    public void setNameFull(String nameFull) {
        this.nameFull = nameFull;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Date getDataDeNascimento() {
        return dataDeNascimento;
    }

    public void setDataDeNascimento(Date dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "nameFull='" + nameFull + '\'' +
                ", cpf='" + cpf + '\'' +
                ", endereco=" + endereco +
                ", dataDeNascimento=" + dataDeNascimento +
                '}';
    }
}
