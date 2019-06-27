package com.softhex.sonic;

import android.provider.ContactsContract;

import java.text.DecimalFormat;
import java.util.Date;

/**
 * Created by Administrador on 04/08/2017.
 */

public class sonicFinanceiroHolder {

     int codigo_cliente;
     String razao_social;
     String limite_credito;
     String saldo;
     String maior_compra;
     String data_maior_compra;
     String ulltima_compra;
     String data_ultima_compra;

    public sonicFinanceiroHolder(){

    }
    public sonicFinanceiroHolder(int codigo_cliente,
                                 String razao_social,
                                 String limite_credito,
                                 String saldo,
                                 String maior_compra,
                                 String data_maior_compra,
                                 String ulltima_compra,
                                 String data_ultima_compra
                                ){

        this.razao_social = razao_social;
        this.codigo_cliente = codigo_cliente;
        this.limite_credito = limite_credito;
        this.saldo = saldo;
        this.maior_compra = maior_compra;
        this.data_maior_compra = data_maior_compra;
        this.ulltima_compra = ulltima_compra;
        this.data_ultima_compra = data_ultima_compra;

    }

    public int getCodigoCliente(){
        return this.codigo_cliente;
    }
    public String getRazaoSocial(){
        return this.razao_social;
    }
    public String getLimiteCredito(){
        return this.limite_credito;
    }
    public String getSaldo(){
        return this.saldo;
    }
    public String getMaiorCompra() {return this.maior_compra;}
    public String getDataMaiorCompra() {return this.data_maior_compra;}
    public String getUltimaCompra() {return this.ulltima_compra;}
    public String getDataUltimaCompra() {return this.data_ultima_compra;}


    public void setCodigoCliente(int codigo_cliente){
        this.codigo_cliente = codigo_cliente;
    }
    public void setRazaoSocial(String razao_social){
        this.razao_social = razao_social;
    }
    public void setLimiteCredito(String limite_credito){
        this.limite_credito = limite_credito;
    }
    public void setSaldo(String saldo){
        this.saldo = saldo;
    }
    public void setMaiorCompra(String maior_compra){
        this.maior_compra = maior_compra;
    }
    public void setDataMaiorCompra(String data_maior_compra){ this.data_maior_compra = data_maior_compra; }
    public void setUlltimaCompra(String ulltima_compra){
        this.ulltima_compra = ulltima_compra;
    }
    public void setDataUltimaCompra(String data_ultima_compra){ this.data_ultima_compra = data_ultima_compra; }


    @Override
    public String toString(){
        return razao_social;
    }


}
