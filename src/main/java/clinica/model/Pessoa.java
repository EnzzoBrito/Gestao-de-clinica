package clinica.model;

public abstract class Pessoa {

    protected String nome;
    protected String cpf;
    protected int idade;
    protected String contato;

    public Pessoa(String nome, String cpf, int idade, String contato) {
        this.nome = nome;
        this.cpf = cpf;
        this.idade = idade;
        this.contato = contato;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public int getIdade() {
        return idade;
    }

    public String getContato() {
        return contato;
    }
}
