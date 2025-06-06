package br.com.deveficiente.casadocodigov2.parameterized;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class JsonCategoriaArgumentsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        String json0 = "";
        String json1 = "{}";

        String json2 = """
                {
                    "nome": "DevOps"
                }
                """;

        String json3 = """
                {
                    "nome": ""
                }
                """;

        return Stream.of(
                Arguments.of(json0, 500),
                Arguments.of(json1, 400),
                Arguments.of(json2, 201),
                Arguments.of(json3, 400)
        );
    }
}
