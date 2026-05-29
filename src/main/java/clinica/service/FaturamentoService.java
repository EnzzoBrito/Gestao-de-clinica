package clinica.service;

import clinica.model.Atendimento;
import clinica.model.Recibo;
import clinica.repository.ClinicaRepository;

public class FaturamentoService {

    private final ClinicaRepository repository;

    public FaturamentoService(ClinicaRepository repository) {
        this.repository = repository;
    }

    public Recibo emitirRecibo(Atendimento atendimento) {
        return new Recibo(
                atendimento.getPaciente().getNome(),
                atendimento.getProfissional().getNome(),
                atendimento.getData() + " " + atendimento.getHora(),
                atendimento.getServico(),
                atendimento.calcularValor()
        );
    }
}
