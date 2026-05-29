package clinica.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Procedimento extends Atendimento {

    private String descricao;
    private double valorAdicional;

    public Procedimento(Paciente paciente, Profissional profissional, Sala sala, LocalDate data, LocalTime hora, int duracaoMinutos, String descricao, double valorAdicional) {
        super(paciente, profissional, sala, data, hora, duracaoMinutos);
        this.descricao = descricao;
        this.valorAdicional = valorAdicional;
    }

    @Override
    public double calcularValor() {
        return profissional.getValorConsulta() + valorAdicional;
    }

    @Override
    public String getServico() {
        return "Procedimento: " + descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getValorAdicional() {
        return valorAdicional;
    }
}
