package com.softhex.sonic;

/**
 * Created by Administrador on 04/08/2017.
 */

public class sonicProdutosHolder {

     int codigo_produto;
     String descricao;
     String codigo_alternativo;
     int estoque;
     int estoque_minimo;
     String unidade_medida;
     String grupo_produto;
     int situacao;
     String quantidade;
     String quantidade_anterior;
     int pedidos;
     String atuacao;

    public sonicProdutosHolder(){

    }
    public sonicProdutosHolder(int codigo_produto,
                               String descricao,
                               String codigo_alternativo,
                               int estoque,
                               int estoque_minimo,
                               String unidade_medida,
                               String grupo_produto,
                               int situacao,
                               String quantidade,
                               String quantidade_anterior,
                               int pedidos,
                               String atuacao){

        this.codigo_produto = codigo_produto;
        this.descricao = descricao;
        this.codigo_alternativo = codigo_alternativo;
        this.estoque = estoque;
        this.estoque_minimo = estoque_minimo;
        this.unidade_medida = unidade_medida;
        this.grupo_produto = grupo_produto;
        this.situacao = situacao;
        this.quantidade = quantidade;
        this.quantidade_anterior = quantidade_anterior;
        this.pedidos = pedidos;
        this.atuacao = atuacao;

    }

    public int getCodigo(){
        return this.codigo_produto;
    }
    public String getDescricao(){ return this.descricao; }
    public String getCodigoAlternativo(){
        return this.codigo_alternativo;
    }
    public int getEstoque(){
        return this.estoque;
    }
    public int getEstoqueMinimo(){
        return this.estoque_minimo;
    }
    public String getUnidadeMedida(){
        return this.unidade_medida;
    }
    public String getGrupo(){
        return this.grupo_produto;
    }

    public int getSituacao() {
        return situacao;
    }

    public String getQuantidade() {
        return quantidade;
    }

    public String getQuantidadeAnterior() {
        return quantidade_anterior;
    }

    public int getPedidos() {
        return pedidos;
    }

    public String getAtuacao() {
        return atuacao;
    }

    public void setCodigo(int codigo_produto){
        this.codigo_produto = codigo_produto;
    }
    public void setDescricao(String descricao){
        this.descricao = descricao;
    }
    public void setCodigoAlternativo(String codigo_alternativo){ this.codigo_alternativo = codigo_alternativo; }
    public void setEstoque(int estoque){
        this.estoque = estoque;
    }
    public void setEstoqueMinimo(int estoque_minimo){this.estoque_minimo = estoque_minimo;}
    public void setUnidadeMedida(String unidade_medida){ this.unidade_medida = unidade_medida; }
    public void setGrupo(String grupo_produto){
        this.grupo_produto = grupo_produto;
    }

    public void setSituacao(int situacao) {
        this.situacao = situacao;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }

    public void setQuantidadeAnterior(String quantidade_anterior) {
        this.quantidade_anterior = quantidade_anterior;
    }

    public void setPedidos(int pedidos) {
        this.pedidos = pedidos;
    }

    public void setAtuacao(String atuacao) {
        this.atuacao = atuacao;
    }

    @Override
    public String toString(){
        return descricao;
    }


}
