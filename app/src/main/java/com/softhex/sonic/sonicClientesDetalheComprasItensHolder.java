package com.softhex.sonic;

public class sonicClientesDetalheComprasItensHolder {

    int id;
    int codigo;
    String produto;
    String letra;
    String grupo;
    int codigo_produto;
    String referencia;
    int codigo_venda;
    String unidade_medida;
    String unidade_medida_sigla;
    int quantidade;
    String preco_unitario;
    String valor_geral;
    String desconto;

    public sonicClientesDetalheComprasItensHolder() {

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

    public String getProduto() {
        return produto;
    }

    public void setCodigoProduto(int codigo_produto){this.codigo_produto = codigo_produto;}

    public int getCodigoProduto(){return codigo_produto;}

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public int getCodigoVenda() {
        return codigo_venda;
    }

    public void setCodigoVenda(int codigo_venda) {
        this.codigo_venda = codigo_venda;
    }

    public String getUnidadeMedida() {
        return unidade_medida;
    }

    public void setUnidadeMedida(String unidade_medida) {
        this.unidade_medida = unidade_medida;
    }

    public String getUnidadeMedidaSigla() {
        return unidade_medida_sigla;
    }

    public void setUnidadeMedidaSigla(String unidade_medida_sigla) {
        this.unidade_medida_sigla = unidade_medida_sigla;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getPrecoUnitario() {
        return preco_unitario;
    }

    public void setPrecoUnitario(String preco_unitario) {
        this.preco_unitario = preco_unitario;
    }

    public String getValorGeral() {
        return valor_geral;
    }

    public void setValorGeral(String valor_geral) {
        this.valor_geral = valor_geral;
    }

    public String getDesconto() {
        return desconto;
    }

    public void setDesconto(String desconto) {
        this.desconto = desconto;
    }

    public String getLetra() {
        return String.valueOf(produto.charAt(0));
    }
}
