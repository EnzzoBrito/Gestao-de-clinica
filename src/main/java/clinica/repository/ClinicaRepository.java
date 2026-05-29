package clinica.repository;

import clinica.model.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ClinicaRepository {

    private static final String ARQUIVO_PACIENTES = "pacientes.json";
    private static final String ARQUIVO_PROFISSIONAIS = "profissionais.json";
    private static final String ARQUIVO_SALAS = "salas.json";
    private static final String ARQUIVO_ATENDIMENTOS = "atendimentos.json";

    private List<Paciente> pacientes = new ArrayList<>();
    private List<Profissional> profissionais = new ArrayList<>();
    private List<Sala> salas = new ArrayList<>();
    private List<Atendimento> atendimentos = new ArrayList<>();
    private List<Atendimento> filaEspera = new ArrayList<>();

    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>) (src, typeOfSrc, context) -> new JsonPrimitive(src.toString()))
            .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (json, typeOfT, context) -> LocalDate.parse(json.getAsString()))
            .registerTypeAdapter(LocalTime.class, (JsonSerializer<LocalTime>) (src, typeOfSrc, context) -> new JsonPrimitive(src.toString()))
            .registerTypeAdapter(LocalTime.class, (JsonDeserializer<LocalTime>) (json, typeOfT, context) -> LocalTime.parse(json.getAsString()))
            .registerTypeAdapter(Atendimento.class, new AtendimentoAdapter())
            .setPrettyPrinting()
            .create();

    public void carregar() {
        pacientes = carregarLista(ARQUIVO_PACIENTES, new TypeToken<List<Paciente>>() {}.getType());
        profissionais = carregarLista(ARQUIVO_PROFISSIONAIS, new TypeToken<List<Profissional>>() {}.getType());
        salas = carregarLista(ARQUIVO_SALAS, new TypeToken<List<Sala>>() {}.getType());
        atendimentos = carregarLista(ARQUIVO_ATENDIMENTOS, new TypeToken<List<Atendimento>>() {}.getType());

        if (salas.isEmpty()) {
            salas.add(new Sala("01", "Sala de consulta"));
            salas.add(new Sala("02", "Sala de procedimentos"));
        }
    }

    public void salvar() {
        salvarLista(ARQUIVO_PACIENTES, pacientes);
        salvarLista(ARQUIVO_PROFISSIONAIS, profissionais);
        salvarLista(ARQUIVO_SALAS, salas);
        salvarLista(ARQUIVO_ATENDIMENTOS, atendimentos);
    }

    private <T> List<T> carregarLista(String arquivo, Type tipo) {
        try (FileReader reader = new FileReader(arquivo)) {
            List<T> lista = gson.fromJson(reader, tipo);
            return lista != null ? lista : new ArrayList<>();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private void salvarLista(String arquivo, Object lista) {
        try (FileWriter writer = new FileWriter(arquivo)) {
            gson.toJson(lista, writer);
        } catch (Exception e) {
            System.out.println("Erro ao salvar " + arquivo + ": " + e.getMessage());
        }
    }

    public void adicionarPaciente(Paciente paciente) {
        pacientes.add(paciente);
    }

    public void adicionarProfissional(Profissional profissional) {
        profissionais.add(profissional);
    }

    public void adicionarSala(Sala sala) {
        salas.add(sala);
    }

    public void adicionarAtendimento(Atendimento atendimento) {
        atendimentos.add(atendimento);
    }

    public void adicionarFilaEspera(Atendimento atendimento) {
        atendimento.setStatus(StatusAtendimento.FILA_ESPERA);
        filaEspera.add(atendimento);
    }

    public List<Paciente> getPacientes() {
        return pacientes;
    }

    public List<Profissional> getProfissionais() {
        return profissionais;
    }

    public List<Sala> getSalas() {
        return salas;
    }

    public List<Atendimento> getAtendimentos() {
        return atendimentos;
    }

    public List<Atendimento> getFilaEspera() {
        return filaEspera;
    }
}
