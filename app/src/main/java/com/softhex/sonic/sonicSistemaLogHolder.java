package com.softhex.sonic;

/**
 * Created by Administrador on 04/08/2017.
 */

public class sonicSistemaLogHolder {

    int codigo;
    String manufaturer;
    String model;
    int sdk;
    String name;
    String release;
    String activity;
    String classe;
    String method;
    String log;
    String data;
    String hora;

    sonicSistemaLogHolder(){

    }
    public sonicSistemaLogHolder(int codigo,
                                 String manufaturer,
                                 String model,
                                 int sdk,
                                 String name,
                                 String release,
                                 String activity,
                                 String classe,
                                 String method,
                                 String log,
                                 String data,
                                 String hora
                                ){

        this.codigo = codigo;
        this.manufaturer = manufaturer;
        this.model = model;
        this.sdk = sdk;
        this.name = name;
        this.release = release;
        this.activity = activity;
        this.classe = classe;
        this.method = method;
        this.log = log;
        this.data = data;
        this.hora = hora;

    }

    public int getCodigo(){
        return this.codigo;
    }
    public String getLog(){
        return this.log;
    }
    public String getData(){
        return this.data;
    }
    public String getHora(){
        return this.hora;
    }
    public String getManufaturer(){
        return this.manufaturer;
    }
    public String getModel(){
        return this.model;
    }
    public int getSdk(){
        return this.sdk;
    }
    public String getName(){
        return this.name;
    }
    public String getRelease(){
        return this.release;
    }
    public String getActivity(){
        return this.activity;
    }
    public String getClasse(){
        return this.classe;
    }
    public String getMethod(){
        return this.method;
    }

    public void setCodigo(int codigo){
        this.codigo = codigo;
    }
    public void setLog(String log){
        this.log = log;
    }
    public void setData(String data){
        this.data = data;
    }
    public void setHora(String hora){
        this.hora = hora;
    }
    public void setManufaturer(String manufaturer){
        this.manufaturer = manufaturer;
    }
    public void setModel(String model){
        this.model = model;
    }
    public void setSdk(int sdk){
        this.sdk = sdk;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setRelease(String release){
        this.release = release;
    }
    public void setActivity(String activity){
        this.activity = activity;
    }
    public void setClasse(String classe){this.classe = classe;
    }public void setMethod(String method){
        this.method = method;
    }




    @Override
    public String toString(){
        return log;
    }


}
