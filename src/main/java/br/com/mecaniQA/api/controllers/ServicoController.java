package br.com.mecaniQA.api.controllers;

import br.com.mecaniQA.api.dtos.ServicoDTO;
import br.com.mecaniQA.api.mappers.ServicoMapper;
import br.com.mecaniQA.api.models.Servico;
import br.com.mecaniQA.api.repositories.ServicoRepository;
import br.com.mecaniQA.api.services.ServicoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/servicos")
public class ServicoController {

    private final ServicoService servicoService;

    public ServicoController(){
        this.servicoService = new ServicoService();
    }

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody ServicoDTO servicoDTO) {
        try {
            ServicoDTO servicoSalvo = servicoService.salvar(servicoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(servicoSalvo); // 201 Created
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()); // 400 Bad Request
        }
    }

    @GetMapping
    public ResponseEntity<List<ServicoDTO>> listarTodos() {
        List<ServicoDTO> servicos = servicoService.listarTodos();
        return ResponseEntity.ok(servicos); // 200 OK
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            ServicoDTO servico = servicoService.buscarPorId(id);
            return ResponseEntity.ok(servico);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); // 404 Not Found
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody ServicoDTO servicoDTO) {
        try {
            ServicoDTO servicoAtualizado = servicoService.atualizar(id, servicoDTO);
            return ResponseEntity.ok(servicoAtualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        boolean deletado = servicoService.deletar(id);
        if (deletado) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Serviço não encontrado para exclusão.");
    }
}