package com.softhex.sonic;

public class sonicRankingClientesHolder {

    int id;
    int codigo_cliente;
    String cliente;
    String grupo;
    String valor;
    int pedidos;
    String atuacao;


    public sonicRankingClientesHolder() {
    }

    public sonicRankingClientesHolder(int id, int codigo_cliente, String cliente, String grupo, String valor, int pedidos, String atuacao) {
        this.id = id;
        this.codigo_cliente = codigo_cliente;
        this.cliente = cliente;
        this.grupo = grupo;
        this.valor = valor;
        this.pedidos = pedidos;
        this.atuacao = atuacao;
    }

    public int getCodigoCliente() {
        return codigo_cliente;
    }

    public String getCliente() {
        return cliente;
    }

    public String getGrupo() {
        return grupo;
    }

    public String getValor() {
        return valor;
    }

    public int getPedidos() {
        return pedidos;
    }

    public String getAtuacao() {
        return atuacao;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCodigoCliente(int codigo_cliente) {
        this.codigo_cliente = codigo_cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public void setGrupoCliente(String grupo) {
        this.grupo = grupo;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public void setPedidos(int pedidos) {
        this.pedidos = pedidos;
    }

    public void setAtuacao(String atuacao) {
        this.atuacao = atuacao;
    }
}
