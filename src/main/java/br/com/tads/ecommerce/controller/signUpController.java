/*
    Nome do Projeto: E-commerce
    Data de Criação: 15/02/2024
    Versão: 5
    Data da Última Modificação: 22/05/2024
    Versão do Java: 17
    Versão do Spring Boot: 3.2.4
    Banco de dados: H2
    Equipe de Desenvolvimento:
        - Juan Souza Santos
        - Maria Helena dos Santos
        - Pedro Ferreira Lima
        - Thiago Shigueto Hossaka

    Descrição: Este programa é uma plataforma de e-commerce destinada à venda de produtos variados.
        A aplicação permite aos usuários navegar por uma lista de produtos, adicionar itens ao carrinho
        de compras, calcular o custo total incluindo frete e finalizar a compra.

    Funcionalidades incluem cadastro e login de usuários, exibição de produtos, cálculo de preços,
        gerenciamento do carrinho de compras, checkout e armazenamento dos resultados em um banco de dados.
*/

package br.com.tads.ecommerce.controller;

import br.com.tads.ecommerce.model.Users;
import br.com.tads.ecommerce.repository.UserRepository;
import br.com.tads.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signUp")
public class signUpController {

    @Autowired
    private UserService userService;

    @PostMapping
    public String registerUser(Users user, Model model) {
        // Verifica se o e-mail já está em uso
        if (userService.existsByEmail(user.getEmail())) {
            // Adiciona uma mensagem de erro ao modelo
            model.addAttribute("error", "O e-mail fornecido já está em uso.");
            // Retorna a página de registro com uma mensagem de erro
            return "login";
        }
        // Salva o novo usuário no banco de dados
        userService.createUser(user);
        // Adiciona uma mensagem de sucesso ao modelo
        model.addAttribute("message", "Usuário cadastrado com sucesso!");
        // Redireciona para a página de login após o registro bem-sucedido
        return "redirect:/login";
    }

    @GetMapping
    public String showPageRegistration(Model model) {
        // Adiciona um novo objeto de usuário ao modelo para o formulário de registro
        model.addAttribute("users", new Users());
        // Retorna a página de registro
        return "signUp";
    }
}
