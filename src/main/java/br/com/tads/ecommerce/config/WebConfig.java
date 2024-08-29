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

package br.com.tads.ecommerce.config;

import br.com.tads.ecommerce.component.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private AuthInterceptor authInterceptor;

    // Método addInterceptors: Adiciona interceptadores ao registro.
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Adiciona o interceptor de autenticação ao registro de interceptadores.
        registry.addInterceptor(authInterceptor)
                // Define os padrões de URL aos quais o interceptor será aplicado.
                .addPathPatterns("/**")
                // Define os padrões de URL que serão excluídos do interceptor.
                .excludePathPatterns("/login", "/logout", "/public/**", "/css/**", "/img/**", "/js/**", "/",
                        "/contact", "/signUp", "/about", "/console");
    }
}

