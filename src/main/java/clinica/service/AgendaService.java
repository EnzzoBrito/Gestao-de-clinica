package clinica.service;

import clinica.interfaces.Agendavel;
import clinica.model.Atendimento;
import clinica.model.StatusAtendimento;
import clinica.repository.ClinicaRepository;

public class AgendaService implements Agendavel {

    private static final int LIMITE_ATIVOS = 10;
    private static final double TAXA_CANCELAMENTO = 50.0;

    private final ClinicaRepository repository;

    public AgendaService(ClinicaRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean agendar(Atendimento atendimento) {
        long ativos = repository.getAtendimentos().stream()
                .filter(a -> a.getStatus() == StatusAtendimento.AGENDADO)
                .count();

        if (ativos >= LIMITE_ATIVOS || existeConflito(atendimento)) {
            repository.adicionarFilaEspera(atendimento);
            return false;
        }

        repository.adicionarAtendimento(atendimento);
        return true;
    }

    private boolean existeConflito(Atendimento novoAtendimento) {
        return repository.getAtendimentos().stream()
                .filter(a -> a.getStatus() == StatusAtendimento.AGENDADO)
                .anyMatch(novoAtendimento::conflitaCom);
    }

    @Override
    public double cancelar(String atendimentoId, boolean foraDoPrazo) {
        for (Atendimento atendimento : repository.getAtendimentos()) {
            if (atendimento.getId().equals(atendimentoId)) {
                atendimento.setStatus(StatusAtendimento.CANCELADO);
                tentarPromoverFila();
                return foraDoPrazo ? TAXA_CANCELAMENTO : 0.0;
            }
        }
        return 0.0;
    }

    private void tentarPromoverFila() {
        if (repository.getFilaEspera().isEmpty()) return;

        Atendimento candidato = repository.getFilaEspera().remove(0);
        candidato.setStatus(StatusAtendimento.AGENDADO);

        if (!existeConflito(candidato)) {
            repository.adicionarAtendimento(candidato);
            System.out.println("Paciente da fila foi promovido para agendamento: " + candidato.resumo());
        } else {
            candidato.setStatus(StatusAtendimento.FILA_ESPERA);
            repository.getFilaEspera().add(candidato);
        }
    }
}
