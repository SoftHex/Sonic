package com.softhex.sonic;

public class sonicGrupoProdutosHolder {

    int id;
    int codigo;
    String descricao;


    public sonicGrupoProdutosHolder() {
    }

    public sonicGrupoProdutosHolder(int id, int codigo, String descricao) {
        this.id = id;
        this.codigo = codigo;
        this.descricao = descricao;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
