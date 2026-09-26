package br.com.mecaniQA.api.dtos;

import br.com.mecaniQA.api.models.StatusOS;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrdemServicoDTO {

    private Long id;
    private Long idCliente;
    private LocalDateTime dataAbertura;
    private LocalDateTime dataFechamento;
    private StatusOS status;
    private List<Long> servicosIds = new ArrayList<>();
    private List<Long> pecasIds = new ArrayList<>();
    private double valorTotal;
    private double desconto;

    public OrdemServicoDTO(){

    }

    //Getters e Setters
    public Long getId() {return id;}
    public void setId(Long id) { this.id = id; }

    public Long getIdCliente() { return idCliente; }
    public void setIdCliente(Long idCliente) { this.idCliente = idCliente; }

    public LocalDateTime getDataAbertura() { return dataAbertura; }
    public void setDataAbertura(LocalDateTime dataAbertura) { this.dataAbertura = dataAbertura; }

    public LocalDateTime getDataFechamento() { return dataFechamento; }
    public void setDataFechamento(LocalDateTime dataFechamento) { this.dataFechamento = dataFechamento; }

    public StatusOS getStatus() { return status; }
    public void setStatus(StatusOS status) { this.status = status; }

    public List<Long> getServicos() { return servicosIds;}
    public void setServicos(List<Long> servicos) { this.servicosIds = servicosIds; }

    public List<Long> getPecas() { return pecasIds; }
    public void setPecas(List<Long> pecasIds) { this.pecasIds = pecasIds; }

    public double getValorTotal() { return valorTotal; }
    public void setValorTotal(double valorTotal) { this.valorTotal = valorTotal; }

    public double getDesconto() { return desconto; }
    public void setDesconto(double desconto) { this.desconto = desconto; }
}
