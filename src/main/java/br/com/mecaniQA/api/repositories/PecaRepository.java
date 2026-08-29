package br.com.mecaniQA.api.repositories;

import br.com.mecaniQA.api.models.Peca;
import java.util.ArrayList;
import java.util.List;

public class PecaRepository {

    private static PecaRepository instancia;

    //Lista simulando Banco
    private List<Peca> pecas;

    private long contadorId = 1;

    private PecaRepository(){
        this.pecas = new ArrayList<>();
    }

    public static PecaRepository getInstance(){
        if (instancia == null){
            instancia = new PecaRepository();
        }
        return instancia;
    }

    //CRUD

    //Create
    public Peca salvar(Peca peca){
        peca.setId(contadorId++);

        peca.setDataCadastro(java.time.LocalDateTime.now());
        peca.setDataUltimaAtualizacao(java.time.LocalDateTime.now());

        this.pecas.add(peca);
        return peca;
    }

    //Read
    public List<Peca> listarTodas() {
        return  new ArrayList<>(this.pecas);
    }

    //Read
    public Peca buscarPorId(long id){
        return this.pecas.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    //Update
    public Peca atualizar(long id, Peca pecaAtualizada){
        Peca pecaExistente = buscarPorId(id);

        if (pecaExistente != null){
            pecaExistente.setPrecoCusto(pecaAtualizada.getPrecoCusto());
            pecaExistente.setPrecoVenda(pecaAtualizada.getPrecoVenda());
            pecaExistente.setQtdEstoque(pecaAtualizada.getQtdEstoque());

            pecaExistente.setDataUltimaAtualizacao(java.time.LocalDateTime.now());

            return pecaExistente;
        }

        return null;
    }

    //Delete
    public boolean deletar(long id) {
        Peca peca = buscarPorId(id);
        if (peca != null){
            this.pecas.remove(peca);
            return true;
        }
        return false;
    }
}
