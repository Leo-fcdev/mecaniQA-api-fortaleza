package br.com.mecaniQA.api.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrdemServico {

    private Long id;
    private Long idCliente;
    private LocalDateTime dataAbertura;
    private LocalDateTime dataFechamento;
    private StatusOS status;
    private List<Servico> servicos;
    private List<Peca> pecas;
    private double valorTotal;
    private double desconto;

    public OrdemServico() {
        this.servicos = new ArrayList<>();
        this.pecas = new ArrayList<>();
    }

    public OrdemServico(Long id, Long idCliente, LocalDateTime dataAbertura, StatusOS status,
                        List<Servico> servicos, List<Peca> pecas, double valorTotal, double desconto) {
        this.id = id;
        this.idCliente = idCliente;
        this.dataAbertura = dataAbertura;
        this.status = status;
        this.servicos = servicos != null ? servicos : new ArrayList<>();
        this.pecas = pecas != null ? pecas : new ArrayList<>();
        this.valorTotal = valorTotal;
        this.desconto = desconto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public LocalDateTime getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(LocalDateTime dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public LocalDateTime getDataFechamento() {
        return dataFechamento;
    }

    public void setDataFechamento(LocalDateTime dataFechamento) {
        this.dataFechamento = dataFechamento;
    }

    public StatusOS getStatus() {
        return status;
    }

    public void setStatus(StatusOS status) {
        this.status = status;
    }

    public List<Servico> getServicos() {
        return servicos;
    }

    public void setServicos(List<Servico> servicos) {
        this.servicos = servicos;
    }

    public List<Peca> getPecas() {
        return pecas;
    }

    public void setPecas(List<Peca> pecas) {
        this.pecas = pecas;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public double getDesconto() {
        return desconto;
    }

    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }
}
