package br.com.mecaniQA.api.controllers;

import br.com.mecaniQA.api.models.Peca;
import br.com.mecaniQA.api.repositories.PecaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pecas")
public class PecaController {

    private PecaRepository repository = PecaRepository.getInstance();

    @PostMapping
    public ResponseEntity<Peca> cadastrar(@RequestBody Peca peca) {
        Peca pecaSalva = repository.salvar(peca);
        return ResponseEntity.status(HttpStatus.CREATED).body(pecaSalva);
    }

    @GetMapping
    public ResponseEntity<List<Peca>> listarTodas() {
        return ResponseEntity.ok(repository.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Peca> buscarPorId(@PathVariable long id){
        Peca peca = repository.buscarPorId(id);
        if (peca != null) {
            return ResponseEntity.ok(peca);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Peca> atualizar(@PathVariable long id, @RequestBody Peca peca) {
        Peca pecaAtualizada = repository.atualizar(id, peca);
        if (pecaAtualizada != null) {
            return ResponseEntity.ok(pecaAtualizada);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable long id){
        boolean deletado = repository.deletar(id);
        if (deletado) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
