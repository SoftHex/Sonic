package com.softhex.sonic;

/**
 * Created by Administrador on 04/08/2017.
 */

public class sonicProdutosHolder {

    int codigo_produto;
    int codigo_empresa;
    String nome;
    String unidade_medida;
    String grupo_produto;
    String data_cadastro;
    String codigo_alternativo;
    String descricao;
    String ncm;
    String peso_bruto;
    String peso_liquido;
    int estoque;
    int estoque_minimo;
    int estoque_maximo;
    int multiplicidade;
    String codigo_ean;
    String codigo_ean_tributavel;


    public sonicProdutosHolder(){

    }
    public sonicProdutosHolder( int codigo_produto,
                                int codigo_empresa,
                                String nome,
                                String unidade_medida,
                                String grupo_produto,
                                String data_cadastro,
                                String codigo_alternativo,
                                String descricao,
                                String ncm,
                                String peso_bruto,
                                String peso_liquido,
                                int estoque,
                                int estoque_minimo,
                                int estoque_maximo,
                                int multiplicidade,
                                String codigo_ean,
                                String codigo_ean_tributavel){

        this.codigo_produto = codigo_produto;
        this.codigo_empresa = codigo_empresa;
        this.nome = nome;
        this.unidade_medida = unidade_medida;
        this.grupo_produto = grupo_produto;
        this.data_cadastro = data_cadastro;
        this.codigo_alternativo = codigo_alternativo;
        this.descricao = descricao;
        this.ncm = ncm;
        this.peso_bruto = peso_bruto;
        this.peso_liquido = peso_liquido;
        this.estoque = estoque;
        this.estoque_minimo = estoque_minimo;
        this.estoque_maximo = estoque_maximo;
        this.multiplicidade = multiplicidade;
        this.codigo_ean = codigo_ean;
        this.codigo_ean_tributavel = codigo_ean_tributavel;

    }

    public int getCodigo(){
        return this.codigo_produto;
    }
    public int getCodigoEmpresa(){return this.codigo_empresa;}
    public String getNome(){return this.nome;}
    public String getUnidadeMedida(){
        return this.unidade_medida;
    }
    public String getGrupo(){
        return this.grupo_produto;
    }
    public String getDataCadastro(){return this.data_cadastro;}
    public String getCodigoAlternativo(){
        return this.codigo_alternativo;
    }
    public String getDescricao(){ return this.descricao; }
    public String getNcm(){return this.ncm;}
    public String getPesoBruto(){return this.peso_bruto;}
    public String getPesoPiquido(){return this.peso_liquido;}
    public int getEstoque(){
        return this.estoque;
    }
    public int getEstoqueMinimo(){
        return this.estoque_minimo;
    }
    public int getEstoqueMaximo(){return this.estoque_maximo;}
    public int getMultiplicidade(){return this.multiplicidade;}
    public String getCodigoEan(){return this.codigo_ean;}
    public String getCodigoEanTributavel(){return this.codigo_ean_tributavel;}

    public void setCodigo(int codigo_produto){
        this.codigo_produto = codigo_produto;
    }
    public void setCodigoEmpresa(int codigo_empresa){this.codigo_empresa = codigo_empresa;}
    public void setNome(String nome){this.nome = nome;}
    public void setUnidadeMedida(String unidade_medida){ this.unidade_medida = unidade_medida; }
    public void setGrupo(String grupo_produto){
        this.grupo_produto = grupo_produto;
    }
    public void setDataCadastro(String data_cadastro){this.data_cadastro = data_cadastro;}
    public void setCodigoAlternativo(String codigo_alternativo){ this.codigo_alternativo = codigo_alternativo; }
    public void setDescricao(String descricao){
        this.descricao = descricao;
    }
    public void setNcm(String ncm){this.ncm = ncm;}
    public void setPesoBruto(String peso_bruto){this.peso_bruto = peso_bruto;}
    public void setPesoLiquido(String peso_liquido){this.peso_liquido = peso_liquido;}
    public void setEstoque(int estoque){
        this.estoque = estoque;
    }
    public void setEstoqueMinimo(int estoque_minimo){this.estoque_minimo = estoque_minimo;}
    public void setEstoqueMaximo(int estoque_maximo){this.estoque_maximo = estoque_maximo;}
    public void setMultiplicidade(int multiplicidade){this.multiplicidade= multiplicidade;}
    public void setCodigoEan(String codigo_ean){this.codigo_ean = codigo_ean;}
    public void setCodigoEanTributavel(String codigo_ean_tributavel){this.codigo_ean_tributavel = codigo_ean_tributavel;}

    @Override
    public String toString(){
        return nome;
    }


}
