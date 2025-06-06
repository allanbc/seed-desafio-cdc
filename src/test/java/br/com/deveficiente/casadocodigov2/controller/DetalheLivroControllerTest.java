package br.com.deveficiente.casadocodigov2.controller;

import br.com.deveficiente.casadocodigov2.model.livro.NovoLivroRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ActiveProfiles("tests")
@SpringBootTest
@AutoConfigureMockMvc
public class DetalheLivroControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    @DisplayName("exibe detalhes do livro corretamente")
    public void testDetalheLivro() throws Exception {
        //ARRANGE
        BigDecimal valorLivro = new BigDecimal("89.90");
        String titulo = "Spring Boot e Microservices";
        String resumo = "Livro de spring sobre microserviços";
        String sumario = "Construindo uma API Rest com Spring Boot";
        int numPaginas = 150;
        String isbn = "978-1803233307";
        var dataPublicacao = LocalDate.now().plusDays(1);

        String payloadAutores = """
                {
                    "nome": "Allan",
                    "email": "teste6@gmail.com",
                    "descricao": "teste descricao 2"
                }
                """;
        mockMvc.perform(post("/autores").content(payloadAutores)
                        .contentType(MediaType.APPLICATION_JSON));

        String payloadCategorias = """
                {
                    "nome": "DevOps"
                }
                """;
        mockMvc.perform(
                post("/categorias").content(payloadCategorias)
                        .contentType(MediaType.APPLICATION_JSON));

        //ACT
        var livroRequest = new NovoLivroRequest("Spring Boot e Microservices", "Livro de spring sobre microserviços", "Construindo uma API Rest com Spring Boot", new BigDecimal("89.90"), 150, "978-1803233307", dataPublicacao, 1L, 1L);

        mockMvc.perform(
                post("/livros")
                        .content(objectMapper.writeValueAsString(livroRequest))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        ResultActions resultado = mockMvc.perform(get("/livros/produtos/1").contentType(MediaType.APPLICATION_JSON));

        Map<String, Object> autor = Map.of("nome","Allan");
        Map<String, Object> detalheLivro = Map.of(
                "titulo", titulo,
                "isbn",isbn,
                "numPagina", numPaginas,
                "preco", valorLivro.setScale(2),
                "resumo",resumo,
                "sumario", sumario,
                "dataPublicacao", dataPublicacao.toString(),
                "autor",autor);

        String jsonEsperado = objectMapper.writeValueAsString(detalheLivro);
        resultado.andExpect(MockMvcResultMatchers.content().json(jsonEsperado));

    }
}
