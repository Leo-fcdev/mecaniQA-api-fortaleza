package br.com.mecaniQA.api.mappers;

import br.com.mecaniQA.api.dtos.OrdemServicoDTO;
import br.com.mecaniQA.api.models.OrdemServico;
import br.com.mecaniQA.api.models.Peca;
import br.com.mecaniQA.api.models.Servico;
import br.com.mecaniQA.api.models.StatusOS;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

public class OrdemServicoMapper {

    private OrdemServicoMapper(){
    }

    public static OrdemServico fromResultSet(ResultSet rs) throws SQLException {
        OrdemServico.OrdemServicoBuilder builder = OrdemServico.builder()
                .id(rs.getLong("id"))
                .idCliente(rs.getLong("id_cliente"))
                .valorTotal(rs.getDouble("valor_total"))
                .desconto(rs.getDouble("desconto"));

        String status = rs.getString("status");
        if (status != null) {
            builder.status(StatusOS.valueOf(status));
        }

        Timestamp dataAbertura = rs.getTimestamp("data_abertura");
        if (dataAbertura != null) {
            builder.dataAbertura(dataAbertura.toLocalDateTime());
        }

        Timestamp dataFechamento = rs.getTimestamp("data_fechamento");
        if (dataFechamento != null) {
            builder.dataFechamento(dataFechamento.toLocalDateTime());
        }

        return builder.build();
    }
    //De-Para: DTO pra Entidade
    public static OrdemServico toEntity(OrdemServicoDTO dto) {
        if (dto == null) return null;

        return OrdemServico.builder()
                .id(dto.getId())
                .idCliente(dto.getIdCliente())
                .valorTotal(dto.getValorTotal())
                .desconto(dto.getDesconto())
                .status(dto.getStatus())
                .build();

    }

    //De-para: Entidade para DTO (Caminho Inverso)
    public static OrdemServicoDTO toDTO (OrdemServico entidade){
        if (entidade == null){
            return null;
        }

        OrdemServicoDTO dto = new OrdemServicoDTO();
        dto.setId(entidade.getId());
        dto.setIdCliente(entidade.getIdCliente());
        dto.setDataAbertura(entidade.getDataAbertura());
        dto.setDataFechamento(entidade.getDataFechamento());
        dto.setStatus(entidade.getStatus());
        dto.setValorTotal(entidade.getValorTotal());
        dto.setDesconto(entidade.getDesconto());

        return dto;
    }
}
