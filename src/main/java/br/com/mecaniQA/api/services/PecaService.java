package br.com.mecaniQA.api.services;

import br.com.mecaniQA.api.dtos.PecaDTO;
import br.com.mecaniQA.api.models.Peca;
import br.com.mecaniQA.api.repositories.PecaRepository;
import br.com.mecaniQA.api.mappers.PecaMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PecaService {

    private final PecaRepository pecaRepository;

    public PecaService() {
        // Instancia o repositório JDBC definitivo
        this.pecaRepository = new PecaRepository();
    }

    public PecaDTO salvar(PecaDTO dto) {
        // Regras de negócio: Validações antes de tocar no banco
        if (dto.getNome() == null || dto.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome da peça é obrigatório.");
        }
        if (dto.getPrecoVenda() <= 0) {
            throw new IllegalArgumentException("O preço de venda deve ser maior que zero.");
        }

        // Fluxo: DTO -> Mapper -> Entidade -> Banco -> Entidade -> Mapper -> DTO
        Peca peca = PecaMapper.toEntity(dto);
        Peca pecaSalva = pecaRepository.save(peca);

        return PecaMapper.toDTO(pecaSalva);
    }

    public List<PecaDTO> listarTodas() {
        return pecaRepository.findAll().stream()
                .map(PecaMapper::toDTO) // Converte cada Entidade que veio do banco para DTO
                .collect(Collectors.toList());
    }

    public PecaDTO buscarPorId(Long id) {
        Optional<Peca> pecaOpt = pecaRepository.findById(id);

        if (pecaOpt.isPresent()) {
            return PecaMapper.toDTO(pecaOpt.get());
        }

        throw new IllegalArgumentException("Peça não encontrada com o ID: " + id);
    }

    public PecaDTO atualizar(Long id, PecaDTO dto) {
        // Valida se a peça realmente existe antes de tentar atualizar
        if (!pecaRepository.existsById(id)) {
            throw new IllegalArgumentException("Peça não encontrada para atualização.");
        }

        Peca peca = PecaMapper.toEntity(dto);
        peca.setId(id); // Trava de segurança: Garante que o ID da URL será mantido, evitando sobrescrever outro registro

        Peca pecaAtualizada = pecaRepository.update(peca);
        return PecaMapper.toDTO(pecaAtualizada);
    }

    public boolean deletar(Long id) {
        if (pecaRepository.existsById(id)) {
            pecaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}