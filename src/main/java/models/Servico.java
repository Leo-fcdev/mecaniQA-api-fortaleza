package models;

import java.time.LocalDateTime;

public class Servico {

    private long id;
    private String nomeServico;
    private int tempoEstimado;
    private double custoTabelado;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataUltimaAtualizacao;

    public long getId() {
        return id;
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

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public LocalDateTime getDataUltimaAtualizacao() {
        return dataUltimaAtualizacao;
    }

    public Servico(long id, String nomeServico, int tempoEstimado, double custoTabelado) {
        this.id = id;
        this.nomeServico = nomeServico;
        this.tempoEstimado = tempoEstimado;
        this.custoTabelado = custoTabelado;
    }
}
