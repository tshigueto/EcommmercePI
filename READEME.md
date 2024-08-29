# Projeto: E-Commerce

## Visão Geral

Este documento fornece uma visão geral detalhada do projeto de e-commerce desenvolvido usando Java Spring Boot MVC, com frontend em HTML, JavaScript e CSS. O projeto adota design patterns para garantir uma arquitetura robusta e escalável.

## Sumário

1. [Introdução](#introdução)
2. [Arquitetura do Sistema](#arquitetura-do-sistema)
3. [Tecnologias Utilizadas](#tecnologias-utilizadas)
4. [Design Patterns Aplicados](#design-patterns-aplicados)
5. [Estrutura do Projeto](#estrutura-do-projeto)
6. [Configuração e Instalação](#configuração-e-instalação)
7. [Uso e Funcionalidades](#uso-e-funcionalidades)
8. [Contribuição e Suporte](#contribuição-e-suporte)

## Introdução

Este projeto é um sistema de e-commerce desenvolvido com Java Spring Boot MVC. Ele foi projetado para fornecer uma plataforma de compras online eficiente e escalável, com uma interface de usuário responsiva e amigável. O sistema é capaz de gerenciar produtos, processar pedidos e fornecer uma experiência de compra integrada para os usuários.

## Arquitetura do Sistema

O sistema adota uma arquitetura MVC (Model-View-Controller) para separar as responsabilidades e promover a manutenibilidade e escalabilidade.

- **Model:** Representa a camada de dados e a lógica de negócios. Utiliza entidades JPA para mapear os dados do banco de dados.
- **View:** Utiliza HTML, JavaScript e CSS para criar uma interface de usuário responsiva e interativa.
- **Controller:** Gerencia as solicitações do usuário, interage com o Model e retorna a resposta adequada à View.

## Tecnologias Utilizadas

- **Backend:** Java Spring Boot MVC
- **Frontend:** HTML, JavaScript, CSS
- **Banco de Dados:** H2
- **Design Patterns:** Strategy

## Design Patterns Aplicados

- **Strategy:** Permite definir uma família de algoritmos, encapsulá-los e torná-los intercambiáveis. Utilizado para selecionar algoritmos em tempo de execução e promover flexibilidade e escalabilidade na aplicação.

## Estrutura do Projeto

- **src/main/java/com/example/ecommerce**
  - **controller:** Contém classes responsáveis pela manipulação das requisições e respostas.
  - **model:** Contém as entidades e classes relacionadas à lógica de negócios.
  - **repository:** Contém interfaces para operações de acesso ao banco de dados.
  - **service:** Contém a lógica de negócios e serviços.
  - **config:** Contém classes de configuração do Spring Boot.

- **src/main/resources**
  - **templates:** Contém arquivos HTML para a camada de apresentação.
  - **static**
    - **css:** Contém arquivos de estilo CSS.
    - **js:** Contém arquivos de script JavaScript.
    - **images:** Contém arquivos de imagem.

## Configuração e Instalação

1. **Clone o Repositório**
   ```bash
   git clone https://github.com/juansouzamd/e-commerceMVC

## Uso e Funcionalidades

- **Cadastro e Login:** Permite aos usuários se cadastrar e fazer login.
- **Catálogo de Produtos:** Exibe uma lista de produtos disponíveis para compra.
- **Carrinho de Compras:** Permite adicionar produtos ao carrinho e gerenciar a compra.
- **Processamento de Pedidos:** Gerencia o checkout e o processamento de pagamentos.
