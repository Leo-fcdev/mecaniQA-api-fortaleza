package br.com.mecaniQA.api.mappers;

import br.com.mecaniQA.api.dtos.ServicoDTO;
import br.com.mecaniQA.api.models.Servico;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class ServicoMapper {

    private ServicoMapper() {}

    public static Servico fromResultSet(ResultSet rs) throws SQLException {
        Servico servico = new Servico(
                rs.getLong("id"),
                rs.getString("nome_servico"),
                rs.getInt("tempo_estimado"),
                rs.getDouble("custo_tabelado")
        );

        Timestamp dataCadastro = rs.getTimestamp("data_cadastro");
        if (dataCadastro != null) {
            servico.setDataCadastro(dataCadastro.toLocalDateTime());
        }

        Timestamp dataAtualizacao = rs.getTimestamp("data_ultima_atualizacao");
        if (dataAtualizacao != null) {
            servico.setDataUltimaAtualizacao(dataAtualizacao.toLocalDateTime());
        }

        return servico;
    }

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