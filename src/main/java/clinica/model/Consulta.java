package clinica.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Consulta extends Atendimento {

    private TipoAtendimento tipo;

    public Consulta(Paciente paciente, Profissional profissional, Sala sala, LocalDate data, LocalTime hora, int duracaoMinutos, TipoAtendimento tipo) {
        super(paciente, profissional, sala, data, hora, duracaoMinutos);
        this.tipo = tipo;
    }

    @Override
    public double calcularValor() {
        double base = profissional.getValorConsulta();

        return switch (tipo) {
            case PARTICULAR -> base;
            case CONVENIO -> base * 0.60;
            case RETORNO -> 0.0;
            case PROCEDIMENTO -> base;
        };
    }

    @Override
    public String getServico() {
        return "Consulta " + tipo;
    }

    public TipoAtendimento getTipo() {
        return tipo;
    }
}
