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
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;


    // Método showLoginPage: Manipula solicitações GET para exibir a página de login.
    @GetMapping("/login")
    public String showLoginPage(Model model, @RequestParam(name = "error", required = false) String error) {
        // Verifica se há um erro de autenticação e adiciona uma mensagem de erro ao modelo, se necessário.
        if (error != null) {
            model.addAttribute("errorMessage", "Usuário ou senha incorretos.");
        }
        // Retorna o nome da página de login a ser renderizada.
        return "login";
    }

    // Método loginUser: Manipula solicitações POST para autenticar o usuário.
    @PostMapping("/login")
    public String loginUser(@RequestParam("email") String email,
                            @RequestParam("password") String password,
                            Model model, HttpSession session) {
        // Busca o usuário pelo e-mail no banco de dados.
        Users user = userService.findByEmail(email);

        // Verifica se o usuário existe e se a senha está correta.
        if (user != null && user.getPassword().equals(password)) {
            // Define o usuário na sessão.
            session.setAttribute("usuario", user);
            // Redireciona para a página inicial após o login bem-sucedido.
            return "redirect:/";
        } else {
            // Adiciona uma mensagem de erro ao modelo e redireciona de volta para a página de login com um parâmetro de erro.
            model.addAttribute("error", "Credenciais inválidas. Por favor, tente novamente.");
            return "redirect:/login?error=true";
        }
    }

    // Método redirectLogout: Manipula solicitações GET para realizar logout.
    @GetMapping("/logout")
    public String redirectLogout(HttpSession session) {
        // Invalida a sessão do usuário.
        session.invalidate();
        // Redireciona para a página de login após o logout.
        return "redirect:/login";
    }
}

