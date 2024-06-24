package br.com.deveficiente.casadocodigov2.parameterized;

import br.com.deveficiente.casadocodigov2.entity.Autor;
import net.jqwik.api.ForAll;
import net.jqwik.api.Label;
import net.jqwik.api.constraints.AlphaChars;
import net.jqwik.api.constraints.StringLength;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class JsonArgumentsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        //ARRANGE

        String json0 = "";
        String json1 = "{}";

        String json2 = """
                {
                    "nome": "Allan",
                    "email": "teste6@gmail.com",
                    "descricao": "teste descricao 2"
                }
                """;

        String json3 = """
                {
                    "nome": "",
                    "email": "teste6@gmail.com",
                    "descricao": "teste descricao 2"
                }
                """;

        String json4 = """
                {
                    "nome": "Allan",
                    "email": "",
                    "descricao": "teste descricao 2"
                }
                """;

        String json5 = """
                {
                    "nome": "Allan",
                    "email": "teste6@gmail.com",
                    "descricao": ""
                }
                """;

        return Stream.of(
                Arguments.of(json0, 500),
                Arguments.of(json1, 422),
                Arguments.of(json2, 200),
                Arguments.of(json3, 422),
                Arguments.of(json4, 422),
                Arguments.of(json5, 422)
        );
    }
}
