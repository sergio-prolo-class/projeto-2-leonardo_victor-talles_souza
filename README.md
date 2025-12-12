# 🛡️ Projeto 02 (POO) - Java of Empires

**Disciplina:** Programação Orientada a Objetos  
**Professor:** Sergio Maurício Prolo Santos Junior  
**Alunos:** **Leonardo Victor** e **Talles Souza**

Este projeto é inspirado em jogos RTS e utiliza conceitos de Programação Orientada a Objetos, arquitetura modular e interface gráfica em Java (Swing).  
A seleção das funcionalidades foi feita através da **Árvore de Requisitos**, acumulando pontos até atingir o necessário.

---

# 🎯 Objetivos do Projeto

- Aplicar **herança** e **polimorfismo**.
- Utilizar **interfaces** para comportamentos especializados.
- Criar uma **arquitetura modular e escalável**.
- Desenvolver uma **interface gráfica interativa** com Swing.
- Documentar decisões de design e organização do código.

---

# 🌳 Funcionalidades Implementadas (Árvore de Requisitos)

**Pontuação mínima exigida:** 32 pontos  
**Pontuação alcançada:** **53 pontos**

---

## ⚔️ Sistema de Combate (15 pontos)

Funcionalidades implementadas:

- Ataque básico (3 pontos)
- Sistema de morte (3 pontos)
- Alcance variável (4 pontos)
- Esquiva (5 pontos)

---

## 🎮 Controles Avançados (15 pontos)

Funcionalidades implementadas:

- Filtros por tipo de personagem (4 pontos)
- Controle de montaria (5 pontos)
- Atalhos de teclado para ações rápidas (6 pontos)

---

## 🏗️ Arquitetura de Software (15 pontos)

Funcionalidades implementadas:

- Arquivo de configurações externo (3 pontos)
- Fábrica de personagens utilizando padrão de projeto (6 pontos)
- Sistema de cache para recursos (6 pontos)

---

## 🎨 Interface do Usuário (4 pontos)

Funcionalidade implementada:

- Barra de vida exibida visualmente nos personagens (4 pontos)

Funcionalidades NÃO implementadas:

- Efeitos sonoros
- Tooltips informativos

---

## 🪓 Funcionalidades de Jogo (4 pontos)

Funcionalidade implementada:

- Sistema de coleta (4 pontos)

Funcionalidades NÃO implementadas:

- Economia
- Salvar e carregar progresso

---

# ⌨️ Atalhos de Teclado Utilizados no Jogo

Para melhorar a experiência do jogador e agilizar o controle dos personagens, o projeto implementa um conjunto de atalhos de teclado que permitem realizar ações rapidamente sem depender apenas da interface gráfica.

## 🔹 Ações de Personagem

- **Space** → Realiza ataque
- **C** → Inicia a ação de coleta
- **M** → Monta ou desmonta (controle de montaria)
- **Tab** → Alterna o personagem atualmente selecionado

## 🔹 Movimentação

As setas direcionais controlam diretamente o personagem selecionado:

- **Seta para Cima** → Move para cima
- **Seta para Baixo** → Move para baixo
- **Seta para Esquerda** → Move para a esquerda
- **Seta para Direita** → Move para a direita

## 🔹 Criação de Personagens

Os números do teclado permitem criar personagens rapidamente:

- **1** → Criar Aldeão
- **2** → Criar Arqueiro
- **3** → Criar Cavaleiro

Esses atalhos foram implementados para tornar o fluxo do jogo mais dinâmico, aproximando a experiência de um RTS tradicional e facilitando o gerenciamento de múltiplos personagens.

---

# 🧩 Decisões de Design Importantes

## ✔️ Arquitetura Modular

A estrutura do código separa claramente:

- **domain/** → lógica dos personagens
- **ui/** → interface gráfica e controles
- **enums/** → tipos e constantes do jogo
- **consts/** → constantes do jogo

Essa separação aumenta organização, clareza e facilita a manutenção.

---

## ✔️ Uso do Padrão *Factory* (Fábrica de Personagens)

Todos os personagens são criados através de uma fábrica, o que garante:

- Centralização da lógica de construção
- Redução de duplicação de código
- Facilidade para adicionar novos personagens futuramente

---

## ✔️ Cache de Recursos

O sistema de cache evita recarregar imagens repetidas vezes.

Benefícios:

- Menor consumo de memória
- Melhor desempenho
- Menor acoplamento entre interface gráfica e recursos

---

# 🚀 Como Executar o Projeto

## Pré-requisitos

- Java **JDK 17 ou superior**
- Gradle (o projeto já inclui o wrapper `gradlew`)
- IDE recomendada: **IntelliJ IDEA**

---

## Passos para executar

1. **Clonar o repositório:**

```bash
git clone https://github.com/sergio-prolo-class/projeto-2-leonardo_victor-talles_souza.git
```

2.  Entrar na pasta do projeto:
```bash
cd projeto-2-leonardo_victor-talles_souza
```

3. Executar o projeto com o Gradle:
* Linux / MacOS:
```bash
./gradlew run
```

* Windows:
``` bash
gradlew run
```