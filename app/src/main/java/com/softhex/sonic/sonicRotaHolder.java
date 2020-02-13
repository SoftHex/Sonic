package com.softhex.sonic;

public class sonicRotaHolder{


    int id;
    int codigo;
    int codigo_cliente;
    int codigo_empresa;
    String nome_fantasia;
    String razao_social;
    String grupo_cliente;
    String endereco;
    String bairro;
    String municipio;
    String endereco_completo;
    String tipo;
    int situacao;
    String data_agendamento;
    String hora_agendamento;
    int ordem;
    String observacao;
    String data_inicio;
    String hora_inicio;
    String data_fim;
    String hora_fim;
    String conclusao;
    int negativacao;
    String cancelamento;
    String latitude;
    String longitude;


    public sonicRotaHolder() {
    }

    public sonicRotaHolder(int codigo,
                           int codigo_cliente,
                           int codigo_empresa,
                           String nome_fantasia,
                           String razao_social,
                           String grupo_cliente,
                           String endereco,
                           String bairro,
                           String municipio,
                           String endereco_completo,
                           String tipo,
                           int situacao,
                           String data_agendamento,
                           String hora_agendamento,
                           int ordem,
                           String observacao,
                           String data_inicio,
                           String hora_inicio,
                           String data_fim,
                           String hora_fim,
                           String conclusao,
                           int negativacao,
                           String cancelamento,
                           String latitude,
                           String longitude) {
        this.codigo = codigo;
        this.codigo_cliente = codigo_cliente;
        this.codigo_empresa = codigo_empresa;
        this.nome_fantasia = nome_fantasia;
        this.razao_social = razao_social;
        this.grupo_cliente = grupo_cliente;
        this.endereco = endereco;
        this.bairro = bairro;
        this.municipio = municipio;
        this.endereco_completo = endereco_completo;
        this.tipo = tipo;
        this.situacao = situacao;
        this.data_agendamento = data_agendamento;
        this.hora_agendamento = hora_agendamento;
        this.ordem = ordem;
        this.observacao = observacao;
        this.data_inicio = data_inicio;
        this.hora_inicio = hora_inicio;
        this.data_fim = data_fim;
        this.hora_fim = hora_fim;
        this.negativacao = negativacao;
        this.cancelamento = cancelamento;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getCodigo() {
        return codigo;
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

    public String getEndereco() {
        return endereco;
    }

    public String getBairro() {
        return bairro;
    }

    public String getMunicipio() {
        return municipio;
    }

    public String getEnderecoCompleto() {
        return endereco+", "+bairro+", "+municipio;
    }

    public String getTipo(){return  tipo;};

    public int getSituacao(){return  situacao;};

    public String getDataAgendamento() {
        return data_agendamento;
    }

    public String getHoraAgendamento() {
        return hora_agendamento;
    }

    public int getOrdem() {
        return ordem;
    }

    public String getObservacao(){return observacao;}

    public String getDataInicio(){return data_inicio;}

    public String getDataFim(){return data_fim;}

    public String getHoraInicio(){return hora_inicio;}

    public String getHoraFim(){return hora_fim;}

    public String getConclusao(){return conclusao;}

    public int getNegativacao(){return negativacao;}

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

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }
    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public void setEnderecoCompleto(String endereco_completo) {
        this.endereco_completo = endereco_completo;
    }

    public void setTipo(String tipo){this.tipo = tipo;}
    public void setSituacao(int situacao) {
        this.situacao = situacao;
    }

    public void setDataAgendamento(String data_agendamento) {
        this.data_agendamento = data_agendamento;
    }

    public void setHoraAgendamento(String hora_agendamento) {
        this.hora_agendamento = hora_agendamento;
    }

    public void setOrdem(int ordem) {
        this.ordem = ordem;
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
    public void setConclusao(String conclusao) {
        this.conclusao = conclusao;
    }
    public void setNegativacao(int negativacao) {
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
