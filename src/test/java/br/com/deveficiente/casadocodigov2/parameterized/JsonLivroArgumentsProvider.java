package br.com.deveficiente.casadocodigov2.parameterized;

import br.com.deveficiente.casadocodigov2.model.livro.NovoLivroRequest;
import com.github.javafaker.Faker;
import net.jqwik.api.Arbitrary;
import net.jqwik.api.Provide;
import net.jqwik.time.api.Dates;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import java.util.stream.Stream;

public class JsonLivroArgumentsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        Faker faker = new Faker();
        String isbn = faker.idNumber().valid() + "-" + UUID.randomUUID().toString().substring(0, 10);
        String tituloLivro = faker.book().title() + UUID.randomUUID().toString().substring(0, 5);;

        var livro1 = new NovoLivroRequest(tituloLivro, "Livro de spring sobre microserviços", "Construindo uma API Rest com Spring Boot", new BigDecimal("89.90"), 150, isbn, datasFuturas().sample(), 1L, 1L);
        var livro2 = new NovoLivroRequest(null, "Livro de spring sobre microserviços", "Construindo uma API Rest com Spring Boot", new BigDecimal("89.90"), 150, isbn, LocalDate.of(2024, 10, 20), 1L, 1L);
        var livro3 = new NovoLivroRequest(tituloLivro, null, "Construindo uma API Rest com Spring Boot", new BigDecimal("89.90"), 150, isbn, LocalDate.of(2024, 10, 20), 1L, 1L);
        var livro4 = new NovoLivroRequest(tituloLivro, "Livro de spring sobre microserviços", "Construindo uma API Rest com Spring Boot", null, 150, isbn, LocalDate.of(2024, 10, 20), 1L, 1L);
        var livro5 = new NovoLivroRequest(tituloLivro, "Livro de spring sobre microserviços", "Construindo uma API Rest com Spring Boot", new BigDecimal("89.90"), 0, isbn, LocalDate.of(2024, 10, 20), 1L, 1L);
        var livro6 = new NovoLivroRequest(tituloLivro, "Livro de spring sobre microserviços", "Construindo uma API Rest com Spring Boot", new BigDecimal("89.90"), 0, null, LocalDate.of(2024, 10, 20), 1L, 1L);
        var livro7 = new NovoLivroRequest(tituloLivro, "Livro de spring sobre microserviços", "Construindo uma API Rest com Spring Boot", new BigDecimal("89.90"), 0, null, LocalDate.of(2024, 10, 16), 1L, 1L);
        return Stream.of(
                Arguments.of(livro1, 201),
                Arguments.of(livro2, 400),
                Arguments.of(livro3, 400),
                Arguments.of(livro4, 400),
                Arguments.of(livro5, 400),
                Arguments.of(livro6, 400),
                Arguments.of(livro7, 400)
        );
    }

    @Provide
    Arbitrary<LocalDate> datasFuturas() {
        return Dates.dates().atTheEarliest(LocalDate.now().plusDays(1));
    }
}
