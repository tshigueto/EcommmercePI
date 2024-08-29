package br.com.tads.ecommerce.controller;

import br.com.tads.ecommerce.model.Product;
import br.com.tads.ecommerce.model.ProductImage;
import br.com.tads.ecommerce.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc; // Injeta o objeto MockMvc para testar os controladores

    @MockBean
    private ProductService productService; // Mock do serviço ProductService

    private List<Product> productList; // Lista de produtos
    private Product product; // Objeto de produto
    private List<ProductImage> productImageList; // Lista de imagens de produto

    @BeforeEach
    void setUp() {
        // Mock dos produtos
        product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setPrice(100.0);
        product.setDescription("Test Description");
        product.setGender("Male");
        product.setCategory("Clothing");
        product.setBrand("Test Brand");
        product.setMain_image("main_image.jpg");

        productList = new ArrayList<>();
        productList.add(product);

        // Mock das imagens do produto
        ProductImage productImage = new ProductImage();
        productImage.setId(1L);
        productImage.setPath("image1.jpg");
        productImage.setProduct(product);

        productImageList = new ArrayList<>();
        productImageList.add(productImage);
    }

    // Teste para o método getProdutos do controlador
    @Test
    public void testGetProdutos() throws Exception {
        // Configura o comportamento do serviço productService.getAllProducts() para retornar a lista de produtos mockados
        Mockito.when(productService.getAllProducts()).thenReturn(productList);

        // Realiza uma requisição GET para a raiz ("/") e verifica o comportamento esperado
        mockMvc.perform(get("/"))
                .andExpect(status().isOk()) // Espera um status HTTP 200 OK
                .andExpect(view().name("index")) // Espera que a view seja "index"
                .andExpect(model().attributeExists("firstHalfProducts")) // Espera que o atributo "firstHalfProducts" exista no modelo
                .andExpect(model().attributeExists("secondHalfProducts")) // Espera que o atributo "secondHalfProducts" exista no modelo
                .andExpect(model().attribute("firstHalfProducts", productList.subList(0, productList.size() / 2))) // Espera que o atributo "firstHalfProducts" seja igual à primeira metade da lista de produtos
                .andExpect(model().attribute("secondHalfProducts", productList.subList(productList.size() / 2, productList.size()))); // Espera que o atributo "secondHalfProducts" seja igual à segunda metade da lista de produtos
    }

}
