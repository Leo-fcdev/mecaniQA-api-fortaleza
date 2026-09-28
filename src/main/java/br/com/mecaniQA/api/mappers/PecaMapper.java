package br.com.mecaniQA.api.mappers;

import br.com.mecaniQA.api.dtos.PecaDTO;
import br.com.mecaniQA.api.models.Peca;

public class PecaMapper {

    private PecaMapper() {}

    public static Peca toEntity(PecaDTO dto) {
        if (dto == null) return null;

        Peca peca = new Peca();
        peca.setId(dto.getId());
        peca.setNome(dto.getNome());
        peca.setCodigoDeBarras(dto.getCodigoDeBarras());
        peca.setFornecedor(dto.getFornecedor());
        peca.setQtdEstoque(dto.getQtdEstoque());
        peca.setPrecoCusto(dto.getPrecoCusto());
        peca.setPrecoVenda(dto.getPrecoVenda());
        peca.setCategoriaPeca(dto.getCategoriaPeca());
        return peca;
    }

    public static PecaDTO toDTO(Peca entidade) {
        if (entidade == null) return null;

        PecaDTO dto = new PecaDTO();
        dto.setId(entidade.getId());
        dto.setNome(entidade.getNome());
        dto.setCodigoDeBarras(entidade.getCodigoDeBarras());
        dto.setFornecedor(entidade.getFornecedor());
        dto.setQtdEstoque(entidade.getQtdEstoque());
        dto.setPrecoCusto(entidade.getPrecoCusto());
        dto.setPrecoVenda(entidade.getPrecoVenda());
        dto.setCategoriaPeca(entidade.getCategoriaPeca());
        dto.setDataCadastro(entidade.getDataCadastro());
        dto.setDataUltimaAtualizacao(entidade.getDataUltimaAtualizacao());
        return dto;
    }
}