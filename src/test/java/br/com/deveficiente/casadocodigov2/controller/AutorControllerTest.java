package br.com.deveficiente.casadocodigov2.controller;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ActiveProfiles("tests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc
class AutorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    void deveRetornarErro422AoTentarCriarAutorComErros() throws Exception {
        //ARRANGE
        String json = "{}";

        //ACT
        var response = mockMvc.perform(
                post("/autores")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(422, response.getStatus());
    }

    @Test
    @Order(2)
    void deveRetornar200AoCriarAutorComSucesso() throws Exception {
        //ARRANGE
        String json = """
                {
                    "nome": "Allan",
                    "email": "teste6@gmail.com",
                    "descricao": "teste descricao 2"
                }
                """;

        //ACT
        var response = mockMvc.perform(
                post("/autores")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(200, response.getStatus());
    }

    @ParameterizedTest
    @MethodSource("codigoAutorInexistente")
    @Order(3)
    void deveRetornar200SeAutorEstiverCadastrado(Long id, int expectedStatus) throws Exception {
        //ARRANGE => codigoAutorInexistente
        //ACT
        var response = mockMvc.perform(
                get("/autores/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(expectedStatus, response.getStatus());
    }
    private static Stream<Arguments> codigoAutorInexistente() {
        return Stream.of(
                Arguments.of(1L, 200),
                Arguments.of(null, 404),
                Arguments.of(0L, 404)
        );
    }
}