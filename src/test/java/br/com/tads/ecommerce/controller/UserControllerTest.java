package br.com.tads.ecommerce.controller;

import br.com.tads.ecommerce.model.Address;
import br.com.tads.ecommerce.model.Orders;
import br.com.tads.ecommerce.model.Users;
import br.com.tads.ecommerce.service.AddressService;
import br.com.tads.ecommerce.service.OrdersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    // Injeta MockMvc para simular requisições HTTP
    @Autowired
    private MockMvc mockMvc;

    // Injeta WebApplicationContext para configurar MockMvc
    @Autowired
    private WebApplicationContext webApplicationContext;

    // Cria mocks para os serviços usados pelo controlador
    @MockBean
    private AddressService addressService;

    @MockBean
    private OrdersService ordersService;

    // Declara um objeto Users para representar um usuário fictício
    private Users mockUser;

    // Método executado antes de cada teste para configurar o ambiente de teste
    @BeforeEach
    public void setUp() {
        // Cria um usuário fictício
        mockUser = new Users();
        mockUser.setId(1L);
        mockUser.setName("Test User");

        // Configura MockMvc para usar o contexto da aplicação web
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    // Testa o método showPageUser do controlador
    @Test
    public void showPageUserTest() throws Exception {
        // Cria listas vazias para endereços e pedidos fictícios
        List<Address> mockAddresses = new ArrayList<>();
        List<Orders> mockOrders = new ArrayList<>();

        // Configura os mocks para retornar listas vazias quando solicitados
        when(addressService.getAddressesByUserId(mockUser.getId())).thenReturn(mockAddresses);
        when(ordersService.getOrdersByUserId(mockUser.getId())).thenReturn(mockOrders);

        // Simula uma requisição GET para /user com o usuário fictício na sessão
        mockMvc.perform(get("/user").sessionAttr("usuario", mockUser))
                // Verifica se o status HTTP é 200 (OK)
                .andExpect(status().isOk())
                // Verifica se a view retornada é "user"
                .andExpect(view().name("user"))
                // Verifica se os atributos do modelo estão presentes
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("address"))
                .andExpect(model().attributeExists("adresses"))
                .andExpect(model().attributeExists("orders"));
    }
}
