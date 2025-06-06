package br.com.deveficiente.casadocodigov2.controller;

import br.com.deveficiente.casadocodigov2.entity.Autor;
import br.com.deveficiente.casadocodigov2.entity.Categoria;
import br.com.deveficiente.casadocodigov2.model.autor.AutorResponse;
import br.com.deveficiente.casadocodigov2.model.livro.NovoLivroRequest;
import br.com.deveficiente.casadocodigov2.parameterized.JsonLivroArgumentsProvider;
import br.com.deveficiente.casadocodigov2.service.AutorService;
import br.com.deveficiente.casadocodigov2.service.CategoriaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.javafaker.Faker;
import net.jqwik.api.Label;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ActiveProfiles("tests")
@SpringBootTest
@AutoConfigureMockMvc
class LivroControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Mock
    private CategoriaService categoriaService;

    @Mock
    private AutorService autorService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @ParameterizedTest
    @ArgumentsSource(JsonLivroArgumentsProvider.class)
    @Label("Fluxo de cadastro de um novo livro")
    void deveRetornarHttpCodeParaCadaArgumentoAoCriarLivro(NovoLivroRequest payloadLivro, int expectedStatus) throws Exception {
        //ARRANGE
        Faker faker = new Faker();
        String nomeAutor = faker.name().fullName() + UUID.randomUUID().toString().substring(0, 3);
        String tituloLivro = faker.book().title() + UUID.randomUUID().toString().substring(0, 5);
        String emailAutor = faker.internet().emailAddress() + "-" + UUID.randomUUID().toString().substring(0, 3);
        String categoria = faker.book().genre() + UUID.randomUUID().toString().substring(0, 3);
        String payloadAutores = String.format("""
        {
            "nome": "%s",
            "email": "%s",
            "descricao": "teste descricao 2"
        }
        """, nomeAutor, emailAutor);
       mockMvc.perform(
                post("/autores")
                        .content(payloadAutores)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        String payloadCategorias = String.format("""
        {
            "nome": "%s"
        }
        """, categoria);
        mockMvc.perform(
                post("/categorias")
                        .content(payloadCategorias)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        if (payloadLivro.idCategoria() != null) {
            Categoria categoriaMock = new Categoria(1L, categoria);
            when(categoriaService.findById(1L)).thenReturn(categoriaMock);
        }
        if (payloadLivro.idAutor() != null) {
            Autor autorMock = Autor.builder()
                    .id(1L)
                    .nome(nomeAutor)
                    .descricao("novo livro do autor")
                    .build();
            when(autorService.getById(1L)).thenReturn(new AutorResponse(autorMock));
        }
            String payload = objectMapper.writeValueAsString(payloadLivro);

            //ACT
            var responseLivros = mockMvc.perform(
                    post("/livros")
                            .content(payload)
                            .contentType(MediaType.APPLICATION_JSON)
            ).andReturn().getResponse();

            //ASSERT
            assertEquals(expectedStatus, responseLivros.getStatus());
        }

}