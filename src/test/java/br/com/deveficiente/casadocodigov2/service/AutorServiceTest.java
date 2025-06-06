package br.com.deveficiente.casadocodigov2.service;

import br.com.deveficiente.casadocodigov2.entity.Autor;
import br.com.deveficiente.casadocodigov2.model.autor.CadastroAutorRequest;
import br.com.deveficiente.casadocodigov2.repository.AutorRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("tests")
@SpringBootTest
class AutorServiceTest {

    @Autowired
    private AutorService autorService;

    @Mock
    private AutorRepository autorRepository;

    AutorServiceTest() {
    }

    private static Stream<Arguments> provideAutorRequests() {
        return Stream.of(

                // Caso em que o e-mail já está cadastrado
                Arguments.of(new CadastroAutorRequest("Allan Eduardo", "teste-dev@gmail.com", "Testando"),
                        true, // Simular que o autor já existe
                        "Existe autor cadastrado para o e-mail informado!"),
                // Caso em que o e-mail é único
                Arguments.of(new CadastroAutorRequest("Allan Barros", "teste-dev@gmail.com", "Testando"),
                        false, // Simular que o autor não existe
                        null) // Nenhuma exceção esperada


        );
    }

//    @Test
//    @DisplayName("Dado os parâmetros para criação, deverá ser possível cadastrar um autor.")
//    void testDeveCriarUmAutor() {
//        System.out.println("Testando...");
//
//        //Arrange
//        CadastroAutorRequest request = new CadastroAutorRequest("Allan Campos", "teste-dev@gmail.com", "testando");
//
//        //limpar o repositório antes do teste
//        autorRepository.deleteAll();
//
//        // Act
//        Autor result = autorService.create((request));
//
//        //Assert
//        assertNotNull(result);
//        assertEquals(request.nome(), result.getNome());
//        assertEquals(request.email(), result.getEmail());
//    }
//
//    @Test
//    @DisplayName("Deve tentar cadastrar um autor com o mesmo email e deverá retornar uma exceção")
//    void testDeveTentarCriarUmAutorComOMesmoEmailERetornarExcecao() {
//        AutorRepository autorRepository = mock(AutorRepository.class);
//        AutorService autorService = new AutorService(autorRepository);
//
//        CadastroAutorRequest autorForm = new CadastroAutorRequest("Nome do Autor", "autor@example.com", "Descrição");
//
//        /// Simular que a validação customizada dispara
//        doThrow(new DataIntegrityViolationException("Existe autor cadastrado para o e-mail informado!"))
//                .when(autorRepository).save(any());
//
//        // Act and Assert
//        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class,
//                () -> autorService.create(autorForm));
//
//        // Verificar se a exceção contém a mensagem esperada
//        assertNotNull(exception.getMessage());
//        assertTrue(exception.getMessage().contains("Existe autor cadastrado para o e-mail informado!"));
//
//        // Verificar se o método save não foi chamado
//        verify(autorRepository).save(any());
//    }

    @ParameterizedTest
    @MethodSource("provideAutorRequests")
    @DisplayName("Dado os parâmetros para criação, deverá ser possível cadastrar um autor e validar o e-mail.")
    public void testCriaAutor(CadastroAutorRequest autorForm, boolean emailExists, String expectedExceptionMessage) {
        // Arrange
        AutorRepository autorRepository = mock(AutorRepository.class);
        AutorService autorService = new AutorService(autorRepository);

        if (emailExists) {
            // Simular que a validação customizada dispara
            doThrow(new DataIntegrityViolationException(expectedExceptionMessage)).when(autorRepository).save(any());
        } else {
            // Simular a criação de um novo autor
            Autor autor = Autor.builder()
                    .nome(autorForm.nome())
                    .email(autorForm.email())
                    .descricao(autorForm.descricao())
                    .build();
            when(autorRepository.save(any(Autor.class))).thenReturn(autor);
        }
        // Verificação do validaPayload antes de criar o autor
        boolean isValid = autorForm.validaPayload();
        assertTrue(isValid, "Payload deve ser válido");

        if (expectedExceptionMessage != null) {
            // Act and Assert
            DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class,
                    () -> autorService.create(autorForm));

            // Verificar se a exceção contém a mensagem esperada da validação customizada
            assertNotNull(exception.getMessage());
            assertTrue(exception.getMessage().contains(expectedExceptionMessage));
        } else {
            // Act
            Autor result = autorService.create(autorForm);

            // Assert
            assertNotNull(result);
            assertEquals(autorForm.nome(), result.getNome());
            assertEquals(autorForm.email(), result.getEmail());
        }

        // Verificar se o método save foi chamado (ou não, dependendo do caso)
        verify(autorRepository).save(any(Autor.class));
    }

    private static Stream<Arguments> provideCadastroAutorRequests() {
        return Stream.of(
                Arguments.of("Allan", "allanbc@gmail.com", "Testando", true),
                Arguments.of(null, "allanbc@gmail.com", "Testando", false),
                Arguments.of("Allan", null, "Testando", false),
                Arguments.of("Allan", "allanbc@gmail.com", null, false),
                Arguments.of(null, null, null, false)
        );
    }
    @ParameterizedTest
    @MethodSource("provideCadastroAutorRequests")
    public void testValidaPayload(String nome, String email, String descricao, boolean expected) {
        // Arrange
        CadastroAutorRequest request = new CadastroAutorRequest(nome, email, descricao);

        // Act
        boolean result = request.validaPayload();

        // Assert
        assertEquals(expected, result);
    }

}
