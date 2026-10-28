package br.com.mecaniQA.api.mappers;

import br.com.mecaniQA.api.dtos.ItemPedidoPecaDTO;
import br.com.mecaniQA.api.dtos.PedidoPecasDTO;
import br.com.mecaniQA.api.models.ItemPedidoPeca;
import br.com.mecaniQA.api.models.Peca;
import br.com.mecaniQA.api.models.PedidoPecas;
import br.com.mecaniQA.api.models.StatusPedidoPeca;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PedidoPecasMapper {

    private PedidoPecasMapper() {}

    public static PedidoPecas fromResultSet(ResultSet rs) throws SQLException{
        PedidoPecas pedido = new PedidoPecas();
        pedido.setId(rs.getLong("id"));
        pedido.setValorTotal(rs.getDouble("valor_total"));
        pedido.setFormaPagamento(rs.getString("forma_pagamento"));
        pedido.setObservacoes(rs.getString("observacoes"));

        String status = rs.getString("status");
        if (status != null) {
            pedido.setStatus(StatusPedidoPeca.valueOf(status));
        }

        Timestamp dataPedido = rs.getTimestamp("data_pedido");
        if (dataPedido != null) {
            pedido.setDataPedido(dataPedido.toLocalDateTime());
        }

        Timestamp dataPrevisao = rs.getTimestamp("data_previsao");
        if (dataPrevisao != null) {
            pedido.setDataPrevisao(dataPrevisao.toLocalDateTime());
        }

        return pedido;
    };
    //De-Para: DTO pra Entidade
    public static PedidoPecas toEntity(PedidoPecasDTO dto) {
        if (dto == null) return null;

        PedidoPecas pedido = new PedidoPecas();
        pedido.setId(dto.getId());
        pedido.setDataPrevisao(dto.getDataPrevisao());
        pedido.setStatus(dto.getStatus());
        pedido.setValorTotal(dto.getValorTotal());
        pedido.setFormaPagamento(dto.getFormaPagamento());
        pedido.setObservacoes(dto.getObservacoes());

        return pedido;
    }

    //De-para: Entidade para DTO (Caminho Inverso)
    public static PedidoPecasDTO toDTO(PedidoPecas entidade) {
        if (entidade == null) return null;

        PedidoPecasDTO dto = new PedidoPecasDTO();
        dto.setId(entidade.getId());
        dto.setDataPedido(entidade.getDataPedido());
        dto.setDataPrevisao(entidade.getDataPrevisao());
        dto.setStatus(entidade.getStatus());
        dto.setValorTotal(entidade.getValorTotal());
        dto.setFormaPagamento(entidade.getFormaPagamento());
        dto.setObservacoes(entidade.getObservacoes());

        return dto;
    }
}