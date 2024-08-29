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
import br.com.tads.ecommerce.model.Users;
import br.com.tads.ecommerce.service.AddressService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/address")
public class AddressController {
    @Autowired
    private AddressService addressService;

    // Construtor AddressController: Injeta o serviço de endereço.
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    // Método showPageAddress: Manipula solicitações GET para exibir a página de atualização de endereço.
    @GetMapping("/update/{id}")
    public String showPageAddress(Model model, @PathVariable Long id) {
        // Obtém o endereço pelo ID fornecido.
        Address address = addressService.getAddressById(id);

        // Adiciona o endereço ao modelo para ser exibido na página.
        model.addAttribute("address", address);

        // Retorna o nome da página de atualização de endereço.
        return "updateAddress";
    }

    // Método updateAddress: Manipula solicitações POST para atualizar um endereço.
    @PostMapping("/update/{id}")
    public String updateAddress(@ModelAttribute("updateAddress") Address updateAddress,
                                HttpServletRequest request, @PathVariable Long id) {

        // Obtém o usuário da sessão.
        Users user = (Users) request.getSession().getAttribute("usuario");

        // Obtém o endereço pelo ID fornecido.
        Address address = addressService.getAddressById(id);

        // Atualiza os detalhes do endereço com os novos valores.
        address.setStreet(updateAddress.getStreet());
        address.setCep(updateAddress.getCep());
        address.setNeighborhood(updateAddress.getNeighborhood());
        address.setNumber(updateAddress.getNumber());

        // Salva as alterações no endereço.
        addressService.createAddress(address);

        // Redireciona para a página do usuário após a atualização do endereço.
        return "redirect:/user";
    }

    // Método deleteAddress: Manipula solicitações POST para excluir um endereço.
    @PostMapping("/delete")
    public String deleteAddress(@RequestParam("id") Long id, @RequestParam("userId") Long userId, HttpSession session) {
        // Exclui o endereço pelo ID fornecido.
        addressService.deleteAddressById(id);

        // Verifica se o usuário atual é o proprietário do endereço excluído e atualiza a lista de endereços na sessão, se necessário.
        Users user = (Users) session.getAttribute("usuario");
        if (user != null && user.getId().equals(userId)) {
            List<Address> addresses = addressService.getAddressesByUserId(userId);
            session.setAttribute("addresses", addresses);
        }

        // Redireciona para a página do usuário após a exclusão do endereço.
        return "redirect:/user";
    }
}
