package br.com.deveficiente.casadocodigov2.parameterized;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

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
