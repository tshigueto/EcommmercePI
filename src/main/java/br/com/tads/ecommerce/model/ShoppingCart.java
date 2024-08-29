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

package br.com.tads.ecommerce.model;

import br.com.tads.ecommerce.strategy.*;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems = new ArrayList<>();

    @Transient
    private ShippingContext shippingContext = new ShippingContext();

    public void addItem(CartItem item) {
        cartItems.add(item);
    }

    public double getTotalPrice() {
        // Calcula o preço total somando os preços totais de todos os itens do carrinho
        return cartItems.stream().mapToDouble(CartItem::getTotalPrice).sum();
    }

    public double calculateShipping() {
        // Obtém o preço total do carrinho
        double totalPrice = getTotalPrice();

        // Define a estratégia de frete com base no preço total
        if (totalPrice < 300) {
            shippingContext.setStrategy(new LowPriceShippingStrategy());
        } else if (totalPrice < 600) {
            shippingContext.setStrategy(new MediumPriceShippingStrategy());
        } else {
            shippingContext.setStrategy(new HighPriceShippingStrategy());
        }

        // Executa a estratégia de frete e retorna o custo de envio calculado
        return shippingContext.executeStrategy(totalPrice);
    }
}
