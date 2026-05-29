package clinica.model;
import clinica.interfaces.Faturavel;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public abstract class Atendimento implements Faturavel {

    protected String id;
    protected Paciente paciente;
    protected Profissional profissional;
    protected Sala sala;
    protected LocalDate data;
    protected LocalTime hora;
    protected int duracaoMinutos;
    protected StatusAtendimento status;

    public Atendimento(Paciente paciente, Profissional profissional, Sala sala, LocalDate data, LocalTime hora, int duracaoMinutos) {
        this.id = UUID.randomUUID().toString();
        this.paciente = paciente;
        this.profissional = profissional;
        this.sala = sala;
        this.data = data;
        this.hora = hora;
        this.duracaoMinutos = duracaoMinutos;
        this.status = StatusAtendimento.AGENDADO;
    }

    public abstract String getServico();

    public LocalTime getHoraFim() {
        return hora.plusMinutes(duracaoMinutos);
    }

    public boolean conflitaCom(Atendimento outro) {
        if (!this.data.equals(outro.data)) return false;

        boolean mesmoProfissional = this.profissional.getCpf().equals(outro.profissional.getCpf());
        boolean mesmaSala = this.sala.getCodigo().equals(outro.sala.getCodigo());

        if (!mesmoProfissional && !mesmaSala) return false;

        return this.hora.isBefore(outro.getHoraFim()) && outro.hora.isBefore(this.getHoraFim());
    }

    public String resumo() {
        return id.substring(0, 8) + " | " + data + " " + hora + " | " + paciente.getNome()
                + " | " + profissional.getNome() + " | Sala " + sala.getCodigo()
                + " | " + getServico() + " | " + status;
    }

    public String getId() {
        return id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public Profissional getProfissional() {
        return profissional;
    }

    public Sala getSala() {
        return sala;
    }

    public LocalDate getData() {
        return data;
    }

    public LocalTime getHora() {
        return hora;
    }

    public StatusAtendimento getStatus() {
        return status;
    }

    public void setStatus(StatusAtendimento status) {
        this.status = status;
    }
}
