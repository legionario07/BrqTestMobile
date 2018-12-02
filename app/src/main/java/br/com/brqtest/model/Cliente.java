package br.com.brqtest.model;

import br.com.brqtest.R;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "cliente")
public class Cliente extends EntidadeDominio {

    @DatabaseField(generatedId = true)
    private Integer id;
    @DatabaseField
    private String nameFull;
    @DatabaseField
    private String cpf;
    @DatabaseField(foreign = true)
    private Endereco endereco;
    @DatabaseField
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
