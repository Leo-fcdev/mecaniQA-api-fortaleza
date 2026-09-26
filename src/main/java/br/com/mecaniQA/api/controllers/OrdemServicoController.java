package br.com.mecaniQA.api.controllers;

import br.com.mecaniQA.api.dtos.OrdemServicoDTO;
import br.com.mecaniQA.api.mappers.OrdemServicoMapper;
import br.com.mecaniQA.api.models.OrdemServico;
import br.com.mecaniQA.api.models.StatusOS;
import br.com.mecaniQA.api.repositories.OrdemServicoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ordens-servico")
public class OrdemServicoController {

    //Instância do repositória utilizando o padrão Singleton
    private OrdemServicoRepository repository = OrdemServicoRepository.getInstance();

    //Create
    @PostMapping
    public ResponseEntity criar(@RequestBody OrdemServicoDTO dto){
        //Converte o DTO recebido para e entidade com o mapper
        OrdemServico entidade = OrdemServicoMapper.toEntity(dto);

        //Salva a entidade no repositório
        OrdemServico osSalva = repository.salvar(entidade);

        //Converte a entidade salva de volta para DTO pra devolver ao cliente
        OrdemServicoDTO dtoSalvo = OrdemServicoMapper.toDTO(osSalva);

        return ResponseEntity.status(HttpStatus.CREATED).body(dtoSalvo);
    }

    //Update
    @PatchMapping("/{id}/status")
    public ResponseEntity atualizarStatus(@PathVariable long id, @RequestBody StatusOS novoStatus) {
        //Atualiza apenas o status no repositório
        OrdemServico osAtualizada = repository.atualizarStatus(id, novoStatus);

        if (osAtualizada != null) {
            //Converte a OS atualizada para DTO e retorna
            return ResponseEntity.ok(OrdemServicoMapper.toDTO(osAtualizada));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // READ geral
    @GetMapping
    public ResponseEntity<List<OrdemServicoDTO>> listarTodas() {
        // Busca a lista de entidades e converte cada uma para DTO
        List <OrdemServicoDTO> listaDTOs = repository.listarTodas().stream()
                .map(OrdemServicoMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(listaDTOs);
    }
}