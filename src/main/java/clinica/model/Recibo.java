package clinica.model;

import java.time.LocalDateTime;

public class Recibo {

    private String paciente;
    private String profissional;
    private String dataAtendimento;
    private String servico;
    private double total;
    private LocalDateTime emitidoEm;

    public Recibo(String paciente, String profissional, String dataAtendimento, String servico, double total) {
        this.paciente = paciente;
        this.profissional = profissional;
        this.dataAtendimento = dataAtendimento;
        this.servico = servico;
        this.total = total;
        this.emitidoEm = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Paciente: " + paciente + "\n"
                + "Profissional: " + profissional + "\n"
                + "Data: " + dataAtendimento + "\n"
                + "Servico: " + servico + "\n"
                + "Total: R$ " + String.format("%.2f", total) + "\n"
                + "Emitido em: " + emitidoEm;
    }
}
