package com.softhex.sonic;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrador on 10/07/2017.
 */

public class sonicDatabase extends SQLiteOpenHelper{

    private static final String DATABASE = sonicConstants.DATABASE;
    private static final String DB_SITE = sonicConstants.TB_SITE;
    private static final String DB_FTP = sonicConstants.TB_FTP;
    private static final String DB_EMPRESAS = sonicConstants.TB_EMPRESAS;
    private static final String DB_NIVEL_ACESSO = sonicConstants.TB_NIVEL_ACESSO;
    private static final String DB_USUARIOS = sonicConstants.TB_USUARIOS;
    private static final String DB_EMPRESAS_USUARIOS = sonicConstants.TB_EMPRESAS_USUARIOS;
    private static final String DB_HISTORICO_USUARIO = sonicConstants.TB_HISTORICO_USUARIO;
    private static final String DB_CLIENTES = sonicConstants.TB_CLIENTES;
    private static final String DB_EMPRESAS_CLIENTES = sonicConstants.TB_EMPRESAS_CLIENTES;
    private static final String DB_GRUPO_CLIENTES = sonicConstants.TB_GRUPO_CLIENTES;
    private static final String DB_RANKING_CLIENTES = sonicConstants.TB_RANKING_CLIENTES;
    private static final String DB_CLIENTES_SEM_COMPRA = sonicConstants.TB_CLIENTES_SEM_COMPRA;
    private static final String DB_PRODUTOS = sonicConstants.TB_PRODUTOS;
    private static final String DB_GRUPO_PRODUTOS = sonicConstants.TB_GRUPO_PRODUTOS;
    private static final String DB_RANKING_PRODUTOS = sonicConstants.TB_RANKING_PRODUTOS;
    private static final String DB_ESTOQUE = sonicConstants.TB_ESTOQUE;
    private static final String DB_TABELA_PRECO = sonicConstants.TB_TABELA_PRECO;
    private static final String DB_FINANCEIRO = sonicConstants.TB_FINANCEIRO;
    private static final String DB_TITTULOS = sonicConstants.TB_TITULOS;
    private static final String DB_RETORNO_PEDIDO = sonicConstants.TB_RETORNO_PEDIDO;
	private static final String DB_RETORNO_PEDIDO_ITENS = sonicConstants.TB_RETORNO_PEDIDO_ITENS;
    private static final String DB_TIPO_COBRANCA = sonicConstants.TB_TIPO_COBRANCA;
    private static final String DB_CONDICOES_PAGAMENTO = sonicConstants.TB_CONDICOES_PAGAMENTO;
    private static final String DB_TABELA_PRECO_PRODUTO = sonicConstants.TB_TABELA_PRECO_PRODUTO;
    private static final String DB_VENDAS = sonicConstants.TB_VENDAS;
    private static final String DB_VENDAS_ITENS = sonicConstants.TB_VENDAS_ITENS;
    private static final String DB_TRANSPORTADORA = sonicConstants.TB_TRANSPORTADORA;
    private static final String DB_UNIDADE_MEDIDA = sonicConstants.TB_UNIDADE_MEDIDA;
    private static final String DB_TIPO_PEDIDO = sonicConstants.TB_TIPO_PEDIDO;
    private static final String DB_TIPO_DOCUMENTO = sonicConstants.TB_TIPO_DOCUMENTO;
    private static final String DB_DESCONTO = sonicConstants.TB_DESCONTO;
    private static final String DB_FORMA_PAGAMENTO = sonicConstants.TB_FORMA_PAGAMENTO;
    private static final String DB_PRAZO = sonicConstants.TB_PRAZO;
    private static final String DB_TABELA_PRECO_CLIENTE = sonicConstants.TB_TABELA_PRECO_CLIENTE;
    private static final String DB_FRETE = sonicConstants.TB_FRETE;
    private static final String DB_IMPRESSORAS = sonicConstants.TB_IMPRESSORAS;
    private static final String DB_AVISOS = sonicConstants.TB_AVISOS;
    private static final String DB_AVISOS_LIDOS = sonicConstants.TB_AVISOS_LIDOS;
    private static final String DB_SINCRONIZACAO = sonicConstants.TB_SINCRONIZACAO;
    private static final String DB_LOCALIZACAO = sonicConstants.TB_LOCALIZACAO;
    private static final String DB_LOG_ERRO = sonicConstants.TB_LOG_ERRO;
    private Context myCtx;

    private static final String CREATE_TABLE_SITE = "CREATE TABLE IF NOT EXISTS "+DB_SITE+" (" +
            "_id integer primary key autoincrement, " +
            "site string not null, " +
            "licensa string);";

    private static final String CREATE_TABLE_FTP = "CREATE TABLE IF NOT EXISTS "+DB_FTP+" (" +
            "_id integer primary key autoincrement, " +
            "ftp string not null, " +
            "user string not null, " +
            "pass string);";

    private static final String CREATE_TABLE_EMPRESA = "CREATE TABLE IF NOT EXISTS "+DB_EMPRESAS+" (" +
            "_id integer primary key autoincrement, " +
            "codigo int not null, " +
            "razao_social string not null, " +
            "nome_fantasia string not null," +
            "selecionada int);";
    private static final String CREATE_INDEX_TABLE_EMPRESA_CODIGO = "CREATE INDEX index_empresa_codigo ON "+DB_EMPRESAS+" (codigo);";
    private static final String CREATE_INDEX_TABLE_EMPRESA_SELECIONADO = "CREATE INDEX index_empresa_selecionada ON "+DB_EMPRESAS+" (selecionada);";

    private static final String CREATE_TABLE_NIVEL_ACESSO = "CREATE TABLE IF NOT EXISTS "+DB_NIVEL_ACESSO+" (" +
            "_id integer primary key autoincrement, " +
            "nivel int not null, " +
            "nome string not null);";
    private static final String CREATE_INDEX_TABLE_NIVEL_ACESSO = "CREATE INDEX index_nivel ON "+DB_NIVEL_ACESSO+" (nivel);";

    private static final String CREATE_TABLE_USUARIOS = "CREATE TABLE IF NOT EXISTS "+DB_USUARIOS+" (" +
            "_id integer primary key autoincrement, "+
            "codigo int not null, " +
            "nome string not null, " +
            "login string not null, " +
            "senha string, " +
            "imei string not null, " +
            "nivel_acesso int not null, " +
            "usuario_superior int, " +
            "ativo bit);";
    private static final String CREATE_INDEX_TABLE_USUARIOS_CODIGO = "CREATE INDEX index_usuario_codigo ON "+DB_USUARIOS+" (codigo);";
    private static final String CREATE_INDEX_TABLE_USUARIOS_NIVEL_ACESSO = "CREATE INDEX index_usuario_nivel_acesso ON "+DB_USUARIOS+" (nivel_acesso);";
    private static final String CREATE_INDEX_TABLE_USUARIOS_USUARIO_SUPERIOR = "CREATE INDEX index_usuario_usuario_superior ON "+DB_USUARIOS+" (usuario_superior);";
    private static final String CREATE_INDEX_TABLE_USUARIOS_ATIVO = "CREATE INDEX index_usuario_ativo ON "+DB_USUARIOS+" (ativo);";

    private static final String CREATE_TABLE_HISTORICO_USUARIO = "CREATE TABLE IF NOT EXISTS "+DB_HISTORICO_USUARIO+" (" +
            "_id integer primary key autoincrement, "+
            "codigo_empresa int, " +
            "codigo_usuario int, " +
            "mes1 string, " +
            "valor1 decimal(9,2), " +
            "mes2 string, " +
            "valor2 decimal(9,2), " +
            "mes3 string, " +
            "valor3 decimal(9,2), " +
            "mes4 string, " +
            "valor4 decimal(9,2), " +
            "mes5 string, " +
            "valor5 decimal(9,2), " +
            "mes6 string, " +
            "valor6 decimal(9,2));";
    private static final String CREATE_INDEX_TABLE_HISTORICO_USUARIO_CODIGO_EMPRESA = "CREATE INDEX index_historico_usuario_codigo_empresa ON "+DB_HISTORICO_USUARIO+" (codigo_empresa);";
    private static final String CREATE_INDEX_TABLE_HISTORICO_USUARIO_CODIGO_USUARIO = "CREATE INDEX index_historico_usuario_codigo_usuario ON "+DB_HISTORICO_USUARIO+" (codigo_usuario);";

    private static final String CREATE_TABLE_EMPRESAS_USUARIO = "CREATE TABLE IF NOT EXISTS "+DB_EMPRESAS_USUARIOS+" (" +
            "_id integer primary key autoincrement, "+
            "codigo_usuario int, " +
            "codigo_empresa int," +
            "mvenda string," +
			"mvisita int);";
    private static final String CREATE_INDEX_TABLE_EMPRESAS_USUARIO_CODIGO_USUARIO = "CREATE INDEX index_empresas_usuario_codigo_usuario ON "+DB_EMPRESAS_USUARIOS+" (codigo_usuario);";
    private static final String CREATE_INDEX_TABLE_EMPRESAS_USUARIO_CODIGO_EMPRESA = "CREATE INDEX index_empresas_usuario_codigo_empresa ON "+DB_EMPRESAS_USUARIOS+" (codigo_empresa);";

    private static final String CREATE_TABLE_CLIENTES = "CREATE TABLE IF NOT EXISTS "+DB_CLIENTES+" (" +
            "_id integer primary key autoincrement, " +
            "codigo int not null, " +
            "tipo character(1) not null, " +
            "razao_social string not null, " +
            "nome_fantasia string not null, " +
            "cpf_cnpj string not null, " +
            "insc_estadual string, " +
            "endereco string not null, " +
            "bairro string not null, " +
            "municipio string not null, " +
            "uf character(2) not null, " +
            "cep character(10), " +
            "fone string, " +
            "contato string, " +
            "email string, " +
            "observacao string, " +
            "data_cadastro string, " +
            "codigo_grupo int not null, " +
			"situacao int, " +
            "selecionado int);";
    private static final String CREATE_INDEX_TABLE_CLIENTES_CODIGO = "CREATE INDEX index_clientes_codigo ON "+DB_CLIENTES+" (codigo);";
    private static final String CREATE_INDEX_TABLE_CLIENTES_CODIGO_GRUPO = "CREATE INDEX index_clientes_codigo_grupo ON "+DB_CLIENTES+" (codigo_grupo);";
    private static final String CREATE_INDEX_TABLE_CLIENTES_SITUACAO = "CREATE INDEX index_clientes_situacao ON "+DB_CLIENTES+" (situacao);";
    private static final String CREATE_INDEX_TABLE_CLIENTES_SELECIONADO = "CREATE INDEX index_clientes_selecionado ON "+DB_CLIENTES+" (selecionado);";

    private static final String CREATE_TABLE_EMPRESAS_CLIENTES = "CREATE TABLE IF NOT EXISTS "+DB_EMPRESAS_CLIENTES+" (" +
            "_id integer primary key autoincrement, "+
            "codigo_empresa int, " +
            "codigo_cliente int);";
    private static final String CREATE_INDEX_TABLE_EMPRESAS_CLIENTES_CODIGO_EMPRESA = "CREATE INDEX index_empresas_clientes_codigo_empresa ON "+DB_EMPRESAS_CLIENTES+" (codigo_empresa);";
    private static final String CREATE_INDEX_TABLE_EMPRESAS_CLIENTES_CODIGO_CLIENTE = "CREATE INDEX index_empresas_clientes_codigo_cliente ON "+DB_EMPRESAS_CLIENTES+" (codigo_cliente);";

    private static final String CREATE_TABLE_GRUPO_CLIENTES = "CREATE TABLE IF NOT EXISTS "+DB_GRUPO_CLIENTES+" (" +
            "_id integer primary key autoincrement, " +
            "codigo int, " +
            "nome string);";
    private static final String CREATE_INDEX_TABLE_GRUPO_CLIENTES_CODIGO = "CREATE INDEX index_grupo_clientes_codigo ON "+DB_GRUPO_CLIENTES+" (codigo);";

    private static final String CREATE_TABLE_RANKING_CLIENTES = "CREATE TABLE IF NOT EXISTS "+DB_RANKING_CLIENTES+" (" +
            "_id integer primary key autoincrement, " +
            "codigo_cliente int, " +
            "codigo_empresa int, " +
            "valor decimal(9,2), " +
            "pedidos int, " +
            "valor_anterior decimal(9,2), " +
            "atuacao decimal(9,2));";
    private static final String CREATE_INDEX_TABLE_RANKING_CLIENTES_CODIGO_CLIENTE = "CREATE INDEX index_ranking_clientes_codigo_cliente ON "+DB_RANKING_CLIENTES+" (codigo_cliente);";
    private static final String CREATE_INDEX_TABLE_RANKING_CLIENTES_CODIGO_EMPRESA = "CREATE INDEX index_ranking_clientes_codigo_empresa ON "+DB_RANKING_CLIENTES+" (codigo_empresa);";

    private static final String CREATE_TABLE_CLIENTES_SEM_COMPRA = "CREATE TABLE IF NOT EXISTS "+DB_CLIENTES_SEM_COMPRA+" (" +
            "_id integer primary key autoincrement, " +
            "codigo_cliente int, " +
            "dias_sem_compra int);";
    private static final String CREATE_INDEX_TABLE_CLIENTES_SEM_COMPRA_CODIGO_CLIENTE = "CREATE INDEX index_clientes_sem_compra_codigo_cliente ON "+DB_CLIENTES_SEM_COMPRA+" (codigo_cliente);";

    private static final String CREATE_TABLE_PRODUTOS = "CREATE TABLE IF NOT EXISTS "+DB_PRODUTOS+" (" +
            "_id integer primary key autoincrement, " +
            "codigo int, " +
            "codigo_alternativo string, " +
            "descricao string, " +
            "codigo_unidade int, " +
            "codigo_grupo int," +
            "codigo_empresa int," +
            "selecionado int);";
    private static final String CREATE_INDEX_TABLE_PRODUTOS_CODIGO = "CREATE INDEX index_produtos_codigo ON "+DB_PRODUTOS+" (codigo);";
    private static final String CREATE_INDEX_TABLE_PRODUTOS_CODIGO_UNIDADE = "CREATE INDEX index_produtos_codigo_unidade ON "+DB_PRODUTOS+" (codigo_unidade);";
    private static final String CREATE_INDEX_TABLE_PRODUTOS_CODIGO_GRUPO = "CREATE INDEX index_produtos_codigo_grupo ON "+DB_PRODUTOS+" (codigo_grupo);";
    private static final String CREATE_INDEX_TABLE_PRODUTOS_CODIGO_EMPRESA = "CREATE INDEX index_produtos_codigo_empresa ON "+DB_PRODUTOS+" (codigo_empresa);";

    private static final String CREATE_TABLE_GRUPO_PRODUTOS = "CREATE TABLE IF NOT EXISTS "+DB_GRUPO_PRODUTOS+" (" +
            "_id integer primary key autoincrement, " +
            "codigo int, " +
            "nome string);";
    private static final String CREATE_INDEX_TABLE_GRUPO_PRODUTOS_CODIGO = "CREATE INDEX index_grupo_produtos_codigo ON "+DB_GRUPO_PRODUTOS+" (codigo);";

    private static final String CREATE_TABLE_ESTOQUE_PRODUTOS = "CREATE TABLE IF NOT EXISTS "+ DB_ESTOQUE +" (" +
            "_id integer primary key autoincrement, " +
            "codigo_empresa int, " +
            "codigo_produto int, " +
            "estoque int, " +
            "estoque_min int, " +
            "estoque_max int," +
            "situacao string);";

    private static final String CREATE_TABLE_VENDAS = "CREATE TABLE IF NOT EXISTS "+DB_VENDAS+" (" +
            "_id integer primary key autoincrement, " +
            "codigo int not null, " +
            "codigo_empresa int not null, " +
            "codigo_cliente int not null, " +
            "codigo_tipo_cobranca int not null, " +
            "codigo_prazo int not null," +
            "data string," +
            "valor decimal(9,2));";

    private static final String CREATE_TABLE_VENDAS_ITENS = "CREATE TABLE IF NOT EXISTS "+DB_VENDAS_ITENS+" (" +
            "_id integer primary key autoincrement, " +
            "codigo_venda int not null, " +
            "codigo_item int not null, " +
            "codigo_produto int not null, " +
            "codigo_unidade int not null, " +
            "quantidade decimal(10)," +
            "preco decimal(9,2)," +
            "valor decimal(9,2));";

    private static final String CREATE_TABLE_RANKING_PRODUTOS = "CREATE TABLE IF NOT EXISTS "+DB_RANKING_PRODUTOS+" (" +
            "_id integer primary key autoincrement, " +
            "codigo_produto int, " +
            "quantidade decimal(9,2), " +
            "quantidade_anterior decimal(9,2), " +
            "pedidos int, " +
            "atuacao decimal(9,2), " +
            "codigo_unidade int, " +
            "codigo_grupo int);";

    private static final String CREATE_TABLE_FINANCEIRO = "CREATE TABLE IF NOT EXISTS "+DB_FINANCEIRO+" (" +
            "_id integer primary key autoincrement, " +
            "codigo_cliente int, " +
            "limite_credito decimal(9,2), " +
            "saldo decimal(9,2), " +
            "maior_compra decimal(9,2), " +
            "data_maior_compra string, " +
            "ultima_compra decimal(9,2), " +
            "data_ultima_compra string);";


    private static final String CREATE_TABLE_TITULOS = "CREATE TABLE IF NOT EXISTS "+DB_TITTULOS+" (" +
            "_id integer primary key autoincrement, " +
            "codigo int, " +
            "codigo_cliente int, " +
            "numero string, " +
            "data_emissao string, " +
            "data_vencimento string, " +
            "dias_atraso int, " +
            "valor decimal(9,2), " +
			"saldo decimal(9,2), " +
            "situacao int);";

    private static final String CREATE_TABLE_RETORNO_PEDIDO = "CREATE TABLE IF NOT EXISTS "+DB_RETORNO_PEDIDO+" (" +
            "_id integer primary key autoincrement, " +
            "codigo_cliente int, " +
            "codigo_pedido int, " +
            "numero_pedido_mobile string, " +
            "data_pedido string, " +
            "valor_pedido decimal(9,2), " +
			"valor_desconto decimal(9,2), " +
            "codigo_tipo_pedido int," +
            "codigo_prazo int," +
            "situacao int," +
			"situacao_cor string);";
			
	private static final String CREATE_TABLE_RETORNO_PEDIDO_ITENS = "CREATE TABLE IF NOT EXISTS "+DB_RETORNO_PEDIDO_ITENS+" (" +
            "_id integer primary key autoincrement, " +
            "codigo_pedido int, " +
            "codigo_produto int, " +
            "codigo_unidade int," +
			"quantidade int," +
            "valor decimal(9,2), " +
			"valor_desconto decimal(9,2));";

    private static final String CREATE_TABLE_TIPO_COBRANCA = "CREATE TABLE IF NOT EXISTS "+DB_TIPO_COBRANCA+" (" +
            "_id integer primary key autoincrement, " +
            "codigo int, " +
            "nome string);";

    private static final String CREATE_TABLE_CONDICOES_PAGAMENTO = "CREATE TABLE IF NOT EXISTS "+DB_CONDICOES_PAGAMENTO+" (" +
            "_id integer primary key autoincrement, " +
            "codigo int, " +
            "nome string);";

    private static final String CREATE_TABLE_TABELA_PRECO = "CREATE TABLE IF NOT EXISTS "+DB_TABELA_PRECO+" (" +
            "_id integer primary key autoincrement, " +
            "codigo int, " +
            "nome string);";

    private static final String CREATE_TABLE_TABELA_PRECO_PRODUTO = "CREATE TABLE IF NOT EXISTS "+DB_TABELA_PRECO_PRODUTO+" (" +
            "_id integer primary key autoincrement, " +
            "codigo_tabela_preco int, " +
            "codigo_empresa int, " +
            "preco decimal(9,2), " +
            "preco_promocao decimal(9,2), " +
            "desconto_geral int, " +
            "desconto_item_min int, " +
            "desconto_item_max int);";


    private static final String CREATE_TABLE_TRANSPORTADORA = "CREATE TABLE IF NOT EXISTS "+DB_TRANSPORTADORA+" (" +
            "_id integer primary key autoincrement, " +
            "codigo int, " +
            "nome string);";

    private static final String CREATE_TABLE_UNIDADE_MEDIDA = "CREATE TABLE IF NOT EXISTS "+DB_UNIDADE_MEDIDA+" (" +
            "_id integer primary key autoincrement, " +
            "codigo int, " +
            "nome string," +
            "sigla char(3));";

    private static final String CREATE_TABLE_TIPO_PEDIDO = "CREATE TABLE IF NOT EXISTS "+DB_TIPO_PEDIDO+" (" +
            "_id integer primary key autoincrement, " +
            "codigo int, " +
            "nome string);";

    private static final String CREATE_TABLE_TIPO_DOCUMENTO = "CREATE TABLE IF NOT EXISTS "+DB_TIPO_DOCUMENTO+" (" +
            "_id integer primary key autoincrement, " +
            "codigo int, " +
            "nome string);";

    private static final String CREATE_TABLE_DESCONTO = "CREATE TABLE IF NOT EXISTS "+DB_DESCONTO+" (" +
            "_id integer primary key autoincrement, " +
            "codigo_vendedor int, " +
            "perc_desconto decimal(9,2));";

    private static final String CREATE_TABLE_PRAZO = "CREATE TABLE IF NOT EXISTS "+DB_PRAZO+" (" +
            "_id integer primary key autoincrement, " +
            "codigo int, " +
            "nome string, " +
            "prazo_venda int);";

    private static final String CREATE_TABLE_PRECO_CLIENTE = "CREATE TABLE IF NOT EXISTS "+DB_TABELA_PRECO_CLIENTE+" (" +
            "_id integer primary key autoincrement, " +
            "codigo_tabela int, " +
            "condicao_cliente);";

    private static final String CREATE_TABLE_IMPRESSORAS = "CREATE TABLE IF NOT EXISTS "+DB_IMPRESSORAS+" (" +
            "_id integer primary key autoincrement, " +
            "codigo_tabela int, " +
            "condicao_cliente);";

    private static final String CREATE_TABLE_AVISOS = "CREATE TABLE IF NOT EXISTS "+DB_AVISOS+" (" +
            "_id integer primary key autoincrement, " +
            "codigo int, " +
            "autor string, " +
            "data string, " +
            "hora string, " +
            "prioridade int, " +
            "titulo string, " +
            "mensagem string, " +
            "status int);";

    private static final String CREATE_TABLE_AVISOS_LIDOS = "CREATE TABLE IF NOT EXISTS "+DB_AVISOS_LIDOS+" (" +
            "_id integer primary key autoincrement, " +
            "codigo int);";

    private static final String CREATE_TABLE_SINCRONIZACAO = "CREATE TABLE IF NOT EXISTS "+DB_SINCRONIZACAO+" (" +
            "_id integer primary key autoincrement, " +
            "tabela string, " +
            "tipo string, " +
            "data_sinc string, " +
            "hora_sinc string);";

    private static final String CREATE_TABLE_LOG_ERRO = "CREATE TABLE IF NOT EXISTS "+DB_LOG_ERRO+" (" +
            "_id integer primary key autoincrement, " +
            "android_manufacturer string,"+
            "android_model string,"+
            "android_sdk int,"+
            "android_name string,"+
            "android_release string,"+
            "activity string,"+
            "class string,"+
            "method string,"+
            "line int,"+
            "log string, " +
            "data string, " +
            "hora string);";

    private static final String CREATE_TABLE_LOCALIZACAO = "CREATE TABLE IF NOT EXISTS "+DB_LOCALIZACAO+" (" +
            "_id integer primary key autoincrement, " +
            "codigo_vendedor int, " +
            "data string, " +
            "hora string, " +
            "latitude string, " +
            "longitude string);";

    private static final String DROP_TABLE = " DROP TABLE [IF EXISTS] ";
    private static final int DB_VERSION = 2;

    public sonicDatabase(Context context) {
        //super(context, Environment.getExternalStorageDirectory().getPath()+sonicConstants.LOCAL_DATA+DATABASE+".db" , null, DB_VERSION);
        super(context, DATABASE , null, DB_VERSION);
        this.myCtx = context;
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {

        DB.execSQL(CREATE_TABLE_SITE);
        DB.execSQL(CREATE_TABLE_FTP);
        DB.execSQL(CREATE_TABLE_EMPRESA);
        DB.execSQL(CREATE_INDEX_TABLE_EMPRESA_CODIGO);
        DB.execSQL(CREATE_INDEX_TABLE_EMPRESA_SELECIONADO);
        DB.execSQL(CREATE_TABLE_NIVEL_ACESSO);
        DB.execSQL(CREATE_INDEX_TABLE_NIVEL_ACESSO);
        DB.execSQL(CREATE_TABLE_USUARIOS);
        DB.execSQL(CREATE_INDEX_TABLE_USUARIOS_CODIGO);
        DB.execSQL(CREATE_INDEX_TABLE_USUARIOS_NIVEL_ACESSO);
        DB.execSQL(CREATE_INDEX_TABLE_USUARIOS_USUARIO_SUPERIOR);
        DB.execSQL(CREATE_INDEX_TABLE_USUARIOS_ATIVO);
        DB.execSQL(CREATE_TABLE_HISTORICO_USUARIO);
        DB.execSQL(CREATE_INDEX_TABLE_HISTORICO_USUARIO_CODIGO_USUARIO);
        DB.execSQL(CREATE_INDEX_TABLE_HISTORICO_USUARIO_CODIGO_EMPRESA);
        DB.execSQL(CREATE_TABLE_EMPRESAS_USUARIO);
        DB.execSQL(CREATE_INDEX_TABLE_EMPRESAS_USUARIO_CODIGO_USUARIO);
        DB.execSQL(CREATE_INDEX_TABLE_EMPRESAS_USUARIO_CODIGO_EMPRESA);
        DB.execSQL(CREATE_TABLE_CLIENTES);
        DB.execSQL(CREATE_INDEX_TABLE_CLIENTES_CODIGO);
        DB.execSQL(CREATE_INDEX_TABLE_CLIENTES_CODIGO_GRUPO);
        DB.execSQL(CREATE_INDEX_TABLE_CLIENTES_SELECIONADO);
        DB.execSQL(CREATE_INDEX_TABLE_CLIENTES_SITUACAO);
        DB.execSQL(CREATE_TABLE_EMPRESAS_CLIENTES);
        DB.execSQL(CREATE_INDEX_TABLE_EMPRESAS_CLIENTES_CODIGO_CLIENTE);
        DB.execSQL(CREATE_INDEX_TABLE_EMPRESAS_CLIENTES_CODIGO_EMPRESA);
        DB.execSQL(CREATE_TABLE_GRUPO_CLIENTES);
        DB.execSQL(CREATE_INDEX_TABLE_GRUPO_CLIENTES_CODIGO);
        DB.execSQL(CREATE_TABLE_RANKING_CLIENTES);
        DB.execSQL(CREATE_INDEX_TABLE_RANKING_CLIENTES_CODIGO_CLIENTE);
        DB.execSQL(CREATE_INDEX_TABLE_RANKING_CLIENTES_CODIGO_EMPRESA);
        DB.execSQL(CREATE_TABLE_CLIENTES_SEM_COMPRA);
        DB.execSQL(CREATE_INDEX_TABLE_CLIENTES_SEM_COMPRA_CODIGO_CLIENTE);
        DB.execSQL(CREATE_TABLE_PRODUTOS);
        DB.execSQL(CREATE_INDEX_TABLE_PRODUTOS_CODIGO);
        DB.execSQL(CREATE_INDEX_TABLE_PRODUTOS_CODIGO_UNIDADE);
        DB.execSQL(CREATE_INDEX_TABLE_PRODUTOS_CODIGO_GRUPO);
        DB.execSQL(CREATE_INDEX_TABLE_PRODUTOS_CODIGO_EMPRESA);
        DB.execSQL(CREATE_TABLE_GRUPO_PRODUTOS);
        DB.execSQL(CREATE_INDEX_TABLE_GRUPO_PRODUTOS_CODIGO);
        DB.execSQL(CREATE_TABLE_ESTOQUE_PRODUTOS);
        DB.execSQL(CREATE_TABLE_RANKING_PRODUTOS);
        DB.execSQL(CREATE_TABLE_TABELA_PRECO);
        DB.execSQL(CREATE_TABLE_VENDAS);
        DB.execSQL(CREATE_TABLE_VENDAS_ITENS);
        DB.execSQL(CREATE_TABLE_TABELA_PRECO);
        //DB.execSQL(CREATE_TABLE_TABELA_PRECO_PRODUTO);
        //DB.execSQL(CREATE_TABLE_TABELA_PRECO_CLIENTE);
        DB.execSQL(CREATE_TABLE_FINANCEIRO);
        DB.execSQL(CREATE_TABLE_TITULOS);
        DB.execSQL(CREATE_TABLE_RETORNO_PEDIDO);
		DB.execSQL(CREATE_TABLE_RETORNO_PEDIDO_ITENS);
        DB.execSQL(CREATE_TABLE_TIPO_COBRANCA);
        //DB.execSQL(CREATE_TABLE_CONDICOES_PAGAMENTO);
        //DB.execSQL(CREATE_TABLE_TRANSPORTADORA);
        DB.execSQL(CREATE_TABLE_UNIDADE_MEDIDA);
        DB.execSQL(CREATE_TABLE_TIPO_PEDIDO);
        //DB.execSQL(CREATE_TABLE_TIPO_DOCUMENTO);
        //DB.execSQL(CREATE_TABLE_DESCONTO);
        DB.execSQL(CREATE_TABLE_PRAZO);

        DB.execSQL(CREATE_TABLE_AVISOS);
        DB.execSQL(CREATE_TABLE_AVISOS_LIDOS);
        DB.execSQL(CREATE_TABLE_SINCRONIZACAO);
        DB.execSQL(CREATE_TABLE_LOCALIZACAO);
        DB.execSQL(CREATE_TABLE_LOG_ERRO);

    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int oldVersion, int newVersion) {
        /*DB.execSQL(DROP_TABLE+DB_SITE);
        DB.execSQL(DROP_TABLE+DB_FTP);
        DB.execSQL(DROP_TABLE+DB_EMPRESA);
        DB.execSQL(DROP_TABLE+DB_USUARIO);
        DB.execSQL(DROP_TABLE+DB_CLIENTES);
        DB.execSQL(DROP_TABLE+DB_VENDEDOR);
        DB.execSQL(DROP_TABLE+DB_EMPRESAS_VENDEDORES);
        DB.execSQL(DROP_TABLE+DB_PRODUTOS);
        DB.execSQL(DROP_TABLE+DB_TABELA_PRECO);
        //DB.execSQL(CREATE_TABLE_TABELA_PRECO_PRODUTO);
        //DB.execSQL(CREATE_TABLE_TABELA_PRECO_CLIENTE);
        DB.execSQL(DROP_TABLE+DB_ESTOQUE);
        DB.execSQL(DROP_TABLE+DB_FINANCEIRO);
        DB.execSQL(DROP_TABLE+DB_TITTULOS);
        DB.execSQL(DROP_TABLE+DB_RETORNO_PEDIDO);
        DB.execSQL(DROP_TABLE+DB_RETORNO_PEDIDO_ITENS);
        DB.execSQL(DROP_TABLE+DB_GRUPO_PRODUTO);
        //DB.execSQL(CREATE_TABLE_TIPO_COBRANCA);
        //DB.execSQL(CREATE_TABLE_CONDICOES_PAGAMENTO);
        //DB.execSQL(CREATE_TABLE_TRANSPORTADORA);
        DB.execSQL(DROP_TABLE+DB_UNIDADE_MEDIDA);
        DB.execSQL(DROP_TABLE+DB_TIPO_PEDIDO);
        //DB.execSQL(CREATE_TABLE_TIPO_DOCUMENTO);
        //DB.execSQL(CREATE_TABLE_DESCONTO);
        DB.execSQL(DROP_TABLE+DB_PRAZO);

        //DB.execSQL(CREATE_TABLE_MENSAGEM);
        DB.execSQL(DROP_TABLE+DB_SINCRONIZACAO);
        DB.execSQL(DROP_TABLE+DB_LOCALIZACAO);
        DB.execSQL(DROP_TABLE+DB_LOG_ERRO);*/
        onCreate(DB);
    }


    public String exportDb() {

        String result = "";

        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        File dataDirectory = Environment.getDataDirectory();

        FileChannel source = null;
        FileChannel destination = null;

        String currentDBPath = "/data/" + myCtx.getApplicationInfo().packageName + "/databases/"+DATABASE;
        String backupDBPath = sonicConstants.LOCAL_DATA_BACKUP+"Sonic-bkp-"+(new SimpleDateFormat("dd-MM-yyy-HHmm").format(new Date()))+".db";
        File currentDB = new File(dataDirectory, currentDBPath);
        File backupDB = new File(externalStorageDirectory, backupDBPath);

        try {
            source = new FileInputStream(currentDB).getChannel();
            destination = new FileOutputStream(backupDB).getChannel();
            destination.transferFrom(source, 0, source.size());

           result = "Backup salvo em: "+backupDBPath;

        } catch (IOException e) {
            result = e.getMessage();
            e.printStackTrace();
        } finally {
            try {
                if (source != null) source.close();
            } catch (IOException e) {
                result = e.getMessage();
                e.printStackTrace();
            }
            try {
                if (destination != null) destination.close();
            } catch (IOException e) {
                result = e.getMessage();
                e.printStackTrace();
            }
        }
        return  result;
    }

}
