package br.com.mecaniQA.api.repositories;

import br.com.mecaniQA.api.database.DatabaseConnection;
import br.com.mecaniQA.api.models.Servico;
import br.com.mecaniQA.api.mappers.ServicoMapper;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ServicoRepository implements Repository<Servico, Long>{

    @Override
    public Servico save(Servico servico){
        String sql = "INSERT INTO servicos (nome_servico, tempo_estimado, custo_tabelado, data_cadastro) VALUES (?, ?, ?, NOW())";
        try (Connection conn = DatabaseConnection.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, servico.getNomeServico());
            stmt.setInt(2, servico.getTempoEstimado());
            stmt.setDouble(3, servico.getCustoTabelado());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0){
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()){
                    if (generatedKeys.next()) {

                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return servico;
    }

    @Override
    public Servico update(Servico servico){
        String sql = "UPDATE servicos SET nome_servico = ?, tempo_estimado = ?, custo_tabelado = ?, data_ultima_atualizacao = NOW() WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, servico.getNomeServico());
            stmt.setInt(2, servico.getTempoEstimado());
            stmt.setDouble(3, servico.getCustoTabelado());
            stmt.setLong(4, servico.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return servico;
    }

    @Override
    public Optional<Servico> findById(Long id) {
        String sql = "SELECT * FROM servicos WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(ServicoMapper.fromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Servico> findAll() {
        List<Servico> servicos = new ArrayList<>();
        String sql = "SELECT * FROM servicos";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                servicos.add(ServicoMapper.fromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return servicos;
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM servicos WHERE id = ?";
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
        String sql = "SELECT 1 FROM servicos WHERE id = ?";
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