package com.softhex.sonic;

import android.content.Context;

/**
 * Created by Administrador on 03/08/2017.
 */

public class sonicConstants {

    // PATHS
    public static final String LOCAL_IMG_CATALOGO = "/Sonic/media/imagens/catalogo/";
    public static final String LOCAL_IMG_CLIENTES = "/Sonic/media/imagens/clientes/";
    public static final String LOCAL_IMG_PERFIL = "/Sonic/media/imagens/perfil/";
    public static final String LOCAL_DATA_BACKUP = "/Sonic/data/backup/";
    public static final String LOCAL_DATA = "/Sonic/data/";
    public static final String LOCAL_TMP = "/Sonic/temp/";

    // DATABASE / TABLES
    public static final String DATABASE = "sonic";
    public static final String TB_SITE = "site";
    public static final String TB_FTP = "ftp";
    public static final String TB_EMPRESAS = "empresas";
    public static final String TB_USUARIOS = "usuarios";
    public static final String TB_EMPRESAS_USUARIOS = "empresas_usuarios";
    public static final String TB_HISTORICO_USUARIO = "historico_usuario";
    public static final String TB_NIVEL_ACESSO = "nivel_acesso";
    public static final String TB_CLIENTES = "clientes";
    public static final String TB_EMPRESAS_CLIENTES = "empresas_clientes";
    public static final String TB_GRUPO_CLIENTES = "grupo_clientes";
    public static final String TB_RANKING_CLIENTES = "ranking_clientes";
    public static final String TB_CLIENTES_SEM_COMPRA = "clientes_sem_compra";
    public static final String TB_PRODUTOS = "produtos";
    public static final String TB_GRUPO_PRODUTOS = "grupo_produtos";
    public static final String TB_ROTA = "rota";
    public static final String TB_ESTOQUE = "estoque";
    public static final String TB_RETORNO_PEDIDO = "retorno_pedido";
    public static final String TB_RETORNO_PEDIDO_ITENS = "retorno_pedido_itens";
    public static final String TB_FINANCEIRO = "financeiro";
    public static final String TB_TITULOS = "titulos";
    public static final String TB_TIPO_COBRANCA = "tipo_cobranca";
    public static final String TB_CONDICOES_PAGAMENTO = "condicoes_pagamento";
    public static final String TB_PRECO_PRODUTO = "preco_produto";
    public static final String TB_TABELA_PRECO = "tabela_preco";
    public static final String TB_TABELA_PRECO_PRODUTO = "tabela_preco_produto";
    public static final String TB_TABELA_PRECO_CLIENTE = "tabela_preco_cliente";
    public static final String TB_VENDAS = "vendas";
    public static final String TB_VENDAS_ITENS = "vendas_itens";
    public static final String TB_RANKING_PRODUTOS = "ranking_produtos";
    public static final String TB_TRANSPORTADORA = "transportadora";
    public static final String TB_UNIDADE_MEDIDA = "unidade_medida";
    public static final String TB_TIPO_PEDIDO = "tipo_pedido";
    public static final String TB_TIPO_DOCUMENTO = "tipo_documento";
    public static final String TB_DESCONTO = "desconto";
    public static final String TB_FORMA_PAGAMENTO = "forma_pagamento";
    public static final String TB_PRAZO = "prazo";
    public static final String TB_FRETE = "frete";
    public static final String TB_AVISOS = "avisos";
    public static final String TB_AVISOS_LIDOS = "avisos_lidos";
    public static final String TB_LOCALIZACAO = "localizacao";
    public static final String TB_SINCRONIZACAO = "sincronizacao";
    public static final String TB_IMPRESSORAS = "impressoras";
    public static final String TB_LOG_ERRO = "log_erro";

    // PUT EXTRAS
    public static String PUT_EXTRA_CLIENTE_NOME = "CLIENTE_NOME";
    public static String PUT_EXTRA_CLIENTE_ID = "PUT_EXTRA_CLIENTE_ID";
    public static int PUT_EXTRA_CLIENTE_ID_INT = 0;

    // ON LOGED

    public static int USUARIO_ATIVO_ID = 0;
    public static int USUARIO_ATIVO_NIVEL = 0;
    public static String USUARIO_ATIVO_NOME = "USUARIO";
    public static String USUARIO_ATIVO_CARGO = "CARGO";
    public static String USUARIO_ATIVO_META_VENDA = "R$ 0,00";
    public static int USUARIO_ATIVO_META_VISITA = 0;
    public static String EMPRESA_SELECIONADA_NOME = "EMPRESA";
    public static int EMPRESA_SELECIONADA_ID = 0;

    // OTHERS
    public static int TOTAL_IMAGES_SLIDE = 3;
    public static String EMP_EXIST = "";
    public static String EMP_SITE = "";
    public static boolean EMP_TESTE = false;
    public static boolean REFRESH_HOME = false;
    public static String DOWNLOAD_TYPE = "";
    public static String USER_IMEI = "";
    public static String FTP_SERVER = "45.234.150.253";
    public static int FTP_PORT = 21;
    public static String FTP_USER = "sonic";
    public static String FTP_PASS = "463215S0n1c";
    public static final String FTP_USUARIOS = "server_sonic/usuarios/";
    public static final String FTP_IMAGENS = "server_sonic/imagens/";
    public static final String FTP_ESTOQUE = "server_sonic/estoque/";
    public static final String FTP_LOCALIZACAO = "server_sonic/localizacao/";
    public static final String CURRENCY = "BRL";
    public static final String CURRENCY_SIMBOl = "R$ ";
    public static final int CURRENCY_DIGITS = 2;

    // CONSTANTES DE FRAGMENTOS - RETORNO
    public static String RETORNO_PENDENTE_DATA = "Semana atual";
    public static String RETORNO_FATURADO_DATA = "Semana atual";
    public static String RETORNO_EM_ROTA_DATA = "Semana atual";
    public static String RETORNO_ENTREGUE_DATA = "Semana atual";
    public static String RETORNO_CANCELADO_DATA = "Semana atual";

    // CONSTANTES DE FRAGMENTOS
    public static String GRUPO_PRODUTOS = "TODOS";
    public static String GRUPO_PRODUTOS_RANKING = "TODOS";
    public static String GRUPO_CLIENTES = "TODOS";
    public static String GRUPO_CLIENTES_RANKING = "TODOS";


}
