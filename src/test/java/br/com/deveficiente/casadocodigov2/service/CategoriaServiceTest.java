package br.com.deveficiente.casadocodigov2.service;

import br.com.deveficiente.casadocodigov2.entity.Categoria;
import br.com.deveficiente.casadocodigov2.model.categoria.CadastroCategoriaRequest;
import br.com.deveficiente.casadocodigov2.repository.CategoriaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("tests")
@SpringBootTest
class CategoriaServiceTest {

    @Mock
    private CategoriaRepository categoriaRepository;


    private static Stream<Arguments> provideCategoriaRequests() {
        return Stream.of(
                // Caso em que a categoria ainda não existe
                Arguments.of(new CadastroCategoriaRequest("DevOps"),
                        false,
                        null),
                // Caso em que a categoria já está cadastrado
                Arguments.of(new CadastroCategoriaRequest("DevOps"),
                        true, // Simular que a categoria já existe
                        "Existe categoria cadastrada com esse nome!")
        );
    }
    @ParameterizedTest
    @MethodSource("provideCategoriaRequests")
    @DisplayName(("Dado os parâmetros para criação, deverá ser possível cadastrar uma categoria."))
    public void testCriaCategoria(CadastroCategoriaRequest categoriaRequest, boolean nomeExists, String expectedExceptionMessage) {
        //Arrange
        categoriaRepository = mock(CategoriaRepository.class);
        CategoriaService categoriaService = new CategoriaService(categoriaRepository);

        if(nomeExists) {
            doThrow(new DataIntegrityViolationException(expectedExceptionMessage)).when(categoriaRepository).save(any());
        } else {
            Categoria categoria;
            categoria = new Categoria();
            categoria.setNome(categoriaRequest.nome());
            when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoria);
        }

        if(expectedExceptionMessage != null) {
            //Act and Assert
            DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class,
                    () -> categoriaService.create(categoriaRequest));
            // Verificar se a exceção contém a mensagem esperada da validação customizada
            assertNotNull(exception.getMessage());
            assertTrue(exception.getMessage().contains(expectedExceptionMessage));
        } else {
            //Act
            Categoria result = categoriaService.create(categoriaRequest);

            //Assert
            assertNotNull(result);
            assertEquals(categoriaRequest.nome(), result.getNome());
        }

        verify(categoriaRepository).save(any(Categoria.class));
    }

}