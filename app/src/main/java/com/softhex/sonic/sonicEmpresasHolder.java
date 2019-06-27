package com.softhex.sonic;

/**
 * Created by Administrador on 04/08/2017.
 */

public class sonicEmpresasHolder {

    // variables
    int id;
     String codigo;
     String licenca;
     String nome;

    public sonicEmpresasHolder(){

    }

    public sonicEmpresasHolder(Integer id, String codigo, String licenca, String nome){
        this.id = id;
        this.codigo = codigo;
        this.licenca = licenca;
        this.nome = nome;

    }

    public int getId(){
        return this.id;
    }

    public String getCodigo(){
        return this.codigo;
    }

    public String getLicenca(){
        return this.licenca;
    }

    public String getNome(){
        return this.nome;
    }

    public void setId(Integer id) { this.id = id; }

    public void setCodigo(String codigo) { this.codigo = codigo; }

    public void setLicenca(String licenca) { this.licenca = licenca; }

    public void setNome(String nome) { this.nome = nome; }

    public String toString(){
        return codigo;
    }

}
