package br.com.deveficiente.casadocodigov2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.jqwik.api.ForAll;
import net.jqwik.api.Label;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.AlphaChars;
import net.jqwik.api.constraints.StringLength;
import net.jqwik.spring.JqwikSpringSupport;
import org.junit.jupiter.api.Assumptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("tests")
@JqwikSpringSupport
@SpringBootTest
@AutoConfigureMockMvc
public class CriaPaisControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private static final Set<String> unicos = new HashSet<>();
    @Property(tries = 10)
    @Label("fluxo de cadastro de novo pais")
    void testCadastroPaiseRetorna200SeSucessoE400SeInsucesso(@ForAll @AlphaChars @StringLength(min = 1, max = 255) String nome) throws Exception {

        Assumptions.assumeTrue(unicos.add(nome));

        // Arrange (dados de entrada)
        String jsonRequest = objectMapper.writeValueAsString(Map.of("nome", nome));

        // Act (chamada à API)
        mockMvc.perform(post("/dominios/paises")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().is2xxSuccessful());

        mockMvc.perform(post("/dominios/paises")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().is4xxClientError());

    }
}
