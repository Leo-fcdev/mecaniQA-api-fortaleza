package br.com.mecaniQA.api.mappers;

import br.com.mecaniQA.api.dtos.ServicoDTO;
import br.com.mecaniQA.api.models.Servico;

public class ServicoMapper {

    private ServicoMapper() {}

    public static Servico toEntity(ServicoDTO dto) {
        if (dto == null) return null;

        return new Servico(
                dto.getId(),
                dto.getNomeServico(),
                dto.getTempoEstimado(),
                dto.getCustoTabelado()
        );
    }

    public static ServicoDTO toDTO(Servico entidade) {
        if (entidade == null) return null;

        ServicoDTO dto = new ServicoDTO();
        dto.setId(entidade.getId());
        dto.setNomeServico(entidade.getNomeServico());
        dto.setTempoEstimado(entidade.getTempoEstimado());
        dto.setCustoTabelado(entidade.getCustoTabelado());
        dto.setDataCadastro(entidade.getDataCadastro());
        dto.setDataUltimaAtualizacao(entidade.getDataUltimaAtualizacao());
        return dto;
    }
}