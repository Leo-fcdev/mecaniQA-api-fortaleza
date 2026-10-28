package br.com.mecaniQA.api.repositories;

import br.com.mecaniQA.api.database.DatabaseConnection;
import br.com.mecaniQA.api.models.OrdemServico;
import br.com.mecaniQA.api.models.Peca;
import br.com.mecaniQA.api.models.Servico;
import br.com.mecaniQA.api.mappers.OrdemServicoMapper;
import br.com.mecaniQA.api.mappers.PecaMapper;
import br.com.mecaniQA.api.mappers.ServicoMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrdemServicoRepository implements Repository<OrdemServico, Long> {

    @Override
    public OrdemServico save(OrdemServico os) {
        String sqlOS = "INSERT INTO ordens_servico (id_cliente, data_abertura, data_fechamento, status, valor_total, desconto) VALUES (?, NOW(), ?, ?, ?, ?)";
        String sqlPecas = "INSERT INTO ordens_servico_pecas (ordem_servico_id, peca_id) VALUES (?, ?)";
        String sqlServicos = "INSERT INTO ordens_servico_servicos (ordem_servico_id, servico_id) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmtOS = conn.prepareStatement(sqlOS, Statement.RETURN_GENERATED_KEYS)) {
                stmtOS.setLong(1, os.getIdCliente());
                stmtOS.setTimestamp(2, os.getDataFechamento() != null ? Timestamp.valueOf(os.getDataFechamento()) : null);
                stmtOS.setString(3, os.getStatus() != null ? os.getStatus().name() : "PENDENTE");
                stmtOS.setDouble(4, os.getValorTotal());
                stmtOS.setDouble(5, os.getDesconto());

                int affectedRows = stmtOS.executeUpdate();
                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = stmtOS.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            os.setId(generatedKeys.getLong(1));
                        }
                    }
                }

                if (os.getPecas() != null && !os.getPecas().isEmpty()) {
                    try (PreparedStatement stmtPecas = conn.prepareStatement(sqlPecas)) {
                        for (Peca peca : os.getPecas()) {
                            stmtPecas.setLong(1, os.getId());
                            stmtPecas.setLong(2, peca.getId());
                            stmtPecas.addBatch();
                        }
                        stmtPecas.executeBatch();
                    }
                }

                if (os.getServicos() != null && !os.getServicos().isEmpty()) {
                    try (PreparedStatement stmtServicos = conn.prepareStatement(sqlServicos)) {
                        for (Servico servico : os.getServicos()) {
                            stmtServicos.setLong(1, os.getId());
                            stmtServicos.setLong(2, servico.getId());
                            stmtServicos.addBatch();
                        }
                        stmtServicos.executeBatch();
                    }
                }

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return os;
    }

    @Override
    public OrdemServico update(OrdemServico os) {
        String sqlOS = "UPDATE ordens_servico SET status = ?, valor_total = ?, desconto = ?, data_fechamento = ? WHERE id = ?";
        String deletePecas = "DELETE FROM ordens_servico_pecas WHERE ordem_servico_id = ?";
        String deleteServicos = "DELETE FROM ordens_servico_servicos WHERE ordem_servico_id = ?";
        String insertPecas = "INSERT INTO ordens_servico_pecas (ordem_servico_id, peca_id) VALUES (?, ?)";
        String insertServicos = "INSERT INTO ordens_servico_servicos (ordem_servico_id, servico_id) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmtOS = conn.prepareStatement(sqlOS)) {
                stmtOS.setString(1, os.getStatus() != null ? os.getStatus().name() : null);
                stmtOS.setDouble(2, os.getValorTotal());
                stmtOS.setDouble(3, os.getDesconto());
                stmtOS.setTimestamp(4, os.getDataFechamento() != null ? Timestamp.valueOf(os.getDataFechamento()) : null);
                stmtOS.setLong(5, os.getId());
                stmtOS.executeUpdate();

                // Limpa as peças antigas e insere as novas
                try (PreparedStatement stmtDelPecas = conn.prepareStatement(deletePecas)) {
                    stmtDelPecas.setLong(1, os.getId());
                    stmtDelPecas.executeUpdate();
                }
                if (os.getPecas() != null && !os.getPecas().isEmpty()) {
                    try (PreparedStatement stmtInsPecas = conn.prepareStatement(insertPecas)) {
                        for (Peca peca : os.getPecas()) {
                            stmtInsPecas.setLong(1, os.getId());
                            stmtInsPecas.setLong(2, peca.getId());
                            stmtInsPecas.addBatch();
                        }
                        stmtInsPecas.executeBatch();
                    }
                }

                // Limpa os serviços antigos e insere os novos
                try (PreparedStatement stmtDelServicos = conn.prepareStatement(deleteServicos)) {
                    stmtDelServicos.setLong(1, os.getId());
                    stmtDelServicos.executeUpdate();
                }
                if (os.getServicos() != null && !os.getServicos().isEmpty()) {
                    try (PreparedStatement stmtInsServicos = conn.prepareStatement(insertServicos)) {
                        for (Servico servico : os.getServicos()) {
                            stmtInsServicos.setLong(1, os.getId());
                            stmtInsServicos.setLong(2, servico.getId());
                            stmtInsServicos.addBatch();
                        }
                        stmtInsServicos.executeBatch();
                    }
                }

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return os;
    }

    @Override
    public Optional<OrdemServico> findById(Long id) {
        String sql = "SELECT * FROM ordens_servico WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    OrdemServico os = OrdemServicoMapper.fromResultSet(rs);
                    os.setPecas(carregarPecas(os.getId(), conn));
                    os.setServicos(carregarServicos(os.getId(), conn));
                    return Optional.of(os);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<OrdemServico> findAll() {
        List<OrdemServico> ordens = new ArrayList<>();
        String sql = "SELECT * FROM ordens_servico";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                OrdemServico os = OrdemServicoMapper.fromResultSet(rs);
                os.setPecas(carregarPecas(os.getId(), conn));
                os.setServicos(carregarServicos(os.getId(), conn));
                ordens.add(os);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ordens;
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM ordens_servico WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate(); // ON DELETE CASCADE no banco cuidará das tabelas associativas
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean existsById(Long id) {
        String sql = "SELECT 1 FROM ordens_servico WHERE id = ?";
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

    // Métodos auxiliares para buscar os itens nas tabelas associativas
    private List<Peca> carregarPecas(Long osId, Connection conn) throws SQLException {
        List<Peca> pecas = new ArrayList<>();
        String sql = "SELECT p.* FROM pecas p INNER JOIN ordens_servico_pecas osp ON p.id = osp.peca_id WHERE osp.ordem_servico_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, osId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    pecas.add(PecaMapper.fromResultSet(rs));
                }
            }
        }
        return pecas;
    }

    private List<Servico> carregarServicos(Long osId, Connection conn) throws SQLException {
        List<Servico> servicos = new ArrayList<>();
        String sql = "SELECT s.* FROM servicos s INNER JOIN ordens_servico_servicos oss ON s.id = oss.servico_id WHERE oss.ordem_servico_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, osId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    servicos.add(ServicoMapper.fromResultSet(rs));
                }
            }
        }
        return servicos;
    }
}