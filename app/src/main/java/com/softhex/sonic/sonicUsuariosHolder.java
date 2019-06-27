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
     String  nivel_acesso;
     int  pessoa_superior;
     String cargo;
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
                               String nivel_acesso,
                               int pessoa_superior,
                               String cargo,
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
        this.nivel_acesso = nivel_acesso;
        this.pessoa_superior = pessoa_superior;
        this.cargo = cargo;
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

    public String getNivelAcesso(){
        return this.nivel_acesso;
    }

    public int getPessoaSuperior(){
        return this.pessoa_superior;
    }

    public String getCargo(){return this.cargo;}

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

    public void setNivelAcesso(String  nivel_acesso) { this.nivel_acesso = nivel_acesso; }

    public void setMeta(String  meta) { this.meta = meta; }

    public void setPessoaSuperior(int  pessoa_superior) { this.pessoa_superior = pessoa_superior; }

    public void setCargo(String cargo){this.cargo = cargo;}

    public void setAtivo(String  ativo) { this.ativo = ativo; }
    public void setData(String  data) { this.data = data; }
    public void setHora(String  hora) { this.hora = hora; }
    public void setLatitude(String  latitude) { this.latitude = latitude; }
    public void setLongitude(String  longitude) { this.longitude = longitude; }

    public String toString(){
    return nome;
    }

}
