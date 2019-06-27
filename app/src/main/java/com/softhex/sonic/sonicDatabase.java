package com.softhex.sonic;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

/**
 * Created by Administrador on 10/07/2017.
 */

public class sonicDatabase extends SQLiteOpenHelper{

    private static final String DATABASE = sonicConstants.DATABASE;
    private static final String DB_SITE = sonicConstants.TB_SITE;
    private static final String DB_FTP = sonicConstants.TB_FTP;
    private static final String DB_EMPRESA = sonicConstants.TB_EMPRESA;
    private static final String DB_USUARIO = sonicConstants.TB_USUARIO;
    private static final String DB_VENDEDOR = sonicConstants.TB_VENDEDOR;
    private static final String DB_HISTORICO_VENDEDOR = sonicConstants.TB_HISTORICO_VENDEDOR;
    private static final String DB_EMPRESAS_VENDEDORES = sonicConstants.TB_EMPRESAS_VENDEDORES;
    private static final String DB_CLIENTES = sonicConstants.TB_CLIENTES;
    private static final String DB_GRUPO_CLIENTES = sonicConstants.TB_GRUPO_CLIENTES;
    private static final String DB_RANKING_CLIENTES = sonicConstants.TB_RANKING_CLIENTES;
    private static final String DB_CLIENTES_SEM_COMPRA = sonicConstants.TB_CLIENTES_SEM_COMPRA;
    private static final String DB_PRODUTOS = sonicConstants.TB_PRODUTOS;
    private static final String DB_GRUPO_PRODUTOS = sonicConstants.TB_GRUPO_PRODUTOS;
    private static final String DB_RANKING_PRODUTOS = sonicConstants.TB_RANKING_PRODUTOS;
    private static final String DB_ESTOQUE_PRODUTOS = sonicConstants.TB_ESTOQUE_PRODUTOS;
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
    private static final String DB_AVISOS = sonicConstants.TB_AVISOS;
    private static final String DB_AVISOS_LIDOS = sonicConstants.TB_AVISOS_LIDOS;
    private static final String DB_SINCRONIZACAO = sonicConstants.TB_SINCRONIZACAO;
    private static final String DB_LOCALIZACAO = sonicConstants.TB_LOCALIZACAO;
    private static final String DB_LOG_ERRO = sonicConstants.TB_LOG_ERRO;

    private static final String CREATE_TABLE_SITE = "CREATE TABLE IF NOT EXISTS "+DB_SITE+" (" +
            "_id integer primary key autoincrement, " +
            "site string not null, " +
            "licensa string);";

    private static final String CREATE_TABLE_FTP = "CREATE TABLE IF NOT EXISTS "+DB_FTP+" (" +
            "_id integer primary key autoincrement, " +
            "ftp string not null, " +
            "user string not null, " +
            "pass string);";

    private static final String CREATE_TABLE_EMPRESA = "CREATE TABLE IF NOT EXISTS "+DB_EMPRESA+" (" +
            "_id integer primary key autoincrement, " +
            "codigo_empresa int not null, " +
            "razao_social string not null, " +
            "nome_fantasia string not null," +
            "selecionado int);";
    private static final String CREATE_INDEX_TABLE_EMPRESA_CODIGO_EMPRESA = "CREATE INDEX index_empresa_codigo_empresa ON "+DB_EMPRESA+" (codigo_empresa);";

    private static final String CREATE_TABLE_USUARIO = "CREATE TABLE IF NOT EXISTS "+DB_USUARIO+" (" +
            "_id integer primary key autoincrement, " +
            "nivel_acesso int not null, " +
            "nome string not null);";
    private static final String CREATE_INDEX_TABLE_USUARIO_NIVEL_ACESSO = "CREATE INDEX index_usuario_nivel_acesso ON "+DB_USUARIO+" (nivel_acesso);";

    private static final String CREATE_TABLE_VENDEDOR = "CREATE TABLE IF NOT EXISTS "+DB_VENDEDOR+" (" +
            "_id integer primary key autoincrement, "+
            "codigo_vendedor int not null, " +
            "nome string not null, " +
            "login string not null, " +
            "senha string, " +
            "nivel_acesso int not null, " +
            "pessoa_superior int, " +
            "ativo bit);";
    private static final String CREATE_INDEX_TABLE_VENDEDOR_CODIGO_VENDEDOR = "CREATE INDEX index_empresa_codigo_vendedor ON "+DB_VENDEDOR+" (codigo_vendedor);";
    private static final String CREATE_INDEX_TABLE_VENDEDOR_PESSOA_SUPERIOR = "CREATE INDEX index_empresa_pessoa_superior ON "+DB_VENDEDOR+" (pessoa_superior);";

    private static final String CREATE_TABLE_HISTORICO_VENDEDOR = "CREATE TABLE IF NOT EXISTS "+DB_HISTORICO_VENDEDOR+" (" +
            "_id integer primary key autoincrement, "+
            "codigo_empresa int, " +
            "codigo_vendedor int, " +
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
    private static final String CREATE_INDEX_TABLE_HISTORICO_VENDEDOR_CODIGO_EMPRESA = "CREATE INDEX index_historico_vendedor_codigo_empresa ON "+DB_HISTORICO_VENDEDOR+" (codigo_empresa);";
    private static final String CREATE_INDEX_TABLE_HISTORICO_VENDEDOR_CODIGO_VENDEDOR = "CREATE INDEX index_historico_vendedor_codigo_vendedor ON "+DB_HISTORICO_VENDEDOR+" (codigo_vendedor);";

    private static final String CREATE_TABLE_EMPRESAS_VENDEDORES = "CREATE TABLE IF NOT EXISTS "+DB_EMPRESAS_VENDEDORES+" (" +
            "_id integer primary key autoincrement, "+
            "codigo_vendedor int, " +
            "codigo_empresa int," +
			"meta decimal(9,2));";
    private static final String CREATE_INDEX_TABLE_EMPRESAS_VENDEDORES_CODIGO_VENDEDOR = "CREATE INDEX index_empresas_vendedores_codigo_vendedor ON "+DB_EMPRESAS_VENDEDORES+" (codigo_vendedor);";
    private static final String CREATE_INDEX_TABLE_EMPRESAS_VENDEDORES_CODIGO_EMPRESA = "CREATE INDEX index_empresas_vendedores_codigo_empresa ON "+DB_EMPRESAS_VENDEDORES+" (codigo_empresa);";

    private static final String CREATE_TABLE_CLIENTES = "CREATE TABLE IF NOT EXISTS "+DB_CLIENTES+" (" +
            "_id integer primary key autoincrement, " +
            "codigo_cliente int not null, " +
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
    private static final String CREATE_INDEX_TABLE_CLIENTES_CODIGO_CLIENTE = "CREATE INDEX index_clientes_codigo_cliente ON "+DB_CLIENTES+" (codigo_cliente);";
    private static final String CREATE_INDEX_TABLE_CLIENTES_CODIGO_GRUPO = "CREATE INDEX index_clientes_codigo_grupo ON "+DB_CLIENTES+" (codigo_grupo);";

    private static final String CREATE_TABLE_GRUPO_CLIENTES = "CREATE TABLE IF NOT EXISTS "+DB_GRUPO_CLIENTES+" (" +
            "_id integer primary key autoincrement, " +
            "codigo_grupo int, " +
            "nome string);";
    private static final String CREATE_INDEX_TABLE_GRUPO_CLIENTES_CODIGO_GRUPO = "CREATE INDEX index_grupo_clientes_codigo_grupo ON "+DB_GRUPO_CLIENTES+" (codigo_grupo);";

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
            "codigo_produto int, " +
            "codigo_alternativo string, " +
            "descricao string, " +
            "codigo_unidade int, " +
            "codigo_grupo int," +
            "codigo_empresa int," +
            "selecionado int);";
    private static final String CREATE_INDEX_TABLE_PRODUTOS_CODIGO_PRODUTO = "CREATE INDEX index_produtos_codigo_produto ON "+DB_PRODUTOS+" (codigo_produto);";
    private static final String CREATE_INDEX_TABLE_PRODUTOS_CODIGO_UNIDADE = "CREATE INDEX index_produtos_codigo_unidade ON "+DB_PRODUTOS+" (codigo_unidade);";
    private static final String CREATE_INDEX_TABLE_PRODUTOS_CODIGO_GRUPO = "CREATE INDEX index_produtos_codigo_grupo ON "+DB_PRODUTOS+" (codigo_grupo);";
    private static final String CREATE_INDEX_TABLE_PRODUTOS_CODIGO_EMPRESA = "CREATE INDEX index_produtos_codigo_empresa ON "+DB_PRODUTOS+" (codigo_empresa);";

    private static final String CREATE_TABLE_GRUPO_PRODUTOS = "CREATE TABLE IF NOT EXISTS "+DB_GRUPO_PRODUTOS+" (" +
            "_id integer primary key autoincrement, " +
            "codigo_grupo int, " +
            "nome string);";
    private static final String CREATE_INDEX_TABLE_GRUPO_PRODUTOS_CODIGO_GRUPO = "CREATE INDEX index_grupo_produtos_codigo_grupo ON "+DB_GRUPO_PRODUTOS+" (codigo_grupo);";

    private static final String CREATE_TABLE_ESTOQUE_PRODUTOS = "CREATE TABLE IF NOT EXISTS "+DB_ESTOQUE_PRODUTOS+" (" +
            "_id integer primary key autoincrement, " +
            "codigo_empresa int, " +
            "codigo_produto int, " +
            "estoque int, " +
            "estoque_min int, " +
            "estoque_max int," +
            "situacao string);";

    private static final String CREATE_TABLE_VENDAS = "CREATE TABLE IF NOT EXISTS "+DB_VENDAS+" (" +
            "_id integer primary key autoincrement, " +
            "codigo_venda int not null, " +
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
            "codigo_cliente int, " +
            "numero string, " +
            "data_emissao string, " +
            "data_vencimento string, " +
            "dias_atraso int, " +
            "valor decimal(9,2), " +
			"saldo decimal(9,2), " +
            "situacao int, " +
			"situacao_cor string);";

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
            "codigo_tipo int, " +
            "nome string);";

    private static final String CREATE_TABLE_CONDICOES_PAGAMENTO = "CREATE TABLE IF NOT EXISTS "+DB_CONDICOES_PAGAMENTO+" (" +
            "_id integer primary key autoincrement, " +
            "codigo int, " +
            "nome string);";

    private static final String CREATE_TABLE_TABELA_PRECO = "CREATE TABLE IF NOT EXISTS "+DB_TABELA_PRECO+" (" +
            "_id integer primary key autoincrement, " +
            "codigo_tabela int, " +
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
            "codigo_unidade int, " +
            "nome string," +
            "sigla char(3));";

    private static final String CREATE_TABLE_TIPO_PEDIDO = "CREATE TABLE IF NOT EXISTS "+DB_TIPO_PEDIDO+" (" +
            "_id integer primary key autoincrement, " +
            "codigo_tipo int, " +
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
            "codigo_prazo int, " +
            "nome string, " +
            "prazo_venda int);";

    private static final String CREATE_TABLE_PRECO_CLIENTE = "CREATE TABLE IF NOT EXISTS "+DB_TABELA_PRECO_CLIENTE+" (" +
            "_id integer primary key autoincrement, " +
            "codigo_tabela int, " +
            "condicao_cliente);";

    private static final String CREATE_TABLE_AVISOS = "CREATE TABLE IF NOT EXISTS "+DB_AVISOS+" (" +
            "_id integer primary key autoincrement, " +
            "codigo_aviso int, " +
            "autor string, " +
            "data string, " +
            "hora string, " +
            "prioridade int, " +
            "titulo string, " +
            "mensagem string, " +
            "status int);";

    private static final String CREATE_TABLE_AVISOS_LIDOS = "CREATE TABLE IF NOT EXISTS "+DB_AVISOS_LIDOS+" (" +
            "_id integer primary key autoincrement, " +
            "codigo_aviso int);";

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
    private static final int DB_VERSION = 3;

    public sonicDatabase(Context context) {
        //super(context, Environment.getExternalStorageDirectory().getPath()+sonicConstants.LOCAL_DATA+DATABASE+".db" , null, DB_VERSION);
        super(context, DATABASE , null, DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase DB) {

        DB.execSQL(CREATE_TABLE_SITE);
        DB.execSQL(CREATE_TABLE_FTP);
        DB.execSQL(CREATE_TABLE_EMPRESA);
        DB.execSQL(CREATE_INDEX_TABLE_EMPRESA_CODIGO_EMPRESA);
        DB.execSQL(CREATE_TABLE_USUARIO);
        DB.execSQL(CREATE_INDEX_TABLE_USUARIO_NIVEL_ACESSO);
        DB.execSQL(CREATE_TABLE_VENDEDOR);
        DB.execSQL(CREATE_INDEX_TABLE_VENDEDOR_CODIGO_VENDEDOR);
        DB.execSQL(CREATE_INDEX_TABLE_VENDEDOR_PESSOA_SUPERIOR);
        DB.execSQL(CREATE_TABLE_HISTORICO_VENDEDOR);
        DB.execSQL(CREATE_TABLE_EMPRESAS_VENDEDORES);
        DB.execSQL(CREATE_INDEX_TABLE_EMPRESAS_VENDEDORES_CODIGO_EMPRESA);
        DB.execSQL(CREATE_INDEX_TABLE_EMPRESAS_VENDEDORES_CODIGO_VENDEDOR);
        DB.execSQL(CREATE_TABLE_CLIENTES);
        DB.execSQL(CREATE_INDEX_TABLE_CLIENTES_CODIGO_CLIENTE);
        DB.execSQL(CREATE_INDEX_TABLE_CLIENTES_CODIGO_GRUPO);
        DB.execSQL(CREATE_TABLE_GRUPO_CLIENTES);
        DB.execSQL(CREATE_INDEX_TABLE_GRUPO_CLIENTES_CODIGO_GRUPO);
        DB.execSQL(CREATE_TABLE_RANKING_CLIENTES);
        DB.execSQL(CREATE_INDEX_TABLE_RANKING_CLIENTES_CODIGO_CLIENTE);
        DB.execSQL(CREATE_INDEX_TABLE_RANKING_CLIENTES_CODIGO_EMPRESA);
        DB.execSQL(CREATE_TABLE_CLIENTES_SEM_COMPRA);
        DB.execSQL(CREATE_INDEX_TABLE_CLIENTES_SEM_COMPRA_CODIGO_CLIENTE);
        DB.execSQL(CREATE_TABLE_PRODUTOS);
        DB.execSQL(CREATE_INDEX_TABLE_PRODUTOS_CODIGO_PRODUTO);
        DB.execSQL(CREATE_INDEX_TABLE_PRODUTOS_CODIGO_UNIDADE);
        DB.execSQL(CREATE_INDEX_TABLE_PRODUTOS_CODIGO_GRUPO);
        DB.execSQL(CREATE_INDEX_TABLE_PRODUTOS_CODIGO_EMPRESA);
        DB.execSQL(CREATE_TABLE_GRUPO_PRODUTOS);
        DB.execSQL(CREATE_INDEX_TABLE_GRUPO_PRODUTOS_CODIGO_GRUPO);
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

}
