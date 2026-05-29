package clinica.service;

import clinica.model.Atendimento;
import clinica.model.StatusAtendimento;
import clinica.repository.ClinicaRepository;

import java.util.Map;
import java.util.stream.Collectors;

public class RelatorioService {

    private final ClinicaRepository repository;

    public RelatorioService(ClinicaRepository repository) {
        this.repository = repository;
    }

    public void listarAtendimentos() {
        System.out.println("\n===== ATENDIMENTOS =====");
        if (repository.getAtendimentos().isEmpty()) {
            System.out.println("Nenhum atendimento cadastrado.");
            return;
        }

        repository.getAtendimentos().forEach(a -> System.out.println(a.resumo()));
    }

    public void listarFilaEspera() {
        System.out.println("\n===== FILA DE ESPERA =====");
        if (repository.getFilaEspera().isEmpty()) {
            System.out.println("Fila vazia.");
            return;
        }

        repository.getFilaEspera().forEach(a -> System.out.println(a.resumo()));
    }

    public void relatorioMensal() {
        System.out.println("\n===== RELATORIO MENSAL =====");

        long finalizados = repository.getAtendimentos().stream()
                .filter(a -> a.getStatus() == StatusAtendimento.FINALIZADO)
                .count();

        long cancelados = repository.getAtendimentos().stream()
                .filter(a -> a.getStatus() == StatusAtendimento.CANCELADO)
                .count();

        double receita = repository.getAtendimentos().stream()
                .filter(a -> a.getStatus() == StatusAtendimento.FINALIZADO)
                .mapToDouble(Atendimento::calcularValor)
                .sum();

        Map<String, Double> receitaPorEspecialidade = repository.getAtendimentos().stream()
                .filter(a -> a.getStatus() == StatusAtendimento.FINALIZADO)
                .collect(Collectors.groupingBy(
                        a -> a.getProfissional().getEspecialidade(),
                        Collectors.summingDouble(Atendimento::calcularValor)
                ));

        Map<String, Long> demandaPorProfissional = repository.getAtendimentos().stream()
                .collect(Collectors.groupingBy(
                        a -> a.getProfissional().getNome(),
                        Collectors.counting()
                ));

        int capacidadeMensalSimples = 10;
        double taxaOcupacao = capacidadeMensalSimples == 0 ? 0 : (repository.getAtendimentos().size() * 100.0 / capacidadeMensalSimples);

        System.out.println("Pacientes atendidos: " + finalizados);
        System.out.println("Cancelamentos: " + cancelados);
        System.out.println("Receita total: R$ " + String.format("%.2f", receita));
        System.out.println("Taxa de ocupacao da agenda: " + String.format("%.2f", taxaOcupacao) + "%");
        System.out.println("Horarios ociosos estimados: " + Math.max(0, capacidadeMensalSimples - repository.getAtendimentos().size()));
        System.out.println("Receita por especialidade: " + receitaPorEspecialidade);
        System.out.println("Profissionais mais demandados: " + demandaPorProfissional);
    }
}
