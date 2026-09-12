package br.com.mecaniQA.api.models;

public class ItemPedidoPeca {

    private Long id;
    private PedidoPecas pedidoPecas;
    private Peca peca;
    private int quantidade;

    public ItemPedidoPeca() {
    }

    public ItemPedidoPeca(Long id, PedidoPecas pedidoPecas, Peca peca, int quantidade) {
        this.id = id;
        this.pedidoPecas = pedidoPecas;
        this.peca = peca;
        this.quantidade = quantidade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PedidoPecas getPedidoPecas() {
        return pedidoPecas;
    }

    public void setPedidoPecas(PedidoPecas pedidoPecas) {
        this.pedidoPecas = pedidoPecas;
    }

    public Peca getPeca() {
        return peca;
    }

    public void setPeca(Peca peca) {
        this.peca = peca;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}