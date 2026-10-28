package br.com.mecaniQA.api.repositories;

import br.com.mecaniQA.api.database.DatabaseConnection;
import br.com.mecaniQA.api.models.Peca;
import br.com.mecaniQA.api.mappers.PecaMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PecaRepository implements Repository<Peca, Long> {

    @Override
    public Peca save(Peca peca) {
        String sql = "INSERT INTO pecas (nome, codigo_de_barras, fornecedor, qtd_estoque, preco_custo, preco_venda, categoria_peca, cor, tamanho, data_cadastro) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, NOW())";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, peca.getNome());
            stmt.setString(2, peca.getCodigoDeBarras());
            stmt.setString(3, peca.getFornecedor());
            stmt.setInt(4, peca.getQtdEstoque());
            stmt.setDouble(5, peca.getPrecoCusto());
            stmt.setDouble(6, peca.getPrecoVenda());
            stmt.setString(7, peca.getCategoriaPeca() != null ? peca.getCategoriaPeca().name() : null);
            stmt.setString(8, peca.getCor());
            stmt.setString(9, peca.getTamanho());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        peca.setId(generatedKeys.getLong(1));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return peca;
    }

    @Override
    public Peca update(Peca peca) {
        String sql = "UPDATE pecas SET nome = ?, codigo_de_barras = ?, fornecedor = ?, qtd_estoque = ?, preco_custo = ?, preco_venda = ?, categoria_peca = ?, cor = ?, tamanho = ?, data_ultima_atualizacao = NOW() WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, peca.getNome());
            stmt.setString(2, peca.getCodigoDeBarras());
            stmt.setString(3, peca.getFornecedor());
            stmt.setInt(4, peca.getQtdEstoque());
            stmt.setDouble(5, peca.getPrecoCusto());
            stmt.setDouble(6, peca.getPrecoVenda());
            stmt.setString(7, peca.getCategoriaPeca() != null ? peca.getCategoriaPeca().name() : null);
            stmt.setString(8, peca.getCor());
            stmt.setString(9, peca.getTamanho());
            stmt.setLong(10, peca.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return peca;
    }

    @Override
    public Optional<Peca> findById(Long id) {
        String sql = "SELECT * FROM pecas WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(PecaMapper.fromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Peca> findAll() {
        List<Peca> pecas = new ArrayList<>();
        String sql = "SELECT * FROM pecas";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                pecas.add(PecaMapper.fromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pecas;
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM pecas WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean existsById(Long id) {
        String sql = "SELECT 1 FROM pecas WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}