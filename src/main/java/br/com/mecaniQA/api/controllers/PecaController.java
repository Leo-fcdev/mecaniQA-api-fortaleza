package br.com.mecaniQA.api.controllers;

import br.com.mecaniQA.api.dtos.PecaDTO;
import br.com.mecaniQA.api.mappers.PecaMapper;
import br.com.mecaniQA.api.models.Peca;
import br.com.mecaniQA.api.repositories.PecaRepository;
import br.com.mecaniQA.api.services.PecaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pecas")
public class PecaController {

    private final PecaService pecaService;

    public PecaController(){
        this.pecaService = new PecaService();
    }

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody PecaDTO pecaDTO) {
        try {
            PecaDTO pecaSalva = pecaService.salvar(pecaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(pecaSalva); // 201 Created
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()); // 400 Bad Request
        }
    }

    @GetMapping
    public ResponseEntity<List<PecaDTO>> listarTodas() {
        List<PecaDTO> pecas = pecaService.listarTodas();
        return ResponseEntity.ok(pecas); // 200 OK
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            PecaDTO peca = pecaService.buscarPorId(id);
            return ResponseEntity.ok(peca);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); // 404 Not Found
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody PecaDTO pecaDTO) {
        try {
            PecaDTO pecaAtualizada = pecaService.atualizar(id, pecaDTO);
            return ResponseEntity.ok(pecaAtualizada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        boolean deletado = pecaService.deletar(id);
        if (deletado) {
            return ResponseEntity.noContent().build(); // 204 No Content (Sucesso, sem corpo)
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Peça não encontrada para exclusão.");
    }
}