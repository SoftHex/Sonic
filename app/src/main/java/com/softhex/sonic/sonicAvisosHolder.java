package com.softhex.sonic;

/**
 * Created by Administrador on 04/08/2017.
 */

public class sonicAvisosHolder {

    int id;
    int codigo_aviso;
    int prioridade;
    String autor;
    String data;
    String hora;
    String titulo;
    String mensagem;
    int status;

    public sonicAvisosHolder() {
    }

    public sonicAvisosHolder(Integer id,
                             int codigo_aviso,
                             int prioridade,
                             String autor,
                             String data,
                             String hora,
                             String titulo,
                             String mensagem,
                             int status){
        this.id = id;
        this.codigo_aviso = codigo_aviso;
        this.prioridade = prioridade;
        this.autor = autor;
        this.data = data;
        this.hora = hora;
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.status = status;

    }

    public int getId(){
        return this.id;
    }

    public int getCodigo(){
        return this.codigo_aviso;
    }

    public int getPrioridade(){
        return this.prioridade;
    }

    public String getAutor(){
        return this.autor;
    }

    public String getData(){
        return this.data;
    }

    public String getHora(){
        return this.hora;
    }

    public String getTitulo(){
        return this.titulo;
    }

    public String getMensagem(){
        return this.mensagem;
    }

    public int getStatus() {
        return status;
    }

    public void setId(Integer id) { this.id = id; }

    public void setCodigo(int codigo_aviso) { this.codigo_aviso = codigo_aviso; }


    public void setPrioridade(int prioridade) {
        this.prioridade = prioridade;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String toString(){
        return titulo;
    }

}
