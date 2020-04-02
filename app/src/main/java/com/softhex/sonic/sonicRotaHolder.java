package com.softhex.sonic;

public class sonicRotaHolder{


    int id;
    int codigo;
    int codigo_cliente;
    int codigo_empresa;
    String nome_fantasia;
    String razao_social;
    String grupo_cliente;
    String logradouro;
    String bairro;
    String municipio;
    String uf;
    String cep;
    int tipo;
    int status;
    String data_agendamento;
    String hora_agendamento;
    String atendente;
    int ordem;
    String observacao;
    String data_inicio;
    String hora_inicio;
    String data_fim;
    String hora_fim;
    int situacao;
    String negativacao;
    String cancelamento;
    String latitude;
    String longitude;


    public sonicRotaHolder() {
    }

    public int getCodigo() {
        return codigo;
    }

    public int getCodigoEmpresa() {
        return codigo_empresa;
    }

    public int getCodigoCliente() {
        return codigo_cliente;
    }

    public String getNomeFantasia() {
        return nome_fantasia;
    }

    public String getRazaoSocial() {
        return razao_social;
    }

    public String getGrupoCliente() {
        return grupo_cliente;
    }

    public String getLogradrouro() {
        return logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public String getMunicipio() {
        return municipio;
    }

    public String getUf(){return  uf;}

    public String getCep(){return  cep;}

    public String getEnderecoCompleto() {
        return logradouro+", " + bairro+", "+municipio+" - "+uf;
    }

    public int getTipo(){return  tipo;};

    public int getStatus(){return  status;};

    public String getDataAgendamento() {
        return data_agendamento;
    }

    public String getHoraAgendamento() {
        return hora_agendamento;
    }

    public String getAtendente() {
        return atendente;
    }

    public int getOrdem() {
        return ordem;
    }

    public String getObservacao(){return observacao;}

    public String getDataInicio(){return data_inicio;}

    public String getDataFim(){return data_fim;}

    public String getHoraInicio(){return hora_inicio;}

    public String getHoraFim(){return hora_fim;}

    public int getSituacao(){return situacao;}

    public String getNegativacao(){return negativacao;}

    public String getCancelamento(){return cancelamento;}

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setCodigoEmpresa(int codigo_empresa) {
        this.codigo_empresa = codigo_empresa;
    }

    public void setCodigoCliente(int codigo_cliente) {
        this.codigo_cliente = codigo_cliente;
    }

    public void setNomeFantasia(String nome_fantasia) {
        this.nome_fantasia = nome_fantasia;
    }

    public void setRazaoSocial(String razao_social) {
        this.razao_social = razao_social;
    }

    public void setGrupoCliente(String grupo_cliente) {
        this.grupo_cliente = grupo_cliente;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }
    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public void setUf(String uf){this.uf = uf;}

    public void setCep(String cep){this.cep = cep;}

    public void setTipo(int tipo){this.tipo = tipo;}
    public void setStatus(int status) {
        this.status = status;
    }

    public void setDataAgendamento(String data_agendamento) {
        this.data_agendamento = data_agendamento;
    }

    public void setHoraAgendamento(String hora_agendamento) {
        this.hora_agendamento = hora_agendamento;
    }

    public void setAtendente(String atendente) {
        this.atendente = atendente;
    }

    public void setOrdem(int ordem) {
        this.ordem = ordem;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public void setDataInicio(String data_inicio) {
        this.data_inicio = data_inicio;
    }
    public void setHoraInicio(String hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    public void setDataFim(String data_fim) {
        this.data_fim = data_fim;
    }
    public void setHoraFim(String hora_fim) {
        this.hora_fim= hora_fim;
    }
    public void setSituacao(int situacao) {
        this.situacao = situacao;
    }
    public void setNegativacao(String negativacao) {
        this.negativacao = negativacao;
    }

    public void setCancelamento(String cancelamento) {
        this.cancelamento = cancelamento;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
