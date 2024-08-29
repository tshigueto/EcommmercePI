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

import br.com.tads.ecommerce.model.ProductImage;
import br.com.tads.ecommerce.model.Product;
import br.com.tads.ecommerce.service.ProductService;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/")
public class ProductController {
    private final ProductService produtoService;

    @Autowired
    public ProductController(ProductService produtoService) {
        this.produtoService = produtoService;
    }

    // Método getProdutos: Manipula solicitações GET para obter todos os produtos e exibi-los na página inicial.
    @GetMapping
    public String getProdutos(Model model) {
        // Obtém todos os produtos do serviço de produto.
        List<Product> product = produtoService.getAllProducts();
        // Divide a lista de produtos ao meio.
        int sizeList = product.size();
        int half = sizeList / 2;
        List<Product> firstHalfProducts = product.subList(0, half);
        List<Product> secondHalfProducts = product.subList(half, sizeList);
        // Adiciona as duas metades da lista de produtos ao modelo.
        model.addAttribute("firstHalfProducts", firstHalfProducts);
        model.addAttribute("secondHalfProducts", secondHalfProducts);
        // Retorna o nome da página inicial a ser renderizada.
        return "index";
    }

    // Método getProduto: Manipula solicitações GET para exibir os detalhes de um produto específico.
    @GetMapping("product/{id}")
    public String getProduto(@PathVariable int id, Model model) {
        // Obtém o produto com o ID especificado do serviço de produto.
        Product product = produtoService.getProductById(id);
        // Obtém todas as imagens associadas ao produto.
        List<ProductImage> images = produtoService.getImagesByProductId(product.getId());
        // Adiciona o produto e as imagens ao modelo.
        model.addAttribute("product", product);
        model.addAttribute("images", images);
        model.addAttribute("id", id);

        // Obtém todos os produtos, exceto o produto atual, para exibir produtos relacionados.
        List<Product> allProducts = produtoService.getAllProducts();
        allProducts.removeIf(x -> x.getId().equals(id));
        Collections.shuffle(allProducts);
        List<Product> randomProducts = allProducts.subList(0, 4);
        // Adiciona produtos relacionados ao modelo.
        model.addAttribute("randomProducts", randomProducts);

        // Retorna o nome da página de detalhes do produto a ser renderizada.
        return "product";
    }
}
