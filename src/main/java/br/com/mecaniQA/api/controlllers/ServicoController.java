package br.com.mecaniQA.api.controlllers;

import br.com.mecaniQA.api.models.Servico;
import br.com.mecaniQA.api.repositories.ServicoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicos")
public class ServicoController {

    private ServicoRepository repository = ServicoRepository.getInstance();

    @PostMapping
    public ResponseEntity<Servico> cadastrar(@RequestBody Servico servico) {
         Servico servicoSalvo = repository.salvar(servico);

         return ResponseEntity.status(HttpStatus.CREATED).body(servicoSalvo);
    }

    @GetMapping
    public ResponseEntity<List<Servico>> listarTodos() {
        return ResponseEntity.ok(repository.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Servico> buscarPorId(@PathVariable long id) {
        Servico servico = repository.buscarPorId(id);

        if (servico != null) {
            return ResponseEntity.ok(servico);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/{ìd}")
    public ResponseEntity<Servico> atualizar(@PathVariable long id, @RequestBody Servico servico) {
        Servico servicoAtualizado = repository.atualizar(id, servico);
        if (servicoAtualizado != null) {
            return ResponseEntity.ok(servicoAtualizado);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable long id) {
        boolean deletado = repository.deletar(id);
        if (deletado) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
