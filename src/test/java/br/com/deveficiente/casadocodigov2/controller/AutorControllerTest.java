package br.com.deveficiente.casadocodigov2.controller;

import br.com.deveficiente.casadocodigov2.parameterized.JsonArgumentsProvider;
import net.jqwik.api.Label;
import net.jqwik.spring.JqwikSpringSupport;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsSource;
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
@JqwikSpringSupport
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) //Coloca os testes na ordem de execução
@SpringBootTest //Levanta todo o contexto inteiro do spring::controller, service e hibernate
@AutoConfigureMockMvc
class AutorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Order(1)
    @ParameterizedTest
    @ArgumentsSource(JsonArgumentsProvider.class)
    @Label("Fluxo de cadastro de um novo autor")
    void deveRetornar200AoCriarAutorComSucessoJsonArgumentsProvider(String payload, int expectedStatus) throws Exception {
        //ACT
        var response = mockMvc.perform(
                post("/autores")
                        .content(payload)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        assertEquals(expectedStatus, response.getStatus());
    }

    @ParameterizedTest
    @MethodSource("codigoAutorInexistente")
    @Order(2)
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