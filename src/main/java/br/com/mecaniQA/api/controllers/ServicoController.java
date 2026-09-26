package br.com.mecaniQA.api.controllers;

import br.com.mecaniQA.api.dtos.ServicoDTO;
import br.com.mecaniQA.api.mappers.ServicoMapper;
import br.com.mecaniQA.api.models.Servico;
import br.com.mecaniQA.api.repositories.ServicoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/servicos")
public class ServicoController {

    private ServicoRepository repository = ServicoRepository.getInstance();

    @PostMapping
    public ResponseEntity cadastrar(@RequestBody ServicoDTO servicoDTO) {
        Servico servicoEntidade = ServicoMapper.toEntity(servicoDTO);
        Servico servicoSalvo = repository.salvar(servicoEntidade);
        return ResponseEntity.status(HttpStatus.CREATED).body(ServicoMapper.toDTO(servicoSalvo));
    }

    @GetMapping
    public ResponseEntity<List<ServicoDTO>> listarTodos() {
        List<ServicoDTO> listaDTO = repository.listarTodos().stream()
                .map(ServicoMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(listaDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity buscarPorId(@PathVariable long id) {
        Servico servico = repository.buscarPorId(id);
        if (servico != null) {
            return ResponseEntity.ok(ServicoMapper.toDTO(servico));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizar(@PathVariable long id, @RequestBody ServicoDTO servicoDTO) {
        Servico servicoEntidade = ServicoMapper.toEntity(servicoDTO);
        Servico servicoAtualizado = repository.atualizar(id, servicoEntidade);
        if (servicoAtualizado != null) {
            return ResponseEntity.ok(ServicoMapper.toDTO(servicoAtualizado));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletar(@PathVariable long id) {
        boolean deletado = repository.deletar(id);
        if (deletado) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}