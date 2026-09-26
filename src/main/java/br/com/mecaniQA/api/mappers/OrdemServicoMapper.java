package br.com.mecaniQA.api.mappers;

import br.com.mecaniQA.api.dtos.OrdemServicoDTO;
import br.com.mecaniQA.api.models.OrdemServico;
import br.com.mecaniQA.api.models.Peca;
import br.com.mecaniQA.api.models.Servico;
import java.util.List;
import java.util.stream.Collectors;

public class OrdemServicoMapper {

    private OrdemServicoMapper(){
    }

    //De-Para: DTO pra Entidade
    public static OrdemServico toEntity(OrdemServicoDTO dto) {
        if (dto == null){
            return null;
        }

        //Converter a lista de IDs de Serviços (Long) numa lista de objetivos Serviço
        List<Servico> servicos = null;
        if (dto.getServicos() != null){
            servicos = dto.getServicos().stream()
                    .map(id -> new Servico(id, null, 0, 0.0))
                    .collect(Collectors.toList());
        }

        //Converter a lista de IDs de Peças (Long) numa lista de objetos Peca
        List<Peca> pecas = null;
        if (dto.getPecas() != null){
            pecas = dto.getPecas().stream()
                    .map(id -> {
                        Peca peca = new Peca();
                        peca.setId(id);
                        return peca;
                    }).collect(Collectors.toList());
        }

        //Utilizar o  Builder da entidade para construir o objeto final
        return OrdemServico.builder()
                .id(dto.getId())
                .idCliente(dto.getIdCliente())
                .dataAbertura(dto.getDataAbertura())
                .dataFechamento(dto.getDataFechamento())
                .status(dto.getStatus())
                .valorTotal(dto.getValorTotal())
                .desconto(dto.getDesconto())
                .servicos(servicos)
                .pecas(pecas)
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

        //Extrai apenas os IDs dos objetos Serviço para a lista numérica do DTO
        if (entidade.getServicos() != null){
            dto.setServicos(entidade.getServicos().stream()
                    .map(Servico::getId)
                    .collect(Collectors.toList()));
        }

        //Extrai apenas os IDs dos objetos Peça para a lista numérica do DTO
        if (entidade.getPecas() != null){
            dto.setPecas(entidade.getPecas().stream()
                    .map(Peca::getId)
                    .collect(Collectors.toList()));
        }

        return dto;
    }
}
