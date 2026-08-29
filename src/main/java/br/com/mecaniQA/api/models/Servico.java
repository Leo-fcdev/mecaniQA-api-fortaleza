package br.com.mecaniQA.api.models;

import java.time.LocalDateTime;

public class Servico {

    private Long id;
    private String nomeServico;
    private int tempoEstimado;
    private double custoTabelado;
    private LocalDateTime dataCadastro;
    private LocalDateTime dataUltimaAtualizacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getNomeServico() {
        return nomeServico;
    }

    public void setNomeServico(String nomeServico) {
        this.nomeServico = nomeServico;
    }

    public void setTempoEstimado(int tempoEstimado) {
        this.tempoEstimado = tempoEstimado;
    }

    public void setDataUltimaAtualizacao(LocalDateTime dataUltimaAtualizacao) {
        this.dataUltimaAtualizacao = dataUltimaAtualizacao;
    }

    public void setCustoTabelado(double custoTabelado) {
        this.custoTabelado = custoTabelado;
    }

    public int getTempoEstimado() {
        return tempoEstimado;
    }

    public double getCustoTabelado() {
        return custoTabelado;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public LocalDateTime getDataUltimaAtualizacao() {
        return dataUltimaAtualizacao;
    }

    public Servico(Long id, String nomeServico, int tempoEstimado, double custoTabelado) {
        this.id = id;
        this.nomeServico = nomeServico;
        this.tempoEstimado = tempoEstimado;
        this.custoTabelado = custoTabelado;
    }
}
