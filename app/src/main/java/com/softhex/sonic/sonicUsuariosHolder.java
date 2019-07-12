package com.softhex.sonic;

/**
 * Created by Administrador on 04/08/2017.
 */

public class sonicUsuariosHolder {

     Integer id;
     int  codigo;
     String nome;
     String login;
     String pass;
     String imei;
     String nivel_acesso_nome;
     int nivel_acesso_id;
     String  usuario_superior;
     String cargo;
     String empresa;
     int empresa_id;
     String meta;
     String  ativo;
     String  data;
     String  hora;
     String  latitude;
     String  longitude;


    public sonicUsuariosHolder(){

    }

    public sonicUsuariosHolder(Integer id,
                               int codigo,
                               String nome,
                               String login,
                               String pass,
                               String imei,
                               String nivel_acesso_nome,
                               int nivel_acesso_id,
                               String usuario_superior,
                               String cargo,
                               String empresa,
                               int empresa_id,
                               String meta,
                               String ativo,
                               String data,
                               String hora,
                               String latitude,
                               String longitude){
        this.id = id;
        this.codigo = codigo;
        this.nome = nome;
        this.login = login;
        this.pass = pass;
        this.imei = imei;
        this.nivel_acesso_nome = nivel_acesso_nome;
        this.nivel_acesso_id = nivel_acesso_id;
        this.usuario_superior = usuario_superior;
        this.cargo = cargo;
        this.empresa = empresa;
        this.empresa_id = empresa_id;
        this.meta = meta;
        this.ativo = ativo;
        this.data = data;
        this.hora = hora;
        this.latitude = latitude;
        this.login = longitude;
    }

    public Integer getId(){
        return this.id;
    }

    public String getNome(){
        return this.nome;
    }

    public String getLogin(){
        return this.login;
    }

    public int getCodigo(){
        return this.codigo;
    }

    public String getPass(){
        return this.pass;
    }

    public String getImei() {
        return imei;
    }

    public String getNivelAcessoNome(){
        return this.nivel_acesso_nome;
    }

    public int getNivelAcessoId() {
        return nivel_acesso_id;
    }

    public String getUsuarioSuperior(){
        return this.usuario_superior;
    }

    public String getCargo(){return this.cargo;}

    public String getEmpresa() {
        return empresa;
    }

    public int getEmpresaId() {
        return empresa_id;
    }

    public String getMeta(){
        return this.meta;
    }

    public String getAtivo(){
        return this.ativo;
    }

    public String getData(){
        return this.data;
    }
    public String getHora(){
        return this.hora;
    }
    public String getLatitude(){
        return this.latitude;
    }
    public String getLongitude(){
        return this.longitude;
    }

    public void setId(Integer id) { this.id = id; }

    public void setCodigo(int codigo) { this.codigo = codigo; }

    public void setNome(String nome) { this.nome = nome; }

    public void setLogin(String login) { this.login = login; }

    public void setPass(String pass) { this.pass = pass; }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public void setNivelAcesso(String  nivel_acesso_nome) { this.nivel_acesso_nome = nivel_acesso_nome; }

    public void setNivelAcessoId(int nivel_acesso_id) {
        this.nivel_acesso_id = nivel_acesso_id;
    }

    public void setMeta(String  meta) { this.meta = meta; }

    public void setUsuarioSuperior(String  usuario_superior) { this.usuario_superior = usuario_superior; }

    public void setCargo(String cargo){this.cargo = cargo;}

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public void setEmpresaId(int empresa_id) {
        this.empresa_id = empresa_id;
    }

    public void setAtivo(String  ativo) { this.ativo = ativo; }
    public void setData(String  data) { this.data = data; }
    public void setHora(String  hora) { this.hora = hora; }
    public void setLatitude(String  latitude) { this.latitude = latitude; }
    public void setLongitude(String  longitude) { this.longitude = longitude; }

    public String toString(){
    return nome;
    }

}
