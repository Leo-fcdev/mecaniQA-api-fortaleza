package br.com.mecaniQA.api.services;

import br.com.mecaniQA.api.dtos.ServicoDTO;
import br.com.mecaniQA.api.models.Servico;
import br.com.mecaniQA.api.repositories.ServicoRepository;
import br.com.mecaniQA.api.mappers.ServicoMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ServicoService {

    private final ServicoRepository servicoRepository;

    public ServicoService() {
        this.servicoRepository = new ServicoRepository();
    }

    public ServicoDTO salvar(ServicoDTO dto) {
        // Regras de negócio: Validações antes de tocar no banco
        if (dto.getNomeServico() == null || dto.getNomeServico().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do serviço é obrigatório.");
        }
        if (dto.getCustoTabelado() < 0) {
            throw new IllegalArgumentException("O custo do serviço não pode ser negativo.");
        }

        Servico servico = ServicoMapper.toEntity(dto);
        Servico servicoSalvo = servicoRepository.save(servico);

        return ServicoMapper.toDTO(servicoSalvo);
    }

    public List<ServicoDTO> listarTodos() {
        return servicoRepository.findAll().stream()
                .map(ServicoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ServicoDTO buscarPorId(Long id) {
        Optional<Servico> servicoOpt = servicoRepository.findById(id);

        if (servicoOpt.isPresent()) {
            return ServicoMapper.toDTO(servicoOpt.get());
        }

        throw new IllegalArgumentException("Serviço não encontrado com o ID: " + id);
    }

    public ServicoDTO atualizar(Long id, ServicoDTO dto) {
        if (!servicoRepository.existsById(id)) {
            throw new IllegalArgumentException("Serviço não encontrado para atualização.");
        }

        Servico servico = ServicoMapper.toEntity(dto);
        servico.setId(id); // Garante que o ID da requisição será respeitado

        Servico servicoAtualizado = servicoRepository.update(servico);
        return ServicoMapper.toDTO(servicoAtualizado);
    }

    public boolean deletar(Long id) {
        if (servicoRepository.existsById(id)) {
            servicoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}