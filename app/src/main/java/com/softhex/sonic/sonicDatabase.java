package com.softhex.sonic;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Administrador on 10/07/2017.
 */

public class sonicDatabase extends SQLiteOpenHelper{

    private static final String DATABASE = sonicConstants.DATABASE;
    private static final String DB_SITE = sonicConstants.TB_SITE;
    private static final String DB_FTP = sonicConstants.TB_FTP;
    private static final String DB_EMPRESAS = sonicConstants.TB_EMPRESAS;
    private static final String DB_GRUPO_EMPRESAS = sonicConstants.TB_GRUPO_EMPRESAS;
    private static final String DB_NIVEL_ACESSO = sonicConstants.TB_NIVEL_ACESSO;
    private static final String DB_USUARIOS = sonicConstants.TB_USUARIOS;
    private static final String DB_EMPRESAS_USUARIOS = sonicConstants.TB_EMPRESAS_USUARIOS;
    private static final String DB_CLIENTES = sonicConstants.TB_CLIENTES;
    private static final String DB_EMPRESAS_CLIENTES = sonicConstants.TB_EMPRESAS_CLIENTES;
    private static final String DB_GRUPO_CLIENTES = sonicConstants.TB_GRUPO_CLIENTES;
    private static final String DB_RANKING_CLIENTES = sonicConstants.TB_RANKING_CLIENTES;
    private static final String DB_CLIENTE_SEM_COMPRA = sonicConstants.TB_CLIENTES_SEM_COMPRA;
    private static final String DB_ACESSO_CLIENTE = sonicConstants.TB_ACESSO_CLIENTE;
    private static final String DB_PRODUTOS = sonicConstants.TB_PRODUTOS;
    private static final String DB_GRUPO_PRODUTOS = sonicConstants.TB_GRUPO_PRODUTOS;
    private static final String DB_RANKING_PRODUTOS = sonicConstants.TB_RANKING_PRODUTOS;
    private static final String DB_ESTOQUE_PRODUTOS = sonicConstants.TB_ESTOQUE_PRODUTOS;
    private static final String DB_BLOQUEIO_PRODUTOS = sonicConstants.TB_BLOQUEIO_PRODUTOS;
    private static final String DB_ACESSO_PRODUTO = sonicConstants.TB_ACESSO_PRODUTO;
    private static final String DB_ROTA = sonicConstants.TB_ROTA;
    private static final String DB_TABELA_PRECO = sonicConstants.TB_TABELA_PRECO;
    private static final String DB_TABELA_PRECO_PRODUTO = sonicConstants.TB_TABELA_PRECO_PRODUTO;
    private static final String DB_TABELA_PRECO_EMPRESA = sonicConstants.TB_TABELA_PRECO_EMPRESA;
    private static final String DB_FINANCEIRO = sonicConstants.TB_FINANCEIRO;
    private static final String DB_TITULOS = sonicConstants.TB_TITULOS;
    private static final String DB_RETORNO_PEDIDO = sonicConstants.TB_RETORNO_PEDIDO;
	private static final String DB_RETORNO_PEDIDO_ITENS = sonicConstants.TB_RETORNO_PEDIDO_ITENS;
    private static final String DB_TIPO_COBRANCA = sonicConstants.TB_TIPO_COBRANCA;
    private static final String DB_AGENTE_COBRADOR = sonicConstants.TB_AGENTE_COBRADOR;
    private static final String DB_CONDICAO_PAGAMENTO = sonicConstants.TB_CONDICAO_PAGAMENTO;
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
    private static final String DB_ULTIMAS_COMPRAS = sonicConstants.TB_ULTIMAS_COMPRAS;
    private static final String DB_ULTIMAS_COMPRAS_ITENS = sonicConstants.TB_ULTIMAS_COMPRAS_ITENS;
    private static final String DB_SINCRONIZACAO = sonicConstants.TB_SINCRONIZACAO;
    private static final String DB_LOCALIZACAO = sonicConstants.TB_LOCALIZACAO;
    private static final String DB_LOG_ERRO = sonicConstants.TB_LOG_ERRO;

    // TABELAS PREENCHIDAS SEM SINCRONIZAÇÃO
    private static final String DB_BANCOS = sonicConstants.TB_BANCOS;

    private Context mContext;
    private sonicPreferences mPrefs;

    // INÍCIO TABELAS FUNDAMENTAIS/NECESSÁRIAS PARA INICIAR O APLICATIVO

    private static final String CREATE_SITE = "CREATE TABLE IF NOT EXISTS "+DB_SITE+" (" +
            "_id integer PRIMARY KEY AUTOINCREMENT, " +
            "site varchar NOT NULL, " +
            "licensa varchar);";

    private static final String CREATE_FTP = "CREATE TABLE IF NOT EXISTS "+DB_FTP+" (" +
            "_id integer PRIMARY KEY AUTOINCREMENT, " +
            "descricao varchar NOT NULL, " +
            "endereco varchar NOT NULL, " +
            "usuario varchar NOT NULL, " +
            "senha varchar," +
            "ativo bit);" ;

    private static final String CREATE_EMPRESA = "CREATE TABLE IF NOT EXISTS "+ DB_EMPRESAS +" (" +
            "_id integer PRIMARY KEY AUTOINCREMENT, " +
            "codigo int NOT NULL, " +
            "razao_social varchar(100) NOT NULL, " +
            "nome_fantasia varchar(100) NOT NULL," +
            "selecionada int);";
    private static final String CREATE_INDEX_EMPRESA_CODIGO = "CREATE UNIQUE INDEX index_empresa_codigo ON "+ DB_EMPRESAS +" (codigo);";

    private static final String CREATE_GRUPO_EMPRESAS = "CREATE TABLE IF NOT EXISTS "+ DB_GRUPO_EMPRESAS +" (" +
            "_id integer PRIMARY KEY AUTOINCREMENT, " +
            "codigo_empresa int NOT NULL, " +
            "nome varchar NOT NULL, " +
            "descricao varchar(250)," +
            "data_fundacao varchar(10)," +
            "endereco varchar(50)," +
            "bairro varchar(30)," +
            "municipio varchar(30)," +
            "uf character(2)," +
            "cep character(10)," +
            "fone varchar(20)," +
            "whatsapp varchar(20)," +
            "email varchar(40)," +
			"endereco_eletronico varchar," +
			"FOREIGN KEY (codigo_empresa) REFERENCES "+ DB_EMPRESAS +"(codigo));";
    private static final String CREATE_INDEX_GRUPO_EMPRESAS_CODIGO_EMPRESA = "CREATE UNIQUE INDEX index_grupo_empresas_codigo_empresa ON "+ DB_GRUPO_EMPRESAS +" (codigo_empresa);";

    private static final String CREATE_NIVEL_ACESSO = "CREATE TABLE IF NOT EXISTS "+DB_NIVEL_ACESSO+" (" +
            "_id integer PRIMARY KEY AUTOINCREMENT, " +
            "codigo int NOT NULL, " +
            "nome varchar NOT NULL);";
    private static final String CREATE_INDEX_NIVEL_ACESSO_NIVEL = "CREATE INDEX index_nivel_acesso_nivel ON "+DB_NIVEL_ACESSO+" (codigo);";

    private static final String CREATE_USUARIO = "CREATE TABLE IF NOT EXISTS "+ DB_USUARIOS +" (" +
            "_id integer PRIMARY KEY AUTOINCREMENT, "+
            "codigo int NOT NULL, " +
            "nome varchar NOT NULL, " +
            "login varchar NOT NULL, " +
            "senha varchar, " +
            "imei varchar NOT NULL, " +
            "nivel_acesso int NOT NULL, " +
            "usuario_superior int, " +
            "admin bit, " +
            "ativo bit);";
    private static final String CREATE_INDEX_USUARIO_CODIGO = "CREATE UNIQUE INDEX index_usuario_codigo ON "+ DB_USUARIOS +" (codigo);";
    private static final String CREATE_INDEX_USUARIO_NIVEL_ACESSO = "CREATE INDEX index_usuario_nivel_acesso ON "+ DB_USUARIOS +" (nivel_acesso);";
    private static final String CREATE_INDEX_USUARIO_USUARIO_SUPERIOR = "CREATE INDEX index_usuario_usuario_superior ON "+ DB_USUARIOS +" (usuario_superior);";
    private static final String CREATE_INDEX_USUARIO_ATIVO = "CREATE INDEX index_usuario_ativo ON "+ DB_USUARIOS +" (ativo);";

    private static final String CREATE_EMPRESA_USUARIO = "CREATE TABLE IF NOT EXISTS "+ DB_EMPRESAS_USUARIOS +" (" +
            "_id integer PRIMARY KEY AUTOINCREMENT, "+
            "codigo_usuario int, " +
            "codigo_empresa int," +
            "meta_venda varchar," +
			"meta_visita int," +
            "FOREIGN KEY (codigo_usuario) REFERENCES "+ DB_USUARIOS +"(codigo)," +
            "FOREIGN KEY (codigo_empresa) REFERENCES "+ DB_EMPRESAS +"(codigo));" ;
    private static final String CREATE_INDEX_EMPRESA_USUARIO_CODIGO_USUARIO = "CREATE INDEX index_empresa_usuario_codigo_usuario ON "+ DB_EMPRESAS_USUARIOS +" (codigo_usuario);";
    private static final String CREATE_INDEX_EMPRESA_USUARIO_CODIGO_EMPRESA = "CREATE INDEX index_empresa_usuario_codigo_empresa ON "+ DB_EMPRESAS_USUARIOS +" (codigo_empresa);";

    // FIM TABELAS FUNDAMENTAIS/NECESSÁRIAS PARA INICIAR O APLICATIVO

    private static final String CREATE_CLIENTE = "CREATE TABLE IF NOT EXISTS "+ DB_CLIENTES +" (" +
            "_id integer PRIMARY KEY AUTOINCREMENT, " +
            "codigo_usuario int NOT NULL, " +
            "codigo int NOT NULL, " +
            "codigo_grupo int NOT NULL, " +
            "tipo character(1) NOT NULL, " +
            "razao_social varchar NOT NULL, " +
            "nome_fantasia varchar NOT NULL, " +
            "cpf_cnpj varchar NOT NULL, " +
            "insc_estadual varchar, " +
            "endereco varchar NOT NULL, " +
            "bairro varchar NOT NULL, " +
            "municipio varchar NOT NULL, " +
            "uf character(2) NOT NULL, " +
            "data_cadastro varchar, " +
            "cep character(10), " +
            "fone varchar, " +
            "whatsapp varchar, " +
            "contato varchar, " +
            "email varchar, " +
            "observacao varchar, " +
            "situacao bit," +
            "FOREIGN KEY (codigo_usuario) REFERENCES "+ DB_USUARIOS +"(codigo_usuario)," +
            "FOREIGN KEY (codigo_grupo) REFERENCES "+ DB_GRUPO_CLIENTES +"(codigo_grupo));" ;
    private static final String CREATE_INDEX_CLIENTE_CODIGO_USUARIO = "CREATE INDEX index_cliente_codigo_usuario ON "+ DB_CLIENTES +" (codigo_usuario);";
    private static final String CREATE_INDEX_CLIENTE_CODIGO = "CREATE UNIQUE INDEX index_cliente_codigo ON "+ DB_CLIENTES +" (codigo);";
    private static final String CREATE_INDEX_CLIENTE_CODIGO_GRUPO = "CREATE INDEX index_cliente_codigo_grupo ON "+ DB_CLIENTES +" (codigo_grupo);";
    private static final String CREATE_INDEX_CLIENTE_SITUACAO = "CREATE INDEX index_cliente_situacao ON "+ DB_CLIENTES +" (situacao);";

    private static final String CREATE_EMPRESA_CLIENTE = "CREATE TABLE IF NOT EXISTS "+ DB_EMPRESAS_CLIENTES +" (" +
            "_id integer PRIMARY KEY AUTOINCREMENT, "+
            "codigo_usuario int NOT NULL, " +
            "codigo_empresa int NOT NULL, " +
            "codigo_cliente int NOT NULL," +
            "FOREIGN KEY (codigo_usuario) REFERENCES "+ DB_USUARIOS +"(codigo), " +
            "FOREIGN KEY (codigo_empresa) REFERENCES "+ DB_EMPRESAS +"(codigo), " +
            "FOREIGN KEY (codigo_cliente) REFERENCES "+ DB_CLIENTES +"(codigo)); " ;
    private static final String CREATE_INDEX_EMPRESA_CLIENTE_CODIGO_USUARIO = "CREATE INDEX index_empresa_cliente_codigo_usuario ON "+ DB_EMPRESAS_CLIENTES +" (codigo_usuario);";
    private static final String CREATE_INDEX_EMPRESA_CLIENTE_CODIGO_EMPRESA = "CREATE INDEX index_empresa_cliente_codigo_empresa ON "+ DB_EMPRESAS_CLIENTES +" (codigo_empresa);";
    private static final String CREATE_INDEX_EMPRESA_CLIENTE_CODIGO_CLIENTE = "CREATE INDEX index_empresa_cliente_codigo_cliente ON "+ DB_EMPRESAS_CLIENTES +" (codigo_cliente);";

    private static final String CREATE_GRUPO_CLIENTE = "CREATE TABLE IF NOT EXISTS "+ DB_GRUPO_CLIENTES +" (" +
            "_id integer PRIMARY KEY AUTOINCREMENT, " +
            "codigo int, " +
            "nome string);";
    private static final String CREATE_INDEX_GRUPO_CLIENTE_CODIGO = "CREATE UNIQUE INDEX index_grupo_cliente_codigo ON "+ DB_GRUPO_CLIENTES +" (codigo);";

    private static final String CREATE_RANKING_CLIENTE = "CREATE TABLE IF NOT EXISTS "+ DB_RANKING_CLIENTES +" (" +
            "_id integer PRIMARY KEY AUTOINCREMENT, " +
            "codigo_cliente int, " +
            "codigo_empresa int, " +
            "valor decimal(9,2), " +
            "pedidos int, " +
            "valor_anterior decimal(9,2), " +
            "atuacao decimal(9,2));";
    private static final String CREATE_INDEX_RANKING_CLIENTE_CODIGO_CLIENTE = "CREATE INDEX index_ranking_cliente_codigo_cliente ON "+ DB_RANKING_CLIENTES +" (codigo_cliente);";
    private static final String CREATE_INDEX_RANKING_CLIENTE_CODIGO_EMPRESA = "CREATE INDEX index_ranking_cliente_codigo_empresa ON "+ DB_RANKING_CLIENTES +" (codigo_empresa);";

    private static final String CREATE_TABLE_ACESSO_CLIENTE = "CREATE TABLE IF NOT EXISTS "+DB_ACESSO_CLIENTE+" (" +
            "_id integer PRIMARY KEY AUTOINCREMENT, " +
            "codigo_cliente int NOT NULL, " +
            "acessos int NOT NULL DEFAULT 0," +
            "data varchar," +
            "hora varchar," +
            "FOREIGN KEY (codigo_cliente) REFERENCES "+ DB_CLIENTES +"(codigo));";
    private static final String CREATE_INDEX_ACESSO_CLIENTE_CODIGO_CLIENTE = "CREATE INDEX index_acesso_cliente_codigo_cliente ON "+DB_ACESSO_CLIENTE+" (codigo_cliente);";

    private static final String CREATE_CLIENTE_SEM_COMPRA = "CREATE TABLE IF NOT EXISTS "+DB_CLIENTE_SEM_COMPRA+" (" +
            "_id integer PRIMARY KEY AUTOINCREMENT, " +
            "codigo_usuario int, " +
            "codigo_cliente int);";
    private static final String CREATE_INDEX_CLIENTE_SEM_COMPRA_CODIGO_USUARIO = "CREATE INDEX index_cliente_sem_compra_codigo_usuario ON "+DB_CLIENTE_SEM_COMPRA+" (codigo_usuario);";
    private static final String CREATE_INDEX_CLIENTE_SEM_COMPRA_CODIGO_CLIENTE = "CREATE INDEX index_cliente_sem_compra_codigo_cliente ON "+DB_CLIENTE_SEM_COMPRA+" (codigo_cliente);";

    private static final String CREATE_PRODUTO = "CREATE TABLE IF NOT EXISTS "+ DB_PRODUTOS +" (" +
            "_id integer PRIMARY KEY AUTOINCREMENT, " +
            "codigo int NOT NULL, " +
            "codigo_empresa int NOT NULL," +
            "codigo_unidade int NOT NULL, " +
            "codigo_grupo int NOT NULL," +
            "nome varchar NOT NULL, " +
            "codigo_alternativo varchar, " +
            "descricao varchar, " +
            "data_cadastro varchar, " +
            "ncm varchar, " +
            "peso_bruto varchar, " +
            "peso_liquido varchar, " +
            "estoque_minimo int, " +
            "estoque_maximo int, " +
            "multiplicidade int, " +
            "codigo_ean varchar, " +
            "codigo_ean_tributavel varchar," +
            "foto bit," +
            "FOREIGN KEY (codigo_empresa) REFERENCES "+ DB_EMPRESAS +"(codigo)," +
            "FOREIGN KEY (codigo_unidade) REFERENCES "+DB_UNIDADE_MEDIDA+"(codigo)," +
            "FOREIGN KEY (codigo_grupo) REFERENCES "+ DB_GRUPO_PRODUTOS +"(codigo));";
    private static final String CREATE_INDEX_PRODUTO_CODIGO = "CREATE INDEX index_produto_codigo ON "+ DB_PRODUTOS +" (codigo);";
    private static final String CREATE_INDEX_PRODUTO_CODIGO_EMPRESA = "CREATE INDEX index_produto_codigo_empresa ON "+ DB_PRODUTOS +" (codigo_empresa);";
    private static final String CREATE_INDEX_PRODUTO_CODIGO_UNIDADE = "CREATE INDEX index_produto_codigo_unidade ON "+ DB_PRODUTOS +" (codigo_unidade);";
    private static final String CREATE_INDEX_PRODUTO_CODIGO_GRUPO = "CREATE INDEX index_produto_codigo_grupo ON "+ DB_PRODUTOS +" (codigo_grupo);";

    private static final String CREATE_GRUPO_PRODUTO = "CREATE TABLE IF NOT EXISTS "+ DB_GRUPO_PRODUTOS +" (" +
            "_id integer PRIMARY KEY AUTOINCREMENT, " +
            "codigo int, " +
            "nome string);";
    private static final String CREATE_INDEX_GRUPO_PRODUTO_CODIGO = "CREATE UNIQUE INDEX index_grupo_produto_codigo ON "+ DB_GRUPO_PRODUTOS +" (codigo);";

    private static final String CREATE_BLOQUEIO_PRODUTO = "CREATE TABLE IF NOT EXISTS "+ DB_BLOQUEIO_PRODUTOS +" (" +
            "_id integer PRIMARY KEY AUTOINCREMENT, " +
            "codigo_usuario int NOT NULL, " +
            "codigo_empresa int NOT NULL, " +
            "codigo_produto int NOT NULL, " +
            "FOREIGN KEY (codigo_empresa) REFERENCES "+ DB_EMPRESAS +"(codigo)," +
            "FOREIGN KEY (codigo_produto) REFERENCES "+ DB_PRODUTOS +"(codigo));";
    private static final String CREATE_INDEX_BLOQUEIO_PRODUTO_CODIGO_USUARIO = "CREATE INDEX index_bloqueio_produto_codigo_usuario ON "+ DB_BLOQUEIO_PRODUTOS +" (codigo_usuario);";
    private static final String CREATE_INDEX_BLOQUEIO_PRODUTO_CODIGO_EMPRESA = "CREATE INDEX index_bloqueio_produto_codigo_empresa ON "+ DB_BLOQUEIO_PRODUTOS +" (codigo_empresa);";
    private static final String CREATE_INDEX_BLOQUEIO_PRODUTO_CODIGO_PRODUTO = "CREATE INDEX index_bloqueio_produto_codigo_produto ON "+ DB_BLOQUEIO_PRODUTOS +" (codigo_produto);";

    private static final String CREATE_TABLE_ACESSO_PRODUTO = "CREATE TABLE IF NOT EXISTS "+DB_ACESSO_PRODUTO+" (" +
            "_id integer PRIMARY KEY AUTOINCREMENT, " +
            "codigo_produto int NOT NULL, " +
            "acessos int NOT NULL DEFAULT 0," +
            "data varchar," +
            "hora varchar," +
            "FOREIGN KEY (codigo_produto) REFERENCES "+ DB_PRODUTOS +"(codigo));";
    private static final String CREATE_INDEX_ACESSO_PRODUTO_CODIGO_PRODUTO = "CREATE INDEX index_acesso_produto_codigo_produto ON "+DB_ACESSO_PRODUTO+" (codigo_produto);";


    private static final String CREATE_ROTA = "CREATE TABLE IF NOT EXISTS "+DB_ROTA+" (" +
            "_id integer PRIMARY KEY AUTOINCREMENT, " +
            "codigo int NOT NULL, " +
            "codigo_usuario int NOT NULL, " +
            "codigo_empresa int NOT NULL, " +
            "codigo_cliente int NOT NULL, " +
            "tipo int NOT NULL, " +                 // 1=AGENDAMENTO, 2=REAGENDAMENTO
            "status int NOT NULL, " +               // 1=NÃO INICIADO, 2=EM ANDAMENTO, 3=CONCLUIDO, 4=CANCELADO
            "data_agendamento varchar NOT NULL, " +
            "hora_agendamento varchar NOT NULL, " +
            "proprietario INT NOT NULL, "  +        // 1=AGENDA, 2=PESSOAL
            "responsavel string, " +
            "ordem int, " +
            "observacao string, " +
            "data_inicio varchar, " +
            "hora_inicio varchar, " +
            "data_fim varchar, " +
            "hora_fim varchar, " +
            "reagendamento int, " +                 // CODIGO AGENDAMENTO ANTERIOR
            "data_reagendamento varchar, " +
            "hora_reagendamento varchar, " +
            "situacao int, " +                      // 1=POSITIVADO, 2=NEGATIVADO
            "negativacao string, " +                // MOTIVO DA NEGATIVAÇÃO
            "cancelamento string, " +               // MOTIVO DO CANCELAMENTO
            "FOREIGN KEY (codigo_usuario) REFERENCES "+ DB_USUARIOS +"(codigo)," +
            "FOREIGN KEY (codigo_empresa) REFERENCES "+ DB_EMPRESAS +"(codigo)," +
            "FOREIGN KEY (codigo_cliente) REFERENCES "+ DB_CLIENTES +"(codigo));";
    private static final String CREATE_INDEX_ROTA_CODIGO = "CREATE INDEX index_rota_codigo ON "+DB_ROTA+" (codigo);";
    private static final String CREATE_INDEX_ROTA_CODIGO_USUARIO = "CREATE INDEX index_rota_codigo_usuario ON "+DB_ROTA+" (codigo_usuario);";
    private static final String CREATE_INDEX_ROTA_CODIGO_EMPRESA = "CREATE INDEX index_rota_codigo_empresa ON "+DB_ROTA+" (codigo_empresa);";
    private static final String CREATE_INDEX_ROTA_CODIGO_CLIENTE = "CREATE INDEX index_rota_codigo_cliente ON "+DB_ROTA+" (codigo_cliente);";

    private static final String CREATE_ESTOQUE_PRODUTO = "CREATE TABLE IF NOT EXISTS "+ DB_ESTOQUE_PRODUTOS +" (" +
            "_id integer PRIMARY KEY AUTOINCREMENT, " +
            "codigo_produto int NOT NULL, " +
            "codigo_empresa int NOT NULL, " +
            "codigo_unidade_medida int NOT NULL, " +
            "estoque int, " +
            "estoque_min int, " +
            "estoque_max int, " +
            "FOREIGN KEY (codigo_produto) REFERENCES "+ DB_PRODUTOS +"(codigo)," +
            "FOREIGN KEY (codigo_empresa) REFERENCES "+ DB_EMPRESAS +"(codigo)," +
            "FOREIGN KEY (codigo_unidade_medida) REFERENCES "+DB_UNIDADE_MEDIDA+"(codigo));";
    private static final String CREATE_INDEX_ESTOQUE_PRODUTO_CODIGO_PRODUTO = "CREATE INDEX index_estoque_produto_codigo_produto ON "+ DB_ESTOQUE_PRODUTOS +" (codigo_produto);";
    private static final String CREATE_INDEX_ESTOQUE_PRODUTO_CODIGO_EMPRESA = "CREATE INDEX index_estoque_produto_codigo_empresa ON "+ DB_ESTOQUE_PRODUTOS +" (codigo_empresa);";
    private static final String CREATE_INDEX_ESTOQUE_PRODUTO_CODIGO_UNIDADE_MEDIDA = "CREATE INDEX index_estoque_produto_codigo_unidade_medida ON "+ DB_ESTOQUE_PRODUTOS +" (codigo_unidade_medida);";

    private static final String CREATE_VENDAS = "CREATE TABLE IF NOT EXISTS "+ DB_VENDAS +" (" +
            "_id integer PRIMARY KEY AUTOINCREMENT, " +
            "codigo_usuario int NOT NULL, " +
            "codigo_empresa int NOT NULL, " +
            "data varchar," +
            "valor numeric," +
            "valor_desconto numeric);";
    private static final String CREATE_INDEX_VENDAS_CODIGO_USUARIO = "CREATE INDEX index_vendas_codigo_usuario ON "+ DB_VENDAS +" (codigo_usuario);";
    private static final String CREATE_INDEX_VENDAS_CODIGO_EMPRESA = "CREATE INDEX index_vendas_codigo_empresa ON "+ DB_VENDAS +" (codigo_empresa);";

    private static final String CREATE_RANKING_PRODUTO = "CREATE TABLE IF NOT EXISTS "+ DB_RANKING_PRODUTOS +" (" +
            "_id integer PRIMARY KEY AUTOINCREMENT, " +
            "codigo_produto int, " +
            "quantidade decimal(9,2), " +
            "quantidade_anterior decimal(9,2), " +
            "pedidos int, " +
            "atuacao decimal(9,2), " +
            "codigo_unidade int, " +
            "codigo_grupo int);";

    private static final String CREATE_FINANCEIRO = "CREATE TABLE IF NOT EXISTS "+DB_FINANCEIRO+" (" +
            "_id integer PRIMARY KEY AUTOINCREMENT, " +
            "codigo_cliente int, " +
            "limite_credito decimal(9,2), " +
            "saldo decimal(9,2), " +
            "maior_compra decimal(9,2), " +
            "data_maior_compra string, " +
            "ultima_compra decimal(9,2), " +
            "data_ultima_compra string);";


    private static final String CREATE_TITULOS = "CREATE TABLE IF NOT EXISTS "+ DB_TITULOS +" (" +
            "_id integer PRIMARY KEY AUTOINCREMENT, " +
            "codigo int NOT NULL, " +
            "codigo_usuario NOT NULL, " +
            "codigo_cliente int NOT NULL, " +
            "codigo_empresa int NOT NULL, " +
            "numero varchar NOT NULL, " +
            "data_emissao varchar NOT NULL, " +
            "data_vencimento varchar NOT NULL, " +
            "codigo_agente_cobrador int NOT NULL, " +
            "codigo_tipo_cobranca int NOT NULL, " +
            "vendedor varchar, " +
            "valor varchar, " +
			"saldo varchar, " +
            "juros varchar, " +
            "situacao int, " + // 1=A VENCER, 2=VENCIDO
            "FOREIGN KEY (codigo_usuario) REFERENCES "+ DB_USUARIOS +"(codigo)," +
            "FOREIGN KEY (codigo_cliente) REFERENCES "+ DB_CLIENTES +"(codigo)," +
            "FOREIGN KEY (codigo_empresa) REFERENCES "+ DB_EMPRESAS +"(codigo));";
    private static final String CREATE_INDEX_TITULO_CODIGO = "CREATE INDEX index_estoque_titulo_codigo ON "+ DB_TITULOS +" (codigo);";
    private static final String CREATE_INDEX_TITULO_CODIGO_USUARIO = "CREATE INDEX index_titulo_codigo_usuario ON "+ DB_TITULOS +" (codigo_usuario);";
    private static final String CREATE_INDEX_TITULO_CODIGO_CLIENTE = "CREATE INDEX index_titulo_codigo_cliente ON "+ DB_TITULOS +" (codigo_cliente);";
    private static final String CREATE_INDEX_TITULO_CODIGO_EMPRESA = "CREATE INDEX index_titulo_codigo_empresa ON "+ DB_TITULOS +" (codigo_empresa);";
    private static final String CREATE_INDEX_TITULO_CODIGO_AGENTE_COBRADOR = "CREATE INDEX index_titulo_codigo_agente_cobrador ON "+ DB_TITULOS +" (codigo_agente_cobrador);";
    private static final String CREATE_INDEX_TITULO_CODIGO_TIPO_COBRANCA = "CREATE INDEX index_titulo_codigo_tipo_cobranca ON "+ DB_TITULOS +" (codigo_tipo_cobranca);";

    private static final String CREATE_RETORNO_PEDIDO = "CREATE TABLE IF NOT EXISTS "+DB_RETORNO_PEDIDO+" (" +
            "_id integer PRIMARY KEY AUTOINCREMENT, " +
            "codigo_cliente int, " +
            "codigo_pedido int, " +
            "numero_pedido_mobile varchar, " +
            "data_pedido varchar, " +
            "valor_pedido decimal(9,2), " +
			"valor_desconto decimal(9,2), " +
            "codigo_tipo_pedido int," +
            "codigo_prazo int," +
            "situacao int);";
			
	private static final String CREATE_RETORNO_PEDIDO_ITENS = "CREATE TABLE IF NOT EXISTS "+DB_RETORNO_PEDIDO_ITENS+" (" +
            "_id integer PRIMARY KEY AUTOINCREMENT, " +
            "codigo_pedido int, " +
            "codigo_produto int, " +
            "codigo_unidade int," +
			"quantidade int," +
            "valor decimal(9,2), " +
			"valor_desconto decimal(9,2));";

    private static final String CREATE_TIPO_COBRANCA = "CREATE TABLE IF NOT EXISTS "+DB_TIPO_COBRANCA+" (" +
            "_id integer PRIMARY KEY AUTOINCREMENT, " +
            "codigo int, " +
            "nome string);";

    private static final String CREATE_AGENTE_COBRADOR = "CREATE TABLE IF NOT EXISTS "+DB_AGENTE_COBRADOR+" (" +
            "_id integer PRIMARY KEY AUTOINCREMENT, " +
            "codigo int, " +
            "nome string);";

    private static final String CREATE_CONDICOES_PAGAMENTO = "CREATE TABLE IF NOT EXISTS "+DB_CONDICAO_PAGAMENTO+" (" +
            "_id integer PRIMARY KEY AUTOINCREMENT, " +
            "codigo int, " +
            "nome string);";

    private static final String CREATE_TABELA_PRECO = "CREATE TABLE IF NOT EXISTS "+DB_TABELA_PRECO+" (" +
            "_id integer PRIMARY KEY AUTOINCREMENT, " +
            "codigo int, " +
            "nome string);";
    private static final String CREATE_INDEX_TABELA_PRECO_CODIGO = "CREATE INDEX index_tabela_preco_codigo ON "+DB_TABELA_PRECO+" (codigo);";

    private static final String CREATE_TABELA_PRECO_EMPRESA = "CREATE TABLE IF NOT EXISTS "+DB_TABELA_PRECO_EMPRESA+" (" +
            "_id integer PRIMARY KEY AUTOINCREMENT, " +
            "codigo_tabela int, " +
            "codigo_tabela_empresa int, " +
            "codigo_empresa int); ";
    private static final String CREATE_INDEX_TABELA_PRECO_EMPRESA_CODIGO_TABELA = "CREATE INDEX index_tabela_preco_empresa_codigo_tabela ON "+DB_TABELA_PRECO_EMPRESA+" (codigo_tabela);";
    private static final String CREATE_INDEX_TABELA_PRECO_EMPRESA_CODIGO_TABELA_EMPRESA = "CREATE INDEX index_tabela_preco_empresa_codigo_tabela_empresa ON "+DB_TABELA_PRECO_EMPRESA+" (codigo_tabela_empresa);";
    private static final String CREATE_INDEX_TABELA_PRECO_EMPRESA_CODIGO_EMPRESA = "CREATE INDEX index_tabela_preco_empresa_codigo_empresa ON "+DB_TABELA_PRECO_EMPRESA+" (codigo_empresa);";

    private static final String CREATE_TABELA_PRECO_PRODUTO = "CREATE TABLE IF NOT EXISTS "+DB_TABELA_PRECO_PRODUTO+" (" +
            "_id integer PRIMARY KEY AUTOINCREMENT, " +
            "codigo_tabela int, " +
            "codigo_empresa int, " +
            "codigo_produto int, " +
            "codigo_unidade int, " +
            "preco decimal(9,2), " +
            "quantidade_minima int, " +
            "quantidade_maxima int, " +
            "valor_desconto decimal(9,2));";
    private static final String CREATE_INDEX_TABELA_PRECO_PRODUTO_CODIGO_TABELA = "CREATE INDEX index_tabela_preco_produto_codigo_tabela ON "+DB_TABELA_PRECO_PRODUTO+" (codigo_tabela);";
    private static final String CREATE_INDEX_TABELA_PRECO_PRODUTO_CODIGO_EMPRESA = "CREATE INDEX index_tabela_preco_produto_codigo_empresa ON "+DB_TABELA_PRECO_PRODUTO+" (codigo_empresa);";
    private static final String CREATE_INDEX_TABELA_PRECO_PRODUTO_CODIGO_PRODUTO = "CREATE INDEX index_tabela_preco_produto_codigo_produto ON "+DB_TABELA_PRECO_PRODUTO+" (codigo_produto);";
    private static final String CREATE_INDEX_TABELA_PRECO_PRODUTO_CODIGO_UNIDADE = "CREATE INDEX index_tabela_preco_produto_codigo_unidade ON "+DB_TABELA_PRECO_PRODUTO+" (codigo_unidade);";


    private static final String CREATE_TRANSPORTADORA = "CREATE TABLE IF NOT EXISTS "+DB_TRANSPORTADORA+" (" +
            "_id integer PRIMARY KEY AUTOINCREMENT, " +
            "codigo int, " +
            "nome string);";

    private static final String CREATE_UNIDADE_MEDIDA = "CREATE TABLE IF NOT EXISTS "+DB_UNIDADE_MEDIDA+" (" +
            "_id integer PRIMARY KEY AUTOINCREMENT, " +
            "codigo int, " +
            "nome string," +
            "sigla char(3));";

    private static final String CREATE_TIPO_PEDIDO = "CREATE TABLE IF NOT EXISTS "+DB_TIPO_PEDIDO+" (" +
            "_id integer PRIMARY KEY AUTOINCREMENT, " +
            "codigo int, " +
            "nome string);";

    private static final String CREATE_TIPO_DOCUMENTO = "CREATE TABLE IF NOT EXISTS "+DB_TIPO_DOCUMENTO+" (" +
            "_id integer PRIMARY KEY AUTOINCREMENT, " +
            "codigo int, " +
            "nome string);";

    private static final String CREATE_DESCONTO = "CREATE TABLE IF NOT EXISTS "+DB_DESCONTO+" (" +
            "_id integer PRIMARY KEY AUTOINCREMENT, " +
            "codigo_vendedor int, " +
            "perc_desconto decimal(9,2));";

    private static final String CREATE_PRAZO = "CREATE TABLE IF NOT EXISTS "+DB_PRAZO+" (" +
            "_id integer PRIMARY KEY AUTOINCREMENT, " +
            "codigo int, " +
            "nome string, " +
            "prazo_venda int);";

    private static final String CREATE_PRECO_CLIENTE = "CREATE TABLE IF NOT EXISTS "+DB_TABELA_PRECO_CLIENTE+" (" +
            "_id integer PRIMARY KEY AUTOINCREMENT, " +
            "codigo_tabela int, " +
            "condicao_cliente);";

    private static final String CREATE_ULTIMAS_COMPRAS = "CREATE TABLE IF NOT EXISTS "+DB_ULTIMAS_COMPRAS+" (" +
            "_id integer PRIMARY KEY AUTOINCREMENT, " +
            "codigo_usuario int NOT NULL, " +
            "codigo int NOT NULL, " +
            "codigo_empresa int NOT NULL, " +
            "codigo_cliente int NOT NULL, " +
            "codigo_tipo_cobranca int NOT NULL, " +
            "codigo_agente_cobrador int NOT NULL, " +
            "codigo_prazo int, " +
            "data varchar, " +
            "vendedor string, " +
            "valor decimal(9,2), " +
            "valor_desconto decimal(9,2));";
    private static final String CREATE_INDEX_ULTIMAS_COMPRAS_CODIGO_USUARIO = "CREATE INDEX index_ultimas_compras_codigo_usuario ON "+DB_ULTIMAS_COMPRAS+" (codigo_usuario);";
    private static final String CREATE_INDEX_ULTIMAS_COMPRAS_CODIGO = "CREATE UNIQUE INDEX index_ultimas_compras_codigo ON "+DB_ULTIMAS_COMPRAS+" (codigo);";
    private static final String CREATE_INDEX_ULTIMAS_COMPRAS_CODIGO_EMPRESA = "CREATE INDEX index_ultimas_compras_codigo_empresa ON "+DB_ULTIMAS_COMPRAS+" (codigo_empresa);";
    private static final String CREATE_INDEX_ULTIMAS_COMPRAS_CODIGO_CLIENTE = "CREATE INDEX index_ultimas_compras_codigo_cliente ON "+DB_ULTIMAS_COMPRAS+" (codigo_cliente);";
    private static final String CREATE_INDEX_ULTIMAS_COMPRAS_CODIGO_TIPO_COBRANCA = "CREATE INDEX index_ultimas_compras_codigo_tipo_cobranca ON "+DB_ULTIMAS_COMPRAS+" (codigo_tipo_cobranca);";
    private static final String CREATE_INDEX_ULTIMAS_COMPRAS_CODIGO_AGENTE_COBRADOR = "CREATE INDEX index_ultimas_compras_codigo_agente_cobrador ON "+DB_ULTIMAS_COMPRAS+" (codigo_agente_cobrador);";
    private static final String CREATE_INDEX_ULTIMAS_COMPRAS_CODIGO_PRAZO = "CREATE INDEX index_ultimas_compras_codigo_prazo ON "+DB_ULTIMAS_COMPRAS+" (codigo_prazo);";

    private static final String CREATE_ULTIMAS_COMPRAS_ITENS = "CREATE TABLE IF NOT EXISTS "+DB_ULTIMAS_COMPRAS_ITENS+" (" +
            "_id integer PRIMARY KEY AUTOINCREMENT, " +
            "codigo_usuario int NOT NULL, " +
            "codigo int NOT NULL, " +
            "codigo_produto int NOT NULL, " +
            "codigo_unidade int NOT NULL, " +
            "quantidade int," +
            "preco decimal(9,2)," +
            "valor decimal(9,2)," +
            "valor_desconto decimal(9,2));";
    private static final String CREATE_INDEX_ULTIMAS_COMPRAS_ITENS_CODIGO_USUARIO = "CREATE INDEX index_ultimas_compras_itens_codigo_usuario ON "+DB_ULTIMAS_COMPRAS_ITENS+" (codigo_usuario);";
    private static final String CREATE_INDEX_ULTIMAS_COMPRAS_ITENS_CODIGO = "CREATE INDEX index_ultimas_compras_itens_codigo ON "+DB_ULTIMAS_COMPRAS_ITENS+" (codigo);";
    private static final String CREATE_INDEX_ULTIMAS_COMPRAS_ITENS_CODIGO_PRODUTO = "CREATE INDEX index_ultimas_compras_itens_codigo_produto ON "+DB_ULTIMAS_COMPRAS_ITENS+" (codigo_produto);";
    private static final String CREATE_INDEX_ULTIMAS_COMPRAS_ITENS_CODIGO_UNIDADE = "CREATE INDEX index_ultimas_compras_itens_codigo_unidade ON "+DB_ULTIMAS_COMPRAS_ITENS+" (codigo_unidade);";

    private static final String CREATE_SINCRONIZACAO = "CREATE TABLE IF NOT EXISTS "+DB_SINCRONIZACAO+" (" +
            "_id integer PRIMARY KEY AUTOINCREMENT, " +
            "tabela string, " +
            "tipo string, " +
            "data_sinc varchar, " +
            "hora_sinc varchar);";

    private static final String CREATE_LOG_ERRO = "CREATE TABLE IF NOT EXISTS "+DB_LOG_ERRO+" (" +
            "_id integer PRIMARY KEY AUTOINCREMENT, " +
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
            "data varchar, " +
            "hora varchar);";

    private static final String CREATE_LOCALIZACAO = "CREATE TABLE IF NOT EXISTS "+DB_LOCALIZACAO+" (" +
            "_id integer PRIMARY KEY AUTOINCREMENT, " +
            "codigo_vendedor int, " +
            "data varchar, " +
            "hora varchar, " +
            "latitude varchar, " +
            "longitude varchar);";

    // TABELAS PREENCHIDAS SEM SINCRONIZAÇÃO
    private static final String CREATE_BANCOS = "CREATE TABLE IF NOT EXISTS "+DB_BANCOS+" (" +
            "_id integer PRIMARY KEY AUTOINCREMENT, " +
            "codigo varchar, " +
            "nome varchar, " +
            "nome_completo varchar);";

    private static final String DROP_TABLE = " DROP TABLE [IF EXISTS] ";
    private static final int DB_VERSION = 2;

    public sonicDatabase(Context context) {
        //super(context, Environment.getExternalStorageDirectory().getPath()+sonicConstants.LOCAL_DATA+DATABASE+".db" , null, DB_VERSION);
        super(context, DATABASE , null, DB_VERSION);
        this.mContext = context;
        this.mPrefs = new sonicPreferences(context);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {

        DB.execSQL(CREATE_SITE);
        DB.execSQL(CREATE_FTP);
        // TABELA EMPRESA
        DB.execSQL(CREATE_EMPRESA);
        DB.execSQL(CREATE_INDEX_EMPRESA_CODIGO);
        // TABELA MATRIZ
        DB.execSQL(CREATE_GRUPO_EMPRESAS);
        DB.execSQL(CREATE_INDEX_GRUPO_EMPRESAS_CODIGO_EMPRESA);
        // TABELA NIVEL_ACESSO
        DB.execSQL(CREATE_NIVEL_ACESSO);
        DB.execSQL(CREATE_INDEX_NIVEL_ACESSO_NIVEL);
        // TABELA USUARIO
        DB.execSQL(CREATE_USUARIO);
        DB.execSQL(CREATE_INDEX_USUARIO_CODIGO);
        DB.execSQL(CREATE_INDEX_USUARIO_NIVEL_ACESSO);
        DB.execSQL(CREATE_INDEX_USUARIO_USUARIO_SUPERIOR);
        DB.execSQL(CREATE_INDEX_USUARIO_ATIVO);
        // TABELA EMPRESA USUARIO
        DB.execSQL(CREATE_EMPRESA_USUARIO);
        DB.execSQL(CREATE_INDEX_EMPRESA_USUARIO_CODIGO_USUARIO);
        DB.execSQL(CREATE_INDEX_EMPRESA_USUARIO_CODIGO_EMPRESA);
        // TABELA CLIENTE
        DB.execSQL(CREATE_CLIENTE);
        DB.execSQL(CREATE_INDEX_CLIENTE_CODIGO);
        DB.execSQL(CREATE_INDEX_CLIENTE_CODIGO_USUARIO);
        DB.execSQL(CREATE_INDEX_CLIENTE_CODIGO_GRUPO);
        DB.execSQL(CREATE_INDEX_CLIENTE_SITUACAO);
        // TABELA ACESSO CLIENTE
        DB.execSQL(CREATE_TABLE_ACESSO_CLIENTE);
        DB.execSQL(CREATE_INDEX_ACESSO_CLIENTE_CODIGO_CLIENTE);
        // TABELA EMPRESA_CLIENTE
        DB.execSQL(CREATE_EMPRESA_CLIENTE);
        DB.execSQL(CREATE_INDEX_EMPRESA_CLIENTE_CODIGO_USUARIO);
        DB.execSQL(CREATE_INDEX_EMPRESA_CLIENTE_CODIGO_CLIENTE);
        DB.execSQL(CREATE_INDEX_EMPRESA_CLIENTE_CODIGO_EMPRESA);
        // TABELA GRUPO_CLIENTE
        DB.execSQL(CREATE_GRUPO_CLIENTE);
        DB.execSQL(CREATE_INDEX_GRUPO_CLIENTE_CODIGO);
        DB.execSQL(CREATE_RANKING_CLIENTE);
        DB.execSQL(CREATE_INDEX_RANKING_CLIENTE_CODIGO_CLIENTE);
        DB.execSQL(CREATE_INDEX_RANKING_CLIENTE_CODIGO_EMPRESA);
        DB.execSQL(CREATE_CLIENTE_SEM_COMPRA);
        DB.execSQL(CREATE_INDEX_CLIENTE_SEM_COMPRA_CODIGO_CLIENTE);
        DB.execSQL(CREATE_INDEX_CLIENTE_SEM_COMPRA_CODIGO_USUARIO);
        // TABELA PRODUTO
        DB.execSQL(CREATE_PRODUTO);
        DB.execSQL(CREATE_INDEX_PRODUTO_CODIGO);
        DB.execSQL(CREATE_INDEX_PRODUTO_CODIGO_UNIDADE);
        DB.execSQL(CREATE_INDEX_PRODUTO_CODIGO_GRUPO);
        DB.execSQL(CREATE_INDEX_PRODUTO_CODIGO_EMPRESA);
        // TABELA GRUPO_PRODUTO
        DB.execSQL(CREATE_GRUPO_PRODUTO);
        DB.execSQL(CREATE_INDEX_GRUPO_PRODUTO_CODIGO);
        // TABELA ESTOQUE_PRODUTO
        DB.execSQL(CREATE_ESTOQUE_PRODUTO);
        DB.execSQL(CREATE_INDEX_ESTOQUE_PRODUTO_CODIGO_PRODUTO);
        DB.execSQL(CREATE_INDEX_ESTOQUE_PRODUTO_CODIGO_EMPRESA);
        DB.execSQL(CREATE_INDEX_ESTOQUE_PRODUTO_CODIGO_UNIDADE_MEDIDA);
        // TABELA BLOQUEIO_PRODUTO
        DB.execSQL(CREATE_BLOQUEIO_PRODUTO);
        DB.execSQL(CREATE_INDEX_BLOQUEIO_PRODUTO_CODIGO_USUARIO);
        DB.execSQL(CREATE_INDEX_BLOQUEIO_PRODUTO_CODIGO_EMPRESA);
        DB.execSQL(CREATE_INDEX_BLOQUEIO_PRODUTO_CODIGO_PRODUTO);
        // TABELA ROTA
        DB.execSQL(CREATE_ROTA);
        DB.execSQL(CREATE_INDEX_ROTA_CODIGO);
        DB.execSQL(CREATE_INDEX_ROTA_CODIGO_USUARIO);
        DB.execSQL(CREATE_INDEX_ROTA_CODIGO_CLIENTE);
        DB.execSQL(CREATE_INDEX_ROTA_CODIGO_EMPRESA);
        // TABELA ULTIMAS COMPRAS
        DB.execSQL(CREATE_ULTIMAS_COMPRAS);
        DB.execSQL(CREATE_INDEX_ULTIMAS_COMPRAS_CODIGO);
        DB.execSQL(CREATE_INDEX_ULTIMAS_COMPRAS_CODIGO_USUARIO);
        DB.execSQL(CREATE_INDEX_ULTIMAS_COMPRAS_CODIGO_CLIENTE);
        DB.execSQL(CREATE_INDEX_ULTIMAS_COMPRAS_CODIGO_EMPRESA);
        DB.execSQL(CREATE_INDEX_ULTIMAS_COMPRAS_CODIGO_TIPO_COBRANCA);
        DB.execSQL(CREATE_INDEX_ULTIMAS_COMPRAS_CODIGO_AGENTE_COBRADOR);
        DB.execSQL(CREATE_INDEX_ULTIMAS_COMPRAS_CODIGO_PRAZO);
        DB.execSQL(CREATE_RANKING_PRODUTO);
        // TABELA ULTIMAS XOMPRAS ITENS
        DB.execSQL(CREATE_ULTIMAS_COMPRAS_ITENS);
        DB.execSQL(CREATE_INDEX_ULTIMAS_COMPRAS_ITENS_CODIGO);
        DB.execSQL(CREATE_INDEX_ULTIMAS_COMPRAS_ITENS_CODIGO_PRODUTO);
        DB.execSQL(CREATE_INDEX_ULTIMAS_COMPRAS_ITENS_CODIGO_UNIDADE);
        DB.execSQL(CREATE_INDEX_ULTIMAS_COMPRAS_ITENS_CODIGO_USUARIO);
        // TABELA PRECO
        DB.execSQL(CREATE_TABELA_PRECO);
        DB.execSQL(CREATE_INDEX_TABELA_PRECO_CODIGO);
        // TABELA PRECO_EMPRESA
        DB.execSQL(CREATE_TABELA_PRECO_EMPRESA);
        DB.execSQL(CREATE_INDEX_TABELA_PRECO_EMPRESA_CODIGO_EMPRESA);
        DB.execSQL(CREATE_INDEX_TABELA_PRECO_EMPRESA_CODIGO_TABELA);
        DB.execSQL(CREATE_INDEX_TABELA_PRECO_EMPRESA_CODIGO_TABELA_EMPRESA);
        // TABELA PRECO_PRODUTO
        DB.execSQL(CREATE_TABELA_PRECO_PRODUTO);
        DB.execSQL(CREATE_INDEX_TABELA_PRECO_PRODUTO_CODIGO_TABELA);
        DB.execSQL(CREATE_INDEX_TABELA_PRECO_PRODUTO_CODIGO_EMPRESA);
        DB.execSQL(CREATE_INDEX_TABELA_PRECO_PRODUTO_CODIGO_PRODUTO);
        DB.execSQL(CREATE_INDEX_TABELA_PRECO_PRODUTO_CODIGO_UNIDADE);
        // TABELA ACESSO_PRODUTO
        DB.execSQL(CREATE_TABLE_ACESSO_PRODUTO);
        DB.execSQL(CREATE_INDEX_ACESSO_PRODUTO_CODIGO_PRODUTO);
        // TABELA VENDAS
        DB.execSQL(CREATE_VENDAS);
        DB.execSQL(CREATE_INDEX_VENDAS_CODIGO_USUARIO);
        DB.execSQL(CREATE_INDEX_VENDAS_CODIGO_EMPRESA);

        DB.execSQL(CREATE_TABELA_PRECO);
        //DB.execSQL(CREATE_TABELA_PRECO_PRODUTO);
        //DB.execSQL(CREATE_TABELA_PRECO_CLIENTE);
        DB.execSQL(CREATE_FINANCEIRO);
        // TABELA TITULO
        DB.execSQL(CREATE_TITULOS);
        DB.execSQL(CREATE_INDEX_TITULO_CODIGO);
        DB.execSQL(CREATE_INDEX_TITULO_CODIGO_CLIENTE);
        DB.execSQL(CREATE_INDEX_TITULO_CODIGO_EMPRESA);
        DB.execSQL(CREATE_INDEX_TITULO_CODIGO_USUARIO);
        DB.execSQL(CREATE_INDEX_TITULO_CODIGO_AGENTE_COBRADOR);
        DB.execSQL(CREATE_INDEX_TITULO_CODIGO_TIPO_COBRANCA);
        // RETORNO PEDIDO
        DB.execSQL(CREATE_RETORNO_PEDIDO);
		DB.execSQL(CREATE_RETORNO_PEDIDO_ITENS);
        DB.execSQL(CREATE_TIPO_COBRANCA);
        DB.execSQL(CREATE_AGENTE_COBRADOR);
        //DB.execSQL(CREATE_CONDICOES_PAGAMENTO);
        //DB.execSQL(CREATE_TRANSPORTADORA);
        DB.execSQL(CREATE_UNIDADE_MEDIDA);
        DB.execSQL(CREATE_TIPO_PEDIDO);
        //DB.execSQL(CREATE_TIPO_DOCUMENTO);
        //DB.execSQL(CREATE_DESCONTO);
        DB.execSQL(CREATE_PRAZO);
        DB.execSQL(CREATE_SINCRONIZACAO);
        DB.execSQL(CREATE_LOCALIZACAO);
        DB.execSQL(CREATE_LOG_ERRO);

        // TABELAS PREENCHIDAS SEM SINCRONIZAÇÃO
        DB.execSQL(CREATE_BANCOS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int oldVersion, int newVersion) {
        onCreate(DB);
    }

    @Override
    protected void finalize() throws Throwable {
        this.close();
        super.finalize();
    }

    public boolean exportDatabase() {

        Boolean result = false;

        StackTraceElement el = Thread.currentThread().getStackTrace()[2];
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        File dataDirectory = Environment.getDataDirectory();

        FileChannel source = null;
        FileChannel destination = null;
        Calendar mCalendar = Calendar.getInstance();
        String currentDBPath = "/data/" + mContext.getApplicationInfo().packageName + "/databases/"+DATABASE;
        String backupDBPath = sonicConstants.LOCAL_DATA_BACKUP+"Sonic-bkp-"+(new SimpleDateFormat("dd-MM-yyy-HHmm").format(mCalendar.getTime()))+".db";
        File currentDB = new File(dataDirectory, currentDBPath);
        File backupDB = new File(externalStorageDirectory, backupDBPath);

        try {
            source = new FileInputStream(currentDB).getChannel();
            destination = new FileOutputStream(backupDB).getChannel();
            destination.transferFrom(source, 0, source.size());

            result = true;

        } catch (IOException e) {
            result = false;
            mPrefs.Geral.setError(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (source != null) source.close();
            } catch (IOException e) {
                mPrefs.Geral.setError(e.getMessage());
                e.printStackTrace();
            }
            try {
                if (destination != null) destination.close();
            } catch (IOException e) {
                mPrefs.Geral.setError(e.getMessage());
                e.printStackTrace();
            }
        }
        return  result;
    }

}
