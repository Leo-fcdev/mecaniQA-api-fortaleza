package br.com.mecaniQA.api.dtos;

import java.time.LocalDateTime;

public class ServicoDTO {
    private Long id;
    private String nomeServico;
    private int tempoEstimado;
    private double custoTabelado;
    private LocalDateTime dataCadastro;
    private LocalDateTime dataUltimaAtualizacao;

    public ServicoDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNomeServico() { return nomeServico; }
    public void setNomeServico(String nomeServico) { this.nomeServico = nomeServico; }

    public int getTempoEstimado() { return tempoEstimado; }
    public void setTempoEstimado(int tempoEstimado) { this.tempoEstimado = tempoEstimado; }

    public double getCustoTabelado() { return custoTabelado; }
    public void setCustoTabelado(double custoTabelado) { this.custoTabelado = custoTabelado; }

    public LocalDateTime getDataCadastro() { return dataCadastro; }
    public void setDataCadastro(LocalDateTime dataCadastro) { this.dataCadastro = dataCadastro; }

    public LocalDateTime getDataUltimaAtualizacao() { return dataUltimaAtualizacao; }
    public void setDataUltimaAtualizacao(LocalDateTime dataUltimaAtualizacao) { this.dataUltimaAtualizacao = dataUltimaAtualizacao; }
}