package com.softhex.sonic;

/**
 * Created by Administrador on 04/08/2017.
 */

public class sonicGrupoClientesHolder {

     int id;
     int codigo_grupo;
     String nome;

    public sonicGrupoClientesHolder(){

    }
    public sonicGrupoClientesHolder(int id,
                                    int codigo_grupo,
                                    String nome){

        this.id = id;
        this.codigo_grupo = codigo_grupo;
        this.nome = nome;


    }

    public int getId(){
        return this.id;
    }
    public int getCodigo(){
        return this.codigo_grupo;
    }
    public String getNome(){
        return this.nome;
    }



    public void setId(int id){
        this.id = id;
    }
    public void setCodigo(int codigo_grupo){
        this.codigo_grupo = codigo_grupo;
    }
    public void setNome(String nome){
        this.nome = nome;
    }


    @Override
    public String toString(){
        return nome;
    }


}
