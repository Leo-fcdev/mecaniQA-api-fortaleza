package br.com.mecaniQA.api.dtos;

public class ItemPedidoPecaDTO {

    private Long idPeca;
    private int quantidade;

    public ItemPedidoPecaDTO() {
    }

    public Long getIdPeca() { return idPeca; }
    public void setIdPeca(Long idPeca) { this.idPeca = idPeca; }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }
}