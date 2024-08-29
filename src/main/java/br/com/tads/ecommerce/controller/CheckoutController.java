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

import br.com.tads.ecommerce.model.*;
import br.com.tads.ecommerce.repository.OrdersRepository;
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

import static java.lang.Double.parseDouble;

@Controller
@RequestMapping("/checkout")
public class CheckoutController {

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private AddressService addressService;

    // Método checkout: Manipula solicitações GET para exibir a página de checkout.
    @GetMapping
    public String checkout(HttpSession session, Model model) {
        // Obtém o carrinho de compras e o usuário da sessão.
        ShoppingCart cartItem = (ShoppingCart) session.getAttribute("carrinho");
        Users user = (Users) session.getAttribute("usuario");

        // Obtém os endereços associados ao usuário.
        List<Address> adresses = addressService.getAddressesByUserId(user.getId());

        // Adiciona o carrinho de compras e os endereços ao modelo para serem exibidos na página de checkout.
        model.addAttribute("cartItem", cartItem);
        model.addAttribute("addresses", adresses);

        // Retorna o nome da página de checkout.
        return "checkout";
    }

    // Método placeOrder: Manipula solicitações POST para realizar um pedido.
    @PostMapping("/order")
    public String placeOrder(HttpSession session, Model model, String opcaoEndereco) {
        // Obtém o carrinho de compras e o usuário da sessão.
        ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("carrinho");
        Users user = (Users) session.getAttribute("usuario");

        // Lista para armazenar os pedidos realizados.
        List<Orders> orders = new ArrayList<>();

        // Para cada item no carrinho de compras, cria um novo pedido.
        for (CartItem cartItem : shoppingCart.getCartItems()) {
            Orders order = new Orders();
            order.setUser(user);
            order.setName(cartItem.getProduct().getName());
            order.setImage(cartItem.getProduct().getMain_image());
            order.setPrice(cartItem.getProduct().getPrice());
            order.setValueTotal(cartItem.getTotalPrice());
            order.setAddress(opcaoEndereco);
            order.setQuantity(cartItem.getQuantity());
            ordersService.saveOrder(order);
            orders.add(order);
        }

        // Remove o carrinho de compras da sessão.
        session.removeAttribute("carrinho");

        // Adiciona os pedidos ao modelo para serem exibidos na página de agradecimento.
        model.addAttribute("orders", orders);

        // Retorna o nome da página de agradecimento.
        return "thanks";
    }
}