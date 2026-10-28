package br.com.mecaniQA.api.services;

import br.com.mecaniQA.api.dtos.ItemPedidoPecaDTO;
import br.com.mecaniQA.api.dtos.PedidoPecasDTO;
import br.com.mecaniQA.api.models.ItemPedidoPeca;
import br.com.mecaniQA.api.models.PedidoPecas;
import br.com.mecaniQA.api.models.Peca;
import br.com.mecaniQA.api.repositories.PedidoPecasRepository;
import br.com.mecaniQA.api.repositories.PecaRepository;
import br.com.mecaniQA.api.mappers.PedidoPecasMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PedidoPecasService {

    private final PedidoPecasRepository pedidoPecasRepository;
    private final PecaRepository pecaRepository;

    public PedidoPecasService() {
        this.pedidoPecasRepository = new PedidoPecasRepository();
        this.pecaRepository = new PecaRepository();
    }

    public PedidoPecasDTO salvar(PedidoPecasDTO dto) {
        PedidoPecas pedido = PedidoPecasMapper.toEntity(dto);

        // Constrói os itens reais buscando as peças no banco de dados
        pedido.setItens(buscarItensReais(dto.getItens(), pedido));

        PedidoPecas pedidoSalvo = pedidoPecasRepository.save(pedido);

        return converterParaDTOComListas(pedidoSalvo);
    }

    public List<PedidoPecasDTO> listarTodos() {
        return pedidoPecasRepository.findAll().stream()
                .map(this::converterParaDTOComListas)
                .collect(Collectors.toList());
    }

    public PedidoPecasDTO buscarPorId(Long id) {
        Optional<PedidoPecas> pedidoOpt = pedidoPecasRepository.findById(id);

        if (pedidoOpt.isPresent()) {
            return converterParaDTOComListas(pedidoOpt.get());
        }

        throw new IllegalArgumentException("Pedido de Peças não encontrado com o ID: " + id);
    }

    public PedidoPecasDTO atualizar(Long id, PedidoPecasDTO dto) {
        if (!pedidoPecasRepository.existsById(id)) {
            throw new IllegalArgumentException("Pedido de Peças não encontrado para atualização.");
        }

        PedidoPecas pedido = PedidoPecasMapper.toEntity(dto);
        pedido.setId(id);

        // Revalida e busca as peças reais novamente para a atualização
        pedido.setItens(buscarItensReais(dto.getItens(), pedido));

        PedidoPecas pedidoAtualizado = pedidoPecasRepository.update(pedido);

        return converterParaDTOComListas(pedidoAtualizado);
    }

    public boolean deletar(Long id) {
        if (pedidoPecasRepository.existsById(id)) {
            pedidoPecasRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // --- Métodos Privados Auxiliares ---

    private List<ItemPedidoPeca> buscarItensReais(List<ItemPedidoPecaDTO> itensDto, PedidoPecas pedidoPai) {
        List<ItemPedidoPeca> itensReais = new ArrayList<>();
        if (itensDto != null && !itensDto.isEmpty()) {
            for (ItemPedidoPecaDTO itemDto : itensDto) {
                // Busca a peça real no banco para evitar os "objetos fantasmas"
                Peca peca = pecaRepository.findById(itemDto.getIdPeca())
                        .orElseThrow(() -> new IllegalArgumentException("Peça não encontrada com o ID: " + itemDto.getIdPeca()));

                ItemPedidoPeca item = new ItemPedidoPeca();
                item.setPeca(peca);
                item.setQuantidade(itemDto.getQuantidade());
                item.setPedidoPecas(pedidoPai); // Estabelece a ligação bidirecional

                itensReais.add(item);
            }
        }
        return itensReais;
    }

    // Garante que o DTO de saída contenha a lista de Itens preenchida para o Front-end
    private PedidoPecasDTO converterParaDTOComListas(PedidoPecas pedido) {
        PedidoPecasDTO dto = PedidoPecasMapper.toDTO(pedido);

        if (pedido.getItens() != null) {
            List<ItemPedidoPecaDTO> itensDto = pedido.getItens().stream().map(item -> {
                ItemPedidoPecaDTO itemDto = new ItemPedidoPecaDTO();
                itemDto.setIdPeca(item.getPeca().getId());
                itemDto.setQuantidade(item.getQuantidade());
                return itemDto;
            }).collect(Collectors.toList());

            dto.setItens(itensDto);
        }

        return dto;
    }
}