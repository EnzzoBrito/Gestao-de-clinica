# 🏥 Sistema de Gestão de Clínica

### Plataforma de Agendamento, Atendimento e Gestão Administrativa para Clínicas Multidisciplinares

![Status](https://img.shields.io/badge/Status-Completo-brightgreen)
![Java](https://img.shields.io/badge/Java-21-orange)
![Maven](https://img.shields.io/badge/Maven-3.9-red)
![POO](https://img.shields.io/badge/POO-Herança%20%7C%20Polimorfismo%20%7C%20Interfaces-blue)
![Persistência](https://img.shields.io/badge/Persistência-JSON-success)
![Licença](https://img.shields.io/badge/Licença-Acadêmica-lightgrey)

---

## 👥 Equipe

- **Enzo Brito**
- **Anderson Gabriel**

---

## 📖 Sobre o Projeto

O Sistema de Gestão de Clínica foi desenvolvido como projeto acadêmico utilizando **Java**, **Maven** e os princípios da **Programação Orientada a Objetos (POO)**.

O sistema simula o funcionamento de uma clínica multidisciplinar, permitindo o gerenciamento de pacientes, profissionais, salas, atendimentos, faturamento e relatórios gerenciais.

Além disso, foi implementada persistência de dados utilizando arquivos JSON, garantindo o armazenamento permanente das informações cadastradas.

---

## 🎯 Objetivos

- Automatizar processos básicos de uma clínica;
- Aplicar conceitos de Programação Orientada a Objetos;
- Utilizar Maven para gerenciamento de dependências;
- Implementar persistência de dados;
- Organizar o sistema utilizando arquitetura em camadas;
- Simular regras de negócio reais.

---

## 🚀 Funcionalidades

### 👤 Gestão de Pacientes

- Cadastro de pacientes;
- Controle de convênio;
- Controle de prioridade;
- Histórico do paciente.

### 👨‍⚕️ Gestão de Profissionais

- Cadastro de profissionais;
- Especialidade;
- Valor da consulta;
- Controle de disponibilidade.

### 🏢 Gestão de Salas

- Cadastro de salas;
- Controle de ocupação.

### 📅 Agendamento

- Agendamento de consultas;
- Agendamento de procedimentos;
- Verificação automática de conflitos;
- Controle de horários.

### ⏳ Lista de Espera

- Inclusão automática em fila de espera;
- Controle de disponibilidade;
- Aproveitamento de vagas liberadas.

### 💰 Faturamento

- Cálculo automático de consultas;
- Cálculo de procedimentos;
- Convênios;
- Retornos;
- Taxas adicionais.

### 📊 Relatórios

- Atendimentos por profissional;
- Receita por especialidade;
- Relatório de cancelamentos;
- Profissionais mais demandados;
- Controle de ocupação.

---

## 🏗️ Arquitetura do Sistema

```text
┌─────────────┐
│     App     │
└──────┬──────┘
       │
       ▼
┌─────────────┐
│   Service   │
└──────┬──────┘
       │
       ▼
┌─────────────┐
│ Repository  │
└──────┬──────┘
       │
       ▼
┌─────────────┐
│ Arquivos    │
│    JSON     │
└─────────────┘
```

---

## 📂 Estrutura do Projeto

```text
src/main/java/clinica
│
├── model
│   ├── Pessoa
│   ├── Paciente
│   ├── Profissional
│   ├── Atendimento
│   ├── Consulta
│   ├── Procedimento
│   ├── Sala
│   └── StatusAtendimento
│
├── service
│   ├── AgendaService
│   ├── FaturamentoService
│   └── RelatorioService
│
├── repository
│   ├── ClinicaRepository
│   └── AtendimentoAdapter
│
├── interfaces
│   └── Faturavel
│
└── App
```

---

## 🧠 Conceitos de Programação Orientada a Objetos

### 🔹 Herança

Foi utilizada para evitar repetição de código e promover reutilização.

```text
Pessoa
├── Paciente
└── Profissional
```

A classe `Pessoa` concentra atributos comuns:

- Nome
- CPF
- Idade
- Contato

As classes `Paciente` e `Profissional` herdam esses atributos.

---

### 🔹 Polimorfismo

Aplicado através dos diferentes tipos de atendimento.

```text
Atendimento
├── Consulta
└── Procedimento
```

O sistema pode tratar todos como objetos do tipo `Atendimento`, porém cada um possui comportamento próprio.

Exemplo:

```java
atendimento.calcularValor();
```

O resultado depende do tipo real do atendimento.

---

### 🔹 Interface

Foi criada a interface:

```java
public interface Faturavel {
    double calcularValor();
}
```

Qualquer classe que implemente essa interface é obrigada a fornecer sua própria regra de cálculo financeiro.

---

### 🔹 Encapsulamento

Os atributos são protegidos através de modificadores de acesso.

Exemplo:

```java
private String convenio;
private boolean prioridade;
```

O acesso ocorre através de construtores, getters e setters.

---

### 🔹 Composição

A classe Atendimento possui outras classes dentro dela:

```text
Atendimento
├── Paciente
├── Profissional
└── Sala
```

Isso representa corretamente os relacionamentos do mundo real.

---

## 🔄 Fluxo do Sistema

```text
Cadastro
    ↓
Agendamento
    ↓
Verificação de Conflitos
    ↓
Fila de Espera
    ↓
Atendimento
    ↓
Faturamento
    ↓
Recibo
    ↓
Relatórios
```

---

## ⏳ Funcionamento da Lista de Espera

Quando um atendimento não pode ser agendado devido à indisponibilidade de horários ou conflitos de agenda, ele é encaminhado para uma fila de espera.

```java
public void adicionarFilaEspera(Atendimento atendimento) {
    atendimento.setStatus(StatusAtendimento.FILA_ESPERA);
    filaEspera.add(atendimento);
}
```

A fila de espera permite reorganizar automaticamente os atendimentos quando uma vaga é liberada.

---

## 💾 Persistência de Dados

O sistema utiliza a biblioteca **Gson** para converter objetos Java em JSON e vice-versa.

Arquivos utilizados:

```text
pacientes.json
profissionais.json
salas.json
atendimentos.json
```

Fluxo de salvamento:

```text
Objeto Java
     ↓
    Gson
     ↓
    JSON
     ↓
 Arquivo
```

Fluxo de carregamento:

```text
Arquivo JSON
      ↓
     Gson
      ↓
 Objeto Java
```

---

## 🗄️ Integração com Banco de Dados

Nesta versão foi utilizada persistência baseada em arquivos JSON.

Entretanto, a arquitetura foi planejada para permitir futura integração com bancos de dados como:

- MySQL
- PostgreSQL
- SQL Server

A utilização da camada Repository permite substituir a persistência atual sem alterar as regras de negócio implementadas nos Services.

---

## 📦 Dependências Maven

Principais dependências utilizadas:

```xml
<dependency>
    <groupId>com.google.code.gson</groupId>
    <artifactId>gson</artifactId>
    <version>2.13.1</version>
</dependency>
```

Responsável pela serialização e desserialização dos dados.

---

## ⚙️ Tecnologias Utilizadas

| Tecnologia | Finalidade |
|------------|------------|
| Java 21 | Linguagem principal |
| Maven | Gerenciamento de dependências |
| Gson | Manipulação de JSON |
| JSON | Persistência de dados |
| Git | Controle de versão |
| GitHub | Hospedagem do projeto |
| POO | Estruturação do sistema |

---

## ▶️ Como Executar

### Clonar o repositório

```bash
git clone https://github.com/EnzzoBrito/Gestao-de-clinica.git
```

### Entrar na pasta

```bash
cd Gestao-de-clinica
```

### Compilar o projeto

```bash
mvn clean compile
```

### Executar o projeto

```bash
mvn exec:java
```

---

## 📚 Aprendizados Aplicados

Durante o desenvolvimento deste projeto foram aplicados conceitos de:

- Programação Orientada a Objetos;
- Herança;
- Polimorfismo;
- Interfaces;
- Encapsulamento;
- Composição;
- Persistência de Dados;
- Arquitetura em Camadas;
- Maven;
- Git e GitHub.

---

## 🔮 Melhorias Futuras

- Integração com banco de dados MySQL;
- Interface gráfica JavaFX;
- Sistema de autenticação;
- Dashboard administrativo;
- Relatórios em PDF;
- Integração com API de notificações;
- Agendamento online.

---

## 📄 Licença

Projeto desenvolvido para fins acadêmicos como atividade prática da disciplina de Programação Orientada a Objetos.

© 2026 - Enzo Brito & Anderson Gabriel
