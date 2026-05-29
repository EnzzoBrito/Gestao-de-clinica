# Plataforma de Gestão de Clínica e Agendamentos

Projeto Java com Maven para gerenciar pacientes, profissionais, salas, agendamentos, atendimentos, recibos e relatórios.

## Requisitos atendidos

- Cadastro de pacientes
- Cadastro de profissionais
- Cadastro de salas
- Agendamento de consultas e procedimentos
- Controle de conflito por profissional e sala
- Limite de 10 atendimentos ativos
- Fila de espera
- Finalização de atendimento
- Emissão de recibo
- Cancelamento com taxa configurada
- Relatório mensal
- Herança: `Pessoa`, `Paciente`, `Profissional`
- Polimorfismo: `Atendimento`, `Consulta`, `Procedimento`
- Interface: `Faturavel`, `Agendavel`
- Integração externa: persistência em JSON com Gson

## Como executar

```bash
mvn clean compile
mvn exec:java
```

Ou:

```bash
mvn exec:java "-Dexec.mainClass=clinica.App"
```

## Estrutura principal

```text
src/main/java/clinica
├── App.java
├── interfaces
├── model
├── repository
└── service
```

## Observação

Os dados são salvos em arquivos JSON na raiz do projeto:
- `pacientes.json`
- `profissionais.json`
- `salas.json`
- `atendimentos.json`
