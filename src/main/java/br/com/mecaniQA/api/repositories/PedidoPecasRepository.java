package br.com.mecaniQA.api.repositories;

import br.com.mecaniQA.api.models.ItemPedidoPeca;
import br.com.mecaniQA.api.models.PedidoPecas;
import br.com.mecaniQA.api.models.StatusPedidoPeca;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PedidoPecasRepository {

    private static PedidoPecasRepository instancia;

    //Simulando banco
    private List<PedidoPecas> pedidos;

    // Contadores para geração de ID do pedido e dos itens
    private long contadorPedidoId = 1;
    private long contadorItemId = 1;

    private PedidoPecasRepository() {
        this.pedidos = new ArrayList<>();
    }

    public static PedidoPecasRepository getInstance() {
        if (instancia == null) {
            instancia = new PedidoPecasRepository();
        }
        return instancia;
    }

    // CREATE
    public PedidoPecas salvar(PedidoPecas pedido) {
        pedido.setId(contadorPedidoId++);
        pedido.setDataPedido(LocalDateTime.now());

        if (pedido.getStatus() == null) {
            pedido.setStatus(StatusPedidoPeca.ORCANDO); // Status padrão conforme enum
        }

        // Garante que cada item do pedido receba um ID único
        if (pedido.getItens() != null) {
            for (ItemPedidoPeca item : pedido.getItens()) {
                if (item.getId() == null) {
                    item.setId(contadorItemId++);
                }
                item.setPedidoPecas(pedido);
            }
        }

        this.pedidos.add(pedido);
        return pedido;
    }

    // BUSCAR POR ID
    public PedidoPecas buscarPorId(long id) {
        return this.pedidos.stream()
                .filter(p -> p.getId() != null && p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // ADICIONAR PEÇA A PEDIDO EXISTENTE
    public PedidoPecas adicionarItens(long idPedido, List<ItemPedidoPeca>novosItens) {
        PedidoPecas pedido = buscarPorId(idPedido);

        if (pedido != null && novosItens != null) {
            for (ItemPedidoPeca item : novosItens) {
                item.setId(contadorItemId++);
                item.setPedidoPecas(pedido);
                pedido.getItens().add(item);
            }
            return pedido;
        }
        return null;
    }

    // MODIFICAR STATUS DO PEDIDO
    public PedidoPecas atualizarStatus(long idPedido, StatusPedidoPeca novoStatus) {
        PedidoPecas pedido = buscarPorId(idPedido);

        if (pedido != null) {
            pedido.setStatus(novoStatus);
            return pedido;
        }
        return null;
    }
}