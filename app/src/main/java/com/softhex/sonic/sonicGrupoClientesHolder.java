package com.softhex.sonic;

/**
 * Created by Administrador on 04/08/2017.
 */

public class sonicGrupoClientesHolder {

     int id;
     int codigo;
     String nome;

    public sonicGrupoClientesHolder(){

    }
    public sonicGrupoClientesHolder(int id,
                                    int codigo,
                                    String nome){

        this.id = id;
        this.codigo = codigo;
        this.nome = nome;


    }

    public int getId(){
        return this.id;
    }
    public int getCodigo(){
        return this.codigo;
    }
    public String getNome(){
        return this.nome;
    }



    public void setId(int id){
        this.id = id;
    }
    public void setCodigo(int codigo){
        this.codigo = codigo;
    }
    public void setNome(String nome){
        this.nome = nome;
    }


    @Override
    public String toString(){
        return nome;
    }


}
