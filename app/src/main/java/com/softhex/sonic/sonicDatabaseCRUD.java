package com.softhex.sonic;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.preference.PreferenceManager;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Administrador on 10/07/2017.
 */

public class sonicDatabaseCRUD {

    private final String TABLE_SITE = sonicConstants.TB_SITE;
    private final String TABLE_FTP = sonicConstants.TB_FTP;
    private final String TABLE_EMPRESAS = sonicConstants.TB_EMPRESAS;
    private final String TABLE_NIVEL_ACESSO = sonicConstants.TB_NIVEL_ACESSO;
    private final String TABLE_USUARIOS = sonicConstants.TB_USUARIOS;
    private final String TABLE_EMPRESAS_USUARIOS = sonicConstants.TB_EMPRESAS_USUARIOS;
    private final String TABLE_HITORICO_USUARIO = sonicConstants.TB_HISTORICO_USUARIO;
    private final String TABLE_CLIENTES = sonicConstants.TB_CLIENTES;
    private final String TABLE_EMPRESAS_CLIENTES = sonicConstants.TB_EMPRESAS_CLIENTES;
    private final String TABLE_GRUPO_CLIENTES = sonicConstants.TB_GRUPO_CLIENTES;
    private final String TABLE_RANKING_CLIENTES = sonicConstants.TB_RANKING_CLIENTES;
    private final String TABLE_CLIENTES_SEM_COMPRA = sonicConstants.TB_CLIENTES_SEM_COMPRA;
    private final String TABLE_PRODUTOS = sonicConstants.TB_PRODUTOS;
    private final String TABLE_GRUPO_PRODUTO = sonicConstants.TB_GRUPO_PRODUTOS;
    private final String TABLE_ESTOQUE_PRODUTOS = sonicConstants.TB_ESTOQUE;
    private final String TABLE_FINANCEIRO = sonicConstants.TB_FINANCEIRO;
    private final String TABLE_TITULOS = sonicConstants.TB_TITULOS;
    private final String TABLE_RETORNO_PEDIDO = sonicConstants.TB_RETORNO_PEDIDO;
    private final String TABLE_RETORNO_PEDIDO_ITENS = sonicConstants.TB_RETORNO_PEDIDO_ITENS;
    private final String TABLE_TIPO_COBRANCA = sonicConstants.TB_TIPO_COBRANCA;
    private final String TABLE_CONDICOES_PAGAMENTO = sonicConstants.TB_CONDICOES_PAGAMENTO;
    private final String TABLE_PRECO_PRODUTO = sonicConstants.TB_PRECO_PRODUTO;
    private final String TABLE_TABELA_PRECO = sonicConstants.TB_TABELA_PRECO;
    private final String TABLE_VENDAS = sonicConstants.TB_VENDAS;
    private final String TABLE_VENDAS_ITENS = sonicConstants.TB_VENDAS_ITENS;
    private final String TABLE_RANKING_PRODUTOS = sonicConstants.TB_RANKING_PRODUTOS;
    private final String TABLE_TRANSPORTADORA = sonicConstants.TB_TRANSPORTADORA;
    private final String TABLE_UNIDADE_MEDIDA = sonicConstants.TB_UNIDADE_MEDIDA;
    private final String TABLE_TIPO_PEDIDO = sonicConstants.TB_TIPO_PEDIDO;
    private final String TABLE_TIPO_DOCUMENTO = sonicConstants.TB_TIPO_DOCUMENTO;
    private final String TABLE_DESCONTO = sonicConstants.TB_DESCONTO;
    private final String TABLE_FORMA_PAGAMENTO = sonicConstants.TB_FORMA_PAGAMENTO;
    private final String TABLE_PRAZO = sonicConstants.TB_PRAZO;
    private final String TABLE_TABELA_PRECO_CLIENTE = sonicConstants.TB_TABELA_PRECO_CLIENTE;
    private final String TABLE_FRETE = sonicConstants.TB_FRETE;
    private final String TABLE_AVISOS = sonicConstants.TB_AVISOS;
    private final String TABLE_AVISOS_LIDOS = sonicConstants.TB_AVISOS_LIDOS;
    private final String TABLE_SINCRONIZACAO = sonicConstants.TB_SINCRONIZACAO;
    private final String TABLE_LOCALIZACAO = sonicConstants.TB_LOCALIZACAO;
    private final String TABLE_LOG_ERRO = sonicConstants.TB_LOG_ERRO;
    private sonicDatabase DB;
    private sonicDatabaseLogCRUD DBCL;
    private sonicUtils myUtil;
    private SharedPreferences prefs;
    private sonicConstants myCons;
    private Context myCtx;
    private sonicSystem mySystem;

    public sonicDatabaseCRUD(Context ctx){

        this.myCtx = ctx;
        this.DB = new sonicDatabase(myCtx);
        this.DBCL = new sonicDatabaseLogCRUD(myCtx);
        this.prefs = PreferenceManager.getDefaultSharedPreferences(myCtx);
        this.myUtil = new sonicUtils(myCtx);
        this.myCons = new sonicConstants();
        this.mySystem = new sonicSystem(myCtx);

    }


    Database Database = new Database();
    Site Site = new Site();
    Ftp Ftp = new Ftp();
    Empresa Empresa = new Empresa();
    Usuarios Usuarios = new Usuarios();
    HistoricoUsuario HistoricoUsuario = new HistoricoUsuario();
    EmpresasUsuarios EmpresasUsuarios = new EmpresasUsuarios();
    sonicDatabaseCRUD.NivelAcesso NivelAcesso = new NivelAcesso();
    Clientes Clientes = new Clientes();
    EmpresasClientes EmpresasClientes = new EmpresasClientes();
    Vendas Vendas = new Vendas();
    VendasItens VendasItens = new VendasItens();
    GrupoCliente GrupoCliente = new GrupoCliente();
    RankingCliente RankingClientes = new RankingCliente();
    ClienteSemCompra ClienteSemCompra = new ClienteSemCompra();
    Produto Produto = new Produto();
    Estoque Estoque = new Estoque();
    Titulos Titulos = new Titulos();
    Retorno Retorno = new Retorno();
    Financeiro Financeiro = new Financeiro();
    TabelaPreco TabelaPreco = new TabelaPreco();
    GrupoProduto GrupoProduto = new GrupoProduto();
    UnidadeMedida UnidadeMedida = new UnidadeMedida();
    Sincronizacao Sincronizacao = new Sincronizacao();
    Localizacao Localizacao = new Localizacao();
    TipoPedido TipoPedido = new TipoPedido();
    Prazo Prazo = new Prazo();
    Avisos Avisos = new Avisos();

    class Database{

        public boolean checkMinimumData(){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            Boolean result = true;

                try{
                    Cursor cursor;
                    cursor = DB.getReadableDatabase().rawQuery("SELECT * FROM "+TABLE_SITE , null);
                    result = result && cursor.moveToFirst();
                    Log.d("TABLE_SITE", result.toString());
                    cursor = DB.getReadableDatabase().rawQuery("SELECT * FROM "+TABLE_FTP , null);
                    result = result && cursor.moveToFirst();
                    Log.d("TABLE_FTP", result.toString());
                    cursor = DB.getReadableDatabase().rawQuery("SELECT * FROM "+TABLE_EMPRESAS , null);
                    result = result && cursor.moveToFirst();
                    Log.d("TABLE_EMPRESAS", result.toString());
                    cursor = DB.getReadableDatabase().rawQuery("SELECT * FROM "+TABLE_NIVEL_ACESSO , null);
                    result = result && cursor.moveToFirst();
                    Log.d("TABLE_NIVEL_ACESSO", result.toString());
                    cursor = DB.getReadableDatabase().rawQuery("SELECT * FROM "+TABLE_USUARIOS , null);
                    result = result && cursor.moveToFirst();
                    Log.d("TABLE_USUARIOS", result.toString());
                    cursor = DB.getReadableDatabase().rawQuery("SELECT * FROM "+TABLE_EMPRESAS_USUARIOS , null);
                    result = result &&  cursor.moveToFirst();
                    Log.d("TABLE_E_USUARIOS", result.toString());
                }catch (SQLiteException e){
                    e.printStackTrace();
                                    DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                }

            return result;
        }

        public void truncateAllTables(){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];

            try{

                Cursor cursor = DB.getReadableDatabase().rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name NOT IN ('sqlite_master', 'sqlite_sequence')", null);
                List<String> tables = new ArrayList<>();

                while (cursor.moveToNext()){
                    tables.add(cursor.getString(0));
                }

                for(String table: tables){
                    DB.getWritableDatabase().delete(table, null, null);
                }

            }catch (SQLiteException e){
                                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();

            }


        }

    }

    class Site{

        public boolean saveSite(List<String> lista){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            Boolean result = false;
            ContentValues cv = new ContentValues();

            try{
                for(int i = 0; i < lista.size(); i++) {

                    cv.put("Site", lista.get(0));
                    cv.put("licensa", lista.get(1));

                }

                result = DB.getWritableDatabase().insert(TABLE_SITE, null, cv)>0;

            }catch (SQLiteException e){

                DBCL.Log.saveLog(
                        e.getStackTrace()[0].getLineNumber(),
                        e.getMessage(),
                        mySystem.System.getActivityName(),
                        mySystem.System.getClassName(el),
                        mySystem.System.getMethodNames(el));
                e.printStackTrace();


            }

            return result;
        }

        public boolean cleanSite(){

            return DB.getWritableDatabase().delete(TABLE_SITE, null, null)>0;
        }

    }

    class Ftp{

        public boolean saveFtp(List<String> lista){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            Boolean result = false;
            ContentValues cv = new ContentValues();

            try{
                for(int i = 0; i < lista.size(); i++) {

                    cv.put("Ftp", lista.get(0));
                    cv.put("user", lista.get(1));
                    cv.put("pass", lista.get(2));

                }

                result = DB.getWritableDatabase().insert(TABLE_FTP, null, cv)>0;

            }catch (SQLiteException e){

                                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();

            }
            return result;

        }

        public boolean cleanFtp(){

            return DB.getWritableDatabase().delete(TABLE_FTP, null, null)>0;
        }

        public String ftp() {

            Cursor cursor = DB.getReadableDatabase().rawQuery(
                    "SELECT ftp FROM "+TABLE_FTP , null);
            String ftp = null;
            if(cursor.moveToFirst()) {
                ftp = cursor.getString(cursor.getColumnIndex("ftp"));
            }
            return ftp;
        }
        public String user() {

            Cursor cursor = DB.getReadableDatabase().rawQuery(
                    "SELECT user FROM "+TABLE_FTP , null);
            String user = null;
            if(cursor.moveToFirst()) {
                    user = cursor.getString(cursor.getColumnIndex("user"));
            }
            return user;
        }

        public String pass() {

            Cursor cursor = DB.getReadableDatabase().rawQuery(
                    "SELECT pass FROM "+TABLE_FTP , null);
            String pass = null;
            if(cursor.moveToFirst()) {
                pass = cursor.getString(cursor.getColumnIndex("pass"));
            }
            return pass;
        }

    }

    class Empresa{

        public boolean saveEmpresa(List<String> lista){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            Boolean result = false;
            ContentValues cv = new ContentValues();

            try{
                for(int i = 0; i < lista.size(); i++){

                    cv.put("codigo", lista.get(0));
                    cv.put("razao_social", lista.get(1));
                    cv.put("nome_fantasia", lista.get(2));
                    if(i==0){
                        cv.put("selecionada", 1);
                    }else{
                        cv.put("selecionada", 0);
                    }

                }

                result = DB.getWritableDatabase().insert(TABLE_EMPRESAS, null, cv)>0;

            }catch (SQLiteException e){

                                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();

            }
            return result;
        }

        public boolean cleanEmpresa(){

            return DB.getWritableDatabase().delete(TABLE_EMPRESAS, null, null)>0;
        }

        public String empresaPadrao() {

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            String empresa = null;

            try{

                Cursor cursor = DB.getReadableDatabase().rawQuery(
                        "SELECT nome_fantasia FROM "+TABLE_EMPRESAS+" LIMIT 1 " , null);

                if(cursor.moveToFirst()) {
                    empresa = cursor.getString(cursor.getColumnIndex("nome_fantasia"));
                }

            }catch (SQLiteException e){
                                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();
            }

            return empresa;
        }

        public int empresaPadraoId() {

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            int empresa = 0;

            try{

                Cursor cursor = DB.getReadableDatabase().rawQuery(
                        "SELECT e.codigo FROM "+TABLE_EMPRESAS+" e LIMIT 1 " , null);

                if(cursor.moveToFirst()) {
                    empresa = cursor.getInt(cursor.getColumnIndex("codigo"));
                }

            }catch (SQLiteException e){
                                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();
            }

            return empresa;
        }

        public boolean empresaExiste(){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            boolean result = false;

            try{
                Cursor cursor = DB.getReadableDatabase().rawQuery("SELECT * FROM "+TABLE_EMPRESAS, null);
                if(cursor!=null && cursor.getCount()>0){
                    result = true;
                }

            }catch (SQLiteException e){
                                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();
            }

            return  result;
        }

        /**
         * Verificase existe alguma Empresa já selecionada
         * @return
         */
        public boolean empresaSelecionada(){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            boolean result = false;

            try{
                Cursor cursor = DB.getReadableDatabase().rawQuery("SELECT * FROM "+TABLE_EMPRESAS+" e WHERE e.selecionada=1  ", null);
                if(cursor!=null && cursor.getCount()>0){
                    result = true;
                }

            }catch (SQLiteException e){
                                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();
            }

            return  result;
        }

        public List<sonicEmpresasHolder> selectEmpresas(){
            List<sonicEmpresasHolder> empresas = new ArrayList<sonicEmpresasHolder>();

            Cursor cursor = DB.getReadableDatabase().rawQuery(
                    "SELECT * FROM "+TABLE_EMPRESAS , null);

            while(cursor.moveToNext()){

                sonicEmpresasHolder empresa = new sonicEmpresasHolder();

                empresa.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
                empresa.setNomeFantasia(cursor.getString(cursor.getColumnIndex("nome_fantasia")));


                empresas.add(empresa);

            }
            cursor.close();
            return empresas;
        }

        public List<sonicEmpresasHolder> empresasUsuarios(){
            List<sonicEmpresasHolder> empresas = new ArrayList<sonicEmpresasHolder>();

            String query = "SELECT " +
                    "e.nome_fantasia, " +
                    "e.razao_social, " +
                    "e.codigo " +
                    "FROM " + TABLE_EMPRESAS + " e " +
                    "JOIN " + TABLE_EMPRESAS_USUARIOS + " eu " +
                    "ON eu.codigo_empresa = e.codigo " +
                    "JOIN " + TABLE_USUARIOS + " u " +
                    "ON u.codigo = eu.codigo_usuario " +
                    "WHERE u.ativo = 1 ORDER BY e.selecionada DESC";

            Cursor cursor = DB.getReadableDatabase().rawQuery(query, null);

            while(cursor.moveToNext()){

                sonicEmpresasHolder empresa = new sonicEmpresasHolder();

                empresa.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
                empresa.setNomeFantasia(cursor.getString(cursor.getColumnIndex("nome_fantasia")));
                empresa.setRazaoSocial(cursor.getString(cursor.getColumnIndex("razao_social")));

                empresas.add(empresa);

            }
            cursor.close();
            return empresas;
        }

        /**
         * Desmarca todas as empresas selecionadas para fazer uma nova seleção
         */
        public void desmarcarEmpresas() {

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            ContentValues args = new ContentValues();
            args.put("selecionada", 0);

            try {

                DB.getWritableDatabase().update(TABLE_EMPRESAS, args, null, null);

            } catch (SQLiteException e) {
                                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();

            }

        }
        /**
         * Marca todas as empresas existentes
         */
        public void marcarEmpresas() {

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            ContentValues args = new ContentValues();
            args.put("selecionada", 1);

            try {

                DB.getWritableDatabase().update(TABLE_EMPRESAS, args, null, null);

            } catch (SQLiteException e) {
                                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();

            }

        }
        /**
         * Marca a Empresa como selecionada de acordo com o código enviado
         * Tipo código: INT
         */
        public void setSelecionada(int codigo) {

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            ContentValues args = new ContentValues();
            args.put("selecionada", 1);

            try {

                DB.getWritableDatabase().update(TABLE_EMPRESAS, args, "codigo="+codigo, null);

            } catch (SQLiteException e) {
                                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();

            }

        }
    }

    class EmpresasClientes{

        public long count() {

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            long count = 0;

            SQLiteDatabase db = DB.getReadableDatabase();
            try {
                count = DatabaseUtils.queryNumEntries(db, TABLE_EMPRESAS_CLIENTES);
            } catch (SQLiteException e) {
                DBCL.Log.saveLog(
                        e.getStackTrace()[0].getLineNumber(),
                        e.getMessage(),
                        mySystem.System.getActivityName(),
                        mySystem.System.getClassName(el),
                        mySystem.System.getMethodNames(el));
                e.printStackTrace();
            }

            return count;
        }


        public boolean saveEmpresasClientes(List<String> lista){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            Boolean result = false;
            ContentValues cv = new ContentValues();

            try{
                for(int i = 0; i < lista.size(); i++){

                    cv.put("codigo_empresa", lista.get(0));
                    cv.put("codigo_cliente", lista.get(1));

                }

                result = DB.getWritableDatabase().insert(TABLE_EMPRESAS_CLIENTES, null, cv)>0;

            }catch (SQLiteException e){

                DBCL.Log.saveLog(
                        e.getStackTrace()[0].getLineNumber(),
                        e.getMessage(),
                        mySystem.System.getActivityName(),
                        mySystem.System.getClassName(el),
                        mySystem.System.getMethodNames(el));
                e.printStackTrace();

            }
            return result;
        }

        public boolean cleanEmpresasClientes() {

            return DB.getWritableDatabase().delete(TABLE_EMPRESAS_CLIENTES, null, null) > 0;
        }

    }

    class NivelAcesso {

        public boolean saveNivelAcesso(List<String> lista){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            Boolean result = false;
            ContentValues cv = new ContentValues();
            //DB.getWritableDatabase().beginTransaction();

            try{
                for(int i = 0; i < lista.size(); i++){

                    cv.put("nivel", lista.get(0));
                    cv.put("nome", lista.get(1));

                }

                result = DB.getWritableDatabase().insert(TABLE_NIVEL_ACESSO, null, cv)>0;

            }catch (SQLiteException e){

                                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();

            }

            return result;
        }

        public boolean nivelAcesso(){

            return DB.getWritableDatabase().delete(TABLE_NIVEL_ACESSO, null, null)>0;
        }

    }


    class Avisos {

        public long count() {

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            long count = 0;
            SQLiteDatabase db = DB.getReadableDatabase();
            try {
                count = DatabaseUtils.queryNumEntries(db, TABLE_AVISOS);
            } catch (SQLiteException e) {
                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();
            }

            return count;
        }

        public int countNaoLidos() {

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            int count = 0;

            SQLiteDatabase db = DB.getReadableDatabase();
            try {
                Cursor cursor = db.rawQuery("SELECT _id FROM "+TABLE_AVISOS+" WHERE codigo NOT IN (SELECT DISTINCT(codigo) FROM "+TABLE_AVISOS_LIDOS+")", null);;
                count = cursor.getCount();
            } catch (SQLiteException e) {
                                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();
            }

            return count;
        }

        public List<sonicAvisosHolder> selectAvisos(){
            List<sonicAvisosHolder> avisos = new ArrayList<sonicAvisosHolder>();

            Cursor cursor = DB.getReadableDatabase().rawQuery(
                    "SELECT " +
                            "a.codigo," +
                            "a.prioridade," +
                            "a.autor," +
                            "a.data," +
                            "a.hora," +
                            "a.titulo," +
                            "a.mensagem," +
                            "CASE WHEN EXISTS(SELECT DISTINCT(al.codigo) FROM "+TABLE_AVISOS_LIDOS+" al WHERE al.codigo = a.codigo) THEN 1 ELSE 0 END AS status" +
                            " FROM " + TABLE_AVISOS + " a ORDER BY a.data DESC" , null);

            while(cursor.moveToNext()){

                sonicAvisosHolder aviso = new sonicAvisosHolder();

                aviso.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
                aviso.setPrioridade(cursor.getInt(cursor.getColumnIndex("prioridade")));
                aviso.setAutor(cursor.getString(cursor.getColumnIndex("autor")));
                aviso.setData(cursor.getString(cursor.getColumnIndex("data")));
                aviso.setHora(cursor.getString(cursor.getColumnIndex("hora")));
                aviso.setTitulo(cursor.getString(cursor.getColumnIndex("titulo")));
                aviso.setMensagem(cursor.getString(cursor.getColumnIndex("dialogRedefinir")));
                aviso.setStatus(cursor.getInt(cursor.getColumnIndex("status")));

                avisos.add(aviso);

            }
            cursor.close();
            return avisos;
        }


        public boolean saveAvisos(List<String> lista) {

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            Boolean result = false;
            ContentValues cv = new ContentValues();

            try {
                for (int i = 0; i < lista.size(); i++) {

                    cv.put("codigo", lista.get(0));
                    cv.put("autor", lista.get(1));
                    cv.put("data", lista.get(2));
                    cv.put("hora", lista.get(3));
                    cv.put("prioridade", lista.get(4));
                    cv.put("titulo", lista.get(5));
                    cv.put("dialogRedefinir", lista.get(6));
                    cv.put("status", 0);

                }

                result = DB.getWritableDatabase().insert(TABLE_AVISOS, null, cv) > 0;

            } catch (SQLiteException e) {

                                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();

            }

                return result;
            }

        public boolean saveAvisosLidos(int id) {

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            Boolean result = false;
            ContentValues cv = new ContentValues();

            try {

                    cv.put("codigo", id);

                result = DB.getWritableDatabase().insert(TABLE_AVISOS_LIDOS, null, cv) > 0;

            } catch (SQLiteException e) {
                                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();

            }

            return result;
        }

        public boolean deleteAvisosLidos(int id) {

            return DB.getWritableDatabase().delete(TABLE_AVISOS, "codigo=?"+id, null) > 0;
        }


        public boolean cleanAvisos() {

            return DB.getWritableDatabase().delete(TABLE_AVISOS, null, null) > 0;
        }

        }
        class Clientes {

            public long count() {

                StackTraceElement el = Thread.currentThread().getStackTrace()[2];
                long count = 0;
                SQLiteDatabase db = DB.getReadableDatabase();
                try {
                    count = DatabaseUtils.queryNumEntries(db, TABLE_CLIENTES);
                } catch (SQLiteException e) {
                    DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(), e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                    e.printStackTrace();
                }

                return count;
            }

            /**
             * Conta os clientes por Empresa.
             */
            public int countPorEmpresa() {

                StackTraceElement el = Thread.currentThread().getStackTrace()[2];
                int count = 0;

                SQLiteDatabase db = DB.getReadableDatabase();
                try {
                    Cursor cursor = db.rawQuery("SELECT c.codigo FROM " + TABLE_CLIENTES + " c WHERE c.codigo IN (SELECT ec.codigo_cliente FROM " + TABLE_EMPRESAS_CLIENTES + " ec WHERE ec.codigo_empresa = (SELECT e.codigo FROM "+ TABLE_EMPRESAS +" e WHERE e.selecionada = 1))", null);
                    count = cursor.getCount();
                } catch (SQLiteException e) {
                    DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(), e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                    e.printStackTrace();
                }

                return count;
            }

            public boolean saveCliente(List<String> lista) {

                StackTraceElement el = Thread.currentThread().getStackTrace()[2];
                Boolean result = false;
                ContentValues cv = new ContentValues();

                try {
                    for (int i = 0; i < lista.size(); i++) {

                        cv.put("codigo", lista.get(0));
                        cv.put("tipo", lista.get(1));
                        cv.put("razao_social", lista.get(2));
                        cv.put("nome_fantasia", lista.get(3));
                        cv.put("cpf_cnpj", lista.get(4));
                        cv.put("insc_estadual", lista.get(5));
                        cv.put("endereco", lista.get(6));
                        cv.put("bairro", lista.get(7));
                        cv.put("municipio", lista.get(8));
                        cv.put("uf", lista.get(9));
                        cv.put("cep", lista.get(10));
                        cv.put("fone", lista.get(11));
                        cv.put("contato", lista.get(12));
                        cv.put("email", lista.get(13));
                        cv.put("observacao", lista.get(14));
                        cv.put("data_cadastro", lista.get(15));
                        cv.put("codigo_grupo", lista.get(16));
                        cv.put("situacao", lista.get(17));
                        // POSIÇÃO 18 VENDEDOR, IGNORAR
                        cv.put("selecionado", 0);

                    }

                    result = DB.getWritableDatabase().insert(TABLE_CLIENTES, null, cv) > 0;

                } catch (SQLiteException e) {

                    DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(), e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                    e.printStackTrace();

                }

                return result;
            }

            public boolean cleanCliente() {

                return DB.getWritableDatabase().delete(TABLE_CLIENTES, null, null) > 0;
            }

            public void setSelecionado(int codigo) {

                StackTraceElement el = Thread.currentThread().getStackTrace()[2];
                ContentValues args = new ContentValues();
                args.put("selecionado", 1);

                try {

                    DB.getWritableDatabase().update(TABLE_CLIENTES, args, " codigo = " + codigo, null);

                } catch (SQLiteException e) {
                    DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(), e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                    e.printStackTrace();

                }

            }

            public void setNaoSelecionado() {

                StackTraceElement el = Thread.currentThread().getStackTrace()[2];
                ContentValues args = new ContentValues();
                args.put("selecionado", 0);

                try {

                    DB.getWritableDatabase().update(TABLE_CLIENTES, args, null, null);

                } catch (SQLiteException e) {
                    DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(), e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                    e.printStackTrace();

                }

            }

            public Boolean selecionadoExiste() {

                StackTraceElement el = Thread.currentThread().getStackTrace()[2];
                Boolean result = false;

                try {
                    Cursor cursor = DB.getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_CLIENTES + " c WHERE  c.selecionado = 1 ", null);
                    if (cursor != null && cursor.getCount() > 0) {
                        result = true;
                    }
                } catch (SQLiteException e) {
                    DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(), e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                    e.printStackTrace();
                }

                return result;
            }


            public List<sonicClientesHolder> selectClienteTipo(String tipo) {

                StackTraceElement el = Thread.currentThread().getStackTrace()[2];
                List<sonicClientesHolder> clientes = new ArrayList<sonicClientesHolder>();

                String order = prefs.getString("show_name", "0");
                String orderby = "";

                String where = "";

                if (myCons.GRUPO_CLIENTES != "TODOS") {
                    where += " AND gc.nome = '" + myCons.GRUPO_CLIENTES + "' ";
                }

                switch (order) {
                    case "0":
                        orderby = " ORDER BY razao_social";
                        break;
                    case "1":
                        orderby = " ORDER BY nome_fantasia";
                        break;
                    default:
                        orderby = " ORDER BY razao_social";
                }

                try {

                    Cursor cursor = DB.getReadableDatabase().rawQuery(
                            "SELECT " +
                                    "C.codigo AS codigo, " +
                                    "C.tipo AS tipo, " +
                                    "C.razao_social AS razao, " +
                                    "C.nome_fantasia AS fantasia, " +
                                    "C.cpf_cnpj AS cpf_cnpj, " +
                                    "C.insc_estadual AS ie, " +
                                    "C.endereco AS endereco, " +
                                    "C.bairro AS bairro, " +
                                    "C.municipio AS municipio, " +
                                    "C.uf AS uf, " +
                                    "C.cep AS cep, " +
                                    "C.fone AS fone, " +
                                    "C.contato AS contato, " +
                                    "C.email AS email, " +
                                    "C.observacao AS obs, " +
                                    "C.data_cadastro AS cadastro, " +
                                    "GC.nome AS grupo, " +
                                    "(SELECT COUNT(T._id) FROM " + TABLE_TITULOS + " T WHERE T.codigo_cliente = C.codigo) AS titulos, " +
                                    "C.situacao AS situacao" +
                                    " FROM " + TABLE_CLIENTES +
                                    " C LEFT JOIN " + TABLE_GRUPO_CLIENTES +
                                    " GC ON gc.codigo = C.codigo_grupo " +
                                    " LEFT JOIN " + TABLE_EMPRESAS_CLIENTES +
                                    " EC ON EC.codigo_cliente = C.codigo " +
                                    " WHERE EC.codigo_empresa = (SELECT E.codigo FROM " + TABLE_EMPRESAS + " E WHERE E.selecionada = 1)" +
                                    " AND C.tipo= '" + tipo + "' " + where + orderby, null);

                    while (cursor.moveToNext()) {

                        sonicClientesHolder cliente = new sonicClientesHolder();


                        cliente.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
                        cliente.setTipo(cursor.getString(cursor.getColumnIndex("tipo")));
                        cliente.setRazaoSocial(cursor.getString(cursor.getColumnIndex("razao")));
                        cliente.setNomeFantasia(cursor.getString(cursor.getColumnIndex("fantasia")));
                        cliente.setCpfCnpj(cursor.getString(cursor.getColumnIndex("cpf_cnpj")));
                        cliente.setInscEstadual(cursor.getString(cursor.getColumnIndex("ie")));
                        cliente.setEndereco(cursor.getString(cursor.getColumnIndex("endereco")));
                        cliente.setBairro(cursor.getString(cursor.getColumnIndex("bairro")));
                        cliente.setMunicipio(cursor.getString(cursor.getColumnIndex("municipio")));
                        cliente.setUf(cursor.getString(cursor.getColumnIndex("uf")));
                        cliente.setCep(cursor.getString(cursor.getColumnIndex("cep")));
                        cliente.setFone(cursor.getString(cursor.getColumnIndex("fone")));
                        cliente.setContato(cursor.getString(cursor.getColumnIndex("contato")));
                        cliente.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                        cliente.setObservacao(cursor.getString(cursor.getColumnIndex("obs")));
                        cliente.setDataCadastro(cursor.getString(cursor.getColumnIndex("cadastro")));
                        cliente.setGrupo(cursor.getString(cursor.getColumnIndex("grupo")));
                        cliente.setTitulos(cursor.getInt(cursor.getColumnIndex("titulos")));
                        cliente.setSituacao(cursor.getInt(cursor.getColumnIndex("situacao")));
                        clientes.add(cliente);

                    }
                    cursor.close();

                } catch (SQLiteException e) {
                    DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(), e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                    e.printStackTrace();
                }
                Log.v("LOG", clientes.size() + "");
                return clientes;

            }

            public List<sonicClientesHolder> selectClienteID(int id) {

                StackTraceElement el = Thread.currentThread().getStackTrace()[2];
                List<sonicClientesHolder> clientes = new ArrayList<sonicClientesHolder>();

                try {

                    Cursor cursor = DB.getReadableDatabase().rawQuery(
                            "SELECT " +
                                    "c.codigo," +
                                    "c.tipo," +
                                    "c.razao_social," +
                                    "c.nome_fantasia," +
                                    "c.cpf_cnpj," +
                                    "c.insc_estadual," +
                                    "c.endereco," +
                                    "c.bairro," +
                                    "c.municipio," +
                                    "c.uf," +
                                    "c.cep," +
                                    "c.fone," +
                                    "c.contato," +
                                    "c.email," +
                                    "c.situacao," +
                                    "c.observacao," +
                                    "c.data_cadastro" +

                                    " FROM " + TABLE_CLIENTES +

                                    " c WHERE c.codigo = "+id, null);

                    while (cursor.moveToNext()) {

                        sonicClientesHolder cliente = new sonicClientesHolder();


                        cliente.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
                        cliente.setTipo(cursor.getString(cursor.getColumnIndex("tipo")));
                        cliente.setRazaoSocial(cursor.getString(cursor.getColumnIndex("razao_social")));
                        cliente.setNomeFantasia(cursor.getString(cursor.getColumnIndex("nome_fantasia")));
                        cliente.setCpfCnpj(cursor.getString(cursor.getColumnIndex("cpf_cnpj")));
                        cliente.setInscEstadual(cursor.getString(cursor.getColumnIndex("insc_estadual")));
                        cliente.setEndereco(cursor.getString(cursor.getColumnIndex("endereco")));
                        cliente.setBairro(cursor.getString(cursor.getColumnIndex("bairro")));
                        cliente.setMunicipio(cursor.getString(cursor.getColumnIndex("municipio")));
                        cliente.setUf(cursor.getString(cursor.getColumnIndex("uf")));
                        cliente.setCep(cursor.getString(cursor.getColumnIndex("cep")));
                        cliente.setFone(cursor.getString(cursor.getColumnIndex("fone")));
                        cliente.setContato(cursor.getString(cursor.getColumnIndex("contato")));
                        cliente.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                        cliente.setSituacao(cursor.getInt(cursor.getColumnIndex("situacao")));
                        cliente.setObservacao(cursor.getString(cursor.getColumnIndex("observacao")));
                        cliente.setDataCadastro(cursor.getString(cursor.getColumnIndex("data_cadastro")));
                        clientes.add(cliente);

                    }

                    cursor.close();

                } catch (SQLiteException e) {
                    DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),
                            e.getMessage(),
                            mySystem.System.getActivityName(),
                            mySystem.System.getClassName(el),
                            mySystem.System.getMethodNames(el));
                    e.printStackTrace();
                }

                return clientes;

            }


        public List<sonicClientesHolder> selectClienteSelecionado(){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            List<sonicClientesHolder> clientes = new ArrayList<sonicClientesHolder>();

            try{

            Cursor cursor = DB.getReadableDatabase().rawQuery(
                    "SELECT " +
                            "c.codigo," +
                            "c.tipo," +
                            "c.razao_social," +
                            "c.nome_fantasia," +
                            "c.cpf_cnpj," +
                            "c.insc_estadual," +
                            "c.endereco," +
                            "c.bairro," +
                            "c.municipio," +
                            "c.uf," +
                            "c.cep," +
                            "c.fone," +
                            "c.contato," +
                            "c.email," +
                            "c.situacao," +
                            "c.observacao," +
                            "c.data_cadastro" +

                            " FROM "+TABLE_CLIENTES+

                            " c WHERE c.selecionado = 1", null);

            while(cursor.moveToNext()){

                sonicClientesHolder cliente = new sonicClientesHolder();


                cliente.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
                cliente.setTipo(cursor.getString(cursor.getColumnIndex("tipo")));
                cliente.setRazaoSocial(cursor.getString(cursor.getColumnIndex("razao_social")));
                cliente.setNomeFantasia(cursor.getString(cursor.getColumnIndex("nome_fantasia")));
                cliente.setCpfCnpj(cursor.getString(cursor.getColumnIndex("cpf_cnpj")));
                cliente.setInscEstadual(cursor.getString(cursor.getColumnIndex("insc_estadual")));
                cliente.setEndereco(cursor.getString(cursor.getColumnIndex("endereco")));
                cliente.setBairro(cursor.getString(cursor.getColumnIndex("bairro")));
                cliente.setMunicipio(cursor.getString(cursor.getColumnIndex("municipio")));
                cliente.setUf(cursor.getString(cursor.getColumnIndex("uf")));
                cliente.setCep(cursor.getString(cursor.getColumnIndex("cep")));
                cliente.setFone(cursor.getString(cursor.getColumnIndex("fone")));
                cliente.setContato(cursor.getString(cursor.getColumnIndex("contato")));
                cliente.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                cliente.setSituacao(cursor.getInt(cursor.getColumnIndex("situacao")));
                cliente.setObservacao(cursor.getString(cursor.getColumnIndex("observacao")));
                cliente.setDataCadastro(cursor.getString(cursor.getColumnIndex("data_cadastro")));
                clientes.add(cliente);

            }

                cursor.close();

            }catch (SQLiteException e){
                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),
                        e.getMessage(),
                        mySystem.System.getActivityName(),
                        mySystem.System.getClassName(el),
                        mySystem.System.getMethodNames(el));
                e.printStackTrace();
            }

            return clientes;
        }

    }

    class Vendas{

        public long count() {

            long count = 0;
            SQLiteDatabase db = DB.getReadableDatabase();
            try {
                count = DatabaseUtils.queryNumEntries(db, TABLE_VENDAS);
            } catch (SQLiteException e) {
                e.printStackTrace();
            }

            return count;
        }

        public boolean saveVendas(List<String> lista){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            Boolean result = false;
            ContentValues cv = new ContentValues();
            //DB.getWritableDatabase().beginTransaction();

            try{
                for(int i = 0; i < lista.size(); i++){

                    cv.put("codigo", lista.get(0));
                    cv.put("codigo_empresa", lista.get(1));
                    cv.put("codigo_cliente", lista.get(2));
                    cv.put("codigo_tipo_cobranca", lista.get(3));
                    cv.put("codigo_prazo", lista.get(4));
                    cv.put("data", lista.get(5));
                    cv.put("valor", lista.get(6));
                }

                result = DB.getWritableDatabase().insert(TABLE_VENDAS, null, cv)>0;

            }catch (SQLiteException e){
                                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();

            }
            return result;
        }

        public boolean cleanVendas(){

            return DB.getWritableDatabase().delete(TABLE_VENDAS , null, null)>0;
        }


    }

    class VendasItens{

        public long count() {

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            long count = 0;
            SQLiteDatabase db = DB.getReadableDatabase();
            try {
                count = DatabaseUtils.queryNumEntries(db, TABLE_VENDAS_ITENS);
            } catch (SQLiteException e) {
                                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();
            }

            return count;
        }

        public boolean saveVendasItens(List<String> lista){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            Boolean result = false;
            ContentValues cv = new ContentValues();

            try{
                for(int i = 0; i < lista.size(); i++){

                    cv.put("codigo_venda", lista.get(0));
                    cv.put("codigo_item", lista.get(1));
                    cv.put("codigo_produto", lista.get(2));
                    cv.put("codigo_unidade", lista.get(3));
                    cv.put("quantidade", lista.get(4));
                    cv.put("preco", lista.get(5));
                    cv.put("valor", lista.get(6));

                }

                result = DB.getWritableDatabase().insert(TABLE_VENDAS_ITENS, null, cv)>0;

            }catch (SQLiteException e){
                                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();

            }

            return result;
        }

        public boolean cleanVendasItens(){

            return DB.getWritableDatabase().delete(TABLE_VENDAS_ITENS , null, null)>0;
        }


    }

    class GrupoCliente{

        public long count(){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            long count=0;
            SQLiteDatabase db = DB.getReadableDatabase();
            try{
                count  = DatabaseUtils.queryNumEntries(db, TABLE_GRUPO_CLIENTES);
            }catch (SQLiteException e){
                                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();
            }

            return count;
        }

        public boolean saveGrupoCliente(List<String> lista){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            Boolean result = false;
            ContentValues cv = new ContentValues();

            try{
                for(int i = 0; i < lista.size(); i++){

                    cv.put("codigo", lista.get(0));
                    cv.put("nome", lista.get(1));

                }

                result = DB.getWritableDatabase().insert(TABLE_GRUPO_CLIENTES, null, cv)>0;

            }catch (SQLiteException e){
                                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();

            }
            return result;
        }

        public List<sonicGrupoClientesHolder> selectGrupoClientes(){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            List<sonicGrupoClientesHolder> grupos = new ArrayList<sonicGrupoClientesHolder>();

            Cursor cursor = DB.getReadableDatabase().rawQuery(
                    "SELECT * FROM " + TABLE_GRUPO_CLIENTES , null);

            while(cursor.moveToNext()){

                sonicGrupoClientesHolder grupo = new sonicGrupoClientesHolder();

                grupo.setId(cursor.getInt(cursor.getColumnIndex("_id")));
                grupo.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo_grupo")));
                grupo.setNome(cursor.getString(cursor.getColumnIndex("nome")));


                grupos.add(grupo);

            }
            cursor.close();
            return grupos;
        }

        public boolean cleanGrupoCliente(){

            return DB.getWritableDatabase().delete(TABLE_GRUPO_CLIENTES , null, null)>0;
        }

    }

    class RankingCliente{

        public long count(){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            long count=0;
            SQLiteDatabase db = DB.getReadableDatabase();
            try{
                count  = DatabaseUtils.queryNumEntries(db, TABLE_RANKING_CLIENTES);
            }catch (SQLiteException e){
                                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();
            }

            return count;
        }

        public boolean saveRankingCliente(List<String> lista){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            Boolean result = false;
            ContentValues cv = new ContentValues();

            try{
                for(int i = 0; i < lista.size(); i++){

                    cv.put("codigo_cliente", lista.get(0));
                    cv.put("codigo_empresa", lista.get(1));
                    cv.put("valor", lista.get(2));
                    cv.put("pedidos", lista.get(3));
                    cv.put("valor_anterior", lista.get(4));
                    cv.put("atuacao", lista.get(5));

                }

                result = DB.getWritableDatabase().insert(TABLE_RANKING_CLIENTES, null, cv)>0;

            }catch (SQLiteException e){
                                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();

            }

            return result;
        }

        public List<sonicRankingClientesHolder> selectRankingClientes(){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            List<sonicRankingClientesHolder> clientes = new ArrayList<sonicRankingClientesHolder>();

            String where = "";

            if(myCons.GRUPO_CLIENTES_RANKING != "TODOS"){
                where+= " AND gp.nome = '"+ myCons.GRUPO_CLIENTES_RANKING+"'";
            }

            Cursor cursor = DB.getReadableDatabase().rawQuery(
                    "SELECT " +
                            "c.codigo_cliente as codigo_cliente," +
                            "c.nome_fantasia as cliente," +
                            "gp.nome as grupo," +
                            "rc.valor as valor," +
                            "rc.pedidos as pedidos," +
                            "rc.atuacao as atuacao," +
                            "rc.codigo_empresa as empresa" +
                            " FROM " + TABLE_RANKING_CLIENTES +
                            " rc JOIN " + TABLE_CLIENTES +
                            " c ON c.codigo_cliente = rc.codigo_cliente " +
                            " JOIN " + TABLE_GRUPO_CLIENTES +
                            " gp ON gp.codigo_grupo = c.codigo_grupo " +
                            " WHERE rc.codigo_empresa IN (SELECT e.codigo_empresa FROM "+ TABLE_EMPRESAS +" e WHERE e.selecionado = 1) " + where + " ORDER BY rc._id ", null);

            while(cursor.moveToNext()){

                sonicRankingClientesHolder cliente = new sonicRankingClientesHolder();

                cliente.setCodigoCliente(cursor.getInt(cursor.getColumnIndex("codigo_cliente")));
                cliente.setCliente(cursor.getString(cursor.getColumnIndex("cliente")));
                cliente.setGrupoCliente(cursor.getString(cursor.getColumnIndex("grupo")));
                cliente.setValor(cursor.getString(cursor.getColumnIndex("valor")));
                cliente.setPedidos(cursor.getInt(cursor.getColumnIndex("pedidos")));
                cliente.setAtuacao(cursor.getString(cursor.getColumnIndex("atuacao")));

                clientes.add(cliente);

            }
            cursor.close();
            return clientes;
        }

        public boolean cleanRankingCliente(){

            return DB.getWritableDatabase().delete(TABLE_RANKING_CLIENTES , null, null)>0;
        }

    }

    class ClienteSemCompra{

        public long count(){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            long count=0;
            SQLiteDatabase db = DB.getReadableDatabase();
            try{
                count  = DatabaseUtils.queryNumEntries(db, TABLE_CLIENTES_SEM_COMPRA);
            }catch (SQLiteException e){
                                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();
            }

            return count;
        }

        public boolean saveClienteSemCompra(List<String> lista){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            Boolean result = false;
            ContentValues cv = new ContentValues();

            try{
                for(int i = 0; i < lista.size(); i++){

                    cv.put("codigo_cliente", lista.get(0));
                    cv.put("dias_sem_compra", lista.get(1));

                }

                result = DB.getWritableDatabase().insert(TABLE_CLIENTES_SEM_COMPRA, null, cv)>0;

            }catch (SQLiteException e){
                                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();

            }
            return result;
        }

        public List<sonicClientesSemCompraHolder> selectClientesSemCompra(){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            List<sonicClientesSemCompraHolder> clientes = new ArrayList<sonicClientesSemCompraHolder>();

            Cursor cursor = DB.getReadableDatabase().rawQuery(
                    "SELECT * FROM " + TABLE_CLIENTES_SEM_COMPRA , null);

            while(cursor.moveToNext()){

                sonicClientesSemCompraHolder cliente = new sonicClientesSemCompraHolder();

                cliente.setCodigoCliente(cursor.getInt(cursor.getColumnIndex("codigo_cliente")));
                cliente.setDias(cursor.getInt(cursor.getColumnIndex("dias_sem_compra")));


                clientes.add(cliente);

            }
            cursor.close();
            return clientes;
        }

        public boolean cleanClienteSemCompra(){

            return DB.getWritableDatabase().delete(TABLE_CLIENTES_SEM_COMPRA , null, null)>0;
        }

    }

    class Usuarios {

        public long count(){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            long count=0;
            SQLiteDatabase db = DB.getReadableDatabase();
            try{
                count  = DatabaseUtils.queryNumEntries(db, TABLE_USUARIOS);
            }catch (SQLiteException e){
                                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();
            }

            return count;
        }

        public boolean saveUsuario(List<String> lista){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            Boolean result = false;
            ContentValues cv = new ContentValues();
            //DB.getWritableDatabase().beginTransaction();

            try{
                for(int i = 0; i < lista.size(); i++){

                    cv.put("codigo", lista.get(0));
                    cv.put("nome", lista.get(1));
                    cv.put("login", lista.get(2));
                    cv.put("senha", lista.get(3));
                    cv.put("imei", lista.get(4));
                    cv.put("nivel_acesso", lista.get(5));
                    cv.put("usuario_superior", lista.get(6));
                    cv.put("ativo", 0);
                }

                result =  DB.getWritableDatabase().insert(TABLE_USUARIOS, null, cv)>0;

            }catch (SQLiteException e){
                                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();

            }

            return result;
        }

        public List<sonicUsuariosHolder> selectUsuario(int usuario_superior){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            List<sonicUsuariosHolder> usuarios = new ArrayList<sonicUsuariosHolder>();

            String order = prefs.getString("order_usuario", "");
            Boolean vswitch = prefs.getBoolean("usuario_switch", false);
            String order2 = "";
            String vsswitch = "";
            if(vswitch){
                vsswitch = " OR u.codigo = "+usuario_superior;
            }


            switch (order){
                case "0":
                    order2 = "nome";
                    break;
                case "1":
                    order2 = "u.nivel_acesso";
                    break;
                default:
                    order2 = "nome";
            }

            String MY_QUERY = "" +
                    "SELECT " +
                    "u.codigo, " +
                    "u.nome, " +
                    "u.login, " +
                    "na.nome AS nivel_acesso, " +
                    "u.ativo " +
                    "FROM "+TABLE_USUARIOS+" u " +
                    "JOIN "+TABLE_NIVEL_ACESSO+" na " +
                    "ON u.nivel_acesso = na.nivel " +
                    "WHERE u.usuario_superior IN " +
                    "(SELECT u.codigo FROM "+TABLE_USUARIOS+" u WHERE u.usuaario_superior = "+usuario_superior+") " +
                    "OR u.usuario_superior = "+usuario_superior+vsswitch+" ORDER BY "+order2;

            Cursor cursor = DB.getReadableDatabase().rawQuery(MY_QUERY, null);

            while(cursor.moveToNext()){

                sonicUsuariosHolder usuario = new sonicUsuariosHolder();

                usuario.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
                usuario.setNome(cursor.getString(cursor.getColumnIndex("nome")));
                usuario.setLogin(cursor.getString(cursor.getColumnIndex("login")));
                usuario.setNivelAcesso(cursor.getString(cursor.getColumnIndex("nivel_acesso")));
                usuario.setAtivo(cursor.getString(cursor.getColumnIndex("ativo")));
                usuarios.add(usuario);

            }
            cursor.close();
            return usuarios;
        }

        public List<sonicUsuariosHolder> selectUsuarioAtivo(){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            List<sonicUsuariosHolder> usuarios = new ArrayList<sonicUsuariosHolder>();

            String query = "SELECT " +
                    "u.codigo, " +
                    "u.nome, " +
                    "u.login, " +
                    "u.senha, " +
                    "u.imei, " +
                    "u.nivel_acesso, " +
                    "(SELECT u2.nome FROM "+TABLE_USUARIOS+" u2 WHERE u2.codigo = u.usuario_superior) AS usuario_superior, " +
                    "(SELECT na.nome FROM "+TABLE_NIVEL_ACESSO+" na WHERE na.nivel = u.nivel_acesso) AS cargo, " +
                    "(SELECT e.razao_social FROM "+TABLE_EMPRESAS+ " e WHERE e.codigo = (SELECT eu.codigo_empresa FROM "+TABLE_EMPRESAS_USUARIOS+" eu WHERE eu.codigo_usuario = u.codigo)) AS empresa, " +
                    /*"(SELECT eu.mvenda FROM "+TABLE_EMPRESAS_USUARIOS+ " eu WHERE eu.codigo_usuario = u.codigo AND eu.codigo_empresa = (SELECT e.codigo FROM "+TABLE_EMPRESAS+" e WHERE e.selecionada=1)) AS mvenda, " +
                    "(SELECT eu.mvisita FROM "+TABLE_EMPRESAS_USUARIOS+ " eu WHERE eu.codigo_usuario = u.codigo AND eu.codigo_empresa = (SELECT e.codigo FROM "+TABLE_EMPRESAS+" e WHERE e.selecionada=1)) AS mvisita " +*/
                    " 0 AS mvenda, 0 AS mvisita "+
                    " FROM "+TABLE_USUARIOS+" u WHERE u.ativo=1 ";


            try{

                Cursor cursor = DB.getReadableDatabase().rawQuery(query, null);

                if(cursor.moveToFirst()){

                    //Log.d("MOVED", DatabaseUtils.dumpCursorToString(cursor));

                    sonicUsuariosHolder usuario = new sonicUsuariosHolder();

                    usuario.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
                    usuario.setNome(cursor.getString(cursor.getColumnIndex("nome")));
                    usuario.setLogin(cursor.getString(cursor.getColumnIndex("login")));
                    usuario.setPass(cursor.getString(cursor.getColumnIndex("senha")));
                    usuario.setImei(cursor.getString(cursor.getColumnIndex("imei")));
                    usuario.setNivelAcessoId(cursor.getInt(cursor.getColumnIndex("nivel_acesso")));
                    usuario.setCargo(cursor.getString(cursor.getColumnIndex("cargo")));
                    usuario.setUsuarioSuperior(cursor.getString(cursor.getColumnIndex("usuario_superior")));
                    usuario.setEmpresa(cursor.getString(cursor.getColumnIndex("empresa")));
                    usuario.setMetaVenda(cursor.getString(cursor.getColumnIndex("mvenda")));
                    usuario.setMetaVisita(cursor.getInt(cursor.getColumnIndex("mvisita")));
                    usuarios.add(usuario);
                }


                cursor.close();

            }catch (SQLiteException e){
                                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();
            }

            return usuarios;
        }

        public List<sonicUsuariosHolder> selectUsuarioImei(String imei){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            List<sonicUsuariosHolder> usuarios = new ArrayList<sonicUsuariosHolder>();

            try{

                Cursor cursor = DB.getReadableDatabase().rawQuery(
                        "SELECT " +
                                "tu.codigo, " +
                                "tu.nome, " +
                                "(SELECT na.nome FROM "+TABLE_NIVEL_ACESSO+" na WHERE na.nivel = tu.nivel_acesso) AS cargo, " +
                                "(SELECT e.razao_social FROM "+TABLE_EMPRESAS+ " e WHERE e.codigo = (SELECT eu.codigo_empresa FROM "+TABLE_EMPRESAS_USUARIOS+" eu WHERE eu.codigo_usuario = tu.codigo)) AS empresa, " +
                                "(SELECT e.codigo FROM "+TABLE_EMPRESAS+ " e WHERE e.codigo = (SELECT eu.codigo_empresa FROM "+TABLE_EMPRESAS_USUARIOS+" eu WHERE eu.codigo_usuario = tu.codigo)) AS empresa_id " +
                                " FROM "+TABLE_USUARIOS+" tu WHERE tu.imei = '"+imei+"'", null);

                while(cursor.moveToNext()){

                    sonicUsuariosHolder usuario = new sonicUsuariosHolder();

                    usuario.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
                    usuario.setNome(cursor.getString(cursor.getColumnIndex("nome")));
                    usuario.setCargo(cursor.getString(cursor.getColumnIndex("cargo")));
                    usuario.setEmpresa(cursor.getString(cursor.getColumnIndex("empresa")));
                    usuario.setEmpresaId(cursor.getInt(cursor.getColumnIndex("empresa_id")));
                    usuarios.add(usuario);


                }
                cursor.close();

            }catch (SQLiteException e){
                                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();
            }

            return usuarios;
        }

        public Boolean usuarioAtivo() {

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            Boolean result = false;

            try {
                Cursor cursor = DB.getReadableDatabase().rawQuery("SELECT u.codigo FROM "+TABLE_USUARIOS+" u WHERE u.ativo = 1", null);
                result = cursor.moveToFirst();
            } catch (SQLiteException e) {
                                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();
            }
            return result;

        }

        public void setAtivo(int codigo){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            ContentValues args = new ContentValues();
            args.put("ativo", 1);

            try{

                DB.getWritableDatabase().update(TABLE_USUARIOS, args, " codigo = "+codigo, null);

            }catch (SQLiteException e){
                                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();

            }

        }

        public void setInativo(){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            ContentValues args = new ContentValues();
            args.put("ativo", 0);

            try{
                DB.getWritableDatabase().update(TABLE_USUARIOS, args, null, null);

            }catch (SQLiteException e){
                                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();

            }
        }

        public boolean cleanUsuarios(){
            return DB.getWritableDatabase().delete(TABLE_USUARIOS, null, null)>0;
        }

    }

    class HistoricoUsuario {

        StackTraceElement el = Thread.currentThread().getStackTrace()[2];
        public long count() {

            long count = 0;
            SQLiteDatabase db = DB.getReadableDatabase();
            try {
                count = DatabaseUtils.queryNumEntries(db, TABLE_HITORICO_USUARIO);
            } catch (SQLiteException e) {
                                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();
            }

            return count;
        }

        public boolean saveHistoricoUsuario(List<String> lista) {

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            Boolean result = false;
            ContentValues cv = new ContentValues();

            try {
                for (int i = 0; i < lista.size(); i++) {

                    cv.put("codigo_empresa", lista.get(0));
                    cv.put("codigo_vendedor", lista.get(1));
                    cv.put("mes1", lista.get(2));
                    cv.put("valor1", lista.get(3));
                    cv.put("mes2", lista.get(4));
                    cv.put("valor2", lista.get(5));
                    cv.put("mes3", lista.get(6));
                    cv.put("valor3", lista.get(7));
                    cv.put("mes4", lista.get(8));
                    cv.put("valor4", lista.get(9));
                    cv.put("mes5", lista.get(10));
                    cv.put("valor5", lista.get(11));
                    cv.put("mes6", lista.get(12));
                    cv.put("valor6", lista.get(13));
                }

                result = DB.getWritableDatabase().insert(TABLE_HITORICO_USUARIO, null, cv) > 0;

            } catch (SQLiteException e) {
                                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();

            }
            return result;
        }

        /*public List<sonicMainFragOneHolder> selectHistoricoVendedor(){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            List<sonicMainFragOneHolder> hists = new ArrayList<sonicMainFragOneHolder>();

            int nivel = prefs.getInt("VENDEDOR_NIVEL_ACESSO",0);
            String query="";

            switch (nivel){
                case 2:
                    query = " AND hv.codigo_vendedor IN (SELECT v.codigo_vendedor FROM "+TABLE_VENDEDOR+" v WHERE v.pessoa_superior = (SELECT v2.codigo_vendedor FROM "+TABLE_VENDEDOR+" v2 WHERE V2.ativo=1) )  ";
                    break;
                case 3:
                    query = " AND hv.codigo_vendedor = (SELECT v.codigo_vendedor FROM "+TABLE_VENDEDOR+" v WHERE v.ativo=1) ";
                    break;
            }

            Cursor cursor = DB.getReadableDatabase().rawQuery(
                    "SELECT " +
                            " SUM(hv.valor1) AS valor1," +
                            " hv.mes1 AS mes1," +
                            " SUM(hv.valor2) AS valor2," +
                            " hv.mes2 AS mes2," +
                            " SUM(hv.valor3) AS valor3," +
                            " hv.mes3 AS mes3," +
                            " SUM(hv.valor4) AS valor4," +
                            " hv.mes4 AS mes4," +
                            " SUM(hv.valor5) AS valor5," +
                            " hv.mes5 AS mes5," +
                            " SUM(hv.valor6) AS valor6," +
                            " hv.mes6 AS mes6" +
                            " FROM " + TABLE_HITORICO_VENDEDOR +
                            " hv WHERE  hv.codigo_empresa IN (SELECT e.codigo_empresa FROM "+TABLE_EMPRESAS+" e WHERE e.selecionada=1)" +
                            query +
                            " GROUP BY hv.mes1, hv.mes2, hv.mes3, hv.mes4, hv.mes5, hv.mes6 ", null);

            while(cursor.moveToNext()){

                sonicMainFragOneHolder historico = new sonicMainFragOneHolder();

                //historico.setVendedor(cursor.getString(cursor.getColumnIndex("Usuarios")));
                historico.setMes1(cursor.getString(cursor.getColumnIndex("mes1")));
                historico.setValor1(cursor.getString(cursor.getColumnIndex("valor1")));
                historico.setMes2(cursor.getString(cursor.getColumnIndex("mes2")));
                historico.setValor2(cursor.getString(cursor.getColumnIndex("valor2")));
                historico.setMes3(cursor.getString(cursor.getColumnIndex("mes3")));
                historico.setValor3(cursor.getString(cursor.getColumnIndex("valor3")));
                historico.setMes4(cursor.getString(cursor.getColumnIndex("mes4")));
                historico.setValor4(cursor.getString(cursor.getColumnIndex("valor4")));
                historico.setMes5(cursor.getString(cursor.getColumnIndex("mes5")));
                historico.setValor5(cursor.getString(cursor.getColumnIndex("valor5")));
                historico.setMes6(cursor.getString(cursor.getColumnIndex("mes6")));
                historico.setValor6(cursor.getString(cursor.getColumnIndex("valor6")));

                hists.add(historico);

            }
            cursor.close();
            return hists;
        }*/

        public boolean cleanHistoricoUsuario() {

            return DB.getWritableDatabase().delete(TABLE_HITORICO_USUARIO, null, null) > 0;
        }

    }

    class EmpresasUsuarios {

        public boolean saveEmpresasUsuarios(List<String> lista) {

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            Boolean result = false;
            ContentValues cv = new ContentValues();

            try {
                for (int i = 0; i < lista.size(); i++) {

                    cv.put("codigo_usuario", lista.get(0));
                    cv.put("codigo_empresa", lista.get(1));
					cv.put("mvenda", lista.get(2));
                    cv.put("mvisita", lista.get(3));

                }

                result = DB.getWritableDatabase().insert(TABLE_EMPRESAS_USUARIOS, null, cv) > 0;

            } catch (SQLiteException e) {
                DBCL.Log.saveLog(
                        e.getStackTrace()[0].getLineNumber(),
                        e.getMessage(),
                        mySystem.System.getActivityName(),
                        mySystem.System.getClassName(el),
                        mySystem.System.getMethodNames(el));
                e.printStackTrace();

            }

            return result;
        }

        public boolean cleanEmpresasUsuarios() {

            return DB.getWritableDatabase().delete(TABLE_EMPRESAS_USUARIOS, null, null) > 0;
        }

    }

    class Sincronizacao{

        public long count(){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            long count=0;
            SQLiteDatabase db = DB.getReadableDatabase();
            try{
                count  = DatabaseUtils.queryNumEntries(db, TABLE_SINCRONIZACAO);
            }catch (SQLiteException e){
                                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();
            }

            return count;
        }

        public boolean saveSincronizacao(String tabela, String tipo){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            ContentValues cv = new ContentValues();

            // DATA ATUAL E HORA
            Calendar c = Calendar.getInstance();
            SimpleDateFormat hora = new SimpleDateFormat("hh:mm");
            SimpleDateFormat data = new SimpleDateFormat("dd/mm/yyyy");
            String hora_atual = hora.format(c.getTime());
            String data_atual = data.format(c.getTime());
            try{

                cv.put("tabela", tabela);
                cv.put("tipo", tipo);
                cv.put("data_sinc", data_atual);
                cv.put("hora_sinc", hora_atual);

            }catch (SQLiteException e){
                                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();

            }

            return DB.getWritableDatabase().insert(TABLE_SINCRONIZACAO, null, cv)>0;
        }

        public boolean cleanSincronizacao(){

            return DB.getWritableDatabase().delete(TABLE_SINCRONIZACAO, null, null)>0;
        }


        public String ultimaData(){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            String data = "";

            try{

                Cursor cursor = DB.getReadableDatabase().rawQuery("SELECT data_sinc FROM "+TABLE_SINCRONIZACAO+" ORDER BY _id DESC LIMIT 1", null);

                if( cursor != null && cursor.moveToFirst() ){
                    data = cursor.getString(cursor.getColumnIndex("data_sinc"));
                    cursor.close();
                }


            }catch (SQLiteException e){
                                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();
            }

            return data;
        }

        public String ultimaHora(){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            String data = "";

            try{

                Cursor cursor = DB.getReadableDatabase().rawQuery("SELECT hora_sinc FROM "+TABLE_SINCRONIZACAO+" ORDER BY _id DESC LIMIT 1", null);

                if( cursor != null && cursor.moveToFirst() ){
                    data = cursor.getString(cursor.getColumnIndex("hora_sinc"));
                    cursor.close();
                }


            }catch (SQLiteException e){
                                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();
            }

            return data;
        }

        public String ultimaSincTable(String coluna, String tabela){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            String data = "";


            try{

                Cursor cursor = DB.getReadableDatabase().rawQuery("SELECT "+coluna+" FROM "+TABLE_SINCRONIZACAO+" WHERE tabela = '"+tabela+"' ORDER BY _id DESC LIMIT 1", null);

                if(cursor.moveToFirst()){

                    data = cursor.getString(cursor.getColumnIndex(coluna));
                }

            }catch (SQLiteException e){
                                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();
            }

            return data;

        }


    }

    class Produto{

        public long count(){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            long count=0;
            SQLiteDatabase db = DB.getReadableDatabase();
            try{
                count  = DatabaseUtils.queryNumEntries(db, TABLE_PRODUTOS);
            }catch (SQLiteException e){
                                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();
            }

            return count;
        }

        /**
         *   Conta os produtos por Empresa.
         */
        public int countPorEmpresa() {

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            int count = 0;

            SQLiteDatabase db = DB.getReadableDatabase();
            try {
                Cursor cursor = db.rawQuery("SELECT p._id FROM "+TABLE_PRODUTOS+" p WHERE p.codigo = (SELECT e.codigo FROM "+TABLE_EMPRESAS+" e WHERE e.selecionada=1)", null);
                count = cursor.getCount();
            } catch (SQLiteException e) {
                                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();
            }

            return count;
        }

        public long countRanking(){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            long count=0;
            SQLiteDatabase db = DB.getReadableDatabase();
            try{
                count  = DatabaseUtils.queryNumEntries(db, TABLE_RANKING_PRODUTOS);
            }catch (SQLiteException e){
                                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();
            }

            return count;
        }

        public boolean saveProduto(List<String> lista){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            Boolean result = false;
            ContentValues cv = new ContentValues();

            try{
                for(int i = 0; i < lista.size(); i++){

                    cv.put("codigo", lista.get(0));
                    cv.put("codigo_alternativo", lista.get(1));
                    cv.put("descricao",  lista.get(2));
                    cv.put("codigo_unidade", lista.get(3));
                    cv.put("codigo_grupo", lista.get(4));
                    cv.put("codigo_empresa", lista.get(5));
                    cv.put("selecionado", 0);

                }

                result = DB.getWritableDatabase().insert(TABLE_PRODUTOS, null, cv)>0;

            }catch (SQLiteException e){

                                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();

            }

            return result;
        }

        public boolean saveRankingProduto(List<String> lista){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            Boolean result = false;
            ContentValues cv = new ContentValues();
            //DB.getWritableDatabase().beginTransaction();

            try{
                for(int i = 0; i < lista.size(); i++){

                    cv.put("codigo_produto", lista.get(0));
                    cv.put("quantidade", lista.get(1));
                    cv.put("quantidade_anterior", lista.get(2));
                    cv.put("pedidos", lista.get(3));
                    cv.put("atuacao", lista.get(4));
                    cv.put("codigo_unidade", lista.get(5));
                    cv.put("codigo_grupo", lista.get(6));

                }

                result = DB.getWritableDatabase().insert(TABLE_RANKING_PRODUTOS, null, cv)>0;

            }catch (SQLiteException e){

                                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();

            }

            return result;
        }

        public boolean cleanProduto(){

            return DB.getWritableDatabase().delete(TABLE_PRODUTOS, null, null)>0;
        }

        public boolean cleanRankingProduto(){

            return DB.getWritableDatabase().delete(TABLE_RANKING_PRODUTOS, null, null)>0;
        }

        public void setselecionado(int codigo){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            ContentValues args = new ContentValues();
            args.put("selecionado", 1);

            try{

                DB.getWritableDatabase().update(TABLE_PRODUTOS, args, " codigo_produto = "+codigo, null);

            }catch (SQLiteException e){
                                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();

            }

        }

        public void setNaoselecionado(){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            ContentValues args = new ContentValues();
            args.put("selecionado", 0);

            try{

                DB.getWritableDatabase().update(TABLE_PRODUTOS, args, null, null);

            }catch (SQLiteException e){
                                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();

            }

        }

        public List<sonicProdutosHolder> selectProduto(){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            List<sonicProdutosHolder> produtos = new ArrayList<sonicProdutosHolder>();

            String order = prefs.getString("order_produto", "");
            Boolean estoque = prefs.getBoolean("produto_zero_switch", false);
            String orderby = "";
            String where = "";

            if(estoque){
                where+= " AND ep.estoque > 0 ";
            }
            if(myCons.GRUPO_PRODUTOS != "TODOS"){
                where+= " AND grupo_produto = '"+ myCons.GRUPO_PRODUTOS+"' ";
            }

            switch (order){
                case "0":
                    orderby = " ORDER BY p.descricao";
                    break;
                case "1":
                    orderby = " ORDER BY p.codigo_alternativo";
                    break;
                case "2":
                    orderby = " ORDER BY ep.estoque";
                    break;
                case "3":
                    orderby = " ORDER BY ep.estoque DESC";
                    break;
                default:
                    orderby = " ORDER BY p.descricao";
            }

            Cursor cursor = DB.getReadableDatabase().rawQuery(
                    "SELECT " +
                            "p.codigo_produto AS codigo," +
                            "p.descricao AS descricao, " +
                            "p.codigo_alternativo AS codigo_alternativo, " +
                            "(SELECT ep.estoque FROM estoque_produtos ep WHERE ep.codigo_produto = p.codigo_produto) AS estoque, " +
                            "(SELECT ep1.estoque_min FROM estoque_produtos ep1 WHERE ep1.codigo_produto = p.codigo_produto) AS estoque_minimo, " +
                            "(SELECT un.sigla FROM unidade_medida un WHERE un.codigo_unidade = p.codigo_unidade) AS unidade_medida, " +
                            "(SELECT gp.nome FROM grupo_produtos gp WHERE gp.codigo_grupo = p.codigo_grupo) AS grupo_produto, " +
                            "(SELECT ep2.situacao FROM estoque_produtos ep2 WHERE ep2.codigo_produto = p.codigo_produto) AS situacao " +
                            "FROM produtos p " +
                            "WHERE p.codigo_empresa = (SELECT emp.codigo_empresa FROM "+TABLE_EMPRESAS+" emp WHERE emp.selecionada=1)" + where + orderby, null);

            while(cursor.moveToNext()){

                sonicProdutosHolder produto = new sonicProdutosHolder();

                produto.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
                produto.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
                produto.setCodigoAlternativo(cursor.getString(cursor.getColumnIndex("codigo_alternativo")));
                produto.setEstoque(cursor.getInt(cursor.getColumnIndex("estoque")));
                produto.setEstoqueMinimo(cursor.getInt(cursor.getColumnIndex("estoque_minimo")));
                produto.setUnidadeMedida(cursor.getString(cursor.getColumnIndex("unidade_medida")));
                produto.setGrupo(cursor.getString(cursor.getColumnIndex("grupo_produto")));
                produto.setSituacao(cursor.getInt(cursor.getColumnIndex("situacao")));

                produtos.add(produto);

            }
            cursor.close();
            return produtos;
        }

        public List<sonicProdutosHolder> selectProdutoselecionadO(){
            List<sonicProdutosHolder> produtos = new ArrayList<sonicProdutosHolder>();

            Cursor cursor = DB.getReadableDatabase().rawQuery(
                    "SELECT " +
                            "p.codigo_produto AS codigo," +
                            "p.descricao AS descricao, " +
                            "p.codigo_alternativo AS codigo_alternativo, " +
                            "(SELECT ep.estoque FROM estoque_produtos ep WHERE ep.codigo_produto = p.codigo_produto) AS estoque, " +
                            "(SELECT ep1.estoque_min FROM estoque_produtos ep1 WHERE ep1.codigo_produto = p.codigo_produto) AS estoque_minimo, " +
                            "(SELECT un.sigla FROM unidade_medida un WHERE un.codigo_unidade = p.codigo_unidade) AS unidade_medida, " +
                            "(SELECT gp.nome FROM grupo_produtos gp WHERE gp.codigo_grupo = p.codigo_grupo) AS grupo_produto, " +
                            "(SELECT ep2.situacao FROM estoque_produtos ep2 WHERE ep2.codigo_produto = p.codigo_produto) AS situacao " +
                            "FROM produtos p " +
                            "WHERE p.selecionadO = 1", null);

            while(cursor.moveToNext()){

                sonicProdutosHolder produto = new sonicProdutosHolder();

                produto.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
                produto.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
                produto.setCodigoAlternativo(cursor.getString(cursor.getColumnIndex("codigo_alternativo")));
                produto.setEstoque(cursor.getInt(cursor.getColumnIndex("estoque")));
                produto.setEstoqueMinimo(cursor.getInt(cursor.getColumnIndex("estoque_minimo")));
                produto.setUnidadeMedida(cursor.getString(cursor.getColumnIndex("unidade_medida")));
                produto.setGrupo(cursor.getString(cursor.getColumnIndex("grupo_produto")));
                produto.setSituacao(cursor.getInt(cursor.getColumnIndex("situacao")));

                produtos.add(produto);

            }
            cursor.close();
            return produtos;
        }

        public List<sonicProdutosHolder> selectRankingProduto(){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            List<sonicProdutosHolder> produtos = new ArrayList<sonicProdutosHolder>();

            String where = "";

            if(myCons.GRUPO_PRODUTOS_RANKING != "TODOS"){
                where+= " AND gp.nome = '"+ myCons.GRUPO_PRODUTOS_RANKING+"'";
            }

            Cursor cursor = DB.getReadableDatabase().rawQuery(
                    "SELECT " +
                            "p.codigo_produto as codigo," +
                            "p.descricao as descricao," +
                            "p.codigo_alternativo as codigo_alternativo," +
                            "gp.nome as grupo_produto," +
                            "rp.quantidade as quantidade," +
                            "rp.quantidade_anterior as quantidade_anterior," +
                            "rp.pedidos as pedidos," +
                            "rp.atuacao as atuacao" +
                            " FROM " + TABLE_PRODUTOS +
                            " p JOIN " + TABLE_GRUPO_PRODUTO +
                            " gp ON gp.codigo_grupo = p.codigo_grupo" +
                            " JOIN " + TABLE_EMPRESAS +
                            " e ON e.codigo_empresa = p.codigo_empresa" +
                            " JOIN " + TABLE_RANKING_PRODUTOS +
                            " rp ON rp.codigo_produto = p.codigo_produto WHERE e.selecionada = 1 " + where +
                            " ORDER BY rp._id", null);

            while(cursor.moveToNext()){

                sonicProdutosHolder produto = new sonicProdutosHolder();

                produto.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
                produto.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
                produto.setCodigoAlternativo(cursor.getString(cursor.getColumnIndex("codigo_alternativo")));
                produto.setGrupo(cursor.getString(cursor.getColumnIndex("grupo_produto")));
                produto.setQuantidade(cursor.getString(cursor.getColumnIndex("quantidade")));
                produto.setQuantidadeAnterior(cursor.getString(cursor.getColumnIndex("quantidade_anterior")));
                produto.setPedidos(cursor.getInt(cursor.getColumnIndex("pedidos")));
                produto.setAtuacao(cursor.getString(cursor.getColumnIndex("atuacao")));

                produtos.add(produto);

            }
            cursor.close();
            return produtos;
        }

        public List<sonicProdutosHolder> selectRankingProdutoGrupo(){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            List<sonicProdutosHolder> produtos = new ArrayList<sonicProdutosHolder>();

            Cursor cursor = DB.getReadableDatabase().rawQuery(
                    "SELECT * FROM (SELECT " +
                            "gp.nome as nome, " +
                            "SUM(rp.quantidade) as quantidade, " +
                            "SUM(rp.quantidade_anterior) as quantidade_anterior, " +
                            "SUM(rp.pedidos) as pedidos, " +
                            "SUM(rp.atuacao) as atuacao, " +
                            "(SELECT p.codigo_empresa FROM " + TABLE_PRODUTOS + " p WHERE p.codigo_grupo = gp.codigo_grupo LIMIT 1) as empresa" +
                            " FROM " + TABLE_RANKING_PRODUTOS +
                            " rp JOIN " + TABLE_GRUPO_PRODUTO +
                            " gp ON gp.codigo_grupo = rp.codigo_grupo " +
                            " GROUP BY gp.nome) T WHERE T.empresa = (SELECT e.codigo_empresa FROM "+ TABLE_EMPRESAS +" e WHERE e.selecionada = 1) ORDER BY T.quantidade DESC", null);

            while(cursor.moveToNext()){

                sonicProdutosHolder produto = new sonicProdutosHolder();

                produto.setGrupo(cursor.getString(cursor.getColumnIndex("nome")));
                produto.setQuantidade(cursor.getString(cursor.getColumnIndex("quantidade")));
                produto.setQuantidadeAnterior(cursor.getString(cursor.getColumnIndex("quantidade_anterior")));
                produto.setPedidos(cursor.getInt(cursor.getColumnIndex("pedidos")));
                produto.setAtuacao(cursor.getString(cursor.getColumnIndex("atuacao")));

                produtos.add(produto);

            }
            cursor.close();
            return produtos;
        }

    }

    class Estoque{

        public boolean saveEstoque(List<String> lista){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            Boolean result = false;
            ContentValues cv = new ContentValues();

            try{
                for(int i = 0; i < lista.size(); i++){

                    cv.put("codigo_empresa", lista.get(0));
                    cv.put("codigo_produto", lista.get(1));
                    cv.put("estoque", lista.get(2));
                    cv.put("estoque_min", lista.get(3));
                    cv.put("estoque_max", lista.get(4));
                    cv.put("situacao", lista.get(5));

                }

                result = DB.getWritableDatabase().insert(TABLE_ESTOQUE_PRODUTOS, null, cv)>0;

            }catch (SQLiteException e){

                                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();

            }
            return result;
        }

        public boolean cleanEstoque(){

            return DB.getWritableDatabase().delete(TABLE_ESTOQUE_PRODUTOS, null, null)>0;
        }

    }

    class Financeiro{

        public long count(){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            long count=0;
            SQLiteDatabase db = DB.getReadableDatabase();
            try{
                count  = DatabaseUtils.queryNumEntries(db, TABLE_FINANCEIRO);
            }catch (SQLiteException e){
                e.printStackTrace();
            }

            return count;
        }

        public boolean saveFinanceiro(List<String> lista){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            Boolean result = false;
            ContentValues cv = new ContentValues();
            DB.getWritableDatabase().beginTransaction();

            try{
                for(int i = 0; i < lista.size(); i++){



                    cv.put("codigo_cliente", lista.get(0));
                    cv.put("limite_credito", lista.get(1));
                    cv.put("saldo", lista.get(2));
                    cv.put("maior_compra", lista.get(3));
                    cv.put("data_maior_compra", myUtil.Data.dataFotmatadaUS(lista.get(4)));
                    cv.put("ultima_compra", lista.get(5));
                    cv.put("data_ultima_compra", myUtil.Data.dataFotmatadaUS(lista.get(6)));

                }

                result = DB.getWritableDatabase().insert(TABLE_FINANCEIRO, null, cv)>0;

            }catch (SQLiteException e){

                                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();
            }

            return result;
        }

        public boolean cleanFinanceiro(){

            return DB.getWritableDatabase().delete(TABLE_FINANCEIRO, null, null)>0;
        }

        public List<sonicFinanceiroHolder> selectFinanceiro(){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            List<sonicFinanceiroHolder> financeiros = new ArrayList<sonicFinanceiroHolder>();


            Cursor cursor = DB.getReadableDatabase().rawQuery(
                    "SELECT " +
                            "f.codigo_cliente, " +
                            "c.razao_social, " +
                            "f.limite_credito, " +
                            "f.saldo, " +
                            "f.maior_compra, " +
                            "f.data_maior_compra, " +
                            "f.ultima_compra, " +
                            "f.data_ultima_compra " +
                            " FROM "+TABLE_FINANCEIRO+
                            " f JOIN "+TABLE_CLIENTES+" c ON c.codigo_cliente = f.codigo_cliente WHERE c.selecionado = 1", null);

            while(cursor.moveToNext()){

                sonicFinanceiroHolder financeiro = new sonicFinanceiroHolder();

                financeiro.setCodigoCliente(cursor.getInt(cursor.getColumnIndex("codigo_cliente")));
                financeiro.setRazaoSocial(cursor.getString(cursor.getColumnIndex("razao_social")));
                financeiro.setLimiteCredito(myUtil.Number.stringToMoeda(cursor.getString(cursor.getColumnIndex("limite_credito"))));
                financeiro.setSaldo(myUtil.Number.stringToMoeda(cursor.getString(cursor.getColumnIndex("saldo"))));
                financeiro.setMaiorCompra(myUtil.Number.stringToMoeda(cursor.getString(cursor.getColumnIndex("maior_compra"))));
                financeiro.setDataMaiorCompra(myUtil.Data.dataFotmatadaBR(cursor.getString(cursor.getColumnIndex("data_maior_compra"))));
                financeiro.setUlltimaCompra(myUtil.Number.stringToMoeda(cursor.getString(cursor.getColumnIndex("ultima_compra"))));
                financeiro.setDataUltimaCompra(myUtil.Data.dataFotmatadaBR(cursor.getString(cursor.getColumnIndex("data_ultima_compra"))));

                financeiros.add(financeiro);

            }
            cursor.close();
            return financeiros;
        }


    }

    class Titulos{

        public long count(){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            long count=0;
            SQLiteDatabase db = DB.getWritableDatabase();
            try{
                count  = DatabaseUtils.queryNumEntries(db, TABLE_TITULOS);
            }catch (SQLiteException e){
                                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();
            }

            return count;
        }

        public long countTitulosCliente(int cliente, int situacao){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            long count=0;
            SQLiteDatabase db = DB.getWritableDatabase();
            try{
                count  = DatabaseUtils.queryNumEntries(db, TABLE_TITULOS, "codigo_cliente="+cliente+" AND situacao="+situacao);
            }catch (SQLiteException e){
                                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();
            }

            return count;
        }

        public String sumTitulosAtraso(){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            String count = "";

            try{

                Cursor cursor = DB.getReadableDatabase().rawQuery(
                        "SELECT sum(valor) " +
                                " FROM "+TABLE_TITULOS+
                                " t JOIN "+TABLE_CLIENTES+
                                " c ON c.codigo_cliente = t.codigo_cliente "+
                                " WHERE t.situacao = 2" , null);


                if(cursor.moveToFirst()){

                    count = cursor.getString(0);
                }

            }catch (SQLiteException e){
                                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();
            }

            return count;

        }

        public boolean saveTitulos(List<String> lista){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            Boolean result = false;
            ContentValues cv = new ContentValues();
            //DB.getWritableDatabase().beginTransaction();

            try{
                for(int i = 0; i < lista.size(); i++){

                    cv.put("codigo", lista.get(0));
                    cv.put("codigo_cliente", lista.get(1));
                    cv.put("numero", lista.get(2));
                    cv.put("data_emissao", lista.get(3));
                    cv.put("data_vencimento", lista.get(4));
                    cv.put("dias_atraso", lista.get(5));
                    cv.put("valor", lista.get(6));
					cv.put("saldo", lista.get(7));
                    cv.put("situacao", lista.get(8));
                    // POSIÇÃO 9 VENDEDOR, IGNORAR

                }

                result = DB.getWritableDatabase().insert(TABLE_TITULOS, null, cv)>0;

            }catch (SQLiteException e){

                                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();

            }

            return result;
        }

        public boolean cleanTitulos(){

            return DB.getWritableDatabase().delete(TABLE_TITULOS, null, null)>0;
        }


        public List<sonicTitulosHolder> selectTitulos(int situacao){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            List<sonicTitulosHolder> titulos = new ArrayList<sonicTitulosHolder>();

            Cursor cursor = DB.getReadableDatabase().rawQuery(
                    "SELECT " +
                            "c.codigo_cliente as codigo," +
                            "c.nome_fantasia as fantasia," +
                            "(SELECT g.nome FROM "+ TABLE_GRUPO_CLIENTES + " g WHERE g.codigo_grupo = c.codigo_grupo) as grupo," +
                            "t.numero as numero," +
                            "t.data_emissao as emissao," +
                            "t.data_vencimento as vencimento," +
                            "t.dias_atraso as atraso," +
                            "t.valor as valor," +
                            "t.saldo as saldo," +
                            "t.situacao as situacao," +
                            "t.situacao_cor as situacao_cor" +
                            " FROM "+TABLE_TITULOS +
                            " t JOIN "+ TABLE_CLIENTES +
                            " c ON c.codigo_cliente = t.codigo_cliente" +
                            " WHERE t.situacao = "+situacao +" ORDER BY t.data_vencimento", null);

            while(cursor.moveToNext()){

                sonicTitulosHolder titulo = new sonicTitulosHolder();

                titulo.setCodigoCliente(cursor.getInt(cursor.getColumnIndex("codigo")));
                titulo.setNomeFantasia(cursor.getString(cursor.getColumnIndex("fantasia")));
                titulo.setGrupo(cursor.getString(cursor.getColumnIndex("grupo")));
                titulo.setNumero(cursor.getString(cursor.getColumnIndex("numero")));
                titulo.setDataEmissao(cursor.getString(cursor.getColumnIndex("emissao")));
                titulo.setDataVencimento(cursor.getString(cursor.getColumnIndex("vencimento")));
                titulo.setDiasAtraso(cursor.getInt(cursor.getColumnIndex("atraso")));
                titulo.setValor(cursor.getString(cursor.getColumnIndex("valor")));
                titulo.setSaldo(cursor.getString(cursor.getColumnIndex("saldo")));
                titulo.setSituacao(cursor.getInt(cursor.getColumnIndex("situacao")));
				titulo.setSituacaoCor(cursor.getString(cursor.getColumnIndex("situacao_cor")));

                titulos.add(titulo);

            }
            cursor.close();
            return titulos;
        }

        public List<sonicTitulosHolder> selectTitulosClienteselecionado(){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            List<sonicTitulosHolder> titulos = new ArrayList<sonicTitulosHolder>();

            Cursor cursor = DB.getReadableDatabase().rawQuery(
                    "SELECT " +
                            "c.codigo_cliente as codigo," +
                            "c.nome_fantasia as fantasia," +
                            "(SELECT g.nome FROM "+ TABLE_GRUPO_CLIENTES + " g WHERE g.codigo_grupo = c.codigo_grupo) as grupo," +
                            "t.numero as numero," +
                            "t.data_emissao as emissao," +
                            "t.data_vencimento as vencimento," +
                            "t.dias_atraso as atraso," +
                            "t.valor as valor," +
                            "t.saldo as saldo," +
                            "t.situacao as situacao," +
                            "t.situacao_cor as situacao_cor" +
                            " FROM "+TABLE_TITULOS +
                            " t JOIN "+ TABLE_CLIENTES +
                            " c ON c.codigo_cliente = t.codigo_cliente" +
                            " WHERE c.selecionado = 1 ORDER BY t.data_vencimento" , null);

            while(cursor.moveToNext()){

                sonicTitulosHolder titulo = new sonicTitulosHolder();

                titulo.setCodigoCliente(cursor.getInt(cursor.getColumnIndex("codigo")));
                titulo.setNomeFantasia(cursor.getString(cursor.getColumnIndex("fantasia")));
                titulo.setGrupo(cursor.getString(cursor.getColumnIndex("grupo")));
                titulo.setNumero(cursor.getString(cursor.getColumnIndex("numero")));
                titulo.setDataEmissao(cursor.getString(cursor.getColumnIndex("emissao")));
                titulo.setDataVencimento(cursor.getString(cursor.getColumnIndex("vencimento")));
                titulo.setDiasAtraso(cursor.getInt(cursor.getColumnIndex("atraso")));
                titulo.setValor(cursor.getString(cursor.getColumnIndex("valor")));
                titulo.setSaldo(cursor.getString(cursor.getColumnIndex("saldo")));
                titulo.setSituacao(cursor.getInt(cursor.getColumnIndex("situacao")));
                titulo.setSituacaoCor(cursor.getString(cursor.getColumnIndex("situacao_cor")));

                titulos.add(titulo);

            }
            cursor.close();
            return titulos;
        }

    }

    class Retorno{

        public long count(){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            long count=0;
            SQLiteDatabase db = DB.getReadableDatabase();
            try{
                count  = DatabaseUtils.queryNumEntries(db, TABLE_RETORNO_PEDIDO);
            }catch (SQLiteException e){
                                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();
            }

            return count;
        }

        public long countRetornoSituacao(int situacao){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            String data = "";

            switch (situacao){
                case 1:
                    switch (myCons.RETORNO_PENDENTE_DATA){
                        case "HOJE":
                            data = myUtil.Data.hoje();
                            break;
                        case "ONTEM":
                            data = myUtil.Data.ontem();
                            break;
                        case "SEMANA ATUAL":
                            data = myUtil.Data.semanaAtual();
                            break;
                        case "SEMANA ANTERIOR":
                            data = myUtil.Data.semanaAnterior();
                            break;
                        case "MÊS ATUAL":
                            data = myUtil.Data.mesAtual();
                            break;
                        case "MÊS ANTERIOR":
                            data = myUtil.Data.mesAnterior();
                            break;
                        case "ÚLTIMOS TRÊS MÊSES":
                            data = myUtil.Data.tresMeses();
                            break;
                    }
                    break;
                case 2:
                    switch (myCons.RETORNO_FATURADO_DATA){
                        case "HOJE":
                            data = myUtil.Data.hoje();
                            break;
                        case "ONTEM":
                            data = myUtil.Data.ontem();
                            break;
                        case "SEMANA ATUAL":
                            data = myUtil.Data.semanaAtual();
                            break;
                        case "SEMANA ANTERIOR":
                            data = myUtil.Data.semanaAnterior();
                            break;
                        case "MÊS ATUAL":
                            data = myUtil.Data.mesAtual();
                            break;
                        case "MÊS ANTERIOR":
                            data = myUtil.Data.mesAnterior();
                            break;
                        case "ÚLTIMOS TRÊS MÊSES":
                            data = myUtil.Data.tresMeses();
                            break;
                    }
                    break;
                case 3:
                    switch (myCons.RETORNO_EM_ROTA_DATA){
                        case "HOJE":
                            data = myUtil.Data.hoje();
                            break;
                        case "ONTEM":
                            data = myUtil.Data.ontem();
                            break;
                        case "SEMANA ATUAL":
                            data = myUtil.Data.semanaAtual();
                            break;
                        case "SEMANA ANTERIOR":
                            data = myUtil.Data.semanaAnterior();
                            break;
                        case "MÊS ATUAL":
                            data = myUtil.Data.mesAtual();
                            break;
                        case "MÊS ANTERIOR":
                            data = myUtil.Data.mesAnterior();
                            break;
                        case "ÚLTIMOS TRÊS MÊSES":
                            data = myUtil.Data.tresMeses();
                            break;
                    }
                    break;
                case 4:
                    switch (myCons.RETORNO_ENTREGUE_DATA){
                        case "HOJE":
                            data = myUtil.Data.hoje();
                            break;
                        case "ONTEM":
                            data = myUtil.Data.ontem();
                            break;
                        case "SEMANA ATUAL":
                            data = myUtil.Data.semanaAtual();
                            break;
                        case "SEMANA ANTERIOR":
                            data = myUtil.Data.semanaAnterior();
                            break;
                        case "MÊS ATUAL":
                            data = myUtil.Data.mesAtual();
                            break;
                        case "MÊS ANTERIOR":
                            data = myUtil.Data.mesAnterior();
                            break;
                        case "ÚLTIMOS TRÊS MÊSES":
                            data = myUtil.Data.tresMeses();
                            break;
                    }
                    break;
                case 5:
                    switch (myCons.RETORNO_CANCELADO_DATA){
                        case "HOJE":
                            data = myUtil.Data.hoje();
                            break;
                        case "ONTEM":
                            data = myUtil.Data.ontem();
                            break;
                        case "SEMANA ATUAL":
                            data = myUtil.Data.semanaAtual();
                            break;
                        case "SEMANA ANTERIOR":
                            data = myUtil.Data.semanaAnterior();
                            break;
                        case "MÊS ATUAL":
                            data = myUtil.Data.mesAtual();
                            break;
                        case "MÊS ANTERIOR":
                            data = myUtil.Data.mesAnterior();
                            break;
                        case "ÚLTIMOS TRÊS MÊSES":
                            data = myUtil.Data.tresMeses();
                            break;
                    }
                    break;

            }

            long count=0;

            SQLiteDatabase db = DB.getWritableDatabase();
            try{
                count  = DatabaseUtils.queryNumEntries(db, TABLE_RETORNO_PEDIDO, "situacao="+situacao+" AND data_pedido "+data);
            }catch (SQLiteException e){
                                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();
            }

            return count;
        }

        public boolean saveRetornoPedido(List<String> lista){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            Boolean result = false;
            ContentValues cv = new ContentValues();

            try{
                for(int i = 0; i < lista.size(); i++){

                    cv.put("codigo_cliente", lista.get(0));
                    cv.put("codigo_pedido", lista.get(1));
                    cv.put("numero_pedido_mobile", lista.get(2));
                    cv.put("data_pedido", lista.get(3));
                    cv.put("valor_pedido", lista.get(4));
					cv.put("valor_desconto", lista.get(5));
                    cv.put("codigo_tipo_pedido", lista.get(6));
                    cv.put("codigo_prazo", lista.get(7));
					cv.put("situacao", lista.get(8));
					cv.put("situacao_cor", lista.get(9));

                }

                result = DB.getWritableDatabase().insert(TABLE_RETORNO_PEDIDO, null, cv)>0;

            }catch (SQLiteException e){

                                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();

            }

            return result;
        }
		
		public boolean saveRetornoPedidoItens(List<String> lista){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            Boolean result = false;
            ContentValues cv = new ContentValues();

            try{
                for(int i = 0; i < lista.size(); i++){

                    cv.put("codigo_pedido", lista.get(0));
                    cv.put("codigo_produto", lista.get(1));
                    cv.put("codigo_unidade", lista.get(2));
                    cv.put("quantidade", lista.get(3));
                    cv.put("valor", lista.get(4));
					cv.put("valor_desconto", lista.get(5));

                }

                result = DB.getWritableDatabase().insert(TABLE_RETORNO_PEDIDO_ITENS, null, cv)>0;

            }catch (SQLiteException e){

                                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();

            }

            return result;
        }

        public boolean cleanRetornoPedido(){

            return DB.getWritableDatabase().delete(TABLE_RETORNO_PEDIDO, null, null)>0;
        }
		public boolean cleanRetornoPedidoItens(){

            return DB.getWritableDatabase().delete(TABLE_RETORNO_PEDIDO_ITENS, null, null)>0;
        }

        /*public List<sonicRetornoPedidosHolder> selectRetornoPedido(int situacao){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            List<sonicRetornoPedidosHolder> retornos = new ArrayList<sonicRetornoPedidosHolder>();

            String data = "";

            switch (situacao){
                case 1:
                    switch (myCons.RETORNO_PENDENTE_DATA){
                        case "HOJE":
                            data = myUtil.Data.hoje();
                            break;
                        case "ONTEM":
                            data = myUtil.Data.ontem();
                            break;
                        case "SEMANA ATUAL":
                            data = myUtil.Data.semanaAtual();
                            break;
                        case "SEMANA ANTERIOR":
                            data = myUtil.Data.semanaAnterior();
                            break;
                        case "MÊS ATUAL":
                            data = myUtil.Data.mesAtual();
                            break;
                        case "MÊS ANTERIOR":
                            data = myUtil.Data.mesAnterior();
                            break;
                        case "ÚLTIMOS TRÊS MÊSES":
                            data = myUtil.Data.tresMeses();
                            break;
                    }
                    break;
                case 2:
                    switch (myCons.RETORNO_FATURADO_DATA){
                        case "HOJE":
                            data = myUtil.Data.hoje();
                            break;
                        case "ONTEM":
                            data = myUtil.Data.ontem();
                            break;
                        case "SEMANA ATUAL":
                            data = myUtil.Data.semanaAtual();
                            break;
                        case "SEMANA ANTERIOR":
                            data = myUtil.Data.semanaAnterior();
                            break;
                        case "MÊS ATUAL":
                            data = myUtil.Data.mesAtual();
                            break;
                        case "MÊS ANTERIOR":
                            data = myUtil.Data.mesAnterior();
                            break;
                        case "ÚLTIMOS TRÊS MÊSES":
                            data = myUtil.Data.tresMeses();
                            break;
                    }
                    break;
                case 3:
                    switch (myCons.RETORNO_EM_ROTA_DATA){
                        case "HOJE":
                            data = myUtil.Data.hoje();
                            break;
                        case "ONTEM":
                            data = myUtil.Data.ontem();
                            break;
                        case "SEMANA ATUAL":
                            data = myUtil.Data.semanaAtual();
                            break;
                        case "SEMANA ANTERIOR":
                            data = myUtil.Data.semanaAnterior();
                            break;
                        case "MÊS ATUAL":
                            data = myUtil.Data.mesAtual();
                            break;
                        case "MÊS ANTERIOR":
                            data = myUtil.Data.mesAnterior();
                            break;
                        case "ÚLTIMOS TRÊS MÊSES":
                            data = myUtil.Data.tresMeses();
                            break;
                    }
                    break;
                case 4:
                    switch (myCons.RETORNO_ENTREGUE_DATA){
                        case "HOJE":
                            data = myUtil.Data.hoje();
                            break;
                        case "ONTEM":
                            data = myUtil.Data.ontem();
                            break;
                        case "SEMANA ATUAL":
                            data = myUtil.Data.semanaAtual();
                            break;
                        case "SEMANA ANTERIOR":
                            data = myUtil.Data.semanaAnterior();
                            break;
                        case "MÊS ATUAL":
                            data = myUtil.Data.mesAtual();
                            break;
                        case "MÊS ANTERIOR":
                            data = myUtil.Data.mesAnterior();
                            break;
                        case "ÚLTIMOS TRÊS MÊSES":
                            data = myUtil.Data.tresMeses();
                            break;
                    }
                    break;
                case 5:
                    switch (myCons.RETORNO_CANCELADO_DATA){
                        case "HOJE":
                            data = myUtil.Data.hoje();
                            break;
                        case "ONTEM":
                            data = myUtil.Data.ontem();
                            break;
                        case "SEMANA ATUAL":
                            data = myUtil.Data.semanaAtual();
                            break;
                        case "SEMANA ANTERIOR":
                            data = myUtil.Data.semanaAnterior();
                            break;
                        case "MÊS ATUAL":
                            data = myUtil.Data.mesAtual();
                            break;
                        case "MÊS ANTERIOR":
                            data = myUtil.Data.mesAnterior();
                            break;
                        case "ÚLTIMOS TRÊS MÊSES":
                            data = myUtil.Data.tresMeses();
                            break;
                    }
                    break;

            }

            Cursor cursor = DB.getReadableDatabase().rawQuery(
                    "SELECT " +
                            "r.codigo_cliente as codigo_cliente," +
                            "c.razao_social as nome_cliente," +
                            "r.numero_pedido_mobile as numero," +
                            "r.codigo_pedido as codigo_pedido," +
                            "r.data_pedido as data_pedido," +
                            "r.valor_pedido as valor_pedido," +
                            "tp.nome as tipo_pedido_nome," +
                            "p.nome as prazo," +
                            "r.situacao as situacao," +
                            "r.situacao_cor as situacao_cor" +
                            " FROM "+TABLE_RETORNO_PEDIDO+" r " +
                            " JOIN "+TABLE_CLIENTES+
                            " c ON c.codigo_cliente = r.codigo_cliente" +
                            " JOIN "+TABLE_TIPO_PEDIDO+
                            " tp ON tp.codigo_tipo = r.codigo_tipo_pedido" +
                            " JOIN "+TABLE_PRAZO+
                            " p ON p.codigo_prazo = r.codigo_prazo" +
                            " WHERE r.situacao = "+situacao+" AND r.data_pedido "+data+" ORDER BY r.data_pedido DESC", null);

            while(cursor.moveToNext()){

                sonicRetornoPedidosHolder retorno = new sonicRetornoPedidosHolder();

                retorno.setCodigoCliente(cursor.getInt(cursor.getColumnIndex("codigo_cliente")));
                retorno.setNomeCliente(cursor.getString(cursor.getColumnIndex("nome_cliente")));
                retorno.setNumeroPedidoMobile(cursor.getString(cursor.getColumnIndex("numero")));
                retorno.setCodigoPedido(cursor.getInt(cursor.getColumnIndex("codigo_pedido")));
                retorno.setDataPedido(cursor.getString(cursor.getColumnIndex("data_pedido")));
                retorno.setValorPedido(cursor.getString(cursor.getColumnIndex("valor_pedido")));
                retorno.setSituacaoPedido(cursor.getInt(cursor.getColumnIndex("situacao")));
                retorno.setSituacaoPedidoCor(cursor.getString(cursor.getColumnIndex("situacao_cor")));
                retorno.setTipoPedido(cursor.getString(cursor.getColumnIndex("tipo_pedido_nome")));
                retorno.setPrazo(cursor.getString(cursor.getColumnIndex("prazo")));

                retornos.add(retorno);

            }
            cursor.close();
            return retornos;
        }*/

    }


    class GrupoProduto{

        public boolean saveGrupoProduto(List<String> lista){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            Boolean result = false;
            ContentValues cv = new ContentValues();

            try{
                for(int i = 0; i < lista.size(); i++){

                    cv.put("codigo", lista.get(0));
                    cv.put("nome", lista.get(1));

                }

                result = DB.getWritableDatabase().insert(TABLE_GRUPO_PRODUTO, null, cv)>0;

            }catch (SQLiteException e){

                                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();

            }

            return result;
        }

        public boolean cleanGrupoProduto(){

            return DB.getWritableDatabase().delete(TABLE_GRUPO_PRODUTO, null, null)>0;
        }

        public List<sonicGrupoProdutosHolder> selectGrupoProduto(){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            List<sonicGrupoProdutosHolder> groups = new ArrayList<sonicGrupoProdutosHolder>();

            try{

                Cursor cursor = DB.getReadableDatabase().rawQuery(
                        "SELECT " +
                                "g._id as id," +
                                "g.codigo_grupo as codigo_grupo," +
                                "g.nome as nome" +
                                " FROM " + TABLE_GRUPO_PRODUTO +
                                " g JOIN " + TABLE_PRODUTOS +
                                " p ON p.codigo_grupo = g.codigo_grupo" +
                                " JOIN " + TABLE_EMPRESAS +
                                " e ON E.codigo_empresa = p.codigo_empresa " +
                                "   WHERE e.selecionada = 1 GROUP BY g.nome " , null);

                while(cursor.moveToNext()){

                    sonicGrupoProdutosHolder group = new sonicGrupoProdutosHolder();

                    group.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    group.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo_grupo")));
                    group.setDescricao(cursor.getString(cursor.getColumnIndex("nome")));

                    groups.add(group);

                }

                cursor.close();


            }catch (SQLiteException e){
                                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();
            }

            return groups;

        }

    }

    class TabelaPreco{

        public boolean saveTabelaPreco(List<String> lista){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            Boolean result = false;
            ContentValues cv = new ContentValues();

            try{
                for(int i = 0; i < lista.size(); i++){

                    cv.put("codigo", lista.get(0));
                    cv.put("nome", lista.get(1));

                }

                result = DB.getWritableDatabase().insert(TABLE_TABELA_PRECO, null, cv)>0;

            }catch (SQLiteException e){

                                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();

            }
            return result;
        }

        public boolean cleanTabelaPreco(){

            return DB.getWritableDatabase().delete(TABLE_TABELA_PRECO, null, null)>0;
        }

    }

    class TipoCobranca{

        public boolean saveTipoCobranca(List<String> lista){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            Boolean result = false;
            ContentValues cv = new ContentValues();

            try{
                for(int i = 0; i < lista.size(); i++){

                    cv.put("codigo", lista.get(0));
                    cv.put("nome", lista.get(1));

                }

                result = DB.getWritableDatabase().insert(TABLE_TIPO_COBRANCA, null, cv)>0;

            }catch (SQLiteException e){

                                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();

            }

            return result;
        }

        public boolean cleanTipoCobranca(){

            return DB.getWritableDatabase().delete(TABLE_TIPO_COBRANCA, null, null)>0;
        }

    }

    class CondicoesPagamento{

        public boolean saveCondicoesPagamento(List<String> lista){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            Boolean result = false;
            ContentValues cv = new ContentValues();

            try{
                for(int i = 0; i < lista.size(); i++){

                    cv.put("codigo", lista.get(0));
                    cv.put("nome", lista.get(1));

                }

                result = DB.getWritableDatabase().insert(TABLE_CONDICOES_PAGAMENTO, null, cv)>0;

            }catch (SQLiteException e){

                                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();

            }
            return result;
        }

        public boolean cleanCondicoesPagamento(){

            return DB.getWritableDatabase().delete(TABLE_CONDICOES_PAGAMENTO, null, null)>0;
        }

    }


    class Transportadora{

        public boolean saveTransportadora(List<String> lista){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            Boolean result = false;
            ContentValues cv = new ContentValues();


            try{
                for(int i = 0; i < lista.size(); i++){

                    cv.put("codigo", lista.get(0));
                    cv.put("nome", lista.get(1));

                }

                result = DB.getWritableDatabase().insert(TABLE_TRANSPORTADORA, null, cv)>0;

            }catch (SQLiteException e){

                                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();

            }

            return result;
        }

        public boolean cleanTransportadora(){

            return DB.getWritableDatabase().delete(TABLE_TRANSPORTADORA, null, null)>0;
        }

    }

    class UnidadeMedida{

        public boolean saveUnidadeMedida(List<String> lista){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            Boolean result = false;
            ContentValues cv = new ContentValues();

            try{
                for(int i = 0; i < lista.size(); i++){

                    cv.put("codigo", lista.get(0));
                    cv.put("nome", lista.get(1));
                    cv.put("sigla", lista.get(2));

                }

                result = DB.getWritableDatabase().insert(TABLE_UNIDADE_MEDIDA, null, cv)>0;

            }catch (SQLiteException e){

                                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();

            }

            return result;
        }

        public boolean cleanUnidadeMedida(){

            return DB.getWritableDatabase().delete(TABLE_UNIDADE_MEDIDA, null, null)>0;
        }

    }

    class TipoPedido{

        public boolean saveTipoPedido(List<String> lista){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            Boolean result = false;
            ContentValues cv = new ContentValues();

            try{
                for(int i = 0; i < lista.size(); i++){

                    cv.put("codigo", lista.get(0));
                    cv.put("nome", lista.get(1));

                }

                result = DB.getWritableDatabase().insert(TABLE_TIPO_PEDIDO, null, cv)>0;

            }catch (SQLiteException e){

                                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();

            }
            return result;
        }

        public boolean cleanTipoPedido(){

            return DB.getWritableDatabase().delete(TABLE_TIPO_PEDIDO, null, null)>0;
        }

    }

    class TipoDocumento{

        public boolean saveTipoDocumento(List<String> lista){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            Boolean result = false;
            ContentValues cv = new ContentValues();


            try{
                for(int i = 0; i < lista.size(); i++){

                    cv.put("codigo", lista.get(0));
                    cv.put("nome", lista.get(1));

                }

                result = DB.getWritableDatabase().insert(TABLE_TIPO_DOCUMENTO, null, cv)>0;

            }catch (SQLiteException e){

                                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();

            }

            return result;
        }

        public boolean dropTipoDocumento(){

            return DB.getWritableDatabase().delete(TABLE_TIPO_DOCUMENTO, null, null)>0;
        }

    }

    class Desconto{

        public boolean saveDesconto(List<String> lista){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            Boolean result = false;
            ContentValues cv = new ContentValues();

            try{
                for(int i = 0; i < lista.size(); i++){

                    cv.put("codigo_vendedor", lista.get(0));
                    cv.put("perc_desconto", lista.get(1));

                }

                result = DB.getWritableDatabase().insert(TABLE_DESCONTO, null, cv)>0;

            }catch (SQLiteException e){

                                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();

            }

            return result;
        }

        public boolean dropDesconto(){

            return DB.getWritableDatabase().delete(TABLE_DESCONTO, null, null)>0;
        }

    }

    class Prazo{

        public boolean savePrazo(List<String> lista){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            Boolean result = false;
            ContentValues cv = new ContentValues();

            try{
                for(int i = 0; i < lista.size(); i++){

                    cv.put("codigo", lista.get(0));
                    cv.put("nome", lista.get(1));
                    cv.put("prazo_venda", lista.get(2));

                }

                result = DB.getWritableDatabase().insert(TABLE_PRAZO, null, cv)>0;

            }catch (SQLiteException e){

                                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();

            }

            return result;
        }

        public boolean cleanPrazo(){

            return DB.getWritableDatabase().delete(TABLE_PRAZO, null, null)>0;
        }

    }

    class TabelaPrecoCliente{

        public boolean saveTabelaPrecoCliente(List<String> lista){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            Boolean result = false;
            ContentValues cv = new ContentValues();
            
            try{
                for(int i = 0; i < lista.size(); i++){

                    cv.put("codigo_tabela", lista.get(0));
                    cv.put("codigo_cliente", lista.get(1));

                }

                result = DB.getWritableDatabase().insert(TABLE_TABELA_PRECO_CLIENTE, null, cv)>0;

            }catch (SQLiteException e){

                                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();

            }
            return result;
        }

        public boolean dropTabelaPrecoCliente(){

            return DB.getWritableDatabase().delete(TABLE_TABELA_PRECO_CLIENTE, null, null)>0;
        }

    }

    class Mensagem{

        public boolean saveMensagem(List<String> lista){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            int size = lista.size();
            ContentValues cv = new ContentValues();

            try{
                for(int i = 0; i < size; i++){

                    cv.put("codigo", lista.get(0));
                    cv.put("dialogRedefinir", lista.get(1));

                }

            }catch (SQLiteException e){
                                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();
            }

            return DB.getWritableDatabase().insert(TABLE_TABELA_PRECO_CLIENTE, null, cv)>0;
        }

        public boolean dropMensagem(){

            return DB.getWritableDatabase().delete(TABLE_TABELA_PRECO_CLIENTE, null, null)>0;
        }

    }

    class Localizacao{

        public long count(){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            long count=0;
            SQLiteDatabase db = DB.getReadableDatabase();
            try{
                count  = DatabaseUtils.queryNumEntries(db, TABLE_LOCALIZACAO);
            }catch (SQLiteException e){
                                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();
            }

            return count;
        }

        public boolean saveLocalizacao(List<String> lista){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            int size = lista.size();
            ContentValues cv = new ContentValues();

            try{
                for(int i = 0; i < size; i++){

                    cv.put("codigo_vendedor", lista.get(0));
                    cv.put("data", myUtil.Data.dataFotmatadaUS(lista.get(1)));
                    cv.put("hora", myUtil.Data.horaFotmatadaBR(lista.get(2)));
                    cv.put("latitude", lista.get(3));
                    cv.put("longitude", lista.get(4));

                }

            }catch (SQLiteException e){
                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();
            }

            return DB.getWritableDatabase().insert(TABLE_LOCALIZACAO, null, cv)>0;
        }

        public boolean cleanLocalizacao(){

            return DB.getWritableDatabase().delete(TABLE_LOCALIZACAO, null, null)>0;
        }

        public List<sonicLocalizacaoHolder> selectLocalizacao(){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            List<sonicLocalizacaoHolder> locs = new ArrayList<sonicLocalizacaoHolder>();

            Cursor cursor = DB.getReadableDatabase().rawQuery(
                    "SELECT " +
                            "l.codigo_vendedor," +
                            "u.login," +
                            "u.nome," +
                            "na.nome_acesso," +
                            "l.data," +
                            "l.hora," +
                            "l.latitude," +
                            "l.longitude" +
                            " FROM "+TABLE_LOCALIZACAO+" l "+
                            " JOIN "+TABLE_USUARIOS+" u " +
                            " ON l.codigo_vendedor = u.codigo" +
                            " JOIN "+TABLE_NIVEL_ACESSO+" na " +
                            " ON na.nivel = u.nivel_acesso ", null);

            while(cursor.moveToNext()){

                sonicLocalizacaoHolder loc = new sonicLocalizacaoHolder();

                loc.setCodigoVendedor(cursor.getInt(cursor.getColumnIndex("codigo_vendedor")));
                loc.setLogin(cursor.getString(cursor.getColumnIndex("login")));
                loc.setNome(cursor.getString(cursor.getColumnIndex("nome")));
                loc.setNivelAcesso(cursor.getString(cursor.getColumnIndex("nome_acesso")));
                loc.setData(myUtil.Data.dataFotmatadaBR(cursor.getString(cursor.getColumnIndex("data"))));
                loc.setHora(cursor.getString(cursor.getColumnIndex("hora")));
                loc.setLatitude(cursor.getString(cursor.getColumnIndex("latitude")));
                loc.setLongitude(cursor.getString(cursor.getColumnIndex("longitude")));

                locs.add(loc);

            }
            cursor.close();
            return locs;
        }

    }

    class LogErro{

        public long count(){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            long count=0;
            SQLiteDatabase db = DB.getReadableDatabase();
            try{
                count  = DatabaseUtils.queryNumEntries(db, TABLE_LOG_ERRO);
            }catch (SQLiteException e){
                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();
            }

            return count;
        }

        public String selectLastError() {

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            String log = "";

            try {
                Cursor cursor = DB.getReadableDatabase().rawQuery(
                        "SELECT log FROM " + TABLE_LOG_ERRO + " ORDER BY _id DESC LIMIT 1 ", null);
                if (cursor.moveToFirst()) {
                    log = cursor.getString(cursor.getColumnIndex("log"));
                }
            }catch (SQLiteException e){
                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();
            }
            return log;
        }

        public boolean cleanLogErro(){

            return DB.getWritableDatabase().delete(TABLE_LOG_ERRO, null, null)>0;
        }

    }


}
