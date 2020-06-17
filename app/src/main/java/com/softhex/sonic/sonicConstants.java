package com.softhex.sonic;

public class sonicConstants {

    // DATABASE
    public final static String DATABASE = "Sonic";
    public final static String TB_SITE = "site";
    public final static String TB_FTP = "ftp";
    public final static String TB_EMPRESA = "empresa";
    public final static String TB_MATRIZ = "matriz";
    public final static String TB_NIVEL_ACESSO = "nivel_acesso";
    public final static String TB_USUARIO = "usuario";
    public final static String TB_EMPRESA_USUARIO = "empresa_usuario";
    public final static String TB_CLIENTE = "cliente";
    public final static String TB_EMPRESA_CLIENTE = "empresa_cliente";
    public final static String TB_GRUPO_CLIENTE = "grupo_cliente";
    public final static String TB_RANKING_CLIENTE = "ranking_cliente";
    public final static String TB_CLIENTE_SEM_COMPRA = "cliente_sem_compra";
    public final static String TB_PRODUTO = "produto";
    public final static String TB_GRUPO_PRODUTO = "grupo_produto";
    public final static String TB_ROTA = "rota";
    public final static String TB_ROTA_PESSOAL = "rota_pessoal";
    public final static String TB_RANKING_PRODUTO = "ranking_produto";
    public final static String TB_ESTOQUE_PRODUTO = "estoque_produto";
    public final static String TB_BLOQUEIO_PRODUTO = "bloqueio_produto";
    public final static String TB_TABELA_PRECO = "tabela_preco";
    public final static String TB_TABELA_PRECO_PRODUTO = "tabela_preco_produto";
    public final static String TB_TABELA_PRECO_EMPRESA = "tabela_preco_empresa";
    public final static String TB_FINANCEIRO = "financeiro";
    public final static String TB_TITULO = "titulo";
    public final static String TB_RETORNO_PEDIDO = "retorno_pedido";
    public final static String TB_RETORNO_PEDIDO_ITEM = "retorno_pedido_item";
    public final static String TB_TIPO_COBRANCA = "tipo_cobranca";
    public final static String TB_AGENTE_COBRADOR = "agente_cobrador";
    public final static String TB_CONDICAO_PAGAMENTO = "condicao_pagamento";
    public final static String TB_VENDA = "venda";
    public final static String TB_VENDA_ITEM = "venda_item";
    public final static String TB_TRANSPORTADORA = "transportadora";
    public final static String TB_UNIDADE_MEDIDA= "unidade_medida";
    public final static String TB_TIPO_PEDIDO = "tipo_pedido";
    public final static String TB_TIPO_DOCUMENTO = "tipo_documento";
    public final static String TB_DESCONTO = "desconto";
    public final static String TB_FORMA_PAGAMENTO = "forma_pagamento";
    public final static String TB_PRAZO = "prazo";
    public final static String TB_TABELA_PRECO_CLIENTE = "tabela_preco_cliente";
    public final static String TB_FRETE = "frete";
    public final static String TB_ULTIMAS_COMPRAS = "ultimas_compras";
    public final static String TB_ULTIMAS_COMPRAS_ITENS = "ultimas_compras_itens";
    public final static String TB_IMPRESSORA = "impressora";
    public final static String TB_AVISO = "aviso";
    public final static String TB_AVISO_LIDO = "aviso_lido";
    public final static String TB_SINCRONIZACAO = "sincronizacao";
    public final static String TB_LOCALIZACAO = "localizacao";
    public final static String TB_LOG_ERRO = "log_erro";
    public final static String TB_TODAS = "todas";

    // TABELAS PREENCHIDAS SEM SINCRONIZAÇÃO
    public final static String TB_BANCOS = "bancos";

    // REQUEST CODE
    public final static int ROTA_ADD_REQUEST_CODE = 10;

    // TIPO SINCRONIZACAO
    public final static String TIPO_SINC_DADOS = "dados";
    public final static String TIPO_SINC_IMAGENS = "imagens";

    // FINAL STRINGS
    public final static String FTP_SITES = "sites/";
    public final static String FTP_SERVER = "177.129.223.102";
    public final static String FTP_USER = "sonic";
    public final static String FTP_PASS = "@sonic@";
    public final static String FTP_EMPRESA = "/server_sonic/empresa/";
    public final static String FTP_USUARIOS = "/server_sonic/usuarios/";
    public final static String FTP_IMAGENS = "/server_sonic/imagens/";
    public final static String FTP_ESTOQUE = "/server_sonic/estoque/";
    public final static String FTP_LOCALIZACAO = "/server_sonic/localizacao/";
    public final static String LOCAL_TEMP = "/Sonic/Temp/";
    public final static String LOCAL_DATA = "/Sonic/Data/";
    public final static String LOCAL_IMG_CLIENTES = "/Sonic/Midia/Imagens/Clientes/";
    public final static String LOCAL_IMG_CATALOGO = "/Sonic/Midia/Imagens/Catalogo/";
    public final static String LOCAL_IMG_USUARIO = "/Sonic/Midia/Imagens/Usuario/";
    public final static String LOCAL_IMG_EMPRESA = "/Sonic/Midia/Imagens/Empresa/";
    public final static String LOCAL_DATA_BACKUP = "/Sonic/Data/Backup/";

    // NON FINAL STRINGS
    public static String EMPRESA_SELECIONADA_NOME = "";
    public static String USUARIO_ATIVO_CARGO = "";
    public static String USUARIO_ATIVO_META_VENDA = "";
    public static String DOWNLOAD_TYPE = "";
    public static String GRUPO_CLIENTES_RANKING = "TODOS";
    public static String GRUPO_CLIENTES_CNPJ = "TODOS";
    public static String GRUPO_CLIENTES_CPF = "TODOS";
    public static String GRUPO_CLIENTES_CNPJ_LIMPAR = "";
    public static String GRUPO_CLIENTES_CPF_LIMPAR = "";
    public static String GRUPO_PRODUTOS_LISTA = "TODOS";
    public static String GRUPO_PRODUTOS_RANKING = "TODOS";
    public static String GRUPO_ROTA_STATUS = "TODOS";
    public static int GRUPO_ROTA_STATUS_INT;
    public static String GRUPO_ROTA_STATUS_LIMPAR = "";

    // FINAL INTEGERS
    public static Integer USUARIO_ATIVO_META_VISITA = 0;
    public static Integer BACK;
    public static Integer TOTAL_ITENS_LOAD = 10;

    // NON FINAL INTEGERS
    public static Integer USUARIO_ATIVO_NIVEL = 0;
    public static Integer EMPRESA_SELECIONADA_ID = 0;
    public static Integer TOTAL_IMAGES_SLIDE = 3;

    // FINAL BOOLEAN
    public static Boolean EMP_TESTE = false;
    public static Boolean REFRESH_HOME = false;

}
