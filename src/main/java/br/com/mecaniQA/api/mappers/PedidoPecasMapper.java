package br.com.mecaniQA.api.mappers;

import br.com.mecaniQA.api.dtos.ItemPedidoPecaDTO;
import br.com.mecaniQA.api.dtos.PedidoPecasDTO;
import br.com.mecaniQA.api.models.ItemPedidoPeca;
import br.com.mecaniQA.api.models.Peca;
import br.com.mecaniQA.api.models.PedidoPecas;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PedidoPecasMapper {

    private PedidoPecasMapper() {}

    //De-Para: DTO pra Entidade
    public static PedidoPecas toEntity(PedidoPecasDTO dto) {
        if (dto == null) return null;

        PedidoPecas pedido = new PedidoPecas();
        pedido.setId(dto.getId());
        pedido.setDataPedido(dto.getDataPedido());
        pedido.setDataPrevisao(dto.getDataPrevisao());
        pedido.setStatus(dto.getStatus());
        pedido.setValorTotal(dto.getValorTotal());
        pedido.setFormaPagamento(dto.getFormaPagamento());
        pedido.setObservacoes(dto.getObservacoes());

        if (dto.getItens() != null) {
            List <ItemPedidoPeca> itens = dto.getItens().stream().map(itemDto -> {
                ItemPedidoPeca item = new ItemPedidoPeca();
                Peca peca = new Peca();
                peca.setId(itemDto.getIdPeca());

                item.setPeca(peca);
                item.setQuantidade(itemDto.getQuantidade());
                item.setPedidoPecas(pedido); //Ligação entidade associativa
                return item;
            }).collect(Collectors.toList());
            pedido.setItens(itens);
        } else {
            pedido.setItens(new ArrayList<>());
        }

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

        if (entidade.getItens() != null) {
            List<ItemPedidoPecaDTO> itensDTO = entidade.getItens().stream().map(item -> {
                ItemPedidoPecaDTO itemDTO = new ItemPedidoPecaDTO();
                if (item.getPeca() != null) {
                    itemDTO.setIdPeca(item.getPeca().getId());
                }
                itemDTO.setQuantidade(item.getQuantidade());
                return itemDTO;
            }).collect(Collectors.toList());
            dto.setItens(itensDTO);
        }

        return dto;
    }
}