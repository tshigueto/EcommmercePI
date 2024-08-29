package br.com.tads.ecommerce.controller;

import br.com.tads.ecommerce.model.Product;
import br.com.tads.ecommerce.model.ShoppingCart;
import br.com.tads.ecommerce.model.Users;
import br.com.tads.ecommerce.service.AddressService;
import br.com.tads.ecommerce.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ShoppingCartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private ProductService productService;

    @MockBean
    private AddressService addressService;

    private ShoppingCart mockCart;
    private Users mockUser;

    @BeforeEach
    public void setUp() {
        mockCart = new ShoppingCart();
        mockUser = new Users();
        mockUser.setId(1L);

        // Inicializa o mockMvc com as configurações do contexto da web
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testViewCart() throws Exception {
        // Cria uma sessão de mock e atribui o carrinho e o usuário
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("carrinho", mockCart);
        session.setAttribute("usuario", mockUser);

        // Realiza a solicitação GET ao endpoint /shoppingCart com a sessão
        mockMvc.perform(get("/shoppingCart").session(session))
                // Verifica se o status HTTP é 200 (OK)
                .andExpect(status().isOk())
                // Verifica se a visão retornada é "shoppingCart"
                .andExpect(view().name("shoppingCart"))
                // Verifica se os atributos do modelo esperados estão presentes
                .andExpect(model().attributeExists("shoppingCart"))
                .andExpect(model().attributeExists("totalPrice"))
                .andExpect(model().attributeExists("shippingCost"))
                .andExpect(model().attributeExists("total"))
                .andExpect(model().attributeExists("hasItems"))
                .andExpect(model().attributeExists("hasAddress"));
    }

    @Test
    public void testAddItemToCart() throws Exception {
        int productId = 4;
        int quantity = 2;

        // Simula um usuário logado na sessão
        Users user = new Users();
        user.setId(1L);
        user.setName("Test User");

        // Simula um produto existente
        Product product = new Product();
        product.setId(product.getId());
        product.setName("Test Product");
        product.setPrice(10.0); // Define um preço para o produto

        // Adiciona o produto ao contexto do teste
        when(productService.getProductById(productId)).thenReturn(product);

        // Realiza a solicitação ao endpoint com o usuário na sessão
        mockMvc.perform(post("/shoppingCart/add")
                        .param("produtoId", String.valueOf(productId))
                        .param("quantidade", String.valueOf(quantity))
                        .sessionAttr("usuario", user)
                        .sessionAttr("carrinho", new ShoppingCart()))
                // Verifica se o status HTTP é um redirecionamento 3xx
                .andExpect(status().is3xxRedirection())
                // Verifica se o redirecionamento é para /shoppingCart
                .andExpect(redirectedUrl("/shoppingCart"));

        // Verifica se o serviço ProductService é chamado para obter o produto pelo ID
        verify(productService, times(1)).getProductById(productId);
    }

    @Test
    public void testRemoveItemFromCart() throws Exception {
        Long productId = 1L;

        // Criar um usuário fictício para simular o login na sessão
        Users mockUser = new Users();
        mockUser.setId(1L);

        // Realiza a solicitação ao endpoint com o usuário na sessão
        mockMvc.perform(post("/shoppingCart/remove")
                        .param("produtoId", String.valueOf(productId))
                        .sessionAttr("carrinho", new ShoppingCart())
                        .sessionAttr("usuario", mockUser)) // Adicionar usuário à sessão
                // Verifica se o status HTTP é um redirecionamento 3xx
                .andExpect(status().is3xxRedirection())
                // Verifica se o redirecionamento é para /shoppingCart
                .andExpect(redirectedUrl("/shoppingCart"));

        // Verificar que o serviço ProductService nunca é chamado para obter o produto pelo ID
        verify(productService, never()).getProductById(anyInt());
    }
}
