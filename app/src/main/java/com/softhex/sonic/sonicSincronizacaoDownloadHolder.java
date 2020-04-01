package com.softhex.sonic;

public class sonicSincronizacaoDownloadHolder {

    private String titulo;
    private String descricao;
    private String dependencia;
    private String sincronizacao;
    private String data;
    private String hora;
    private int imagem;

    public sonicSincronizacaoDownloadHolder(){

    }

    public sonicSincronizacaoDownloadHolder(String titulo, String descricao, String data, String hora, int imagem) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.imagem = imagem;
        this.dependencia = dependencia;
        this.sincronizacao = sincronizacao;
        this.data = data;
        this.hora = hora;

    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getImagem() {
        return imagem;
    }

    public void setImagem(int imagem) {
        this.imagem = imagem;
    }

    public String getDependencia() {
        return dependencia;
    }

    public void setDependencia(String dependencia) {
        this.dependencia = dependencia;
    }

    public String getSincronizacao() {
        return sincronizacao;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public void setSincronizacao(String sincronizacao) {
        this.sincronizacao = sincronizacao;


    }
}
