package br.com.mecaniQA.api.dtos;

import br.com.mecaniQA.api.models.CategoriaPeca;
import java.time.LocalDateTime;

public class PecaDTO {
    private Long id;
    private String nome;
    private String codigoDeBarras;
    private String fornecedor;
    private int qtdEstoque;
    private double precoCusto;
    private double precoVenda;
    private CategoriaPeca categoriaPeca;
    private LocalDateTime dataCadastro;
    private LocalDateTime dataUltimaAtualizacao;

    public PecaDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCodigoDeBarras() { return codigoDeBarras; }
    public void setCodigoDeBarras(String codigoDeBarras) { this.codigoDeBarras = codigoDeBarras; }

    public String getFornecedor() { return fornecedor; }
    public void setFornecedor(String fornecedor) { this.fornecedor = fornecedor; }

    public int getQtdEstoque() { return qtdEstoque; }
    public void setQtdEstoque(int qtdEstoque) { this.qtdEstoque = qtdEstoque; }

    public double getPrecoCusto() { return precoCusto; }
    public void setPrecoCusto(double precoCusto) { this.precoCusto = precoCusto; }

    public double getPrecoVenda() { return precoVenda; }
    public void setPrecoVenda(double precoVenda) { this.precoVenda = precoVenda; }

    public CategoriaPeca getCategoriaPeca() { return categoriaPeca; }
    public void setCategoriaPeca(CategoriaPeca categoriaPeca) { this.categoriaPeca = categoriaPeca; }

    public LocalDateTime getDataCadastro() { return dataCadastro; }
    public void setDataCadastro(LocalDateTime dataCadastro) { this.dataCadastro = dataCadastro; }

    public LocalDateTime getDataUltimaAtualizacao() { return dataUltimaAtualizacao; }
    public void setDataUltimaAtualizacao(LocalDateTime dataUltimaAtualizacao) { this.dataUltimaAtualizacao = dataUltimaAtualizacao; }
}