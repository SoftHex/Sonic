package com.softhex.sonic;

/**
 * Created by Administrador on 04/08/2017.
 */

public class sonicEmpresasHolder {

    int id;
    int codigo;
    String licenca;
    String nome_fantasia;
    String razao_social;

    public sonicEmpresasHolder() {
    }

    public sonicEmpresasHolder(Integer id,
                               int codigo,
                               String licenca,
                               String nome_fantasia,
                               String razao_social){
        this.id = id;
        this.codigo = codigo;
        this.licenca = licenca;
        this.nome_fantasia = nome_fantasia;
        this.razao_social = razao_social;

    }

    public int getId(){
        return this.id;
    }

    public int getCodigo(){
        return this.codigo;
    }

    public String getLicenca(){
        return this.licenca;
    }

    public String getNomeFantasia(){
        return this.nome_fantasia;
    }

    public String getRazaoSocial(){
        return this.razao_social;
    }

    public void setId(Integer id) { this.id = id; }

    public void setCodigo(int codigo) { this.codigo = codigo; }

    public void setLicenca(String licenca) { this.licenca = licenca; }

    public void setNomeFantasia(String nome_fantasia) { this.nome_fantasia = nome_fantasia; }

    public void setRazaoSocial(String razao_social){this.razao_social = razao_social;}

    public String toString(){
        return razao_social;
    }

}
