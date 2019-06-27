package com.softhex.sonic;

/**
 * Created by Administrador on 04/08/2017.
 */

public class sonicLocalizacaoHolder {

     Integer id;
     int codigo_vendedor;
     String login;
     String nome;
     String nivel_acesso;
     String data;
     String hora;
     String latitude;
     String longitude;

    public sonicLocalizacaoHolder(){

    }

    public sonicLocalizacaoHolder(Integer id,
                                  int codigo_vendedor,
                                  String login,
                                  String nome,
                                  String nivel_acesso,
                                  String data,
                                  String hora,
                                  String latitude,
                                  String longitude){
        this.id = id;
        this.codigo_vendedor = codigo_vendedor;
        this.login = login;
        this.nome = nome;
        this.nivel_acesso = nivel_acesso;
        this.data = data;
        this.hora = hora;
        this.latitude = latitude;
        this.longitude = longitude;

    }

    public int getId(){
        return this.id;
    }

    public int getCodigoVendedor(){
        return this.codigo_vendedor;
    }

    public String getLogin(){
        return this.login;
    }

    public String getNome(){
        return this.nome;
    }

    public String getNivelAcesso(){
        return this.nivel_acesso;
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

    public void setCodigoVendedor(int codigo_vendedor) { this.codigo_vendedor = codigo_vendedor; }

    public void setLogin(String login) { this.login = login; }

    public void setNome(String nome) { this.nome = nome; }

    public void setNivelAcesso(String nivel_acesso) { this.nivel_acesso = nivel_acesso; }

    public void setData(String data) { this.data = data; }

    public void setHora(String hora) { this.hora = hora; }

    public void setLatitude(String latitude) { this.latitude = latitude; }

    public void setLongitude(String longitude) { this.longitude = longitude; }


    public String toString(){
        return login;
    }

}
