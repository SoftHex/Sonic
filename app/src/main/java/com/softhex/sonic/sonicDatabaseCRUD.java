package com.softhex.sonic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Administrador on 10/07/2017.
 */

public class sonicDatabaseCRUD {

    public static final int DB_MODE_SAVE = 1;
    public static final int DB_MODE_UPDATE = 2;
    public static final int DB_MODE_SAVE_UNIQUE = 3;
    private final String DB_NAME = sonicConstants.DATABASE;
    private final String TABLE_SITE = sonicConstants.TB_SITE;
    private final String TABLE_FTP = sonicConstants.TB_FTP;
    private final String TABLE_EMPRESAS = sonicConstants.TB_EMPRESAS;
    private final String TABLE_GRUPO_EMPRESAS = sonicConstants.TB_GRUPO_EMPRESAS;
    private final String TABLE_NIVEL_ACESSO = sonicConstants.TB_NIVEL_ACESSO;
    private final String TABLE_USUARIOS = sonicConstants.TB_USUARIOS;
    private final String TABLE_EMPRESAS_USUARIOS = sonicConstants.TB_EMPRESAS_USUARIOS;
    private final String TABLE_CLIENTES = sonicConstants.TB_CLIENTES;
    private final String TABLE_EMPRESAS_CLIENTES = sonicConstants.TB_EMPRESAS_CLIENTES;
    private final String TABLE_GRUPO_CLIENTES = sonicConstants.TB_GRUPO_CLIENTES;
    private final String TABLE_RANKING_CLIENTES = sonicConstants.TB_RANKING_CLIENTES;
    private final String TABLE_CLIENTES_SEM_COMPRA = sonicConstants.TB_CLIENTES_SEM_COMPRA;
    private final String TABLE_ACESSO_CLIENTE = sonicConstants.TB_ACESSO_CLIENTE;
    private final String TABLE_PRODUTOS = sonicConstants.TB_PRODUTOS;
    private final String TABLE_GRUPO_PRODUTOS = sonicConstants.TB_GRUPO_PRODUTOS;
    private final String TABLE_BLOQUEIO_PRODUTOS = sonicConstants.TB_BLOQUEIO_PRODUTOS;
    private final String TABLE_ACESSO_PRODUTO = sonicConstants.TB_ACESSO_PRODUTO;
    private final String TABLE_ROTA = sonicConstants.TB_ROTA;
    private final String TABLE_ESTOQUE_PRODUTOS = sonicConstants.TB_ESTOQUE_PRODUTOS;
    private final String TABLE_FINANCEIRO = sonicConstants.TB_FINANCEIRO;
    private final String TABLE_TITULOS = sonicConstants.TB_TITULOS;
    private final String TABLE_RETORNO_PEDIDO = sonicConstants.TB_RETORNO_PEDIDO;
    private final String TABLE_RETORNO_PEDIDO_ITENS = sonicConstants.TB_RETORNO_PEDIDO_ITENS;
    private final String TABLE_TIPO_COBRANCA = sonicConstants.TB_TIPO_COBRANCA;
    private final String TABLE_AGENTE_COBRADOR = sonicConstants.TB_AGENTE_COBRADOR;
    private final String TABLE_CONDICAO_PAGAMENTO = sonicConstants.TB_CONDICAO_PAGAMENTO;
    private final String TABLE_PRECO_PRODUTO = sonicConstants.TB_TABELA_PRECO_PRODUTO;
    private final String TABLE_TABELA_PRECO = sonicConstants.TB_TABELA_PRECO;
    private final String TABLE_ULTIMAS_COMPRAS = sonicConstants.TB_ULTIMAS_COMPRAS;
    private final String TABLE_ULTIMAS_COMPRAS_ITENS = sonicConstants.TB_ULTIMAS_COMPRAS_ITENS;
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
    private final String TABLE_SINCRONIZACAO = sonicConstants.TB_SINCRONIZACAO;
    private final String TABLE_LOCALIZACAO = sonicConstants.TB_LOCALIZACAO;
    private final String TABLE_LOG_ERRO = sonicConstants.TB_LOG_ERRO;
    private sonicDatabase DB;
    private sonicDatabaseLogCRUD DBCL;
    private sonicUtils myUtil;
    private sonicConstants myCons;
    private Context myCtx;
    private sonicSystem mySystem;
    private sonicPreferences mPrefs;
    private Calendar mCalendar;
    private SimpleDateFormat data = new SimpleDateFormat("yyyyMMdd");
    private SimpleDateFormat hora = new SimpleDateFormat("HHmmss");

    public sonicDatabaseCRUD(Context context){

        this.myCtx = context;
        this.mPrefs = new sonicPreferences(context);
        this.DB = new sonicDatabase(myCtx);
        this.DBCL = new sonicDatabaseLogCRUD(myCtx);
        this.myCons = new sonicConstants();
        this.mySystem = new sonicSystem(myCtx);

    }

    Database Database = new Database();
    Site Site = new Site();
    Ftp Ftp = new Ftp();
    Empresa Empresa = new Empresa();
    GrupoEmpresas GrupoEmpresas = new GrupoEmpresas();
    Usuario Usuario = new Usuario();
    EmpresaUsuario EmpresaUsuario = new EmpresaUsuario();
    sonicDatabaseCRUD.NivelAcesso NivelAcesso = new NivelAcesso();
    Cliente Cliente = new Cliente();
    EmpresaCliente EmpresaCliente = new EmpresaCliente();
    Venda Venda = new Venda();
    VendaItem VendaItem = new VendaItem();
    GrupoCliente GrupoCliente = new GrupoCliente();
    RankingCliente RankingCliente = new RankingCliente();
    ClientesSemCompra ClienteSemCompra = new ClientesSemCompra();
    Produto Produto = new Produto();
    GrupoProduto GrupoProduto = new GrupoProduto();
    Rota Rota = new Rota();
    Estoque Estoque = new Estoque();
    Titulo Titulo = new Titulo();
    Financeiro Financeiro = new Financeiro();
    TabelaPreco TabelaPreco = new TabelaPreco();
    UnidadeMedida UnidadeMedida = new UnidadeMedida();
    Sincronizacao Sincronizacao = new Sincronizacao();
    Localizacao Localizacao = new Localizacao();
    TipoPedido TipoPedido = new TipoPedido();
    TipoCobranca TipoCobranca = new TipoCobranca();
    Prazo Prazo = new Prazo();

    class Database{

        public boolean checkMinimumData(){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            Boolean result = true;

                try{
                    Cursor cursor;
                    cursor = DB.getReadableDatabase().rawQuery("SELECT * FROM "+TABLE_SITE , null);
                    result = result && cursor.moveToFirst();
                    cursor = DB.getReadableDatabase().rawQuery("SELECT * FROM "+TABLE_FTP , null);
                    result = result && cursor.moveToFirst();
                    cursor = DB.getReadableDatabase().rawQuery("SELECT * FROM "+ TABLE_EMPRESAS, null);
                    result = result && cursor.moveToFirst();
                    cursor = DB.getReadableDatabase().rawQuery("SELECT * FROM "+TABLE_NIVEL_ACESSO , null);
                    result = result && cursor.moveToFirst();
                    cursor = DB.getReadableDatabase().rawQuery("SELECT * FROM "+ TABLE_USUARIOS, null);
                    result = result && cursor.moveToFirst();
                    cursor = DB.getReadableDatabase().rawQuery("SELECT * FROM "+ TABLE_EMPRESAS_USUARIOS, null);
                    result = result &&  cursor.moveToFirst();
                    cursor = DB.getReadableDatabase().rawQuery("SELECT * FROM "+TABLE_GRUPO_EMPRESAS , null);
                    result = result &&  cursor.moveToFirst();
                }catch (SQLiteException e){
                    mPrefs.Geral.setError(e.getMessage());
                    e.printStackTrace();
                    DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                }

            return result;
        }

        public void truncateAllTables(){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];

            try{

                Cursor cursor = DB.getReadableDatabase().rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name NOT IN (" +
                        "'sqlite_master', 'sqlite_sequence', '" +
                        TABLE_ACESSO_CLIENTE +"','"+
                        TABLE_ACESSO_PRODUTO +"')", null);
                List<String> tables = new ArrayList<>();

                while (cursor.moveToNext()){
                    tables.add(cursor.getString(0));
                }

                for(String table: tables){
                    DB.getWritableDatabase().delete(table, null, null);
                }

            }catch (SQLiteException e){
                mPrefs.Geral.setError(e.getMessage());
                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();

            }

        }

        public void truncateAllTablesNonNecessary(){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];

            try{

                Cursor cursor = DB.getReadableDatabase().rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name NOT IN (" +
                        "'sqlite_master', " +
                        "'sqlite_sequence','"+
                        TABLE_SITE+"','"+
                        TABLE_FTP+"','"+
                        TABLE_EMPRESAS +"','"+
                        TABLE_NIVEL_ACESSO+"','"+
                        TABLE_USUARIOS +"','"+
                        TABLE_EMPRESAS_USUARIOS +"','"+
                        TABLE_GRUPO_EMPRESAS+"','"+
                        TABLE_GRUPO_EMPRESAS+"')", null);
                List<String> tables = new ArrayList<>();

                while (cursor.moveToNext()){
                    tables.add(cursor.getString(0));
                }

                for(String table: tables){
                    DB.getWritableDatabase().delete(table, null, null);
                }

            }catch (SQLiteException e){
                mPrefs.Geral.setError(e.getMessage());
                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();

            }

        }


        public Boolean saveData(String tabela, List<String> values, ModeSave m){
            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            ContentValues cv = new ContentValues();
            Cursor cursor = DB.getWritableDatabase().query(tabela, null, null, null, null, null, null);
            boolean result = false;

            switch (m){
                case DELETE_AND_INSERT:
                    try {

                        for(int i = 0; i < cursor.getColumnNames().length-1; i++) {
                            cv.put(cursor.getColumnNames()[i+1], values.get(i));
                        }

                        result = DB.getWritableDatabase().insert(tabela, null, cv)>0;

                    }catch (SQLiteException e){
                        e.printStackTrace();
                        mPrefs.Geral.setError("Detalhe do erro: \n" + Log.getStackTraceString(e));
                        return false;
                    }
                    break;
                case UPDATE_OR_INSERT:
                    Cursor c = DB.getReadableDatabase().rawQuery("SELECT * FROM "+tabela+" WHERE "+cursor.getColumnNames()[1]+"=?", new String[]{values.get(0)});
                    // SE NAO EXISTIR VALOR, INSERE
                    if(c==null || c.getCount()==0){

                        try {
                            for(int i = 0; i < cursor.getColumnNames().length-1; i++) {
                                cv.put(cursor.getColumnNames()[i+1], values.get(i));
                            }

                            result = DB.getWritableDatabase().insert(tabela, null, cv)>0;

                        }catch (SQLiteException e){
                            mPrefs.Geral.setError("Detalhe do erro: \n" + e.getMessage());
                            e.printStackTrace();
                            return false;
                        }
                    // SE EXISTIR, ATUALIZA
                    }else{

                        try {
                            for(int i = 0; i < cursor.getColumnNames().length-1; i++) {
                                cv.put(cursor.getColumnNames()[i+1], values.get(i));
                            }

                            result = DB.getWritableDatabase().update(tabela, cv, values.get(0)+"=?", null)>0;

                        }catch (SQLiteException e){
                            mPrefs.Geral.setError("Detalhe do erro: \n" + e.getMessage());
                            e.printStackTrace();
                            return false;
                        }

                    }
                    break;
                }
			
            return result;
        }

        public boolean cleanData(String tabela){

            return DB.getWritableDatabase().delete(tabela, null, null)>0;
        }

    }

    class Site{

    }

    class Ftp{

        public List<sonicFtpHolder> selectFtpAtivo(){
            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            List<sonicFtpHolder> ftps = new ArrayList<>();
            Cursor cursor = DB.getWritableDatabase().rawQuery("SELECT * FROM " + TABLE_FTP + " WHERE ativo=? ", new String[]{"1"});

            try{

                if(cursor!=null){

                    while (cursor.moveToNext()){
                        sonicFtpHolder ftp = new sonicFtpHolder();
                        ftp.setId(cursor.getInt(cursor.getColumnIndex("_id")));
                        ftp.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
                        ftp.setEndereco(cursor.getString(cursor.getColumnIndex("endereco")));
                        ftp.setUsuario(cursor.getString(cursor.getColumnIndex("usuario")));
                        ftp.setSenha(cursor.getString(cursor.getColumnIndex("senha")));
                        ftp.setAtivo(cursor.getInt(cursor.getColumnIndex("ativo")));
                        ftps.add(ftp);
                    }

                    cursor.close();

                }

            }catch (SQLiteException e){
                mPrefs.Geral.setError(e.getMessage());
                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();
            }finally{
				cursor.close();
			}

            return ftps;

        }

    }

    class Empresa{

        public String empresaPadrao() {

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            String empresa = null;

            try{

                Cursor cursor = DB.getReadableDatabase().rawQuery(
                        "SELECT nome_fantasia FROM "+ TABLE_EMPRESAS +" LIMIT 1 " , null);

                if(cursor.moveToFirst()) {
                    empresa = cursor.getString(cursor.getColumnIndex("nome_fantasia"));
                }

            }catch (SQLiteException e){
                mPrefs.Geral.setError(e.getMessage());
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
                        "SELECT e.codigo FROM "+ TABLE_EMPRESAS +" e LIMIT 1 " , null);

                if(cursor.moveToFirst()) {
                    empresa = cursor.getInt(cursor.getColumnIndex("codigo"));
                }

            }catch (SQLiteException e){
                mPrefs.Geral.setError(e.getMessage());
                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();
            }

            return empresa;
        }

        public boolean empresaExiste(){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            boolean result = false;

            try{
                Cursor cursor = DB.getReadableDatabase().rawQuery("SELECT * FROM "+ TABLE_EMPRESAS, null);
                if(cursor!=null && cursor.getCount()>0){
                    result = true;
                }

            }catch (SQLiteException e){
                mPrefs.Geral.setError(e.getMessage());
                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();
            }

            return  result;
        }

        public boolean EmpresaSelecionada(){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            boolean result = false;

            try{
                Cursor cursor = DB.getReadableDatabase().rawQuery("SELECT * FROM "+ TABLE_EMPRESAS +" e WHERE e.selecionada=1  ", null);
                if(cursor!=null && cursor.getCount()>0){
                    result = true;
                }

            }catch (SQLiteException e){
                mPrefs.Geral.setError(e.getMessage());
                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();
            }

            return  result;
        }

        public List<sonicEmpresasHolder> selectEmpresa(){
            List<sonicEmpresasHolder> empresas = new ArrayList<sonicEmpresasHolder>();

            Cursor cursor = DB.getReadableDatabase().rawQuery(
                    "SELECT * FROM "+ TABLE_EMPRESAS, null);

            if(cursor!=null){
                while(cursor.moveToNext()){

                    sonicEmpresasHolder empresa = new sonicEmpresasHolder();

                    empresa.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
                    empresa.setNomeFantasia(cursor.getString(cursor.getColumnIndex("nome_fantasia")));
                    empresas.add(empresa);

                }
            }

            return empresas;
        }

        public List<sonicEmpresasHolder> empresaUsuario(){
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

            if(cursor!=null){
                while(cursor.moveToNext()){

                    sonicEmpresasHolder empresa = new sonicEmpresasHolder();

                    empresa.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
                    empresa.setNomeFantasia(cursor.getString(cursor.getColumnIndex("nome_fantasia")));
                    empresa.setRazaoSocial(cursor.getString(cursor.getColumnIndex("razao_social")));
                    empresas.add(empresa);

                }
            }

            cursor.close();
            return empresas;
        }

        public void selecionarPrimeiraEmpresa() {

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            ContentValues args = new ContentValues();
            ContentValues args2 = new ContentValues();
            args.put("selecionada", 0);
            args2.put("selecionada", 1);

            try {

                DB.getWritableDatabase().update(TABLE_EMPRESAS, args, null, null);
                DB.getWritableDatabase().update(TABLE_EMPRESAS, args2, "_id=(SELECT MIN(_id) FROM " + TABLE_EMPRESAS + ")", null);

            } catch (SQLiteException e) {
                mPrefs.Geral.setError(e.getMessage());
                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();

            }

        }

        public void marcarEmpresa() {

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            ContentValues args = new ContentValues();
            args.put("selecionada", 1);

            try {

                DB.getWritableDatabase().update(TABLE_EMPRESAS, args, null, null);

            } catch (SQLiteException e) {
                mPrefs.Geral.setError(e.getMessage());
                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();

            }

        }

        public void setSelecionada(int codigo) {

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            ContentValues args = new ContentValues();
            ContentValues args2 = new ContentValues();
            args.put("selecionada", 0);
            args2.put("selecionada", 1);

            try {

                DB.getWritableDatabase().update(TABLE_EMPRESAS, args, null, null);
                DB.getWritableDatabase().update(TABLE_EMPRESAS, args2, "codigo="+codigo, null);

            } catch (SQLiteException e) {
                mPrefs.Geral.setError(e.getMessage());
                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();

            }

        }
    }

    class GrupoEmpresas{

        public List<sonicGrupoEmpresasHolder> selectGrupoEmpresas(){

            List<sonicGrupoEmpresasHolder> grupoEmpresa = new ArrayList<>();

            Cursor cursor = DB.getReadableDatabase().rawQuery(
                    "SELECT * FROM " + TABLE_GRUPO_EMPRESAS, null);

            if(cursor!=null && cursor.moveToFirst()){

                sonicGrupoEmpresasHolder grupoEmpresas = new sonicGrupoEmpresasHolder();

                grupoEmpresas.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo_empresa")));
                grupoEmpresas.setNome(cursor.getString(cursor.getColumnIndex("nome")));
                grupoEmpresas.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
                grupoEmpresas.setDataFundacao(cursor.getString(cursor.getColumnIndex("data_fundacao")));
                grupoEmpresas.setEndereco(cursor.getString(cursor.getColumnIndex("endereco")));
                grupoEmpresas.setBairro(cursor.getString(cursor.getColumnIndex("bairro")));
                grupoEmpresas.setMunicipio(cursor.getString(cursor.getColumnIndex("municipio")));
                grupoEmpresas.setUf(cursor.getString(cursor.getColumnIndex("uf")));
                grupoEmpresas.setCep(cursor.getString(cursor.getColumnIndex("cep")));
                grupoEmpresas.setFone(cursor.getString(cursor.getColumnIndex("fone")));
                grupoEmpresas.setWhatsapp(cursor.getString(cursor.getColumnIndex("whatsapp")));
                grupoEmpresas.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                grupoEmpresas.setSite(cursor.getString(cursor.getColumnIndex("endereco_eletronico")));
                grupoEmpresa.add(grupoEmpresas);

            }
            cursor.close();
            return grupoEmpresa;
        }

    }

    class EmpresaCliente {

        public long count() {

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            long count = 0;

            SQLiteDatabase db = DB.getReadableDatabase();
            try {
                count = DatabaseUtils.queryNumEntries(db, TABLE_EMPRESAS_CLIENTES);
            } catch (SQLiteException e) {
                mPrefs.Geral.setError(e.getMessage());
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

    }

    class NivelAcesso {
    }

    class Cliente {

            public long count() {

                StackTraceElement el = Thread.currentThread().getStackTrace()[2];
                long count = 0;
                SQLiteDatabase db = DB.getReadableDatabase();
                try {
                    count = DatabaseUtils.queryNumEntries(db, TABLE_CLIENTES);
                } catch (SQLiteException e) {
                    mPrefs.Geral.setError(e.getMessage());
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

            public int countPorEmpresa() {

                StackTraceElement el = Thread.currentThread().getStackTrace()[2];
                int count = 0;

                SQLiteDatabase db = DB.getReadableDatabase();

                try {
                    Cursor cursor = db.rawQuery("SELECT c.codigo FROM " + TABLE_CLIENTES + " c WHERE c.codigo IN (SELECT ec.codigo_cliente FROM " + TABLE_EMPRESAS_CLIENTES + " ec WHERE ec.codigo_empresa = (SELECT e.codigo FROM "+ TABLE_EMPRESAS +" e WHERE e.selecionada = 1))", null);
                    count = cursor.getCount();
                } catch (SQLiteException e) {
                    mPrefs.Geral.setError(e.getMessage());
                    DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(), e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                    e.printStackTrace();
                }

                return count;
            }

            public List<sonicClientesHolder> selectClienteTipo(boolean cnpj) {

                StackTraceElement el = Thread.currentThread().getStackTrace()[2];
                List<sonicClientesHolder> cliente = new ArrayList<>();

                String query = "SELECT " +
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
                        "C.situacao AS situacao, " +
                        "GC.nome AS grupo," +
                        "(SELECT COUNT(T._id) FROM " + TABLE_TITULOS + " T WHERE T.codigo_cliente = C.codigo) AS titulos, " +
                        "(SELECT COUNT(T._id) FROM " + TABLE_TITULOS + " T WHERE T.codigo_cliente = C.codigo AND T.situacao = 2) AS titulos_em_atraso, " +
                        "(SELECT COUNT(UC._id) FROM " + TABLE_ULTIMAS_COMPRAS + " UC WHERE UC.codigo_cliente = C.codigo) AS compras, " +
                        "(SELECT COUNT(R._id) FROM " + TABLE_ROTA + " R WHERE R.codigo_cliente = C.codigo AND R.status=1) AS visitas, " +
                        "(SELECT CASE WHEN COUNT(CSC._id) > 0 THEN 1 ELSE 0 END FROM " + TABLE_CLIENTES_SEM_COMPRA + " CSC WHERE CSC.codigo_cliente = C.codigo) AS cli_sem_compra " +
                        " FROM " + TABLE_CLIENTES +
                        " C LEFT JOIN " + TABLE_GRUPO_CLIENTES +
                        " GC ON GC.codigo = C.codigo_grupo " +
                        " WHERE ((SELECT E.codigo FROM " + TABLE_EMPRESAS + " E WHERE E.selecionada = 1) IN (SELECT EC.codigo_empresa FROM "+ TABLE_EMPRESAS_CLIENTES +" EC WHERE EC.codigo_cliente = C.codigo))" +
                        " AND C.tipo = ? " + (cnpj ? (mPrefs.GrupoCliente.getFiltroCnpj().equals("TODOS") ? "" : " AND GC.nome = '" + mPrefs.GrupoCliente.getFiltroCnpj() + "'") : (mPrefs.GrupoCliente.getFiltroCpf().equals("TODOS") ? "" : " AND GC.nome = '" + mPrefs.GrupoCliente.getFiltroCpf() + "'")) + " ORDER BY " + (mPrefs.Clientes.getClienteExibicao().equals("Nome Fantasia") ? "C.nome_fantasia" : "C.razao_social");

                try {

                    Log.d("QUERY GRUPO CLIENTE", query);

                    Cursor cursor = DB.getReadableDatabase().rawQuery(
                            query, new String[]{cnpj ? "J" : "F"});

                    if(cursor!=null){

                        while (cursor.moveToNext()) {

                            sonicClientesHolder clientes = new sonicClientesHolder();
                            clientes.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
                            clientes.setTipo(cursor.getString(cursor.getColumnIndex("tipo")));
                            clientes.setRazaoSocial(cursor.getString(cursor.getColumnIndex("razao")));
                            clientes.setNomeFantasia(cursor.getString(cursor.getColumnIndex("fantasia")));
                            clientes.setCpfCnpj(cursor.getString(cursor.getColumnIndex("cpf_cnpj")));
                            clientes.setInscEstadual(cursor.getString(cursor.getColumnIndex("ie")));
                            clientes.setEndereco(cursor.getString(cursor.getColumnIndex("endereco")));
                            clientes.setBairro(cursor.getString(cursor.getColumnIndex("bairro")));
                            clientes.setMunicipio(cursor.getString(cursor.getColumnIndex("municipio")));
                            clientes.setUf(cursor.getString(cursor.getColumnIndex("uf")));
                            clientes.setCep(cursor.getString(cursor.getColumnIndex("cep")));
                            clientes.setFone(cursor.getString(cursor.getColumnIndex("fone")));
                            clientes.setContato(cursor.getString(cursor.getColumnIndex("contato")));
                            clientes.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                            clientes.setObservacao(cursor.getString(cursor.getColumnIndex("obs")));
                            clientes.setDataCadastro(cursor.getString(cursor.getColumnIndex("cadastro")));
                            clientes.setGrupo(cursor.getString(cursor.getColumnIndex("grupo")));
                            clientes.setTitulos(cursor.getInt(cursor.getColumnIndex("titulos")));
                            clientes.setTitulosEmAtraso(cursor.getInt(cursor.getColumnIndex("titulos_em_atraso")));
                            clientes.setCompras(cursor.getInt(cursor.getColumnIndex("compras")));
                            clientes.setVisitas(cursor.getInt(cursor.getColumnIndex("visitas")));
                            clientes.setSituacao(cursor.getInt(cursor.getColumnIndex("situacao")));
                            clientes.setCliSemCompra(cursor.getInt(cursor.getColumnIndex("cli_sem_compra")));
                            cliente.add(clientes);

                        }
                        cursor.close();
                    }

                } catch (SQLiteException e) {
                    mPrefs.Geral.setError(e.getMessage());
                    DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(), e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                    e.printStackTrace();
                }

                return cliente;

            }

            public List<sonicClientesDetalheComprasHolder> selectComprasPorCliente(int codigo, int limit) {

                StackTraceElement el = Thread.currentThread().getStackTrace()[2];
                List<sonicClientesDetalheComprasHolder> compra = new ArrayList<>();

                String query = "SELECT " +
                                "UC._id, " +
                                "UC.vendedor, " +
                                "UC.codigo, " +
                                "(SELECT E.nome_fantasia FROM " + TABLE_EMPRESAS + " E WHERE E.codigo = UC.codigo_empresa) AS empresa, " +
                                "(SELECT TP.nome FROM " + TABLE_TIPO_COBRANCA + " TP WHERE TP.codigo = UC.codigo_tipo_cobranca) AS tipo_cobranca, " +
                                "(SELECT AC.nome FROM " + TABLE_AGENTE_COBRADOR + " AC WHERE AC.codigo = UC.codigo_agente_cobrador) AS agente_cobrador, " +
                                "(SELECT P.nome FROM " + TABLE_PRAZO + " P WHERE P.codigo = UC.codigo_prazo) AS prazo, " +
                                "UC.data, " +
                                "UC.valor, " +
                                "UC.valor_desconto " +
                                " FROM " + TABLE_ULTIMAS_COMPRAS + " UC WHERE UC.codigo_cliente = "+codigo+" ORDER BY UC.data DESC LIMIT "+limit;

                try {

                    Cursor cursor = DB.getReadableDatabase().rawQuery(query, null);

                    if(cursor!=null){

                        while (cursor.moveToNext()){
                            sonicClientesDetalheComprasHolder compras = new sonicClientesDetalheComprasHolder();
                            compras.setVendedor(cursor.getString(cursor.getColumnIndex("vendedor")));
                            compras.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
                            compras.setEmpresa(cursor.getString(cursor.getColumnIndex("empresa")));
                            compras.setTipoCobranca(cursor.getString(cursor.getColumnIndex("tipo_cobranca")));
                            compras.setAgenteCobrador(cursor.getString(cursor.getColumnIndex("agente_cobrador")));
                            compras.setPrazo(cursor.getString(cursor.getColumnIndex("prazo")));
                            compras.setData(cursor.getString(cursor.getColumnIndex("data")));
                            compras.setValor(cursor.getString(cursor.getColumnIndex("valor")));
                            compras.setValorDesc(cursor.getString(cursor.getColumnIndex("valor_desconto")));
                            compra.add(compras);

                        }

                        cursor.close();
                    }

                }catch (SQLException e){
                    mPrefs.Geral.setError(e.getMessage());
                    DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),
                            e.getMessage(),
                            mySystem.System.getActivityName(),
                            mySystem.System.getClassName(el),
                            mySystem.System.getMethodNames(el));
                    e.printStackTrace();
                }
                return compra;
            }

            public List<sonicClientesDetalheComprasItensHolder> selectComprasPorClienteItens(int codigo) {

                StackTraceElement el = Thread.currentThread().getStackTrace()[2];
                List<sonicClientesDetalheComprasItensHolder> item = new ArrayList<>();

                String query = "SELECT * FROM (SELECT " +
                        "UCI._id, " +
                        "(SELECT IFNULL(P.nome,'--') FROM " + TABLE_PRODUTOS + " P WHERE P.codigo = UCI.codigo_produto) AS produto, " +
                        "UCI.codigo_produto AS codigo_produto, " +
                        "UCI.codigo AS codigo_venda, " +
                        "(SELECT UM.nome FROM " + TABLE_UNIDADE_MEDIDA + " UM WHERE UM.codigo = UCI.codigo_unidade) AS unidade_medida, " +
                        "(SELECT UM.sigla FROM " + TABLE_UNIDADE_MEDIDA + " UM WHERE UM.codigo = UCI.codigo_unidade) AS unidade_medida_sigla, " +
                        "UCI.quantidade AS quantidade, " +
                        "UCI.preco AS preco_unitario, " +
                        "UCI.valor AS valor, " +
                        "UCI.valor_desconto AS desconto " +
                        " FROM " + TABLE_ULTIMAS_COMPRAS_ITENS +
                        " UCI WHERE UCI.codigo = "+codigo+") X ORDER BY X.produto";

                try {

                    Cursor cursor = DB.getReadableDatabase().rawQuery(query, null);

                    if(cursor!=null){

                        while (cursor.moveToNext()){
                            sonicClientesDetalheComprasItensHolder itens = new sonicClientesDetalheComprasItensHolder();
                            itens.setProduto(cursor.getString(cursor.getColumnIndex("produto")));
                            itens.setCodigoProduto(cursor.getInt(cursor.getColumnIndex("codigo_produto")));
                            itens.setCodigoVenda(cursor.getInt(cursor.getColumnIndex("codigo_venda")));
                            itens.setUnidadeMedida(cursor.getString(cursor.getColumnIndex("unidade_medida")));
                            itens.setUnidadeMedidaSigla(cursor.getString(cursor.getColumnIndex("unidade_medida_sigla")));
                            itens.setQuantidade(cursor.getInt(cursor.getColumnIndex("quantidade")));
                            itens.setPrecoUnitario(cursor.getString(cursor.getColumnIndex("preco_unitario")));
                            itens.setValorGeral(cursor.getString(cursor.getColumnIndex("valor")));
                            itens.setDesconto(cursor.getString(cursor.getColumnIndex("desconto")));
                            item.add(itens);

                        }

                        cursor.close();
                    }

                }catch (SQLException e){
                    mPrefs.Geral.setError(e.getMessage());
                    DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),
                            e.getMessage(),
                            mySystem.System.getActivityName(),
                            mySystem.System.getClassName(el),
                            mySystem.System.getMethodNames(el));
                    e.printStackTrace();
                }
                return item;
            }


            public List<sonicClientesHolder> selectClienteByID(int id) {

                StackTraceElement el = Thread.currentThread().getStackTrace()[2];
                List<sonicClientesHolder> cliente = new ArrayList<sonicClientesHolder>();

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
                                    "c.data_cadastro," +
                                    "c.situacao," +
                                    "(SELECT GC.nome FROM "+ TABLE_GRUPO_CLIENTES +" GC WHERE GC.codigo = C.codigo_grupo) AS grupo," +
                                    "(SELECT COUNT(T._id) FROM " + TABLE_TITULOS + " T WHERE T.codigo_cliente = C.codigo) AS titulos, " +
                                    "(SELECT COUNT(T._id) FROM " + TABLE_TITULOS + " T WHERE T.codigo_cliente = C.codigo AND T.situacao = 2) AS titulos_em_atraso, " +
                                    "(SELECT COUNT(UC._id) FROM " + TABLE_ULTIMAS_COMPRAS + " UC WHERE UC.codigo_cliente = C.codigo) AS compras, " +
                                    "(SELECT COUNT(R._id) FROM " + TABLE_ROTA + " R WHERE R.codigo_cliente = C.codigo) AS visitas " +
                                    " FROM " + TABLE_CLIENTES +
                                    " c WHERE c.codigo = "+id, null);

                    while (cursor.moveToNext()) {

                        sonicClientesHolder clientes = new sonicClientesHolder();


                        clientes.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
                        clientes.setTipo(cursor.getString(cursor.getColumnIndex("tipo")));
                        clientes.setRazaoSocial(cursor.getString(cursor.getColumnIndex("razao_social")));
                        clientes.setNomeFantasia(cursor.getString(cursor.getColumnIndex("nome_fantasia")));
                        clientes.setCpfCnpj(cursor.getString(cursor.getColumnIndex("cpf_cnpj")));
                        clientes.setInscEstadual(cursor.getString(cursor.getColumnIndex("insc_estadual")));
                        clientes.setEndereco(cursor.getString(cursor.getColumnIndex("endereco")));
                        clientes.setBairro(cursor.getString(cursor.getColumnIndex("bairro")));
                        clientes.setMunicipio(cursor.getString(cursor.getColumnIndex("municipio")));
                        clientes.setUf(cursor.getString(cursor.getColumnIndex("uf")));
                        clientes.setCep(cursor.getString(cursor.getColumnIndex("cep")));
                        clientes.setFone(cursor.getString(cursor.getColumnIndex("fone")));
                        clientes.setContato(cursor.getString(cursor.getColumnIndex("contato")));
                        clientes.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                        clientes.setSituacao(cursor.getInt(cursor.getColumnIndex("situacao")));
                        clientes.setObservacao(cursor.getString(cursor.getColumnIndex("observacao")));
                        clientes.setDataCadastro(cursor.getString(cursor.getColumnIndex("data_cadastro")));
                        clientes.setGrupo(cursor.getString(cursor.getColumnIndex("grupo")));
                        clientes.setTitulos(cursor.getInt(cursor.getColumnIndex("titulos")));
                        clientes.setTitulosEmAtraso(cursor.getInt(cursor.getColumnIndex("titulos_em_atraso")));
                        clientes.setCompras(cursor.getInt(cursor.getColumnIndex("compras")));
                        clientes.setVisitas(cursor.getInt(cursor.getColumnIndex("visitas")));
                        cliente.add(clientes);

                    }

                    cursor.close();

                } catch (SQLiteException e) {
                    mPrefs.Geral.setError(e.getMessage());
                    DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),
                            e.getMessage(),
                            mySystem.System.getActivityName(),
                            mySystem.System.getClassName(el),
                            mySystem.System.getMethodNames(el));
                    e.printStackTrace();
                }

                return cliente;

            }

            public void addAcesso(int codigo){
                StackTraceElement el = Thread.currentThread().getStackTrace()[2];
                mCalendar = Calendar.getInstance();
                Cursor c;

                try {
                    c = DB.getReadableDatabase().rawQuery("SELECT * FROM "+TABLE_ACESSO_CLIENTE+" WHERE codigo_cliente=?", new String[]{String.valueOf(codigo)});
                    // SE NAO EXISTIR VALOR, INSERE
                    if(c==null || c.getCount()==0) {

                        ContentValues cv = new ContentValues();
                        cv.put("codigo_cliente", codigo);
                        cv.put("acessos", 1);
                        cv.put("data", data.format(mCalendar.getTime()));
                        cv.put("hora", hora.format(mCalendar.getTime()));

                        DB.getWritableDatabase().insert(TABLE_ACESSO_CLIENTE, null, cv);

                    }else{

                        DB.getWritableDatabase().execSQL("UPDATE " + TABLE_ACESSO_CLIENTE + " SET acessos=acessos+1, data=?, hora=? WHERE codigo_cliente=?", new String[]{data.format(mCalendar.getTime()),hora.format(mCalendar.getTime()), String.valueOf(codigo)});

                    }
                }
                catch (SQLiteException e){
                    mPrefs.Geral.setError(e.getMessage());
                    DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),
                            e.getMessage(),
                            mySystem.System.getActivityName(),
                            mySystem.System.getClassName(el),
                            mySystem.System.getMethodNames(el));
                    e.printStackTrace();
                    }

            }

        public List<sonicClientesHolder> selectAccessos(boolean cnpj){
            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            List<sonicClientesHolder> cliente = new ArrayList<sonicClientesHolder>();
            String query = "SELECT " +
                    "C.codigo," +
                    "C.razao_social," +
                    "C.nome_fantasia," +
                    "C.cpf_cnpj," +
                    "C.endereco," +
                    "(SELECT GC.nome FROM "+ TABLE_GRUPO_CLIENTES +" GC WHERE GC.codigo = C.codigo_grupo) AS grupo" +
                    " FROM " + TABLE_ACESSO_CLIENTE +
                    " AC JOIN "+ TABLE_CLIENTES + " C ON AC.codigo_cliente = C.codigo " +
                    " WHERE C.tipo=? ORDER BY AC.data DESC, AC.hora DESC, AC._id DESC, AC.acessos DESC LIMIT 6";
            try {
                Cursor cursor = DB.getReadableDatabase().rawQuery(query, new String[]{cnpj ? "J" : "F"});
                if(cursor!=null){
                    while (cursor.moveToNext()){
                        sonicClientesHolder clientes = new sonicClientesHolder();
                        clientes.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
                        clientes.setRazaoSocial(cursor.getString(cursor.getColumnIndex("razao_social")));
                        clientes.setNomeFantasia(cursor.getString(cursor.getColumnIndex("nome_fantasia")));
                        clientes.setCpfCnpj(cursor.getString(cursor.getColumnIndex("cpf_cnpj")));
                        clientes.setEndereco(cursor.getString(cursor.getColumnIndex("endereco")));
                        clientes.setGrupo(cursor.getString(cursor.getColumnIndex("grupo")));
                        cliente.add(clientes);
                    }
                }

                Log.d("QUERY ACESSOS", query);

                cursor.close();

            }catch (SQLiteException e){
                mPrefs.Geral.setError(e.getMessage());
                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),
                        e.getMessage(),
                        mySystem.System.getActivityName(),
                        mySystem.System.getClassName(el),
                        mySystem.System.getMethodNames(el));
                e.printStackTrace();
            }

            return cliente;
        }

    }

    class Venda {

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

        public List<sonicVendasValorHolder> selectVendas() {

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            List<sonicVendasValorHolder> venda = new ArrayList<>();

            try {

                String query = "SELECT * FROM (" +

                        "SELECT " +
                        "5 AS sequencial, "+
                        "strftime('%m', date('now', 'start of month')) AS mes, " +
                        "strftime('%Y', date('now', 'start of month')) AS ano, " +
                        "IFNULL(SUM(v.valor),0) AS valor " +
                        "FROM " + TABLE_VENDAS + " v WHERE v.codigo_empresa = (SELECT E.codigo FROM " + TABLE_EMPRESAS + " E WHERE E.selecionada=1) AND v.data >= strftime('%Y%m%d', date('now', 'start of month')) " +
                        "UNION " +
                        "SELECT " +
                        "4 AS sequencial, "+
                        "strftime('%m', date('now', 'start of month', '-1 months')) AS mes, " +
                        "strftime('%Y', date('now', 'start of month', '-1 months')) AS ano, " +
                        "IFNULL(SUM(v.valor),0) AS valor " +
                        "FROM " + TABLE_VENDAS + " v WHERE v.codigo_empresa = (SELECT E.codigo FROM " + TABLE_EMPRESAS + " E WHERE E.selecionada=1) AND v.data BETWEEN strftime('%Y%m%d', date('now', 'start of month', '-1 months')) AND strftime('%Y%m%d', date('now', 'start of month','0 months', '-1 day')) " +
                        "UNION " +
                        "SELECT " +
                        "3 AS sequencial, "+
                        "strftime('%m', date('now', 'start of month', '-2 months')) AS mes, " +
                        "strftime('%Y', date('now', 'start of month', '-2 months')) AS ano, " +
                        "IFNULL(SUM(v.valor),0) AS valor " +
                        "FROM " + TABLE_VENDAS + " v WHERE v.codigo_empresa = (SELECT E.codigo FROM " + TABLE_EMPRESAS + " E WHERE E.selecionada=1) AND v.data BETWEEN strftime('%Y%m%d', date('now', 'start of month', '-2 months')) AND strftime('%Y%m%d', date('now', 'start of month','-1 months', '-1 day')) " +
                        "UNION " +
                        "SELECT " +
                        "2 AS sequencial, "+
                        "strftime('%m', date('now', 'start of month', '-3 months')) AS mes, " +
                        "strftime('%Y', date('now', 'start of month', '-3 months')) AS ano, " +
                        "IFNULL(SUM(v.valor),0) AS valor " +
                        "FROM " + TABLE_VENDAS + " v WHERE v.codigo_empresa = (SELECT E.codigo FROM " + TABLE_EMPRESAS + " E WHERE E.selecionada=1) AND v.data BETWEEN strftime('%Y%m%d', date('now', 'start of month', '-3 months')) AND strftime('%Y%m%d', date('now', 'start of month','-2 months', '-1 day')) " +
                        "UNION " +
                        "SELECT " +
                        "1 AS sequencial, "+
                        "strftime('%m', date('now', 'start of month', '-4 months')) AS mes, " +
                        "strftime('%Y', date('now', 'start of month', '-4 months')) AS ano, " +
                        "IFNULL(SUM(v.valor),0) AS valor " +
                        "FROM " + TABLE_VENDAS + " v WHERE v.codigo_empresa = (SELECT E.codigo FROM " + TABLE_EMPRESAS + " E WHERE E.selecionada=1) AND v.data BETWEEN strftime('%Y%m%d', date('now', 'start of month', '-4 months')) AND strftime('%Y%m%d', date('now', 'start of month','-3 months', '-1 day')) " +
                        "UNION " +
                        "SELECT " +
                        "0 AS sequencial, "+
                        "strftime('%m', date('now', 'start of month', '-5 months')) AS mes, " +
                        "strftime('%Y', date('now', 'start of month', '-5 months')) AS ano, " +
                        "IFNULL(SUM(v.valor),0) AS valor " +
                        "FROM " + TABLE_VENDAS + " v WHERE v.codigo_empresa = (SELECT E.codigo FROM " + TABLE_EMPRESAS + " E WHERE E.selecionada=1) AND v.data BETWEEN strftime('%Y%m%d', date('now', 'start of month', '-6 months')) AND strftime('%Y%m%d', date('now', 'start of month','-5 months', '-1 day')));";

                Cursor cursor = DB.getReadableDatabase().rawQuery(query, null);

                if(cursor!=null){
                    while (cursor.moveToNext()){
                        sonicVendasValorHolder vendas = new sonicVendasValorHolder();

                        vendas.setMes(cursor.getString(cursor.getColumnIndex("mes")));
                        vendas.setAno(cursor.getString(cursor.getColumnIndex("ano")));
                        vendas.setValor(cursor.getString(cursor.getColumnIndex("valor")));
                        venda.add(vendas);

                    }

                }

            } catch (SQLiteException e) {
                mPrefs.Geral.setError(e.getMessage());
                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),
                        e.getMessage(),
                        mySystem.System.getActivityName(),
                        mySystem.System.getClassName(el),
                        mySystem.System.getMethodNames(el));
                e.printStackTrace();
            }
            return venda;
        }

        public List<sonicVendasPedidosHolder> selectPedidos() {

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            List<sonicVendasPedidosHolder> pedido = new ArrayList<>();

            try {

                String query = "SELECT * FROM (" +

                        "SELECT " +
                        "5 AS sequencial, "+
                        "strftime('%m', date('now', 'start of month')) AS mes, " +
                        "strftime('%Y', date('now', 'start of month')) AS ano, " +
                        "IFNULL(COUNT(v._id),0) AS total " +
                        "FROM " + TABLE_VENDAS + " v WHERE v.codigo_empresa = (SELECT E.codigo FROM " + TABLE_EMPRESAS + " E WHERE E.selecionada=1) AND v.data >= strftime('%Y%m%d', date('now', 'start of month')) " +
                        "UNION " +
                        "SELECT " +
                        "4 AS sequencial, "+
                        "strftime('%m', date('now', 'start of month', '-1 months')) AS mes, " +
                        "strftime('%Y', date('now', 'start of month', '-1 months')) AS ano, " +
                        "IFNULL(COUNT(v._id),0) AS total " +
                        "FROM " + TABLE_VENDAS + " v WHERE v.codigo_empresa = (SELECT E.codigo FROM " + TABLE_EMPRESAS + " E WHERE E.selecionada=1) AND v.data BETWEEN strftime('%Y%m%d', date('now', 'start of month', '-1 months')) AND strftime('%Y%m%d', date('now', 'start of month','0 months', '-1 day')) " +
                        "UNION " +
                        "SELECT " +
                        "3 AS sequencial, "+
                        "strftime('%m', date('now', 'start of month', '-2 months')) AS mes, " +
                        "strftime('%Y', date('now', 'start of month', '-2 months')) AS ano, " +
                        "IFNULL(COUNT(v._id),0) AS total " +
                        "FROM " + TABLE_VENDAS + " v WHERE v.codigo_empresa = (SELECT E.codigo FROM " + TABLE_EMPRESAS + " E WHERE E.selecionada=1) AND v.data BETWEEN strftime('%Y%m%d', date('now', 'start of month', '-2 months')) AND strftime('%Y%m%d', date('now', 'start of month','-1 months', '-1 day')) " +
                        "UNION " +
                        "SELECT " +
                        "2 AS sequencial, "+
                        "strftime('%m', date('now', 'start of month', '-3 months')) AS mes, " +
                        "strftime('%Y', date('now', 'start of month', '-3 months')) AS ano, " +
                        "IFNULL(COUNT(v._id),0) AS total " +
                        "FROM " + TABLE_VENDAS + " v WHERE v.codigo_empresa = (SELECT E.codigo FROM " + TABLE_EMPRESAS + " E WHERE E.selecionada=1) AND v.data BETWEEN strftime('%Y%m%d', date('now', 'start of month', '-3 months')) AND strftime('%Y%m%d', date('now', 'start of month','-2 months', '-1 day')) " +
                        "UNION " +
                        "SELECT " +
                        "1 AS sequencial, "+
                        "strftime('%m', date('now', 'start of month', '-4 months')) AS mes, " +
                        "strftime('%Y', date('now', 'start of month', '-4 months')) AS ano, " +
                        "IFNULL(COUNT(v._id),0) AS total " +
                        "FROM " + TABLE_VENDAS + " v WHERE v.codigo_empresa = (SELECT E.codigo FROM " + TABLE_EMPRESAS + " E WHERE E.selecionada=1) AND v.data BETWEEN strftime('%Y%m%d', date('now', 'start of month', '-4 months')) AND strftime('%Y%m%d', date('now', 'start of month','-3 months', '-1 day')) " +
                        "UNION " +
                        "SELECT " +
                        "0 AS sequencial, "+
                        "strftime('%m', date('now', 'start of month', '-5 months')) AS mes, " +
                        "strftime('%Y', date('now', 'start of month', '-5 months')) AS ano, " +
                        "IFNULL(COUNT(v._id),0) AS total " +
                        "FROM " + TABLE_VENDAS + " v WHERE v.codigo_empresa = (SELECT E.codigo FROM " + TABLE_EMPRESAS + " E WHERE E.selecionada=1) AND v.data BETWEEN strftime('%Y%m%d', date('now', 'start of month', '-6 months')) AND strftime('%Y%m%d', date('now', 'start of month','-5 months', '-1 day')));";

                Cursor cursor = DB.getReadableDatabase().rawQuery(query, null);

                if(cursor!=null){
                    while (cursor.moveToNext()){
                        sonicVendasPedidosHolder pedidos = new sonicVendasPedidosHolder();

                        pedidos.setMes(cursor.getString(cursor.getColumnIndex("mes")));
                        pedidos.setAno(cursor.getString(cursor.getColumnIndex("ano")));
                        pedidos.setTotal(cursor.getInt(cursor.getColumnIndex("total")));
                        pedido.add(pedidos);

                    }

                }

                cursor.close();

            } catch (SQLiteException e) {
                mPrefs.Geral.setError(e.getMessage());
                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),
                        e.getMessage(),
                        mySystem.System.getActivityName(),
                        mySystem.System.getClassName(el),
                        mySystem.System.getMethodNames(el));
                e.printStackTrace();
            }
            return pedido;
        }

        public List<sonicVendasDesempenhoHolder> selectDesempenhoDiario(){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            List<sonicVendasDesempenhoHolder> desempenho = new ArrayList<>();

            try {

                String query = "SELECT " +
                        "substr(V.data, 7, 9) AS dia, " +
                        "SUM(V.valor) AS valor " +
                        "FROM " + TABLE_VENDAS + " V " +
                        "WHERE V.data >=  strftime('%Y%m%d', date('now', 'start of month','-4 months')) GROUP BY V.data ORDER BY V.data";

                Cursor cursor = DB.getReadableDatabase().rawQuery(query, null);

                if(cursor!=null){
                    while (cursor.moveToNext()){
                        sonicVendasDesempenhoHolder des = new sonicVendasDesempenhoHolder();
                        des.setDia(cursor.getString(cursor.getColumnIndex("dia")));
                        des.setValor(cursor.getString(cursor.getColumnIndex("valor")));
                        desempenho.add(des);
                    }
                }

                cursor.close();

            }catch (SQLiteException e){
                mPrefs.Geral.setError(e.getMessage());
                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),
                        e.getMessage(),
                        mySystem.System.getActivityName(),
                        mySystem.System.getClassName(el),
                        mySystem.System.getMethodNames(el));
                e.printStackTrace();
            }

            return desempenho;
        }

    }

    class VendaItem {

        public long count() {

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            long count = 0;
            SQLiteDatabase db = DB.getReadableDatabase();
            try {
                count = DatabaseUtils.queryNumEntries(db, TABLE_VENDAS_ITENS);
            } catch (SQLiteException e) {
                mPrefs.Geral.setError(e.getMessage());
                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();
            }

            return count;
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
                mPrefs.Geral.setError(e.getMessage());
                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();
            }

            return count;
        }

        public List<sonicGrupoClientesHolder> selectGrupoCliente(String... args){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            List<sonicGrupoClientesHolder> grupos = new ArrayList<sonicGrupoClientesHolder>();

            try{

                String query = "SELECT " +
                        "GC._id, " +
                        "GC.codigo, " +
                        "GC.nome FROM " + TABLE_GRUPO_CLIENTES + " GC " +
                        " JOIN " + TABLE_CLIENTES + " C " +
                        " ON C.codigo_grupo = GC.codigo " +
                        " JOIN " + TABLE_EMPRESAS_CLIENTES + " EC " +
                        " ON EC.codigo_cliente = C.codigo " +
                        " JOIN " + TABLE_EMPRESAS + " E " +
                        " ON E.codigo = EC.codigo_empresa " +
                        " WHERE E.selecionada=1 AND C.tipo = ? GROUP BY GC.nome ";

                Cursor cursor = DB.getReadableDatabase().rawQuery(
                        query , new String[]{args[0]});

                if(cursor!=null){
                    while(cursor.moveToNext()){

                        sonicGrupoClientesHolder grupo = new sonicGrupoClientesHolder();

                        grupo.setId(cursor.getInt(cursor.getColumnIndex("_id")));
                        grupo.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
                        grupo.setNome(cursor.getString(cursor.getColumnIndex("nome")));
                        grupos.add(grupo);

                    }
                }

                cursor.close();


            }catch (SQLiteException e){
                mPrefs.Geral.setError(e.getMessage());
                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();
            }

            return grupos;
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
                mPrefs.Geral.setError(e.getMessage());
                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();
            }

            return count;
        }


    }

    class ClientesSemCompra {

        public long count(){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            long count=0;
            SQLiteDatabase db = DB.getReadableDatabase();
            try{
                count  = DatabaseUtils.queryNumEntries(db, TABLE_CLIENTES_SEM_COMPRA);
            }catch (SQLiteException e){
                mPrefs.Geral.setError(e.getMessage());
                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();
            }

            return count;
        }

        public List<sonicClientesSemCompraHolder> selectClienteSemCompra(){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            List<sonicClientesSemCompraHolder> CLIENTE = new ArrayList<sonicClientesSemCompraHolder>();

            Cursor cursor = DB.getReadableDatabase().rawQuery(
                    "SELECT * FROM " + TABLE_CLIENTES_SEM_COMPRA, null);

            while(cursor.moveToNext()){

                sonicClientesSemCompraHolder cliente = new sonicClientesSemCompraHolder();

                cliente.setCodigoCliente(cursor.getInt(cursor.getColumnIndex("codigo_cliente")));
                cliente.setDias(cursor.getInt(cursor.getColumnIndex("dias_sem_compra")));


                CLIENTE.add(cliente);

            }
            cursor.close();
            return CLIENTE;
        }
    }

    class Usuario {

        public long count(){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            long count=0;
            SQLiteDatabase db = DB.getReadableDatabase();
            try{
                count  = DatabaseUtils.queryNumEntries(db, TABLE_USUARIOS);
            }catch (SQLiteException e){
                mPrefs.Geral.setError(e.getMessage());
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

        public boolean checkCredenciais(String usuario, String senha){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            Cursor cursor;
            boolean res = false;
            String query = "SELECT * FROM " + TABLE_USUARIOS + " WHERE codigo = "+usuario+" AND senha = '"+senha+"'";
            try{

                cursor = DB.getReadableDatabase().rawQuery(query, null);

                if(cursor!= null && cursor.moveToFirst() ){
                    res =  true;
                }
                cursor.close();

            }catch (SQLiteException e){
                mPrefs.Geral.setError(e.getMessage());
                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();
            }

            return res;

        }

        public List<sonicUsuariosHolder> selectUsuarioAtivo(){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            List<sonicUsuariosHolder> usuarios = new ArrayList<sonicUsuariosHolder>();

            String query = "SELECT " +
                    "U.codigo, " +
                    "U.nome, " +
                    "U.login, " +
                    "U.senha, " +
                    "U.imei, " +
                    "U.admin, " +
                    "U.nivel_acesso, " +
                    "EU.meta_venda, " +
                    "EU.meta_visita, " +
                    "E.nome_fantasia AS empresa, " +
                    "E.codigo AS empresa_id, " +
                    "(SELECT U2.nome FROM " + TABLE_USUARIOS + " U2 WHERE U2.codigo = U.usuario_superior) AS usuario_superior, " +
                    "(SELECT NA.nome FROM " + TABLE_NIVEL_ACESSO + " NA WHERE NA.codigo = U.nivel_acesso) AS cargo " +
                    " FROM " + TABLE_USUARIOS + " U " +
                    " JOIN " + TABLE_EMPRESAS_USUARIOS + " EU ON EU.codigo_usuario = U.codigo " +
                    " JOIN " + TABLE_EMPRESAS + " E ON E.codigo = EU.codigo_empresa AND U.ativo = 1 ORDER BY E.selecionada DESC";


            try{

                Cursor cursor = DB.getReadableDatabase().rawQuery(query, null);

                if(cursor!=null && cursor.moveToFirst()){

                    sonicUsuariosHolder usuario = new sonicUsuariosHolder();

                    usuario.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
                    usuario.setNome(cursor.getString(cursor.getColumnIndex("nome")));
                    usuario.setLogin(cursor.getString(cursor.getColumnIndex("login")));
                    usuario.setPass(cursor.getString(cursor.getColumnIndex("senha")));
                    usuario.setImei(cursor.getString(cursor.getColumnIndex("imei")));
                    usuario.setAdmin(cursor.getInt(cursor.getColumnIndex("admin")));
                    usuario.setNivelAcessoId(cursor.getInt(cursor.getColumnIndex("nivel_acesso")));
                    usuario.setCargo(cursor.getString(cursor.getColumnIndex("cargo")));
                    usuario.setUsuarioSuperior(cursor.getString(cursor.getColumnIndex("usuario_superior")));
                    usuario.setEmpresa(cursor.getString(cursor.getColumnIndex("empresa")));
                    usuario.setEmpresaId(cursor.getInt(cursor.getColumnIndex("empresa_id")));
                    usuario.setMetaVenda(cursor.getString(cursor.getColumnIndex("meta_venda")));
                    usuario.setMetaVisita(cursor.getInt(cursor.getColumnIndex("meta_visita")));
                    usuarios.add(usuario);
                }

                Log.d("QUERY", query);

                cursor.close();

            }catch (SQLiteException e){
                mPrefs.Geral.setError(e.getMessage());
                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();
            }

            return usuarios;
        }

        public List<sonicUsuariosHolder> listaUsuarios(){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            List<sonicUsuariosHolder> usuarios = new ArrayList<sonicUsuariosHolder>();

            String query = "SELECT " +
                    "U.codigo, " +
                    "U.nome, " +
                    "U.login " +
                    " FROM " + TABLE_USUARIOS + " U ";

            try{

                Cursor cursor = DB.getReadableDatabase().rawQuery(query, null);

                if(cursor!=null){

                    while (cursor.moveToNext()){
                        sonicUsuariosHolder usuario = new sonicUsuariosHolder();

                        usuario.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
                        usuario.setNome(cursor.getString(cursor.getColumnIndex("nome")));
                        usuario.setLogin(cursor.getString(cursor.getColumnIndex("login")));
                        usuarios.add(usuario);
                    }

                }

                cursor.close();

            }catch (SQLiteException e){
                mPrefs.Geral.setError(e.getMessage());
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
                                "u.codigo, " +
                                "u.nome, " +
                                "e.nome_fantasia AS empresa, " +
                                "e.codigo AS empresa_id, " +
                                "(SELECT na.nome FROM "+TABLE_NIVEL_ACESSO+" na WHERE na.codigo = u.nivel_acesso) AS cargo " +
                                " FROM " + TABLE_USUARIOS + " u " +
                                " JOIN " + TABLE_EMPRESAS_USUARIOS + " eu ON eu.codigo_usuario=u.codigo " +
                                " JOIN " + TABLE_EMPRESAS + " e ON e.codigo=eu.codigo_empresa WHERE e.selecionada = 1 AND u.imei = '"+imei+"'", null);

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
                mPrefs.Geral.setError(e.getMessage());
                DBCL.Log.saveLog(
                        e.getStackTrace()[0].getLineNumber(),e.getMessage(),
                        mySystem.System.getActivityName(),
                        mySystem.System.getClassName(el),
                        mySystem.System.getMethodNames(el));
                e.printStackTrace();
            }

            return usuarios;
        }

        public Boolean usuarioAtivo() {

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            Boolean result = false;

            try {
                Cursor cursor = DB.getReadableDatabase().rawQuery("SELECT u.codigo FROM "+ TABLE_USUARIOS +" u WHERE u.ativo = 1", null);
                result = cursor.moveToFirst();
            } catch (SQLiteException e) {
                mPrefs.Geral.setError(e.getMessage());
                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();
            }
            return result;

        }

        public void setAtivo(int codigo){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            ContentValues args = new ContentValues();
            ContentValues args2 = new ContentValues();
            args.put("ativo", 0);
            args2.put("ativo", 1);

            try{

                DB.getWritableDatabase().update(TABLE_USUARIOS, args, null, null);
                DB.getWritableDatabase().update(TABLE_USUARIOS, args2, " codigo = ?", new String[]{String.valueOf(codigo)});

            }catch (SQLiteException e){
                mPrefs.Geral.setError(e.getMessage());
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
                mPrefs.Geral.setError(e.getMessage());
                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();

            }
        }

    }

    class EmpresaUsuario {

    }

    class Sincronizacao{

        public long count(){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            long count=0;
            SQLiteDatabase db = DB.getReadableDatabase();
            try{
                count  = DatabaseUtils.queryNumEntries(db, TABLE_SINCRONIZACAO);
            }catch (SQLiteException e){
                mPrefs.Geral.setError(e.getMessage());
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
            SimpleDateFormat hora = new SimpleDateFormat((mPrefs.Geral.getTipoHora().equals("12 Horas") || mPrefs.Geral.getTipoHora() == "12 Horas" ? "hh:mm a" : "HH:mm"));
            SimpleDateFormat data = new SimpleDateFormat("dd/MM/yyyy");
            String hora_atual = hora.format(c.getTime());
            String data_atual = data.format(c.getTime());
            try{

                cv.put("tabela", tabela);
                cv.put("tipo", tipo);
                cv.put("data_sinc", data_atual);
                cv.put("hora_sinc", hora_atual);

            }catch (SQLiteException e){
                mPrefs.Geral.setError(e.getMessage());
                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();

            }

            return DB.getWritableDatabase().insert(TABLE_SINCRONIZACAO, null, cv)>0;
        }

        public List<sonicSincronizacaoDownloadHolder> selectLastSinc(String tabela, String tipo){
            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            List<sonicSincronizacaoDownloadHolder> sincs = new ArrayList<>();

            try{

                Cursor cursor = DB.getReadableDatabase().rawQuery("SELECT data_sinc, hora_sinc FROM " + TABLE_SINCRONIZACAO + " WHERE tabela='"+tabela+"' AND tipo='"+tipo+"' ORDER BY _id DESC LIMIT 1", null);

                if( cursor != null && cursor.moveToFirst() ){
                    sonicSincronizacaoDownloadHolder sinc = new sonicSincronizacaoDownloadHolder();
                    sinc.setData(cursor.getString(cursor.getColumnIndex("data_sinc")));
                    sinc.setHora(cursor.getString(cursor.getColumnIndex("hora_sinc")));
                    sincs.add(sinc);

                }else{
                    sonicSincronizacaoDownloadHolder sinc = new sonicSincronizacaoDownloadHolder();
                    sinc.setData("");
                    sinc.setHora("");
                    sincs.add(sinc);
                }
                cursor.close();

            }catch (SQLiteException e){
                mPrefs.Geral.setError(e.getMessage());
                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();
            }

            return sincs;
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
                mPrefs.Geral.setError(e.getMessage());
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
                mPrefs.Geral.setError(e.getMessage());
                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();
            }

            return count;
        }

        public int countPorEmpresa() {

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            int count = 0;

            SQLiteDatabase db = DB.getReadableDatabase();
            try {
                Cursor cursor = db.rawQuery("SELECT P._id FROM " + TABLE_PRODUTOS + " P WHERE P.codigo NOT IN(SELECT BP.codigo_produto FROM "+ TABLE_BLOQUEIO_PRODUTOS +" BP WHERE BP.codigo_empresa = P.codigo_empresa) AND P.codigo_empresa = (SELECT E.codigo FROM " + TABLE_EMPRESAS + " E WHERE E.selecionada=1)", null);
                count = cursor.getCount();
            } catch (SQLiteException e) {
                mPrefs.Geral.setError(e.getMessage());
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
                mPrefs.Geral.setError(e.getMessage());
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

        public List<sonicProdutosHolder> selectProduto(boolean lista){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            List<sonicProdutosHolder> produto = new ArrayList<sonicProdutosHolder>();

            String query = "SELECT " +
                    "P.codigo, " +
                    "P.nome, " +
                    "P.codigo_empresa, " +
                    "P.data_cadastro, " +
                    "P.codigo_alternativo, " +
                    "P.descricao, " +
                    "P.ncm, " +
                    "P.peso_bruto, " +
                    "P.peso_liquido, " +
                    "P.estoque_minimo, " +
                    "P.estoque_maximo, " +
                    "P.multiplicidade, " +
                    "P.codigo_ean, " +
                    "P.codigo_ean_tributavel, " +
                    "P.foto, " +
                    "GP.nome AS grupo_produto, " +
                    " (SELECT EP.estoque FROM " + TABLE_ESTOQUE_PRODUTOS + " EP WHERE EP.codigo_produto = P.codigo) AS estoque, " +
                    " (SELECT UN.nome FROM " + TABLE_UNIDADE_MEDIDA + " UN WHERE UN.codigo = P.codigo_unidade) AS unidade_medida " +
                    " FROM " + TABLE_PRODUTOS + " P " +
                    " JOIN " + TABLE_GRUPO_PRODUTOS + " GP ON GP.codigo = P.codigo_grupo " +
                    " WHERE P.codigo NOT IN(SELECT BP.codigo_produto FROM "+ TABLE_BLOQUEIO_PRODUTOS +" BP WHERE BP.codigo_empresa = P.codigo_empresa) AND P.codigo_empresa = (SELECT E.codigo FROM " + TABLE_EMPRESAS + " E WHERE E.selecionada=1) " + (lista ? (mPrefs.GrupoProduto.getFiltroLista().equals("TODOS") ? "" : " AND GP.nome = '"+ mPrefs.GrupoProduto.getFiltroLista() +"'") : (mPrefs.GrupoProduto.getFiltroGrid().equals("TODOS") ? "" : " AND GP.nome = '"+ mPrefs.GrupoProduto.getFiltroGrid() +"'"))+" ORDER BY P.nome ";

            Log.d("QUERY", query);

            Cursor cursor = DB.getReadableDatabase().rawQuery(query, null);

            try{

                if(cursor!=null){

                    while(cursor.moveToNext()){

                        sonicProdutosHolder produtos = new sonicProdutosHolder();

                        produtos.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
                        produtos.setCodigoEmpresa(cursor.getInt(cursor.getColumnIndex("codigo_empresa")));
                        produtos.setNome(cursor.getString(cursor.getColumnIndex("nome")));
                        produtos.setUnidadeMedida(cursor.getString(cursor.getColumnIndex("unidade_medida")));
                        produtos.setGrupo(cursor.getString(cursor.getColumnIndex("grupo_produto")));
                        produtos.setDataCadastro(cursor.getString(cursor.getColumnIndex("data_cadastro")));
                        produtos.setCodigoAlternativo(cursor.getString(cursor.getColumnIndex("codigo_alternativo")));
                        produtos.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
                        produtos.setNcm(cursor.getString(cursor.getColumnIndex("ncm")));
                        produtos.setPesoBruto(cursor.getString(cursor.getColumnIndex("peso_bruto")));
                        produtos.setPesoLiquido(cursor.getString(cursor.getColumnIndex("peso_liquido")));
                        produtos.setEstoque(cursor.getInt(cursor.getColumnIndex("estoque")));
                        produtos.setEstoqueMinimo(cursor.getInt(cursor.getColumnIndex("estoque_minimo")));
                        produtos.setEstoqueMaximo(cursor.getInt(cursor.getColumnIndex("estoque_maximo")));
                        produtos.setMultiplicidade(cursor.getInt(cursor.getColumnIndex("multiplicidade")));
                        produtos.setCodigoEan(cursor.getString(cursor.getColumnIndex("codigo_ean")));
                        produtos.setCodigoEanTributavel(cursor.getString(cursor.getColumnIndex("codigo_ean_tributavel")));
                        produtos.setFoto(cursor.getInt(cursor.getColumnIndex("foto")));

                        produto.add(produtos);

                    }
                }


            }catch (SQLiteException e){
                mPrefs.Geral.setError(e.getMessage());
                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();
            }finally {
                cursor.close();
                DB.close();
            }

            return produto;
        }

        public List<sonicProdutosHolder> selectProdutoID(int codigo){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            List<sonicProdutosHolder> produto = new ArrayList<>();

            String query = "SELECT " +
                    "p.codigo, " +
                    "p.nome, " +
                    "p.codigo_empresa, " +
                    "p.data_cadastro, " +
                    "p.codigo_alternativo, " +
                    "p.descricao, " +
                    "p.ncm, " +
                    "p.peso_bruto, " +
                    "p.peso_liquido, " +
                    "p.estoque_minimo, " +
                    "p.estoque_maximo, " +
                    "p.multiplicidade, " +
                    "p.codigo_ean, " +
                    "p.codigo_ean_tributavel, " +
                    "p.foto, " +
                    " (SELECT ep.estoque FROM " + TABLE_ESTOQUE_PRODUTOS + " ep WHERE ep.codigo_produto = p.codigo) AS estoque, " +
                    " (SELECT un.nome FROM " + TABLE_UNIDADE_MEDIDA + " un WHERE un.codigo = p.codigo_unidade) AS unidade_medida, " +
                    " (SELECT gp.nome FROM " + TABLE_GRUPO_PRODUTOS + " gp WHERE gp.codigo = p.codigo_grupo) AS grupo_produto " +
                    " FROM " + TABLE_PRODUTOS + " p " +
                    " WHERE P.codigo NOT IN(SELECT BP.codigo_produto FROM "+ TABLE_BLOQUEIO_PRODUTOS +" BP WHERE BP.codigo_empresa = P.codigo_empresa) AND p.codigo_empresa = (SELECT emp.codigo FROM " + TABLE_EMPRESAS + " emp WHERE emp.selecionada=1) AND p.codigo=?";

            Cursor cursor = DB.getReadableDatabase().rawQuery(query, new String[]{String.valueOf(codigo)});

            try{

                if(cursor!=null && cursor.moveToFirst()){

                    sonicProdutosHolder produtos = new sonicProdutosHolder();

                    produtos.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
                    produtos.setCodigoEmpresa(cursor.getInt(cursor.getColumnIndex("codigo_empresa")));
                    produtos.setNome(cursor.getString(cursor.getColumnIndex("nome")));
                    produtos.setUnidadeMedida(cursor.getString(cursor.getColumnIndex("unidade_medida")));
                    produtos.setGrupo(cursor.getString(cursor.getColumnIndex("grupo_produto")));
                    produtos.setDataCadastro(cursor.getString(cursor.getColumnIndex("data_cadastro")));
                    produtos.setCodigoAlternativo(cursor.getString(cursor.getColumnIndex("codigo_alternativo")));
                    produtos.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
                    produtos.setNcm(cursor.getString(cursor.getColumnIndex("ncm")));
                    produtos.setPesoBruto(cursor.getString(cursor.getColumnIndex("peso_bruto")));
                    produtos.setPesoLiquido(cursor.getString(cursor.getColumnIndex("peso_liquido")));
                    produtos.setEstoque(cursor.getInt(cursor.getColumnIndex("estoque")));
                    produtos.setEstoqueMinimo(cursor.getInt(cursor.getColumnIndex("estoque_minimo")));
                    produtos.setEstoqueMaximo(cursor.getInt(cursor.getColumnIndex("estoque_maximo")));
                    produtos.setMultiplicidade(cursor.getInt(cursor.getColumnIndex("multiplicidade")));
                    produtos.setCodigoEan(cursor.getString(cursor.getColumnIndex("codigo_ean")));
                    produtos.setCodigoEanTributavel(cursor.getString(cursor.getColumnIndex("codigo_ean_tributavel")));
                    produtos.setFoto(cursor.getInt(cursor.getColumnIndex("foto")));
                    produto.add(produtos);

                }
            }catch (SQLiteException e){
                mPrefs.Geral.setError(e.getMessage());
                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();
            }finally {
                cursor.close();
            }


            return produto;
        }

        public List<sonicProdutosHolder> selectRankingProduto(){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            List<sonicProdutosHolder> PRODUTO = new ArrayList<sonicProdutosHolder>();

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
                            " p JOIN " + TABLE_GRUPO_PRODUTOS +
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
                produto.setEstoque(cursor.getInt(cursor.getColumnIndex("estoque")));


                PRODUTO.add(produto);

            }
            cursor.close();
            return PRODUTO;
        }

        public List<sonicProdutosHolder> selectRankingProdutoGrupo(){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            List<sonicProdutosHolder> PRODUTO = new ArrayList<sonicProdutosHolder>();

            Cursor cursor = DB.getReadableDatabase().rawQuery(
                    "SELECT * FROM (SELECT " +
                            "gp.nome as nome, " +
                            "SUM(rp.quantidade) as quantidade, " +
                            "SUM(rp.quantidade_anterior) as quantidade_anterior, " +
                            "SUM(rp.pedidos) as pedidos, " +
                            "SUM(rp.atuacao) as atuacao, " +
                            "(SELECT p.codigo_empresa FROM " + TABLE_PRODUTOS + " p WHERE p.codigo_grupo = gp.codigo_grupo LIMIT 1) as empresa" +
                            " FROM " + TABLE_RANKING_PRODUTOS +
                            " rp JOIN " + TABLE_GRUPO_PRODUTOS +
                            " gp ON gp.codigo_grupo = rp.codigo_grupo " +
                            " GROUP BY gp.nome) T WHERE T.empresa = (SELECT e.codigo_empresa FROM "+ TABLE_EMPRESAS +" e WHERE e.selecionada = 1) ORDER BY T.quantidade DESC", null);

            while(cursor.moveToNext()){

                sonicProdutosHolder produto = new sonicProdutosHolder();

                produto.setGrupo(cursor.getString(cursor.getColumnIndex("nome")));

                PRODUTO.add(produto);

            }
            cursor.close();
            return PRODUTO;
        }

    }

    class Estoque{

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
                            " f JOIN "+ TABLE_CLIENTES +" c ON c.codigo_cliente = f.codigo_cliente WHERE c.selecionado = 1", null);

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

    class Titulo {

        public long count(){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            long count=0;
            SQLiteDatabase db = DB.getWritableDatabase();
            try{
                count  = DatabaseUtils.queryNumEntries(db, TABLE_TITULOS);
            }catch (SQLiteException e){
                mPrefs.Geral.setError(e.getMessage());
                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();
            }

            return count;
        }

        public List<sonicTitulosHolder> selectTitulosPorCliente(int codigo){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            List<sonicTitulosHolder> titulos = new ArrayList<sonicTitulosHolder>();

            try {

                String query = "SELECT " +
                        "T._id, " +
                        "T.codigo, " +
                        "T.numero, " +
                        "(SELECT E.nome_fantasia FROM " + TABLE_EMPRESAS + " E WHERE E.codigo = T.codigo_empresa) AS nome_fantasia, " +
                        "(SELECT E.razao_social FROM " + TABLE_EMPRESAS + " E WHERE E.codigo = T.codigo_empresa) AS razao_social, " +
                        "T.data_emissao, " +
                        "T.data_vencimento, " +
                        "(SELECT AC.nome FROM "+ TABLE_AGENTE_COBRADOR +" AC WHERE AC.codigo = T.codigo_agente_cobrador) AS agente_cobrador, " +
                        "(SELECT TC.nome FROM "+ TABLE_TIPO_COBRANCA +" TC WHERE TC.codigo = T.codigo_tipo_cobranca) AS tipo_cobranca, " +
                        "T.valor, " +
                        "T.saldo, " +
                        "(T.data_vencimento - strftime('%Y%m%d', date('now')))*-1 AS atraso, " +
                        "T.juros, " +
                        "T.situacao " +
                        " FROM " + TABLE_TITULOS +
                        " T WHERE T.codigo_cliente=? ORDER BY T.data_emissao DESC";

                Cursor cursor = DB.getReadableDatabase().rawQuery(query
                        , new String[]{String.valueOf(codigo)});

                Log.d("QUERY TITULO", query);

                if(cursor!=null){

                    while(cursor.moveToNext()){

                        sonicTitulosHolder titulo = new sonicTitulosHolder();

                        titulo.setNomeFantasia(cursor.getString(cursor.getColumnIndex("nome_fantasia")));
                        titulo.setRazaoSocial(cursor.getString(cursor.getColumnIndex("razao_social")));
                        titulo.setAgenteCobrador(cursor.getString(cursor.getColumnIndex("agente_cobrador")));
                        titulo.setTipoCobranca(cursor.getString(cursor.getColumnIndex("tipo_cobranca")));
                        titulo.setDataEmissao(cursor.getString(cursor.getColumnIndex("data_emissao")));
                        titulo.setDataVencimento(cursor.getString(cursor.getColumnIndex("data_vencimento")));
                        titulo.setSituacao(cursor.getInt(cursor.getColumnIndex("situacao")));
                        titulo.setNumero(cursor.getString(cursor.getColumnIndex("numero")));
                        titulo.setValor(cursor.getString(cursor.getColumnIndex("valor")));
                        titulo.setSaldo(cursor.getString(cursor.getColumnIndex("saldo")));
                        titulo.setJuros(cursor.getString(cursor.getColumnIndex("juros")));
                        titulo.setDiasAtraso(cursor.getInt(cursor.getColumnIndex("atraso")));
                        titulos.add(titulo);

                    }
                }

                cursor.close();

            }catch (SQLiteException e){
                mPrefs.Geral.setError(e.getMessage());
                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();
            }

            return titulos;
        }

        public List<sonicTitulosHolder> selectTituloClienteSelecionado(){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            List<sonicTitulosHolder> TITULO = new ArrayList<sonicTitulosHolder>();

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
                            "t.situacao as situacao" +
                            " FROM "+ TABLE_TITULOS +
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

                TITULO.add(titulo);

            }
            cursor.close();
            return TITULO;
        }

    }

    class GrupoProduto{

        public List<sonicGrupoProdutosHolder> selectGrupo(){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            List<sonicGrupoProdutosHolder> groups = new ArrayList<sonicGrupoProdutosHolder>();

            try{

                Cursor cursor = DB.getReadableDatabase().rawQuery(
                        "SELECT " +
                                "G._id as id," +
                                "G.codigo as codigo," +
                                "G.nome as nome" +
                                " FROM " + TABLE_GRUPO_PRODUTOS +
                                " G JOIN " + TABLE_PRODUTOS +
                                " P ON P.codigo_grupo = G.codigo" +
                                " JOIN " + TABLE_EMPRESAS +
                                " E ON E.codigo = P.codigo_empresa " +
                                " WHERE E.selecionada = 1 GROUP BY G.nome " , null);

                while(cursor.moveToNext()){

                    sonicGrupoProdutosHolder group = new sonicGrupoProdutosHolder();

                    group.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    group.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
                    group.setDescricao(cursor.getString(cursor.getColumnIndex("nome")));

                    groups.add(group);

                }

                cursor.close();


            }catch (SQLiteException e){
                mPrefs.Geral.setError(e.getMessage());
                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(),e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();
            }

            return groups;

        }

    }

    class Rota {

        public long count() {

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            long count = 0;

            SQLiteDatabase db = DB.getReadableDatabase();
            try {
                count = DatabaseUtils.queryNumEntries(db, TABLE_ROTA);
            } catch (SQLiteException e) {
                mPrefs.Geral.setError(e.getMessage());
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

        public int countPorEmpresa() {

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            int count = 0;

            SQLiteDatabase db = DB.getReadableDatabase();

            try {
                Cursor cursor = db.rawQuery("SELECT R._id FROM " + TABLE_ROTA + " R WHERE R.codigo_empresa IN (SELECT e.codigo FROM "+ TABLE_EMPRESAS +" e WHERE e.selecionada = 1)", null);
                count = cursor.getCount();
            } catch (SQLiteException e) {
                DBCL.Log.saveLog(e.getStackTrace()[0].getLineNumber(), e.getMessage(), mySystem.System.getActivityName(), mySystem.System.getClassName(el), mySystem.System.getMethodNames(el));
                e.printStackTrace();
            }

            return count;
        }

        public List<sonicRotaHolder> selectRota(boolean pessoal){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            List<sonicRotaHolder> rotas = new ArrayList<>();

            String query = "SELECT " +
                        "R._id, " +
                        "R.codigo, " +
                        "R.codigo_empresa, " +
                        "(SELECT E.nome_fantasia FROM "+ TABLE_EMPRESAS +" E WHERE E.codigo = R.codigo_empresa) AS empresa, " +
                        "R.codigo_cliente, " +
                        "R.tipo, " +
                        "R.status, " +
                        "R.data_agendamento, " +
                        "R.hora_agendamento, " +
                        "R.proprietario, " +
                        "R.responsavel, " +
                        "R.ordem, " +
                        "R.observacao, " +
                        "R.data_inicio, " +
                        "R.data_fim, " +
                        "R.hora_inicio, " +
                        "R.hora_fim, " +
                        "R.situacao, " +
                        "R.negativacao, " +
                        "R.cancelamento, " +
                        "C.razao_social, " +
                        "C.nome_fantasia, " +
                        "C.endereco, " +
                        "C.bairro, " +
                        "C.municipio, " +
                        "C.uf, " +
                        "C.cep " +
                        " FROM " + TABLE_ROTA +
                        " R JOIN " + TABLE_CLIENTES + " C ON C.codigo = R.codigo_cliente" +
                        " WHERE R.proprietario=? AND R.codigo_empresa IN (SELECT E.codigo FROM "+ TABLE_EMPRESAS +" E WHERE E.selecionada=1) ORDER BY "+ (pessoal ? "R._id" : "R.codigo") +" DESC";

                try{

                    Cursor cursor = DB.getReadableDatabase().rawQuery(
                            query , new String[]{String.valueOf((pessoal ? 2 : 1))});


                        while(cursor.moveToNext()){

                            sonicRotaHolder rota = new sonicRotaHolder();

                            rota.setId(cursor.getInt(cursor.getColumnIndex("_id")));
                            rota.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
                            rota.setCodigoEmpresa(cursor.getInt(cursor.getColumnIndex("codigo_empresa")));
                            rota.setEmpresa(cursor.getString(cursor.getColumnIndex("empresa")));
                            rota.setCodigoCliente(cursor.getInt(cursor.getColumnIndex("codigo_cliente")));
                            rota.setTipo(cursor.getInt(cursor.getColumnIndex("tipo")));
                            rota.setStatus(cursor.getInt(cursor.getColumnIndex("status")));
                            rota.setDataAgendamento(cursor.getString(cursor.getColumnIndex("data_agendamento")));
                            rota.setHoraAgendamento(cursor.getString(cursor.getColumnIndex("hora_agendamento")));
                            rota.setProprietario(cursor.getInt(cursor.getColumnIndex("proprietario")));
                            rota.setResponsavel(cursor.getString(cursor.getColumnIndex("responsavel")));
                            rota.setOrdem(cursor.getInt(cursor.getColumnIndex("ordem")));
                            rota.setObservacao(cursor.getString(cursor.getColumnIndex("observacao")));
                            rota.setDataInicio(cursor.getString(cursor.getColumnIndex("data_inicio")));
                            rota.setDataFim(cursor.getString(cursor.getColumnIndex("data_fim")));
                            rota.setHoraInicio(cursor.getString(cursor.getColumnIndex("hora_inicio")));
                            rota.setHoraFim(cursor.getString(cursor.getColumnIndex("hora_fim")));
                            rota.setSituacao(cursor.getInt(cursor.getColumnIndex("situacao")));
                            rota.setNegativacao(cursor.getString(cursor.getColumnIndex("negativacao")));
                            rota.setCancelamento(cursor.getString(cursor.getColumnIndex("cancelamento")));
                            rota.setRazaoSocial(cursor.getString(cursor.getColumnIndex("razao_social")));
                            rota.setNomeFantasia(cursor.getString(cursor.getColumnIndex("nome_fantasia")));
                            rota.setLogradouro(cursor.getString(cursor.getColumnIndex("endereco")));
                            rota.setBairro(cursor.getString(cursor.getColumnIndex("bairro")));
                            rota.setMunicipio(cursor.getString(cursor.getColumnIndex("municipio")));
                            rota.setUf(cursor.getString(cursor.getColumnIndex("uf")));
                            rota.setCep(cursor.getString(cursor.getColumnIndex("cep")));

                            rotas.add(rota);

                        }
                        cursor.close();

                }catch (SQLiteException e){
                    mPrefs.Geral.setError(e.getMessage());
                    DBCL.Log.saveLog(
                            e.getStackTrace()[0].getLineNumber(),
                            e.getMessage(),
                            mySystem.System.getActivityName(),
                            mySystem.System.getClassName(el),
                            mySystem.System.getMethodNames(el));
                    e.printStackTrace();
                }
            return rotas;
        }

        public List<sonicRotaHolder> selectRotaPorCliente(int codigo){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            List<sonicRotaHolder> rotas = new ArrayList<>();

            String query = "SELECT " +
                    "R._id, " +
                    "R.codigo, " +
                    "R.codigo_empresa, " +
                    "(SELECT E.nome_fantasia FROM "+ TABLE_EMPRESAS +" E WHERE E.codigo = R.codigo_empresa) AS empresa, " +
                    "R.codigo_cliente, " +
                    "R.tipo, " +
                    "R.status, " +
                    "R.data_agendamento, " +
                    "R.hora_agendamento, " +
                    "R.proprietario, " +
                    "R.responsavel, " +
                    "R.ordem, " +
                    "R.observacao, " +
                    "R.data_inicio, " +
                    "R.data_fim, " +
                    "R.hora_inicio, " +
                    "R.hora_fim, " +
                    "R.situacao, " +
                    "R.negativacao, " +
                    "R.cancelamento, " +
                    "C.razao_social, " +
                    "C.nome_fantasia, " +
                    "C.endereco, " +
                    "C.bairro, " +
                    "C.municipio, " +
                    "C.uf, " +
                    "C.cep " +
                    " FROM " + TABLE_ROTA +
                    " R JOIN " + TABLE_CLIENTES + " C ON C.codigo = R.codigo_cliente" +
                    " WHERE C.codigo=? ORDER BY R.data_inicio DESC, R.hora_inicio DESC, R.status DESC";

            try{

                Cursor cursor = DB.getReadableDatabase().rawQuery(
                        query , new String[]{String.valueOf(codigo)});

                Log.d("QUERY ROTA", query);

                while(cursor.moveToNext()){

                    sonicRotaHolder rota = new sonicRotaHolder();

                    rota.setId(cursor.getInt(cursor.getColumnIndex("_id")));
                    rota.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
                    rota.setCodigoEmpresa(cursor.getInt(cursor.getColumnIndex("codigo_empresa")));
                    rota.setEmpresa(cursor.getString(cursor.getColumnIndex("empresa")));
                    rota.setCodigoCliente(cursor.getInt(cursor.getColumnIndex("codigo_cliente")));
                    rota.setTipo(cursor.getInt(cursor.getColumnIndex("tipo")));
                    rota.setStatus(cursor.getInt(cursor.getColumnIndex("status")));
                    rota.setDataAgendamento(cursor.getString(cursor.getColumnIndex("data_agendamento")));
                    rota.setHoraAgendamento(cursor.getString(cursor.getColumnIndex("hora_agendamento")));
                    rota.setProprietario(cursor.getInt(cursor.getColumnIndex("proprietario")));
                    rota.setResponsavel(cursor.getString(cursor.getColumnIndex("responsavel")));
                    rota.setOrdem(cursor.getInt(cursor.getColumnIndex("ordem")));
                    rota.setObservacao(cursor.getString(cursor.getColumnIndex("observacao")));
                    rota.setDataInicio(cursor.getString(cursor.getColumnIndex("data_inicio")));
                    rota.setDataFim(cursor.getString(cursor.getColumnIndex("data_fim")));
                    rota.setHoraInicio(cursor.getString(cursor.getColumnIndex("hora_inicio")));
                    rota.setHoraFim(cursor.getString(cursor.getColumnIndex("hora_fim")));
                    rota.setSituacao(cursor.getInt(cursor.getColumnIndex("situacao")));
                    rota.setNegativacao(cursor.getString(cursor.getColumnIndex("negativacao")));
                    rota.setCancelamento(cursor.getString(cursor.getColumnIndex("cancelamento")));
                    rota.setRazaoSocial(cursor.getString(cursor.getColumnIndex("razao_social")));
                    rota.setNomeFantasia(cursor.getString(cursor.getColumnIndex("nome_fantasia")));
                    rota.setLogradouro(cursor.getString(cursor.getColumnIndex("endereco")));
                    rota.setBairro(cursor.getString(cursor.getColumnIndex("bairro")));
                    rota.setMunicipio(cursor.getString(cursor.getColumnIndex("municipio")));
                    rota.setUf(cursor.getString(cursor.getColumnIndex("uf")));
                    rota.setCep(cursor.getString(cursor.getColumnIndex("cep")));

                    rotas.add(rota);

                }
                cursor.close();

            }catch (SQLiteException e){
                mPrefs.Geral.setError(e.getMessage());
                DBCL.Log.saveLog(
                        e.getStackTrace()[0].getLineNumber(),
                        e.getMessage(),
                        mySystem.System.getActivityName(),
                        mySystem.System.getClassName(el),
                        mySystem.System.getMethodNames(el));
                e.printStackTrace();
            }
            return rotas;
        }

        public List<sonicRotaHolder> selectRotaPorID(int codigo){

            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            List<sonicRotaHolder> rotas = new ArrayList<>();

            String query = "SELECT " +
                    "R._id, " +
                    "R.codigo, " +
                    "R.codigo_empresa, " +
                    "(SELECT E.nome_fantasia FROM "+ TABLE_EMPRESAS +" E WHERE E.codigo = R.codigo_empresa) AS empresa, " +
                    "R.codigo_cliente, " +
                    "R.tipo, " +
                    "R.status, " +
                    "R.data_agendamento, " +
                    "R.hora_agendamento, " +
                    "R.responsavel, " +
                    "R.ordem, " +
                    "R.observacao, " +
                    "R.data_inicio, " +
                    "R.data_fim, " +
                    "R.hora_inicio, " +
                    "R.hora_fim, " +
                    "R.situacao, " +
                    "R.negativacao, " +
                    "R.cancelamento, " +
                    "C.razao_social, " +
                    "C.nome_fantasia, " +
                    "C.endereco, " +
                    "C.bairro, " +
                    "C.municipio, " +
                    "C.uf, " +
                    "C.cep " +
                    " FROM " + TABLE_ROTA +
                    " R JOIN " + TABLE_CLIENTES + " C ON C.codigo = R.codigo_cliente WHERE "+ (mPrefs.Rota.getPessoal() ? "R._id=?" : "R.codigo=?");

            try{

                Cursor cursor = DB.getReadableDatabase().rawQuery(
                        query , new String[]{String.valueOf(codigo)});

                if(cursor!=null){

                    if(cursor.moveToFirst()){

                        sonicRotaHolder rota = new sonicRotaHolder();

                        rota.setId(cursor.getInt(cursor.getColumnIndex("_id")));
                        rota.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
                        rota.setCodigoEmpresa(cursor.getInt(cursor.getColumnIndex("codigo_empresa")));
                        rota.setEmpresa(cursor.getString(cursor.getColumnIndex("empresa")));
                        rota.setCodigoCliente(cursor.getInt(cursor.getColumnIndex("codigo_cliente")));
                        rota.setTipo(cursor.getInt(cursor.getColumnIndex("tipo")));
                        rota.setStatus(cursor.getInt(cursor.getColumnIndex("status")));
                        rota.setDataAgendamento(cursor.getString(cursor.getColumnIndex("data_agendamento")));
                        rota.setHoraAgendamento(cursor.getString(cursor.getColumnIndex("hora_agendamento")));
                        rota.setResponsavel(cursor.getString(cursor.getColumnIndex("responsavel")));
                        rota.setOrdem(cursor.getInt(cursor.getColumnIndex("ordem")));
                        rota.setObservacao(cursor.getString(cursor.getColumnIndex("observacao")));
                        rota.setDataInicio(cursor.getString(cursor.getColumnIndex("data_inicio")));
                        rota.setDataFim(cursor.getString(cursor.getColumnIndex("data_fim")));
                        rota.setHoraInicio(cursor.getString(cursor.getColumnIndex("hora_inicio")));
                        rota.setHoraFim(cursor.getString(cursor.getColumnIndex("hora_fim")));
                        rota.setSituacao(cursor.getInt(cursor.getColumnIndex("situacao")));
                        rota.setNegativacao(cursor.getString(cursor.getColumnIndex("negativacao")));
                        rota.setCancelamento(cursor.getString(cursor.getColumnIndex("cancelamento")));
                        rota.setRazaoSocial(cursor.getString(cursor.getColumnIndex("razao_social")));
                        rota.setNomeFantasia(cursor.getString(cursor.getColumnIndex("nome_fantasia")));
                        rota.setLogradouro(cursor.getString(cursor.getColumnIndex("endereco")));
                        rota.setBairro(cursor.getString(cursor.getColumnIndex("bairro")));
                        rota.setMunicipio(cursor.getString(cursor.getColumnIndex("municipio")));
                        rota.setUf(cursor.getString(cursor.getColumnIndex("uf")));
                        rota.setCep(cursor.getString(cursor.getColumnIndex("cep")));

                        rotas.add(rota);
                    }

                }
                cursor.close();

            }catch (SQLiteException e){
                mPrefs.Geral.setError(e.getMessage());
                DBCL.Log.saveLog(
                        e.getStackTrace()[0].getLineNumber(),
                        e.getMessage(),
                        mySystem.System.getActivityName(),
                        mySystem.System.getClassName(el),
                        mySystem.System.getMethodNames(el));
                e.printStackTrace();
            }

            return rotas;

        }

        public Boolean insertRota(String data, String hora){
            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            Boolean result;

            try{

                ContentValues cv = new ContentValues();
                cv.put("codigo", sonicUtils.Randomizer.generate(100000000,999999999));
                cv.put("codigo_usuario", mPrefs.Users.getUsuarioId());
                cv.put("codigo_empresa", mPrefs.Users.getEmpresaId());
                cv.put("codigo_cliente", mPrefs.Clientes.getCodigo());
                cv.put("tipo", 1); // 1=PADRÃO, 2=AGENDAMENTO, 3=REAGENDAMENTO
                cv.put("status", 1); // 1=NÃO INICIADO, 2=EM_ATENDIMENTO, 3=CONCLUIDO, 4=CANCELADO
                cv.put("data_agendamento", data);
                cv.put("hora_agendamento", hora);
                cv.put("proprietario", 2); //1=AGENDA, 2=PESSOAL
                cv.put("responsavel", mPrefs.Users.getUsuarioNome());

                result = DB.getWritableDatabase().insertOrThrow(TABLE_ROTA, null, cv)>0;

            }catch (SQLiteException e){
                mPrefs.Geral.setError(e.getMessage());
                DBCL.Log.saveLog(
                        e.getStackTrace()[0].getLineNumber(),
                        e.getMessage(),
                        mySystem.System.getActivityName(),
                        mySystem.System.getClassName(el),
                        mySystem.System.getMethodNames(el));
                e.printStackTrace();

                result = false;

            }

            return result;
        }

        public boolean iniciarRota(String codigo){
            mCalendar = Calendar.getInstance();
            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            Boolean result = false;
            ContentValues cv = new ContentValues();
            cv.put("status", 2);
            cv.put("data_inicio", data.format(mCalendar.getTime()));
            cv.put("hora_inicio", hora.format(mCalendar.getTime()));
            try{
                result = DB.getWritableDatabase().update(TABLE_ROTA, cv, (mPrefs.Rota.getPessoal() ? "_id=?" : "codigo=?"), new String[]{codigo})>0;
            }catch (SQLiteException e){
                mPrefs.Geral.setError(e.getMessage());
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

        public boolean positivarRota(String codigo, String obs){
            mCalendar = Calendar.getInstance();
            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            Boolean result = false;
            ContentValues cv = new ContentValues();
            cv.put("status", 3);
            cv.put("situacao", 1);
            cv.put("observacao", obs);
            cv.put("data_fim", data.format(mCalendar.getTime()));
            cv.put("hora_fim", hora.format(mCalendar.getTime()));

            try{
                result = DB.getWritableDatabase().update(TABLE_ROTA, cv, (mPrefs.Rota.getPessoal() ? "_id=?" : "codigo=?"), new String[]{codigo})>0;
            }catch (SQLiteException e){
                mPrefs.Geral.setError(e.getMessage());
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

        public boolean negativarRota(String codigo, String neg, String obs){
            mCalendar = Calendar.getInstance();
            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            Boolean result = false;
            ContentValues cv = new ContentValues();
            cv.put("status", 3);
            cv.put("situacao", 2);
            cv.put("negativacao", neg);
            cv.put("observacao", obs);
            cv.put("data_fim", data.format(mCalendar.getTime()));
            cv.put("hora_fim", hora.format(mCalendar.getTime()));

            try{
                result = DB.getWritableDatabase().update(TABLE_ROTA, cv, (mPrefs.Rota.getPessoal() ? "_id=?" : "codigo=?"), new String[]{codigo})>0;
            }catch (SQLiteException e){
                mPrefs.Geral.setError(e.getMessage());
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

        public boolean cancelarRota(String codigo, String canc, String obs){
            mCalendar = Calendar.getInstance();
            StackTraceElement el = Thread.currentThread().getStackTrace()[2];
            Boolean result = false;
            ContentValues cv = new ContentValues();
            cv.put("status", 4);
            cv.put("observacao", obs);
            cv.put("cancelamento", canc);
            cv.put("data_inicio", data.format(mCalendar.getTime()));
            cv.put("hora_inicio", hora.format(mCalendar.getTime()));

            try{
                result = DB.getWritableDatabase().update(TABLE_ROTA, cv, (mPrefs.Rota.getPessoal() ? "_id=?" : "codigo=?"), new String[]{codigo})>0;
            }catch (SQLiteException e){
                mPrefs.Geral.setError("Não foi possível executar a operação.\n\nDetalhe -> "+e.getMessage());
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

    }

    class TabelaPreco{

    }

    class TipoCobranca{

    }

    class CondicoesPagamento{


    }

    class Transportadora{

    }

    class UnidadeMedida{

    }

    class TipoPedido{

    }

    class TipoDocumento{

    }

    class Desconto{

    }

    class Prazo{

    }

    class TabelaPrecoCliente{

    }

    class Mensagem{

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

    }

}
