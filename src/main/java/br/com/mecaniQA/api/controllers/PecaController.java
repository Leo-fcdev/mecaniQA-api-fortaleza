package br.com.mecaniQA.api.controllers;

import br.com.mecaniQA.api.dtos.PecaDTO;
import br.com.mecaniQA.api.mappers.PecaMapper;
import br.com.mecaniQA.api.models.Peca;
import br.com.mecaniQA.api.repositories.PecaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pecas")
public class PecaController {

    private PecaRepository repository = PecaRepository.getInstance();

    @PostMapping
    public ResponseEntity cadastrar(@RequestBody PecaDTO pecaDTO) {
        Peca pecaEntidade = PecaMapper.toEntity(pecaDTO);
        Peca pecaSalva = repository.salvar(pecaEntidade);
        return ResponseEntity.status(HttpStatus.CREATED).body(PecaMapper.toDTO(pecaSalva));
    }

    @GetMapping
    public ResponseEntity<List<PecaDTO>> listarTodas() {
        List <PecaDTO> listaDTO = repository.listarTodas().stream()
                .map(PecaMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(listaDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity buscarPorId(@PathVariable long id){
        Peca peca = repository.buscarPorId(id);
        if (peca != null) {
            return ResponseEntity.ok(PecaMapper.toDTO(peca));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizar(@PathVariable long id, @RequestBody PecaDTO pecaDTO) {
        Peca pecaEntidade = PecaMapper.toEntity(pecaDTO);
        Peca pecaAtualizada = repository.atualizar(id, pecaEntidade);
        if (pecaAtualizada != null) {
            return ResponseEntity.ok(PecaMapper.toDTO(pecaAtualizada));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletar(@PathVariable long id){
        boolean deletado = repository.deletar(id);
        if (deletado) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}