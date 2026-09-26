package br.com.mecaniQA.api.controllers;

import br.com.mecaniQA.api.dtos.ItemPedidoPecaDTO;
import br.com.mecaniQA.api.dtos.PedidoPecasDTO;
import br.com.mecaniQA.api.mappers.PedidoPecasMapper;
import br.com.mecaniQA.api.models.ItemPedidoPeca;
import br.com.mecaniQA.api.models.Peca;
import br.com.mecaniQA.api.models.PedidoPecas;
import br.com.mecaniQA.api.models.StatusPedidoPeca;
import br.com.mecaniQA.api.repositories.PedidoPecasRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pedidos-pecas")
public class PedidoPecasController {

    private PedidoPecasRepository repository = PedidoPecasRepository.getInstance();

    //CREATE
    @PostMapping
    public ResponseEntity criar(@RequestBody PedidoPecasDTO dto) {
        PedidoPecas entidade = PedidoPecasMapper.toEntity(dto);
        PedidoPecas pedidoSalvo = repository.salvar(entidade);

        return ResponseEntity.status(HttpStatus.CREATED).body(PedidoPecasMapper.toDTO(pedidoSalvo));
    }

    //ADICIONAR PEÇA A EXISTENTE
    @PostMapping("/{id}/itens")
    public ResponseEntity adicionarItens(
            @PathVariable long id,
            @RequestBody List<ItemPedidoPecaDTO> itensDTO) {

        // Converte os DTOs de itens para Entidades antes de passar ao repositório
        List <ItemPedidoPeca> novosItens = itensDTO.stream().map(dto -> {
            ItemPedidoPeca item = new ItemPedidoPeca();
            item.setQuantidade(dto.getQuantidade());

            Peca peca = new Peca();
            peca.setId(dto.getIdPeca());
            item.setPeca(peca);

            return item;
        }).collect(Collectors.toList());

        PedidoPecas pedidoAtualizado = repository.adicionarItens(id, novosItens);

        if (pedidoAtualizado != null) {
            return ResponseEntity.ok(PedidoPecasMapper.toDTO(pedidoAtualizado));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    //MODIFICAR STATUS DE PEDIDO
    @PatchMapping("/{id}/status")
    public ResponseEntity atualizarStatus(
            @PathVariable long id,
            @RequestBody StatusPedidoPeca novoStatus) {

        PedidoPecas pedidoAtualizado = repository.atualizarStatus(id, novoStatus);

        if (pedidoAtualizado != null) {
            return ResponseEntity.ok(PedidoPecasMapper.toDTO(pedidoAtualizado));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}