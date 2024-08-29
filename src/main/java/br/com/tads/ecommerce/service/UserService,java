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

package br.com.tads.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.tads.ecommerce.model.Users;
import br.com.tads.ecommerce.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean existsByEmail(String email) {
        // Verifica se um usuário com o email fornecido já existe
        return userRepository.existsByEmail(email);
    }

    public void createUser(Users user) {
        // Cria um novo usuário no banco de dados
        userRepository.save(user);
    }

    public Users findByEmail(String email) {
        // Retorna o usuário com o email fornecido, se existir
        return userRepository.findByEmail(email);
    }

    public boolean verifyCredentials(String email, String password) {
        // Verifica se as credenciais de login (email e senha) são válidas
        Users user = userRepository.findByEmail(email);
        return user != null && user.getPassword().equals(password);
    }
}

