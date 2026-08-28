package br.com.mecaniQA.api.repositories;

import br.com.mecaniQA.api.models.Servico;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ServicoRepository {

    private static ServicoRepository instancia;

    private List<Servico> servicos;

    private long contadorId = 1;

    private ServicoRepository(){
        this.servicos = new ArrayList<>();
    }

    public static ServicoRepository getInstance() {
        if (instancia == null){
            instancia = new ServicoRepository();
        }
        return instancia;
    }

    //CRUD

    //Create
    public Servico salvar(Servico servico){
        servico.setId(contadorId++);
        servico,setDataCadastro(LocalDateTime.now());
        servico.setDataUltimaAtualizacao(LocalDateTime.now());

        this.servicos.add(servico);
        return servico;
    }

    //Read
    public List<Servico> listarTodos() {
        return new ArrayList<>(this.servicos);
    }

    //Read
    public Servico buscarPorId(long id){
        return this.servicos.stream()
                .filter(s -> s.getId() == id)
                .findFirst()
                .orElse(null);
    }

    //Update
    public Servico atualizar(long id, Servico servicoAtualizado) {
        Servico servicoExistente = buscarPorId(id);

        if (servicoExistente != null) {
            servicoExistente.setTempoEstimado(servicoAtualizado.getTempoEstimado());
            servicoExistente.setCustoTabelado(servicoAtualizado.getCustoTabelado());

            servicoExistente.setDataUltimaAtualizacao(LocalDateTime.now());

            return servicoExistente;
        }

        return null;
    }

    //Delete
    public boolean deletar(long id) {
        Servico servico = buscarPorId(id);
        if (servico != null){
            this.servicos.remove(servico);
            return true;
        }
        return false;
    }
}
