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
import br.com.tads.ecommerce.repository.CartItemRepository;
import br.com.tads.ecommerce.repository.UserRepository;
import br.com.tads.ecommerce.service.AddressService;
import br.com.tads.ecommerce.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

@Controller
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    @Autowired
    private ProductService productService;

    @Autowired
    private AddressService addressService;


    @GetMapping
    public String viewCart(HttpSession session, Model model) {
        // Obtém o carrinho de compras da sessão do usuário
        ShoppingCart cartItem = (ShoppingCart) session.getAttribute("carrinho");
        // Obtém o usuário da sessão
        Users user = (Users) session.getAttribute("usuario");

        // Se o carrinho estiver vazio, cria um novo
        if (cartItem == null) {
            cartItem = new ShoppingCart();
            session.setAttribute("carrinho", cartItem);
        }

        // Obtém os endereços do usuário
        List<Address> adresses = addressService.getAddressesByUserId(user.getId());

        // Formatação dos preços
        DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
        symbols.setDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("#.00", symbols);
        String totalPriceFormatted = df.format(cartItem.getTotalPrice());
        String shippingCostFormatted = df.format(cartItem.calculateShipping());

        // Convertendo as strings formatadas em números
        double totalPrice = Double.parseDouble(totalPriceFormatted);
        double shippingCost = Double.parseDouble(shippingCostFormatted);

        // Calcula o total
        double total = totalPrice + shippingCost;
        String totalValue = df.format(total);

        // Verifica se o frete é grátis
        if (total >= 600) {
            shippingCostFormatted = "Frete Grátis";
        } else {
            // Adiciona o símbolo de moeda ao custo de envio
            shippingCostFormatted = "R$ " + shippingCostFormatted;
        }

        // Adiciona os atributos ao modelo
        model.addAttribute("shoppingCart", cartItem);
        model.addAttribute("totalPrice", totalPriceFormatted);
        model.addAttribute("shippingCost", shippingCostFormatted);
        model.addAttribute("total", totalValue);

        // Verifica se o carrinho e os endereços têm itens
        boolean hasItems = !cartItem.getCartItems().isEmpty();
        boolean hasAddress = !adresses.isEmpty();

        model.addAttribute("hasItems", hasItems);
        model.addAttribute("hasAddress", hasAddress);

        // Retorna a página do carrinho de compras
        return "shoppingCart";
    }

    @PostMapping("/add")
    public String addItemToCart(@RequestParam int produtoId, @RequestParam int quantidade, HttpSession session) {
        // Obtém o carrinho de compras da sessão
        ShoppingCart cartItem = (ShoppingCart) session.getAttribute("carrinho");
        // Se o carrinho estiver vazio, cria um novo
        if (cartItem == null) {
            cartItem = new ShoppingCart();
            session.setAttribute("carrinho", cartItem);
        }

        // Obtém o produto pelo ID
        Product product = productService.getProductById(produtoId);
        // Cria um novo item de carrinho
        CartItem item = new CartItem();
        item.setProduct(product);
        item.setQuantity(quantidade);
        item.setPrice(product.getPrice());
        // Adiciona o item ao carrinho
        cartItem.addItem(item);

        // Salva o item de carrinho no repositório
        productService.saveCartItem(item);

        // Redireciona de volta para a página do carrinho de compras
        return "redirect:/shoppingCart";
    }

    @PostMapping("/remove")
    public String removeItemFromCart(@RequestParam Long produtoId, HttpSession session) {
        // Obtém o carrinho de compras da sessão
        ShoppingCart cartItem = (ShoppingCart) session.getAttribute("carrinho");
        // Remove o item do carrinho se ele existir
        if (cartItem != null) {
            cartItem.getCartItems().removeIf(item -> item.getProduct().getId().equals(produtoId));
        }
        // Redireciona de volta para a página do carrinho de compras
        return "redirect:/shoppingCart";
    }
}
