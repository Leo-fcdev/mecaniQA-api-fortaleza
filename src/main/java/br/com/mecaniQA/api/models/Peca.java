package models;

import java.time.LocalDateTime;

public class Peca {

    private long id;
    private String nome;
    private String codigoDeBarras;
    private String fornecedor;
    private int qtdEstoque;
    private double precoCusto;
    private double precoVenda;
    private LocalDateTime dataCadastro;
    private LocalDateTime dataUltimaAtualizacao;
    private CategoriaPeca categoriaPeca;
    private String cor;
    private String tamanho;

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
    }

    public void setQtdEstoque(int qtdEstoque) {
        this.qtdEstoque = qtdEstoque;
    }

    public void setPrecoCusto(double precoCusto) {
        this.precoCusto = precoCusto;
    }

    public void setPrecoVenda(double precoVenda) {
        this.precoVenda = precoVenda;
    }

    public void setCategoriaPeca(CategoriaPeca categoriaPeca) {
        this.categoriaPeca = categoriaPeca;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCodigoDeBarras() {
        return codigoDeBarras;
    }

    public String getFornecedor() {
        return fornecedor;
    }

    public int getQtdEstoque() {
        return qtdEstoque;
    }

    public double getPrecoCusto() {
        return precoCusto;
    }

    public double getPrecoVenda() {
        return precoVenda;
    }

    public LocalDateTime getDataUltimaAtualizacao() {
        return dataUltimaAtualizacao;
    }

    public CategoriaPeca getCategoriaPeca() {
        return categoriaPeca;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public void setDataUltimaAtualizacao(LocalDateTime dataUltimaAtualizacao) {
        this.dataUltimaAtualizacao = dataUltimaAtualizacao;
    }

    public Peca(long id, String nome, String codigoDeBarras, String fornecedor, double precoVenda, double precoCusto, CategoriaPeca categoriaPeca) {
        this.id = id;
        this.nome = nome;
        this.codigoDeBarras = codigoDeBarras;
        this.fornecedor = fornecedor;
        this.precoVenda = precoVenda;
        this.precoCusto = precoCusto;
        this.categoriaPeca = categoriaPeca;
    }
}
