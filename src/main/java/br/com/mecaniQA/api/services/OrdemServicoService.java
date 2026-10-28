package br.com.mecaniQA.api.services;

import br.com.mecaniQA.api.dtos.OrdemServicoDTO;
import br.com.mecaniQA.api.models.OrdemServico;
import br.com.mecaniQA.api.models.Peca;
import br.com.mecaniQA.api.models.Servico;
import br.com.mecaniQA.api.repositories.OrdemServicoRepository;
import br.com.mecaniQA.api.repositories.PecaRepository;
import br.com.mecaniQA.api.repositories.ServicoRepository;
import br.com.mecaniQA.api.mappers.OrdemServicoMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OrdemServicoService {

    private final OrdemServicoRepository ordemServicoRepository;
    private final PecaRepository pecaRepository;
    private final ServicoRepository servicoRepository;

    public OrdemServicoService() {
        this.ordemServicoRepository = new OrdemServicoRepository();
        this.pecaRepository = new PecaRepository();
        this.servicoRepository = new ServicoRepository();
    }

    public OrdemServicoDTO salvar(OrdemServicoDTO dto) {
        if (dto.getIdCliente() == null) {
            throw new IllegalArgumentException("O ID do cliente é obrigatório para abrir uma Ordem de Serviço.");
        }

        OrdemServico os = OrdemServicoMapper.toEntity(dto);

        // Busca as Peças reais no banco a partir dos IDs enviados
        os.setPecas(buscarPecasReais(dto.getPecas()));

        // Busca os Serviços reais no banco a partir dos IDs enviados
        os.setServicos(buscarServicosReais(dto.getServicos()));

        OrdemServico osSalva = ordemServicoRepository.save(os);

        return converterParaDTOComListas(osSalva);
    }

    public List<OrdemServicoDTO> listarTodas() {
        return ordemServicoRepository.findAll().stream()
                .map(this::converterParaDTOComListas)
                .collect(Collectors.toList());
    }

    public OrdemServicoDTO buscarPorId(Long id) {
        Optional<OrdemServico> osOpt = ordemServicoRepository.findById(id);

        if (osOpt.isPresent()) {
            return converterParaDTOComListas(osOpt.get());
        }

        throw new IllegalArgumentException("Ordem de Serviço não encontrada com o ID: " + id);
    }

    public OrdemServicoDTO atualizar(Long id, OrdemServicoDTO dto) {
        if (!ordemServicoRepository.existsById(id)) {
            throw new IllegalArgumentException("Ordem de Serviço não encontrada para atualização.");
        }

        OrdemServico os = OrdemServicoMapper.toEntity(dto);
        os.setId(id);

        // Revalida e busca os itens reais novamente para a atualização
        os.setPecas(buscarPecasReais(dto.getPecas()));
        os.setServicos(buscarServicosReais(dto.getServicos()));

        OrdemServico osAtualizada = ordemServicoRepository.update(os);

        return converterParaDTOComListas(osAtualizada);
    }

    public boolean deletar(Long id) {
        if (ordemServicoRepository.existsById(id)) {
            ordemServicoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // --- Métodos Privados Auxiliares ---

    private List<Peca> buscarPecasReais(List<Long> idsPecas) {
        List<Peca> pecasReais = new ArrayList<>();
        if (idsPecas != null && !idsPecas.isEmpty()) {
            for (Long idPeca : idsPecas) {
                Peca peca = pecaRepository.findById(idPeca)
                        .orElseThrow(() -> new IllegalArgumentException("Peça não encontrada com o ID: " + idPeca));
                pecasReais.add(peca);
            }
        }
        return pecasReais;
    }

    private List<Servico> buscarServicosReais(List<Long> idsServicos) {
        List<Servico> servicosReais = new ArrayList<>();
        if (idsServicos != null && !idsServicos.isEmpty()) {
            for (Long idServico : idsServicos) {
                Servico servico = servicoRepository.findById(idServico)
                        .orElseThrow(() -> new IllegalArgumentException("Serviço não encontrado com o ID: " + idServico));
                servicosReais.add(servico);
            }
        }
        return servicosReais;
    }

    // Garante que o DTO de saída contenha as listas de IDs preenchidas
    private OrdemServicoDTO converterParaDTOComListas(OrdemServico os) {
        OrdemServicoDTO dto = OrdemServicoMapper.toDTO(os);

        if (os.getPecas() != null) {
            dto.setPecas(os.getPecas().stream().map(Peca::getId).collect(Collectors.toList()));
        }

        if (os.getServicos() != null) {
            dto.setServicos(os.getServicos().stream().map(Servico::getId).collect(Collectors.toList()));
        }

        return dto;
    }
}