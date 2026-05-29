package clinica.model;

public class Paciente extends Pessoa {

    private String convenio;
    private boolean prioridade;
    private String historico;

    public Paciente(String nome, String cpf, int idade, String contato, String convenio, boolean prioridade, String historico) {
        super(nome, cpf, idade, contato);
        this.convenio = convenio;
        this.prioridade = prioridade;
        this.historico = historico;
    }

    public String getConvenio() {
        return convenio;
    }

    public boolean isPrioridade() {
        return prioridade;
    }

    public String getHistorico() {
        return historico;
    }

    @Override
    public String toString() {
        return nome + " | CPF: " + cpf + " | Convenio: " + convenio + " | Prioridade: " + (prioridade ? "Sim" : "Nao");
    }
}
