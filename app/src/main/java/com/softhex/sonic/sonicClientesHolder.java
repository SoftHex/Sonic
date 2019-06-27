package com.softhex.sonic;

/**
 * Created by Administrador on 04/08/2017.
 */

public class sonicClientesHolder {

    int codigo_cliente;
     String tipo;
     String razao_social;
     String nome_fantasia;
     String cpf_cnpj;
     String insc_estadual;
     String endereco;
     String bairro;
     String municipio;
     String uf;
     String cep;
     String fone;
     String contato;
     String email;
     int situacao;
     String obs;
     String data_cadastro;
     String grupo;
     int titulos;
     String tabela_preco;

    public sonicClientesHolder(){

    }
    public sonicClientesHolder(int codigo_cliente,
                               String tipo,
                               String razao_social,
                               String nome_fantasia,
                               String cpf_cnpj,
                               String insc_estadual,
                               String endereco,
                               String bairro,
                               String municipio,
                               String uf,
                               String cep,
                               String fone,
                               String contato,
                               String email,
                               int situacao,
                               String obs,
                               String data_cadastro,
                               String grupo,
                               int titulos,
                               String tabela_preco){

        this.codigo_cliente = codigo_cliente;
        this.tipo = tipo;
        this.razao_social = razao_social;
        this.nome_fantasia = nome_fantasia;
        this.cpf_cnpj = cpf_cnpj;
        this.insc_estadual = insc_estadual;
        this.endereco = endereco;
        this.bairro = bairro;
        this.municipio = municipio;
        this.uf = uf;
        this.cep = cep;
        this.fone = fone;
        this.contato = contato;
        this.email = email;
        this.situacao = situacao;
        this.obs = obs;
        this.data_cadastro = data_cadastro;
        this.grupo = grupo;
        this.titulos = titulos;
        this.tabela_preco = tabela_preco;

    }

    public int getCodigo(){
        return this.codigo_cliente;
    }
    public String getTipo(){
        return this.tipo;
    }
    public String getRazaoSocial(){
        return this.razao_social;
    }
    public String getNomeFantasia(){
        return this.nome_fantasia;
    }
    public String getCpfCnpj(){
        return this.cpf_cnpj;
    }
    public String getInscEstadual(){
        return this.insc_estadual;
    }
    public String getEndereco(){
        return this.endereco;
    }
    public String getBairro(){
        return this.bairro;
    }
    public String getMunicipio(){
        return this.municipio;
    }
    public String getUf(){ return this.uf; }
    public String getCep(){
        return this.cep;
    }
    public String getFone(){
        return this.fone;
    }
    public String getContato(){ return this.contato; }
    public String getEmail(){
        return this.email;
    }
    public int getSituacao(){
        return this.situacao;
    }
    public String getObservacao(){
        return this.obs;
    }
    public String getDataCadastro(){
        return this.data_cadastro;
    }

    public String getGrupo() {
        return grupo;
    }

    public int getTitulos() {
        return titulos;
    }

    public String getTabelaPreco(){
        return this.tabela_preco;
    }

    public void setCodigo(int codigo_cliente){
        this.codigo_cliente = codigo_cliente;
    }
    public void setTipo(String tipo){
        this.tipo = tipo;
    }
    public void setRazaoSocial(String razao_social){
        this.razao_social = razao_social;
    }
    public void setNomeFantasia(String nome_fantasia){
        this.nome_fantasia = nome_fantasia;
    }
    public void setCpfCnpj(String cpf_cnpj){ this.cpf_cnpj = cpf_cnpj; }
    public void setInscEstadual(String insc_estadual){ this.insc_estadual = insc_estadual; }
    public void setEndereco(String endereco){
        this.endereco = endereco;
    }
    public void setBairro(String bairro){
        this.bairro = bairro;
    }
    public void setMunicipio(String municipio){ this.municipio = municipio; }
    public void setUf(String uf){
        this.uf = uf;
    }

    public void setCep(String cep){
        this.cep = cep;
    }
    public void setFone(String fone){ this.fone = fone; }
    public void setContato(String contato){
        this.contato = contato;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setSituacao(int situacao){ this.situacao = situacao; }
    public void setObservacao(String obs){ this.obs = obs; }
    public void setDataCadastro(String data_cadastro){ this.data_cadastro = data_cadastro; }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public void setTitulos(int titulos) {
        this.titulos = titulos;
    }

    public void setTabelaPreco(String tabela_preco){ this.tabela_preco = tabela_preco; }

    @Override
    public String toString(){
        return razao_social;
    }


}