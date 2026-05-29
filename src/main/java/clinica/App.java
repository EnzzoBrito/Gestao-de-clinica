package clinica;

import clinica.model.*;
import clinica.repository.ClinicaRepository;
import clinica.service.AgendaService;
import clinica.service.FaturamentoService;
import clinica.service.RelatorioService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

public class App {

    private static final Scanner scanner = new Scanner(System.in);
    private static final ClinicaRepository repository = new ClinicaRepository();
    private static final AgendaService agendaService = new AgendaService(repository);
    private static final FaturamentoService faturamentoService = new FaturamentoService(repository);
    private static final RelatorioService relatorioService = new RelatorioService(repository);

    public static void main(String[] args) {
        repository.carregar();

        int opcao;
        do {
            System.out.println("\n===== SISTEMA DE GESTAO DE CLINICA =====");
            System.out.println("1 - Cadastrar paciente");
            System.out.println("2 - Cadastrar profissional");
            System.out.println("3 - Cadastrar sala");
            System.out.println("4 - Agendar consulta");
            System.out.println("5 - Agendar procedimento");
            System.out.println("6 - Listar atendimentos");
            System.out.println("7 - Finalizar atendimento e emitir recibo");
            System.out.println("8 - Cancelar atendimento");
            System.out.println("9 - Relatorio mensal");
            System.out.println("10 - Listar fila de espera");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");

            opcao = lerInteiro();

            switch (opcao) {
                case 1 -> cadastrarPaciente();
                case 2 -> cadastrarProfissional();
                case 3 -> cadastrarSala();
                case 4 -> agendarConsulta();
                case 5 -> agendarProcedimento();
                case 6 -> relatorioService.listarAtendimentos();
                case 7 -> finalizarAtendimento();
                case 8 -> cancelarAtendimento();
                case 9 -> relatorioService.relatorioMensal();
                case 10 -> relatorioService.listarFilaEspera();
                case 0 -> {
                    repository.salvar();
                    System.out.println("Sistema encerrado.");
                }
                default -> System.out.println("Opcao invalida.");
            }
        } while (opcao != 0);
    }

    private static void cadastrarPaciente() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("CPF ou codigo: ");
        String cpf = scanner.nextLine();

        System.out.print("Idade: ");
        int idade = lerInteiro();

        System.out.print("Contato: ");
        String contato = scanner.nextLine();

        System.out.print("Convenio (ou PARTICULAR): ");
        String convenio = scanner.nextLine();

        System.out.print("Prioridade? (s/n): ");
        boolean prioridade = scanner.nextLine().equalsIgnoreCase("s");

        System.out.print("Historico basico: ");
        String historico = scanner.nextLine();

        Paciente paciente = new Paciente(nome, cpf, idade, contato, convenio, prioridade, historico);
        repository.adicionarPaciente(paciente);
        repository.salvar();

        System.out.println("Paciente cadastrado.");
    }

    private static void cadastrarProfissional() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("CPF ou codigo: ");
        String cpf = scanner.nextLine();

        System.out.print("Idade: ");
        int idade = lerInteiro();

        System.out.print("Contato: ");
        String contato = scanner.nextLine();

        System.out.print("Especialidade: ");
        String especialidade = scanner.nextLine();

        System.out.print("Valor da consulta: ");
        double valorConsulta = lerDouble();

        System.out.print("Duracao padrao em minutos: ");
        int duracao = lerInteiro();

        Profissional profissional = new Profissional(nome, cpf, idade, contato, especialidade, valorConsulta, duracao);
        repository.adicionarProfissional(profissional);
        repository.salvar();

        System.out.println("Profissional cadastrado.");
    }

    private static void cadastrarSala() {
        System.out.print("Codigo da sala: ");
        String codigo = scanner.nextLine();

        System.out.print("Descricao: ");
        String descricao = scanner.nextLine();

        repository.adicionarSala(new Sala(codigo, descricao));
        repository.salvar();

        System.out.println("Sala cadastrada.");
    }

    private static void agendarConsulta() {
        Paciente paciente = escolherPaciente();
        Profissional profissional = escolherProfissional();
        Sala sala = escolherSala();

        if (paciente == null || profissional == null || sala == null) {
            System.out.println("Cadastre paciente, profissional e sala antes de agendar.");
            return;
        }

        System.out.print("Data (AAAA-MM-DD): ");
        LocalDate data = LocalDate.parse(scanner.nextLine());

        System.out.print("Hora (HH:MM): ");
        LocalTime hora = LocalTime.parse(scanner.nextLine());

        System.out.print("Tipo (PARTICULAR, CONVENIO, RETORNO): ");
        TipoAtendimento tipo = TipoAtendimento.valueOf(scanner.nextLine().toUpperCase());

        Consulta consulta = new Consulta(paciente, profissional, sala, data, hora, profissional.getDuracaoPadraoMinutos(), tipo);

        boolean agendado = agendaService.agendar(consulta);
        repository.salvar();

        System.out.println(agendado ? "Consulta agendada." : "Conflito detectado. Paciente enviado para fila de espera.");
    }

    private static void agendarProcedimento() {
        Paciente paciente = escolherPaciente();
        Profissional profissional = escolherProfissional();
        Sala sala = escolherSala();

        if (paciente == null || profissional == null || sala == null) {
            System.out.println("Cadastre paciente, profissional e sala antes de agendar.");
            return;
        }

        System.out.print("Data (AAAA-MM-DD): ");
        LocalDate data = LocalDate.parse(scanner.nextLine());

        System.out.print("Hora (HH:MM): ");
        LocalTime hora = LocalTime.parse(scanner.nextLine());

        System.out.print("Descricao do procedimento: ");
        String descricao = scanner.nextLine();

        System.out.print("Valor adicional: ");
        double valorAdicional = lerDouble();

        Procedimento procedimento = new Procedimento(paciente, profissional, sala, data, hora, profissional.getDuracaoPadraoMinutos(), descricao, valorAdicional);

        boolean agendado = agendaService.agendar(procedimento);
        repository.salvar();

        System.out.println(agendado ? "Procedimento agendado." : "Conflito detectado. Paciente enviado para fila de espera.");
    }

    private static void finalizarAtendimento() {
        Atendimento atendimento = escolherAtendimento(StatusAtendimento.AGENDADO);
        if (atendimento == null) {
            System.out.println("Nenhum atendimento agendado encontrado.");
            return;
        }

        atendimento.setStatus(StatusAtendimento.FINALIZADO);
        Recibo recibo = faturamentoService.emitirRecibo(atendimento);
        repository.salvar();

        System.out.println("\n===== RECIBO =====");
        System.out.println(recibo);
    }

    private static void cancelarAtendimento() {
        Atendimento atendimento = escolherAtendimento(StatusAtendimento.AGENDADO);
        if (atendimento == null) {
            System.out.println("Nenhum atendimento agendado encontrado.");
            return;
        }

        System.out.print("Cancelamento fora do prazo? (s/n): ");
        boolean foraPrazo = scanner.nextLine().equalsIgnoreCase("s");

        double taxa = agendaService.cancelar(atendimento.getId(), foraPrazo);
        repository.salvar();

        System.out.println("Atendimento cancelado. Taxa: R$ " + taxa);
    }

    private static Paciente escolherPaciente() {
        List<Paciente> pacientes = repository.getPacientes();
        for (int i = 0; i < pacientes.size(); i++) {
            System.out.println((i + 1) + " - " + pacientes.get(i));
        }
        if (pacientes.isEmpty()) return null;
        System.out.print("Paciente: ");
        int index = lerInteiro() - 1;
        return index >= 0 && index < pacientes.size() ? pacientes.get(index) : null;
    }

    private static Profissional escolherProfissional() {
        List<Profissional> profissionais = repository.getProfissionais();
        for (int i = 0; i < profissionais.size(); i++) {
            System.out.println((i + 1) + " - " + profissionais.get(i));
        }
        if (profissionais.isEmpty()) return null;
        System.out.print("Profissional: ");
        int index = lerInteiro() - 1;
        return index >= 0 && index < profissionais.size() ? profissionais.get(index) : null;
    }

    private static Sala escolherSala() {
        List<Sala> salas = repository.getSalas();
        for (int i = 0; i < salas.size(); i++) {
            System.out.println((i + 1) + " - " + salas.get(i));
        }
        if (salas.isEmpty()) return null;
        System.out.print("Sala: ");
        int index = lerInteiro() - 1;
        return index >= 0 && index < salas.size() ? salas.get(index) : null;
    }

    private static Atendimento escolherAtendimento(StatusAtendimento status) {
        List<Atendimento> atendimentos = repository.getAtendimentos().stream()
                .filter(a -> a.getStatus() == status)
                .toList();

        for (int i = 0; i < atendimentos.size(); i++) {
            System.out.println((i + 1) + " - " + atendimentos.get(i).resumo());
        }

        if (atendimentos.isEmpty()) return null;

        System.out.print("Atendimento: ");
        int index = lerInteiro() - 1;
        return index >= 0 && index < atendimentos.size() ? atendimentos.get(index) : null;
    }

    private static int lerInteiro() {
        while (!scanner.hasNextInt()) {
            System.out.print("Digite um numero inteiro: ");
            scanner.next();
        }
        int valor = scanner.nextInt();
        scanner.nextLine();
        return valor;
    }

    private static double lerDouble() {
        while (!scanner.hasNextDouble()) {
            System.out.print("Digite um numero valido: ");
            scanner.next();
        }
        double valor = scanner.nextDouble();
        scanner.nextLine();
        return valor;
    }
}
