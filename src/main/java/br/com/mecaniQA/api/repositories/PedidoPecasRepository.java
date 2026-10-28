package br.com.mecaniQA.api.repositories;

import br.com.mecaniQA.api.database.DatabaseConnection;
import br.com.mecaniQA.api.models.ItemPedidoPeca;
import br.com.mecaniQA.api.models.PedidoPecas;
import br.com.mecaniQA.api.models.Peca;
import br.com.mecaniQA.api.mappers.PedidoPecasMapper;
import br.com.mecaniQA.api.mappers.PecaMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PedidoPecasRepository implements Repository<PedidoPecas, Long> {

    @Override
    public PedidoPecas save(PedidoPecas pedido) {
        String sqlPedido = "INSERT INTO pedido_pecas (data_pedido, data_previsao, status, valor_total, forma_pagamento, observacoes) VALUES (NOW(), ?, ?, ?, ?, ?)";
        String sqlItens = "INSERT INTO itens_pedido_peca (pedido_pecas_id, peca_id, quantidade) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmtPedido = conn.prepareStatement(sqlPedido, Statement.RETURN_GENERATED_KEYS)) {
                stmtPedido.setTimestamp(1, pedido.getDataPrevisao() != null ? Timestamp.valueOf(pedido.getDataPrevisao()) : null);
                stmtPedido.setString(2, pedido.getStatus() != null ? pedido.getStatus().name() : "PENDENTE");
                stmtPedido.setDouble(3, pedido.getValorTotal());
                stmtPedido.setString(4, pedido.getFormaPagamento());
                stmtPedido.setString(5, pedido.getObservacoes());

                int affectedRows = stmtPedido.executeUpdate();
                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = stmtPedido.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            pedido.setId(generatedKeys.getLong(1));
                        }
                    }
                }

                if (pedido.getItens() != null && !pedido.getItens().isEmpty()) {
                    try (PreparedStatement stmtItens = conn.prepareStatement(sqlItens)) {
                        for (ItemPedidoPeca item : pedido.getItens()) {
                            stmtItens.setLong(1, pedido.getId());
                            stmtItens.setLong(2, item.getPeca().getId());
                            stmtItens.setInt(3, item.getQuantidade());
                            stmtItens.addBatch();
                        }
                        stmtItens.executeBatch();
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
        return pedido;
    }

    @Override
    public PedidoPecas update(PedidoPecas pedido) {
        String sqlPedido = "UPDATE pedido_pecas SET data_previsao = ?, status = ?, valor_total = ?, forma_pagamento = ?, observacoes = ? WHERE id = ?";
        String deleteItens = "DELETE FROM itens_pedido_peca WHERE pedido_pecas_id = ?";
        String insertItens = "INSERT INTO itens_pedido_peca (pedido_pecas_id, peca_id, quantidade) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmtPedido = conn.prepareStatement(sqlPedido)) {
                stmtPedido.setTimestamp(1, pedido.getDataPrevisao() != null ? Timestamp.valueOf(pedido.getDataPrevisao()) : null);
                stmtPedido.setString(2, pedido.getStatus() != null ? pedido.getStatus().name() : null);
                stmtPedido.setDouble(3, pedido.getValorTotal());
                stmtPedido.setString(4, pedido.getFormaPagamento());
                stmtPedido.setString(5, pedido.getObservacoes());
                stmtPedido.setLong(6, pedido.getId());

                stmtPedido.executeUpdate();

                // Limpa os itens antigos da tabela associativa
                try (PreparedStatement stmtDel = conn.prepareStatement(deleteItens)) {
                    stmtDel.setLong(1, pedido.getId());
                    stmtDel.executeUpdate();
                }

                // Insere os novos itens
                if (pedido.getItens() != null && !pedido.getItens().isEmpty()) {
                    try (PreparedStatement stmtIns = conn.prepareStatement(insertItens)) {
                        for (ItemPedidoPeca item : pedido.getItens()) {
                            stmtIns.setLong(1, pedido.getId());
                            stmtIns.setLong(2, item.getPeca().getId());
                            stmtIns.setInt(3, item.getQuantidade());
                            stmtIns.addBatch();
                        }
                        stmtIns.executeBatch();
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
        return pedido;
    }

    @Override
    public Optional<PedidoPecas> findById(Long id) {
        String sql = "SELECT * FROM pedido_pecas WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    PedidoPecas pedido = PedidoPecasMapper.fromResultSet(rs);
                    pedido.setItens(carregarItens(pedido.getId(), conn));
                    return Optional.of(pedido);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<PedidoPecas> findAll() {
        List<PedidoPecas> pedidos = new ArrayList<>();
        String sql = "SELECT * FROM pedido_pecas";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                PedidoPecas pedido = PedidoPecasMapper.fromResultSet(rs);
                pedido.setItens(carregarItens(pedido.getId(), conn));
                pedidos.add(pedido);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pedidos;
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM pedido_pecas WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate(); // ON DELETE CASCADE cuidará de remover da tabela associativa
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean existsById(Long id) {
        String sql = "SELECT 1 FROM pedido_pecas WHERE id = ?";
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

    // Método auxiliar para buscar os itens e a peça correspondente na tabela associativa
    private List<ItemPedidoPeca> carregarItens(Long pedidoId, Connection conn) throws SQLException {
        List<ItemPedidoPeca> itens = new ArrayList<>();
        // Trazemos a quantidade da tabela associativa e todos os dados da tabela pecas
        String sql = "SELECT i.quantidade, p.* FROM itens_pedido_peca i INNER JOIN pecas p ON i.peca_id = p.id WHERE i.pedido_pecas_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, pedidoId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ItemPedidoPeca item = new ItemPedidoPeca();
                    item.setQuantidade(rs.getInt("quantidade"));

                    // O PecaMapper lê os dados da peça que vieram no p.* do SQL
                    Peca peca = PecaMapper.fromResultSet(rs);
                    item.setPeca(peca);

                    itens.add(item);
                }
            }
        }
        return itens;
    }
}