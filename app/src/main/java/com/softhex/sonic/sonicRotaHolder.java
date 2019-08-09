package com.softhex.sonic;

public class sonicRotaHolder{


    int id;
    int codigo;
    int codigo_cliente;
    String nome_fantasia;
    String razao_social;
    String grupo_cliente;
    String endereco_completo;
    String data;
    String hora;
    int ordem;
    String latitude;
    String longitude;


    public sonicRotaHolder() {
    }

    public sonicRotaHolder(int codigo,
                           int codigo_cliente,
                           String nome_fantasia,
                           String razao_social,
                           String grupo_cliente,
                           String endereco_completo,
                           String data,
                           String hora,
                           int ordem,
                           String latitude,
                           String longitude) {
        this.codigo = codigo;
        this.codigo_cliente = codigo_cliente;
        this.nome_fantasia = nome_fantasia;
        this.razao_social = razao_social;
        this.grupo_cliente = grupo_cliente;
        this.endereco_completo = endereco_completo;
        this.data = data;
        this.hora = hora;
        this.ordem = ordem;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getCodigo() {
        return codigo;
    }

    public int getCodigoCliente() {
        return codigo_cliente;
    }

    public String getNomeFantasia() {
        return nome_fantasia;
    }

    public String getRazaoSocial() {
        return razao_social;
    }

    public String getGrupoCliente() {
        return grupo_cliente;
    }

    public String getEnderecoCompleto() {
        return endereco_completo;
    }

    public String getData() {
        return data;
    }

    public String getHora() {
        return hora;
    }

    public int getOrdem() {
        return ordem;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setCodigoCliente(int codigo_cliente) {
        this.codigo_cliente = codigo_cliente;
    }

    public void setNomeFantasia(String nome_fantasia) {
        this.nome_fantasia = nome_fantasia;
    }

    public void setRazaoSocial(String razao_social) {
        this.razao_social = razao_social;
    }

    public void setGrupoCliente(String grupo_cliente) {
        this.grupo_cliente = grupo_cliente;
    }

    public void setEnderecoCompleto(String endereco_completo) {
        this.endereco_completo = endereco_completo;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public void setOrdem(int ordem) {
        this.ordem = ordem;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
