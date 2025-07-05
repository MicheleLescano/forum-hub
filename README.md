# Fórum Hub - API REST

<p align="center">
  <img src="https://img.shields.io/badge/Status-Concluído-brightgreen" alt="Status do Projeto">
</p>

## 📖 Descrição do Projeto

API REST para um fórum de discussões, desenvolvida como o desafio final do programa ONE (Oracle Next Education) em parceria com a Alura. A aplicação simula o back-end de um fórum, permitindo o gerenciamento completo de tópicos através de um CRUD (Create, Read, Update, Delete).

## ✨ Funcionalidades Principais

A API do Fórum Hub oferece as seguintes funcionalidades para a entidade de **Tópicos**:

-   **`POST /topicos`**: Cadastra um novo tópico no banco de dados.
-   **`GET /topicos`**: Lista todos os tópicos ativos, com paginação e ordenação padrão por data de criação.
-   **`GET /topicos/{id}`**: Exibe os detalhes de um tópico específico pelo seu ID.
-   **`PUT /topicos/{id}`**: Atualiza os dados de um tópico existente.
-   **`DELETE /topicos/{id}`**: Realiza a exclusão lógica de um tópico, marcando-o como inativo.

### Regras de Negócio Implementadas
- **Validação de Campos:** Os campos para cadastro de um tópico são validados para não serem nulos ou vazios.
- **Prevenção de Duplicatas:** A API impede o cadastro ou a atualização de um tópico que resulte em título e mensagem idênticos a um já existente.
- **Tratamento de Erros:** A API retorna o código `404 Not Found` ao tentar acessar ou manipular um tópico com um ID inexistente.

## 🛠️ Tecnologias Utilizadas

-   **Java 17**
-   **Spring Boot 3**
-   **Spring Web**
-   **Spring Data JPA**
-   **Spring Security**
-   **Spring Boot Validation**
-   **Maven** (Gerenciador de Dependências)
-   **Lombok** (Redução de código boilerplate)
-   **H2 Database** (Banco de dados em memória para desenvolvimento)

---

Feito por **Michele Lescano** 👋