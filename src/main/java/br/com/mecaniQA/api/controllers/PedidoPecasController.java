package br.com.mecaniQA.api.controllers;

import br.com.mecaniQA.api.dtos.ItemPedidoPecaDTO;
import br.com.mecaniQA.api.dtos.PedidoPecasDTO;
import br.com.mecaniQA.api.mappers.PedidoPecasMapper;
import br.com.mecaniQA.api.models.ItemPedidoPeca;
import br.com.mecaniQA.api.models.Peca;
import br.com.mecaniQA.api.models.PedidoPecas;
import br.com.mecaniQA.api.models.StatusPedidoPeca;
import br.com.mecaniQA.api.repositories.PedidoPecasRepository;
import br.com.mecaniQA.api.services.PedidoPecasService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pedidos-pecas")
public class PedidoPecasController {

    private final PedidoPecasService pedidoPecasService;

    public PedidoPecasController() {
        this.pedidoPecasService = new PedidoPecasService();
    }

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody PedidoPecasDTO pedidoPecasDTO) {
        try {
            PedidoPecasDTO pedidoSalvo = pedidoPecasService.salvar(pedidoPecasDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(pedidoSalvo); // 201 Created
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()); // 400 Bad Request
        }
    }

    @GetMapping
    public ResponseEntity<List<PedidoPecasDTO>> listarTodos() {
        List<PedidoPecasDTO> pedidos = pedidoPecasService.listarTodos();
        return ResponseEntity.ok(pedidos); // 200 OK
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            PedidoPecasDTO pedido = pedidoPecasService.buscarPorId(id);
            return ResponseEntity.ok(pedido);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); // 404 Not Found
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody PedidoPecasDTO pedidoPecasDTO) {
        try {
            PedidoPecasDTO pedidoAtualizado = pedidoPecasService.atualizar(id, pedidoPecasDTO);
            return ResponseEntity.ok(pedidoAtualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        boolean deletado = pedidoPecasService.deletar(id);
        if (deletado) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pedido de Peças não encontrado para exclusão.");
    }
}