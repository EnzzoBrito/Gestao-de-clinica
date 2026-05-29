package clinica.interfaces;

import clinica.model.Atendimento;

public interface Agendavel {
    boolean agendar(Atendimento atendimento);
    double cancelar(String atendimentoId, boolean foraDoPrazo);
}
