package br.com.tads.ecommerce.controller;

import br.com.tads.ecommerce.model.Address;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerCreateAddressTest {

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

    // Testa o método criarEndereco do controlador
    @Test
    public void criarEnderecoTest() throws Exception {
        // Cria um endereço fictício
        Address mockAddress = new Address();
        mockAddress.setStreet("Test Street");
        mockAddress.setCep("00000");
        mockAddress.setNumber(12);
        mockAddress.setNeighborhood("JD senac");

        // Simula uma requisição POST para /user/address com o usuário fictício na sessão e o endereço fictício como parâmetro
        mockMvc.perform(post("/user/address")
                        .param("street", "Test Street")
                        .sessionAttr("usuario", mockUser))
                // Verifica se o status HTTP é uma redirecionamento 3xx
                .andExpect(status().is3xxRedirection())
                // Verifica se o redirecionamento é para /user
                .andExpect(redirectedUrl("/user"));

        // Verifica se o método createAddress do serviço de endereços foi chamado com o endereço fictício
        verify(addressService).createAddress(any(Address.class));
    }
}
