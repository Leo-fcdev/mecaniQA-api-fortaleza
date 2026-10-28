package br.com.mecaniQA.api.mappers;

import br.com.mecaniQA.api.dtos.PecaDTO;
import br.com.mecaniQA.api.models.CategoriaPeca;
import br.com.mecaniQA.api.models.Peca;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class PecaMapper {

    private PecaMapper() {}

    public static Peca fromResultSet(ResultSet rs) throws SQLException {
        Peca peca = new Peca();
        peca.setId(rs.getLong("id"));
        peca.setNome(rs.getString("nome"));
        peca.setCodigoDeBarras(rs.getString("codigo_de_barras"));
        peca.setFornecedor(rs.getString("fornecedor"));
        peca.setQtdEstoque(rs.getInt("qtd_estoque"));
        peca.setPrecoCusto(rs.getDouble("preco_custo"));
        peca.setPrecoVenda(rs.getDouble("preco_venda"));

        peca.setCor(rs.getString("cor"));
        peca.setTamanho(rs.getString("tamanho"));

        String categoria = rs.getString("categoria_peca");
        if (categoria != null) {
            peca.setCategoriaPeca(CategoriaPeca.valueOf(categoria));
        }

        Timestamp dataCadastro = rs.getTimestamp("data_cadastro");
        if (dataCadastro != null){
            peca.setDataCadastro(dataCadastro.toLocalDateTime());
        }

        Timestamp dataAtualizacao =rs.getTimestamp("data_ultima_atualizacao");
        if (dataAtualizacao != null) {
            peca.setDataUltimaAtualizacao(dataAtualizacao.toLocalDateTime());
        }

        return peca;
    };

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

        peca.setCor(dto.getCor());
        peca.setTamanho(dto.getTamanho());

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

        dto.setCor(entidade.getCor());
        dto.setTamanho(entidade.getTamanho());
        dto.setDataCadastro(entidade.getDataCadastro());
        dto.setDataUltimaAtualizacao(entidade.getDataUltimaAtualizacao());

        return dto;
    }
}