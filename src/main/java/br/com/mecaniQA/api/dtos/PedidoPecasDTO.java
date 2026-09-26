package br.com.mecaniQA.api.dtos;

import br.com.mecaniQA.api.models.StatusPedidoPeca;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PedidoPecasDTO {

    private Long id;
    private LocalDateTime dataPedido;
    private LocalDateTime dataPrevisao;
    private StatusPedidoPeca status;
    private double valorTotal;
    private String formaPagamento;
    private String observacoes;
    private List<ItemPedidoPecaDTO>itens = new ArrayList<>();

    public PedidoPecasDTO() {
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getDataPedido() { return dataPedido; }
    public void setDataPedido(LocalDateTime dataPedido) { this.dataPedido = dataPedido; }

    public LocalDateTime getDataPrevisao() { return dataPrevisao; }
    public void setDataPrevisao(LocalDateTime dataPrevisao) { this.dataPrevisao = dataPrevisao; }

    public StatusPedidoPeca getStatus() { return status; }
    public void setStatus(StatusPedidoPeca status) { this.status = status; }

    public double getValorTotal() { return valorTotal; }
    public void setValorTotal(double valorTotal) { this.valorTotal = valorTotal; }

    public String getFormaPagamento() { return formaPagamento; }
    public void setFormaPagamento(String formaPagamento) { this.formaPagamento = formaPagamento; }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }

    public List <ItemPedidoPecaDTO>getItens() { return itens; }
    public void setItens(List<ItemPedidoPecaDTO> itens) { this.itens = itens; }
}