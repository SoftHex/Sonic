package com.softhex.sonic;

/**
 * Created by Administrador on 04/08/2017.
 */

public class sonicTitulosHolder {

    int id;
    int codigo_cliente;
    String nome_fantasia;
    String razao_social;
    String grupo;
    String agente_cobrador;
    String tipo_cobranca;
    String numero;
    String data_emissao;
    String data_vencimento;
    int dias_atraso;
    String valor;
    String saldo;
    String juros;
    int situacao;
    String situacao_cor;

    sonicTitulosHolder(){

    }

    public int getCodigoCliente(){
        return this.codigo_cliente;
    }

    public String getNomeFantasia() {
        return nome_fantasia;
    }

    public String getRazaoSocial() {
        return razao_social;
    }

    public String getGrupo() {
        return grupo;
    }

    public String getAgenteCobrador() {
        return agente_cobrador;
    }

    public void setAgenteCobrador(String agente_cobrador) {
        this.agente_cobrador = agente_cobrador;
    }

    public String getJuros() {
        return juros;
    }

    public void setJuros(String juros) {
        this.juros = juros;
    }

    public String getTipoCobranca() {
        return tipo_cobranca;
    }

    public void setTipoCobranca(String tipo_cobranca) {
        this.tipo_cobranca = tipo_cobranca;
    }

    public String getNumero(){
        return this.numero;
    }
    public String getDataEmissao(){
        return this.data_emissao;
    }
    public String getDataVencimento(){
        return this.data_vencimento;
    }

    public int getDiasAtraso() {
        return dias_atraso;
    }

    public String getValor(){
        return this.valor;
    }

    public String getSaldo() {
        return saldo;
    }

    public int getSituacao(){
        return this.situacao;
    }

    public String getSituacaoCor() {
        return situacao_cor;
    }

    public void setCodigoCliente(int cod_cliente){
        this.codigo_cliente = cod_cliente;
    }

    public void setNomeFantasia(String nome_fantasia) {
        this.nome_fantasia = nome_fantasia;
    }

    public void setRazaoSocial(String razao_social) {
        this.razao_social = razao_social;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public void setNumero(String numero){
        this.numero= numero;
    }
    public void setDataEmissao(String data_emissao){
        this.data_emissao = data_emissao;
    }
    public void setDataVencimento(String data_vencimento){ this.data_vencimento = data_vencimento; }

    public void setDiasAtraso(int dias_atraso) {
        this.dias_atraso = dias_atraso;
    }

    public void setValor(String valor){
        this.valor = valor;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public void setSituacao(int situacao){ this.situacao = situacao; }

    public void setSituacaoCor(String situacao_cor) {
        this.situacao_cor = situacao_cor;
    }

    @Override
    public String toString(){
        return numero;
    }


}
