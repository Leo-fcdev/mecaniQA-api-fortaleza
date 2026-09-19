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

    private OrdemServico(OrdemServicoBuilder builder) {
        this.id = builder.id;
        this.idCliente = builder.idCliente;
        this.dataAbertura = builder.dataAbertura;
        this.dataFechamento = builder.dataFechamento;
        this.status = builder.status;
        this.servicos = builder.servicos != null ? builder.servicos : new ArrayList<>();
        this.pecas = builder.pecas != null ? builder.pecas : new ArrayList<>();
        this.valorTotal = builder.valorTotal;
        this.desconto = builder.desconto;
    }


    public static OrdemServicoBuilder builder() {
        return new OrdemServicoBuilder();
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getIdCliente() { return idCliente; }
    public void setIdCliente(Long idCliente) { this.idCliente = idCliente; }

    public LocalDateTime getDataAbertura() { return dataAbertura; }
    public void setDataAbertura(LocalDateTime dataAbertura) { this.dataAbertura = dataAbertura; }

    public LocalDateTime getDataFechamento() { return dataFechamento; }
    public void setDataFechamento(LocalDateTime dataFechamento) { this.dataFechamento = dataFechamento; }

    public StatusOS getStatus() { return status; }
    public void setStatus(StatusOS status) { this.status = status; }

    public List<Servico> getServicos() { return servicos; }
    public void setServicos(List<Servico> servicos) { this.servicos = servicos; }

    public List<Peca> getPecas() { return pecas; }
    public void setPecas(List<Peca> pecas) { this.pecas = pecas; }

    public double getValorTotal() { return valorTotal; }
    public void setValorTotal(double valorTotal) { this.valorTotal = valorTotal; }

    public double getDesconto() { return desconto; }
    public void setDesconto(double desconto) { this.desconto = desconto; }

    public static class OrdemServicoBuilder {

        private Long id;
        private Long idCliente;
        private LocalDateTime dataAbertura;
        private LocalDateTime dataFechamento;
        private StatusOS status;
        private List<Servico> servicos = new ArrayList<>();
        private List<Peca> pecas = new ArrayList<>();
        private double valorTotal;
        private double desconto;

        public OrdemServicoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public OrdemServicoBuilder idCliente(Long idCliente) {
            this.idCliente = idCliente;
            return this;
        }

        public OrdemServicoBuilder dataAbertura(LocalDateTime dataAbertura) {
            this.dataAbertura = dataAbertura;
            return this;
        }

        public OrdemServicoBuilder dataFechamento(LocalDateTime dataFechamento) {
            this.dataFechamento = dataFechamento;
            return this;
        }

        public OrdemServicoBuilder status(StatusOS status) {
            this.status = status;
            return this;
        }

        public OrdemServicoBuilder servicos(List<Servico> servicos) {
            this.servicos = servicos;
            return this;
        }

        public OrdemServicoBuilder pecas(List<Peca> pecas) {
            this.pecas = pecas;
            return this;
        }

        public OrdemServicoBuilder valorTotal(double valorTotal) {
            this.valorTotal = valorTotal;
            return this;
        }

        public OrdemServicoBuilder desconto(double desconto) {
            this.desconto = desconto;
            return this;
        }

        public OrdemServico build() {
            return new OrdemServico(this);
        }
    }
}