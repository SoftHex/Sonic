package com.softhex.sonic;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrador on 04/08/2017.
 */

public class sonicPopularTabelas {

    private Context myCtx;
    private sonicDatabaseLogCRUD DBCL;
    private sonicDatabaseCRUD DBC;
    private sonicSystem mySystem;
    private ProgressDialog myProgress;
    private String arquivo;
    private String[][] mTables = {

            { "[SITE]", sonicConstants.TB_SITE },
            { "[FTP]", sonicConstants.TB_FTP },
            { "[EMPRESAS]", sonicConstants.TB_EMPRESA },
            { "[NIVEL_ACESSO]", sonicConstants.TB_NIVEL_ACESSO },
            { "[USUARIOS]", sonicConstants.TB_USUARIO },
            { "[EMPRESAS_USUARIOS]", sonicConstants.TB_EMPRESA_USUARIO },
            { "[GRUPO_EMPRESAS]", sonicConstants.TB_GRUPO_EMPRESAS },
            { "[CLIENTES]", sonicConstants.TB_CLIENTE },
            { "[GRUPO_CLIENTES]", sonicConstants.TB_GRUPO_CLIENTE },
            { "[EMPRESAS_CLIENTES]", sonicConstants.TB_EMPRESA_CLIENTE },
            { "[PRODUTOS]", sonicConstants.TB_PRODUTO },
            { "[GRUPO_PRODUTOS]", sonicConstants.TB_GRUPO_PRODUTO },
            { "[ESTOQUE_PRODUTOS]", sonicConstants.TB_ESTOQUE_PRODUTO },
            { "[TABELA_PRECO]", sonicConstants.TB_TABELA_PRECO },
            { "[TABELA_PRECO_EMPRESA]", sonicConstants.TB_TABELA_PRECO_EMPRESA },
            { "[TABELA_PRECO_PRODUTO]", sonicConstants.TB_TABELA_PRECO_PRODUTO },
            { "[TIPO_COBRANCA]", sonicConstants.TB_TIPO_COBRANCA },
            { "[TIPO_PEDIDO]", sonicConstants.TB_TIPO_PEDIDO },
            { "[UNIDADE_MEDIDA]", sonicConstants.TB_UNIDADE_MEDIDA },
            { "[ROTA]", sonicConstants.TB_ROTA },
            { "[VENDAS]", sonicConstants.TB_VENDA },
            { "[VENDAS_ITENS]", sonicConstants.TB_VENDA_ITEM },
            { "[CLIENTE_SEM_COMPRA]", sonicConstants.TB_CLIENTE_SEM_COMPRA },
            { "[TITULOS]", sonicConstants.TB_TITULO },

    };

    sonicPopularTabelas(Context ctx){
        this.myCtx = ctx;
        this.DBCL = new sonicDatabaseLogCRUD(myCtx);
        this.mySystem = new sonicSystem(myCtx);
        this.DBC = new sonicDatabaseCRUD(myCtx);
    }

    public void gravarDados(String arquivo){

        File file = new File(Environment.getExternalStorageDirectory(),sonicConstants.LOCAL_TEMP+arquivo);

        if(file.exists()){

            new Handler(Looper.getMainLooper()).post(()-> {

                myProgress = new ProgressDialog(myCtx);
                myProgress.setCancelable(false);
                myProgress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                myProgress.setTitle("Preparando arquivo...");
                myProgress.setMessage("");
                myProgress.setProgress(0);
                myProgress.show();
                new myAsyncTaskGravar().execute(arquivo);

            });
        }else {
            new sonicDialog(myCtx).showMI(R.string.headerWarning,R.string.fileNotFound, sonicDialog.MSG_WARNING);
        }

    }

    protected static boolean contains(CharSequence sequence)
    {
        return String.valueOf(sequence).indexOf(sequence.toString()) > -1;
    }

    class myAsyncTaskGravar extends AsyncTask<String, String, Boolean>{
        @Override
        protected Boolean doInBackground(String... strings) {

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            FileInputStream f;
            BufferedReader reader;
            arquivo = strings[0];

            File file = new File(Environment.getExternalStorageDirectory(), sonicConstants.LOCAL_TEMP + arquivo);

            if(file.exists()){

                try {

                    f = new FileInputStream(file);
                    reader = new BufferedReader(new InputStreamReader(f, StandardCharsets.ISO_8859_1));
                    int count=0;
                    myProgress.setMax(sonicUtils.countFileLines(file));
                    String tabela = "";

                    String line = reader.readLine();

                    List<String[]> list = Arrays.asList(mTables);

                    while (line!=null){
                        for(String[] arr: list){
                            //Log.d(Arrays.asList(arr).get(0), Arrays.asList(arr).get(1));

                            if(line!=null && (line.contains(arr[0]) || arr[0].contains(line))){

                                DBC.Database.cleanData(arr[1]);
                                tabela = line;
                                line = reader.readLine();

                                while (line != null && line.indexOf("[") != 0) {

                                    count += 1;
                                    publishProgress(tabela, String.valueOf(count));

                                    String str = line;
                                    int pos = str.indexOf("=") + 1;
                                    int len = str.length();
                                    String str2 = str.substring(pos, len);
                                    List<String> data = Arrays.asList(str2.split(";", -1));
                                    DBC.Database.saveData(arr[1], data);
                                    line = reader.readLine();

                                }
                            }
                        }

                        line = reader.readLine();
                        reader.mark(0);
                        reader.reset();

                    }

                }catch (Exception e){
                    e.printStackTrace();
                    DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                    return false;
                }

                return true;

            }

            return true;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            myProgress.setMessage(values[0]);
            myProgress.setProgress(Integer.parseInt(values[1]));
        }

        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            myProgress.dismiss();
            new sonicUtils(myCtx).Arquivo.deleteFile(sonicConstants.LOCAL_TEMP+arquivo);
            if(aBoolean){
                switch (sonicConstants.DOWNLOAD_TYPE){
                    case "DADOS":
                        new sonicDialog(myCtx).showMI(R.string.headerSuccess,R.string.dadosSincronizados, sonicDialog.MSG_SUCCESS);
                        new sonicDatabaseCRUD(myCtx).Sincronizacao.saveSincronizacao("dados","geral");
                        break;
                    case "ESTOQUE":
                        new sonicDialog(myCtx).showMI(R.string.headerSuccess,R.string.estoqueSincronizado, sonicDialog.MSG_SUCCESS);
                        new sonicDatabaseCRUD(myCtx).Sincronizacao.saveSincronizacao("dados","estoque");
                        break;
                    case "SITE":
                        primeiroAcesso();
                        break;


                }
            }
        }
    }

    public void primeiroAcesso(){

        TelephonyManager myPhoneManager = (TelephonyManager)myCtx.getSystemService(Context.TELEPHONY_SERVICE);

        if (ActivityCompat.checkSelfPermission(myCtx, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED){


            if(new sonicDatabaseCRUD(myCtx).Database.checkMinimumData()){

                String imei = "";

                if(android.os.Build.VERSION.SDK_INT >= 26) {
                    imei = myPhoneManager.getImei();

                }else{
                    imei = myPhoneManager.getDeviceId();
                }

                Log.d("IMEI", imei);

                if(sonicConstants.EMP_TESTE){
                    imei = "0000000000000000000";
                }

                sonicConstants.USER_IMEI = imei;

                List<sonicUsuariosHolder> lista;
                lista = DBC.Usuario.selectUsuarioImei(imei);
                if(!lista.isEmpty()){

                    startActivity(lista.get(0).getEmpresa(), lista.get(0).getEmpresaId() , lista.get(0).getCodigo() ,lista.get(0).getNome(), lista.get(0).getCargo(), imei);

                }else{
                    new sonicDialog(myCtx).showMS("::: Atenção :::" , "Seu aparelho com o IMEI: " + imei + " não está cadastrado para usar o sistema. Favor entrar em contato com o responsável na empresa pela administração do serviço.", sonicDialog.MSG_WARNING);
                    sonicConstants.EMP_TESTE = false;
                }

            }else{

                new sonicDialog(myCtx).showMS("::: Atenção :::", "Não foi possível gravar os dados nas tabelas. O arquivo solicitado parece não conter dados suficientes ou está mal formatado.", sonicDialog.MSG_WARNING);
                sonicConstants.EMP_TESTE = false;
            }

        }else{
            ActivityCompat.requestPermissions(((Activity) myCtx),
                    new String[]{Manifest.permission.READ_PHONE_STATE},
                    1);

        }

    }

    public void startActivity(String empresa, int empresa_id, int id, String usuario, String cargo, String imei){

        Intent i = new Intent(myCtx, sonicFirstAccess.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("ID", id);
        i.putExtra("EMPRESA", empresa);
        i.putExtra("EMPRESA_ID", empresa_id);
        i.putExtra("USUARIO", usuario);
        i.putExtra("CARGO", cargo);
        i.putExtra("IMEI", imei);

        sonicPreferences pref = new sonicPreferences(myCtx);
        pref.Users.setEmpresaId(empresa_id);
        pref.Users.setEmpresaNome(empresa);
        pref.Users.setUsuarioId(id);
        pref.Users.setUsuarioNome(usuario);
        pref.Users.setUsuarioCargo(cargo);
        myCtx.startActivity(i);
        ((AppCompatActivity) myCtx).finish();

    }



}
