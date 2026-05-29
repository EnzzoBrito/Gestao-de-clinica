package clinica.model;

public class Profissional extends Pessoa {

    private String especialidade;
    private double valorConsulta;
    private int duracaoPadraoMinutos;

    public Profissional(String nome, String cpf, int idade, String contato, String especialidade, double valorConsulta, int duracaoPadraoMinutos) {
        super(nome, cpf, idade, contato);
        this.especialidade = especialidade;
        this.valorConsulta = valorConsulta;
        this.duracaoPadraoMinutos = duracaoPadraoMinutos;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public double getValorConsulta() {
        return valorConsulta;
    }

    public int getDuracaoPadraoMinutos() {
        return duracaoPadraoMinutos;
    }

    @Override
    public String toString() {
        return nome + " | " + especialidade + " | R$ " + valorConsulta;
    }
}
