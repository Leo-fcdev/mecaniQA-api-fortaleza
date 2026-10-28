package br.com.mecaniQA.api.controllers;

import br.com.mecaniQA.api.dtos.OrdemServicoDTO;
import br.com.mecaniQA.api.mappers.OrdemServicoMapper;
import br.com.mecaniQA.api.models.OrdemServico;
import br.com.mecaniQA.api.models.StatusOS;
import br.com.mecaniQA.api.repositories.OrdemServicoRepository;
import br.com.mecaniQA.api.services.OrdemServicoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ordens-servico")
public class OrdemServicoController {

    //Instância do repositória utilizando o padrão Singleton
    private final OrdemServicoService ordemServicoService;

    public OrdemServicoController() {
        this.ordemServicoService = new OrdemServicoService();
    }

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody OrdemServicoDTO ordemServicoDTO) {
        try {
            OrdemServicoDTO osSalva = ordemServicoService.salvar(ordemServicoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(osSalva); // 201 Created
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()); // 400 Bad Request
        }
    }

    @GetMapping
    public ResponseEntity<List<OrdemServicoDTO>> listarTodas() {
        List<OrdemServicoDTO> ordens = ordemServicoService.listarTodas();
        return ResponseEntity.ok(ordens); // 200 OK
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            OrdemServicoDTO os = ordemServicoService.buscarPorId(id);
            return ResponseEntity.ok(os);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); // 404 Not Found
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody OrdemServicoDTO ordemServicoDTO) {
        try {
            OrdemServicoDTO osAtualizada = ordemServicoService.atualizar(id, ordemServicoDTO);
            return ResponseEntity.ok(osAtualizada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        boolean deletado = ordemServicoService.deletar(id);
        if (deletado) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ordem de Serviço não encontrada para exclusão.");
    }
}