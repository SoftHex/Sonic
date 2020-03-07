package com.softhex.sonic;

/**
 * Created by Administrador on 04/08/2017.
 */

public class sonicGrupoEmpresasHolder {

    int id;
    int codigo;
    String nome;
    String descricao;
    String data_fundacao;
    String endereco;
    String bairro;
    String municipio;
    String uf;
    String cep;
    String fone;
    String whatsapp;
    String email;
    String site;

    public sonicGrupoEmpresasHolder() {
    }

    public sonicGrupoEmpresasHolder(Integer id,
                                    int codigo,
                                    String nome,
                                    String descricao,
                                    String data_fundacao,
                                    String endereco,
                                    String bairro,
                                    String municipio,
                                    String uf,
                                    String cep,
                                    String fone,
                                    String whatsapp,
                                    String email,
                                    String site){
        this.id = id;
        this.codigo = codigo;
        this.nome = nome;
        this.descricao = descricao;
        this.data_fundacao = data_fundacao;
        this.endereco = endereco;
        this.bairro = bairro;
        this.municipio = municipio;
        this.uf = uf;
        this.cep = cep;
        this.fone = fone;
        this.whatsapp = whatsapp;
        this.email = email;
        this.site = site;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDataFundacao() {
        return data_fundacao;
    }

    public void setDataFundacao(String data_fundacao) {
        this.data_fundacao = data_fundacao;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getFone() {
        return fone;
    }

    public void setFone(String fone) {
        this.fone = fone;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }
}
