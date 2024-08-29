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

import br.com.tads.ecommerce.model.Address;
import br.com.tads.ecommerce.model.Orders;
import br.com.tads.ecommerce.model.Users;
import br.com.tads.ecommerce.service.AddressService;
import br.com.tads.ecommerce.service.OrdersService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private AddressService addressService;

    @Autowired
    private OrdersService ordersService;

    @Autowired
    public UserController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping
    public String showPageUser(Model model, HttpSession session) {
        // Obtém o usuário da sessão
        Users user = (Users) session.getAttribute("usuario");
        if (user != null) {
            // Obtém os endereços do usuário
            List<Address> adresses = addressService.getAddressesByUserId(user.getId());
            // Obtém os pedidos do usuário
            List<Orders> orders = ordersService.getOrdersByUserId(user.getId());
            // Adiciona o usuário, endereços e pedidos ao modelo
            model.addAttribute("user", user);
            model.addAttribute("address", new Address());
            model.addAttribute("adresses", adresses.isEmpty() ? new ArrayList<>() : adresses);
            model.addAttribute("orders", orders.isEmpty() ? new ArrayList<>() : orders);
        }
        // Retorna a página do usuário
        return "user";
    }

    @PostMapping("/address")
    public String criarEndereco(Address endereco, HttpSession session) {
        // Obtém o usuário da sessão
        Users user = (Users) session.getAttribute("usuario");
        // Define o usuário para o endereço
        endereco.setUser(user);
        // Cria o endereço
        addressService.createAddress(endereco);
        // Redireciona de volta para a página do usuário
        return "redirect:/user";
    }
}
