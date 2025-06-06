package br.com.deveficiente.casadocodigov2.controller;

import br.com.deveficiente.casadocodigov2.parameterized.JsonCategoriaArgumentsProvider;
import net.jqwik.api.Label;
import net.jqwik.spring.JqwikSpringSupport;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ActiveProfiles("tests")
@JqwikSpringSupport
@SpringBootTest
@AutoConfigureMockMvc
class CategoriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @ParameterizedTest
    @ArgumentsSource(JsonCategoriaArgumentsProvider.class)
    @Label("Fluxo de cadastro de uma nova categoria")
    void deveRetornarHttpCodeParaCadaArgumentoAoCriarCategoria(String payload, int expectedStatus) throws Exception {
        //ACT
        var response = mockMvc.perform(
                post("/categorias")
                        .content(payload)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();
        //ASSERT
        assertEquals(expectedStatus, response.getStatus());
    }

}