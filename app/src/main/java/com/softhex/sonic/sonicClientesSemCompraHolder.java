package com.softhex.sonic;

public class sonicClientesSemCompraHolder {


    int id;
    int codigo_cliente;
    String nome;
    int dias;


    public sonicClientesSemCompraHolder() {
    }

    public sonicClientesSemCompraHolder(int id, int codigo_cliente, String nome, int dias) {
        this.id = id;
        this.codigo_cliente = codigo_cliente;
        this.nome = nome;
        this.dias = dias;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCodigoCliente() {
        return codigo_cliente;
    }

    public void setCodigoCliente(int codigo_cliente) {
        this.codigo_cliente = codigo_cliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getDias() {
        return dias;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }
}
