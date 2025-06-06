package br.com.deveficiente.casadocodigov2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import net.jqwik.api.*;
import net.jqwik.api.constraints.AlphaChars;
import net.jqwik.api.constraints.BigRange;
import net.jqwik.api.constraints.StringLength;
import net.jqwik.spring.JqwikSpringSupport;
import net.jqwik.time.api.Dates;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("tests")
@JqwikSpringSupport
@SpringBootTest
@AutoConfigureMockMvc
public class NovoCupomControllerTest {
    private final Set<String> unicos = new HashSet<>();
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Property(tries = 10)
    @Label("fluxo de cadastro de novo cupom")
    void testCadastroNovoCupom(
            @ForAll @AlphaChars @StringLength(min = 3, max = 255) String codigo,
            @ForAll @BigRange(min = "1", max = "90") BigDecimal percentualDesconto,
            @ForAll("datasFuturas") LocalDate validade) throws Exception {

        Assumptions.assumeTrue(unicos.add(codigo));
        String validadeFormatada = validade
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        Map<String, Object> novoCupom = Map.of(
                "codigo", codigo,
                "percentualDesconto", percentualDesconto,
                "validade", validadeFormatada);

        mvc.perform(
                post("/cupom")
                        .content(objectMapper.writeValueAsString(novoCupom))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().is2xxSuccessful());

        mvc.perform(
                post("/cupom")
                        .content(objectMapper.writeValueAsString(novoCupom))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().is4xxClientError());

    }

    @Provide
    Arbitrary<LocalDate> datasFuturas() {
        return Dates.dates().atTheEarliest(LocalDate.now().plusDays(1));
    }
}
