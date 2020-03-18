package com.softhex.sonic;

public class sonicClientesDetalheComprasHolder {

    int id;
    String usuario;
    int codigo;
    String tipo_cobranca;
    String prazo;
    String codigo_mobile;
    int situacao;
    String data;
    String valor;
    String valor_desc;

    public sonicClientesDetalheComprasHolder(){

    }

    public sonicClientesDetalheComprasHolder(
                                            int id,
                                            String usuario,
                                            int codigo,
                                            String tipo_cobranca,
                                            String prazo,
                                            String codigo_mobile,
                                            int situacao,
                                            String data,
                                            String valor,
                                            String valor_desc){

        this.id = id;
        this.usuario = usuario;
        this.codigo = codigo;
        this.tipo_cobranca = tipo_cobranca;
        this.prazo = prazo;
        this.codigo_mobile = codigo_mobile;
        this.situacao = situacao;
        this.data = data;
        this.valor = valor;
        this.valor_desc = valor_desc;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getTipoCobranca() {
        return tipo_cobranca;
    }

    public void setTipoCobranca(String tipo_cobranca) {
        this.tipo_cobranca = tipo_cobranca;
    }

    public String getPrazo() {
        return prazo;
    }

    public void setPrazo(String prazo) {
        this.prazo = prazo;
    }

    public String getCodigoMobile() {
        return codigo_mobile;
    }

    public void setCodigoMobile(String codigo_mobile) {
        this.codigo_mobile = codigo_mobile;
    }

    public int getSituacao() {
        return situacao;
    }

    public void setSituacao(int situacao) {
        this.situacao = situacao;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getValorDesc() {
        return valor_desc;
    }

    public void setValorDesc(String valor_desc) {
        this.valor_desc = valor_desc;
    }
}
