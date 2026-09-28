package br.com.mecaniQA.api.repositories;

import br.com.mecaniQA.api.models.OrdemServico;
import br.com.mecaniQA.api.models.StatusOS;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrdemServicoRepository {

    private static OrdemServicoRepository instancia;

    // Lista tipada com simulando banco de dados em memória
    private List<OrdemServico> ordensServico;
    private long contadorId = 1;

    // Construtor privado
    private OrdemServicoRepository() {
        this.ordensServico = new ArrayList<>();
    }

    // Método para recuperar instancia unica
    public static OrdemServicoRepository getInstance() {
        if (instancia == null) {
            instancia = new OrdemServicoRepository();
        }
        return instancia;
    }

    // CREATE
    public OrdemServico salvar(OrdemServico os){
        os.setId(contadorId++);
        os.setDataAbertura(LocalDateTime.now());

        // Se o OS vier sem status, define como aberto por padrão
        if(os.getStatus() == null) {
            os.setStatus(StatusOS.ABERTO);
        }

        this.ordensServico.add(os);
        return os;
    }

    // READ GERAL
    public List<OrdemServico> listarTodas() {
        return new ArrayList<>(this.ordensServico);
    }

    // READ ESPECIFICO POR ID
    public OrdemServico buscarPorId(long id){
        return this.ordensServico.stream()
                .filter(os -> os.getId() != null && os.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // UPDATE
    public OrdemServico atualizarStatus(long id, StatusOS novoStatus){
        OrdemServico osExistente = buscarPorId(id);

        if(osExistente != null){
            osExistente.setStatus(novoStatus);

            // Se o serviço foi finalizado, preenche a data de fechamento
            if(novoStatus == StatusOS.EXECUTADO){
                osExistente.setDataFechamento(LocalDateTime.now());
            }

            return osExistente;
        }

        return null;
    }
}